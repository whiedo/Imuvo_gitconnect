package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sco.imuvo.Model.AskingSingleton;
import com.example.sco.imuvo.Model.Vocab;
import com.example.sco.imuvo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VocabularyResult extends BaseActivity {

    TextView bubbleTextView, rightTextView, skippedTextView, wrongTextView, durationTextView;
    ArrayList<Vocab> rightVocabList, skippedVocabList, wrongVocabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_result, frameLayout);
        getElements();
        getValuesFromSingleton();
        setValuesToTextView();
    }

    private void setValuesToTextView() {
        rightTextView.setText("Richtig: " + Integer.toString(rightVocabList.size()));
        //skippedTextView.setText("Übersprungen: " + Integer.toString(skippedVocabList.size()));
        wrongTextView.setText("Falsch: " + Integer.toString(wrongVocabList.size()));
        AskingSingleton.endingDate = (Date) Calendar.getInstance().getTime();

        long duration = ((long) (AskingSingleton.endingDate.getTime() - AskingSingleton.startingDate.getTime()));
        durationTextView.setText("Dauer: " + Long.toString(duration / 1000 / 60) + " min");
    }

    private void getValuesFromSingleton() {
        rightVocabList = AskingSingleton.rightVocabs;
        wrongVocabList = AskingSingleton.wrongVocabs;
        skippedVocabList = AskingSingleton.skippedVocabs;


    }

    private void setSpeechbubble() {
        //speechbubble.setText("Sehr gut! Du möchtest Vokabeln üben. Bitte entscheide dich zwischen einer Abfrage oder einem Vokabeltest");
    }


    private void getElements() {
        rightTextView = (TextView) findViewById(R.id.rightVocabs);
        wrongTextView = (TextView) findViewById(R.id.wrongVocabs);
        durationTextView = (TextView) findViewById(R.id.duration);
    }

    public void onClickStartTest(View v){
    }

    public void onClickStartAsking(View v){
        final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
        Bundle bundle = new Bundle();
        bundle.putString("type","test");
        menuIntent.putExtras(bundle);
        startActivity(menuIntent);
    }

    public void onClickButtonMoreAsking(View v){
        final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
        Bundle bundle = new Bundle();
        bundle.putString(VocabularyLectionSelection.TYPE,VocabularyLectionSelection.TEST);
        menuIntent.putExtras(bundle);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }

    public void onClickButtonOverview(View v){
        final Intent menuIntent = new Intent(this,Menu.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }

    public void onClickShowWrongVocabs(View v){
        final Intent intent = new Intent(this, VocabularyQuery.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(VocabularyQuery.ASKWRONGVOCABSAGAIN,true);
        bundle.putBoolean(VocabularyLectionSelection.RANDOM,true);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
