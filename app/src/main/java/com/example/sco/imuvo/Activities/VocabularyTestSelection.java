package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.R;

public class VocabularyTestSelection extends AppCompatActivity {
    TextView speechbubble, headlineTextView;
    String nextIntentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_test_selection);
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
        bundle.putString("type", VocabularyLectionSelection.ASKING);
        menuIntent.putExtras(bundle);
        startActivity(menuIntent);
    }

    public void onClickBurgerMenu(View v){
        final Intent menuIntent = new Intent(this,Menu.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }
}
