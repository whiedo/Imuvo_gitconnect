package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.R;

public class VocabularyTestSelection extends BaseActivity {
    TextView speechbubble, headlineTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_test_selection, frameLayout);
        getElements();
        setSpeechbubble();
    }

    private void setSpeechbubble() {
        speechbubble.setText(R.string.selectTestType);
        headlineTextView.setText(FormatHelper.colorsString(this,getString(R.string.askVocabs), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
    }

    private void getElements() {
        speechbubble = (TextView) findViewById(R.id.speechbubble);
        headlineTextView = (TextView) findViewById(R.id.headline);
    }

     public void onClickStartTest(View v){
         final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
         Bundle bundle = new Bundle();
         bundle.putString("type", VocabularyLectionSelection.TEST);
         menuIntent.putExtras(bundle);
         startActivity(menuIntent);
    }

    public void onClickStartAsking(View v){
        final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
        Bundle bundle = new Bundle();
        bundle.putString(VocabularyLectionSelection.TYPE, VocabularyLectionSelection.ASKING);
        menuIntent.putExtras(bundle);
        startActivity(menuIntent);
    }
}
