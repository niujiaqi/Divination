package com.njq.divination.tools;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.njq.divination.MainActivity;
import com.njq.divination.bean.FJGBean;
import com.njq.divination.bean.FirstPassBean;

import java.util.ArrayList;

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
    static String[] Zhi1 = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申",
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
        for (int i = 0;i<Zhi1.length;i++){
            if(Zhi1[i].equals(zi)){
                return i;
            }
        }
        return 0;
    }
//    /**
//     * 获取 天盘之支
//     * @param zi
//     * @return
//     */
//    public static String getTPPos(String zi,ArrayList<String> list){
//        for (int i = 0;i<Zhi1.length;i++){
//            if(Zhi1[i].equals(zi)){
//                return i;
//            }
//        }
//        return 0;
//    }


    /**
     * 排列天盘
     * @return
     * @param y
     * @param h
     */
    public static ArrayList<String> getArrayTP(int y, int h){
        ArrayList<String> strings = new ArrayList<>();

        int w = 0;
        if(h>y){
            w = h - y;
        }else if(h<y){
            w = 12-y+h;
        }
        for (int i = 12-w;i<12;i++){
            strings.add(Zhi1[i]);
        }
        Log.e("位移",""+w);
        int p = 0;
        for (int i = w;i<12;i++){
            strings.add(Zhi1[p]);
            p++;
        }
//        for (int i = 0;i<strings.size();i++){
//            Log.e("排列天盘::"+i,strings.get(i));
//        }
        return strings;
    }
    /**
     * 获取别责法 重新排列的集合
     * @return
     */
    public static ArrayList<String> getArrayBZ(String s, ArrayList<String> list){
        ArrayList<String> strings = new ArrayList<>();
        int dzPos = getDZPos(s);

        for (int i = dzPos;i<12;i++){
            strings.add(list.get(i));
        }
        for (int i = 0;i<dzPos;i++){
            strings.add(list.get(i));
        }
//        for (int i = 0;i<strings.size();i++){
//            Log.e("重新排列的集合::"+i,strings.get(i));
//        }
        return strings;
    }

    /**
     * 获取第一课
     * @return
     * @param substring
     * @param arrayTP
     */
    public static String getK1(String substring, ArrayList<String> arrayTP){
        String jg = getJG(substring);
        int tgPos = getDZPos(jg);
        return arrayTP.get(tgPos);
    }
    /**
     * 获取第二课
     * @return
     * @param substring
     * @param arrayTP
     */
    public static String getK2(String substring, ArrayList<String> arrayTP){
        int tgPos = getDZPos(substring);
        return arrayTP.get(tgPos);
    }

    /**
     * 获取天干 的寄宫
     * @return
     */
    public static String getJG(String tg){
        if(tg.equals("甲")){
            return "寅";
        }
        if(tg.equals("乙")){
            return "辰";
        }
        if(tg.equals("丙")){
            return "巳";
        }
        if(tg.equals("丁")){
            return "未";
        }
        if(tg.equals("戊")){
            return "巳";
        }
        if(tg.equals("己")){
            return "未";
        }
        if(tg.equals("庚")){
            return "申";
        }
        if(tg.equals("辛")){
            return "戌";
        }
        if(tg.equals("壬")){
            return "亥";
        }
        if(tg.equals("癸")){
            return "丑";
        }
        return "";
    }

    /**
     * 获取初传
     * @return
     */
    public static FirstPassBean getCC(String x1, String x2, String x3, String x4, String s1, String s2, String s3, String s4){
        //计算下克上
        boolean xk1 = getXK(getShuXing(x1), getShuXing(s1));
        boolean xk2 = getXK(getShuXing(x2), getShuXing(s2));
        boolean xk3 = getXK(getShuXing(x3), getShuXing(s3));
        boolean xk4 = getXK(getShuXing(x4), getShuXing(s4));
        //计算上克下
        boolean sk1 = getXK(getShuXing(s1), getShuXing(x1));
        boolean sk2 = getXK(getShuXing(s2), getShuXing(x2));
        boolean sk3 = getXK(getShuXing(s3), getShuXing(x3));
        boolean sk4 = getXK(getShuXing(s4), getShuXing(x4));

        ZBLog.e("\n============制败法求初传==========\n");
        FirstPassBean zb = FirstPassUtils.getZB(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4);      //制败法求初传  如果为null  再用比用法求
        if(zb==null){
            ZBLog.e("\n============比用法求初传==========\n");
            FirstPassBean by = FirstPassUtils.getBY(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4);       //比用法求初传  如果为null  再用涉害法求
            if(by==null){
                ZBLog.e("\n============涉害法求初传==========\n");
                FirstPassBean sh = FirstPassUtils.getSH(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4);    //涉害法求初传   如果为null  再用遥制法求
                if(sh==null){
                    ZBLog.e("\n============遥制法求初传==========\n");
                    FirstPassBean yz = FirstPassUtils.getYZ(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4);    //遥制法求初传   如果为null  再用昂星法求
                    if(yz==null){
                        ZBLog.e("\n============昂星法求初传==========\n");
                        FirstPassBean ax = FirstPassUtils.getAX(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4);    //昂星法求初传   如果为null  再用别责法求
                        if(ax==null){
                            ZBLog.e("\n============别责法求初传==========\n");
                            FirstPassBean bz = FirstPassUtils.getBZ(xk1, xk2, xk3, xk4, sk1, sk2, sk3, sk4, s1, s2, s3, s4, x1,  x2,  x3,  x4);    //别责法求初传   如果为null  再用八专注求
                            if(bz==null){
                                ZBLog.e("八专注 程序编写尚未完成   无法计算出结果");
                            }
                            return yz;
                        }
                    }
                    return yz;
                }
                return sh;
            }
            return by;
        }
        return zb;
    }


    /**
     * 获取属性
     * @return
     */
    public static String getShuXing(String tv){
        if(tv.equals("甲")||tv.equals("乙")||tv.equals("寅")||tv.equals("卯")){
            return "木";
        }
        if(tv.equals("丙")||tv.equals("丁")||tv.equals("午")||tv.equals("巳")){
            return "火";
        }
        if(tv.equals("戊")||tv.equals("己")||tv.equals("辰")||tv.equals("戌")||tv.equals("丑")||tv.equals("未")){
            return "土";
        }
        if(tv.equals("庚")||tv.equals("辛")||tv.equals("申")||tv.equals("酉")){
            return "金";
        }
        if(tv.equals("壬")||tv.equals("癸")||tv.equals("子")||tv.equals("亥")){
            return "水";
        }
        return "";
    }

    /**
     * 根据地支的寄宫  反向求对应天干
     * @return
     */
    public static FJGBean getFJG(String tg){
        FJGBean fjgBean = new FJGBean();
        if(tg.equals("寅")){
            fjgBean.mun = 1;
            fjgBean.zi = "甲";
            return fjgBean;
        }
        if(tg.equals("辰")){
            fjgBean.mun = 1;
            fjgBean.zi = "乙";
            return fjgBean;
        }
        if(tg.equals("巳")){
            fjgBean.mun = 2;
            fjgBean.zi = "丙";
            fjgBean.zi2 = "戊";
            return fjgBean;
        }
        if(tg.equals("未")){
            fjgBean.mun = 2;
            fjgBean.zi = "丁";
            fjgBean.zi2 = "己";
            return fjgBean;
        }
        if(tg.equals("申")){
            fjgBean.mun = 1;
            fjgBean.zi = "庚";
            return fjgBean;
        }
        if(tg.equals("戌")){
            fjgBean.mun = 1;
            fjgBean.zi = "辛";
            return fjgBean;
        }
        if(tg.equals("亥")){
            fjgBean.mun = 1;
            fjgBean.zi = "壬";
            return fjgBean;
        }
        if(tg.equals("丑")){
            fjgBean.mun = 1;
            fjgBean.zi = "癸";
            return fjgBean;
        }
        return null;
    }

    /**
     * 获取是否相克
     * @param s1
     * @param s2
     * @return
     */
    public static boolean getXK(String s1,String s2){
        if(s1.equals("木")&&s2.equals("土")){
            return true;
        }
        if(s1.equals("火")&&s2.equals("金")){
            return true;
        }
        if(s1.equals("土")&&s2.equals("水")){
            return true;
        }
        if(s1.equals("金")&&s2.equals("木")){
            return true;
        }
        if(s1.equals("水")&&s2.equals("火")){
            return true;
        }
        return false;
    }


    /**
     * 取阴阳
     */
    public static String getYY(String s){
        if(s.equals("甲")||s.equals("丙")||s.equals("戊")||s.equals("庚")||s.equals("壬")){
            return "阳";
        }
        if(s.equals("乙")||s.equals("丁")||s.equals("己")||s.equals("辛")||s.equals("癸")){
            return "阴";
        }
        if(s.equals("子")||s.equals("寅")||s.equals("辰")||s.equals("午")||s.equals("申")||s.equals("戌")){
            return "阳";
        }
        if(s.equals("丑")||s.equals("卯")||s.equals("巳")||s.equals("未")||s.equals("酉")||s.equals("亥")){
            return "阴";
        }
        return "";
    }

    /**
     * 取天干合神
     */
    public static String getTGH(String s){
        if(s.equals("甲")){
            return "己";
        }
        if(s.equals("乙")){
            return "庚";
        }
        if(s.equals("丙")){
            return "辛";
        }
        if(s.equals("丁")){
            return "壬";
        }
        if(s.equals("戊")){
            return "癸";
        }
        if(s.equals("己")){
            return "甲";
        }
        if(s.equals("庚")){
            return "乙";
        }
        if(s.equals("辛")){
            return "丙";
        }
        if(s.equals("壬")){
            return "丁";
        }
        if(s.equals("癸")){
            return "戊";
        }
        return "";
    }

    /**
     * 取三合其他两位
     */
    public static ArrayList<String> getSHQT(String s){
        ArrayList<String> strings = new ArrayList<>();
        if(s.equals("子")){
            strings.add("申");
            strings.add("辰");
        }
        if(s.equals("丑")){
            strings.add("巳");
            strings.add("酉");
        }
        if(s.equals("寅")){
            strings.add("午");
            strings.add("戌");
        }
        if(s.equals("卯")){
            strings.add("亥");
            strings.add("未");
        }
        if(s.equals("辰")){
            strings.add("子");
            strings.add("申");
        }
        if(s.equals("巳")){
            strings.add("丑");
            strings.add("酉");
        }
        if(s.equals("午")){
            strings.add("寅");
            strings.add("戌");
        }
        if(s.equals("未")){
            strings.add("卯");
            strings.add("亥");
        }
        if(s.equals("申")){
            strings.add("子");
            strings.add("辰");
        }
        if(s.equals("酉")){
            strings.add("巳");
            strings.add("丑");
        }
        if(s.equals("戌")){
            strings.add("寅");
            strings.add("午");
        }
        if(s.equals("亥")){
            strings.add("卯");
            strings.add("未");
        }
        return strings;
    }

    /**
     * 从 孟 仲 季 中取初传
     * @param s
     * @return
     */
    public static String getMZJ(String s,int pos){
        if(pos==1){
            //四孟位
            if(s.equals("寅")||s.equals("申")||s.equals("巳")||s.equals("亥")){
                return s;
            }else{
                return "";
            }
        }
        if(pos==2){
            //四仲位
            if(s.equals("子")||s.equals("午")||s.equals("卯")||s.equals("酉")){
                return s;
            }else{
                return "";
            }
        }
        if(pos==3){
            //四季位
            if(s.equals("辰")||s.equals("戌")||s.equals("丑")||s.equals("未")){
                return s;
            }else{
                return "";
            }
        }
        return "";
    }

    public static void setSC(TextView tvZhong, TextView tvMo) {
        String yy = Utils.getYY(MainActivity.s);
        if(yy.equals("阳")){
            for (int q = 0;q<MainActivity.arrayTP.size();q++){
                if(Utils.Zhi1[q].equals(MainActivity.s1)){
                    String s = MainActivity.arrayTP.get(q);
                    ZBLog.e("阳日取日支   地盘："+MainActivity.s1+"  所加天盘之支为："+s+"  故中传为："+s+"\n");
                    tvZhong.setText(s);
                }
            }
            String jg = getJG(MainActivity.s);
            for (int q = 0;q<MainActivity.arrayTP.size();q++){
                if(Utils.Zhi1[q].equals(jg)){
                    String s = MainActivity.arrayTP.get(q);
                    ZBLog.e("日干："+MainActivity.s+" 寄宫于："+jg+"   地盘："+jg+"  所加天盘之支为："+s+"  故末传为："+s+"\n");
                    tvMo.setText(s);
                }
            }
        }
        if(yy.equals("阴")){
            String jg = getJG(MainActivity.s);
            for (int q = 0;q<MainActivity.arrayTP.size();q++){
                if(Utils.Zhi1[q].equals(jg)){
                    String s = MainActivity.arrayTP.get(q);
                    ZBLog.e("阴日日干："+MainActivity.s+" 寄宫于："+jg+"   地盘："+jg+"  所加天盘之支为："+s+"  故中传为："+s+"\n");
                    tvZhong.setText(s);
                }
            }
            for (int q = 0;q<MainActivity.arrayTP.size();q++){
                if(Utils.Zhi1[q].equals(MainActivity.s1)){
                    String s = MainActivity.arrayTP.get(q);
                    ZBLog.e("日支   地盘："+MainActivity.s1+"  所加天盘之支为："+s+"  故末传为："+s+"\n");
                    tvMo.setText(s);
                }
            }
        }
    }

    public static void setBZ(TextView tvZhong, TextView tvMo) {
        String jg = getJG(MainActivity.s);
        String s = MainActivity.arrayTP.get(getDZPos(jg));
        ZBLog.e("日干："+MainActivity.s+" 寄宫于："+jg+"   地盘："+jg+"  所加天盘之支为："+s+"  故中传、末传为："+s+"\n");
        tvZhong.setText(s);
        tvMo.setText(s);
    }
}
