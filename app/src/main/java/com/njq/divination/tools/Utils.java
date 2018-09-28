package com.njq.divination.tools;

import android.util.Log;
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
    static String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申",
            "酉", "戌", "亥"};

    /**
     * 获取月将
     * @param monthWill
     * @return
     */
    public static String getMonthWill(String monthWill){
        String substring = monthWill.substring(monthWill.length() - 3, monthWill.length() - 2);
        return substring;
    }
    /**
     * 获取时辰
     * @param monthWill
     * @return
     */
    public static String getH(String monthWill){
        String substring = monthWill.substring(monthWill.length() - 2, monthWill.length() - 1);
        return substring;
    }


    /**
     * 获取 地支 位置
     * @param zi
     * @return
     */
    public static int getDZPos(String zi){
        for (int i = 0;i<Zhi.length;i++){
            if(Zhi[i].equals(zi)){
                return i;
            }
        }
        return 0;
    }

    /**
     * 排列天盘
     * @return
     * @param y
     * @param h
     */
    public static String[] getArrayTP(int y, int h){
        String[] s = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申","酉", "戌", "亥"};

        int w = 0;
        if(h>y){
            w = h - y;
        }else if(h<y){
            w = 12-y+h;
        }

        for (int i = 0;i<12;i++){
            s[i] = Zhi[12-w-1];
            w = w-1;
        }
        for (int i = 0;i<12;i++){
            Log.e("排列天盘",s[i]);
        }
        return s;
    }
}
