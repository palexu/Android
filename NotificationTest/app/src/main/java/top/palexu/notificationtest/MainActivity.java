package top.palexu.notificationtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button sendNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:
                NotificationManager manager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(MainActivity.this);
                Intent intent = new Intent(this,NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 0);
                long[] vibrates = {0, 1000, 1000, 1000};
                builder.setVibrate(vibrates);
                builder.setAutoCancel(false);
//                builder.setLights(Color.GREEN,1000,1000);
                builder.setTicker("this is ticker text");
                builder.setContentTitle("hello");
                builder.setSubText("This is subtext...");
                builder.setContentIntent(pendingIntent);
                builder.setOngoing(true);
                builder.setNumber(100);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.build();

                Notification notification = builder.getNotification();
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }
}