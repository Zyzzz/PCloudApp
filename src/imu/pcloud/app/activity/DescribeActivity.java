package imu.pcloud.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import imu.pcloud.app.R;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.been.PlanCircle;
import imu.pcloud.app.model.BaseModel;
import imu.pcloud.app.model.PlanCircleList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by guyu on 2016/7/6.
 */
public class DescribeActivity extends HttpActivity{

    private EditText describe;
    private AlertDialog shareDialog;

    SimpleAdapter planCircleAdapter;

    int clickFlag = 0;
    int GETCIRCLE = 0;
    int SHARED = 1;

    private PersonalPlan sharedPersonalPlan;
    private PlanCircle sharedPLanCircle;
    private ArrayList<HashMap<String, Object>> planCircleItemList = new ArrayList<HashMap<String, Object>>();
    private List<PlanCircle> planCircles = new ArrayList<PlanCircle>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        try {
            String sharedPlanString;
            sharedPlanString = data.getString("sharedPersonalPlan", null);
            sharedPersonalPlan = gson.fromJson(sharedPlanString, PersonalPlan.class);
        } catch (Exception e) {
            finish();
        }
        init();
    }

    private void init() {
        setContentView(R.layout.describe_acitivity);
        setActionBar("分享:" + sharedPersonalPlan.getName());
        getPlanCircle();
        setPlanCircles();
        describe = find(R.id.describe);
        initDialog();
    }

    public void getPlanCircle() {
        clickFlag = GETCIRCLE;
        get("getPlanCircleList");
    }

    private void initDialog() {
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
                                        "describe", describe.getText(),
                                        "cookies", getCookie());
                                clickFlag = SHARED;
                                setDialogStatus(false, dialog);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setDialogStatus(true, dialog);
                            }
                        })
                        .create();
    }

    @Override
    protected void onSuccess() {
        if(clickFlag == SHARED) {
            BaseModel result = getObject(BaseModel.class);
            if (result.getStatus() == 301) {
                toast("分享成功！");
                setDialogStatus(true, shareDialog);
                finish();
            } else {
                toast(result.getResult());
                setDialogStatus(false, shareDialog);
            }
        }
        else if(clickFlag == GETCIRCLE) {
            PlanCircleList result = getObject(PlanCircleList.class);
            planCircles.clear();
            planCircles = result.getPlanCircles();
            editor.putString("planCircle", gson.toJson(planCircles));
            editor.commit();
            setPlanCircleItem();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_plan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.confirm:
                if(describe.length() < 20) {
                    toast("请至少输入10个字以上的描述");
                    break;
                }
                getPlanCircle();
                shareDialog.show();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
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

    public void setPlanCircleItem() {
        setPlanCircles();
        planCircleItemList.clear();
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
}
