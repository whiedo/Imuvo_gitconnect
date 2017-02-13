package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sco.imuvo.CustomViews.CustomSpinnerMultiSelection;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.Model.AskingSingleton;
import com.example.sco.imuvo.R;

import java.util.List;

public class VocabularyLectionSelection extends AppCompatActivity {
    public static final String ASKING = "asking";
    public static final String TEST = "test";
    public static final String READING = "read";
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
    List<Integer> selectedLections = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_lection_selection);

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
        if(nextIntentType.contentEquals(READING)){
            speechbubble.setText(R.string.readVocabSelection);
            headline.setText(FormatHelper.colorsString(this,getString(R.string.readVocab), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
        }
        else if(nextIntentType.contentEquals(ASKING)){
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
        nextIntentType = bundle.getString("type");
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

        String[] from = {LectionDatabaseHelper.COLUMNS[LectionDatabaseHelper.NUMBER_COLUMN_INDEX]};
        int[] to = {R.id.lectionSpinner};
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, R.layout.support_simple_spinner_dropdown_item, cursor, from, to, 0);
        lectionSpinner.setAdapter(cursorAdapter);

        List<String> lables = LectionDatabaseHelper.getAllLabels();
        for(int i = 0; i < lables.size(); i++) {
            lables.set(i, "Lektion " + lables.get(i));
        }

        //Test-->
//        String query = "SELECT * FROM " + LectionDatabaseHelper.TABLE_NAME;
//        Cursor cursor2 = GeneralDatabaseHelper.getSQLDatabase().rawQuery(query, null);
//
//        LectionCursorAdapter adapter2 = new LectionCursorAdapter(
//                this, R.layout.embedded_customspinner, cursor2, 0 );
//        //adapter2.setDropDownViewResource(R.layout.embedded_customspinner);
//        lectionSpinner.setAdapter(adapter2);

//        Spinner dropdown = lectionSpinner;
//        List<String> items = LectionDatabaseHelper.getAllLabels();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        dropdown.setAdapter(adapter);
        //Test<--

        //TODO DELETE NEXT BLOCK AND FIX EMPTY CONTENT
        //MULTIPLE SELECTION
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
        if(checkEverythingSelected() == true){
            final Intent nextIntent;
            if(nextIntentType.contentEquals(READING)){
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

    private boolean checkEverythingSelected() {
        return true;
    }

    public void onClickBurgerMenu(View v){
        final Intent menuIntent = new Intent(this,Menu.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }
}
