package com.tistory.leminity.permissionhelper.job;

import androidx.annotation.NonNull;

/**
 * PermissionResult 결과를 자체 처리없이 그대로 콜백하기 위한 Interface
 */
public interface IPermissionResult {
    public void onPermissionResult(String[] permissions, @NonNull int[] grantResults);
}
