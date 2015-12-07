package com.tistory.leminity.permissionhelper.job;

import android.app.Activity;

/**
 * Created by leminity on 2015-12-02.
 * <p/>
 * Class Name   : com.jiransecurity.udpclientsample.permission.job.JobManager
 * Description  : 권한에 따른 동작(JobItem)을 관리하기 위한 외부 api 모듈
 *                권한 요청 정보를 관리하기 위한 Key로 Activity와 requestCode를 사용한다.
 * History
 * - 2015-12-02 : 최초작성
 */
public class JobManager {

    private DualKeyMap<Activity, Integer, JobItem> mJobMap = new DualKeyMap();

    /**
     * 잡을 추가한다.
     * @param activity
     * @param requestCode
     * @param runWhenDenied
     */
    public void addJob(Activity activity, int requestCode, Runnable runWhenGranted, Runnable runWhenDenied) {
        JobItem newJob = new JobItem(activity, requestCode, runWhenGranted, runWhenDenied);
        mJobMap.add(activity, requestCode, newJob);
    }

    /**
     * 작업을 제거한다.
     * @param activity
     * @param requestCode
     */
    public Runnable removeJob(Activity activity, int requestCode, boolean isAllowPermission) {
        JobItem jobItem = mJobMap.remove(activity, requestCode);
        return (isAllowPermission ? jobItem.getRunWhenGranted() : jobItem.getRunWhenDenied());
    }

    /**
     * Activity의 onDestroy가 호출된 경우 삭제해야 한다.
     * 호출되지 않으면 메모리 릭 발생.
     * @param activity
     */
    public void removeAllJob(Activity activity) {
        mJobMap.removeAllJobByActivity(activity);
    }

}
