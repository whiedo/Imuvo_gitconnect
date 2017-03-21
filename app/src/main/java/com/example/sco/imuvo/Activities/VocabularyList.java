package com.example.sco.imuvo.Activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.VocabCursorAdapter;
import com.example.sco.imuvo.R;

public class VocabularyList extends BaseActivity {
    int lectionNo = 0;
    ListView vocabListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_list, frameLayout);
        popoulateListView();
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
