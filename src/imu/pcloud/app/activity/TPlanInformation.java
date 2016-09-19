package imu.pcloud.app.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import imu.pcloud.app.R;

/**
 * Created by acer on 2016/6/1.
 */
public class TPlanInformation extends HttpActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tplancontent);
    }

    @Override
    protected void onSuccess() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.team_leader, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.lookmember:
                startActivity(TeamMemberActivity.class);
                break;
            case R.id.updateplan:
                initDialog();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void initDialog() {

    }


}
