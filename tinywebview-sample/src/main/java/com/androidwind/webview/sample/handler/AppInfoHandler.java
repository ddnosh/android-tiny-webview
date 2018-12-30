package com.androidwind.webview.sample.handler;

import com.alibaba.fastjson.JSONObject;
import com.androidwind.tinywebview.common.BaseJsApiHandler;
import com.androidwind.tinywebview.common.JsRequest;
import com.androidwind.tinywebview.common.JsResponse;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class AppInfoHandler extends BaseJsApiHandler<JSONObject> {

    @Override
    public String name() {
        return "appInfo";
    }

    @Override
    protected void handelInBackground(JsRequest<JSONObject> jsRequest) {
        System.out.println("[AppInfoHandler] thread id: " + Thread.currentThread().getId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("os", "android");
        jsonObject.put("version", "1.0.0");

        setResponse(jsRequest, JsResponse.success(jsonObject));
    }
}
