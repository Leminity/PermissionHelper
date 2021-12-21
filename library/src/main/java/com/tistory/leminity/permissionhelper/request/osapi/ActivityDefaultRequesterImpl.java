package com.tistory.leminity.permissionhelper.request.osapi;

import android.app.Activity;

import androidx.core.app.ActivityCompat;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.osapi.ActivityDefaultRequesterImpl<br/>
 * Description  : {@link android.app.Activity} 권한 요청 API 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class ActivityDefaultRequesterImpl extends AbstractDefaultRequester<Activity> {

    @Override
    protected Activity getActivity(Activity activity) {
        return activity;
    }

    @Override
    public void requestPermissionImpl(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}