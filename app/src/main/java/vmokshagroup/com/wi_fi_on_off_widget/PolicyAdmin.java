package vmokshagroup.com.wi_fi_on_off_widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by anshikas on 24-05-2016.
 */
public class PolicyAdmin extends DeviceAdminReceiver {
    @Override
    public void onDisabled(Context context, Intent intent) {
        // Called when the app is about to be deactivated as a device administrator.
        // Deletes previously stored password policy.
        super.onDisabled(context, intent);
       /* SharedPreferences prefs = context.getSharedPreferences(APP_PREF, Activity.MODE_PRIVATE);
        prefs.edit().clear().commit();*/
    }




    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, "Truiton's Device Admin is now enabled",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        CharSequence disableRequestedSeq = "Requesting to disable Device Admin";
        return disableRequestedSeq;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        Toast.makeText(context, "Device password is now changed",
                Toast.LENGTH_SHORT).show();
        DevicePolicyManager localDPM = (DevicePolicyManager) context
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName localComponent = new ComponentName(context,
                DeviceAdminReceiver.class);
        localDPM.setPasswordExpirationTimeout(localComponent, 0L);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        // This would require API 11 an above
        Toast.makeText(
                context,
                "Truiton's Device password is going to expire, please change to a new password",
                Toast.LENGTH_LONG).show();

        DevicePolicyManager localDPM = (DevicePolicyManager) context
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName localComponent = new ComponentName(context,
                DeviceAdminReceiver.class);
        long expr = localDPM.getPasswordExpiration(localComponent);
        long delta = expr - System.currentTimeMillis();
        boolean expired = delta < 0L;
        if (expired) {
            localDPM.setPasswordExpirationTimeout(localComponent, 10000L);
            Intent passwordChangeIntent = new Intent(
                    DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
            passwordChangeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(passwordChangeIntent);
        }
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        Toast.makeText(context, "Password failed", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        Toast.makeText(context, "Access Granted", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("tag",
                "MyDevicePolicyReciever Received: " + intent.getAction());
        super.onReceive(context, intent);
    }
}




