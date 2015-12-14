package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by leminity on 2015-12-08.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.request.SupportFragmentRequesterImpl
 * Description  :
 * History
 * - 2015-12-08 : 최초작성
 */
class SupportFragmentRequesterImpl extends AbstractRequester<Fragment> {

    @Override
    Activity getActivity(Fragment fragment) {
        return fragment.getActivity();
    }

    @Override
    void requestPermissionImpl(Fragment fragment, String[] permissions, int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }

    @Override
    boolean shouldShowRequestPermissionsRationalImpl(Fragment fragment, String permission) {
        return (fragment.shouldShowRequestPermissionRationale(permission));
    }
}
