package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.tistory.leminity.permissionhelper.job.IPermissionResult;
import com.tistory.leminity.permissionhelper.request.osapi.ActivityDefaultRequesterImpl;
import com.tistory.leminity.permissionhelper.request.osapi.FragmentDefaultRequesterImpl;
import com.tistory.leminity.permissionhelper.request.supportapi.SupportActivityRequesterImpl;
import com.tistory.leminity.permissionhelper.request.supportapi.SupportFragmentRequesterImpl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.request.PermissionRequester<br/>
 * Description  : 권한 요청 API 구현<br/>
 * History<br/>
 * - 2021-12-20 : <br/>
 * <p/>
 */
public class PermissionRequester {

    public static void request(Activity activity,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways,
                               IPermissionResult iPermissionResult) {

        ActivityDefaultRequesterImpl requester = new ActivityDefaultRequesterImpl();
        requester.execute(activity,
                permissions,
                requestCode,
                runWhenAllow,
                runWhenDenied,
                runWhenDeniedAlways,
                iPermissionResult,
                runWhenShouldRational);
    }

    public static void request(Fragment fragment,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways,
                               IPermissionResult iPermissionResult) {

        FragmentDefaultRequesterImpl requester = new FragmentDefaultRequesterImpl();
        requester.execute(fragment,
                permissions,
                requestCode,
                runWhenAllow,
                runWhenDenied,
                runWhenDeniedAlways,
                iPermissionResult,
                runWhenShouldRational);
    }

    public static void request(AppCompatActivity appcompatActivity,
                               ActivityResultLauncher<String[]> activityResultLauncher,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways,
                               IPermissionResult iPermissionResult) {

        SupportActivityRequesterImpl requester = new SupportActivityRequesterImpl();
        requester.execute(appcompatActivity,
                activityResultLauncher,
                permissions,
                requestCode,
                runWhenAllow,
                runWhenDenied,
                runWhenDeniedAlways,
                iPermissionResult,
                runWhenShouldRational);
    }

    public static void request(androidx.fragment.app.Fragment fragment,
                               ActivityResultLauncher<String[]> activityResultLauncher,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways,
                               IPermissionResult iPermissionResult) {

        SupportFragmentRequesterImpl requester = new SupportFragmentRequesterImpl();
        requester.execute(fragment,
                activityResultLauncher,
                permissions,
                requestCode,
                runWhenAllow,
                runWhenDenied,
                runWhenDeniedAlways,
                iPermissionResult,
                runWhenShouldRational);
    }

    public static void executeJob(Object targetUIComponent, int requestCode, String[] permissions, int[] grantResults) {
        AbstractRequester.RunJob(targetUIComponent, requestCode, permissions, grantResults);
    }

    public static void executeJob(Object targetUIComponent, int requestCode, Map<String, Boolean> grantResultMap) {
        int resultSize = grantResultMap.size();
        String[] permissions = new String[resultSize];
        int[] grantResults = new int[resultSize];

        Set<String> keySet = grantResultMap.keySet();
        Iterator<String> iterator = keySet.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            permissions[i] = iterator.next();

            boolean granted = grantResultMap.get(permissions[i]);
            grantResults[i] = (granted ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED);
        }

        AbstractRequester.RunJob(targetUIComponent, requestCode, permissions, grantResults);
    }

    public static void removeAllJob(Object targetUIComponent) {
        AbstractRequester.RemoveAllJob(targetUIComponent);
    }
}
