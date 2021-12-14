package com.android.asm2.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BroadcastReceiverHelper extends BroadcastReceiver {
    private NotificationHelper notificationHelper;
    private static BroadcastReceiverHelper broadcastReceiverHelper;

    private BroadcastReceiverHelper() {
        notificationHelper = NotificationHelper.getInstance();
    }

    public static BroadcastReceiverHelper getInstance() {
        if (broadcastReceiverHelper == null)
            broadcastReceiverHelper = new BroadcastReceiverHelper();
        return broadcastReceiverHelper;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "Zone updated":
                notificationHelper.createNotification("Table 'zone' changed", "A existing zone is updated");
                break;
            case "Zone added":
                notificationHelper.createNotification("Table 'zone' changed", "A new zone is added");
                break;
            case "Report updated":
                notificationHelper.createNotification("Table 'report' changed", "A existing report is updated");
                break;
            case "Report added":
                notificationHelper.createNotification("Table 'report' changed", "A new report is added");
                break;
            case "User updated":
                notificationHelper.createNotification("Table 'user' changed", "A existing user is updated");
                break;
            case "User added":
                notificationHelper.createNotification("Table 'user' changed", "A new user is added");
                break;
        }
    }
}
