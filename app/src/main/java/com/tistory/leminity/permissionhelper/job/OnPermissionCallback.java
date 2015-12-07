package com.tistory.leminity.permissionhelper.job;

/**
 * Created by leminity on 2015-12-03.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.job.PermissionJob
 * Description  : 권한 결과에 따른 동작을 구현한다.
 * History
 * - 2015-12-03 : 최초작성
 */
public interface OnPermissionCallback {
    void onPermissionGranted();
    void onPermissionDenied();
}
