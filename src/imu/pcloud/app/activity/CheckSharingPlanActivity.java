package imu.pcloud.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import imu.pcloud.app.R;
import imu.pcloud.app.been.Comment;
import imu.pcloud.app.been.PersonalPlan;
import imu.pcloud.app.model.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CheckSharingPlanActivity extends HttpActivity {
    private  int planId;
    private  int loadingTime;
    private  int mode;
    private  boolean falg;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private List<Map<String,Object>> cList =new ArrayList<Map<String, Object>>();
    private List<Comment> comments = new ArrayList<Comment>();
    private int planCircleID;
    private Plans plans =  new Plans();
    private ArrayList<Plan> planList = new ArrayList<Plan>();
    private ListView planItemListView;
    private ListView planComment;
    private TextView loadingTextView;
    private String planName;
    private String planContext;
    private AlertDialog CommentDialog;
    private PersonalPlan plan;
    private EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sharing_plan);
        init();
        setActionBar(""+planName);
        getPlist();
        SimpleAdapter listAdapter = new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content", "title"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
        planItemListView.setAdapter(listAdapter);
        get("getCommentList","personalPlanId",planId);
    }

    private void getPlist(){
        plans.setByJsonString(planContext);
        planList = plans.getPlans();
        for (int i = 0; i < planList.size(); i++)
        {
            Plan plan = planList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("start_time", plan.getStartTimeString());
            map.put("end_time", plan.getEndTimeString());
            map.put("content", plan.getContent());
            map.put("title", plan.getTitle() + ":(来自:" + planName + ")");
            pList.add(map);
        }
    }

    private  void init (){
        planItemListView = find(R.id.plan_informationf);
        planComment = find(R.id.comment_listview);
        loadingTextView = find(R.id.downloan_Times);
        falg = true;
        mode = 1;
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        planCircleID = bundle.getInt("PlanCircleId");
        planId  = bundle.getInt("PlanId");
        loadingTime =bundle.getInt("PlandownLoan");
        loadingTextView.setText(loadingTime+"次");
        planName = bundle.getString("planName");
        planContext = bundle.getString("planContext");
        plan = gson.fromJson(bundle.getString("plan", ""), PersonalPlan.class);
    }

    @Override
    protected void onSuccess() {
        if(mode == 1) {
            CommentList commentList = getObject(CommentList.class);
            if (commentList.getStatus() == 0) {
                comments = commentList.getComments();
                getDate();
                SimpleAdapter listAdapter = new SimpleAdapter(this, cList, R.layout.comment_item,
                        new String[]{"userName", "context"}, new int[]{R.id.uer_name, R.id.comment_context});
                planComment.setAdapter(listAdapter);
            } else {
                toast(commentList.getResult());
            }
        } else if(mode == 2){
                BaseModel baseModel = getObject(BaseModel.class);
                if (baseModel.getStatus() == 0) {
                    downloadPlan(plan);
                    toast("加载计划成功");
                    loadingTime++;
                    loadingTextView.setText(loadingTime + "次");
                } else {
                    toast(baseModel.getResult());
                }
                falg = false;
                mode = 1;
            }else {
            mode = 1;
            BaseModel baseModel = getObject(BaseModel.class);
            if (baseModel.getStatus() == 0) {
                toast("评论计划成功");
                setDialogStatus(true, CommentDialog);
                get("getCommentList","personalPlanId",planId);
            } else {
                toast(baseModel.getResult());
                setDialogStatus(false, CommentDialog);
            }

        }
    }

    private boolean downloadPlan(PersonalPlan plan) {
        ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                sharedPreferences.getString("plansString" + getUserId(), ""),
                new TypeToken<ArrayList<PersonalPlan>>() {
                }.getType());
        personalPlanArrayList.add(plan);
        String plansString = gson.toJson(personalPlanArrayList);
        editor.putString("plansString" + getUserId(), plansString);
        editor.commit();
        return true;
    }

    private void getDate(){
        cList.clear();
        for(Comment comment:comments){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("userName", comment.getUser().getUsername());
            map.put("context",comment.getContent());
            cList.add(map);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.comment_downloan, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.downloan_plan:
                if(falg) {
                    ArrayList<PersonalPlan> personalPlanArrayList = gson.fromJson(
                            sharedPreferences.getString("plansString" + getUserId(), ""),
                            new TypeToken<ArrayList<PersonalPlan>>() {
                            }.getType());
                    for(PersonalPlan var:personalPlanArrayList) {
                        if(var.getId() == plan.getId()) {
                            toast("你已经加载过了");
                            return false;
                        }
                    }
                    mode = 2;
                    get("increaseLoadingTime", "personalPlanId", planId, "planCircleId", planCircleID);
                }else {
                    toast("你已经加载过了");
                }
                break;
            case R.id.comment_plan:
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.text_dialog, null);
                CommentDialog = new AlertDialog.Builder(CheckSharingPlanActivity.this).
                        setTitle("请输入评论内容").
                        setView(layout).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mode = 3;
                        comment = (EditText)layout.findViewById(R.id.text);
                        get("addComment","cookies",getCookie(),"personalPlanId",planId,"content",comment.getText().toString());

                        setDialogStatus(false, dialog);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setDialogStatus(true, dialog);
                    }
                }).create();
                CommentDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
