package com.tistory.leminity.permissionhelper.sample.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tistory.leminity.permissionhelper.sample.R;

/**
 * Created by leminity@jiran.com on 2021-12-20
 * <p/>
 * Class   : com.tistory.leminity.permissionhelper.sample.example.AndroidXFragmentActivity<br/>
 * Description  : {@link AndroidXFragment} 샘플 구현을 위한 액티비티<br/>
 * History<br/>
 * - 2021-12-20 : 최초 구현<br/>
 * <p/>
 */
public class AndroidXFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androidx_fragment);
    }
}