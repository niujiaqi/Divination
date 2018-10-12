package com.njq.divination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ServiceUtils;
import com.njq.divination.bean.EvenBean;
import com.njq.divination.bean.FirstPassBean;
import com.njq.divination.server.LogService;
import com.njq.divination.tools.FirstPassUtils;
import com.njq.divination.tools.TimeUtils;
import com.njq.divination.tools.Utils;
import com.njq.divination.tools.ZBLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_s1)
    TextView tvS1;
    @BindView(R.id.tv_s2)
    TextView tvS2;
    @BindView(R.id.tv_s3)
    TextView tvS3;
    @BindView(R.id.tv_s4)
    TextView tvS4;
    @BindView(R.id.tv_x1)
    TextView tvX1;
    @BindView(R.id.tv_x2)
    TextView tvX2;
    @BindView(R.id.tv_x3)
    TextView tvX3;
    @BindView(R.id.tv_x4)
    TextView tvX4;
    @BindView(R.id.tv_chu)
    TextView tvChu;
    @BindView(R.id.tv_zhong)
    TextView tvZhong;
    @BindView(R.id.tv_mo)
    TextView tvMo;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.btn_lookLog)
    TextView btnLookLog;
    private Object cY;
    private Object cM;
    private Object cD;
    private Object cH;
    public static ArrayList<String> arrayTP = new ArrayList<>();
    public static String s;                 //日的天干
    private String weekDay;
    private String dayTime;
    private String animalsYear;
    private Map lunar;
    private String monthWill1;
    private String monthWill;
    private String h;
    public static String s1;
    private String k1;
    private String k2;
    private String k3;
    private String k4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(!ServiceUtils.isServiceRunning(LogService.class)){
            ServiceUtils.startService(LogService.class);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        handler.sendEmptyMessageDelayed(1,300);
    }

    private void setData() {
        Calendar today = Calendar.getInstance();
        Date date = new Date();
        today.setTime(date);//加载当前日期
        TimeUtils birth = new TimeUtils(today, this);
        monthWill = birth.getMonthWill(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH");
        //计算输出阴历日期
        lunar = birth.toLunar();
        //计算输出属相
        animalsYear = birth.animalsYear() + "年";
        //输出阳历日期
        dayTime = sdf.format(date);
        //计算输出星期几
        weekDay = "星期" + birth.getChinaWeekdayString(today.getTime().toString().substring(0, 3));
        Map map = birth.horoscope(dayTime);
        cY = map.get("cY");
        cM = map.get("cM");
        cD = map.get("cD");
        cH = map.get("cH");
        monthWill1 = Utils.getMonthWill(monthWill);
        h = Utils.getH((String) cH);
        arrayTP = Utils.getArrayTP(Utils.getDZPos(monthWill1), Utils.getDZPos(h));
        setTP(arrayTP);
        tvYang.setText("阳历：" + dayTime + "时    " + weekDay);
        tvYin.setText("阴历：" + (String) lunar.get("m") + lunar.get("d") + "    " + animalsYear);
        tvTianzhi.setText("天干地支：" + cY + "  " + cM + "  " + cD + "  " + cH);
        tvYuejiang.setText("月将：        " + monthWill);
        tvTianpan.setText("天盘：    " + monthWill1 + "将  占在  " + h + "时");
    }


    /**
     * 设置天盘
     *
     * @param arrayTP
     */
    public void setTP(ArrayList<String> arrayTP) {
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

    /**
     * 设置四课
     */
    public void setSK() {
        //取日的天干
        s = ((String) cD).substring(0, 1);
        //取日的地支
        s1 = ((String) cD).substring(1, 2);
        k1 = Utils.getK1(s, arrayTP);
        k2 = Utils.getK2(k1, arrayTP);
        k3 = Utils.getK2(s1, arrayTP);
        k4 = Utils.getK2(k3, arrayTP);
        //设置第一课
        tvX1.setText(s);
        tvS1.setText(k1);
        //设置第二课
        tvX2.setText(k1);
        tvS2.setText(k2);
        //设置第三课
        tvX3.setText(s1);
        tvS3.setText(k3);
        //设置第四课
        tvX4.setText(k3);
        tvS4.setText(k4);
    }

    /**
     * 设置三传
     */
    public void setSC() {
        String chu = "";
        FirstPassBean cc = Utils.getCC(s, k1, s1, k3, k1, k2, k3, k4);
        if(cc==null){
            chu = "酉";
        }else{
            chu = cc.CC;
        }
        if(cc!=null&&cc.fa.equals("昂星法")){
            Utils.setSC(tvZhong,tvMo);
            ZBLog.e("\n=========得出三传========\n" +
                    "   初传："+chu+"\n" +
                    "   中传："+tvZhong.getText().toString()+"\n" +
                    "   末传："+tvMo.getText().toString()+"\n");
        }else if(cc!=null&&cc.fa.equals("别责法")){
            Utils.setBZ(tvZhong,tvMo);
            ZBLog.e("\n=========得出三传========\n" +
                    "   初传："+chu+"\n" +
                    "   中传："+tvZhong.getText().toString()+"\n" +
                    "   末传："+tvMo.getText().toString()+"\n");
        }else if(cc!=null&&cc.fa.equals("八专注")){
            Utils.setBAZ(tvZhong,tvMo);
            ZBLog.e("\n=========得出三传========\n" +
                    "   初传："+chu+"\n" +
                    "   中传："+tvZhong.getText().toString()+"\n" +
                    "   末传："+tvMo.getText().toString()+"\n");
        }else{
            String zhong = arrayTP.get(Utils.getDZPos(chu));
            String mo = arrayTP.get(Utils.getDZPos(zhong));
            tvChu.setText(chu);
            tvZhong.setText(zhong);
            tvMo.setText(mo);
            ZBLog.e("\n=========得出三传========\n" +
                    "   初传："+chu+"\n" +
                    "   中传："+zhong+"\n" +
                    "   末传："+mo+"\n");
        }
    }

    @Subscribe
    public void Even(EvenBean bean) {
        if (bean.type == 1) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("起卦出错：" + bean.text);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if(ServiceUtils.isServiceRunning(LogService.class)){
            ServiceUtils.stopService(LogService.class);
        }
    }



    @OnClick(R.id.btn_lookLog)
    public void onViewClicked() {
        Log.e("点击了","点击了");
        startActivity(new Intent(this,LogActivity.class));
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ZBLog.e("===============开始计算=============\n\n");
            setData();
            ZBLog.e("" +
                    "阳历：" + dayTime + "时    " + weekDay + "\n" + "阴历：" +
                    (String) lunar.get("m") + lunar.get("d") + "    " + animalsYear + "\n" +
                    "天干地支：" + cY + "  " + cM + "  " + cD + "  " + cH + "\n" +
                    "月将：    " + monthWill + "\n" +
                    "天盘：    " + monthWill1 + "将  占在  " + h + "时");

            ZBLog.e( "\n天盘排列：\n " + arrayTP.get(5) + " " + arrayTP.get(6) + " " + arrayTP.get(7) + " " + arrayTP.get(5) + "\n" +
                    arrayTP.get(4) + "------" + arrayTP.get(9) + "\n" +
                    arrayTP.get(3) + "------" + arrayTP.get(10) + "\n" +
                    arrayTP.get(2) + " " + arrayTP.get(1) + " " + arrayTP.get(0) + " " + arrayTP.get(11) + "\n");
            setSK();            //设置四课
            ZBLog.e( "\n四课：\n" + k1 + " " + k2 + " " + k3 + " " + k4 + "\n" +
                    s + " " + k1 + " " + s1 + " " + k3 + "\n");
            setSC();            //设置三传

        }
    };
}
