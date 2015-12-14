package com.tistory.leminity.permissionhelper.job;

import java.util.Arrays;

/**
 * Created by leminity on 2015-12-02.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.job.JobItem
 * Description  : 권한 요청 승인/거부에 따른 로직 및 리소스를 관리하기 위한 VO
 * History
 * - 2015-12-02 : 최초작성
 */
public class JobItem {

    private Object      uiComponent;    //Key 1
    private int         requestCode; //Key 2
    private String[]    permissions;
    private Runnable    runWhenGranted;
    private Runnable    runWhenDenied;
    private Runnable runWhenDeniedAlways;

    public JobItem(Object uiComponent, String[] permissions, int requestCode, Runnable runWhenGranted, Runnable runWhenDenied, Runnable runWhenDeniedAlways) {
        this.uiComponent = uiComponent;
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.runWhenGranted = runWhenGranted;
        this.runWhenDenied = runWhenDenied;
        this.runWhenDeniedAlways = runWhenDeniedAlways;
    }

    public Object getActivity() {
        return uiComponent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public Runnable getRunWhenGranted() {
        return runWhenGranted;
    }

    public Runnable getRunWhenDenied() {
        return runWhenDenied;
    }

    public Runnable getRunWhenDeniedAlways() {
        return runWhenDeniedAlways;
    }

    public void setRunWhenDeniedAlways(Runnable runWhenDeniedAlways) {
        this.runWhenDeniedAlways = runWhenDeniedAlways;
    }

    @Override
    public String toString() {
        return "JobItem{" +
                "uiComponent=" + uiComponent.getClass().getSimpleName() +
                ", requestCode=" + requestCode +
                ", permissions=" + Arrays.toString(permissions) +
                ", runWhenGranted=" + runWhenGranted +
                ", runWhenDenied=" + runWhenDenied +
                ", runWhenDeniedAlways=" + runWhenDeniedAlways +
                '}';
    }
}
