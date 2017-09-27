package com.yy.sagit.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yy.sagit.R;
import com.yy.sagit.single.SingleClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SingleClass.INSTANCE.doSomeThing();
    }
}
