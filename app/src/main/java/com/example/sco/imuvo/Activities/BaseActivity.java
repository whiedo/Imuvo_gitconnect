
package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sco.imuvo.R;

public class BaseActivity extends AppCompatActivity
{
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_basic);
        super.onCreate(savedInstanceState);
        String[] items = getResources().getStringArray(R.array.main_menu_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.app_name, R.string.check)
        {
            public void onDrawerClosed(View view)
            {
                getSupportActionBar().setTitle(R.string.app_name);
            }

            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.embedded_customspinner, items));

        drawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                Intent intent = new Intent(getApplicationContext(), com.example.sco.imuvo.Activities.Menu.class);
                Bundle bundle;
                switch ((pos)){
                    case 0:
                        intent = new Intent(getApplicationContext(),Dictionary.class);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(),VocabularyLectionSelection.class);
                        bundle = new Bundle();
                        bundle.putString(VocabularyLectionSelection.TYPE, VocabularyLectionSelection.READ);
                        intent.putExtras(bundle);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(),VocabularyLectionList.class);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(),VocabularyLectionSelection.class);
                        bundle = new Bundle();
                        bundle.putString(VocabularyLectionSelection.TYPE, VocabularyLectionSelection.READALOUD);
                        intent.putExtras(bundle);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(),VocabularyTestSelection.class);
                        break;
                    case 5:
                        intent = new Intent(getApplicationContext(), com.example.sco.imuvo.Activities.Menu.class);
                        break;

                }
                startActivity(intent);
                drawerList.setItemChecked(pos, true);
                drawerLayout.closeDrawer(drawerList);
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