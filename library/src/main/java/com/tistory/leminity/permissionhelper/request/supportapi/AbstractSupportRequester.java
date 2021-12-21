package com.tistory.leminity.permissionhelper.request.supportapi;

import androidx.activity.result.ActivityResultLauncher;

import com.tistory.leminity.permissionhelper.job.JobManager;
import com.tistory.leminity.permissionhelper.request.AbstractRequester;
import com.tistory.leminity.permissionhelper.request.OnCallbackShouldRational;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.supportapi.AbstractSupportRequester<br/>
 * Description  : 서포트 라이브러리(AndroidX) API 기준 권한 요청 흐름 추상 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
abstract class AbstractSupportRequester<T extends Object> extends AbstractRequester<T> {

    public abstract void requestPermissionImpl(ActivityResultLauncher<String[]> activityResultLauncher,
                                               String[] permissions);

    public void execute(T t,
                        ActivityResultLauncher<String[]> activityResultLauncher,
                        String[] permissions,
                        int requestCode,
                        Runnable runWhenAllow,
                        Runnable runWhenDenied,
                        Runnable runWhenDeniedAlways,
                        OnCallbackShouldRational runWhenShouldRational) {

        //권한 있으면 그냥 동작 실행
        if (hasPermissions(getActivity(t), permissions)) {
            if (runWhenAllow != null)
                runWhenAllow.run();
            return;
        }

        //권한 없으면.. 불행의 시작
        requestPermission(t, activityResultLauncher, requestCode, permissions, runWhenAllow, runWhenDenied, runWhenDeniedAlways, runWhenShouldRational);
    }

    private void requestPermission(T t,
                                   ActivityResultLauncher<String[]> activityResultLauncher,
                                   final int requestCode,
                                   final String[] permissions,
                                   Runnable runAllow,
                                   Runnable runDenied,
                                   Runnable runDeniedAlways,
                                   OnCallbackShouldRational onCallbackShouldRational) {

        if (onCallbackShouldRational != null) {
            if (shouldShowRequestPermissionsRational(t, permissions)) { //거부
                Runnable retryRunnable = () -> {
                    requestPermissionImpl(activityResultLauncher, permissions);
                };

                callbackShouldRational(t, requestCode, onCallbackShouldRational, retryRunnable);
                return;
            }
        }

        JobManager jobManager = getJobManager();
        jobManager.addJob(t, permissions, requestCode, runAllow, runDenied, runDeniedAlways);
        requestPermissionImpl(activityResultLauncher, permissions);
    }

}
