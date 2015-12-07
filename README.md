# PermissionHelper
Android Permission Helper for marshmellow

1.Sample Code
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  findViewById(R.id.buttonRequestPerm).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PermissionHelper
                .requestPermission(SampleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 123)
                .setActionGranted(new Runnable() {
                    @Override
                    public void run() {
                      //implements your business logic.
                    }
                })
                .setActionDenied(new Runnable() {
                    @Override
                    public void run() {
                      //implements your business logic.
                    }
                })
                .setActionShouldRational(R.string.shouldRationalMsg)
                .execute();
      }
  });
}

@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
  //must be called when callback onRequestPermissionsResult.
  PermissionHelper.callbackPermissionResult(this, requestCode, grantResults);
  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

@Override
protected void onDestroy() {
  //must be called when destroy activity.
  //If not can cause memory leak.
  PermissionHelper.activityDestroyed(this);
  super.onDestroy();
}
```

2. API Documents
1. Create instance in Activity or Fragment <font color="red">(Required)</font>
 - requestPermission(android.app.Fragment fragment, String permission, int requestCode)
 - requestPermission(android.app.Fragment fragment, String[] permissions, int requestCode)
 - requestPermission(android.support.v4.app.Fragment fragment, String permission, int requestCode)
 - requestPermission(android.support.v4.app.Fragment fragment, String[] permissions, int requestCode)
 - requestPermission(Activity act, String permission, int requestCode)
 - requestPermission(Activity act, String[] permissions, int requestCode)
 
2. Implements your Logic. <font color="blue">(optional)</font>
 - setActionGranted(Runnable run)
 - setActionDenied(Runnable run)

3. Set message When you need should rational. <font color="blue">(optional)</font>
 - setActionShouldRational(int stringResourceId)
 
4. call helperInstance.callbackPermissionResult on Activity or Fragment's onRequestPermissionsResult method. <font color="red">(Required)</font>
 - callbackPermissionResult(Activity activity, int requestCode, int[] grantResult)
 
5. You must call PermissionHelper.activityDestroyed() when called Activity.onDestroy()
 - activityDestroyed(Activity activity)
