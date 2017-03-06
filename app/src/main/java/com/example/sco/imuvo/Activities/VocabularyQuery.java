package com.example.sco.imuvo.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.DatabaseHelper.VocabDatabaseHelper;
import com.example.sco.imuvo.Model.AskingSingleton;
import com.example.sco.imuvo.Model.Lection;
import com.example.sco.imuvo.Model.Vocab;
import com.example.sco.imuvo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class VocabularyQuery extends BaseActivity {

    public static final String ASKWRONGVOCABSAGAIN = "askWrongVocabsAgain" ;
    VocabDatabaseHelper vocabDatabaseHelper;
    LectionDatabaseHelper lectionDatabaseHelper;
    ArrayList<Vocab> vocabList;
    Lection currentLection;
    ListIterator<?> vocabIterator;
    Vocab currVocab;
    Button nextButton, skipButton;

    TextView bubbleTextView, questionTextView, headlineText, subHeadlineText;
    EditText answerEditText;
    ImageView vocabPictureImageView;
    private long currentDirection;
    private AskingSingleton askingSingleton;
    private boolean doNotCheckAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_vocabulary_query, frameLayout);

        findElements();
        getCurrentLection();
        nextVocab();
        askingSingleton = AskingSingleton.getInstance();
        askingSingleton.resetData();
    }

    private void vocabIsFalse() {
        if (getIntent().getExtras().getBoolean(VocabularyLectionSelection.MULTIPLE_SELECTION)){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.activity_custom_dialog);

            Button repeatButton = (Button) dialog.findViewById(R.id.repeat);
            TextView tv = (TextView) dialog.findViewById(R.id.textView);
            tv.setText(getString(R.string.vocabWrong) + getString(R.string.solution) + ": " + getAnswer());
            repeatButton.setVisibility(View.GONE);
            Button showSolutionButton = (Button) dialog.findViewById(R.id.solution);
            showSolutionButton.setText(R.string.ok);
            showSolutionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSolution();
                    doNotCheckAnswer = true;
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else{
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.activity_custom_dialog);

            Button repeatButton = (Button) dialog.findViewById(R.id.repeat);
            repeatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repeatVocab();
                    dialog.dismiss();
                }
            });
            Button showSolutionButton = (Button) dialog.findViewById(R.id.solution);
            showSolutionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSolution();
                    doNotCheckAnswer = true;
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    private void nextVocab() {
        try {
            setCurrVocab((Vocab) vocabIterator.next());
        } catch (NoSuchElementException e) {
            final Intent intent = new Intent(this,VocabularyResult.class);
            startActivity(intent);
        }

        answerEditText.setEnabled(true);
        answerEditText.setText("");
        nextButton.setText(R.string.check);
    }

    private void setCurrVocab(Vocab vocab) {
        currVocab = vocab;

        if (currentDirection == 1l){
            questionTextView.setText(vocab.getForeign());
        } else{
            questionTextView.setText(vocab.getGerman());
        }

        char firstChar = getAnswer().charAt(0);

        if (Character.isUpperCase(firstChar)){
            answerEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        } else{
            answerEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        subHeadlineText.setText(R.string.lection + " " + Integer.toString(vocab.getLection()));
        if(currVocab.getPicture() != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(currVocab.getPicture(), 0, currVocab.getPicture().length);
            vocabPictureImageView.setImageBitmap(bitmap);
            vocabPictureImageView.setVisibility(View.VISIBLE);
        } else{
            vocabPictureImageView.setVisibility(View.GONE);
        }

    }

    private void findElements() {
        questionTextView = (TextView) findViewById(R.id.text2);
        answerEditText = (EditText) findViewById(R.id.answer);
        subHeadlineText = (TextView) findViewById(R.id.subHeadLine);
        headlineText = (TextView) findViewById(R.id.headline);
        nextButton = (Button) findViewById(R.id.next);
        bubbleTextView = (TextView) findViewById(R.id.bubbleTextAsk);
        skipButton = (Button) findViewById(R.id.skip);
        vocabPictureImageView = (ImageView) findViewById(R.id.vocabImage);
    }

    private void getCurrentLection() {
        Bundle bundle = getIntent().getExtras();

        currentLection = LectionDatabaseHelper.get(bundle.getLong(VocabularyLectionSelection.SELECTED_LECTION) + 1l);
        //vocabDatabaseHelper = VocabDatabaseHelper.getInstance(this);
        if(bundle.getBoolean(ASKWRONGVOCABSAGAIN)){
            vocabList = AskingSingleton.wrongVocabs;
        } else {
            if(bundle.getBoolean(VocabularyLectionSelection.MULTIPLE_SELECTION)){
                List<Integer> indices = AskingSingleton.selectedLections;
                vocabList = VocabDatabaseHelper.getFromMultipleLection(indices);
                subHeadlineText.setVisibility(View.INVISIBLE);
                headlineText.setText(R.string.vocabTest);
                skipButton.setVisibility(View.INVISIBLE);

            } else {
                vocabList = vocabDatabaseHelper.getFromLection(currentLection.getNumber());
                headlineText.setText(FormatHelper.colorsString(this,getString(R.string.askVocabs), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle)));
            }
        }

        vocabIterator = vocabList.listIterator(0);

        if(bundle.getBoolean(VocabularyLectionSelection.RANDOM)){
            Collections.shuffle(vocabList);
        }

        currentDirection = bundle.getLong(VocabularyLectionSelection.SELECTED_DIRECTION);
    }

    public void onClickButtonNext(View v) {
        if(doNotCheckAnswer) {
            doNotCheckAnswer = false;
            answerEditText.setEnabled(true);
            nextVocab();

        } else{
            if (checkVocabCorrect()) {
                vocabIsCorrect();
            } else {
                vocabIsFalse();
            }
        }
    }

    private void showSolution() {
        answerEditText.setText(getAnswer());
        answerEditText.setEnabled(false);
        AskingSingleton.wrongVocabs.add(currVocab);
        nextButton.setText(R.string.next);
    }

    private void repeatVocab() {
        answerEditText.setText("");
    }

    private void vocabIsCorrect() {
        AskingSingleton.rightVocabs.add(currVocab);
        if(positiveFeedbackRelevant()) {
            setBubbleTextAndAnimate();
        }
        nextVocab();
    }

    private boolean checkVocabCorrect() {
        String answer = answerEditText.getText().toString();
        if (answer.contentEquals(getAnswer())) {
            return true;
        } else {
            return false;
        }

    }

    private void setBubbleTextAndAnimate() {
        bubbleTextView.setText(getPositiveFeedbackText());
        bubbleTextView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_in);
        bubbleTextView.startAnimation(animation);

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_fade_out);
                        bubbleTextView.startAnimation(animation);
                        bubbleTextView.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        t.start();
    }

    private String getPositiveFeedbackText() {
        //TODO auslagern in Ressourcen Datei
        String[] appreciation = new String[]{"Gut gemacht!", "Sehr gut!", "Weiter so!", "Du wirst immer besser!"};
        return(appreciation[new Random().nextInt(appreciation.length)]);
    }

    private String getAnswer() {
        if(currentDirection == 1l){
            return(currVocab.getGerman().trim());
        } else {
            return(currVocab.getForeign().trim());
        }
    }

    public void onClickButtonSkip(View v){
        skipVocab();
    }

    private void skipVocab() {
        AskingSingleton.wrongVocabs.add(currVocab);
        nextVocab();

    }

    private boolean positiveFeedbackRelevant() {
        Random rand = new Random();
        int val = rand.nextInt(4) + 1;
        if (val == 1)
        {
            return true;
        } else {
            return false;
        }
    }
}
