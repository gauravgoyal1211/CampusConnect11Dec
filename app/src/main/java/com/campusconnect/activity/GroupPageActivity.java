package com.campusconnect.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.campusconnect.R;
import com.campusconnect.adapter.GroupPageAdapterActivity;
import com.campusconnect.adapter.GroupPage_infoActivity;
import com.campusconnect.utility.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GroupPageActivity extends ActionBarActivity {

    RecyclerView group_page;
    Typeface r_med;
    TextView group_page_title;

    private static final String LOG_TAG="GroupPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        r_med = Typeface.createFromAsset(getAssets(), "font/Roboto_Medium.ttf");

        group_page = (RecyclerView) findViewById(R.id.recycler_group_page);
        group_page_title = (TextView) findViewById(R.id.tv_title);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        group_page.setLayoutManager(llm);
        group_page.setHasFixedSize(true);
        group_page.setItemAnimator(new DefaultItemAnimator());
        group_page.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        GroupPageAdapterActivity gp = new GroupPageAdapterActivity(
                createList_group_page(6));

        group_page.setAdapter(gp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private List<GroupPage_infoActivity> createList_group_page(int size) {

        List<GroupPage_infoActivity> result = new ArrayList<GroupPage_infoActivity>();
        for (int i = 1; i <= size; i++) {
            GroupPage_infoActivity ci = new GroupPage_infoActivity();

            result.add(ci);

        }

        return result;
    }

}
