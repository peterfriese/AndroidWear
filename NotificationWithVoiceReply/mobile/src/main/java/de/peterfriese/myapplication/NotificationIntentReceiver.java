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
            String personName = intent.getExtras().getString(Constants.EXTRA_PERSON_NAME);
            Bundle remoteInputResults = RemoteInput.getResultsFromIntent(intent);
            if (remoteInputResults != null) {
                CharSequence replyMessage = remoteInputResults.getCharSequence(Constants.EXTRA_VOICE_REPLY);
                String text = getMessage(replyMessage, personName);
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getMessage(CharSequence input, String personName) {
        if (input == null) {
            return "Didn't catch that.";
        }
        else {
            if (input.equals(Constants.EXTRA_VOICE_REPLY_KIND_INFORMAL)) {
                return String.format("Happy birthday, %s. Have a good one.", personName);
            }
            else if (input.equals(Constants.EXTRA_VOICE_REPLY_KIND_POLITE)) {
                return String.format("All the best, and a happy birthday, %s", personName);
            }
            else {
                return input.toString();
            }
        }
    }
}
