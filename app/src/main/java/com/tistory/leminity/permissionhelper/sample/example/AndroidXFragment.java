package com.tistory.leminity.permissionhelper.sample.example;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tistory.leminity.permissionhelper.PermissionHelper;
import com.tistory.leminity.permissionhelper.sample.R;
import com.tistory.leminity.permissionhelper.sample.SampleActivity;

import java.util.Map;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.AndroidXFragment<br/>
 * Description  : {@link Fragment}에서의 권한 요청 샘플 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class AndroidXFragment extends Fragment {

    private final int REQUEST_CODE = 102;

    ActivityResultLauncher<String[]> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> resultMap) -> {
                PermissionHelper.callbackPermissionResult(AndroidXFragment.this, REQUEST_CODE, resultMap);
            });

    public AndroidXFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_androidx, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_request_permission_androidx_fragment).setOnClickListener((View v) -> {
            requestPermission();
        });
    }

    private void requestPermission() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO};

        PermissionHelper
                .requestPermission(AndroidXFragment.this, mActivityResultLauncher, permissions, REQUEST_CODE)
                .setActionGranted(() -> {
                    SampleActivity.showToast(getContext(), "Granted");
                })
                .setActionDenied(() -> {
                    SampleActivity.showToast(getContext(), "Denied");
                })
                .setActionDeniedAlwayed(() -> {
                    SampleActivity.showToast(getContext(), "Denied Always");
                })
//                .setActionShouldRational((Runnable requestPermission, Runnable deniedPermission) -> {
//                    SampleActivity.showToast(getContext(), "setActionShouldRational");
//                })
                .execute();
    }
}