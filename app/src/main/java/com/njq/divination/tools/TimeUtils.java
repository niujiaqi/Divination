package com.njq.divination.tools;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.blankj.utilcode.util.*;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：时间工具类
 * 作者：钮家齐
 * 时间: 2018-09-18 17:17
 */

public class TimeUtils {


    private final Context activity;
    int[] lunarInfo = new int[]{0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260,
            0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6,
            0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0,
            0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54,
            0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50,
            0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0,
            0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0,
            0x0a5b0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6,
            0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
            0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4,
            0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6, 0x095b0, 0x049b0,
            0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60,
            0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58,
            0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0,
            0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0,
            0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52,
            0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0,
            0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250,
            0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577,
            0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0, 0x14b63};
    String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬",
            "癸"};
    String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申",
            "酉", "戌", "亥"};
    long[] sTermInfo = new long[]{0, 21208, 42467, 63836, 85337, 107014,
            128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989,
            308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224,
            483532, 504758};
    private int year;
    private int month;
    private int day;
    private boolean leap;
    final static String chineseNumber[] = {"正", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "十一", "腊"};
    final static String chineseNumber1[] = {"一", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "十一", "十二"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
            "yyyy年MM月dd日");

    public TimeUtils(Calendar cal, Activity activity) {
        this.activity = activity;
        int yearCyl, monCyl, dayCyl;
        int leapMonth = 0;
        Date baseDate = null;
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use
            // Options | File Templates.
        }

        // 求出和1900年1月31日相差的天数
        int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
        dayCyl = offset + 40;
        monCyl = 14;

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // i最终结果是农历的年份
        // offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
            daysOfYear = lYearDays(iYear);
            offset -= daysOfYear;
            monCyl += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            monCyl -= 12;
        }
        // 农历年份
        year = iYear;
        yearCyl = iYear - 1864;
        leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        leap = false;
        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = monthDays(year, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
            if (!leap)
                monCyl++;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
                --monCyl;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --monCyl;
        }
        month = iMonth;
        day = offset + 1;
    }
    public static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";

        if (day == 10)
            return "初十";

        if (day == 20)
            return "二十";

        if (day == 30)
            return "三十";

        return chineseTen[day / 10] + chineseNumber1[n];
    }

    public Map toLunar() {
        String m = null;
        String d = getChinaDayString(day);
        if (leap) {
            m = "闰" + chineseNumber[month - 1] + "月";
        } else {
            m = chineseNumber[month - 1] + "月";
        }
        Map map = new HashMap();
        map.put("m", m);
        map.put("d", d);

        return map;

    }

    public String getChinaWeekdayString(String weekday) {
        if (weekday.equals("Mon"))
            return "一";
        if (weekday.equals("Tue"))
            return "二";
        if (weekday.equals("Wed"))
            return "三";
        if (weekday.equals("Thu"))
            return "四";
        if (weekday.equals("Fri"))
            return "五";
        if (weekday.equals("Sat"))
            return "六";
        if (weekday.equals("Sun"))
            return "日";
        else
            return "";

    }

    /**
     * 返回农历 y年的总天数
     *
     * @param y
     */
    public int lYearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            sum += (lunarInfo[y - 1900] & i) > 0 ? 1 : 0;
        }
        return (sum + leapDays(y));
    }

    /**
     * 返回农历 y年闰月的天数
     *
     * @param y
     * @return
     */
    public int leapDays(int y) {
        if (leapMonth(y) > 0) {
            long day = lunarInfo[y - 1900] & 0x10000;
            return day > 0 ? 30 : 29;
        } else
            return 0;
    }

    /**
     * 返回农历 y年闰哪个月 1-12 , 没闰返回 0
     *
     * @param y
     * @return
     */
    public int leapMonth(int y) {
        return (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 返回农历 y年m月的总天数
     *
     * @param y
     * @param m
     * @return
     */
    public int monthDays(int y, int m) {
        return ((lunarInfo[y - 1900] & (0x10000 >> m)) > 0 ? 30 : 29);
    }

    final public String animalsYear() {
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇",
                "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }

    // ===== 某年的第n个节气为几日(从0小寒起算) Date.UTC(1900, 0, 6, 2, 5)

    /**
     * 正确的立春时间应该是以小时来进行计算的
     *
     * @param y
     * @param n
     * @return
     */
    public int sTerm(int y, int n) {
        long times = 31556925974l * (y - 1900) + sTermInfo[n] * 60000l
                + (long) 0.7 * (y - 1900);
        Date offDate = new Date(times - 2208549300000l);
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        cal.setTime(offDate);
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(cal.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(cal.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(cal.MILLISECOND, -(zoneOffset + dstOffset));
        // 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
        return (cal.get(Calendar.DATE));
    }

    /**
     * 传入 offset 返回干支, 0=甲子
     *
     * @param num
     * @return
     */
    public String cyclical(int num) {
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    /**
     * 计算 并 打印 八字
     *
     * @param date
     * @throws ParseException
     */
    public Map horoscope(String date) {
        Map resMap = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int i = cal.get(Calendar.DATE) - 1;
        String cY = null;
        String cM = null;
        String cD = null;
        String cH = null;
        // 年柱 1900年立春后为庚子年(60进制36)
        if (m < 2) {
            cY = cyclical(y - 1900 + 36 - 1);
        } else {
            cY = cyclical(y - 1900 + 36);
        }
        int term2 = sTerm(y, 2); // 立春日期
        // 月柱 1900年1月小寒以前为 丙子月(60进制12)
        int firstNode = sTerm(y, m * 2); // 返回当月「节」为几日开始
        cM = cyclical((y - 1900) * 12 + m + 12);
        int dayCyclical = jlday(y, m);
        // 依节气调整二月分的年柱, 以立春为界
        if (m == 1 && (i + 1) >= term2)
            cY = cyclical(y - 1900 + 36);
        // 依节气月柱, 以「节」为界
        if ((i + 1) >= firstNode)
            cM = cyclical((y - 1900) * 12 + m + 13);
        // 日柱
        cD = cyclical(dayCyclical + i);

        // 时柱
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        cH = Gan[hourG(cD.substring(0, 1), hour)] + Zhi[hourZ(hour)];
        resMap.put("cY", cY + "年");
        resMap.put("cM", cM + "月");
        resMap.put("cD", cD + "日");
        resMap.put("cH", cH + "时");
        return resMap;
    }

    /**
     * 根据 日干 推算 时柱 根据提供的推算图来计算
     *
     * @param dG
     * @param hour
     * @return
     */
    public int hourG(String dG, int hour) {
        int ind = 1;
        for (String s : Gan) {
            if (s.equals(dG)) {
                break;
            }
            ind++;
        }
        ind = ind % 5; // 五个为一周期
        int hourind = hourZ(hour);
        if (hourind > 10)
            return hourind - 10 + (ind - 1) * 2;
        else {
            hourind = hourind + (ind - 1) * 2;
            return hourind >= 10 ? hourind - 10 : hourind;
        }
    }

    /**
     * 返回 小时对应的 支的索引
     *
     * @param hour
     * @return
     */
    public int hourZ(int hour) {
        if (hour >= 23 || hour < 1)
            return 0;
        else if (hour >= 1 && hour < 3)
            return 1;
        else if (hour >= 3 && hour < 5)
            return 2;
        else if (hour >= 5 && hour < 7)
            return 3;
        else if (hour >= 7 && hour < 9)
            return 4;
        else if (hour >= 9 && hour < 11)
            return 5;
        else if (hour >= 11 && hour < 13)
            return 6;
        else if (hour >= 13 && hour < 15)
            return 7;
        else if (hour >= 15 && hour < 17)
            return 8;
        else if (hour >= 17 && hour < 19)
            return 9;
        else if (hour >= 19 && hour < 21)
            return 10;
        else if (hour >= 21 && hour < 23)
            return 11;
        return 0;
    }
    /**
     * 间隔天数
     *
     * @param y
     * @param m
     * @return
     */
    public int jlday(int y, int m) {
        String js = " var y =" + y + ",m=" + m + " ;"+
                "function jlday(   ) { "
                + "return Date.UTC(y,m,1,0,0,0,0)/86400000+25567+10;" + "}";
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(activity, scope));
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(activity.getClassLoader(), scope));

            rhino.evaluateString(scope, js, "TimeUtils", 1, null);

            Function function = (Function) scope.get("jlday", scope);

            double result = (double)function.call(rhino, scope, scope,new String[]{});
            String s = result + "";
            String str = s.substring(0, s.indexOf("."));
            int intgeo = Integer.parseInt(str);
            return intgeo;
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }



    public String getMonthWill(Date time){
        SimpleDateFormat m = new SimpleDateFormat("MM");
        SimpleDateFormat d = new SimpleDateFormat("dd");
        String yue = m.format(time);
        String tian = d.format(time);
        int y = Integer.parseInt(yue);
        int t = Integer.parseInt(tian);
        String yj = getYJ(y, t);
        return yj;
    }

    /**
     * 获取月将
     * @return
     * @param y  月份
     * @param t 日子
     */
    private String getYJ(int y, int t){
        switch (y){
            case 1:
                if(20>t){
                    return "冬至后   大吉   丑将占";
                }else{
                    return "大寒后   神后   子将占";
                }
            case 2:
                if(19>t){
                    return "大寒后   神后   子将占";
                }else{
                    return "雨水后   登明   亥将占";
                }
            case 3:
                if(21>t){
                    return "雨水后   登明   亥将占";
                }else{
                    return "春分后   河魁   戌将占";
                }
            case 4:
                if(20>t){
                    return "春分后   河魁   戌将占";
                }else{
                    return "谷雨后   从魁   酉将占";
                }
            case 5:
                if(21>t){
                    return "谷雨后   从魁   酉将占";
                }else{
                    return "小满后   传送   申将占";
                }
            case 6:
                if(21>t){
                    return "小满后   传送   申将占";
                }else{
                    return "夏至后   小吉   未将占";
                }
            case 7:
                if(23>t){
                    return "夏至后   小吉   未将占";
                }else{
                    return "大暑后   胜光   午将占";
                }
            case 8:
                if(23>t){
                    return "大暑后   胜光   午将占";
                }else{
                    return "处暑后   太乙   巳将占";
                }
            case 9:
                if(23>t){
                    return "处暑后   太乙   巳将占";
                }else{
                    return "秋分后   天罡   辰将占";
                }
            case 10:
                if(23>t){
                    return "秋分后   天罡   辰将占";
                }else{
                    return "霜降后   太冲   卯将占";
                }
            case 11:
                if(22>t){
                    return "霜降后   太冲   卯将占";
                }else{
                    return "小雪后   功曹   寅将占";
                }
            case 12:
                if(22>t){
                    return "小雪后   功曹   寅将占";
                }else{
                    return "冬至后   大吉   丑将占";
                }
        }
        return "";
    }
}
