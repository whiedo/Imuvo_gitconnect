package com.example.sco.imuvo.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.VocabCursorAdapter;
import com.example.sco.imuvo.R;

public class VocabularyList extends BaseActivity {
    public static final String HIDE_DRAWER_LAYOUT = "HideDrawerLayout";
    private boolean hideDrawerLayout;
    int lectionNo = 0;
    ListView vocabListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_list, frameLayout);
        getInitialValuesFromIntent();
        if(hideDrawerLayout){
            disableDrawerLayout();
        }
        popoulateListView();


    }

    private void getInitialValuesFromIntent(){
        Bundle bundle = getIntent().getExtras();
        hideDrawerLayout = bundle.getBoolean(HIDE_DRAWER_LAYOUT);
    }

    private void disableDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void popoulateListView() {
        lectionNo = getLectionFromIntent();
        vocabListView = (ListView) findViewById(R.id.list);

        Cursor cursor = VocabDatabaseHelper.getAll(lectionNo);
        VocabCursorAdapter vocabCursorAdapter = new VocabCursorAdapter(this,cursor,0);
        vocabListView.setAdapter(vocabCursorAdapter);
    }

    private int getLectionFromIntent() {
        Bundle bundle = getIntent().getExtras();
        return(bundle.getInt("lectionNumber"));
    }
}
