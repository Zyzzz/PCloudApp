package imu.pcloud.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import imu.pcloud.app.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class CheckSharingPlanActivity extends HttpActivity{

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
        int planId = bundle.getInt("PlanId");
        setActionBar(""+planName);
        get("","");
    }

    @Override
    protected void onSuccess() {

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
