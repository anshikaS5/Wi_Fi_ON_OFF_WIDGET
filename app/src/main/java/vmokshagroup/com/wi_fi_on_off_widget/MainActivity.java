package vmokshagroup.com.wi_fi_on_off_widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private AudioManager myAudioManager;
    int isVibrateWhenRinging;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static Context mActivity;
    private static final int WRITE_PERMISSION_REQUEST = 5000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public  static Activity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        sharedPreferences = getSharedPreferences(ModelClass.WidgetPrefernce, Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!sharedPreferences.contains("flash_permission")) {

                requestPermissionForCamera(this);


            } else {
                if (checkSelfPermission(android.Manifest.permission.FLASHLIGHT) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionForCamera(this);
                }
            }
        }
       /* if (checkSelfPermission(android.Manifest.permission.FLASHLIGHT) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionForCamera(this);
        }*/
      /*  int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.FLASHLIGHT);
        if (result == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermissionForCamera(this);
        }*/



        /*mActivity = this;*/

     /*   requestPermissionForBrightness(this);*/


    }

    public static boolean checkPermissionForCamera(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public static void requestPermissionForCamera(Activity context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)) {
            Toast.makeText(context, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }


    }



    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    //TODO:

    public void requestPermissionForBrightness(Context context) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_SETTINGS)) {
            Toast.makeText(context, "brightness permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, WRITE_PERMISSION_REQUEST);
        }

    }

   /* private void showPermissionsDialog() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            try {
                int hasWriteSettingsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS);
                if (hasWriteSettingsPermission != PackageManager.PERMISSION_GRANTED) {
                    //You can skip the next if block. I use it to explain to user why I wan his permission.
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel("You need to allow write settings",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, WRITE_PERMISSION_REQUEST);
                                    }
                                });
                        return;
                    }
//The next line causes a dialog to popup, asking the user to allow or deny us write permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, WRITE_PERMISSION_REQUEST);
                    return;
                } else {
                    //Permissions have already been granted. Do whatever you want :)
                }
            }catch (Exception e){
                Log.e("brightness permiossion","");
            }
        }
    }*/

    //Now you only need this if you want to show the rationale behind
//requesting the permission.
   /* private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null).show();
    }*/

    //This method is called immediately after the user makes his decision to either allow
    // or disallow us permision.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //User pressed the allowed button
                    //Do what you want :)
                } else {
                    //User denied the permission
                    //Come up with how to hand the requested permission
                }
            case CAMERA_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();

                    editor = sharedPreferences.edit();
                    editor.putBoolean("flash_permission", true);
                    editor.commit();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
