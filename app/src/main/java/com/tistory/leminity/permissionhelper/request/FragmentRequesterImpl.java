package com.tistory.leminity.permissionhelper.request;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

/**
 * Created by leminity on 2015-12-08.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.request.FragmentRequesterImpl
 * Description  :
 * History
 * - 2015-12-08 : 최초작성
 */
class FragmentRequesterImpl extends AbstractRequester<Fragment>{

    @Override
    Activity getActivity(Fragment fragment) {
        return fragment.getActivity();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    void requestPermissionImpl(Fragment fragment, String[] permissions, int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    boolean shouldShowRequestPermissionsRationalImpl(Fragment fragment, String permission) {
        return fragment.shouldShowRequestPermissionRationale(permission);
    }
}
