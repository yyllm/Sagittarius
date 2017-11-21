package com.yy.sagit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.excallback.CustomCallback;
import com.kingja.loadsir.excallback.EmptyCallback;
import com.kingja.loadsir.excallback.ErrorCallback;
import com.kingja.loadsir.excallback.LoadingCallback;
import com.kingja.loadsir.excallback.TimeoutCallback;

/**
 * Created by Administrator on 2017/9/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();

        /**
         * application里注册activity生命周期回调
         */
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
