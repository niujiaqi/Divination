package com.njq.divination.tools;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;

/**
 * 描述：工具类
 * 作者：钮家齐
 * 时间: 2018-09-17 20:09
 */

public class Utils {

    private static Toast toast;

    public static void TS(String content) {
        if(content.contains("No address associated with hostname")){
            content = "网络连接失败";
        }
        if(toast ==null){
            toast = Toast.makeText(com.blankj.utilcode.util.Utils.getApp(), content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }
}
