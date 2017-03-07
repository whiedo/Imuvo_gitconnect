package com.example.sco.imuvo.HelperClasses;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.sco.imuvo.Activities.Login;
import com.example.sco.imuvo.R;

/**
 * Created by fte on 07.03.2017.
 */

public class AlarmService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "BANANEALARM";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent mIntent = new Intent(this, Login.class);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                .setTicker(getString(R.string.notificationTicker))
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.notificationTitle))
                .setContentText(getString(R.string.notificationContent));

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}