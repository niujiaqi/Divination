package com.njq.divination;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.njq.divination.bean.TimeBean;
import com.njq.divination.http.JsonCallback;
import com.njq.divination.http.McResponse;
import com.njq.divination.tools.Utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_yang)
    TextView tvYang;
    @BindView(R.id.tv_yin)
    TextView tvYin;
    @BindView(R.id.tv_tianzhi)
    TextView tvTianzhi;
    private String time = "http://www.sojson.com/open/api/lunar/json.shtml?date=";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        time = time + TimeUtils.getNowString(simpleDateFormat);
        getData();
    }

    private void getData() {
        OkGo.<McResponse<TimeBean>>get(time)
                .tag(this)
                .execute(new JsonCallback<McResponse<TimeBean>>() {

                    @Override
                    public void onSuccess(Response<McResponse<TimeBean>> response) {
                        TimeBean data = response.body().getData();
                        tvYang.setText("阳历："+data.getYear()+"-"+data.getMonth()+"-"+data.getDay());
                        tvYin.setText("农历："+data.getCnmonth()+"月"+data.getCnday());
                        tvTianzhi.setText("天干地支："+data.getCyclicalYear()+"   "+data.getCyclicalMonth()+"   "+data.getCyclicalDay()+"");
                    }

                    @Override
                    public void onError(Response<McResponse<TimeBean>> response) {
                        if (response.getException() != null) {
                            String message = response.getException().getMessage();
                            Log.e(TAG,message);
                            Utils.TS(message);
                            tvYang.setText("时间获取失败");
                            tvYin.setText("时间获取失败");
                            tvTianzhi.setText("时间获取失败");
                            KeyboardUtils.hideSoftInput();
                        }
                    }
                });
    }
}
