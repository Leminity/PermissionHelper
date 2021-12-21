package com.tistory.leminity.permissionhelper.sample.example;

import android.app.Activity;
import android.os.Bundle;

import com.tistory.leminity.permissionhelper.sample.R;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.DefaultFragmentActivity<br/>
 * Description  : {@link DefaultFragment} 샘플 구현을 위한 액티비티<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class DefaultFragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_fragment);
    }
}