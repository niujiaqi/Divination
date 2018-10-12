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

        if (!xk1&& !xk2 && !xk3 && !xk4 && !sk1 && !sk2 && !sk3 && !sk4){
            ZBLog.e(" 四课中既没有下克上 也没有上克下\n");
            String shuXing = Utils.getShuXing(MainActivity.s);
            FirstPassBean firstPassBean = new FirstPassBean();
            firstPassBean.fa = "遥制法";

            boolean x1 = Utils.getXK(Utils.getShuXing(s1), shuXing);
            boolean x2 = Utils.getXK(Utils.getShuXing(s2), shuXing);
            boolean x3 = Utils.getXK(Utils.getShuXing(s3), shuXing);
            boolean x4 = Utils.getXK(Utils.getShuXing(s4), shuXing);
            if(x1 && !x2 && !x3 && !x4){
                ZBLog.e("只有第一课中上神 "+s1+" 克日干 "+MainActivity.s+"   此课为：蒿矢课\n");
                firstPassBean.CC = s1;
                firstPassBean.ke = "蒿矢课";
                return firstPassBean;
            }
            if(!x1 && x2 && !x3 && !x4){
                ZBLog.e("只有第二课中上神 "+s2+" 克日干 "+MainActivity.s+"   此课为：蒿矢课\n");
                firstPassBean.CC = s2;
                firstPassBean.ke = "蒿矢课";
                return firstPassBean;
            }
            if(!x1 && !x2 && x3 && !x4){
                ZBLog.e("只有第三课中上神 "+s3+" 克日干 "+MainActivity.s+"   此课为：蒿矢课\n");
                firstPassBean.CC = s3;
                firstPassBean.ke = "蒿矢课";
                return firstPassBean;
            }
            if(!x1 && !x2 && !x3 && x4){
                ZBLog.e("只有第四课中上神 "+s4+" 克日干 "+MainActivity.s+"   此课为：蒿矢课\n");
                firstPassBean.CC = s4;
                firstPassBean.ke = "蒿矢课";
                return firstPassBean;
            }
            if(x1 && x2 && x3 && x4){
                ZBLog.e("四课中上神全部和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && x2 && x3 && !x4){
                ZBLog.e("第一、二、三课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && x2 && !x3 && x4){
                ZBLog.e("第一、二、四课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && !x2 && x3 && x4){
                ZBLog.e("第一、三、四课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1)  && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1)  && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!x1 && x2 && x3 && x4){
                ZBLog.e("第二、三、四课中上神全部和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if( yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if( !yy.equals(yy2) && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第二、三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && x2 && !x3 && !x4){
                ZBLog.e("第一、二课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                if(yy.equals(yy1) && !yy.equals(yy2) ){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && !x2 && x3 && !x4){
                ZBLog.e("第一、三课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy1)&& !yy.equals(yy3)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(x1 && !x2 && !x3 && x4){
                ZBLog.e("第一、四课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第一、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!x1 && x2 && x3 && !x4){
                ZBLog.e("第二、三课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第二、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!x1 && x2 && !x3 && x4){
                ZBLog.e("第二、四课中上神和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第二、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!x1 && !x2 && x3 && x4){
                ZBLog.e("第三、四课中上神全部和日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                if(!yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用   此课为：蒿矢课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "蒿矢课";
                    return firstPassBean;
                }
                ZBLog.e("第三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            ZBLog.e("四课中无上神克日干\n");
            boolean xs1 = Utils.getXK(shuXing, Utils.getShuXing(s1));
            boolean xs2 = Utils.getXK(shuXing, Utils.getShuXing(s2));
            boolean xs3 = Utils.getXK(shuXing, Utils.getShuXing(s3));
            boolean xs4 = Utils.getXK(shuXing, Utils.getShuXing(s4));
            if(xs1 && !xs2 && !xs3 && !xs4){
                ZBLog.e("只有第一课中上神 "+s1+" 被日干 "+MainActivity.s+" 克    此课为：弹射课\n");
                firstPassBean.CC = s1;
                firstPassBean.ke = "弹射课";
                return firstPassBean;
            }
            if(!xs1 && xs2 && !xs3 && !xs4){
                ZBLog.e("只有第二课中上神 "+s2+" 被日干 "+MainActivity.s+" 克    此课为：弹射课\n");
                firstPassBean.CC = s2;
                firstPassBean.ke = "弹射课";
                return firstPassBean;
            }
            if(!xs1 && !xs2 && xs3 && !xs4){
                ZBLog.e("只有第三课中上神 "+s3+" 被日干 "+MainActivity.s+" 克    此课为：弹射课\n");
                firstPassBean.CC = s3;
                firstPassBean.ke = "弹射课";
                return firstPassBean;
            }
            if(!xs1 && !xs2 && !xs3 && xs4){
                ZBLog.e("只有第四课中上神 "+s4+" 被日干 "+MainActivity.s+" 克    此课为：弹射课\n");
                firstPassBean.CC = s4;
                firstPassBean.ke = "弹射课";
                return firstPassBean;
            }

            if(xs1 && xs2 && xs3 && xs4){
                ZBLog.e("四课中上神全部被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(xs1 && xs2 && xs3 && !xs4){
                ZBLog.e("第一、二、三课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(xs1 && xs2 && !xs3 && xs4){
                ZBLog.e("第一、二、四课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && !yy.equals(yy2) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(xs1 && !xs2 && xs3 && xs4){
                ZBLog.e("第一、三、四课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1)  && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1)  && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!xs1 && xs2 && xs3 && xs4){
                ZBLog.e("第二、三、四课中上神全部被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if( yy.equals(yy2) && !yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if( !yy.equals(yy2) && yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && !yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第二、三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }

            if(xs1 && xs2 && !xs3 && !xs4){
                ZBLog.e("第一、二课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy2 = Utils.getYY(s2);
                if(yy.equals(yy1) && !yy.equals(yy2) ){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy2)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、二、课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(xs1 && !xs2 && xs3 && !xs4){
                ZBLog.e("第一、三课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy1)&& !yy.equals(yy3)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(xs1 && !xs2 && !xs3 && xs4){
                ZBLog.e("第一、四课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy1 = Utils.getYY(s1);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy1) && !yy.equals(yy4)){
                    ZBLog.e("只有第一课中上神 "+s1+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s1;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy1) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第一、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!xs1 && xs2 && xs3 && !xs4){
                ZBLog.e("第二、三课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy3 = Utils.getYY(s3);
                if(yy.equals(yy2) && !yy.equals(yy3)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && yy.equals(yy3)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第二、三课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!xs1 && xs2 && !xs3 && xs4){
                ZBLog.e("第二、四课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy2 = Utils.getYY(s2);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy2) && !yy.equals(yy4)){
                    ZBLog.e("只有第二课中上神 "+s2+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s2;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy2) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第二、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
            if(!xs1 && !xs2 && xs3 && xs4){
                ZBLog.e("第三、四课中上神被日干 "+MainActivity.s+" 克\n");
                String yy = Utils.getYY(MainActivity.s);
                String yy3 = Utils.getYY(s3);
                String yy4 = Utils.getYY(s4);
                if(yy.equals(yy3) && !yy.equals(yy4)){
                    ZBLog.e("只有第三课中上神 "+s3+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s3;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                if(!yy.equals(yy3) && yy.equals(yy4)){
                    ZBLog.e("只有第四课中上神 "+s4+" 与日干 "+MainActivity.s+" 相比用    此课为：弹射课\n");
                    firstPassBean.CC = s4;
                    firstPassBean.ke = "弹射课";
                    return firstPassBean;
                }
                ZBLog.e("第三、四课中上神 与日干相比用 数量大于等于2 或者没有一个与日干相比用  遥克法无法取初传\n");
            }
        }

        ZBLog.e("------遥克法未求得初传---\n\n");
        return null;
    }


    /**
     * 昂星法 求初传
     */
    public static FirstPassBean getAX(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4) {

        if(!xk1&& !xk2 && !xk3 && !xk4 && !sk1 && !sk2 && !sk3 && !sk4){
            FirstPassBean firstPassBean = new FirstPassBean();
            firstPassBean.fa = "昂星法";
            firstPassBean.CC = "";

            String yy = Utils.getYY(MainActivity.s);
            ZBLog.e(" 日干："+MainActivity.s+"    属："+yy);
            ZBLog.e(" 四课中既无下克上，又无上克下；既无“神遥克日” 又无 “日遥克神”");
            if(yy.equals("阳")){
                for (int q = 0;q<MainActivity.arrayTP.size();q++){
                    if(Utils.Zhi1[q].equals("酉")){
                        String s = MainActivity.arrayTP.get(q);
                        firstPassBean.CC = s;
                        ZBLog.e("地盘酉所加天盘之支为："+s+"  故初传为："+s+"\n");
                        return firstPassBean;
                    }
                }
            }
            if(yy.equals("阴")){
                for (int q = 0;q<Utils.Zhi1.length;q++){
                    if(MainActivity.arrayTP.get(q).equals("酉")){
                        String s = Utils.Zhi1[q];
                        firstPassBean.CC = s;
                        ZBLog.e("天盘酉所加地盘之支为："+s+"  故初传为："+s+"\n");
                        return firstPassBean;
                    }
                }
            }
        }
        ZBLog.e("------昂星法未求得初传---\n\n");
        return null;
    }


    /**
     * 别责法 求初传
     */
    public static FirstPassBean getBZ(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                      boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                      String s1, String s2, String s3, String s4,
                                      String x1, String x2, String x3, String x4) {

        if(!xk1&& !xk2 && !xk3 && !xk4 && !sk1 && !sk2 && !sk3 && !sk4){
            FirstPassBean firstPassBean = new FirstPassBean();
            firstPassBean.fa = "别责法";
            firstPassBean.CC = "";
            String yy = Utils.getYY(MainActivity.s);
            ZBLog.e(" 日干："+MainActivity.s+"    属："+yy);
            if((s1.equals(s2)&& !s1.equals(s3)&& !s1.equals(s4))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s2))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s3))&& !Utils.getShuXing(s1).equals(Utils.getShuXing(s4))&&
                    !Utils.getShuXing(s3).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x2))){
                    ZBLog.e("一、二课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s1.equals(s3)&& !s1.equals(s2)&& !s1.equals(s4))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s3))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s2))&& !Utils.getShuXing(s1).equals(Utils.getShuXing(s4))&&
                    !Utils.getShuXing(s2).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x3))){
                    ZBLog.e("一、三课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s1.equals(s4)&& !s1.equals(s2)&& !s1.equals(s3))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s4))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s2))&& !Utils.getShuXing(s1).equals(Utils.getShuXing(s3))&&
                    !Utils.getShuXing(s2).equals(Utils.getShuXing(s3)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x4))){
                    ZBLog.e("一、四课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s2.equals(s3)&&!s1.equals(s4))||(Utils.getShuXing(s2).equals(Utils.getShuXing(s3))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x2).equals(Utils.getShuXing(x3))){
                    ZBLog.e("二、三课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s2.equals(s4)&&!s1.equals(s3))||(Utils.getShuXing(s2).equals(Utils.getShuXing(s3))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s3)))){
                if(Utils.getShuXing(x2).equals(Utils.getShuXing(x4))){
                    ZBLog.e("二、四课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s3.equals(s4)&&!s1.equals(s2))||(Utils.getShuXing(s3).equals(Utils.getShuXing(s4))&&
                    !Utils.getShuXing(s1).equals(Utils.getShuXing(s2)))){
                if(Utils.getShuXing(x3).equals(Utils.getShuXing(x4))){
                    ZBLog.e("三、四课相等，实际只有三课，并且三课均无克");
                    String bzcc = getBZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
        }
        ZBLog.e("------别责法未求得初传---\n\n");
        return null;
    }


    /**
     * 八专注 求初传
     */
    public static FirstPassBean getBAZ(boolean xk1, boolean xk2, boolean xk3, boolean xk4,
                                       boolean sk1, boolean sk2, boolean sk3, boolean sk4,
                                       String s1, String s2, String s3, String s4,
                                       String x1, String x2, String x3, String x4) {
        if(!xk1&& !xk2 && !xk3 && !xk4 && !sk1 && !sk2 && !sk3 && !sk4){
            FirstPassBean firstPassBean = new FirstPassBean();
            firstPassBean.fa = "八专注";
            firstPassBean.CC = "";
            String yy = Utils.getYY(MainActivity.s);
            ZBLog.e(" 日干："+MainActivity.s+"    属："+yy);
            if((s1.equals(s2)&& s3.equals(s4))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s2))&&
                    Utils.getShuXing(s3).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x2))&&Utils.getShuXing(x3).equals(Utils.getShuXing(x4))){
                    ZBLog.e("一、二课相等，三、四课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s1.equals(s3)&& s2.equals(s4))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s3))&&
                    Utils.getShuXing(s2).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x3))&&Utils.getShuXing(x2).equals(Utils.getShuXing(x4))){
                    ZBLog.e("一、三课相等，二、四课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s1.equals(s4)&& s2.equals(s3))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s4))&&
                    Utils.getShuXing(s2).equals(Utils.getShuXing(s3)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x4))&&Utils.getShuXing(x2).equals(Utils.getShuXing(x3))){
                    ZBLog.e("一、四课相等，二、三课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }

            if((s1.equals(s2)&& s1.equals(s3))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s2))&&
                    Utils.getShuXing(s1).equals(Utils.getShuXing(s3)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x2))&&Utils.getShuXing(x1).equals(Utils.getShuXing(x3))){
                    ZBLog.e("一、二、三课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }

            if((s1.equals(s3)&& s1.equals(s4))||(Utils.getShuXing(s1).equals(Utils.getShuXing(s3))&&
                    Utils.getShuXing(s1).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x1).equals(Utils.getShuXing(x3))&&Utils.getShuXing(x1).equals(Utils.getShuXing(x4))){
                    ZBLog.e("一、三、四课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
            if((s2.equals(s3)&& s2.equals(s4))||(Utils.getShuXing(s2).equals(Utils.getShuXing(s3))&&
                    Utils.getShuXing(s2).equals(Utils.getShuXing(s4)))){
                if(Utils.getShuXing(x2).equals(Utils.getShuXing(x3))&&Utils.getShuXing(x2).equals(Utils.getShuXing(x4))){
                    ZBLog.e("二、三、四课相等，实际只有两课，并且两课均无克");
                    String bzcc = getBAZCC(yy);
                    firstPassBean.CC = bzcc;
                    return firstPassBean;
                }
            }
        }
        ZBLog.e("------八专注未求得初传---\n\n");
        return null;
    }

    public static String getBAZCC(String yy){
        if(yy.equals("阳")){
            String jg = Utils.getJG(MainActivity.s);
            int dzPos = Utils.getDZPos(jg);
            String s = "";
            if(dzPos<=9){
                s = MainActivity.arrayTP.get(dzPos+2);
            }else if(dzPos==10){
                s = MainActivity.arrayTP.get(0);
            }else if(dzPos==11){
                s = MainActivity.arrayTP.get(1);
            }
            ZBLog.e("阳日取日干："+MainActivity.s+"  的寄宫："+jg+"  地盘："+jg+"  对应天盘："+
                    MainActivity.arrayTP.get(dzPos)+"  顺数三位，故取："+s+"  为初传");
            return s;
        }
        if(yy.equals("阴")){
            String jg = Utils.getJG(MainActivity.s);
            int dzPos = Utils.getDZPos(jg);
            String s = "";
            if(dzPos>=2){
                s = MainActivity.arrayTP.get(dzPos - 2);
            }else if(dzPos==1){
                s = MainActivity.arrayTP.get(11);
            }else if(dzPos==0){
                s = MainActivity.arrayTP.get(10);
            }
            String s1 = MainActivity.arrayTP.get(Utils.getDZPos(s));
            ZBLog.e("阴日取日干："+MainActivity.s+"  的寄宫："+jg+"  地盘："+jg+"  逆数三位对应天盘："+
                    s+"  地盘："+s+"  对应天盘："+s1+" ，故取："+s1+"  为初传");
            return s1;
        }
        return "";
    }

    public static String getBZCC(String yy){
        if(yy.equals("阳")){
            String tgh = Utils.getTGH(MainActivity.s);
            String jg = Utils.getJG(tgh);
            String s = MainActivity.arrayTP.get(Utils.getDZPos(jg));
            ZBLog.e("阳日取日干："+MainActivity.s+"  的合神："+tgh+"  寄宫于："+jg+"  地盘："+jg+"  对应天盘："+s+"  故取初传为："+s);
            return s;
        }
        if(yy.equals("阴")){
            ArrayList<String> shqt = Utils.getSHQT(MainActivity.s1);
            ArrayList<String> arrayBZ = Utils.getArrayBZ(MainActivity.s1, MainActivity.arrayTP);
            for (int i = 0;i<arrayBZ.size();i++){
                if(arrayBZ.get(i).equals(shqt.get(0))){
                    ZBLog.e("阴日取日支："+MainActivity.s1+"  的三合神前一位："+shqt.get(0)+"  故取初传为："+shqt.get(0));
                    return shqt.get(0);
                }
                if(arrayBZ.get(i).equals(shqt.get(1))){
                    ZBLog.e("阴日取日支："+MainActivity.s1+"  的三合神前一位："+shqt.get(1)+"  故取初传为："+shqt.get(1));
                    return shqt.get(1);
                }
            }
        }
        return "";
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
