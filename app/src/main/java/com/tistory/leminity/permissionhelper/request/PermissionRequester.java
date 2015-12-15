package com.tistory.leminity.permissionhelper.request;

import android.content.pm.PackageManager;

/**
 * Created by leminity on 2015-12-08.
 * <p/>
 * Class Name   : com.tistory.leminity.permissionhelper.request.PermissionRequester
 * Description  :
 * History
 * - 2015-12-08 : 최초작성
 */
public class PermissionRequester {

    public enum RequestOrigin {
        ACTIVITY,
        DEFAULT_FRAGMENT,
        SUPPORT_FRAGMENT
    }

    public static void request(RequestOrigin    origin,
                               Object           targetUICompnent,
                               String[]         permissions,
                               int              requestCode,
                               Runnable         runWhenAllow,
                               Runnable         runWhenDenied,
                               Runnable         runWhenDeniedAlways,
                               String           shouldRational) {
        AbstractRequester requester = null;
        switch (origin) {
            case ACTIVITY:
                requester = new ActivityRequesterImpl();
                break;
            case DEFAULT_FRAGMENT:
                requester = new FragmentRequesterImpl();
                break;
            case SUPPORT_FRAGMENT:
                requester = new SupportFragmentRequesterImpl();
                break;
            default:
                //not exist(because PermissionHelper api's not allow other case.
                break;
        }
        requester.execute(targetUICompnent, permissions, requestCode, runWhenAllow, runWhenDenied, runWhenDeniedAlways, shouldRational);
    }

    static boolean verifyPermissions(int[] grantResult) {
        int resultCnt = grantResult.length;

        if(resultCnt <= 0)
            return false;

        for (int i = 0; i < resultCnt; i++) {
            if(grantResult[i] != PackageManager.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    public static void executeJob(Object targetUIComponent, int requestCode, int[] grantResult) {
        boolean isGranted = verifyPermissions(grantResult);
        AbstractRequester.runJob(targetUIComponent, requestCode, isGranted);
    }

    public static void removeAllJob(Object targetUIComponent) {
        AbstractRequester.removeAllJob(targetUIComponent);
    }
}
