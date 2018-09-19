package com.njq.divination.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.njq.divination.R;
import com.njq.divination.http.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 描述：Application
 * 作者：钮家齐
 * 时间: 2018-09-17 19:54
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //okgo 网络请求初始化
        initOkGo();

        Utils.init(this);
    }

    private void initOkGo() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor(getString(R.string.app_name));
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
//        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));

        //超时时间设置，默认60秒
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(10000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0);                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
    }
}
