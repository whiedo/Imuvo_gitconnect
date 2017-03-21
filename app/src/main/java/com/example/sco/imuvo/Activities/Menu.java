package com.example.sco.imuvo.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sco.imuvo.CustomViews.ButtonJokerman;
import com.example.sco.imuvo.CustomViews.TextViewITCKRIST;
import com.example.sco.imuvo.DatabaseHelper.LectionDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.SocialMediaHelper;
import com.example.sco.imuvo.Model.SingletonUser;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.R;

public class Menu extends BaseActivity {

    ButtonJokerman dictionaryButton, readButton, vocabsButton, readAloudButton, testButton, taskButton;
    User user;
    ImageView bubbleImageView;
    TextViewITCKRIST bubbleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_menu, frameLayout);
        getElements();
        getInitialValuesFromIntent();
        getBubble();
    }

    @Override
    public  void onBackPressed(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.activity_custom_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.textView);
        textView.setText(R.string.logOutQuestion);
        Button repeatButton = (Button) dialog.findViewById(R.id.solution);
        repeatButton.setText(R.string.yes);
        repeatButton.setTextColor(Color.GREEN);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SocialMediaHelper.logOutFromSocialMedia();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();

            }
        });

        Button showSolutionButton = (Button) dialog.findViewById(R.id.repeat);
        showSolutionButton.setText(R.string.no);
        showSolutionButton.setTextColor(Color.RED);
        showSolutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getInitialValuesFromIntent(){
        user = new User(0,SingletonUser.data,"");
    }

    private void getElements() {
        dictionaryButton = (ButtonJokerman) findViewById(R.id.dictionary);
        readButton = (ButtonJokerman) findViewById(R.id.read);
        vocabsButton = (ButtonJokerman) findViewById(R.id.vocabs);
        readAloudButton = (ButtonJokerman) findViewById(R.id.readAloud);
        testButton = (ButtonJokerman) findViewById(R.id.test);
        bubbleTextView = (TextViewITCKRIST) findViewById(R.id.bubbleText);
    }

    public void getBubble(){
        bubbleTextView.setText(getString(R.string.hello) + getString(R.string.menuWelcomeText));
        bubbleTextView.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public void onClickDictionary(View v){
        final Intent menuIntent = new Intent(this,Dictionary.class);
        startActivity(menuIntent);
    }

    public void onClickRead(View v){
        if (!checkLectionAvailable()) return;

        final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
        Bundle bundle = new Bundle();
        bundle.putString(VocabularyLectionSelection.TYPE, VocabularyLectionSelection.READ);
        menuIntent.putExtras(bundle);
        startActivity(menuIntent);
    }

    public boolean checkLectionAvailable() {
        if (!LectionDatabaseHelper.lectionExists()) {
            Toast.makeText(this, R.string.noLectionsAvailable,Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void onClickVocabs(View v){
        final Intent menuIntent = new Intent(this,VocabularyLectionList.class);
        startActivity(menuIntent);
    }

    public void onClickReadAloud(View v){
        if (!checkLectionAvailable()) return;

        final Intent menuIntent = new Intent(this,VocabularyLectionSelection.class);
        Bundle bundle = new Bundle();
        bundle.putString(VocabularyLectionSelection.TYPE, VocabularyLectionSelection.READALOUD);
        menuIntent.putExtras(bundle);
        startActivity(menuIntent);

    }

    public void onClickTest(View v){
        if (!checkLectionAvailable()) return;

        final Intent menuIntent = new Intent(this,VocabularyTestSelection.class);
        startActivity(menuIntent);
    }
}
