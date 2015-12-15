package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.tistory.leminity.permissionhelper.R;
import com.tistory.leminity.permissionhelper.job.JobItem;
import com.tistory.leminity.permissionhelper.job.JobManager;

/**
 * Created by leminity on 2015-12-08.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.request.PermissionRequester
 * Description  :
 * History
 * - 2015-12-08 : 최초작성
 */
abstract class AbstractRequester<T> {

    static final JobManager JOBMANAGER = new JobManager();

    abstract Activity   getActivity(T t);
    abstract void       requestPermissionImpl(T t, String[] permissions, int requestCode);
    abstract boolean    shouldShowRequestPermissionsRationalImpl(T t, String permission);

    final void execute(T t, String[] permissions, int requestCode, Runnable runWhenAllow, Runnable runWhenDenied, Runnable runWhenDeniedAlways, String shouldRational) {
        //권한 있으면 그냥 동작 실행
        if (hasPermissions(getActivity(t), permissions)) {
            if (runWhenAllow != null)
                runWhenAllow.run();
            return;
        }

        //권한 없으면.. 불행의 시작
        JOBMANAGER.addJob(t, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways);
        requestPermission(t, requestCode, permissions, shouldRational);
    }

    /**
     * 요청된 권한들이 허용되어 있는지 확인한다.
     *
     * @param ctx
     * @param permissions
     * @return <p/>
     * true  - 요청된 권한 목록이 전부 허용인 경우 <p>
     * false - 요청된 권한 목록 중 한개라도 미허용인 경우
     */
    private boolean hasPermissions(Context ctx, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if(ActivityCompat.checkSelfPermission(ctx, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermission(T t, final int requestCode, final String[] permissions, String shouldRational) {
        final T tTmp = t;

        if (shouldShowRequestPermissionsRational(t, permissions)) { //거부
            if(TextUtils.isEmpty(shouldRational) != true) {
                showShouldRationalSnackBar(t, shouldRational, requestCode, new Runnable() {
                    @Override
                    public void run() {
                        requestPermissionImpl(tTmp, permissions, requestCode);
                    }
                });
                return;
            }
        }
        requestPermissionImpl(t, permissions, requestCode);
    }

    private boolean shouldShowRequestPermissionsRational(T t, String[] permissions) {
        int permCnt = permissions.length;

        for (int i = 0; i < permCnt; i++) {
            if(shouldShowRequestPermissionsRationalImpl(t, permissions[i]))
                return true;
        }

        return false;
    }

    private void showShouldRationalSnackBar(final T t, String message, final int requestCode, final Runnable run) {
        Snackbar.make(getActivity(t).findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_label_request_permission, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (run != null)
                            run.run();
                    }
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);

                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
                            JOBMANAGER.removeJob(t, requestCode);
                    }
                })
                .show();
    }

    final static void runJob(Object targetUIComponent, int requestCode, boolean isAllowPermission) {
        JobItem jobItem = JOBMANAGER.removeJob(targetUIComponent, requestCode);
        if(jobItem == null)
            return;

        Runnable run = null;

        if(isAllowPermission) {
            run = jobItem.getRunWhenGranted();
        } else {
            if(isPermissionDeniedAlways(targetUIComponent, jobItem.getPermissions()) != true){
                run = jobItem.getRunWhenDenied();
            } else {
                run = jobItem.getRunWhenDeniedAlways();
            }
        }

        if(run != null)
            run.run();
    }

    private static boolean isPermissionDeniedAlways(Object targetUIComponent, String[] permissions) {
        Activity act = null;
        if(targetUIComponent instanceof Activity)
            act = (Activity)targetUIComponent;
        else if(targetUIComponent instanceof android.app.Fragment)
            act = ((android.app.Fragment)targetUIComponent).getActivity();
        else // (targetUIComponent instanceof android.support.v4.app.Fragment)
            act = ((android.support.v4.app.Fragment)targetUIComponent).getActivity();

        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(act, permissions[i]))
                return false;
        }

        return true;
    }

    final static void removeAllJob(Object targetUIComponent) {
        JOBMANAGER.removeAllJob(targetUIComponent);
    }
}
