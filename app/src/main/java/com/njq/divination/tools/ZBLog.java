package com.njq.divination.tools;

import android.util.Log;

import com.njq.divination.LogListenr;
import com.njq.divination.bean.EvenBean;

import org.greenrobot.eventbus.EventBus;

/**
 * log工具类
 */
public class ZBLog {


    private static EvenBean evenBean;
    private static LogListenr listenr;

    public static void e(String con){
        Log.e("ZBLog",con);
        if(listenr==null){
            return;
        }
        listenr.sendLog(con);
    }

    public static void Register(LogListenr lis) {
        listenr = lis;
        Log.e("ZBLog","注册了");
    }
}
