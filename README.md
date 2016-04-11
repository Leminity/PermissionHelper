# PermissionHelper
Android Permission Helper for marshmellow

1.Sample Code
```java
public class SampleActivity extends Activity {

    private static final String TAG = SampleActivity.class.getSimpleName();

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
                                showToast("Permission granted.");
                            }
                        })
                        .setActionDenied(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission denied.");
                            }
                        })
                        .setActionDeniedAlwayed(new Runnable() {
                            @Override
                            public void run() {
                                showToast("Permission denied always.");
                            }
                        })
                        .setActionShouldRational(new OnCallbackShouldRational() {
                            @Override
                            public void onCallbackShouldRational(final Runnable requestPermission, final Runnable deniedPermission) {
                                AlertDialog alertDialog = new AlertDialog.Builder(SampleActivity.this)
                                        .setTitle("Test")
                                        .setMessage("Required permission for test.")
                                        .setPositiveButton("Request", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermission.run();
                                            }
                                        })
                                        .setNegativeButton("Denied", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                deniedPermission.run();
                                            }
                                        })
                                        .show();
                            }
                        })
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

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
