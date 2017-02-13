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
    TextView bubbleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        nameEditText = (EditText) findViewById(R.id.name);
        passwordEditText = (EditText) findViewById(R.id.password);
        bubbleText = (TextView) findViewById(R.id.speechbubble);
        bubbleText.setText(R.string.createUserText);
    }

    public void onClickCreateUser(View v){
        if(checkUserCorrect() && UserDatabaseHelper.get(nameEditText.getText().toString()) == null){
            User user = new User(0,nameEditText.getText().toString(),passwordEditText.getText().toString());
            UserDatabaseHelper.insert(user);

            FormatHelper.makeLongToast(this,getString(R.string.userCreated));
            final Intent menuIntent = new Intent(this,Login.class);
            startActivity(menuIntent);
            finish();
        }
        else{
            FormatHelper.makeLongToast(this,getString(R.string.passwordWrong));
        }
    }

    public boolean checkUserCorrect(){
        return true;
    }

    public void onClickBurgerMenu(View v){
        final Intent menuIntent = new Intent(this,Login.class);
        menuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(menuIntent);
        finish();
    }

}
