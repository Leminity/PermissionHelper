package com.tistory.leminity.permissionhelper.job;

import android.app.Activity;

/**
 * Created by leminity on 2015-12-02.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.job.JobItem
 * Description  : 권한 요청 승인/거부에 따른 로직 및 리소스를 관리하기 위한 VO
 * History
 * - 2015-12-02 : 최초작성
 */
class JobItem {

    private Activity    activity;    //Key 1
    private int         requestCode; //Key 2
    private Runnable    runWhenGranted;
    private Runnable    runWhenDenied;

    public JobItem(Activity activity, int requestCode, Runnable runWhenGranted, Runnable runWhenDenied) {
        this.activity = activity;
        this.requestCode = requestCode;
        this.runWhenGranted = runWhenGranted;
        this.runWhenDenied = runWhenDenied;
    }

    public Activity getActivity() {
        return activity;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public Runnable getRunWhenGranted() {
        return runWhenGranted;
    }

    public Runnable getRunWhenDenied() {
        return runWhenDenied;
    }

    @Override
    public String toString() {
        return "JobItem{" +
                "activity=" + activity.getClass().getSimpleName() +
                ", requestCode=" + requestCode +
                ", runWhenGranted=" + runWhenGranted +
                ", runWhenDenied=" + runWhenDenied +
                '}';
    }
}
