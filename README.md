# PermissionHelper
Android Permission Helper for marshmellow

1.Sample Code
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample);
    findViewById(R.id.buttonRequestPerm).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PermissionHelper
                    .requestPermission(SampleActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 123)
                    .setActionGranted(new Runnable() {
                        @Override
                        public void run() {
                            //Permission granted.
                        }
                    })
                    .setActionDenied(new Runnable() {
                        @Override
                        public void run() {
                            //Permission denied.
                        }
                    })
                    .setActionDeniedAlwayed(new Runnable() {
                        @Override
                        public void run() {
                            //Permission denied always.
                        }
                    })
                    .setActionShouldRational(R.string.message_read_external_storage)
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
1. Create instance. **(Required)**
 - requestPermission(android.app.Fragment fragment, String permission, int requestCode)
 - requestPermission(android.app.Fragment fragment, String[] permissions, int requestCode)
 - requestPermission(android.support.v4.app.Fragment fragment, String permission, int requestCode)
 - requestPermission(android.support.v4.app.Fragment fragment, String[] permissions, int requestCode)
 - requestPermission(Activity act, String permission, int requestCode)
 - requestPermission(Activity act, String[] permissions, int requestCode)
 
2. Set Action When grant or deny by user. **(optional)**
 - setActionGranted(Runnable run)
 - setActionDenied(Runnable run)
 - setActionDeniedAlways(Runnable run)

3. Set message When you need should rational. **(optional)**
 - setActionShouldRational(int stringResourceId)
 
4. call helperInstance.callbackPermissionResult on Activity or Fragment's onRequestPermissionsResult method. **(Required)**
 - callbackPermissionResult(Activity activity, int requestCode, int[] grantResult)
 
5. You must call PermissionHelper.activityDestroyed() when called Activity.onDestroy()
 - activityDestroyed(Activity activity)
