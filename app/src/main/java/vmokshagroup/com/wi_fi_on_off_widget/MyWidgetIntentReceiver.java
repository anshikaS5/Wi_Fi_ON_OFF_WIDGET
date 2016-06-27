package vmokshagroup.com.wi_fi_on_off_widget;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Policy;

public class MyWidgetIntentReceiver extends BroadcastReceiver {
    private Boolean wifistatus = false;
    private Boolean isBlutoothEnable = false;

    static Camera camera = null;
    static Camera.Parameters params;
    boolean toManual;
    public static final String MyPREFERENCES = "MyPrefs";
    private static final int MINIMUM_BACKLIGHT = 1;
    private static final int MIDIUM_BACKLIGHT = 127;
    private static final int MAXIMUM_BACKLIGHT = 255;
    int brightness;
    int mode;
    private AudioManager myAudioManager;
    public static boolean isBtClicked;
   /* Camera.Parameters p;
    Camera cam;*/

    public static boolean isFlashOn;
    private boolean hasFlash;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int timestamp = 30000;
    long SystemSleepTime;
    private static WifiManager wifi;
    public static int CAMERA_PERMISSION_REQUEST_CODE = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        isBtClicked = false;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
        if (intent.getAction().equals("myOnClickTag1")) {
            // your onClick action is here
            // Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button1");


            initNetworkInfo(context);
            wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

          /*  try {
                wifistatus = new isNetworkConnected().execute().get();
            } catch (Exception e) {
                Log.e("wifiConnected ", e.getMessage().toString());
            }*/
            if (wifistatus && wifi.isWifiEnabled()) {
                // Disable the WIFI

               /* WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);*/
                wifi.setWifiEnabled(false);

                //Update UI
            } else if (!wifistatus && wifi.isWifiEnabled()) {

                wifi.setWifiEnabled(false);
            } else {
                wifi.setWifiEnabled(true);
            }

            // Enable the WIFI

              /*  WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);*/

            //Update UI


            AppWidgetManager widgetManager = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds = widgetManager.getAppWidgetIds(widgetComponent);
            WIfiAppWidget wIfiAppWidget = new WIfiAppWidget();
            wIfiAppWidget.onUpdate(context, widgetManager, appWidgetIds);

            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();


        }
        //check action for aeroplan mode

        else if (intent.getAction().equals("myOnClickTag2")) {
            // your onClick action is here
            // Toast.makeText(context, "Button2", Toast.LENGTH_SHORT).show();
            Log.w("Widget", "Clicked button2");

            views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /*if (!MainActivity.checkPermissionForBrightness(context)) {
                    //requestPermissionForCamera(context);
                    requestPermissionForBritness(context);
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

                } else {
                    toggleAutoBrightness(context, views);
                }*/

                if (!android.provider.Settings.System.canWrite(context)) {
                    requestPermissionForBritness(context);
                } else {
                    toggleAutoBrightness(context, views);
                }

            } else {
                toggleAutoBrightness(context, views);
            }


            AppWidgetManager widgetManager = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds = widgetManager.getAppWidgetIds(widgetComponent);
            WIfiAppWidget wIfiAppWidget = new WIfiAppWidget();
            wIfiAppWidget.onUpdate(context, widgetManager, appWidgetIds);

            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();


        } else if (intent.getAction().equals("myOnClickTag3")) {
            views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
            // Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
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


            // WIfiAppWidget.onUpdate(context, appWidgetManager, appWidgetIds);

            //TODO:
            AppWidgetManager widgetManager = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds = widgetManager.getAppWidgetIds(widgetComponent);
            WIfiAppWidget wIfiAppWidget = new WIfiAppWidget();
            wIfiAppWidget.onUpdate(context, widgetManager, appWidgetIds);

            Log.e("WifiWidget", "Failed to update widget");


            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();

        } else if (intent.getAction().equals("myOnClickTag5")) {
            views = new RemoteViews(context.getPackageName(), R.layout.wifi_app_widget);
            Boolean hasFlash = context.getPackageManager()
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if (!hasFlash) {
                Toast.makeText(context, "Sorry,your device does not support flash light", Toast.LENGTH_LONG).show();
            } else {
                getCamera(context);

               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!MainActivity.checkPermissionForCamera(context)) {
                        // requestPermissionForCamera(context);
                        //checkPermissionForCamera(context);
                       // ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);

                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

                    } else {
                        getCamera(context);
                    }
                } else {
                    getCamera(context);
                }
*/
            }


            AppWidgetManager widgetManager1 = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent1 = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds1 = widgetManager1.getAppWidgetIds(widgetComponent1);
            WIfiAppWidget wIfiAppWidget1 = new WIfiAppWidget();
            wIfiAppWidget1.onUpdate(context, widgetManager1, appWidgetIds1);

            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();


        } else if (intent.getAction().equals("myOnClickTag7")) {
            findBlutoothStatus(context);
            isBtClicked = true;
            if (isBlutoothEnable) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                mBluetoothAdapter.disable();


            } else {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                mBluetoothAdapter.enable();
                while (!findBlutoothStatus()) {
                    Log.i("", "");
                }
            }


            AppWidgetManager widgetManager1 = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent1 = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds1 = widgetManager1.getAppWidgetIds(widgetComponent1);
            WIfiAppWidget wIfiAppWidget1 = new WIfiAppWidget();
            wIfiAppWidget1.onUpdate(context, widgetManager1, appWidgetIds1);

            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();

        } else if (intent.getAction().equals("myOnClickTag9")) {
            Intent dialogIntent = new Intent(context, DialogBoxActivity.class);

// Old activities shouldn't be in the history stack
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0,
                    dialogIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            context.startActivity(dialogIntent);


        } else if (intent.getAction().equals("myOnAutoScreenTag")) {


            if (android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
                android.provider.Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
                Toast.makeText(context, "Rotation OFF", Toast.LENGTH_SHORT).show();
            } else {
                android.provider.Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
                Toast.makeText(context, "Rotation ON", Toast.LENGTH_SHORT).show();
            }


            AppWidgetManager widgetManager1 = AppWidgetManager
                    .getInstance(context);
            ComponentName widgetComponent1 = new ComponentName(context.getPackageName(),
                    WIfiAppWidget.class.getName());
            int[] appWidgetIds1 = widgetManager1.getAppWidgetIds(widgetComponent1);
            WIfiAppWidget wIfiAppWidget1 = new WIfiAppWidget();
            wIfiAppWidget1.onUpdate(context, widgetManager1, appWidgetIds1);

            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();

        } else if (intent.getAction().equals("myOnAutoSyncTag")) {


            boolean isMasterSyncEnabled = ContentResolver.getMasterSyncAutomatically();
            if (isMasterSyncEnabled) {
                ContentResolver.setMasterSyncAutomatically(false);
            } else {
                ContentResolver.setMasterSyncAutomatically(true);
            }
            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();
        } else if (intent.getAction().equals("mySleepModeTag")) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (!android.provider.Settings.System.canWrite(context)) {
                    requestPermissionForBritness(context);
                } else {


                    sleepModeFunctionality(context);
                }

            } else {
                sleepModeFunctionality(context);
            }



















           /* SystemSleepTime = Settings.System.getLong(
                    context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
                    -1);
            if (SystemSleepTime == 30000) {

                // Log.e("timestamp",timestamp+"");
                Toast.makeText(context, "sleep after 60 sec", Toast.LENGTH_SHORT).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 60000);
                // timestamp = 60000;
            } else if (SystemSleepTime == 60000) {
                // Log.e("timestamp",timestamp+"");
                Toast.makeText(context, "sleep after 2 min", Toast.LENGTH_SHORT).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 120000);
                // timestamp = 120000;
            } else if (SystemSleepTime == 120000) {
                // Log.e("timestamp",timestamp+"");
                Toast.makeText(context.getApplicationContext(), "sleep after 5 min", Toast.LENGTH_SHORT).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 300000);
                //  timestamp = 180000;
            } else if (SystemSleepTime == 300000) {
                // Log.e("timestamp",timestamp+"");
                Toast.makeText(context.getApplicationContext(), "sleep after 30 sec", Toast.LENGTH_SHORT).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
                // timestamp = 30000;
            }else{
                Toast.makeText(context.getApplicationContext(), "sleep after 30 sec", Toast.LENGTH_SHORT).show();
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
            }
            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();*/
        }


    }

    private void requestPermissionForBritness(Context context) {

        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }

    private boolean findBlutoothStatus() {
       /* BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();*/
       /* Log.e("bluetooth status", bluetooth.isEnabled() + "");
        Boolean isBlutioothEnable=bluetooth.isEnabled();*/
        boolean isOn = false;
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                isOn = false;

            } else {
                isOn = true;
            }
        }
        return isOn;
    }

    private void initNetworkInfo(Context context) {
        // get connection info if available
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if ((info != null) && (info.isConnected())
                && (ConnectivityManager.TYPE_WIFI == info.getType())) {
            wifistatus = true;

        } else {
            wifistatus = false;

        }
    }


    private void findBlutoothStatus(Context context) {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
    //for setting the brightness

    private void toggleAutoBrightness(Context context, RemoteViews views) {
        final ContentResolver resolver = context.getContentResolver();

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
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MIDIUM_BACKLIGHT) && (brightness < MAXIMUM_BACKLIGHT))) {
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MAXIMUM_BACKLIGHT);
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MAXIMUM_BACKLIGHT))) {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);

                Log.i("brightness", brightness + "");
            }
        } else {
            mode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Log.i("BrightnessToggle", "Switching to manual");
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);
        }


    }


    private void sleepModeFunctionality(Context context) {

        SystemSleepTime = Settings.System.getLong(
                context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
                -1);
        if (SystemSleepTime == 30000) {

            // Log.e("timestamp",timestamp+"");
            Toast.makeText(context, "sleep after 60 sec", Toast.LENGTH_SHORT).show();
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 60000);
            // timestamp = 60000;
        } else if (SystemSleepTime == 60000) {
            // Log.e("timestamp",timestamp+"");
            Toast.makeText(context, "sleep after 2 min", Toast.LENGTH_SHORT).show();
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 120000);
            // timestamp = 120000;
        } else if (SystemSleepTime == 120000) {
            // Log.e("timestamp",timestamp+"");
            Toast.makeText(context.getApplicationContext(), "sleep after 5 min", Toast.LENGTH_SHORT).show();
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 300000);
            //  timestamp = 180000;
        } else if (SystemSleepTime == 300000) {
            // Log.e("timestamp",timestamp+"");
            Toast.makeText(context.getApplicationContext(), "sleep after 30 sec", Toast.LENGTH_SHORT).show();
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
            // timestamp = 30000;
        } else {
            Toast.makeText(context.getApplicationContext(), "sleep after 30 sec", Toast.LENGTH_SHORT).show();
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30000);
        }
        if (DialogBoxActivity.activity != null)
            DialogBoxActivity.UpdateUIStatus();


    }


    private void getCamera(Context context) {


        if (camera == null) {
            try {

                   /* camera = Camera.open();
                    params = camera.getParameters();*/
                camera = Camera.open();
                params = camera.getParameters();



               /* params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.startPreview();*/


                turnOnFlash();


                AppWidgetManager widgetManager = AppWidgetManager
                        .getInstance(context);
                ComponentName widgetComponent = new ComponentName(context.getPackageName(),
                        WIfiAppWidget.class.getName());
                int[] appWidgetIds = widgetManager.getAppWidgetIds(widgetComponent);
                WIfiAppWidget wIfiAppWidget = new WIfiAppWidget();
                wIfiAppWidget.onUpdate(context, widgetManager, appWidgetIds);

            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        } else {
            try {
               /* camera = Camera.open();
                params = camera.getParameters();*/

                turnOffFlash();


                AppWidgetManager widgetManager = AppWidgetManager
                        .getInstance(context);
                ComponentName widgetComponent = new ComponentName(context.getPackageName(),
                        WIfiAppWidget.class.getName());
                int[] appWidgetIds = widgetManager.getAppWidgetIds(widgetComponent);
                WIfiAppWidget wIfiAppWidget = new WIfiAppWidget();
                wIfiAppWidget.onUpdate(context, widgetManager, appWidgetIds);
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    private void turnOffFlash() {


       /* getCamera();*/
        params = camera.getParameters();

        if (params.getFlashMode().equals("torch")) {
            try {
                /*if (camera != null) {
                    return;
                }*/
           /* || params == null*/
                // play sound
                // playSound();

                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                camera.setParameters(params);
                camera.stopPreview();
                camera.release();
                camera = null;
                isFlashOn = false;

                // changing button/switch image
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("isFlashOn", isFlashOn + "");
            }
        }
    }

    private void turnOnFlash() {
       /* getCamera();*/
       /* params = camera.getParameters();
        if (!params.getFlashMode().equals("torch")) {*/
        try {
            if (camera == null) {
                return;
            }
            // play sound
            // playSound();

            //TODO: The camera needs a surface to cling to in order to open the
            /*Flashlight..however SurfaceView cannot be applied to a widget. So this is what you need to.....

            Add this to your turnFlashOn code:*/

            // TODO:
           /* try {
                mCamera.setPreviewTexture(new SurfaceTexture(0));
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            ;
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();

            isFlashOn = true;

            // changing button/switch image

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("isFlashOn", isFlashOn + "");
        }

       /* } else {
            turnOffFlash();
        }*/
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
