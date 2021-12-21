package com.tistory.leminity.permissionhelper.sample.example;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.tistory.leminity.permissionhelper.PermissionHelper;
import com.tistory.leminity.permissionhelper.sample.R;
import com.tistory.leminity.permissionhelper.sample.SampleActivity;

import java.util.Map;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.AndroidXActivity<br/>
 * Description  : {@link AppCompatActivity}에서의 권한 요청 샘플 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 작성<br/>
 * <p/>
 */
public class AndroidXActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 101;
    ActivityResultLauncher<String[]> mActivityResultLancher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> resultMap) -> {
                PermissionHelper.callbackPermissionResult(AndroidXActivity.this, REQUEST_CODE, resultMap);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androidx);

        findViewById(R.id.button_request_permission_androidx_activity).setOnClickListener((View v) -> {
            requestPermission();
        });
    }

    private void requestPermission() {
        String[] permissions = {Manifest.permission.CALL_PHONE};

        PermissionHelper
                .requestPermission(AndroidXActivity.this, mActivityResultLancher, permissions, REQUEST_CODE)
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

}