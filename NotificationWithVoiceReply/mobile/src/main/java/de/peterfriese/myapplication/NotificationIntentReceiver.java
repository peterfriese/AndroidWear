package de.peterfriese.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

public class NotificationIntentReceiver extends BroadcastReceiver {

    public static final String WISH_HAPPY_BIRTHDAY = "de.peterfriese.WISH_HAPPY_BIRTHDAY";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WISH_HAPPY_BIRTHDAY)) {
            Bundle remoteInputResults = RemoteInput.getResultsFromIntent(intent);
            if (remoteInputResults != null) {
                CharSequence replyMessage = remoteInputResults.getCharSequence(Constants.EXTRA_VOICE_REPLY);
                if (replyMessage != null) {
                    String text = String.format("You just said '%s'", replyMessage);
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
