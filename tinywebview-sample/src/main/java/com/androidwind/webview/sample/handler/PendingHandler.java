package com.androidwind.webview.sample.handler;

import com.alibaba.fastjson.JSONObject;
import com.androidwind.webview.common.BaseJsApiHandler;
import com.androidwind.webview.common.JsRequest;
import com.androidwind.webview.common.JsResponse;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class PendingHandler extends BaseJsApiHandler<JSONObject> {

    @Override
    public String name() {
        return "pending";
    }

    @Override
    protected void handelInBackground(JsRequest<JSONObject> jsRequest) {
        System.out.println("[PendingHandler] thread id: " + Thread.currentThread().getId());

        try {
            Thread.sleep(3000);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", 1000);
            setResponse(jsRequest, JsResponse.success(jsonObject));
            handlePendingRequest(jsRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
