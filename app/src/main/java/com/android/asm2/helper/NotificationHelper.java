package com.android.asm2.helper;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.asm2.R;

/**
 * This class sends out notification to phone
 */
public class NotificationHelper {
    @SuppressLint("StaticFieldLeak")
    private static NotificationHelper helper;
    private static int notificationId;
    private final Class<?> classForNotification;
    private final Context context;
    private final String CHANNEL_ID = "my channel";

    private NotificationHelper(Class<?> classForNotification, Context context) {
        this.classForNotification = classForNotification;
        this.context = context;
        createNotificationChannel();
    }

    public static void init(Class<?> classForNotification, Context context) {
        helper = new NotificationHelper(classForNotification, context);
        notificationId = 0;
    }

    public static NotificationHelper getInstance() {
        return helper;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(String title, String content) {
        NotificationCompat.Builder notifyDialog =
                new NotificationCompat.Builder(context, CHANNEL_ID);
        Intent notifyIntent = new Intent(context, classForNotification);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity
                (context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyDialog
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)
                .setPriority(Notification.PRIORITY_MAX);
        Notification notification = notifyDialog.build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
        notificationId++;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, "My name", importance);
            channel.setDescription("This is the description");
            // Register the channel with the system
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
