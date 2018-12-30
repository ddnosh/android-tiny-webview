package com.androidwind.webview.sample.jsinterface;

import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.androidwind.webview.common.JsInterface;

import java.lang.ref.WeakReference;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class Tiny {

    private static final String TAG = "Tiny";

    private WeakReference<WebView> webViewHolder = null;

    @Nullable
    JsInterface mJsInterface;

    public Tiny(WebView view, @Nullable JsInterface jsInterface) {
        if (view != null) {
            webViewHolder = new WeakReference<WebView>(view);
            mJsInterface = jsInterface;
        }
    }

    /**
     * @param jsonParam request:
     *                  {
     *                  "name": "xxx",
     *                  "params": "xxxx",
     *                  "callback": "xxxx"   // 前端设置的回调函数名
     *                  }
     */
    @JavascriptInterface
    public void invoke(String jsonParam) {
        Log.i(TAG, "js -> native : invoke");
        if (null != mJsInterface) {
            JSONObject jsonObject = toObject(jsonParam);
            if (null != jsonObject) {
                String name = jsonObject.getString("name");
                mJsInterface.invoke(name, jsonObject);
            } else {
                Log.e(TAG, "parse js request failed");
            }
        } else {
            Log.e(TAG, "no jsInterface register");
        }
    }

    public static JSONObject toObject(String json) {
        if ( null == json ){
            return null;
        }

        try{
            return JSON.parseObject(json);
        }
        catch (Exception e){
            Log.e(TAG, "toObject: " + e.getMessage());
        }
        return null;
    }


//    @JavascriptInterface
//    public void onClick() {
//        Log.d(TAG, "js -> native");
//        TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
//            public void run() {
//                // 此处调用 HTML 中的javaScript 函数
//                webViewHolder.get().loadUrl("javascript:wave()");
//            }
//        });
//    }
}