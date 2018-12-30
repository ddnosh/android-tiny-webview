package com.androidwind.webview.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainActivity extends AppCompatActivity {

    private TinyWebView tinyWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyWebView = TinyWebView.with(this).go("file:///android_asset/demo.html").create();
        FrameLayout mWebViewContainer = findViewById(R.id.container);
        mWebViewContainer.addView(tinyWebView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tinyWebView.release();
    }
}
