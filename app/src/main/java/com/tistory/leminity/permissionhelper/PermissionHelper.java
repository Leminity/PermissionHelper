package com.tistory.leminity.permissionhelper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.tistory.leminity.permissionhelper.job.JobManager;

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
    private static final JobManager mJobManager = new JobManager();

    private Activity mAct;
    private String[] mPermissions;
    private int      mRequestCode;
    private Runnable mRunGranted;
    private Runnable mRunDenied;
    private String   mShouldRational;

    private PermissionHelper(@NonNull Activity act,@NonNull String[] permissions,int requestCode) {
        this.mAct = act;
        this.mPermissions = permissions;
        this.mRequestCode = requestCode;
    }

    public static PermissionHelper requestPermission(@NonNull android.app.Fragment fragment,@NonNull String permission, int requestCode) {
        return requestPermission(fragment.getActivity(), new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull android.app.Fragment fragment,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(fragment.getActivity(), permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull android.support.v4.app.Fragment fragment,@NonNull String permission, int requestCode) {
        return requestPermission(fragment.getActivity(), new String[]{permission}, requestCode);
    }

    public static PermissionHelper requestPermission(@NonNull android.support.v4.app.Fragment fragment,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(fragment.getActivity(), permissions, requestCode));
    }

    public static PermissionHelper requestPermission(@NonNull Activity act,@NonNull String permission, int requestCode) {
        return requestPermission(act, new String[]{permission}, requestCode);
    }

    /**
     * 권한 요청 인스턴스를 생성한다.
     * @param act
     * @param permissions
     * @param requestCode
     * @return
     */
    public static PermissionHelper requestPermission(@NonNull Activity act,@NonNull String[] permissions, int requestCode) {
        return (new PermissionHelper(act, permissions, requestCode));
    }

    public PermissionHelper setActionGranted(Runnable run){
        this.mRunGranted = run;
        return this;
    }

    public PermissionHelper setActionDenied(Runnable run){
        this.mRunDenied = run;
        return this;
    }

    public PermissionHelper setActionShouldRational(int stringResourceId) {
        this.mShouldRational = mAct.getString(stringResourceId);
        return this;
    }

    public void execute() {
        this.execute(mAct, mPermissions, mRequestCode, mRunGranted, mRunDenied, mShouldRational);
    }

    private void execute(Activity act,
                         String[] permissions,
                         int      requestCode,
                         Runnable runWhenAllow,
                         Runnable runWhenDenied,
                         String   shouldRational) {
        //권한 있으면 그냥 동작 실행
        if(this.hasPermissions(act.getBaseContext(), permissions)){
            if(runWhenAllow != null)
                runWhenAllow.run();
            return;
        }

        //권한 없으면.. 불행의 시작
        mJobManager.addJob(act, requestCode, runWhenAllow, runWhenDenied);
        requestPermission(act, requestCode, permissions, shouldRational);
    }

    /**
     * 요청된 권한들이 허용되어 있는지 확인한다.
     * @param ctx
     * @param permissions
     * @return <p>
     *      true  - 요청된 권한 목록이 전부 허용인 경우 <p>
     *      false - 요청된 권한 목록 중 한개라도 미허용인 경우
     */
    private boolean hasPermissions(Context ctx, String[] permissions){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if(ActivityCompat.checkSelfPermission(ctx, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    /**
     * 권한 요청(for marshmallow or Above only)
     */
    private void requestPermission(final Activity act,
                                   final int      requestCode,
                                   final String[] permissions,
                                   final String   shouldRational) {

        if (shouldShowRequestPermissionsRational(act, permissions)) {
            if(TextUtils.isEmpty(shouldRational) != true) {
                this.showShouldRationalSnackBar(mAct, shouldRational, requestCode, new Runnable() {
                    @Override
                    public void run() {
                        ActivityCompat.requestPermissions(act, permissions, requestCode);
                    }
                });
            }
            return;
        }

        ActivityCompat.requestPermissions(act, permissions, requestCode);
    }

    private boolean shouldShowRequestPermissionsRational(Activity act, String[] permissions) {
        int permCnt = permissions.length;
        for (int i = 0; i < permCnt; i++) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(act, permissions[i]))
                return true;
        }
        return false;
    }

    public void callbackPermissionResult(Activity activity, int requestCode, int[] grantResult) {
        boolean isAllowPermission = verifyPermissions(grantResult);
        Runnable run = mJobManager.removeJob(activity, requestCode, isAllowPermission);

        if(run != null)
            run.run();
    }

    private boolean verifyPermissions(int[] grantResult) {
        int resultCnt = grantResult.length;

        if(resultCnt <= 0)
            return false;

        for (int i = 0; i < resultCnt; i++) {
            if(grantResult[i] != PackageManager.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    public void activityDestroyed(Activity activity) {
        mJobManager.removeAllJob(activity);
    }

    private void showShouldRationalSnackBar(final Activity act, String message, final int requestCode, final Runnable run) {
        Snackbar.make(act.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction(R.string.button_label_request_permission, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(run != null)
                            run.run();
                    }
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);

                        if(event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT)
                            mJobManager.removeJob(act, requestCode, false);
                    }
                })
                .show();
    }

}