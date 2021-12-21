package com.tistory.leminity.permissionhelper.request.supportapi;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.supportapi.SupportFragmentRequesterImpl<br/>
 * Description  : {@link androidx.fragment.app.Fragment} 권한 요청 API 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class SupportFragmentRequesterImpl extends AbstractSupportRequester<Fragment> {

    @Override
    protected Activity getActivity(Fragment fragment) {
        return fragment.getActivity();
    }

    @Override
    public void requestPermissionImpl(ActivityResultLauncher<String[]> activityResultLauncher,
                                      String[] permissions) {
        activityResultLauncher.launch(permissions);
    }
}
