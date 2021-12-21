package com.tistory.leminity.permissionhelper.request.supportapi;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.supportapi.SupportActivityRequesterImpl<br/>
 * Description  : {@link androidx.appcompat.app.AppCompatActivity} 권한 요청 API 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class SupportActivityRequesterImpl extends AbstractSupportRequester<AppCompatActivity> {

    @Override
    protected Activity getActivity(AppCompatActivity appCompatActivity) {
        return appCompatActivity;
    }

    @Override
    public void requestPermissionImpl(ActivityResultLauncher<String[]> activityResultLauncher,
                                      String[] permissions) {
        activityResultLauncher.launch(permissions);
    }
}
