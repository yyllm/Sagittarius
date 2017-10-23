package com.yy.sagit.activity;

import android.app.AlertDialog;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yy.sagit.R;
import com.yy.sagit.builder.User;
import com.yy.sagit.single.SingleClass;
import com.yy.sagit.util.MyLog;
import com.yy.sagit.util.KeyStoreUtils;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=(TextView) this.findViewById(R.id.tv);
        long downtime= SystemClock.uptimeMillis();

        MotionEvent down=MotionEvent.obtain(downtime,downtime,MotionEvent.ACTION_DOWN,100,100,0);
        MotionEvent up=MotionEvent.obtain(downtime,SystemClock.uptimeMillis(),MotionEvent.ACTION_UP,100,100,0);
        textView.onTouchEvent(down);
        textView.onTouchEvent(up);
        SingleClass.INSTANCE.doSomeThing();
        textView.callOnClick();
        textView.performClick();
        MyLog.e("aaa", KeyStoreUtils.getKeyStoreValue(this, KeyStoreUtils.EnumValue.MD5));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa","点击我了");
            }
        });
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("bbb","onTouch");
                return false;
            }
        });
        new User.UserBuilder("王", "小二")
                .age(20)
                .phone("123456789")
                .address("亚特兰蒂斯")
                .build();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.show();
        execShellCmd("what");
    }

    private void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            Log.e("bbb","no problem");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
