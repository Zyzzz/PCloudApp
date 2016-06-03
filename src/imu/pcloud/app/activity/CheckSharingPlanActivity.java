package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import imu.pcloud.app.R;
import imu.pcloud.app.been.Comment;
import imu.pcloud.app.model.CommentList;
import imu.pcloud.app.model.LocalPlan;
import imu.pcloud.app.model.Plan;
import imu.pcloud.app.model.Plans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CheckSharingPlanActivity extends HttpActivity{
    int planId;
    int LoadingTime;
    private List<Map<String,Object>> pList =new ArrayList<Map<String, Object>>();
    private List<Map<String,Object>> cList =new ArrayList<Map<String, Object>>();
    private List<Comment> comments = new ArrayList<Comment>();
    Plans plans =  new Plans();
    ArrayList<Plan> planList = new ArrayList<Plan>();
    ListView planItemListView;
    ListView planComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_sharing_plan);
        planItemListView = find(R.id.plan_informationf);
        planComment = find(R.id.comment_listview);
        Intent intent_accept = getIntent();
        Bundle bundle = intent_accept.getExtras();
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        String planName = bundle.getString("planName");
        String planContext = bundle.getString("planContext");
        planId  = bundle.getInt("PlanId");
        setActionBar(""+planName);
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
        SimpleAdapter listAdapter = new SimpleAdapter(this, pList, R.layout.personal_list_item,
                new String[]{"start_time","end_time", "content", "title"}, new int[]{R.id.start_time, R.id.end_time, R.id.plan_content, R.id.plan_title});
        planItemListView.setAdapter(listAdapter);
        get("getCommentList","personalPlanId",planId);
    }
    @Override
    protected void onSuccess() {
        CommentList commentList = getObject(CommentList.class);
        if(commentList.getStatus()==0){
            comments = commentList.getComments();
            getDate();
            SimpleAdapter listAdapter = new SimpleAdapter(this, cList, R.layout.comment_item,
                    new String[]{"userName","context"}, new int[]{R.id.uer_name, R.id.comment_context});
            planComment.setAdapter(listAdapter);
        }else {
            toast(commentList.getResult());
        }
    }

    private void getDate(){
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
                break;
            case R.id.comment_plan:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
