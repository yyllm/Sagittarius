package com.yy.sagit;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.yy.sagit.activity.MainActivity;
import com.yy.sagit.util.MyLog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/3.
 */

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "[TAG]";
    private Map<Integer, Boolean> handleMap = new HashMap<>();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        MyLog.e("onAccessibilityEvent--------------------------");
        AccessibilityNodeInfo nodeInfo = event.getSource();

        if (nodeInfo != null ) {
            nodeInfo.refresh();
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handleMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handleMap.put(event.getWindowId(), true);
                    }
                }
            }

        }
    }

    @Override
    public void onInterrupt() {
        MyLog.e("onInterrupt--------------------------");
    }

    //遍历节点，模拟点击安装按钮
    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        MyLog.e("iterateNodesAndHandle--------------------------");
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeCotent = nodeInfo.getText().toString();
                Log.e(TAG, "content is: " + nodeCotent);
                if ("安装".equals(nodeCotent) || "完成".equals(nodeCotent) || "确定".equals(nodeCotent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            }
            //遇到ScrollView的时候模拟滑动一下
            else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;
    }
}
