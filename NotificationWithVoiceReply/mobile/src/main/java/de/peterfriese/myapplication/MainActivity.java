package de.peterfriese.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                String name = editText.getText().toString();
                sendNotification(name);
            }
        });
    }

    private void sendNotification(String name) {

        String replyLabel = getResources().getString(R.string.reply_label);
        String[] cannedResponses = getResources().getStringArray(R.array.canned_responses);
        RemoteInput remoteInput = new RemoteInput.Builder(Constants.EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(cannedResponses)
                .build();
        remoteInput.getExtras().putString(Constants.EXTRA_PERSON_NAME, name);

        // Send input to broadcast receiver
        Intent broadcastIntent = new Intent(NotificationIntentReceiver.WISH_HAPPY_BIRTHDAY);
        broadcastIntent.putExtra(Constants.EXTRA_PERSON_NAME, name);
        PendingIntent broadcastPendingIntent = PendingIntent.getBroadcast(this, 17, broadcastIntent, 0);

        // Send input to this activity
//        Intent replyIntent = new Intent(this, MainActivity.class);
//        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 0, replyIntent, 0);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_reply, replyLabel, broadcastPendingIntent)
                    .addRemoteInput(remoteInput)
                .build();

        String contentTitle = getResources().getString(R.string.birthday_notification_title);
        String contentTextTemplate = getResources().getString(R.string.birthday_notification_body);
        String contentText = String.format(contentTextTemplate, name);

        Notification notification =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    // TODO the reply action does not get auto-dismissed on the wearable. Find out if this is WAI
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.peter))
                            .setBigContentTitle(contentTitle)
                            .setSummaryText(contentText))
                    .extend(
                            new WearableExtender()
                                    .addAction(action))
                    .build();

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, notification);

        notificationId++;
    }

}
