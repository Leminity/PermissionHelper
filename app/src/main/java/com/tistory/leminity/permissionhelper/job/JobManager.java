package com.tistory.leminity.permissionhelper.job;

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

    private DualKeyMap<Object, Integer, JobItem> mJobMap = new DualKeyMap();

    /**
     * 잡을 추가한다.
     * @param targetUIComponent
     * @param requestCode
     * @param runWhenDenied
     */
    public void addJob(Object targetUIComponent, String[] permissions, int requestCode, Runnable runWhenGranted, Runnable runWhenDenied, Runnable runWhenDeniedAlways) {
        JobItem newJob = new JobItem(targetUIComponent, permissions, requestCode, runWhenGranted, runWhenDenied, runWhenDeniedAlways);
        mJobMap.add(targetUIComponent, requestCode, newJob);
    }

    /**
     * 작업을 제거한다.
     * @param targetUIComponent
     * @param requestCode
     */
    public JobItem removeJob(Object targetUIComponent, int requestCode) {
        JobItem jobItem = mJobMap.remove(targetUIComponent, requestCode);
        return jobItem;
    }

    /**
     * Activity, Fragment, Support-Fragment 등에서 onDestroy가 호출된 경우 삭제해야 한다.
     * 호출되지 않으면 메모리 릭 발생.
     * @param targetUIComponent
     */
    public void removeAllJob(Object targetUIComponent) {
        mJobMap.removeAllJob(targetUIComponent);
    }

}
