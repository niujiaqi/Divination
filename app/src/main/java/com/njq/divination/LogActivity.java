package com.njq.divination;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.njq.divination.bean.EvenBean;

import org.greenrobot.eventbus.EventBus;

public class LogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_log);
        EvenBean evenBean = new EvenBean();
        evenBean.type = 2;
        EventBus.getDefault().post(evenBean);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EvenBean evenBean = new EvenBean();
//        evenBean.type = 2;
//        EventBus.getDefault().post(evenBean);
    }
}
