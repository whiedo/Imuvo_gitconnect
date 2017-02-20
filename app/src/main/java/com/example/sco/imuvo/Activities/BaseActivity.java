
package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sco.imuvo.R;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.
 */
public class BaseActivity extends AppCompatActivity
{
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    protected void onCreate(Bundle savedInstanceState)
    {
        String[] items = new String[]{"Spielen","test2","test3","test4","test5","test6"};
        // R.id.drawer_layout should be in every activity with exactly the same id.
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle((AppCompatActivity) this, drawerLayout, R.drawable.animal, 0, 0)
        {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle(R.string.app_name);
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(R.string.app_name);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                items)); //Items is an ArrayList or array with the items you want to put in the Navigation Drawer.

        drawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                // Do what you want when an item from the Navigation Drawer is clicked
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}