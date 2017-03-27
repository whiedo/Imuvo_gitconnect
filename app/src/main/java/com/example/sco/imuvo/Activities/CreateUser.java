package com.example.sco.imuvo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sco.imuvo.DatabaseHelper.UserDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.R;

public class CreateUser extends BaseActivity {

    EditText nameEditText, passwordEditText;
    TextView bubbleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_create_user, frameLayout);
        disableDrawerLayout();

        nameEditText = (EditText) findViewById(R.id.name);
        passwordEditText = (EditText) findViewById(R.id.password);
        bubbleText = (TextView) findViewById(R.id.speechbubble);
        bubbleText.setText(R.string.createUserText);
    }

    private void disableDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void onClickCreateUser(View v){
        if(UserDatabaseHelper.get(nameEditText.getText().toString()) == null){
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
}
