package vmokshagroup.com.wi_fi_on_off_widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Implementation of App Widget functionality.
 */
public  class WIfiAppWidget extends AppWidgetProvider {
   /* String ipAddress;
    boolean wifiConnected = false;
    Thread widgetUpdateThread;
    RemoteViews widgetUi;
    private static final String MyOnClick1 = "myOnClickTag1";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick3 = "myOnClickTag3";
    private static final String MyOnClick5 = "myOnClickTag5";
    private static final String MyOnClick7 = "myOnClickTag7";
    private static final String MyOnClick8 = "myOnClickTag8";
    private static final String MyOnClick4 = "MyOnClick4";

    public static final String ACTION_SERVICE_STATE_CHANGED = "android.intent.action.SERVICE_STATE";
    *//* private static final int MINIMUM_BACKLIGHT = 18;
     private static final int MAXIMUM_BACKLIGHT = 255;*//*
    //Anshika code
//    private static final int MINIMUM_BRIGHTNESS = 20;
//    private static final int MIDIUM_BRIGHTNESS = 100;
//    private static final int MAXIUM_BRIUGHTNESS = 255;
//    private AudioManager myAudioManager;
    int mod;

    //    boolean toManual;
//    public static final String MyPREFERENCES = "MyPrefs";
    private Boolean wifistatus = false;
    private static Camera camera;
    boolean toManual;
    public static final String MyPREFERENCES = "MyPrefs";
    private static final int MINIMUM_BACKLIGHT = 1;
    private static final int MIDIUM_BACKLIGHT = 150;
    private static final int MAXIMUM_BACKLIGHT = 255;
    int brightness;
    int mode;
    private AudioManager myAudioManager;*/

    @Override
    public  void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       /* for (int appWidgetId : appWidgetIds) {
            // updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
//TODO:


        context.startService(getIntentForService(context));



    }

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        context.stopService(getIntentForService(context));

        super.onDeleted(context, appWidgetIds);
    }
    //TODO:

    private Intent getIntentForService(Context context) {
        Intent widgetService = new Intent(context.getApplicationContext(), WifiWidgetService.class);
        return widgetService;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    /*@Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
        if (intent.getAction().equals("myOnClickTag1")) {
            // your onClick action is here
            Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");


            initNetworkInfo(context);

            if (wifistatus) {
                // Disable the WIFI

                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(false);

                //Update UI
            } else {
                // Enable the WIFI

                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                //Update UI
            }


        }
        //check action for aeroplan mode

        else if (intent.getAction().equals("myOnClickTag2")) {
            // your onClick action is here
            Toast.makeText(context, "Button2", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button2");

           RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);




          *//*  //Abhishek code
            boolean isEnabled = isAirplaneModeOn(context);
            Toast.makeText(context, "isEnabled", Toast.LENGTH_SHORT).show();
            if (isEnabled == true) {
                setSettings(context, isEnabled ? 1 : 0);
                Log.e("setSettings","setSettings");
                Toast.makeText(context, "Flight mode on", Toast.LENGTH_LONG).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
                Intent newIntent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
                newIntent.putExtra("state", false);
                context.sendBroadcast(newIntent);
            } else {
                setSettings(context, isEnabled ? 1 : 0);
                Toast.makeText(context, "Flight mode off", Toast.LENGTH_LONG).show();
            }
*//*

            Log.e("toggleAutoBrightness", "");
            toggleAutoBrightness(context, views);

        } else if (intent.getAction().equals("myOnClickTag3")) {
            RemoteViews  views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
            Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");
            myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            switch (myAudioManager.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT:

                    Log.i("MyApp", "Silent mode");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    *//*views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.high_volume);*//*

                    break;
                case AudioManager.RINGER_MODE_NORMAL:

                    Log.i("MyApp", "Normal mode");
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                   *//* views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.mute_volume);*//*
                    break;
            }

//            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = mInflater.inflate(R.layout.wifi_app_widget, null, false);
//
//            ImageView mRingtoneIV = (ImageView) view.findViewById(R.id.ringer_logo);

            int mod = myAudioManager.getRingerMode();
            Log.e("mod", mod + "");
            if (mod == AudioManager.RINGER_MODE_SILENT) {
                try {
                    views.setTextViewText(R.id.ringer_text,
                            context.getResources().getString(R.string.silent_mode));

                    views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.mute_volume);
                } catch (Exception e) {
                    Log.e("MyApp", e.getMessage());
                }

                *//*mod=2;*//*
            } else {
                try {
                    views.setTextViewText(R.id.ringer_text,
                            context.getResources().getString(R.string.normal_mode));
                    *//*views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.high_volume);*//*
                    views.setImageViewResource(R.id.ringer_logo,
                            R.mipmap.high_volume);
                } catch (Exception e) {

                }


               *//* mod=0;*//*
            }



        } else if (intent.getAction().equals("myOnClickTag5")) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
            Toast.makeText(context, "Button5", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button5");
            myAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            *//*switch (myAudioManager.getVibrateSetting(0)) {

                case AudioManager.VIBRATE_SETTING_ON:
                   // int setting = AudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER))==AudioManager.AudioManager.VIBRATE_SETTING_OFF));
                    myAudioManager.setVibrateSetting(AudioManager.VIBRATE_SETTING_ON,AudioManager.VIBRATE_SETTING_OFF);
                    break;
                case AudioManager.VIBRATE_SETTING_OFF:
                    myAudioManager.setVibrateSetting(AudioManager.VIBRATE_SETTING_OFF,AudioManager.VIBRATE_SETTING_ON);
                    break;
            }*//*

            // myAudioManager.
            int mod1 = (myAudioManager.getVibrateSetting(AudioManager.VIBRATE_SETTING_ON));
            if (mod1 == myAudioManager.getVibrateSetting(AudioManager.VIBRATE_SETTING_ON)) {
                myAudioManager.setVibrateSetting(AudioManager.VIBRATE_SETTING_ON, AudioManager.VIBRATE_SETTING_OFF);

            } else {
                myAudioManager.setVibrateSetting(AudioManager.VIBRATE_SETTING_OFF, AudioManager.VIBRATE_SETTING_OFF);
            }
            if (mod1 == AudioManager.VIBRATE_SETTING_ON) {
                views.setImageViewResource(R.id.vibration_mode_logo, R.mipmap.vibrate_mode_icon);
            } else {
                views.setImageViewResource(R.id.vibration_mode_logo, R.mipmap.vibrate_mode_off);
            }
        } else if (intent.getAction().equals("myOnClickTag7")) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            } else {
                mBluetoothAdapter.enable();
            }
            if (mBluetoothAdapter.isEnabled()) {
                views.setImageViewResource(R.id.bluetooth_logo, R.mipmap.bluetooth_icon);
            } else {
                views.setImageViewResource(R.id.bluetooth_logo, R.mipmap.bluetooth_off_icon);
            }
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), WIfiAppWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

        onUpdate(context, appWidgetManager, appWidgetIds);
    }
*/

  /*  private void initNetworkInfo(Context context) {
        // get connection info if available
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if ((info != null) && (info.isConnected())
                && (ConnectivityManager.TYPE_WIFI == info.getType())) {
            wifistatus = true;

        } else {
            wifistatus = false;

        }
    }*/

    //for setting the brightness

   /* private void toggleAutoBrightness(Context context, RemoteViews views) {

        int manualBrightnessLevel;
        final ContentResolver resolver = context.getContentResolver();

        // Get the current brightness mode.
       *//*mode = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, -1);*//*

        // Figure out which mode to switch to and pop up a toast.
       *//* toManual = false;
        if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
            toManual = true;
            mode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
            Log.i("BrightnessToggle", "Switching to manual");
            Toast.makeText(context, context.getResources().getString(R.string.toast_off), Toast.LENGTH_SHORT).show();
        } else if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
            mode = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
            Log.i("BrightnessToggle", "Switching to automatic");
            Toast.makeText(context, context.getResources().getString(R.string.toast_on), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Unknown mode: " + Integer.toString(mode), Toast.LENGTH_SHORT).show();
            Log.e("BrightnessToggle", "Unknown value for SCREEN_BRIGHTNESS_MODE: " + Integer.toString(mode));
            return;
        }*//*

        // Set the new SCREEN_BRIGHTNESS_MODE
        // Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE, mode);

        // Restore preferences: saved manual brightness
        SharedPreferences settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       *//* SharedPreferences settings = getPreferences(MODE_PRIVATE);*//*

        //Get the current system brightness
        // brightness = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        // manualBrightnessLevel = settings.getInt("manualBrightnessLevel", -1);

        try {
            brightness = android.provider.Settings.System.getInt(
                    context.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
            mode = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


        if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
            if (((brightness >= MINIMUM_BACKLIGHT) && (brightness < MIDIUM_BACKLIGHT))) {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MIDIUM_BACKLIGHT);
                views.setImageViewResource(R.id.brightness_logo,
                        R.mipmap.brightness_half);
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MIDIUM_BACKLIGHT) && (brightness < MAXIMUM_BACKLIGHT))) {

                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MAXIMUM_BACKLIGHT);
                views.setImageViewResource(R.id.brightness_logo,
                        R.mipmap.full_brightness);
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MAXIMUM_BACKLIGHT))) {
                mode = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                views.setImageViewResource(R.id.brightness_logo,
                        R.mipmap.full_brightness);
                Log.i("brightness", brightness + "");


            } else {

            }

        } else {
            mode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
            //setManualMode
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Log.i("BrightnessToggle", "Switching to manual");
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);
            views.setImageViewResource(R.id.brightness_logo,
                    R.mipmap.brightness_null);
        }


    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {

        Intent intent = new Intent(context, WIfiAppWidget.class);
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetIntentReceiver.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        intent.setAction(action);
//        registerReceiver()
        return PendingIntent.getBroadcast(context, 0, intent, 0);

    }

    protected PendingIntent getPendingSelfIntentRingtone(Context context, String action) {

        Intent intent1 = new Intent(context, WIfiAppWidget.class);
        intent1.setAction(action);
//        registerReceiver()
        return PendingIntent.getBroadcast(context, 0, intent1, 0);

    }

    private  boolean isMobileDataEnableOrNot(Context context){

        boolean mobileDataEnabled = false; // Assume disabled
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
           // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }*/

}