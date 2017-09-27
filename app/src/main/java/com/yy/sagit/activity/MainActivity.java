package com.yy.sagit.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yy.sagit.R;
import com.yy.sagit.builder.User;
import com.yy.sagit.single.SingleClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingleClass.INSTANCE.doSomeThing();

        new User.UserBuilder("王", "小二")
                .age(20)
                .phone("123456789")
                .address("亚特兰蒂斯")
                .build();
        new User.UserBuilder("王", "小二")
                .age(20)
                .phone("123456789")
                .address("亚特兰蒂斯")
                .build();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.show();
    }
}
