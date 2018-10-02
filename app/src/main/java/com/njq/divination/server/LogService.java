package com.njq.divination.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.njq.divination.LogListenr;
import com.njq.divination.R;
import com.njq.divination.bean.EvenBean;
import com.njq.divination.tools.ZBLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LogService extends Service implements LogListenr {

    private ListView listview;
    private LinkedList<LogLine> logList = new LinkedList<LogLine>();
    private LogAdapter mAdapter;
    private final int MAX_LINE = 500;
    private SimpleDateFormat LOGCAT_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
    private Thread readLog;
    private boolean isAllowReadLog = true;
    private boolean isShow = false;
    private WindowManager.LayoutParams lp;
    private View view;
    private WindowManager wm;
    private LayoutInflater inflator;
    private InnerRecevier innerRecevier;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ZBLog.Register(this);
        super.onCreate();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        innerRecevier = new InnerRecevier();
        registerReceiver(innerRecevier,intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Utility.LOG_TAG 为自定义的logString，service会读取此log
        createSystemWindow();
        isAllowReadLog = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isShow = false;
        removeSystemWindow();
        isAllowReadLog = false;
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        unregisterReceiver(innerRecevier);
        super.onDestroy();
    }

    @Subscribe
    public void hindOrShow(EvenBean bean){
        if(bean.type==2){
            Log.e("hindOrShowhindOrShow","hindOrShowhindOrShow");
            if(isShow){
                isShow = false;
                removeSystemWindow();
            }else{
                Show();
            }
        }
    }

    private void Show() {
            // lp.gravity=Gravity.LEFT|Gravity.TOP; //调整悬浮窗口至左上角
            // 以屏幕左上角为原点，设置x、y初始化
            // lp.x=0;
            // lp.y=0;
            if (isAllowReadLog&&view!=null&&!isShow) {
                isShow = true;
                wm.addView(view, lp);
            }
    }

    private void createSystemWindow() {
        lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT
                , WindowManager.LayoutParams.TYPE_PHONE
                , WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =  inflator.inflate(R.layout.log_window, null);
        listview = (ListView) view.findViewById(R.id.list);
        logList = new LinkedList<LogLine>();
        mAdapter = new LogAdapter(this, logList);
        listview.setAdapter(mAdapter);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK || i == KeyEvent.KEYCODE_HOME) {
                    if (null != wm && null != wm&&view!=null&&isShow) {
                        isShow = false;
                        wm.removeView(view);
                    }
                    return true;
                }
                return false;
            }
        });
        view.setFocusableInTouchMode(true);
    }

    private void removeSystemWindow() {
        if (listview != null && listview.getParent() != null&&view!=null&&isShow) {
            wm.removeView(view);
        }
    }

    @Override
    public void sendLog(String log) {
        buildLogLine(log);
    }

    class LogAdapter extends ArrayAdapter<LogLine> {

        private LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        public LogAdapter(Context context, List<LogLine> objects) {
            super(context, 0, objects);
        }

        public void add(LogLine line) {
            logList.add(line);
            notifyDataSetChanged();
        }

        @Override
        public LogLine getItem(int position) {
            return logList.get(position);
        }

        @Override
        public int getCount() {
            return logList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LogLine line = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflator.inflate(R.layout.log_line, parent, false);
                holder.time = (TextView) convertView.findViewById(R.id.log_time);
                holder.content = (TextView) convertView.findViewById(R.id.log_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.time.setText("ZB :");
            holder.time.setVisibility(View.GONE);
            holder.time.setTextColor(getResources().getColor(R.color.red));
            holder.content.setText(line.content);
            if (line.color != 0) {
                holder.content.setTextColor(getResources().getColor(R.color.lan));
            } else {
                holder.content.setTextColor(getResources().getColor(R.color.red));
            }
            return convertView;
        }

    }

    class ViewHolder {
        public TextView time;
        public TextView content;
    }

    private void buildLogLine(String line) {
        LogLine log = new LogLine();
        log.time = LOGCAT_TIME_FORMAT.format(new Date()) + ": ";
         if (line.startsWith("D")) {
            log.color = Color.parseColor("#A9A9A9");
        } else if (line.startsWith("E")) {
            log.color = Color.parseColor("#FF0000");
        }
        if (line.contains(")")) {
            line = line.substring(line.indexOf(")") + 1, line.length());
        }
        log.content = line;

        while (logList.size() > MAX_LINE) {
            logList.remove();
        }
        mAdapter.add(log);
    }



    class InnerRecevier extends BroadcastReceiver {
        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
//                    Log.e(TAG, "action:" + action + ",reason:" + reason);
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            // 短按home键
                            if (null != wm && null != wm&&view!=null&&isShow) {
                                isShow = false;
                                wm.removeView(view);
                            }
                        }/* else if (reason
                                .equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            // 长按home键
                            mListener.onHomeLongPressed();
                        }*/
                }
            }
        }
    }
}
