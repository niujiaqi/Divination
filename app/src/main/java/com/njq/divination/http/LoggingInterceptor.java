package com.njq.divination.http;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * 统一打印请求
 * Created by Administrator on 2017/5/11.
 */

public class LoggingInterceptor implements Interceptor {

    private final Logger logger;

    public LoggingInterceptor(String tag) {
        logger = Logger.getLogger(tag);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        boolean netAvailable = NetworkUtils.isAvailableByPing();

        int maxAge = 0;
        if (netAvailable) {
            request = request.newBuilder()
                    //网络可用 强制从网络获取数据
                    .cacheControl(new CacheControl.Builder().noCache().build())
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
//            request = request.newBuilder()
//                    //网络可用 强制从网络获取数据
//                    .cacheControl(CacheControl.FORCE_NETWORK)
//                    .build();
        } else {
            request = request.newBuilder()
                    //网络不可用 从缓存获取
                    .cacheControl(new CacheControl.Builder().noCache().build())
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }

        long t1 = System.nanoTime();//请求发起的时间
        if(true){
            logger.log(Level.SEVERE, String.format("发送请求 %s",
                    request.url()));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理   %n%s
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        if(true){
            logger.log(Level.SEVERE, String.format("接收响应: [%s] %n返回json:【%s】 %.1fms",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));
        }
        return response;
    }
}
