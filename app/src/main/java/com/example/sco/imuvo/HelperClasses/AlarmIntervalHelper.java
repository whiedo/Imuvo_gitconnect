package com.example.sco.imuvo.HelperClasses;

/**
 * Created by fte on 21.03.2017.
 */

public class AlarmIntervalHelper {

    public static int getAlarmInterval(){
        int minutes = 3;
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = minutes * seconds * milliseconds;
        return repeatMS;
    }
}
