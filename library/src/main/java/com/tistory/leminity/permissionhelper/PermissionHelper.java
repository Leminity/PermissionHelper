package com.tistory.leminity.permissionhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tistory.leminity.permissionhelper.request.OnCallbackShouldRational;
import com.tistory.leminity.permissionhelper.request.PermissionRequester;

import java.util.Map;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.PermissionHelper<br/>
 * Description  : 권한 요청 API<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class PermissionHelper {

    /**
     * 권한 획득 시나리오
     * 1. 요청 전 권한 확인
     * 2-1. 권한 있으면 실행
     * 2-2. 권한 없으면 권한 요청
     * 2-3. 권한이 항상 거부인 경우 Snackbar 출력(콜백 안되면 다른 컴포넌트로 교체해야 함.)
     * 2-4. 권한이 거부 상태인 경우 권한 요청
     * <p>
     * 3. 권한 허용/거부에 따른 로직 처리 및 캐시 해제
     */
    private Activity mActivity;
    private Fragment mFragment;
    private AppCompatActivity mAppcompatActivity;
    private androidx.fragment.app.Fragment mAppcompatFragment;

    ActivityResultLauncher<String[]> mActivityResultLauncher;

    private String[] mPermissions;
    private int mRequestCode;
    private Runnable mRunGranted;
    private OnCallbackShouldRational mRunShouldRational;
    private Runnable mRunDenied;
    private Runnable mRunDeniedAlways;

    private PermissionHelper(@NonNull String[] permissions,
                             int requestCode) {
        this.mPermissions = permissions;
        this.mRequestCode = requestCode;
    }

    private PermissionHelper(@NonNull Activity act,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(permissions, requestCode);
        this.mActivity = act;
    }

    private PermissionHelper(@NonNull Fragment fragment,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(permissions, requestCode);
        this.mFragment = fragment;
    }

    private PermissionHelper(@NonNull AppCompatActivity activity,
                             ActivityResultLauncher<String[]> activityResultLauncher,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(permissions, requestCode);
        this.mAppcompatActivity = activity;
        this.mActivityResultLauncher = activityResultLauncher;
    }

    private PermissionHelper(@NonNull androidx.fragment.app.Fragment fragment,
                             ActivityResultLauncher<String[]> activityResultLauncher,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(permissions, requestCode);
        this.mAppcompatFragment = fragment;
        this.mActivityResultLauncher = activityResultLauncher;
    }

    /**************************************************************************************************************************************
     * Create Instance
     **************************************************************************************************************************************/
    public static PermissionHelper requestPermission(@NonNull Activity act,
                                                     @NonNull String permission,
                                                     int requestCode) {
        return requestPermission(act, new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull Activity act,
                                                     @NonNull String[] permissions,
                                                     int requestCode) {
        return (new PermissionHelper(act, permissions, requestCode));
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static PermissionHelper requestPermission(@NonNull Fragment fragment,
                                                     @NonNull String permission,
                                                     int requestCode) {

        return requestPermission(fragment, new String[]{permission}, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static PermissionHelper requestPermission(@NonNull Fragment fragment,
                                                     @NonNull String[] permissions,
                                                     int requestCode) {

        return (new PermissionHelper(fragment, permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull AppCompatActivity act,
                                                     ActivityResultLauncher<String[]> activityResultLauncher,
                                                     @NonNull String permission,
                                                     int requestCode) {
        return requestPermission(act, activityResultLauncher, new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull AppCompatActivity act,
                                                     ActivityResultLauncher<String[]> activityResultLauncher,
                                                     @NonNull String[] permissions,
                                                     int requestCode) {
        return (new PermissionHelper(act, activityResultLauncher, permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull androidx.fragment.app.Fragment fragment,
                                                     ActivityResultLauncher<String[]> activityResultLauncher,
                                                     @NonNull String permission,
                                                     int requestCode) {

        return requestPermission(fragment, activityResultLauncher, new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull androidx.fragment.app.Fragment fragment,
                                                     ActivityResultLauncher<String[]> activityResultLauncher,
                                                     @NonNull String[] permissions,
                                                     int requestCode) {

        return (new PermissionHelper(fragment, activityResultLauncher, permissions, requestCode));
    }

    /**************************************************************************************************************************************
     * public api
     **************************************************************************************************************************************/
    public PermissionHelper setActionGranted(Runnable run) {
        this.mRunGranted = run;
        return this;
    }

    public PermissionHelper setActionDenied(Runnable run) {
        this.mRunDenied = run;
        return this;
    }

    public PermissionHelper setActionDeniedAlwayed(Runnable run) {
        mRunDeniedAlways = run;
        return this;
    }

    public PermissionHelper setActionShouldRational(OnCallbackShouldRational run) {
        mRunShouldRational = run;
        return this;
    }

    public void execute() {
        if (mActivity != null) {
            PermissionRequester.request(mActivity, mPermissions, mRequestCode, mRunGranted, mRunShouldRational, mRunDenied, mRunDeniedAlways);

        } else if (mFragment != null) {
            PermissionRequester.request(mFragment, mPermissions, mRequestCode, mRunGranted, mRunShouldRational, mRunDenied, mRunDeniedAlways);

        } else if (mAppcompatActivity != null) {
            PermissionRequester.request(mAppcompatActivity, mActivityResultLauncher, mPermissions, mRequestCode, mRunGranted, mRunShouldRational, mRunDenied, mRunDeniedAlways);

        } else { //AndroidX Fragment
            PermissionRequester.request(mAppcompatFragment, mActivityResultLauncher, mPermissions, mRequestCode, mRunGranted, mRunShouldRational, mRunDenied, mRunDeniedAlways);

        }
    }

    public static void callbackPermissionResult(Activity activity, int requestCode, int[] grantResult) {
        PermissionRequester.executeJob(activity, requestCode, grantResult);
    }

    public static void callbackPermissionResult(Fragment fragment, int requestCode, int[] grantResult) {
        PermissionRequester.executeJob(fragment, requestCode, grantResult);
    }

    public static void callbackPermissionResult(AppCompatActivity appcompatActivity, int requestCode, Map<String, Boolean> resultMap) {
        PermissionRequester.executeJob(appcompatActivity, requestCode, resultMap);
    }

    public static void callbackPermissionResult(androidx.fragment.app.Fragment fragment, int requestCode, Map<String, Boolean> resultMap) {
        PermissionRequester.executeJob(fragment, requestCode, resultMap);
    }

    public static void activityDestroyed(Activity activity) {
        PermissionRequester.removeAllJob(activity);
    }

    public static void fragmentDestroyed(Fragment fragment) {
        PermissionRequester.removeAllJob(fragment);
    }

    public static void activityDestroyed(AppCompatActivity appCompatActivity) {
        PermissionRequester.removeAllJob(appCompatActivity);
    }

    public static void fragmentDestroyed(androidx.fragment.app.Fragment fragment) {
        PermissionRequester.removeAllJob(fragment);
    }

}