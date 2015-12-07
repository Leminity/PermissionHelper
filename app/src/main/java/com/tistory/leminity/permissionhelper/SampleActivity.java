package com.tistory.leminity.permissionhelper;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                                showToast("Permission granted");
                            }
                        })
                        .setActionDenied(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission denied");
                            }
                        })
                        .setActionShouldRational(R.string.message_read_external_storage)
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
