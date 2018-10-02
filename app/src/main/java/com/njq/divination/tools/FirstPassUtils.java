package com.njq.divination.tools;

import android.util.Log;

import com.njq.divination.MainActivity;
import com.njq.divination.bean.EvenBean;
import com.njq.divination.bean.FJGBean;
import com.njq.divination.bean.FirstPassBean;
import com.njq.divination.bean.JKBean;
import com.njq.divination.bean.PosBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 获取初传 工具类
 */
public class FirstPassUtils {

    private static String TAG = "FirstPassUtils";
    private static String ss1;
    private static String ss4;
    private static String ss3;
    private static String ss2;

    /**
     * 制败法取课
     * @param xk1  下是否克上
     * @param xk2
     * @param xk3
     * @param xk4
     * @param sk1  上是否克下
     * @param sk2
     * @param sk3
     * @param sk4
     * @param s1    课的地支
     * @param s2
     * @param s3
     * @param s4
     * @return
     *
     * 制败法 求初传
     */
    public static FirstPassBean getZB(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4){
        ss1 = s1;
        ss2 = s2;
        ss3 = s3;
        ss4 = s4;
        FirstPassBean firstPassBean = new FirstPassBean();
        firstPassBean.fa = "制败法";
        firstPassBean.CC = "";

        if(xk1 && !xk2 && !xk3 && !xk4){
            ZBLog.e("第一课下克上，其他三课无下克上\n");
            firstPassBean.CC = s1;
        }
        if(!xk1 && xk2 && !xk3 && !xk4){
            ZBLog.e("第二课下克上，其他三课无下克上\n");
            firstPassBean.CC = s2;
        }
        if(!xk1 && !xk2 && xk3 && !xk4){
            ZBLog.e("第三课下克上，其他三课无下克上\n");
            firstPassBean.CC = s3;
        }
        if(!xk1 && !xk2 && !xk3 && xk4){
            ZBLog.e("第四课下克上，其他三课无下克上\n");
            firstPassBean.CC = s4;
        }

        //TODO  无上克下   且只有一个下克上
        if(!sk1 && !sk2 && !sk3 && !sk4 && !firstPassBean.CC.equals("")){
            ZBLog.e(" 无上克下   且只有一个下克上\n");
            ZBLog.e(" 此课为：   重审课中的始入课\n");
            firstPassBean.ke = "重审课";
            firstPassBean.bm = "始入课";
            firstPassBean.JJ = ShareUtils.CSK;
            return firstPassBean;
        }

        //TODO  有上克下   且只有一个下克上
        if(!firstPassBean.CC.equals("")){
            ZBLog.e(" 有上克下   且只有一个下克上\n");
            ZBLog.e(" 此课为：   重审课\n");
            firstPassBean.ke = "重审课";
            firstPassBean.JJ = ShareUtils.CSK;
            return firstPassBean;
        }

        //TODO 没有下克上
        if(!xk1 && !xk2 && !xk3 && !xk4){
            if(sk1 && !sk2 && !sk3 && !sk4){
                ZBLog.e(" 无下克上   且只有第一课上克下\n");
                firstPassBean.CC = s1;
            }
            if(!sk1 && sk2 && !sk3 && !sk4){
                ZBLog.e(" 无下克上   且只有第二课上克下\n");
                firstPassBean.CC = s2;
            }
            if(!sk1 && !sk2 && sk3 && !sk4){
                ZBLog.e(" 无下克上   且只有第三课上克下\n");
                firstPassBean.CC = s3;
            }
            if(!sk1 && !sk2 && !sk3 && sk4){
                ZBLog.e(" 无下克上   且只有第四课上克下\n");
                firstPassBean.CC = s4;
            }
        }

        //TODO 没有下克上   且只有一个上克下
        if(!firstPassBean.CC.equals("")){
            ZBLog.e(" 无下克上   且只有一个上克下\n");
            ZBLog.e(" 此课为：元首课\n");
            firstPassBean.ke = "元首课";
            firstPassBean.JJ = ShareUtils.YSK;
//            firstPassBean.JX = BadLuckUtils.YSK;
            return firstPassBean;
        }
        ZBLog.e("------制败法未求得初传---\n\n");
        return null;
    }

    /**
     * 比用法 求初传
     */
    public static FirstPassBean getBY(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4) {
        FirstPassBean firstPassBean = new FirstPassBean();
        firstPassBean.fa = "比用法";
        firstPassBean.CC = "";

        String yy = Utils.getYY(MainActivity.s);
        ZBLog.e(" 日干："+MainActivity.s+"    属："+yy);
        ArrayList<String> strings = new ArrayList<>();

        if(xk1 && xk2 && xk3 && xk4){
            ZBLog.e(" 四课均为下克上\n");
            strings.add(s1);
            strings.add(s2);
            strings.add(s3);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk1 && xk2 && xk3){
            ZBLog.e(" 一、二、三课为下克上\n");
            strings.add(s1);
            strings.add(s2);
            strings.add(s3);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk1 && xk2 && xk4){
            ZBLog.e(" 一、二、四课为下克上\n");
            strings.add(s1);
            strings.add(s2);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk2 && xk3 && xk4){
            ZBLog.e(" 二、三、四课为下克上\n");
            strings.add(s2);
            strings.add(s3);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk1 && xk2){
            ZBLog.e(" 一、二课为下克上\n");
            strings.add(s1);
            strings.add(s2);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk1 && xk3){
            ZBLog.e(" 一、三课为下克上\n");
            strings.add(s1);
            strings.add(s3);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk1 && xk4){
            ZBLog.e(" 一、四课为下克上\n");
            strings.add(s1);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk2 && xk3){
            ZBLog.e(" 二、三课为下克上\n");
            strings.add(s2);
            strings.add(s3);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk2 && xk4){
            ZBLog.e(" 二、四课为下克上\n");
            strings.add(s2);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }else if(xk3 && xk4){
            ZBLog.e(" 三、四课为下克上\n");
            strings.add(s3);
            strings.add(s4);
            firstPassBean.CC = getBYH(yy,strings);
        }

        //TODO 多个下克上中  只有一个地支和 日的天干相同属性
        if(!firstPassBean.CC.equals("")){
            ZBLog.e(" 多个下克上中  只有一个地支和 日的天干属性相同\n");
            ZBLog.e(" 此课为：   比用课\n");
            firstPassBean.ke = "比用课";
            firstPassBean.JJ = ShareUtils.BYK;
            return firstPassBean;
        }

        if(!xk1 && !xk2 && !xk3 && !xk4){
            ZBLog.e(" 四课中无下克上\n");
            if(sk1 && sk2 && sk3 && sk4){
                ZBLog.e(" 四课均为上克下\n");
                strings.add(s1);
                strings.add(s2);
                strings.add(s3);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk1 && sk2 && sk3){
                ZBLog.e(" 一、二、三课为上克下\n");
                strings.add(s1);
                strings.add(s2);
                strings.add(s3);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk1 && sk2 && sk4){
                ZBLog.e(" 一、二、四课为上克下\n");
                strings.add(s1);
                strings.add(s2);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk2 && sk3 && sk4){
                ZBLog.e(" 二、三、四课为上克下\n");
                strings.add(s2);
                strings.add(s3);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk1 && sk2){
                ZBLog.e(" 一、二课为上克下\n");
                strings.add(s1);
                strings.add(s2);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk1 && sk3){
                ZBLog.e(" 一、三课为上克下\n");
                strings.add(s1);
                strings.add(s3);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk1 && sk4){
                ZBLog.e(" 一、四课为上克下\n");
                strings.add(s1);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk2 && sk3){
                ZBLog.e(" 二、三课为上克下\n");
                strings.add(s2);
                strings.add(s3);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk2 && sk4){
                ZBLog.e(" 二、四课为上克下\n");
                strings.add(s2);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }else if(sk3 && sk4){
                ZBLog.e(" 三、四课为上克下\n");
                strings.add(s3);
                strings.add(s4);
                firstPassBean.CC = getBYH(yy,strings);
            }
        }

        //TODO 无下克上  但有多个上克下  只有一个地支和 日的天干相同属性
        if(!firstPassBean.CC.equals("")){
            ZBLog.e(" 无下克上  但有多个上克下  只有一个地支和 日的天干属性相同\n");
            ZBLog.e(" 此课为：   比用课\n");
            firstPassBean.ke = "比用课";
            firstPassBean.JJ = ShareUtils.BYK;
            return firstPassBean;
        }
        ZBLog.e("------比用法未求得初传---\n\n");
        return null;
    }
    /**
     * 涉害法 求初传
     */
    public static FirstPassBean getSH(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4) {
        FirstPassBean firstPassBean = new FirstPassBean();
        firstPassBean.fa = "涉害法";
        firstPassBean.CC = "";

        String yy = Utils.getYY(MainActivity.s);
        ZBLog.e(" 日干："+MainActivity.s+"    属："+yy);
        ArrayList<PosBean> strings = new ArrayList<>();

        if((xk1 && xk2 && xk3 && xk4)||(sk1 && sk2 && sk3 && sk4)){
            ZBLog.e(" 四课均为下克上 或 四课均为上克下");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk1 && xk2 && xk3)||(sk1 && sk2 && sk3)){
            ZBLog.e("一、二、三课下克上 或 上克下\n");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk1 && xk2 && xk4)||(sk1 && sk2 && sk4)){
            ZBLog.e("一、二、四课下克上 或 上克下\n");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk2 && xk3 && xk4)||(sk2 && sk3 && sk4)){
            ZBLog.e("二、三、四课下克上 或 上克下\n");
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk1 && xk2)||(sk1 && sk2)){
            ZBLog.e("一、二课下克上 或 上克下\n");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk1 && xk3)||(sk1 && sk3)){
            ZBLog.e("一、三课下克上 或 上克下\n");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk1 && xk4)||(sk1 && sk4)){
            ZBLog.e("一、四课下克上 或 上克下\n");
            PosBean posBean1 = new PosBean();
            posBean1.pos = 1;
            posBean1.zi = s1;
            strings.add(posBean1);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk2 && xk3)||(sk2 && sk3)){
            ZBLog.e("二、三课下克上 或 上克下\n");
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk2 && xk4)||(sk2 && sk4)){
            ZBLog.e("二、四课下克上 或 上克下\n");
            PosBean posBean2 = new PosBean();
            posBean2.pos = 2;
            posBean2.zi = s2;
            strings.add(posBean2);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }else if((xk3 && xk4)||(sk3 && sk4)){
            ZBLog.e("三、四课下克上 或 上克下\n");
            PosBean posBean3 = new PosBean();
            posBean3.pos = 3;
            posBean3.zi = s3;
            strings.add(posBean3);
            PosBean posBean4 = new PosBean();
            posBean4.pos = 4;
            posBean4.zi = s4;
            strings.add(posBean4);
            JKBean shh = getSHH(yy, strings);
            if(shh!=null){
                firstPassBean.CC = shh.cc;
                firstPassBean.JH = shh.jike;
            }
        }

        if(!firstPassBean.CC.equals("")){
            ZBLog.e(" 此课为：   涉害课\n");
            firstPassBean.ke = "涉害课";
            firstPassBean.JJ = ShareUtils.SHK;
            return firstPassBean;
        }
        ZBLog.e("------涉害法未求得初传---\n\n");
        return null;
    }

    /**
     * 遥制法 求初传
     */
    public static FirstPassBean getYZ(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4) {

        return null;
    }

    private static String getBYH(String yy, ArrayList<String> strings) {
        int j = 0;
        String cc = "";
        for (int i = 0;i<strings.size();i++){
            String s = strings.get(i);
            if(yy.equals(Utils.getYY(s))){
                j = j+1;
                cc = s;
            }
        }
        if(j==1){
            ZBLog.e(" 且只有一个："+cc+"   和日干阴阳属性相等\n");
            return cc;
        }
        ZBLog.e(" 没有一个和日干阴阳属性相等  或者 相等数量大于一个  均不满足比用课的要求\n");
        return "";
    }

    private static JKBean getSHH(String yy, ArrayList<PosBean> strings) {
        int j = 0;
        ArrayList<PosBean> strings1 = new ArrayList<>();
        String cc = "";
        for (int i = 0;i<strings.size();i++){
            PosBean posBean = strings.get(i);
            if(yy.equals(Utils.getYY(posBean.zi))){
                j = j+1;
                strings1.add(posBean);
            }
        }

        if(strings1.size()>1){
            //当属性与日天干  相同且不止一个    取克多者为初传
            ZBLog.e(" 当四课中有多个地支与日的天干相同  取克多者为初传\n");
            return getKD(strings1);
        }
        ZBLog.e(" 没有一个地支和日干阴阳属性相等  不满足涉害课的要求\n");
        return null;
    }

    /**
     * 计算谁的克多
     * @return
     * @param strings1
     */
    public static JKBean getKD(ArrayList<PosBean> strings1){
        ArrayList<JKBean> integers = new ArrayList<JKBean>();
        for (int i = 0;i<strings1.size();i++){
            PosBean posBean = strings1.get(i);
            for (int j = 0;j<MainActivity.arrayTP.size();j++){
                if(posBean.zi.equals(MainActivity.arrayTP.get(j))){
                    ArrayList<String> strings = new ArrayList<>();
                    boolean a = false;
                    for (int q = j+1;q<12;q++){
                       if(posBean.zi.equals(Utils.Zhi1[q])){
                            q=12;
                            a = true;
                        }else if(!posBean.zi.equals(Utils.Zhi1[q])){
                           strings.add(Utils.Zhi1[q]);
                       }
                    }
                    if(!a){
                        for (int q = 0;q<12;q++){
                            if(posBean.zi.equals(Utils.Zhi1[q])){
                                q=12;
                            }else if(!posBean.zi.equals(Utils.Zhi1[q])){
                                strings.add(Utils.Zhi1[q]);
                            }
                        }
                    }
                    JKBean jk = getJK(posBean,strings);
                    integers.add(jk);
                }
            }

        }

        if(integers.size()>0) {
            Collections.sort(integers,new Student());
            ArrayList<JKBean> strings = new ArrayList<>();
            if(integers.get(integers.size()-1).jike==0){
                ZBLog.e(" 相克的数量为0\n");
                return null;
            }else if(integers.get(integers.size()-1).jike>integers.get(integers.size()-2).jike){
                ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"克数最多  取为初传\n");
                return integers.get(integers.size()-1);
            }else if((integers.size()==2 && integers.get(integers.size()-1).jike == integers.get(integers.size()-2).jike)){
                ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                        " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"克数一样多，从孟仲季中选取\n");
                strings.add(integers.get(integers.size()-1));
                strings.add(integers.get(integers.size()-2));
                return getMZJ(strings);
            }else if((integers.size()==3 && integers.get(integers.size()-1).jike == integers.get(integers.size()-2).jike)){
                if(integers.get(integers.size()-2).jike == integers.get(integers.size()-3).jike){
                    ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                            " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"   和\n" +
                            " 第"+integers.get(integers.size()-3).pos+"课："+integers.get(integers.size()-3).cc+"克数一样多，从孟仲季中选取\n");
                    strings.add(integers.get(integers.size()-1));
                    strings.add(integers.get(integers.size()-2));
                    strings.add(integers.get(integers.size()-3));
                    return getMZJ(strings);
                }
                ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                        " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"克数一样多，从孟仲季中选取\n");
                strings.add(integers.get(integers.size()-1));
                strings.add(integers.get(integers.size()-2));
                return getMZJ(strings);
            }else if(integers.size()==4 && integers.get(integers.size()-1).jike == integers.get(integers.size()-2).jike){
                if(integers.get(integers.size()-2).jike == integers.get(integers.size()-3).jike){
                    if(integers.get(integers.size()-3).jike == integers.get(integers.size()-4).jike){
                        ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                                " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"   和\n" +
                                " 第"+integers.get(integers.size()-3).pos+"课："+integers.get(integers.size()-3).cc+"   和\n" +
                                " 第"+integers.get(integers.size()-4).pos+"课："+integers.get(integers.size()-4).cc+"克数一样多，从孟仲季中选取\n");
                        strings.add(integers.get(integers.size()-1));
                        strings.add(integers.get(integers.size()-2));
                        strings.add(integers.get(integers.size()-3));
                        strings.add(integers.get(integers.size()-4));
                        return getMZJ(strings);
                    }
                    ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                            " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"   和\n" +
                            " 第"+integers.get(integers.size()-3).pos+"课："+integers.get(integers.size()-3).cc+"克数一样多，从孟仲季中选取\n");
                    strings.add(integers.get(integers.size()-1));
                    strings.add(integers.get(integers.size()-2));
                    strings.add(integers.get(integers.size()-3));
                    return getMZJ(strings);
                }
                ZBLog.e(" 第"+integers.get(integers.size()-1).pos+"课："+integers.get(integers.size()-1).cc+"   和\n" +
                        " 第"+integers.get(integers.size()-2).pos+"课："+integers.get(integers.size()-2).cc+"克数一样多，从孟仲季中选取\n");
                strings.add(integers.get(integers.size()-1));
                strings.add(integers.get(integers.size()-2));
                return getMZJ(strings);
            }
        }
        ZBLog.e(" 这里不会执行到  FirstPassUtils类 getKD（）方法最后一行\n");
        return null;
    }

    private static JKBean getMZJ(ArrayList<JKBean> strings){
        ArrayList<JKBean> strings1 = new ArrayList<>();
        for (int q = 0;q<strings.size();q++){
            String cc = strings.get(q).cc;
            String mzj = Utils.getMZJ(cc, 1);
            if(!mzj.equals("")){
                strings1.add(strings.get(q));
            }
        }
        if(strings1.size()!=0){
            //说明在孟位取到了
            ZBLog.e("孟位中取到了");
            String yy = Utils.getYY(MainActivity.s);
            return getMZ(yy, strings1, "孟位");
        }
        ZBLog.e("孟位没取到，去仲位取");
        // 没取到   去仲位取
        for (int q = 0;q<strings.size();q++){
            String cc = strings.get(q).cc;
            String mzj = Utils.getMZJ(cc, 2);
            if(!mzj.equals("")){
                strings1.add(strings.get(q));
            }
        }
        if(strings1.size()!=0){
            //说明在仲位取到了
            ZBLog.e("仲位中取到了");
            String yy = Utils.getYY(MainActivity.s);
            return getMZ(yy, strings1, "仲位");
        }
        ZBLog.e("仲位没取到，去季位取");
        // 没取到   去季位取
        for (int q = 0;q<strings.size();q++){
            String cc = strings.get(q).cc;
            String mzj = Utils.getMZJ(cc, 3);
            if(!mzj.equals("")){
                strings1.add(strings.get(q));
            }
        }

        if(strings1.size()!=0){
            //说明在季位取到了
            ZBLog.e("季位中取到了");
            String yy = Utils.getYY(MainActivity.s);
            return getMZ(yy, strings1, "季位");
        }
        ZBLog.e("孟仲季  中均为取到相应的值");
        return null;
    }

    /**
     * 判断有几个克
     * @param s
     * @param strings
     * @return
     */
    private static JKBean getJK(PosBean s, ArrayList<String> strings){
        String shuXing = Utils.getShuXing(s.zi);
        JKBean jkBean = new JKBean();
        jkBean.cc = s.zi;
        jkBean.pos = s.pos;
        ArrayList<String> nxk = new ArrayList<>();
        int i = 0;
        for (int q = 0;q<strings.size();q++){
            boolean xk = Utils.getXK(shuXing, strings.get(q));
            if(xk){
                i = i+1;
                nxk.add(strings.get(q));
            }
        }
        ArrayList<String> strings1 = new ArrayList<>();
        for (int v = 0;v<strings.size();v++){
            String s1 = strings.get(v);
            FJGBean fjg = Utils.getFJG(s1);
            if(fjg!=null){
                if(fjg.mun==1){
                    strings1.add(fjg.zi);
                }else{
                    strings1.add(fjg.zi);
                    strings1.add(fjg.zi2);
                }
            }
        }
        int n = 0;
        for (int q = 0;q<strings1.size();q++){
            boolean xk = Utils.getXK(shuXing, strings1.get(q));
            if(xk){
                n = n+1;
                i = i+1;
                nxk.add(strings.get(q));
            }
        }
        ZBLog.e(" 第"+s.pos+"课："+s.zi+"  共有 "+i+" 个克，其中包含天干 "+n+" 个克\n");
        if(nxk.size()>0){
            String a = "";
            for (int q = 0;q<nxk.size();q++){
                if(q == nxk.size()-1){
                    a = a+nxk.get(q);
                }else{
                    a = a+nxk.get(q)+"、";
                }
            }
            ZBLog.e(" 分别为："+a+"\n");
        }
        jkBean.jike = i;
        return jkBean;
    }

    static class Student implements Comparator<JKBean> {

        @Override
        public int compare(JKBean integer, JKBean t1) {
            if(integer.jike>t1.jike){
                return 1;
            }else if(integer.jike==t1.jike){
                return 0;
            }
            return -1;
        }

    }


    private static JKBean getMZ(String yy, ArrayList<JKBean> strings1, String wei){
        if(strings1.size()==1){
            return strings1.get(0);
        }else if(strings1.size()==2){
            if(yy.equals("阳")){
                if(strings1.get(0).pos==1||strings1.get(0).pos==2){
                    if(strings1.get(1).pos==2||strings1.get(1).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==2||strings1.get(1).pos==1){
                    ZBLog.e(" 日天干为阳 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                    return strings1.get(1);
                }
                ZBLog.e(" 日天干为阳 一、二两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }else{
                if(strings1.get(0).pos==3||strings1.get(0).pos==4){
                    if(strings1.get(1).pos==4||strings1.get(1).pos==3){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==4||strings1.get(1).pos==3){
                    ZBLog.e(" 日天干为阴 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                    return strings1.get(1);
                }
                ZBLog.e(" 日天干为阴  三、四两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }
        }else if(strings1.size()==3){
            if(yy.equals("阳")){
                if(strings1.get(0).pos==1||strings1.get(0).pos==2){
                    if(strings1.get(1).pos==2||strings1.get(1).pos==1||strings1.get(2).pos==2||strings1.get(2).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==2||strings1.get(1).pos==1){
                    if(strings1.get(2).pos==2||strings1.get(2).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                        return strings1.get(1);
                    }
                }else if(strings1.get(2).pos==2||strings1.get(2).pos==1){
                    ZBLog.e(" 日天干为阳 取第"+strings1.get(2).pos+"课："+strings1.get(2).cc+"   为初传\n");
                    return strings1.get(2);
                }
                ZBLog.e(" 日天干为阳 一、二两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }else{
                if(strings1.get(0).pos==3||strings1.get(0).pos==4){
                    if(strings1.get(1).pos==3||strings1.get(1).pos==4||strings1.get(2).pos==3||strings1.get(2).pos==4){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==3||strings1.get(1).pos==4){
                    if(strings1.get(2).pos==3||strings1.get(2).pos==4){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                        return strings1.get(1);
                    }
                }else if(strings1.get(2).pos==3||strings1.get(2).pos==4){
                    ZBLog.e(" 日天干为阴 取第"+strings1.get(2).pos+"课："+strings1.get(2).cc+"   为初传\n");
                    return strings1.get(2);
                }
                ZBLog.e(" 日天干为阴  三、四两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }
        }else if(strings1.size()==4){
            //TODO=====================================================如果从孟位取出4个========================================================
            if(yy.equals("阳")){
                if(strings1.get(0).pos==1||strings1.get(0).pos==2){
                    if(strings1.get(1).pos==2||strings1.get(1).pos==1||strings1.get(2).pos==2||strings1.get(2).pos==1||strings1.get(3).pos==2||strings1.get(3).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==2||strings1.get(1).pos==1){
                    if(strings1.get(2).pos==2||strings1.get(2).pos==1||strings1.get(3).pos==2||strings1.get(3).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                        return strings1.get(1);
                    }
                }else if(strings1.get(2).pos==2||strings1.get(2).pos==1){
                    if(strings1.get(3).pos==2||strings1.get(3).pos==1){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阳取 一、二两课，但是一二两课都为阳，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阳 取第"+strings1.get(2).pos+"课："+strings1.get(2).cc+"   为初传\n");
                        return strings1.get(2);
                    }
                }else if(strings1.get(3).pos==2||strings1.get(3).pos==1){
                    ZBLog.e(" 日天干为阳 取第"+strings1.get(3).pos+"课："+strings1.get(3).cc+"   为初传\n");
                    return strings1.get(3);
                }
                ZBLog.e(" 日天干为阳 一、二两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }else{
                if(strings1.get(0).pos==3||strings1.get(0).pos==4){
                    if(strings1.get(1).pos==3||strings1.get(1).pos==4||strings1.get(2).pos==3||strings1.get(2).pos==4||strings1.get(3).pos==3||strings1.get(3).pos==4){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(0).pos+"课："+strings1.get(0).cc+"   为初传\n");
                        return strings1.get(0);
                    }
                }else if(strings1.get(1).pos==3||strings1.get(1).pos==4){
                    if(strings1.get(2).pos==3||strings1.get(2).pos==4||strings1.get(3).pos==3||strings1.get(3).pos==4){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(1).pos+"课："+strings1.get(1).cc+"   为初传\n");
                        return strings1.get(1);
                    }
                }else if(strings1.get(2).pos==3||strings1.get(2).pos==4){
                    if(strings1.get(3).pos==3||strings1.get(3).pos==4){
                        EvenBean evenBean = new EvenBean();
                        evenBean.type = 1;
                        evenBean.text = "涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传";
                        ZBLog.e(" 涉害法求初传，两个克数相等且都在"+wei+"，根据日干判断为阴取 三、四两课，但是三四两课都为阴，不知道取哪个用作初传\n");
                        EventBus.getDefault().post(evenBean);
                        return null;
                    }else{
                        ZBLog.e(" 日天干为阴 取第"+strings1.get(2).pos+"课："+strings1.get(2).cc+"   为初传\n");
                        return strings1.get(2);
                    }
                }else if(strings1.get(3).pos==3||strings1.get(3).pos==4){
                    ZBLog.e(" 日天干为阴 取第"+strings1.get(3).pos+"课："+strings1.get(3).cc+"   为初传\n");
                    return strings1.get(3);
                }
                ZBLog.e(" 日天干为阴  三、四两课中都没有取到相应 "+wei+" 的值\n");
                return null;
            }
        }
        ZBLog.e(" 这里不会执行到  FirstPassUtils类 getMZ（）方法最后一行\n");
        return null;
    }
}