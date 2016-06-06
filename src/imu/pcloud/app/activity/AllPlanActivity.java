package imu.pcloud.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import imu.pcloud.app.R;
import imu.pcloud.app.adapter.AllPlanAdapter;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.PlanCircle;
import imu.pcloud.app.model.*;
import imu.pcloud.app.utils.PlanListTool;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by acer on 2016/5/15.,mn
 */
public class AllPlanActivity extends HttpActivity
        implements AdapterView.OnItemClickListener,
        PullToRefreshBase.OnRefreshListener<ListView>,AllPlanAdapter.MyOnClickListener {

    ArrayList<PersonalPlan> personalPlanArrayList = new ArrayList<PersonalPlan>();
    ArrayList<Integer> selectedPlanId = new ArrayList<Integer>();
    private PullToRefreshListView listView;
    AllPlanAdapter allPlanAdapter;
    AlertDialog shareDialog;
    private List<PlanCircle> planCircles = new ArrayList<PlanCircle>();
    private ArrayList<String> circleNames = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> planCircleItemList = new ArrayList<HashMap<String, Object>>();
    SimpleAdapter planCircleAdapter;
    private PlanCircle sharedPLanCircle;
    private PersonalPlan sharedPersonalPlan;
    private int clickFlag = 0;

    final int DELETE = 2;
    final int SHARE = 1;
    final int DEFAULT = 0;
    final int GETCIRCLE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allplan);
        init();
        allPlanAdapter.notifyDataSetChanged();
    }

    private void init() {
        setActionBar("设置计划");
        setPlanCircles();
        initShareDialog();
        listView = (PullToRefreshListView) findViewById(R.id.allplan_listview);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
        listView.setOnRefreshListener(this);
        allPlanAdapter = new AllPlanAdapter(this, personalPlanArrayList, getUserId());
        allPlanAdapter.setMyOnClickListener(this);
        listView.setAdapter(allPlanAdapter);
        get("getPlanList", "cookies", getCookie());
        setPlans();
    }

    public void getCloudPlan() {
        clickFlag = DEFAULT;
        get("getPlanList", "cookies", getCookie());
    }

    public void getPlanCircle() {
        clickFlag = GETCIRCLE;
        get("getPlanCircleList");
    }

    public void initShareDialog() {
        View view = getLayoutInflater().inflate(R.layout.share_plan_layout, null);
        setPlanCircleItem();
        Spinner spinner = (Spinner) view.findViewById(R.id.plancircles);
        spinner.setAdapter(planCircleAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                sharedPLanCircle = planCircles.get(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        shareDialog =
                new AlertDialog.Builder(this).setView(view)
                        .setTitle("要分享到哪个圈子")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                get("sharingPlan",
                                        "personalPlanId", sharedPersonalPlan.getId(),
                                        "planCircleId", sharedPLanCircle.getId(),
                                        "cookies", getCookie());
                                setDialogStatus(false, dialog);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setDialogStatus(true, dialog);
                                clickFlag = DEFAULT;
                            }
                        })
                        .create();
    }

    public void setPlanCircleItem() {
        for(PlanCircle planCircle: planCircles) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("plan_circle_name", planCircle.getName());
            planCircleItemList.add(map);
        }
        if(planCircleAdapter == null)
            planCircleAdapter = new SimpleAdapter(this, planCircleItemList, R.layout.share_item, new String[]{"plan_circle_name"}, new int[]{R.id.plan_circle_name});
        else
            planCircleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSuccess() {
        if(clickFlag == DEFAULT) {
            PlanList planList = getObject(PlanList.class);
            if (planList.getStatus() != 0) {
                toast(planList.getResult());
            } else {
                //toast("获取云端计划成功");
                if(listView.isRefreshing()) {
                    toast("刷新成功");
                }
                mergeCloudPlanToLocal((ArrayList<PersonalPlan>) planList.getPersonalPlans());
            }
            if(listView.isRefreshing())
                listView.onRefreshComplete();
        }
        else if(clickFlag == SHARE){
            BaseModel result = getObject(BaseModel.class);
            if(result.getStatus() == 301) {
                toast("分享成功！");
                clickFlag = 0;
                setDialogStatus(true, shareDialog);
            }
            else {
                toast(result.getResult());
                setDialogStatus(false, shareDialog);
            }
        }
        else if(clickFlag == DELETE) {
            BaseModel result = getObject(BaseModel.class);
            if(result.getStatus() == 203) {
                toast("删除成功");
                getCloudPlan();
                setNowPlan();
            }
            else {
                toast("删除失败:" + result.getResult());
            }
        }
        else if(clickFlag == GETCIRCLE) {
            editor.putString("planCircle", gson.toJson(planCircles));
            editor.commit();
            setPlanCircleItem();
        }
        clickFlag = DEFAULT;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void setNowPlan() {
        ArrayList<LocalPlan> localPlans = new ArrayList<LocalPlan>();
        setSelectedPlanId();
        for(Integer selectedId:selectedPlanId) {
            for(PersonalPlan plan:personalPlanArrayList) {
                if(plan.getId() == selectedId) {
                    String content = plan.getContent();
                    Plans plans = new Plans();
                    plans.setByJsonString(content);
                    for(Plan p:plans.getPlans()) {
                        LocalPlan localPlan = new LocalPlan(p.getStartTimeString(), p.getEndTimeString(), p.getContent(), p.getTitle(), plan.getName());
                        localPlans.add(localPlan);
                    }
                }
            }
        }
        PlanListTool.sort(localPlans);
        editor.putString("nowPlan" + getUserId(), gson.toJson(localPlans));
        editor.commit();
    }



    public void putPlans() {
        String plansString = gson.toJson(personalPlanArrayList);
        editor.putString("plansString" + getUserId(), plansString);
        editor.commit();
    }

    public void setPlans() {
        ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
        }.getType());
        mergeCloudPlanToLocal(personalPlanArrayList);
    }

    public void setPlanCircles () {
        planCircles = gson.fromJson(sharedPreferences.getString("planCircle", ""),
                new TypeToken<ArrayList<PlanCircle>>() {
                }.getType());
    }

    public void putSelectedPlanId() {
        String selectorIdsString = gson.toJson(selectedPlanId);
        editor.putString("selectedPlanId" + getUserId(),selectorIdsString);
        editor.commit();
    }

    public void setSelectedPlanId() {
        selectedPlanId = gson.fromJson(
                sharedPreferences.getString("selectedPlanId" + getUserId(), ""),
                new TypeToken<ArrayList<Integer>>() {
                }.getType());
        if(selectedPlanId == null)
            selectedPlanId = new ArrayList<Integer>();
    }

    @Override
    protected void onResume() {
        get("getPlanList", "cookies", getCookie());
        super.onResume();
    }

    @Override
    protected void onStop() {
        setNowPlan();
        super.onStop();
    }

    protected void mergeCloudPlanToLocal(ArrayList<PersonalPlan> cloud) {
        if(cloud == null)
            return;
        for (PersonalPlan plan: personalPlanArrayList) {
           if(plan.getUserId() != getUserId()) {
               cloud.add(plan);
               continue;
           }
        }
        personalPlanArrayList.clear();
        personalPlanArrayList.addAll(cloud);
        putPlans();
        allPlanAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFailure() {
        super.onFailure();
        clickFlag = DEFAULT;
        listView.onRefreshComplete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.set_plan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_plan:
                startActivity(AddPlanItemActivity.class);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        setSelectedPlanId();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                get("getPlanList", "cookies", getCookie());
            }
        }, 1000);
    }

    @Override
    public void onClickItem(View view, int position) {
        PersonalPlan plan = personalPlanArrayList.get(position);
        if(view.getId() == R.id.item) {
            if (plan.getUserId() != getUserId()) {
                toast("不可以修改他人的计划");
                return;
            }
            String planString = plan.getContent();
            Bundle data = new Bundle();
            data.putString("planString", planString);
            data.putInt("status", 1);
            data.putString("planName", plan.getName());
            data.putInt("planId", plan.getId());
            startActivity(AddPlanItemActivity.class, data);
        }
        else if(view.getId() == R.id.selector){
            int i = 0;
            setSelectedPlanId();
            int length = selectedPlanId.size();
            for(i = 0; i < length; i++){
                int selectId = selectedPlanId.get(i);
                if(selectId == plan.getId()) {
                    view.setSelected(false);
                    selectedPlanId.remove(i);
                    break;
                }
            }
            if(i == length) {
                selectedPlanId.add(plan.getId());
                view.setSelected(true);
            }
            putSelectedPlanId();
            setNowPlan();
        }
        else if(view.getId() == R.id.conversationlist_delete) {
            clickFlag = DELETE;
            if(plan.getUserId() != getUserId()) {
                personalPlanArrayList.remove(position);
                putPlans();
                allPlanAdapter.notifyDataSetChanged();
                setNowPlan();
            }
            else {
                get("deletePlan", "id", plan.getId());
            }
        }
        else if(view.getId() == R.id.conversationlist_share) {
            clickFlag = SHARE;
            planCircleAdapter.notifyDataSetChanged();
            sharedPersonalPlan = plan;
            shareDialog.show();
        }
    }

    public void setDialogStatus(Boolean opened, DialogInterface dialog) {
        try
        {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField(
                            "mShowing" );
            field.setAccessible( true );
            // 将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, opened);
            dialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
