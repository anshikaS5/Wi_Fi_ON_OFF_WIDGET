package vmokshagroup.com.wi_fi_on_off_widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by anshikas on 09-05-2016.
 */
public class NetWatcher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // send action to the service
        Intent serviceIntent = new Intent(context, WifiWidgetService.class);
        context.startService(serviceIntent);
        if(DialogBoxActivity.activity != null)
            DialogBoxActivity.UpdateUIStatus();
    }

}

