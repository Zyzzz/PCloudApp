package imu.pcloud.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.Comment;
import imu.pcloud.app.been.DiscoverId;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.*;
import imu.pcloud.app.utils.DateTool;
import imu.pcloud.app.utils.ImageUtil;
import imu.pcloud.app.view.NoScrollListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guyu on 2016/7/1.
 */
public class DiscoverPlanAcitivity extends HttpActivity implements ImageUtil.OnLoadListener, View.OnClickListener{

    //view
    private ImageView header;
    private TextView name;
    private TextView sign;
    private TextView loadtime;
    private TextView describe;
    private TextView sharingTime;
    private EditText comment;
    //operator
    private Button show;
    private View load;
    private View info;
    //list
    private NoScrollListView planList;
    private NoScrollListView commentList;
    //dialog
    private AlertDialog CommentDialog;
    //data
    private ArrayList<Comment> commentArrayList;
    private Plans plans;
    private DiscoverId discover;
    private ArrayList<Map<String, Object>> pList = new ArrayList<>();
    private ArrayList<Map<String, Object>> cList = new ArrayList<>();
    private SimpleAdapter planAdapter;
    private SimpleAdapter commentAdapter;
    //flag
    private int mode;
    final private int COMMENT = 1;
    final private int GET_COMMENT = 0;
    final private int LOAD_PLAN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        String discoverString = data.getString("discover", null);
        try {
            discover = gson.fromJson(discoverString, DiscoverId.class);
        } catch (Exception e) {
            e.printStackTrace();
            discover = new DiscoverId();
        }
        init();
    }

    private void init() {
        //find view
        setContentView(R.layout.discover_plan_activity);
        setActionBar(discover.getPlanName());
        header = find(R.id.header);
        name = find(R.id.name);
        sign = find(R.id.sign);
        loadtime = find(R.id.loadtime);
        describe = find(R.id.describe);
        sharingTime = find(R.id.sharing_time);
        show = find(R.id.show);
        load = findViewById(R.id.load);
        info = findViewById(R.id.info);
        planList = (NoScrollListView) findViewById(R.id.plan_list);
        commentList = (NoScrollListView) findViewById(R.id.comment_list);
        initDialog();
        //init date
        plans = new Plans();
        plans.setByJsonString(discover.getContent());
        getComment();
        imageUtil.setOnLoadListener(this);
        //init view
        header.setImageDrawable(imageUtil.getHeader(discover.getUserId(), discover.getHeadImageId()));
        name.setText(discover.getUsername());
        sign.setText(discover.getSignature());
        describe.setText(discover.getDiscribe());
        initLoadtime();
        sharingTime.setText("分享于 " + DateTool.dateToString(discover.getSharingTime()));
        setPlanList();
        show.setOnClickListener(this);
        if(!isMyLoad())
            load.setOnClickListener(this);
        info.setOnClickListener(this);

    }

    public void initLoadtime() {
        loadtime.setText("▼ " + discover.getLoadingTime());
        if(hasLoad() && !isMyLoad())
            loadtime.setTextColor(Color.rgb(0x4f, 0xa2, 0xe3));
        else
            loadtime.setTextColor(Color.rgb(0x5d, 0x5d, 0x5d));
    }

    public boolean isMyLoad() {
        return discover.getUserId() == getUserId();
    }

    public void initDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.text_dialog, null);
        CommentDialog = new AlertDialog.Builder(this).
                setTitle("请输入评论内容").
                setView(layout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mode = COMMENT;
                        comment = (EditText)layout.findViewById(R.id.text);
                        get("addComment","cookies",getCookie(),"personalPlanId", discover.getPersonalPlanId(),"content",comment.getText().toString());

                        setDialogStatus(false, dialog);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setDialogStatus(true, dialog);
            }
        }).create();
    }

    private void getComment() {
        mode = GET_COMMENT;
        get("getCommentList","personalPlanId", discover.getPersonalPlanId());
    }

    private void refreshComment(CommentList commentList) {
        if(commentAdapter == null) {
            commentAdapter = new SimpleAdapter(this, cList, R.layout.comment_item,
                    new String[]{"userName", "context"}, new int[]{R.id.uer_name, R.id.comment_context});
            this.commentList.setAdapter(commentAdapter);
        }
        commentArrayList = (ArrayList<Comment>) commentList.getComments();
        cList.clear();
        for(Comment comment: commentArrayList){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("userName", comment.getUser().getUsername());
            map.put("context",comment.getContent());
            cList.add(map);
        }
        commentAdapter.notifyDataSetChanged();
    }

    private void setPlanList() {
        if(planAdapter == null) {
            planAdapter = new SimpleAdapter(this, pList, R.layout.personal_list_item,
                    new String[]{"start_time","end_time", "content", "title"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
            planList.setAdapter(planAdapter);
        }
        pList.clear();
        ArrayList<Plan> planList = plans.getPlans();
        for (int i = 0; i < planList.size(); i++)
        {
            Plan plan = planList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            map.put("title", plan.getTitle());
            pList.add(map);
        }
        planAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSuccess() {
        BaseModel baseModel;
        switch (mode) {
            case GET_COMMENT:
                CommentList commentList = getObject(CommentList.class);
                if(commentList.getStatus() == 0)
                    refreshComment(commentList);
                break;
            case COMMENT:
                baseModel = getObject(BaseModel.class);
                if (baseModel.getStatus() == 0) {
                    toast("评论计划成功");
                    setDialogStatus(true, CommentDialog);
                    getComment();
                } else {
                    toast(baseModel.getResult());
                    setDialogStatus(false, CommentDialog);
                }
                break;
            case LOAD_PLAN:
                baseModel = getObject(BaseModel.class);
                if (baseModel.getStatus() == 0) {
                    downloadPlan();
                    toast("加载计划成功");
                    discover.setLoadingTime(discover.getLoadingTime() + 1);
                    initLoadtime();
                } else {
                    toast(baseModel.getResult());
                }
        }
    }

    private boolean downloadPlan() {
        PersonalPlan plan = new PersonalPlan();
        plan.setContent(discover.getContent());
        plan.setName(discover.getPlanName());
        plan.setId(discover.getPersonalPlanId());
        plan.setUserId(discover.getUserId());
        ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
                }.getType());
        if(personalPlanArrayList == null)
            personalPlanArrayList = new ArrayList<PersonalPlan>();
        personalPlanArrayList.add(plan);
        String plansString = gson.toJson(personalPlanArrayList);
        editor.putString("plansString" + getUserId(), plansString);
        editor.commit();
        return true;
    }

    @Override
    public void onLoad(ImageModel imageModel) {
        header.setImageDrawable(imageUtil.getHeader(discover.getUserId(), discover.getHeadImageId()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                int listStatus = planList.getVisibility();
                if(listStatus == View.GONE) {
                    show.setText("↑ 点击隐藏计划内容");
                    planList.setVisibility(View.VISIBLE);
                }
                else {
                    show.setText("↓ 点击查看计划内容");
                    planList.setVisibility(View.GONE);
                }
                break;
            case R.id.info:
                break;
            case R.id.load:
                mode = LOAD_PLAN;
                if(hasLoad()) {
                    toast("你已经加载过了");
                    break;
                }
                get("increaseLoadingTime", "personalPlanId", discover.getPersonalPlanId(), "planCircleId", discover.getPlanCircleId());
        }
    }

    private boolean hasLoad() {
        ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
                }.getType());
        if(personalPlanArrayList != null) {
            for (PersonalPlan var : personalPlanArrayList) {
                if (var.getId() == discover.getPersonalPlanId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.comment_downloan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.comment_plan:
                mode = COMMENT;
                CommentDialog.show();
            case R.id.download_plan:
                load.callOnClick();
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
}
