package com.yy.sagit.activity;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.sagit.BuildConfig;
import com.yy.sagit.MAcessibilityService;
import com.yy.sagit.MyAccessibilityService;
import com.yy.sagit.R;
import com.yy.sagit.builder.User;
import com.yy.sagit.single.SingleClass;
import com.yy.sagit.util.MyLog;
import com.yy.sagit.util.KeyStoreUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=(TextView) this.findViewById(R.id.tv);
        long downtime= SystemClock.uptimeMillis();
        findViewById(R.id.bt_begininstall).setOnClickListener(this);
        findViewById(R.id.bt_smartinstall).setOnClickListener(this);
//        new MyAccessibilityService().dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription()).build(), new AccessibilityService.GestureResultCallback() {
//            @Override
//            public void onCompleted(GestureDescription gestureDescription) {
//                super.onCompleted(gestureDescription);
//            }
//
//            @Override
//            public void onCancelled(GestureDescription gestureDescription) {
//                super.onCancelled(gestureDescription);
//            }
//        },new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        });
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
//        execShellCmd("what");
    }

    //智能安装
    private void smartInstall(Context context,String apkPath) {

//        Uri uri = Uri.fromFile(new File(apkPath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        File file=new File(apkPath);
        if (!file.exists()) {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", new File(apkPath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            MyLog.e("大于");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyLog.e("小于");
        }
        startActivity(intent);
        MAcessibilityService.setEnable(true);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_begininstall:
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;
            case R.id. bt_smartinstall:
                MyLog.e(Environment
                        .getExternalStorageDirectory().getAbsolutePath()
                        + File.separator+"test.apk");
                smartInstall(this,Environment
                        .getExternalStorageDirectory().getAbsolutePath()
                        + File.separator+"test.apk");
                break;
        }
    }
}
