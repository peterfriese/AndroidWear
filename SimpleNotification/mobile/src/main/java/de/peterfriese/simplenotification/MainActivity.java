package de.peterfriese.simplenotification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private static final String EXTRA_EVENT_ID = "extra_event_id";
    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.triggerNotificationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    private void sendNotification() {
        Intent viewIntent = new Intent(this, MainActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, 17);

        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(String.format("Simple notification #%d", notificationId))
            .setContentText("This is just the start!")
            .setContentIntent(viewPendingIntent)
            .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId++, notification);
    }

}