package com.tistory.leminity.permissionhelper.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tistory.leminity.permissionhelper.sample.example.AndroidXActivity;
import com.tistory.leminity.permissionhelper.sample.example.AndroidXFragmentActivity;
import com.tistory.leminity.permissionhelper.sample.example.DefaultActivity;
import com.tistory.leminity.permissionhelper.sample.example.DefaultFragmentActivity;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.SampleActivity<br/>
 * Description  : 샘플 동작 확인을 위한 인트로액티비티<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.button_activity_default).setOnClickListener((View v) -> {
            launchActivity(DefaultActivity.class);
        });

        findViewById(R.id.button_fragment_default).setOnClickListener((View v) -> {
            launchActivity(DefaultFragmentActivity.class);
        });

        findViewById(R.id.button_activity_androidx).setOnClickListener((View v) -> {
            launchActivity(AndroidXActivity.class);
        });

        findViewById(R.id.button_fragment_androidx).setOnClickListener((View v) -> {
            launchActivity(AndroidXFragmentActivity.class);
        });
    }

    private void launchActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(SampleActivity.this, activityClass);
        startActivity(i);
    }

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

}
