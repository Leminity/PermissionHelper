package com.tistory.leminity.permissionhelper.request;

/**
 * Used when required callback should rational to user.
 * Created by User on 2016-04-11.
 */
public interface OnCallbackShouldRational {

    public void onCallbackShouldRational(Runnable requestPermission, Runnable deniedPermission);
}
