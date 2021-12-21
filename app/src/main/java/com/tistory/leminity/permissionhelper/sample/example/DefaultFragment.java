package com.tistory.leminity.permissionhelper.sample.example;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tistory.leminity.permissionhelper.PermissionHelper;
import com.tistory.leminity.permissionhelper.sample.R;
import com.tistory.leminity.permissionhelper.sample.SampleActivity;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.DefaultFragment<br/>
 * Description  : {@link Fragment}에서의 권한 요청 샘플 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class DefaultFragment extends Fragment {

    public DefaultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_request_permission_default_fragment).setOnClickListener((View v) -> {
            requestPermission();
        });
    }

    private void requestPermission() {
        int requestCode = 100;
        String[] permissions = {Manifest.permission.READ_CONTACTS};

        PermissionHelper
                .requestPermission(DefaultFragment.this, permissions, requestCode)
                .setActionGranted(() -> {
                    SampleActivity.showToast(getActivity(), "Granted");
                })
                .setActionDenied(() -> {
                    SampleActivity.showToast(getActivity(), "Denied");
                })
                .setActionDeniedAlwayed(() -> {
                    SampleActivity.showToast(getActivity(), "Denied Always");
                })
//                .setActionShouldRational((Runnable requestPermission, Runnable deniedPermission) -> {
//                    SampleActivity.showToast(getActivity(), "setActionShouldRational");
//                })
                .execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.callbackPermissionResult(DefaultFragment.this, requestCode, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PermissionHelper.fragmentDestroyed(DefaultFragment.this);
    }

}