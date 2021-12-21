package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.tistory.leminity.permissionhelper.job.JobItem;
import com.tistory.leminity.permissionhelper.job.JobManager;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.AbstractRequester<br/>
 * Description  : 컴포넌트 별 상이한 권한 요청 구현을 통합하기 위한 추상 클래스<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public abstract class AbstractRequester<T> {

    static final JobManager JobManager = new JobManager();

    protected abstract Activity getActivity(T t);

    protected JobManager getJobManager() {
        return JobManager;
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
    protected boolean hasPermissions(Context ctx, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if (ctx.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    protected void callbackShouldRational(final T t,
                                          final int requestCode,
                                          OnCallbackShouldRational onCallbackShouldRational,
                                          Runnable requestPermissionRunnable) {
        if (onCallbackShouldRational != null) {
            onCallbackShouldRational.onCallbackShouldRational(
                    () -> { //confirm
                        requestPermissionRunnable.run();
                    },
                    () -> {//denied
                        JobManager.removeJob(t, requestCode);
                    }
            );
            return;
        }

        JobManager.removeJob(t, requestCode);
    }

    private boolean shouldShowRequestPermissionsRational(T t, String permission) {
        Activity activity = getActivity(t);
        return (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) != true);
    }

    protected boolean shouldShowRequestPermissionsRational(T t, String[] permissions) {
        int permCnt = permissions.length;

        for (int i = 0; i < permCnt; i++) {
            if (shouldShowRequestPermissionsRational(t, permissions[i]))
                return true;
        }

        return false;
    }

    final static void RunJob(Object targetUIComponent, int requestCode, boolean isAllowPermission) {
        JobItem jobItem = JobManager.removeJob(targetUIComponent, requestCode);
        if (jobItem == null)
            return;

        Runnable run = null;

        if (isAllowPermission) {
            run = jobItem.getRunWhenGranted();
        } else {
            if (IsPermissionDeniedAlways(targetUIComponent, jobItem.getPermissions()) != true) {
                run = jobItem.getRunWhenDenied();
            } else {
                run = jobItem.getRunWhenDeniedAlways();
            }
        }

        if (run != null)
            run.run();
    }

    private static boolean IsPermissionDeniedAlways(Object targetUIComponent, String[] permissions) {
        Activity act = null;
        if (targetUIComponent instanceof Activity) {
            act = (Activity) targetUIComponent;

        } else if (targetUIComponent instanceof android.app.Fragment) {
            act = ((Fragment) targetUIComponent).getActivity();

        } else if (targetUIComponent instanceof AppCompatActivity) {
            act = (AppCompatActivity) targetUIComponent;

        } else {// (targetUIComponent instanceof androidx.fragment.app.Fragment)
            act = ((androidx.fragment.app.Fragment) targetUIComponent).getActivity();
        }

        return AbstractRequester.IsPermissionDeniedAlways(act, permissions);
    }

    protected static boolean IsPermissionDeniedAlways(Activity activity, String[] permissions) {
        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]))
                return false;
        }

        return true;
    }

    final static void RemoveAllJob(Object targetUIComponent) {
        JobManager.removeAllJob(targetUIComponent);
    }
}
