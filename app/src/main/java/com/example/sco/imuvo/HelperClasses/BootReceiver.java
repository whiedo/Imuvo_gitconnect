package com.example.sco.imuvo.HelperClasses;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by fte on 07.03.2017.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            Calendar alarmStartTime = Calendar.getInstance();
            //TODO change starttime and interval of notifications
            alarmStartTime.add(Calendar.MINUTE, 1);
            alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);

            Intent service1 = new Intent(context, AlarmService.class);
            context.startService(service1);
        }
    }

    private int getInterval(){
        int seconds = 30;
        int milliseconds = 1000;
        int repeatMS = seconds * milliseconds;
        return repeatMS;
    }
}
