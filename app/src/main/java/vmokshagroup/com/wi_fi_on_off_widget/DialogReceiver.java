package vmokshagroup.com.wi_fi_on_off_widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.RemoteViews;

public class DialogReceiver extends BroadcastReceiver {
    private AudioManager myAudioManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("myOnClickTag1")) {

            Log.w("Widget", "Clicked button1");
            myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            switch (myAudioManager.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT:
                    Log.i("MyApp", "Silent mode");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    /*views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.high_volume);*/

                    break;
                case AudioManager.RINGER_MODE_NORMAL:

                    Log.i("MyApp", "Normal mode");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                   /* views.setImageViewResource(R.id.ringer_logo,
                           R.mipmap.mute_volume);*/
                    break;
                case AudioManager.RINGER_MODE_VIBRATE:
                    Log.i("MyApp", "Normal mode");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    break;
            }


        }


    }
}
