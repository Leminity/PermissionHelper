package com.tistory.leminity.permissionhelper.request.osapi;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.osapi.FragmentDefaultRequesterImpl<br/>
 * Description  : {@link android.app.Fragment} 권한 요청 API 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class FragmentDefaultRequesterImpl extends AbstractDefaultRequester<Fragment> {

    @Override
    protected Activity getActivity(Fragment fragment) {
        return fragment.getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void requestPermissionImpl(Fragment fragment, String[] permissions, int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }

}
