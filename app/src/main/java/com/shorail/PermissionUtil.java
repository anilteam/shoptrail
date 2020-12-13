package com.shorail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PermissionUtil {

    public static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    public static boolean isPermissionGranted(int grantResult) {
        return (grantResult == PackageManager.PERMISSION_GRANTED);
    }
/*
    public static  boolean checkPhoneState(Context context){
        if(shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        }else{
            return true;
        }

    }

    public static void askPhoneStatePermission(final Activity context){
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1000);
    }*/

    public static boolean checkCameraPermission(Context context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askCameraStatePermission(final Activity context) {
        ActivityCompat.requestPermissions(context,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);

    }

    public static void askCameraStatePermission(final Activity context, int reqCode) {
        ActivityCompat.requestPermissions(context,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqCode);

    }


    public static boolean checkStoragePermission(Context context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askStorageStatePermission(final Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
    }

    public static void askStorageStatePermission(final Activity context, int reqCode) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, reqCode);
    }


    public static boolean checkAudioPermission(Context context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askAudioPermission(final Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
    }

    public static boolean checkContactPermission(Context context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askContactPermission(final Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_CONTACTS}, 1000);
    }

    public static boolean checkLocationPermission(Context context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askLocationPermission(final Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);

    }

    public static boolean checkVideoPermission(Activity context) {
        if (shouldAskPermission()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    public static void askVideoPermission(final Activity context) {
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
    }


    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public final static int PERMISSION_CAMERA = 1;
    public final static int PERMISSION_STORAGE = 2;
    public final static int PERMISSION_AUDIO = 3;
    public final static int PERMISSION_CONTACT = 4;


}
