package com.tistory.leminity.permissionhelper.request;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

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
                               Runnable runWhenDeniedAlways) {
        ActivityDefaultRequesterImpl requester = new ActivityDefaultRequesterImpl();
        requester.execute(activity,
                permissions,
                requestCode,
                runWhenAllow, runWhenDenied, runWhenDeniedAlways, runWhenShouldRational);
    }

    public static void request(Fragment fragment,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways) {
        FragmentDefaultRequesterImpl requester = new FragmentDefaultRequesterImpl();
        requester.execute(fragment, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways, runWhenShouldRational);
    }

    public static void request(AppCompatActivity appcompatActivity,
                               ActivityResultLauncher<String[]> activityResultLauncher,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways) {
        SupportActivityRequesterImpl requester = new SupportActivityRequesterImpl();
        requester.execute(appcompatActivity, activityResultLauncher, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways, runWhenShouldRational);
    }

    public static void request(androidx.fragment.app.Fragment fragment,
                               ActivityResultLauncher<String[]> activityResultLauncher,
                               String[] permissions,
                               int requestCode,
                               Runnable runWhenAllow,
                               OnCallbackShouldRational runWhenShouldRational,
                               Runnable runWhenDenied,
                               Runnable runWhenDeniedAlways) {
        SupportFragmentRequesterImpl requester = new SupportFragmentRequesterImpl();
        requester.execute(fragment, activityResultLauncher, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways, runWhenShouldRational);
    }

    static boolean verifyPermissions(int[] grantResult) {
        int resultCnt = grantResult.length;

        if (resultCnt <= 0)
            return false;

        for (int i = 0; i < resultCnt; i++) {
            if (grantResult[i] != PackageManager.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    static boolean verifyPermissions(Map<String, Boolean> grantResult) {
        Set<String> set = grantResult.keySet();
        Iterator<String> iterator = set.iterator();

        while (iterator.hasNext()) {
            String permission = iterator.next();
            boolean grantPermission = grantResult.get(permission);

            if (!grantPermission) {
                return false;
            }
        }

        return true;
    }

    public static void executeJob(Object targetUIComponent, int requestCode, int[] grantResult) {
        boolean isGranted = verifyPermissions(grantResult);
        AbstractRequester.RunJob(targetUIComponent, requestCode, isGranted);
    }

    public static void executeJob(Object targetUIComponent, int requestCode, Map<String, Boolean> grantResult) {
        boolean isGranted = verifyPermissions(grantResult);
        AbstractRequester.RunJob(targetUIComponent, requestCode, isGranted);
    }

    public static void removeAllJob(Object targetUIComponent) {
        AbstractRequester.RemoveAllJob(targetUIComponent);
    }
}
