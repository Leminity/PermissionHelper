package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;

/**
 * Created by leminity on 2015-12-08.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.request.ActivityRequester
 * Description  :
 * History
 * - 2015-12-08 : 최초작성
 */
class ActivityRequesterImpl extends AbstractRequester<Activity> {

    @Override
    Activity getActivity(Activity activity) {
        return activity;
    }

    @Override
    void requestPermissionImpl(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    @Override
    boolean shouldShowRequestPermissionsRationalImpl(Activity activity, String permission) {
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) != true);
    }
}