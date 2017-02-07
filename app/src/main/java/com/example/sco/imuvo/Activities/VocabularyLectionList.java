package com.example.sco.imuvo.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.sco.imuvo.HelperClasses.LectionCursorAdapter;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.R;

public class VocabularyLectionList extends AppCompatActivity {

    private ListView lectionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_lection_list);
        popoulateListView();
    }

    private void popoulateListView() {
        lectionListView = (ListView) findViewById(R.id.listView);
        Cursor cursor = LectionDatabaseHelper.getAll();
        LectionCursorAdapter lectionCursorAdapter = new LectionCursorAdapter(this,cursor,0);
        lectionListView.setAdapter(lectionCursorAdapter);
    }

}
