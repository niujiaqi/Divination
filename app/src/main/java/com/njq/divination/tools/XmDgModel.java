package com.njq.divination.tools;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.blankj.utilcode.util.ServiceUtils;
import com.njq.divination.server.LogService;

public class XmDgModel {

    public Context context;

    private static final String TAG = "XmDgModel";


    public XmDgModel(Context context) {
        super();
        this.context = context;
    }

    private boolean isFirst = true;

    /*
    小米手机之外的悬浮圈窗权限检查操作
 */
    public void permission(final Context context) {
        if (isFirst) {
            isFirst = false;
            if (Build.VERSION.SDK_INT >= 24) {
                if (!Settings.canDrawOverlays(context)) {
                    new AlertDialog.Builder(context).setTitle("悬浮窗权限")
                            .setMessage("您需要开启悬浮窗权限才可以享受更多服务！").setPositiveButton("去开启", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jumpSetting(context);
                            dialog.dismiss();
                        }
                    }).show();

                } else {
                    if(!ServiceUtils.isServiceRunning(LogService.class)){
                        ServiceUtils.startService(LogService.class);
                    }
                }
            } else {
                if(!ServiceUtils.isServiceRunning(LogService.class)){
                    ServiceUtils.startService(LogService.class);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                if (Settings.canDrawOverlays(context)) {
                    if(!ServiceUtils.isServiceRunning(LogService.class)){
                        ServiceUtils.startService(LogService.class);
                    }
                }
            } else {
                if(!ServiceUtils.isServiceRunning(LogService.class)){
                    ServiceUtils.startService(LogService.class);
                }
            }
        }

//
//        Boolean result = true; //标识是否有悬浮权限
//        /*
//            如过版本大于24 就去检查是否开启了悬浮窗的权限
//		 */
//        if (Build.VERSION.SDK_INT > 24) {
//            try {
//                Class clazz = Settings.class;
//                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
//                result = (Boolean) canDrawOverlays.invoke(null, context);
//            } catch (Exception e) {
//                Log.e(TAG, Log.getStackTraceString(e));
//            }
//        } else {
//            // 小于24可以直接显示悬浮窗
//            Intent serviceStart = new Intent(context, MCHFloatService.class);
//            context.startService(serviceStart);
//        }
//        /*
//            如过版本大于24 且没开启悬浮窗权限则调到设置界面
//		 */
//        if (!result) {
//            new AlertDialog.Builder(context).setTitle("悬浮窗权限")
//                    .setMessage("您需要开启悬浮窗权限才可以享受更多服务！").setPositiveButton("去开启", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    jumpSetting(context);
//                }
//            }).show();
//
//        } else {
//            //开启了权限 可以显示悬浮窗操作
//            Intent serviceStart = new Intent(context, MCHFloatService.class);
//            context.startService(serviceStart);
//        }
    }



    /*
小米手机之外的悬浮圈窗权限检查操作
*/
    public void permission1(final Context context) {
        if (isFirst) {
            isFirst = false;
            if (Build.VERSION.SDK_INT >= 24) {
                if (!Settings.canDrawOverlays(context)) {
                    new AlertDialog.Builder(context).setTitle("悬浮窗权限")
                            .setMessage("您需要开启悬浮窗权限才可以享受更多服务！").setPositiveButton("去开启", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jumpSetting(context);
                            dialog.dismiss();
                        }
                    }).show();

                }else{
                    if(!ServiceUtils.isServiceRunning(LogService.class)){
                        ServiceUtils.startService(LogService.class);
                    }
                }
            }else{
                if(!ServiceUtils.isServiceRunning(LogService.class)){
                    ServiceUtils.startService(LogService.class);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= 24) {
                if (Settings.canDrawOverlays(context)) {
                    if(!ServiceUtils.isServiceRunning(LogService.class)){
                        ServiceUtils.startService(LogService.class);
                    }
                }
            }else{
                if(!ServiceUtils.isServiceRunning(LogService.class)){
                    ServiceUtils.startService(LogService.class);
                }
            }
        }

//
//        Boolean result = true; //标识是否有悬浮权限
//        /*
//            如过版本大于24 就去检查是否开启了悬浮窗的权限
//		 */
//        if (Build.VERSION.SDK_INT > 24) {
//            try {
//                Class clazz = Settings.class;
//                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
//                result = (Boolean) canDrawOverlays.invoke(null, context);
//            } catch (Exception e) {
//                Log.e(TAG, Log.getStackTraceString(e));
//            }
//        } else {
//            // 小于24可以直接显示悬浮窗
//            Intent serviceStart = new Intent(context, MCHFloatService.class);
//            context.startService(serviceStart);
//        }
//        /*
//            如过版本大于24 且没开启悬浮窗权限则调到设置界面
//		 */
//        if (!result) {
//            new AlertDialog.Builder(context).setTitle("悬浮窗权限")
//                    .setMessage("您需要开启悬浮窗权限才可以享受更多服务！").setPositiveButton("去开启", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    jumpSetting(context);
//                }
//            }).show();
//
//        } else {
//            //开启了权限 可以显示悬浮窗操作
//            Intent serviceStart = new Intent(context, MCHFloatService.class);
//            context.startService(serviceStart);
//        }
    }
    /*
       跳到 设置界面(悬浮窗权限操作)
     */
    private void jumpSetting(Context context) {
//        try {
//            Class clazz = Settings.class;
//            Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
//
//            Intent intent = new Intent(field.get(null).toString());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("package:" + context.getPackageName()));
//            context.startActivity(intent);
//        } catch (Exception e) {
//            Log.e(TAG, Log.getStackTraceString(e));
//        }

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);


    }


    public void showxiaomidg() {
        new AlertDialog.Builder(context).setTitle("悬浮窗权限")
                .setMessage("您需要开启悬浮窗权限才可以享受更多服务！").setPositiveButton("去开启", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                XiaomiUtil.openMiuiPermissionActivity(context);
//						XiaomiUtil.askForPermission(context);
            }
        }).show();
//		final AlertDialog dialog = builder.create();
//		View v = View.inflate(this, R.layout.exit_dialog, null);
//		dialog.setView(v, 0, 0, 0, 0);
//		dialog.show();
        /*final Dialog lDialog = new Dialog(context,
                android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(DialogUtil.getIdByName(context, "layout",
				"dialog_xiaomiquanxian"));
		lDialog.setCancelable(false);
		lDialog.findViewById(DialogUtil.getIdByName(context, "id", "toquanxian")).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mUserObsv.toquanxian(context);
				lDialog.dismiss();
			}
		});;
		lDialog.show();*/
    }
}
