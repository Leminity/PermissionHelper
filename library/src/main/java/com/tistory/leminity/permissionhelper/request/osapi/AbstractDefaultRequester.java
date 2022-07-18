package com.tistory.leminity.permissionhelper.request.osapi;


import com.tistory.leminity.permissionhelper.job.IPermissionResult;
import com.tistory.leminity.permissionhelper.job.JobManager;
import com.tistory.leminity.permissionhelper.request.AbstractRequester;
import com.tistory.leminity.permissionhelper.request.OnCallbackShouldRational;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.osapi.AbstractDefaultRequester<br/>
 * Description  : OS 내장 기본 API 기준 권한 요청 흐름 추상 구현<br/>
 * History<br/>
 * - 2021-12-20 : 최초 작성<br/>
 * <p/>
 */
abstract class AbstractDefaultRequester<T extends Object> extends AbstractRequester<T> {

    public abstract void requestPermissionImpl(T t, String[] permissions, int requestCode);

    public void execute(T t,
                        String[] permissions,
                        int requestCode,
                        Runnable runWhenAllow,
                        Runnable runWhenDenied,
                        Runnable runWhenDeniedAlways,
                        IPermissionResult iPermissionResult,
                        OnCallbackShouldRational runWhenShouldRational) {

        //권한 있으면 그냥 동작 실행
        if (hasPermissions(getActivity(t), permissions)) {
            if (runWhenAllow != null)
                runWhenAllow.run();
            return;
        }

        //권한 없으면.. 불행의 시작
        JobManager jobManager = getJobManager();
        jobManager.addJob(t, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways, iPermissionResult);
        requestPermission(t, requestCode, permissions, runWhenShouldRational);
    }

    private void requestPermission(T t,
                                   final int requestCode,
                                   final String[] permissions,
                                   OnCallbackShouldRational onCallbackShouldRational) {

        if (onCallbackShouldRational != null) {
            if (shouldShowRequestPermissionsRational(t, permissions)) { //거부
                Runnable retryRunnable = () -> {
                    requestPermissionImpl(t, permissions, requestCode);
                };

                callbackShouldRational(t, requestCode, onCallbackShouldRational, retryRunnable);
                return;
            }
        }

        requestPermissionImpl(t, permissions, requestCode);
    }
}
