package com.tistory.leminity.permissionhelper.sample.example;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.tistory.leminity.permissionhelper.PermissionHelper;
import com.tistory.leminity.permissionhelper.sample.R;
import com.tistory.leminity.permissionhelper.sample.SampleActivity;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.DefaultActivity<br/>
 * Description  : {@link Activity}에서의 권한 요청 샘플 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class DefaultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        findViewById(R.id.button_request_permission_default_activity).setOnClickListener((View v) -> {
            requestPermission();
        });
    }

    private void requestPermission() {
        int requestCode = 100;
        String[] permissions = {Manifest.permission.READ_CALENDAR};

        PermissionHelper
                .requestPermission(DefaultActivity.this, permissions, requestCode)
                .setActionGranted(() -> {
                    SampleActivity.showToast(getApplicationContext(), "Granted");
                })
                .setActionDenied(() -> {
                    SampleActivity.showToast(getApplicationContext(), "Denied");
                })
                .setActionDeniedAlwayed(() -> {
                    SampleActivity.showToast(getApplicationContext(), "Denied Always");
                })
//                .setActionShouldRational((Runnable requestPermission, Runnable deniedPermission) -> {
//                    SampleActivity.showToast(getApplicationContext(), "setActionShouldRational");
//                })
                .execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.callbackPermissionResult(DefaultActivity.this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionHelper.activityDestroyed(DefaultActivity.this);
    }
}