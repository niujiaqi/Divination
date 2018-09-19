package com.njq.divination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.njq.divination.bean.TimeBean;
import com.njq.divination.http.JsonCallback;
import com.njq.divination.http.McResponse;
import com.njq.divination.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
        com.njq.divination.tools.TimeUtils birth = new com.njq.divination.tools.TimeUtils(today,this);
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
        Log.e(TAG,"阳历："+dayTime+"时    "+weekDay+"\n"+"阴历："+
                (String)lunar.get("m")+lunar.get("d")+"    "+animalsYear+"\n"+"天干地支："+cY+"  "+cM+"  "+cD+"  "+cH+"\n");
        tvYang.setText("阳历："+dayTime+"时    "+weekDay);
        tvYin.setText("阴历："+(String)lunar.get("m")+lunar.get("d")+"    "+animalsYear);
        tvTianzhi.setText("天干地支："+cY+"  "+cM+"  "+cD+"  "+cH);
    }
}
