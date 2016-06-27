package vmokshagroup.com.wi_fi_on_off_widget;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncInfo;
import android.content.SyncStatusObserver;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInput;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

public class DialogBoxActivity extends Activity implements View.OnClickListener, SyncStatusObserver {
    RelativeLayout mRinger_relative, mBrightness_relative, mWi_fi_relative, mAiroplan_Rel, mFlash_relative, mGps_relative,
            mBluetooth_rel, mMobile_data_rel, mAuto_screen_rel, mScreenlock_rel, mAutoSynRel, mSleepRel;
    private static ImageView mRinger_logo, mBrightness_logo, mWifi_logo, mAeroplan_logo, mFlash_logo, mAutoSynLogo,
            mGps_log, mBluetooth_logo, mMobile_data_logo, mAuto_screen_logo, mScreen_lock_logo, mSleepLogo;
    private static AudioManager myAudioManager;
    private static TextView mRinger_text, mRinger_text1, mBrightness_text, mBrightness_text1, mWifi_text, mWifi_text1, mAiroplan_Text, mAiroplan_Text1, mFlash_text, mFlash_text1,
            mGpsText, mGpsText1, mBluetooth_text, mBluetooth_text1, mMobile_data_text, mMobile_data_text1,
            mAuto_screen_text, mAuto_screen_text1, mScreen_loc_text, mAutoSynText, mSleepModeText, mAutoSynText1;
    static int mod;
    static int brightness;
    static int brightnessMode;
    private static final String MyOnClick1 = "myOnClickTag1";
    private static final String MyOnClick3 = "myOnClickTag3";
    private static final String MyOnClick2 = "myOnClickTag2";
    private static final String MyOnClick4 = "MyOnClickTag4";
    private static final String MyOnClick5 = "myOnClickTag5";
    private static final String MyOnClick6 = "MyOnClickTag6";
    private static final String MyOnClick7 = "myOnClickTag7";
    private static final String MyOnClick8 = "myOnClickTag8";
    private static final String MyOnClick9 = "myOnClickTag9";

    //TODO:

    private static final String MyAutoScreenClick = "myOnAutoScreenTag";
    private static final String MyAutoSyncClick = "myOnAutoSyncTag";
    private static final String MySleepModeClick = "mySleepModeTag";


    public static Activity activity;
    private static final int MINIMUM_BACKLIGHT = 1;
    private static final int MIDIUM_BACKLIGHT = 127;
    private static final int MAXIMUM_BACKLIGHT = 255;
    public static boolean wifiConnected = false;
    public static boolean isBlutoothEnable = false;
    private Dialog mLockScreenDialog;

    static final String TAG = "DevicePolicyDemoActivity";
    static final int ACTIVATION_REQUEST = 47; // identifies our request id
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private static final String CONTENT_AUTHORITY = "com.example.authority";
    private static int timestamp = 30000;
    public static Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.activity_dialog_box);
        activity = this;
        initializeUi();

        UpdateUIStatus();


        //ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING, this);

        mRinger_relative.setOnClickListener(this);
        mBrightness_relative.setOnClickListener(this);
        mWi_fi_relative.setOnClickListener(this);
        mAiroplan_Rel.setOnClickListener(this);
        mFlash_relative.setOnClickListener(this);
        mGps_relative.setOnClickListener(this);
        mBluetooth_rel.setOnClickListener(this);
        mMobile_data_rel.setOnClickListener(this);
        mAuto_screen_rel.setOnClickListener(this);
        mScreenlock_rel.setOnClickListener(this);
        mAutoSynRel.setOnClickListener(this);
        mSleepRel.setOnClickListener(this);

        // Initialize Device Policy Manager service and our receiver class
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(this, PolicyAdmin.class);

        //TODO:for screen lock

        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //TODO:use the runithread if u have to update the ui


        Object handleObject = ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS, new SyncStatusObserver() {
            @Override
            public void onStatusChanged(final int i) {
                try {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (DialogBoxActivity.activity != null)
                                DialogBoxActivity.UpdateUIStatus();
                        }
                    });

                } catch (Exception e) {
                    Log.e("Sync Status Exception", e.toString());

                }
            }
        });


    }


    private void initializeUi() {
        mRinger_relative = (RelativeLayout) findViewById(R.id.ringer_relative);
        mBrightness_relative = (RelativeLayout) findViewById(R.id.brightness_relative);
        mWi_fi_relative = (RelativeLayout) findViewById(R.id.wi_fi_relative);
        mAiroplan_Rel = (RelativeLayout) findViewById(R.id.aeroplan_Rel);
        mFlash_relative = (RelativeLayout) findViewById(R.id.flash_relative);
        mGps_relative = (RelativeLayout) findViewById(R.id.gps_relative);
        mBluetooth_rel = (RelativeLayout) findViewById(R.id.bluetooth_rel);
        mMobile_data_rel = (RelativeLayout) findViewById(R.id.mobile_data_rel);

        //TODO:undone layout
        mAuto_screen_rel = (RelativeLayout) findViewById(R.id.auto_screen_rel);
        mScreenlock_rel = (RelativeLayout) findViewById(R.id.screenlock_rel);
        mAutoSynRel = (RelativeLayout) findViewById(R.id.auto_syn_rel);
        mSleepRel = (RelativeLayout) findViewById(R.id.sleep_rel);


        mRinger_logo = (ImageView) findViewById(R.id.ringer_logo);
        mBrightness_logo = (ImageView) findViewById(R.id.brightness_logo);
        mWifi_logo = (ImageView) findViewById(R.id.wifi_logo);
        mAeroplan_logo = (ImageView) findViewById(R.id.aeroplan_logo);
        mFlash_logo = (ImageView) findViewById(R.id.flash_logo);
        mGps_log = (ImageView) findViewById(R.id.gps_log);
        mBluetooth_logo = (ImageView) findViewById(R.id.bluetooth_logo);
        mMobile_data_logo = (ImageView) findViewById(R.id.mobile_data_logo);

        //TODO:
        mAuto_screen_logo = (ImageView) findViewById(R.id.auto_screen_logo);
        mScreen_lock_logo = (ImageView) findViewById(R.id.screen_lock_logo);
        mAutoSynLogo = (ImageView) findViewById(R.id.auto_syn_logo);
        mSleepLogo = (ImageView) findViewById(R.id.sleep_mode_logo);


        mRinger_text = (TextView) findViewById(R.id.ringer_text);
        mRinger_text1 = (TextView) findViewById(R.id.ringer_text1);

        mBrightness_text = (TextView) findViewById(R.id.brightness_text);
        mBrightness_text1 = (TextView) findViewById(R.id.brightness_text1);

        mWifi_text = (TextView) findViewById(R.id.wifi_text);
        mWifi_text1 = (TextView) findViewById(R.id.wifi_text1);

        mAiroplan_Text = (TextView) findViewById(R.id.aeroplan_text);
        mAiroplan_Text1 = (TextView) findViewById(R.id.aeroplan_text1);

        mFlash_text = (TextView) findViewById(R.id.flash_text);
        mFlash_text1 = (TextView) findViewById(R.id.flash_text1);

        mGpsText = (TextView) findViewById(R.id.gps_text);
        mGpsText1 = (TextView) findViewById(R.id.gps_text1);

        mBluetooth_text = (TextView) findViewById(R.id.bluetooth_text);
        mBluetooth_text1 = (TextView) findViewById(R.id.bluetooth_text1);

        mMobile_data_text = (TextView) findViewById(R.id.mobile_data_text);
        mMobile_data_text1 = (TextView) findViewById(R.id.mobile_data_text1);

        //TODO:

        mAuto_screen_text = (TextView) findViewById(R.id.auto_screen_text);
        mAuto_screen_text1 = (TextView) findViewById(R.id.auto_screen_text1);

        mScreen_loc_text = (TextView) findViewById(R.id.screen_loc_text);
        mAutoSynText = (TextView) findViewById(R.id.auto_syn_text);
        mAutoSynText1 = (TextView) findViewById(R.id.auto_syn_text1);

        mSleepModeText = (TextView) findViewById(R.id.sleep_mode_text);


        /*lockScreenDialog();*/
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.ringer_relative:

                IntentAction(this, MyOnClick3);
                break;

            case R.id.brightness_relative:
                IntentAction(this, MyOnClick2);
                break;

            case R.id.wi_fi_relative:
                IntentAction(this, MyOnClick1);
                break;

            case R.id.aeroplan_Rel:

                startActivity(new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS));
                UpdateAiroPlaneMode();

                break;
            case R.id.flash_relative:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!MainActivity.checkPermissionForCamera(activity)) {
                        // requestPermissionForCamera(context);
                        //checkPermissionForCamera(context);
                        // ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                        MainActivity.requestPermissionForCamera(activity);
                       // Toast.makeText(activity, "Permission Denied", Toast.LENGTH_SHORT).show();

                    } else {
                        IntentAction(this, MyOnClick5);
                    }
                } else {
                    IntentAction(this, MyOnClick5);
                }





                break;

            case R.id.gps_relative:

                Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsIntent);
                break;

            case R.id.bluetooth_rel:
                IntentAction(this, MyOnClick7);
                break;

            case R.id.mobile_data_rel:

               /* PendingIntent mobilependingIntent;
                Intent in = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(in);*/


                PendingIntent mobilependingIntent;
                Intent in = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(in);


                break;

            case R.id.auto_screen_rel:

                IntentAction(this, MyAutoScreenClick);
                getContentResolver().registerContentObserver(Settings.System.getUriFor
                                (Settings.System.ACCELEROMETER_ROTATION),
                        true, contentObserver);


                break;

            case R.id.screenlock_rel:

                mScreen_lock_logo.setImageResource(R.mipmap.phone_screen_lock);
                mScreen_loc_text.setVisibility(View.VISIBLE);
//TODO:check the lock screen administratoer is open or not,if open then lock the screen else navigate to LockScreenDialog Activity
                if (!devicePolicyManager.isAdminActive(demoDeviceAdmin)) {

                   /* Intent lockScrenDialog=new Intent(DialogBoxActivity.this,LockScreenDialog.class);
                    startActivity(lockScrenDialog);*/


                    Intent intent = new Intent(
                            DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                            demoDeviceAdmin);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Your boss told you to do this");
                    startActivityForResult(intent, ACTIVATION_REQUEST);


                } else {
                    devicePolicyManager.lockNow();
                }

                break;

            case R.id.auto_syn_rel:

                IntentAction(this, MyAutoSyncClick);

                Object handleObject1 = ContentResolver.addStatusChangeListener(ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE | ContentResolver.SYNC_OBSERVER_TYPE_PENDING, this);


                break;

            case R.id.sleep_rel:

                IntentAction(this, MySleepModeClick);
                break;

        }
    }


    protected void IntentAction(Context context, String action) {

        Intent intent1 = new Intent(this, MyWidgetIntentReceiver.class);
        intent1.setAction(action);
        sendBroadcast(intent1);

    }

    public static void UpdateUIStatus() {

        UpdateRingTone();
        UpdateBrightness();
        UpdateWifiStatus();
        UpdateAiroPlaneMode();
        UpdateFlashLight();
        GpsStatus();
        BluetoothStatus();
        MobileDataStatus();
        screenAutoRotation();
        autoSyncStatus();
        autosleepMode();
    }

    public static void UpdateRingTone() {
        myAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        mod = myAudioManager.getRingerMode();
        Log.e("mod", mod + "");
        if (mod == AudioManager.RINGER_MODE_NORMAL) {

            mRinger_logo.setImageResource(R.mipmap.volume_on);

            mRinger_text.setVisibility(View.VISIBLE);
            mRinger_text1.setVisibility(View.GONE);
        } else if (mod == AudioManager.RINGER_MODE_SILENT) {
                   /*(mod==AudioManager.RINGER_MODE_NORMAL)*/
            mRinger_logo.setImageResource(R.mipmap.volume_off);
            mRinger_text.setVisibility(View.GONE);
            mRinger_text1.setVisibility(View.VISIBLE);
        } else {
            mRinger_logo.setImageResource(R.mipmap.vibration_on);
            mRinger_text.setVisibility(View.VISIBLE);
            mRinger_text1.setVisibility(View.GONE);
        }
    }

    public static void UpdateBrightness() {

        try {
            brightness = android.provider.Settings.System.getInt(
                    activity.getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
            brightnessMode = android.provider.Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL) {
            if (((brightness >= MINIMUM_BACKLIGHT) && (brightness < MIDIUM_BACKLIGHT))) {
                mBrightness_logo.setImageResource(R.mipmap.brightness_low);

                mBrightness_text1.setVisibility(View.VISIBLE);
                mBrightness_text.setVisibility(View.GONE);
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MIDIUM_BACKLIGHT) && (brightness < MAXIMUM_BACKLIGHT))) {

                mBrightness_logo.setImageResource(R.mipmap.brightness_medium);

                mBrightness_text1.setVisibility(View.GONE);
                mBrightness_text.setVisibility(View.VISIBLE);
                Log.i("brightness", brightness + "");
            } else if (((brightness >= MAXIMUM_BACKLIGHT))) {
                brightnessMode = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
                // Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, MINIMUM_BACKLIGHT);

                mBrightness_logo.setImageResource(R.mipmap.brightness_high);
                mBrightness_text1.setVisibility(View.GONE);
                mBrightness_text.setVisibility(View.VISIBLE);
                Log.i("brightness", brightness + "");


            }
        } else {
            mBrightness_logo.setImageResource(R.mipmap.brightness_auto);
            mBrightness_text1.setVisibility(View.GONE);
            mBrightness_text.setVisibility(View.VISIBLE);
        }

    }

    public static void UpdateWifiStatus() {
        initNetworkInfo();
     /* Boolean isNetworkConnected=  isInternetAccessible();*/
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
      /* if(wifi.isWifiEnabled()) {
           Toast.makeText(activity,"isWifiEnabled",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(activity,"NoisWifiEnabled",Toast.LENGTH_SHORT).show();
       }*/



      /*  try {
            wifiConnected = new isNetworkConnected().execute().get();
        } catch (Exception e) {
            Log.e("wifiConnected ", e.getMessage().toString());
        }*/


        if (wifiConnected && wifi.isWifiEnabled()) {
            mWifi_logo.setImageResource(R.mipmap.wifi_on);
            mWifi_text.setVisibility(View.VISIBLE);
            mWifi_text1.setVisibility(View.GONE);

        } else if (!wifiConnected && wifi.isWifiEnabled()) {
            mWifi_logo.setImageResource(R.mipmap.wifi_not_con);
            mWifi_text.setVisibility(View.GONE);
            mWifi_text1.setVisibility(View.VISIBLE);

        } else {
            mWifi_logo.setImageResource(R.mipmap.wifi_off);
            mWifi_text.setVisibility(View.GONE);
            mWifi_text1.setVisibility(View.VISIBLE);
        }

    }

    public static void UpdateAiroPlaneMode() {


        boolean isEnabled = Settings.System.getInt(activity.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        Log.e("airoplane status before clicking", isEnabled + "");
        if (isEnabled) {
            mAeroplan_logo.setImageResource(R.mipmap.flight_mode_on);
            mAiroplan_Text1.setVisibility(View.GONE);
            mAiroplan_Text.setVisibility(View.VISIBLE);

        } else {
            mAeroplan_logo.setImageResource(R.mipmap.flight_mode_off);
            mAiroplan_Text1.setVisibility(View.VISIBLE);
            mAiroplan_Text.setVisibility(View.GONE);
        }
    }

    public static void UpdateFlashLight() {

        if (MyWidgetIntentReceiver.isFlashOn) {
            mFlash_logo.setImageResource(R.mipmap.flash_on);
            mFlash_text.setVisibility(View.VISIBLE);
            mFlash_text1.setVisibility(View.GONE);
        } else {
            mFlash_logo.setImageResource(R.mipmap.flash_off);
            mFlash_text1.setVisibility(View.VISIBLE);
            mFlash_text.setVisibility(View.GONE);
        }


    }

    public static void GpsStatus() {


        final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGps_log.setImageResource(R.mipmap.gps_off);
            mGpsText.setVisibility(View.GONE);
            mGpsText1.setVisibility(View.VISIBLE);

        } else {
            mGps_log.setImageResource(R.mipmap.gps_on);
            mGpsText.setVisibility(View.VISIBLE);
            mGpsText1.setVisibility(View.GONE);
        }


    }

    public static void BluetoothStatus() {

        findBlutoothStatus();

        if (isBlutoothEnable) {
            mBluetooth_logo.setImageResource(R.mipmap.blutooth_on);
            mBluetooth_text.setVisibility(View.VISIBLE);
            mBluetooth_text1.setVisibility(View.GONE);
        } else {
            mBluetooth_logo.setImageResource(R.mipmap.blutooth_off);
            mBluetooth_text.setVisibility(View.GONE);
            mBluetooth_text1.setVisibility(View.VISIBLE);
        }


    }

    public static void MobileDataStatus() {

        if (isMbileenable()) {
            mMobile_data_logo.setImageResource(R.mipmap.data_on);
            mMobile_data_text.setVisibility(View.VISIBLE);
            mMobile_data_text1.setVisibility(View.GONE);
        } else {
            mMobile_data_logo.setImageResource(R.mipmap.data_off);
            mMobile_data_text.setVisibility(View.GONE);
            mMobile_data_text1.setVisibility(View.VISIBLE);
        }

    }

    public static void initNetworkInfo() {
        // get connection info if available
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();


        if ((info != null) && (info.isConnected())
                && (ConnectivityManager.TYPE_WIFI == info.getType())) {
            wifiConnected = true;

        } else {
            wifiConnected = false;

        }
    }


    public static boolean isInternetAccessible() {
        // Check for network connections
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {


            return true;

        } else if (
                cm.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        cm.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {


            return false;
        }
        return false;
    }

//Call Asyntask for checking the internet connection

    public static class isNetworkConnected extends AsyncTask<Boolean, Void, Boolean> {
        public static WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
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


    public static void findBlutoothStatus() {

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

    public static boolean isMbileenable() {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        if (!isConnected) {
            return false;
        }
        return true;
    }


    public static void screenAutoRotation() {
        if (android.provider.Settings.System.getInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            mAuto_screen_logo.setImageResource(R.mipmap.screen_rotaion);
            mAuto_screen_text.setVisibility(View.VISIBLE);
            mAuto_screen_text1.setVisibility(View.GONE);
        } else {
            // android.provider.Settings.System.putInt(activity.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 1);
            // Toast.makeText(activity.getApplicationContext(), "Rotation ON", Toast.LENGTH_SHORT).show();
            mAuto_screen_logo.setImageResource(R.mipmap.screen_lock);
            mAuto_screen_text.setVisibility(View.GONE);
            mAuto_screen_text1.setVisibility(View.VISIBLE);
        }


    }


    public static void autoSyncStatus() {
        boolean isMasterSyncEnabled = ContentResolver.getMasterSyncAutomatically();


        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                AccountManager manager = AccountManager.get(activity);
                Account[] accounts = manager.getAccountsByType("com.google");
                String accountName = "";
                String accountType = "";
                for (Account account : accounts) {

                    if (!ContentResolver.isSyncActive(account, CONTENT_AUTHORITY) &&
                            !ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                        Log.d("TAG", "Sync finished, should refresh nao!!");
                    }
                }



            }
        });

        /*new SyncStatusObserver() {
            @Override
            public void onStatusChanged(final int which) {


                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // SyncInfo getCurrentSyncInfo
                        ContentResolver.getCurrentSync();

                        AccountManager manager = AccountManager.get(DialogBoxActivity.this);
                        Account[] accounts = manager.getAccountsByType("com.google");
                        String accountName = "";
                        String accountType = "";
                        for (Account account : accounts) {
                            accountName = account.name;
                            accountType = account.type;
                            break;
                        }

                        if (!ContentResolver.isSyncActive(account, CONTENT_AUTHORITY) &&
                                !ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                            Log.d("TAG", "Sync finished, should refresh nao!!");
                        }


                        if (i == ContentResolver.SYNC_OBSERVER_TYPE_PENDING) {
                            // 'Pending' state changed.

                            Toast.makeText(getApplicationContext(), "SYNC_OBSERVER_TYPE_PENDING Status", Toast.LENGTH_SHORT).show();
                            if (ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                                // There is now a pending sync.
                                Toast.makeText(getApplicationContext(), "Check for_PENDING Status", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), " There is no longer PENDING Status", Toast.LENGTH_SHORT).show();
                            }
                        } else if (i == ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE) {
                            Toast.makeText(getApplicationContext(), "SYNC_OBSERVER_TYPE_ACTIVEStatus", Toast.LENGTH_SHORT).show();
                            // 'Active' state changed.
                            if (ContentResolver.isSyncActive(account, CONTENT_AUTHORITY)) {
                                // There is now an active sync.
                                Toast.makeText(getApplicationContext(), "sync Status active", Toast.LENGTH_SHORT).show();
                            } else {
                                // There is no longer an active sync.
                                Toast.makeText(getApplicationContext(), "no longer an active sync", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                });

            }
        };*/





        if (isMasterSyncEnabled) {
            mAutoSynLogo.setImageResource(R.mipmap.auto_syn_on);
            mAutoSynText.setVisibility(View.VISIBLE);
            mAutoSynText1.setVisibility(View.GONE);
        } else {
            mAutoSynLogo.setImageResource(R.mipmap.auto_syn_off);
            mAutoSynText.setVisibility(View.GONE);
            mAutoSynText1.setVisibility(View.VISIBLE);
        }
    }

    public static void autosleepMode() {

        long SystemSleepTime = Settings.System.getLong(
                activity.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,
                -1);
        //TODO:update the UI here
        if (SystemSleepTime == 30000) {

            mSleepLogo.setImageResource(R.mipmap.half_sec_sleep);

            mSleepModeText.setVisibility(View.VISIBLE);


            //  Toast.makeText(activity.getApplicationContext(), "System is Sleeping for" +" "+ SystemSleepTime, Toast.LENGTH_SHORT).show();


        } else if (SystemSleepTime == 60000) {

            mSleepLogo.setImageResource(R.mipmap.sleep_60_sec);

            mSleepModeText.setVisibility(View.VISIBLE);
            // Toast.makeText(activity.getApplicationContext(), "System is Sleeping for" +" "+ SystemSleepTime, Toast.LENGTH_SHORT).show();
        } else if (SystemSleepTime == 120000) {

            mSleepLogo.setImageResource(R.mipmap.sleep_2m);
            // Toast.makeText(activity.getApplicationContext(), "System is Sleeping for" +" "+ SystemSleepTime, Toast.LENGTH_SHORT).show();
        } else if (SystemSleepTime == 300000) {
            mSleepLogo.setImageResource(R.mipmap.sleep_5m);
            // Toast.makeText(activity.getApplicationContext(), "System is Sleeping for" +" "+ SystemSleepTime, Toast.LENGTH_SHORT).show();
        }else{
            mSleepLogo.setImageResource(R.mipmap.default_sleep_icon);
        }
    }

    //Thead for sleep

   /* private class ThreadDemo extends Thread {




        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @Override
        public void run() {
            super.run();
            try {
                *//*Thread.sleep(15000);

                PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);

                manager.goToSleep();*//*

                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.screenBrightness = 1;
                getWindow().setAttributes(params);

            }catch(Exception e){
            }
        }
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case ACTIVATION_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, "Administration enabled!");

                } else {
                    Log.i(TAG, "Administration enable FAILED!");
                    //toggleButton.setChecked(false);
                }

                if (requestCode == 1 && resultCode == 0) {
                    String provider = Settings.Secure.getString(getContentResolver(), Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    String gpsStaus = Settings.Secure.getString(getContentResolver(), Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    String mobiledataSetting = Settings.Secure.getString(getContentResolver(), Settings.ACTION_DATA_ROAMING_SETTINGS);

                    if (provider.contentEquals(Settings.ACTION_AIRPLANE_MODE_SETTINGS)) {
                        UpdateAiroPlaneMode();
                    } else if (gpsStaus.contentEquals(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) {
                        GpsStatus();
                    } else if (mobiledataSetting.contentEquals(Settings.ACTION_DATA_ROAMING_SETTINGS)) {
                        MobileDataStatus();
                    }


                } else if (requestCode == 2) {
                    Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {


            if (DialogBoxActivity.activity != null)
                DialogBoxActivity.UpdateUIStatus();

        }


    };

    @Override
    public void onStatusChanged(final int i) {


        /*activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

               // SyncInfo getCurrentSyncInfo
                ContentResolver.getCurrentSync();

                AccountManager manager = AccountManager.get(DialogBoxActivity.this);
                Account[] accounts = manager.getAccountsByType("com.google");
                String accountName = "";
                String accountType = "";
                for (Account account : accounts) {
                    accountName = account.name;
                    accountType = account.type;

                    if (!ContentResolver.isSyncActive(account, CONTENT_AUTHORITY) &&
                            !ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                        Log.d("TAG", "Sync finished, should refresh nao!!");
                    }


                    if (i == ContentResolver.SYNC_OBSERVER_TYPE_PENDING) {
                        // 'Pending' state changed.

                        Toast.makeText(getApplicationContext(),"SYNC_OBSERVER_TYPE_PENDING Status",Toast.LENGTH_SHORT).show();
                        if (ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                            // There is now a pending sync.
                            Toast.makeText(getApplicationContext(),"Check for_PENDING Status",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext()," There is no longer PENDING Status",Toast.LENGTH_SHORT).show();
                        }
                    } else if (i == ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE) {
                        Toast.makeText(getApplicationContext(),"SYNC_OBSERVER_TYPE_ACTIVEStatus",Toast.LENGTH_SHORT).show();
                        // 'Active' state changed.
                        if (ContentResolver.isSyncActive(account, CONTENT_AUTHORITY)) {
                            // There is now an active sync.
                            Toast.makeText(getApplicationContext(),"sync Status active",Toast.LENGTH_SHORT).show();
                        } else {
                            // There is no longer an active sync.
                            Toast.makeText(getApplicationContext(),"no longer an active sync",Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                }



            }
        });
*/
        //TODO:for checking the syn pending status


    }


    @Override
    public void onResume() {
        super.onResume();
        // put your code here...

      /*  activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                AccountManager manager = AccountManager.get(activity);
                Account[] accounts = manager.getAccountsByType("com.google");
                String accountName = "";
                String accountType = "";
                for (Account account : accounts) {

                    if (!ContentResolver.isSyncActive(account, CONTENT_AUTHORITY) &&
                            !ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                        Log.d("TAG", "Sync finished, should refresh nao!!");
                    }
                }



            }
        });
*/


    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
       /* activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                AccountManager manager = AccountManager.get(activity);
                Account[] accounts = manager.getAccountsByType("com.google");
                String accountName = "";
                String accountType = "";
                for (Account account : accounts) {

                    if (!ContentResolver.isSyncActive(account, CONTENT_AUTHORITY) &&
                            !ContentResolver.isSyncPending(account, CONTENT_AUTHORITY)) {
                        Log.d("TAG", "Sync finished, should refresh nao!!");
                    }
                }



            }
        });*/

    }

    //Abhishek code
  /*  private static boolean isSyncActive(Account account, String authority) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return isSyncActiveHoneycomb(account, authority);
        } else {
            SyncInfo currentSync = ContentResolver.getCurrentSync();
            return currentSync != null && currentSync.account.equals(account) &&
                    currentSync.authority.equals(authority);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static boolean isSyncActiveHoneycomb(Account account, String authority) {
        for (SyncInfo syncInfo : ContentResolver.getCurrentSyncs()) {
            if (syncInfo.account.equals(account) &&
                    syncInfo.authority.equals(authority)) {
                return true;
            }
        }
        return false;
    }*/


}



