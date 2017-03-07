package com.example.sco.imuvo.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sco.imuvo.DatabaseHelper.UserDatabaseHelper;
import com.example.sco.imuvo.HelperClasses.AlarmReceiver;
import com.example.sco.imuvo.HelperClasses.FormatHelper;
import com.example.sco.imuvo.HelperClasses.InitData;
import com.example.sco.imuvo.HelperClasses.SocialMediaHelper;
import com.example.sco.imuvo.HelperClasses.TypefaceUtil;
import com.example.sco.imuvo.Model.SingletonUser;
import com.example.sco.imuvo.Model.User;
import com.example.sco.imuvo.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.Callable;

public class Login extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    TextView welcomeTextView, bubbleTextView;
    Button startButton;
    EditText nameEditText, passwordEditText;
    CallbackManager mFacebookCallbackManager;
    LoginButton facebookSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_login, frameLayout);
        disableDrawerLayout();

        checkPermissions();

        TypefaceUtil.overrideFont(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();
        SocialMediaHelper.logOutFromSocialMedia();

        getElements();

        registerSocialMediaCallbacks();

        //TODO test fct.
        testFunction();
        setInitData();
        initSQLData(this);

        setNotificationAlarm();
    }

    public void setNotificationAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(Login.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Login.this, 0, alarmIntent, 0);

        Calendar alarmStartTime = Calendar.getInstance();
        //TODO change starttime and interval of notifications
        alarmStartTime.add(Calendar.MINUTE, 1);
        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
    }

    private int getInterval(){
        int seconds = 30;
        int milliseconds = 1000;
        int repeatMS = seconds * milliseconds;
        return repeatMS;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    private void disableDrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void registerSocialMediaCallbacks() {
        facebookSignInButton = (LoginButton)findViewById(R.id.facebook_sign_in_button);
        facebookSignInButton.setReadPermissions("public_profile");
        facebookSignInButton.setReadPermissions("email");
        facebookSignInButton.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            String name = object.getString("first_name");
                                            String email = object.getString("email");
                                            if (UserDatabaseHelper.get(email) == null) {
                                                User user = new User(0, email, passwordEditText.getText().toString());
                                                UserDatabaseHelper.insert(user);
                                            }

                                            SingletonUser.data = name;

                                            nameEditText.setText(name);
                                            passwordEditText.setText("");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        //New request to facebook for user name
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        handleSignInResult(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                LoginManager.getInstance().logOut();
                                return null;
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        handleSignInResult(null);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        handleSignInResult(null);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(FacebookSdk.isFacebookRequestCode(requestCode)) {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Callable<Void> logout) {
        if(logout == null) {
            Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, Menu.class));
        }
    }

    private void initSQLData(final Context context) {
        new Thread(new Runnable() {
            public void run() {
                InitData.initSQLData(context);
            }
        }).start();
    }

    private void setInitData() {
        bubbleTextView.setText(R.string.welcomeText);
        bubbleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        welcomeTextView.setText(FormatHelper.colorsString(getString(R.string.learningWithImuvo), ContextCompat.getColor(this, R.color.colorMenuTextLeft),ContextCompat.getColor(this, R.color.colorMenuTextMiddle),ContextCompat.getColor(this, R.color.colorMenuTextRight)));
    }

    private void testFunction() {
        nameEditText.setText("Simon");
        passwordEditText.setText("Simon");
    }

    public void getElements(){
        welcomeTextView = (TextView) findViewById(R.id.welcomeText);
        startButton = (Button) findViewById(R.id.start);
        nameEditText = (EditText) findViewById(R.id.name);
        passwordEditText = (EditText) findViewById(R.id.password);
        bubbleTextView = (TextView) findViewById(R.id.bubbleTextLogIn);
    }

    public void onClickStart(View v){
        if(checkUserCorrect()){
            final Intent menuIntent = new Intent(this,Menu.class);
            String username = nameEditText.getText().toString();
            SingletonUser.data = username;
            nameEditText.setText("");
            passwordEditText.setText("");
            startActivity(menuIntent);
        }
        else{
            FormatHelper.makeLongToast(this,getString(R.string.passwordWrong));
        }
    }

    private boolean checkUserCorrect(){
        User user = null;
        user = UserDatabaseHelper.get(nameEditText.getText().toString());
        try{
            if (user.getPassword().contentEquals(passwordEditText.getText())){
                return true;
            }
            else
                return false;
        }
        catch (NullPointerException e){
            return false;
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulogin,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new:
                newUser();
                return true;
            case R.id.showVocabs:
                showVocabs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showVocabs() {
        final Intent menuIntent = new Intent(this,VocabularyLectionList.class);
        startActivity(menuIntent);
    }

    private void newUser() {
        final Intent menuIntent = new Intent(this,CreateUser.class);
        String username = nameEditText.getText().toString();
        SingletonUser.data = username;
        startActivity(menuIntent);
    }
}
