package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sco.imuvo.DatabaseHelper.UserDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.R;

public class CreateUser extends AppCompatActivity {

    EditText nameEditText, passwordEditText;
    public UserDatabaseHelper userDatabaseHelper;
    TextView bubbleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        nameEditText = (EditText) findViewById(R.id.name);
        passwordEditText = (EditText) findViewById(R.id.password);
        bubbleText = (TextView) findViewById(R.id.speechbubble);
        bubbleText.setText("Hier kannst du einen neuen Benutzer anlegen. Bitte lege einen Benutzernamen und ein Passwort fest.");
    }

    public void onClickCreateUser(View v){
        if(checkUserCorrect()){
            User user = new User(0,nameEditText.getText().toString(),passwordEditText.getText().toString());
            userDatabaseHelper.insert(user);
            FormatHelper.makeLongToast(this,"Nutzer wurde angelegt.");
            final Intent menuIntent = new Intent(this,Login2.class);
            startActivity(menuIntent);
            finish();
        }
        else{
            FormatHelper.makeLongToast(this,"Name oder Passwort sind falsch. Bitte versuche es erneut.");
        }
    }

    public boolean checkUserCorrect(){
        return true;
    }

    public void onClickBurgerMenu(View v){
        final Intent menuIntent = new Intent(this,Login2.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }

}
