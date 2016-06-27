package vmokshagroup.com.wi_fi_on_off_widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WifiWidgetService extends Service {
    String ipAddress;
    boolean wifiConnected = false;
    boolean isBlutoothEnable = false;
    Thread widgetUpdateThread;
    RemoteViews widgetUi;
    private static final String MyOnClick1 = "myOnClickTag1";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";
    private static final String MyOnClick4 = "MyOnClickTag4";
    private static final String MyOnClick5 = "myOnClickTag5";
    private static final String MyOnClick6 = "MyOnClickTag6";
    private static final String MyOnClick7 = "myOnClickTag7";
    private static final String MyOnClick8 = "myOnClickTag8";
    private static final String MyOnClick9 = "myOnClickTag9";
    private static final int MINIMUM_BACKLIGHT = 1;
    private static final int MIDIUM_BACKLIGHT = 127;
    private static final int MAXIMUM_BACKLIGHT = 255;
    int brightness;
    int brightnessMode;
    Boolean hasFlash;

    public static final String ACTION_SERVICE_STATE_CHANGED = "android.intent.action.SERVICE_STATE";
    /* private static final int MINIMUM_BACKLIGHT = 18;
     private static final int MAXIMUM_BACKLIGHT = 255;*/
    //Anshika code
    /*private static final int MINIMUM_BRIGHTNESS = 20;
    private static final int MIDIUM_BRIGHTNESS = 100;
    private static final int MAXIUM_BRIUGHTNESS = 255;*/
    private AudioManager myAudioManager;
    int mod;
    private boolean isBtEnable = false;
    boolean toManual;
    public static final String MyPREFERENCES = "MyPrefs";
    DialogBoxActivity dialogBoxActivity;
 private static WifiManager wifi;
    // Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        widgetUpdateThread = new WidgetUpdateThread(this, intent);
        widgetUpdateThread.start();
        dialogBoxActivity = new DialogBoxActivity();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private void initNetworkInfo() {
        // get connection info if available
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if ((info != null) && (info.isConnected())
                && (ConnectivityManager.TYPE_WIFI == info.getType())) {
            wifiConnected = true;

        } else {
            wifiConnected = false;

        }
    }

    private void findBlutoothStatus() {

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // mBluetoothAdapter.e
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {

            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                isBlutoothEnable = false;

            } else {
                isBlutoothEnable = true;

            }
        }

    }


    private class WidgetUpdateThread extends Thread {
        Context context;

        WidgetUpdateThread(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                initNetworkInfo();

                // set remote views


                widgetUi = new RemoteViews(
                        context.getPackageName(), R.layout.wifi_app_widget);
                // widgetUi.setTextViewText(R.id.tvIpAddress, ipAddress);
                wifi =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);

              /*  try {
                    wifiConnected = new isNetworkConnected().execute().get();
                } catch (Exception e) {
                    Log.e("wifiConnected ", e.getMessage().toString());
                }*/

                //TODO:for wifi widget
                if (wifiConnected&&wifi.isWifiEnabled()) {
                    widgetUi.setImageViewResource(R.id.wifi_logo,
                            R.mipmap.wifi_on);
                    widgetUi.setViewVisibility(R.id.wifi_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.wifi_text1, View.GONE);

                } else if(!wifiConnected&&wifi.isWifiEnabled()){
                    widgetUi.setImageViewResource(R.id.wifi_logo,
                            R.mipmap.wifi_not_con);
                    widgetUi.setViewVisibility(R.id.wifi_text1, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.wifi_text, View.GONE);

                }else {
                    widgetUi.setImageViewResource(R.id.wifi_logo,
                            R.mipmap.wifi_off);
                    widgetUi.setViewVisibility(R.id.wifi_text1, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.wifi_text, View.GONE);
                }



                //onclick listener for wifi button
                widgetUi.setOnClickPendingIntent(R.id.wi_fi_relative,
                        getPendingSelfIntent(context, MyOnClick1));


                //TODO:For Brightness

                try {
                    brightness = android.provider.Settings.System.getInt(
                            context.getContentResolver(),
                            android.provider.Settings.System.SCREEN_BRIGHTNESS);
                    brightnessMode = android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
                    if (((brightness >= MINIMUM_BACKLIGHT) && (brightness < MIDIUM_BACKLIGHT))) {
                        widgetUi.setImageViewResource(R.id.brightness_logo,
                                R.mipmap.brightness_low);

                        widgetUi.setViewVisibility(R.id.brightness_text1, View.VISIBLE);
                        widgetUi.setViewVisibility(R.id.brightness_text, View.GONE);
                        Log.i("brightness", brightness + "");
                    } else if (((brightness >= MIDIUM_BACKLIGHT) && (brightness < MAXIMUM_BACKLIGHT))) {

                        widgetUi.setImageViewResource(R.id.brightness_logo,
                                R.mipmap.brightness_medium);

                        widgetUi.setViewVisibility(R.id.brightness_text1, View.GONE);
                        widgetUi.setViewVisibility(R.id.brightness_text, View.VISIBLE);
                        Log.i("brightness", brightness + "");
                    } else if (((brightness >= MAXIMUM_BACKLIGHT))) {
                        brightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
                        // Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);

                        widgetUi.setImageViewResource(R.id.brightness_logo,
                                R.mipmap.brightness_high);
                        widgetUi.setViewVisibility(R.id.brightness_text1, View.GONE);
                        widgetUi.setViewVisibility(R.id.brightness_text, View.VISIBLE);
                        Log.i("brightness", brightness + "");


                    }
                } else {
                    widgetUi.setImageViewResource(R.id.brightness_logo,
                            R.mipmap.brightness_auto);
                    widgetUi.setViewVisibility(R.id.brightness_text1, View.GONE);
                    widgetUi.setViewVisibility(R.id.brightness_text, View.VISIBLE);
                }


               /* widgetUi.setImageViewResource(R.id.brightness_logo,
                        R.mipmap.full_brightness);*/
                widgetUi.setOnClickPendingIntent(R.id.brightness_relative, getPendingSelfIntent(context, MyOnClick2));


                //TODO:setting it for phone ringer
                myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                mod = myAudioManager.getRingerMode();
                Log.e("mod", mod + "");
                if (mod == AudioManager.RINGER_MODE_NORMAL) {

                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.volume_on);
                    widgetUi.setViewVisibility(R.id.ringer_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.ringer_text1, View.GONE);
                    /*dialogBoxActivity.mRinger_logo.setImageResource(R.mipmap.volume_on);*/

                } else if (mod == AudioManager.RINGER_MODE_SILENT) {
                   /*(mod==AudioManager.RINGER_MODE_NORMAL)*/

                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.volume_off);
                    widgetUi.setViewVisibility(R.id.ringer_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.ringer_text1, View.VISIBLE);

                   /* dialogBoxActivity.mRinger_logo.setImageResource(R.mipmap.volume_off);*/
                } else {
                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.vibration_on);
                    widgetUi.setViewVisibility(R.id.ringer_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.ringer_text1, View.GONE);

                }

                widgetUi.setOnClickPendingIntent(R.id.ringer_relative, getPendingSelfIntentRingtone(context, MyOnClick3));
               /* int ringerMode = myAudioManager.getRingerMode();
                if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {

                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.volume_on);

                } else if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
                   *//*(ringerMode==AudioManager.RINGER_MODE_NORMAL)*//*
                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.volume_off);

                } else {
                    widgetUi.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.volume_off);
                }*/

                //TODO:for Aeroplane mode
                boolean isEnabled = Settings.System.getInt(
                        getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                Log.e("airoplane status before clicking", isEnabled + "");
                if (isEnabled) {
                    widgetUi.setImageViewResource(R.id.aeroplan_logo, R.mipmap.flight_mode_on);
                    widgetUi.setViewVisibility(R.id.aeroplan_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.aeroplan_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.aeroplan_logo, R.mipmap.flight_mode_off);
                    widgetUi.setViewVisibility(R.id.aeroplan_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.aeroplan_text1, View.VISIBLE);
                }


                Intent settings = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                PendingIntent pendingIntentImage = PendingIntent
                        .getActivity(context, 0, settings,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                widgetUi.setOnClickPendingIntent(R.id.aeroplan_Rel,
                        pendingIntentImage);

                boolean isEnabled1 = Settings.System.getInt(
                        getContentResolver(),
                        Settings.System.AIRPLANE_MODE_ON, 0) == 1;
                Log.e("airoplane status", isEnabled1 + "");
                if (isEnabled1) {
                    widgetUi.setImageViewResource(R.id.aeroplan_logo, R.mipmap.flight_mode_on);
                    widgetUi.setViewVisibility(R.id.aeroplan_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.aeroplan_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.aeroplan_logo, R.mipmap.flight_mode_off);
                    widgetUi.setViewVisibility(R.id.aeroplan_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.aeroplan_text1, View.VISIBLE);
                }


                //TODO:for vibration mode



               /* if (MyWidgetIntentReceiver.isFlashOn) {
                    widgetUi.setImageViewResource(R.id.vibration_mode_logo,
                            R.mipmap.flash_on);
                    widgetUi.setViewVisibility(R.id.vibration_mode_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.vibration_mode_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.vibration_mode_logo,
                            R.mipmap.flash_off);
                    widgetUi.setViewVisibility(R.id.vibration_mode_text1, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.vibration_mode_text, View.GONE);
                }


                widgetUi.setOnClickPendingIntent(R.id.vibration_mode_relative, getPendingSelfIntent(context, MyOnClick5));*/


                //TODO:for gps
               /* final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    widgetUi.setImageViewResource(R.id.gps_log, R.mipmap.gps_off);
                    widgetUi.setViewVisibility(R.id.gps_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.gps_text1, View.VISIBLE);

                } else {
                    widgetUi.setImageViewResource(R.id.gps_log, R.mipmap.gps_on);
                    widgetUi.setViewVisibility(R.id.gps_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.gps_text1, View.GONE);
                }

                Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               *//* gpsIntent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));*//*
                PendingIntent gpsPendingIntend = PendingIntent
                        .getActivity(context, 0, gpsIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                Log.e("gps status", "");

                widgetUi.setOnClickPendingIntent(R.id.gps_relative, gpsPendingIntend);

                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    widgetUi.setImageViewResource(R.id.gps_log, R.mipmap.gps_on);
                    widgetUi.setViewVisibility(R.id.gps_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.gps_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.gps_log, R.mipmap.gps_off);
                    widgetUi.setViewVisibility(R.id.gps_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.gps_text1, View.VISIBLE);
                }*/

                // TODO:for bluetooth

              /*  findBlutoothStatus();

                if (isBlutoothEnable) {
                    widgetUi.setImageViewResource(R.id.bluetooth_logo,
                            R.mipmap.blutooth_on);
                    widgetUi.setViewVisibility(R.id.bluetooth_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.bluetooth_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.bluetooth_logo,
                            R.mipmap.blutooth_off);
                    widgetUi.setViewVisibility(R.id.bluetooth_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.bluetooth_text1, View.VISIBLE);
                }

                widgetUi.setOnClickPendingIntent(R.id.bluetooth_rel, getPendingSelfIntent(context, MyOnClick7));
                Log.e("bluetooth status after click", isBlutoothEnable + "");

*/
                //TODO:for mobile data


              /*  if (isMbileenable()) {
                    widgetUi.setImageViewResource(R.id.mobile_data_logo,
                            R.mipmap.data_on);
                    widgetUi.setViewVisibility(R.id.mobile_data_text, View.VISIBLE);
                    widgetUi.setViewVisibility(R.id.mobile_data_text1, View.GONE);
                } else {
                    widgetUi.setImageViewResource(R.id.mobile_data_logo,
                            R.mipmap.data_off);
                    widgetUi.setViewVisibility(R.id.mobile_data_text, View.GONE);
                    widgetUi.setViewVisibility(R.id.mobile_data_text1, View.VISIBLE);
                }
                PendingIntent mobilependingIntent;

                Intent dataintent = new Intent().setAction(Settings.ACTION_SETTINGS);
                mobilependingIntent = PendingIntent
                        .getActivity(context, 0, dataintent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                widgetUi.setOnClickPendingIntent(R.id.mobile_data_rel, mobilependingIntent);*/


                //TODO:for vibration on and off

                widgetUi.setImageViewResource(R.id.more_icon,R.mipmap.more_icon );
                widgetUi.setViewVisibility(R.id.dot_text,View.VISIBLE);
                widgetUi.setOnClickPendingIntent(R.id.dot_rel, getPendingSelfIntent(context, MyOnClick9));


                try {
                    ComponentName widgetComponent = new ComponentName(context,
                            WIfiAppWidget.class);
                    AppWidgetManager widgetManager = AppWidgetManager
                            .getInstance(context);
                    widgetManager.updateAppWidget(widgetComponent, widgetUi);
                } catch (Exception e) {
                    Log.e("WifiWidget", "Failed to update widget", e);
                }
            } catch (Exception e) {
                Log.e("WifiWidget", "Failed to update widget", e);
            } finally {
                // clean up
                WifiWidgetService.this.stopSelf();
            }

            if (wifiConnected) {
                widgetUi.setImageViewResource(R.id.wifi_logo, R.mipmap.wifi_on);

                //remoteViews.setTextViewText(R.id.wifi_text,context.getResources().getString(R.string.wifi_status_not_connected));
                wifiConnected = false;

            } else {
                widgetUi.setImageViewResource(R.id.wifi_logo, R.mipmap.wifi_off);
                widgetUi.setViewVisibility(R.id.wifi_text1, View.VISIBLE);
                widgetUi.setViewVisibility(R.id.wifi_text, View.GONE);

                wifiConnected = true;
            }


        }

    }

    private boolean isMbileenable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        if (!isConnected) {
            return false;
        }
        return true;
    }


    protected PendingIntent getPendingSelfIntent(Context context, String action) {

        Intent intent = new Intent(context, MyWidgetIntentReceiver.class);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MyWidgetIntentReceiver.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        intent.setAction(action);

        return PendingIntent.getBroadcast(context, 0, intent, 0);

    }

    protected PendingIntent getPendingSelfIntentRingtone(Context context, String action) {

        Intent intent1 = new Intent(context, MyWidgetIntentReceiver.class);
        intent1.setAction(action);

        return PendingIntent.getBroadcast(context, 0, intent1, 0);


    }

    public static class isNetworkConnected extends AsyncTask<Boolean, Void, Boolean> {

        public static boolean enable = wifi.isWifiEnabled();

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            if (enable) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL("https://google.com").openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(3000); //choose your own timeframe
                    urlc.setReadTimeout(4000); //choose your own timeframe
                    urlc.connect();

                    return (urlc.getResponseCode() == 200);
                } catch (IOException e) {
                    return (false);  //connectivity exists, but no internet.
                }
            } else {
                return false;  //no connectivity
            }

        }
    }


}
