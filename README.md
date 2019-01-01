# android-tiny-webview
a tiny webview for js and native interaction
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190101094639884.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Rkbm9zaA==,size_16,color_FFFFFF,t_70)

# Solution
1. we need a base webview, you know;
2. we will handle request(js -> native) asynchronously;
request(js -> native) in background, and response(nataive -> js) in main thread;
3. "native and js" business will be handled in single file like "xxHandler";
4. as asynchronization, we will use TinyTask library(https://github.com/ddnosh/android-tiny-task);

# Function
1. offer a base webview;
2. implement your handler file to handle your business;
3. in your webview, you will handle the js&&native interaction;

# Technology
1. Design Pattern
    1. Builder
2. Skill Point
    1. webview

# Usage
1. YourHandler extends BaseJsApiHandler;
2. mJsApiRegisterFactory.register(new YourHandler());
3. You need a webview like TinyWebView extends BaseWebView implements JsInterface, LoadJsCallback;

# TODO
1. make much more examples;
