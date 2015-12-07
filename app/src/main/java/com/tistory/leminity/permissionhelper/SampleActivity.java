package com.tistory.leminity.permissionhelper;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by leminity on 2015-12-07.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.SampleActivity
 * Description  : Sample usage PermissionHelper
 * History
 * - 2015-12-07 : 최초작성
 */
public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.buttonRequestPerm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Implements permission request.
                PermissionHelper
                        .requestPermission(SampleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 123)
                        //.requestPermission(SampleActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123)
                        .setActionGranted(new Runnable() {
                            @Override
                            public void run() {

                            }
                        })
                        .setActionDenied(new Runnable() {
                            @Override
                            public void run() {

                            }
                        })
                        //.setActionShouldRational()
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

}
