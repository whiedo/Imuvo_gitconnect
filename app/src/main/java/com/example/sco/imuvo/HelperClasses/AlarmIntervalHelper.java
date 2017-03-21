package com.example.sco.imuvo.HelperClasses;

public class AlarmIntervalHelper {

    public static int getAlarmInterval(){
        int minutes = 3;
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = minutes * seconds * milliseconds;
        return repeatMS;
    }
}
