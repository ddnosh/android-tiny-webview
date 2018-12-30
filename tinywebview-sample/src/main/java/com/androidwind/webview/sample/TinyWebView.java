package com.androidwind.webview.sample;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.androidwind.task.TinyTaskExecutor;
import com.androidwind.webview.BaseWebView;
import com.androidwind.webview.common.JsApiHandler;
import com.androidwind.webview.common.JsApiRegisterFactory;
import com.androidwind.webview.common.JsInterface;
import com.androidwind.webview.common.LoadJsCallback;
import com.androidwind.webview.sample.handler.AppInfoHandler;
import com.androidwind.webview.sample.handler.PendingHandler;
import com.androidwind.webview.sample.jsinterface.Tiny;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyWebView extends BaseWebView implements JsInterface, LoadJsCallback {

    private final static String TAG = "TinyWebView";

    /**
     * javascriptinterface support
     */
    public TinyWebView(Builder builder) {
        super(builder.getContext());
        initJS();
    }

    /**
     * init javascript
     */
    private JsApiRegisterFactory mJsApiRegisterFactory;

    private void initJS() {
        System.out.println("[TinyWebView] thread id: " + Thread.currentThread().getId());

        mJsApiRegisterFactory = JsApiRegisterFactory.newInstance(this);
        mJsApiRegisterFactory.register(new AppInfoHandler());
        mJsApiRegisterFactory.register(new PendingHandler());
        addJavascriptInterface(new Tiny(this, this), "demo");
    }

    public void release() {
        mJsApiRegisterFactory.release();
        destroy();
    }

    /**
     * js -> native
     */
    @Override
    public void invoke(String name, JSONObject request) {
        Log.i(TAG, String.format("js -> native: js invoke name:%s, request:%s", name, request));

        JsApiHandler handler = mJsApiRegisterFactory.getHandler(name);
        if (null == handler) {
            Log.e(TAG, "can not find out handler for name:" + name);
            return;
        }
        //test pending
        if (!TextUtils.isEmpty(name)) {
            if (name.equals("appInfo")) {
                handler.handle(request.get("params"), request.getString("callback"), false);
            } else if (name.equals("pending")) {
                handler.handle(request.get("params"), request.getString("callback"), true);
            }
        }
    }

    /**
     * native -> js
     */
    @Override
    public void loadJavaScript(String js) {
        Log.d(TAG, "native -> js: [loadJavaScript] " + js);
        if (Build.VERSION.SDK_INT <= 18) {
            loadUrl(js);
        } else {
            try {
                evaluateJavascript(js, null);
            } catch (Exception e) {
                Log.e(TAG, "", e);
                Log.i(TAG, "switch to call loadUrl");
                loadUrl(js);
            }
        }
    }

    @Override
    public TinyWebView getCurrentWebView() {
        return TinyWebView.this;
    }

    public JsApiHandler getHandler(String name) {
        return mJsApiRegisterFactory.getHandler(name);
    }

    @Override
    public void loadUrl(final String url) {
        Log.d(TAG, "loadUrl:" + url);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            TinyTaskExecutor.postToMainThread(new Runnable() {
                @Override
                public void run() {
                    loadUrl(url);
                }
            });
        } else {
            super.loadUrl(url);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            unInitJavaScriptSurport();
        }
    }

    @TargetApi(17)
    private void unInitJavaScriptSurport() {
        removeJavascriptInterface("demo");
    }

    /**
     * init new webview
     */
    public static Builder with(@NonNull Context context) {
        if (context == null) {
            throw new NullPointerException("activity can not be null .");
        }
        return new Builder(context);
    }

    public static final class Builder {
        private Context context;

        private TinyWebView tinyWebView;

        private String url;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public TinyWebView create() {
            tinyWebView = new TinyWebView(this);
            //handle url
            if (!TextUtils.isEmpty(url)) {
                tinyWebView.loadUrl(url);
            }
            return tinyWebView;
        }

        public Builder go(String url) {
            this.url = url;
            return this;
        }
    }

}
