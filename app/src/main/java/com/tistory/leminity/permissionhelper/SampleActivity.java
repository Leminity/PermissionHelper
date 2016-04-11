package com.tistory.leminity.permissionhelper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tistory.leminity.permissionhelper.request.OnCallbackShouldRational;

/**
 * Created by leminity on 2015-12-07.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.SampleActivity
 * Description  : Sample usage PermissionHelper
 * History
 * - 2015-12-07 : 최초작성
 */
public class SampleActivity extends Activity {

    private static final String TAG = SampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        findViewById(R.id.buttonRequestPerm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper
                        .requestPermission(SampleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 123)
                        .setActionGranted(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission granted.");
                            }
                        })
                        .setActionDenied(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission denied.");
                            }
                        })
                        .setActionDeniedAlwayed(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission denied always.");
                            }
                        })
                        .setActionShouldRational(new OnCallbackShouldRational() {
                            @Override
                            public void onCallbackShouldRational(final Runnable requestPermission, final Runnable deniedPermission) {
                                AlertDialog alertDialog = new AlertDialog.Builder(SampleActivity.this)
                                        .setTitle("Test")
                                        .setMessage("Required permission for test.")
                                        .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermission.run();
                                            }
                                        })
                                        .setNegativeButton("Denied", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deniedPermission.run();
                                            }
                                        })
                                        .show();
                            }
                        })
                        .execute();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //must be called when callback onRequestPermissionsResult.
        PermissionHelper.callbackPermissionResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        //must be called when destroy activity.
        //If not can cause memory leak.
        PermissionHelper.activityDestroyed(this);
        super.onDestroy();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
