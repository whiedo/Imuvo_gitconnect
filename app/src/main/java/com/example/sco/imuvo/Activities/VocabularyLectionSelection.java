package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sco.imuvo.CustomViews.CustomSpinnerMultiSelection;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.Model.AskingSingleton;
import com.example.sco.imuvo.R;

import java.util.List;

public class VocabularyLectionSelection extends BaseActivity {
    public static final String ASK = "asking";
    public static final String TEST = "test";
    public static final String READ = "read";
    public static final String READALOUD = "readAloud";


    public static final String MULTIPLE_SELECTION = "isMultipleLection";
    public static final String TYPE = "type";
    public static final String SELECTED_LECTION = "selectedLection";
    public static final String SELECTED_DIRECTION = "selectedDirection";
    public static final String RANDOM = "isRandom";

    Button startButton;
    Spinner lectionSpinner, directionSpinner;
    CustomSpinnerMultiSelection multipleLectionSpinner;
    TextView speechbubble, headline;
    String nextIntentType;
    CheckBox randomCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_lection_selection, frameLayout);

        getElements();
        getInitialValuesFromIntent();
        loadDirectionSpinnerData();
        loadLectionSpinnerData();
        setSpeechbubble();
        setVisibility();
    }

    private void setVisibility() {
        if (nextIntentType.contentEquals(TEST)){
            multipleLectionSpinner.setVisibility(View.VISIBLE);
            lectionSpinner.setVisibility(View.GONE);
        }
        else{
            multipleLectionSpinner.setVisibility(View.GONE);
            lectionSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void setSpeechbubble() {
        if(nextIntentType.contentEquals(READ)){
            speechbubble.setText(R.string.readVocabSelection);
            headline.setText(FormatHelper.colorsString(this,getString(R.string.readVocab), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
        }
        else if(nextIntentType.contentEquals(ASK)){
            speechbubble.setText(R.string.queryVocabSelection);
            headline.setText(FormatHelper.colorsString(this,getString(R.string.askVocabs), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
        }
        else if(nextIntentType.contentEquals(TEST)){
            speechbubble.setText(R.string.testVocabSelection);
            headline.setText(FormatHelper.colorsString(this,getString(R.string.testVocabs), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
        }
        else if(nextIntentType.contentEquals(READALOUD)){
            speechbubble.setText(R.string.readAloudSelection);
            headline.setText(FormatHelper.colorsString(this,getString(R.string.readAloudVocabs), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
        }
    }

    private void getInitialValuesFromIntent(){
        Bundle bundle = getIntent().getExtras();
        nextIntentType = bundle.getString(TYPE);
    }

    private void getElements() {
        lectionSpinner = (Spinner) findViewById(R.id.lectionSpinner);
        directionSpinner = (Spinner) findViewById(R.id.directionSpinner);
        startButton = (Button) findViewById(R.id.start);
        speechbubble = (TextView) findViewById(R.id.speechbubble);
        headline = (TextView) findViewById(R.id.headline);
        randomCheckBox = (CheckBox) findViewById(R.id.random);
        multipleLectionSpinner = (CustomSpinnerMultiSelection) findViewById(R.id.lectionSpinnerMultiple);
    }

    private void loadLectionSpinnerData() {
        Cursor cursor = LectionDatabaseHelper.getAll();

        String[] from = {"number"};
        int[] to = {R.id.lectionSpinner};
        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(this,R.layout.support_simple_spinner_dropdown_item,cursor,from,to,0);
        lectionSpinner.setAdapter(cursorAdapter);

        List<String> lables = LectionDatabaseHelper.getAllLabels();
        for(int i = 0; i < lables.size(); i++) {
            lables.set(i, getString(R.string.lection)+ " " + lables.get(i));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.embedded_customspinner, lables);
        dataAdapter.setDropDownViewResource(R.layout.embedded_customspinner);
        lectionSpinner.setAdapter(dataAdapter);

        multipleLectionSpinner.setListener(new CustomSpinnerMultiSelection.OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {
                AskingSingleton.selectedLections = indices;
            }

            @Override
            public void selectedStrings(List<String> strings) {
            }
        });

        multipleLectionSpinner.setItems(lables);
        AskingSingleton.selectedLections = multipleLectionSpinner.getSelectedIndices();
    }

    private void loadDirectionSpinnerData() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.direction_array,
                R.layout.embedded_customspinner);
        adapter.setDropDownViewResource(R.layout.embedded_customspinner);
        directionSpinner.setAdapter(adapter);

    }
    public void onClickStartReading(View v){
        final Intent nextIntent;
        if(nextIntentType.contentEquals(READ)){
            nextIntent = new Intent(this,VocabularyRead.class);
        }
        else if(nextIntentType.contentEquals(READALOUD)){
            nextIntent = new Intent(this,VocabularyRead.class);
        }
        else{
            nextIntent = new Intent(this,VocabularyQuery.class);
        }

        Bundle bundle = new Bundle();
        if(nextIntentType.contentEquals(TEST)){
            bundle.putBoolean(MULTIPLE_SELECTION,true);
        }

        bundle.putString(TYPE,nextIntentType);
        bundle.putLong(SELECTED_LECTION,lectionSpinner.getSelectedItemId());
        bundle.putLong(SELECTED_DIRECTION,directionSpinner.getSelectedItemId());
        bundle.putBoolean(RANDOM,randomCheckBox.isChecked());
        nextIntent.putExtras(bundle);
        startActivity(nextIntent);
    }
}
