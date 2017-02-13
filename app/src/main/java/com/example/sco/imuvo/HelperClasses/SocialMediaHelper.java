package com.example.sco.imuvo.HelperClasses;

import com.facebook.login.LoginManager;

public class SocialMediaHelper {

    public static void logOutFromSocialMedia() {
        LoginManager.getInstance().logOut();
    }
}
