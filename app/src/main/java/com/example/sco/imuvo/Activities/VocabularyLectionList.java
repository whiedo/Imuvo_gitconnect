package com.example.sco.imuvo.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.LectionCursorAdapter;
import com.example.sco.imuvo.R;

public class VocabularyLectionList extends BaseActivity {

    private ListView lectionListView;
    private boolean hideDrawerLayout;
    public static final java.lang.String HIDE_DRAWER_LAYOUT = "HideDrawerLayout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_lection_list, frameLayout);
        getInitialValuesFromIntent();
        if(hideDrawerLayout){
            disableDrawerLayout();
        }
        populateListView();
    }

    private void getInitialValuesFromIntent(){
        Bundle bundle = getIntent().getExtras();
        try{
            hideDrawerLayout = bundle.getBoolean(HIDE_DRAWER_LAYOUT);
        } catch(NullPointerException e){
            hideDrawerLayout = false;
        }

    }

    private void disableDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void populateListView() {
        lectionListView = (ListView) findViewById(R.id.listView);
        Cursor cursor = LectionDatabaseHelper.getAll();
        LectionCursorAdapter lectionCursorAdapter = new LectionCursorAdapter(this, cursor, 0, hideDrawerLayout);
        lectionListView.setAdapter(lectionCursorAdapter);
    }

}
