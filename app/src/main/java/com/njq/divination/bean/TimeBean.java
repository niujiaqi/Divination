package com.njq.divination.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 描述：获取天干地支时间
 * 作者：钮家齐
 * 时间: 2018-09-17 20:15
 */

public class TimeBean {


    /**
     * year : 2017
     * month : 5
     * day : 27
     * lunarYear : 2017
     * lunarMonth : 5
     * lunarDay : 2
     * cnyear : 贰零壹柒
     * cnmonth : 五
     * cnday : 初二
     * hyear : 丁酉
     * cyclicalYear : 丁酉
     * cyclicalMonth : 乙巳
     * cyclicalDay : 甲寅
     * suit : 栽种,捕捉,畋猎,馀事勿取
     * taboo : 开市,动土,祭祀,斋醮,安葬,探病
     * animal : 鸡
     * week : Saturday
     * festivalList : []
     * jieqi : {"5":"立夏","21":"小满"}
     * maxDayInMonth : 29
     * leap : false
     * lunarYearString : 丁酉
     * bigMonth : false
     */

    private String year;
    private String month;
    private String day;
    private String lunarYear;
    private String lunarMonth;
    private String lunarDay;
    private String cnyear;
    private String cnmonth;
    private String cnday;
    private String hyear;
    private String cyclicalYear;
    private String cyclicalMonth;
    private String cyclicalDay;
    private String suit;
    private String taboo;
    private String animal;
    private String week;
    private JieqiBean jieqi;
    private String maxDayInMonth;
    private boolean leap;
    private String lunarYearString;
    private boolean bigMonth;
    private List<?> festivalList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getCnyear() {
        return cnyear;
    }

    public void setCnyear(String cnyear) {
        this.cnyear = cnyear;
    }

    public String getCnmonth() {
        return cnmonth;
    }

    public void setCnmonth(String cnmonth) {
        this.cnmonth = cnmonth;
    }

    public String getCnday() {
        return cnday;
    }

    public void setCnday(String cnday) {
        this.cnday = cnday;
    }

    public String getHyear() {
        return hyear;
    }

    public void setHyear(String hyear) {
        this.hyear = hyear;
    }

    public String getCyclicalYear() {
        return cyclicalYear;
    }

    public void setCyclicalYear(String cyclicalYear) {
        this.cyclicalYear = cyclicalYear;
    }

    public String getCyclicalMonth() {
        return cyclicalMonth;
    }

    public void setCyclicalMonth(String cyclicalMonth) {
        this.cyclicalMonth = cyclicalMonth;
    }

    public String getCyclicalDay() {
        return cyclicalDay;
    }

    public void setCyclicalDay(String cyclicalDay) {
        this.cyclicalDay = cyclicalDay;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getTaboo() {
        return taboo;
    }

    public void setTaboo(String taboo) {
        this.taboo = taboo;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public JieqiBean getJieqi() {
        return jieqi;
    }

    public void setJieqi(JieqiBean jieqi) {
        this.jieqi = jieqi;
    }

    public String getMaxDayInMonth() {
        return maxDayInMonth;
    }

    public void setMaxDayInMonth(String maxDayInMonth) {
        this.maxDayInMonth = maxDayInMonth;
    }

    public boolean isLeap() {
        return leap;
    }

    public void setLeap(boolean leap) {
        this.leap = leap;
    }

    public String getLunarYearString() {
        return lunarYearString;
    }

    public void setLunarYearString(String lunarYearString) {
        this.lunarYearString = lunarYearString;
    }

    public boolean isBigMonth() {
        return bigMonth;
    }

    public void setBigMonth(boolean bigMonth) {
        this.bigMonth = bigMonth;
    }

    public List<?> getFestivalList() {
        return festivalList;
    }

    public void setFestivalList(List<?> festivalList) {
        this.festivalList = festivalList;
    }

    public static class JieqiBean {
        /**
         * 5 : 立夏
         * 21 : 小满
         */

        @SerializedName("5")
        private String _$5;
        @SerializedName("21")
        private String _$21;

        public String get_$5() {
            return _$5;
        }

        public void set_$5(String _$5) {
            this._$5 = _$5;
        }

        public String get_$21() {
            return _$21;
        }

        public void set_$21(String _$21) {
            this._$21 = _$21;
        }
    }
}
