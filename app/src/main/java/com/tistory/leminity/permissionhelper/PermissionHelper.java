package com.tistory.leminity.permissionhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.tistory.leminity.permissionhelper.request.PermissionRequester;
import com.tistory.leminity.permissionhelper.request.PermissionRequester.RequestOrigin;

/**
 * Created by leminity on 2015-12-02.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.PermissionUtils
 * Description  : 왜였을까? 천하의 구글인데 왜 이렇게 거지같이 API를 설계했을까?
 *                구글 정도면 이유가 있겠지... 근데 난 잘 모르겠다. 라고 생각하며 분노로 가득찬 상태로 만들어진 권한 체크 및 로직 실행 유틸
 * History
 * - 2015-12-02 : 최초작성
 */
public class PermissionHelper {

    /**
     * 권한 획득 시나리오
     * 1. 요청 전 권한 확인
     * 2-1. 권한 있으면 실행
     * 2-2. 권한 없으면 권한 요청
     * 2-3. 권한이 항상 거부인 경우 Snackbar 출력(콜백 안되면 다른 컴포넌트로 교체해야 함.)
     * 2-4. 권한이 거부 상태인 경우 권한 요청
     *
     * 3. 권한 허용/거부에 따른 로직 처리 및 캐시 해제
     *
     */
    private Context                         mCtx;
    private RequestOrigin                   mRequestOrigin;

    private Object                          mTargetUICompnent; //activity, fragment, support-fragment
    private String[]                        mPermissions;
    private int                             mRequestCode;
    private Runnable                        mRunGranted;
    private Runnable                        mRunDenied;
    private Runnable mRunDeniedAlways;
    private String                          mShouldRational;

    private PermissionHelper(Context ctx, @NonNull String[] permissions, int requestCode) {
        this.mCtx         = ctx;
        this.mPermissions = permissions;
        this.mRequestCode = requestCode;
    }

    private PermissionHelper(@NonNull Activity act,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(act.getBaseContext(), permissions, requestCode);
        this.mTargetUICompnent = act;
        mRequestOrigin = RequestOrigin.ACTIVITY;
    }

    private PermissionHelper(@NonNull android.app.Fragment fragment,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(fragment.getActivity().getBaseContext(), permissions, requestCode);
        this.mTargetUICompnent = fragment;
        mRequestOrigin = RequestOrigin.DEFAULT_FRAGMENT;
    }

    private PermissionHelper(@NonNull android.support.v4.app.Fragment fragment,
                             @NonNull String[] permissions,
                             int requestCode) {
        this(fragment.getContext(), permissions, requestCode);
        this.mTargetUICompnent = fragment;
        mRequestOrigin = RequestOrigin.SUPPORT_FRAGMENT;
    }

    /**************************************************************************************************************************************
     * Create Instance
     **************************************************************************************************************************************/
    @TargetApi(Build.VERSION_CODES.M)
    public static PermissionHelper requestPermission(@NonNull android.app.Fragment fragment,@NonNull String permission, int requestCode) {
        return requestPermission(fragment, new String[]{permission}, requestCode);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static PermissionHelper requestPermission(@NonNull android.app.Fragment fragment,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(fragment, permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull android.support.v4.app.Fragment fragment,@NonNull String permission, int requestCode) {
        return requestPermission(fragment, new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull android.support.v4.app.Fragment fragment,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(fragment, permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull Activity act,@NonNull String permission, int requestCode) {
        return requestPermission(act, new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull Activity act,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(act, permissions, requestCode));
    }

    /**************************************************************************************************************************************
     * public api
     **************************************************************************************************************************************/
    public PermissionHelper setActionGranted(Runnable run){
        this.mRunGranted = run;
        return this;
    }

    public PermissionHelper setActionDenied(Runnable run){
        this.mRunDenied = run;
        return this;
    }

    public PermissionHelper setActionDeniedAlwayed(Runnable run) {
        mRunDeniedAlways = run;
        return this;
    }

    public PermissionHelper setActionShouldRational(int stringResourceId) {
        this.mShouldRational = mCtx.getString(stringResourceId);
        return this;
    }

    public void execute() {
        PermissionRequester.request(mRequestOrigin, mTargetUICompnent, mPermissions, mRequestCode, mRunGranted, mRunDenied, mRunDeniedAlways, mShouldRational);
    }

    public static void callbackPermissionResult(Activity activity, int requestCode, int[] grantResult) {
        PermissionRequester.executeJob(activity, requestCode, grantResult);
    }

    public static void callbackPermissionResult(android.support.v4.app.Fragment fragment, int requestCode, int[] grantResult) {
        PermissionRequester.executeJob(fragment, requestCode, grantResult);
    }

    public static void callbackPermissionResult(android.app.Fragment fragment, int requestCode, int[] grantResult) {
        PermissionRequester.executeJob(fragment, requestCode, grantResult);
    }

    public static void activityDestroyed(Activity activity) {
        PermissionRequester.removeAllJob(activity);
    }

    public static void fragmentDestroyed(android.app.Fragment fragment) {
        PermissionRequester.removeAllJob(fragment);
    }

    public static void fragmentDestroyed(android.support.v4.app.Fragment fragment) {
        PermissionRequester.removeAllJob(fragment);
    }

    /**************************************************************************************************************************************
     * private api
     **************************************************************************************************************************************/


}