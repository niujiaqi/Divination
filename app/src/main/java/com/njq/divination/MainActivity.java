package com.njq.divination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.njq.divination.tools.TimeUtils;
import com.njq.divination.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_yang)
    TextView tvYang;
    @BindView(R.id.tv_yin)
    TextView tvYin;
    @BindView(R.id.tv_tianzhi)
    TextView tvTianzhi;
    private static final String TAG = "MainActivity";
    @BindView(R.id.tv_yuejiang)
    TextView tvYuejiang;
    @BindView(R.id.tv_tianpan)
    TextView tvTianpan;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.tv_7)
    TextView tv7;
    @BindView(R.id.tv_8)
    TextView tv8;
    @BindView(R.id.tv_9)
    TextView tv9;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_10)
    TextView tv10;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_11)
    TextView tv11;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_12)
    TextView tv12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setData();
    }

    private void setData() {

        Calendar today = Calendar.getInstance();
        Date date = new Date();
        today.setTime(date);//加载当前日期
        TimeUtils birth = new TimeUtils(today, this);
        String monthWill = birth.getMonthWill(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH");
        Map lunar = birth.toLunar();//计算输出阴历日期
        String animalsYear = birth.animalsYear() + "年";//计算输出属相
        String dayTime = sdf.format(date);//输出阳历日期
        String weekDay = "星期" + birth.getChinaWeekdayString(today.getTime().toString().substring(0, 3));//计算输出星期几
        Map map = birth.horoscope(dayTime);
        Object cY = map.get("cY");
        Object cM = map.get("cM");
        Object cD = map.get("cD");
        Object cH = map.get("cH");
        String monthWill1 = Utils.getMonthWill(monthWill);
        String h = Utils.getH((String)cH);
        ArrayList<String> arrayTP = Utils.getArrayTP(Utils.getDZPos(monthWill1), Utils.getDZPos(h));
        setTP(arrayTP);
        Log.e(TAG, "\n阳历：" + dayTime + "时    " + weekDay + "\n" + "阴历：" +
                (String) lunar.get("m") + lunar.get("d") + "    " + animalsYear + "\n" +
                "天干地支：" + cY + "  " + cM + "  " + cD + "  " + cH + "\n"+
                "月将：    " + monthWill+ "\n" +
        "天盘：    "+monthWill1+"将  占在  "+h+"时");
        tvYang.setText("阳历：" + dayTime + "时    " + weekDay);
        tvYin.setText("阴历：" + (String) lunar.get("m") + lunar.get("d") + "    " + animalsYear);
        tvTianzhi.setText("天干地支：" + cY + "  " + cM + "  " + cD + "  " + cH);
        tvYuejiang.setText("月将：        " + monthWill);
        tvTianpan.setText("天盘：    "+monthWill1+"将  占在  "+h+"时");

    }


    public void setTP(ArrayList<String> arrayTP){
        tv1.setText(arrayTP.get(0));
        tv2.setText(arrayTP.get(1));
        tv3.setText(arrayTP.get(2));
        tv4.setText(arrayTP.get(3));
        tv5.setText(arrayTP.get(4));
        tv6.setText(arrayTP.get(5));
        tv7.setText(arrayTP.get(6));
        tv8.setText(arrayTP.get(7));
        tv9.setText(arrayTP.get(8));
        tv10.setText(arrayTP.get(9));
        tv11.setText(arrayTP.get(10));
        tv12.setText(arrayTP.get(11));
    }
}
