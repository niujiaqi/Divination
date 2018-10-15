package com.njq.divination.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;


import java.lang.reflect.Method;
import java.util.List;

public class XiaomiUtil {
    public static int OVERLAY_PERMISSION_REQ_CODE = 0x1234;
    private static final String TAG = "XiaomiUtil";

    /**
     * 判断MIUI的悬浮窗权限
     */
    public static boolean isMiuiFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); // 自己写就是24 为什么是24?看AppOpsManager
            // 即AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            return false;
//			if ((context.getApplicationInfo().flags & 1 << 27) == 1) {
//				return true;
//			} else {
//				return false;
//			}
        }
    }

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 传入app 或者 activity
     *                context，通过context获取应用packegename，之后通过packegename跳转制定应用
     * @return 是否是miui
     */
    public static boolean gotoPermissionSettings(Context context) {
        boolean mark = isMIUI();
        if (mark) {
            // 兼容miui v5/v6 的应用权限设置页面，否则的话跳转应用设置页面（权限设置上一级页面）
            try {
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (ActivityNotFoundException e) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }
        return mark;
    }

    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                if (AppOpsManager.MODE_ALLOWED == (Integer) ReflectUtils.invokeMethod(manager, "checkOp", op, Binder.getCallingUid(),
                        context.getPackageName())) { // 这儿反射就自己写吧
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                Log.e("packl", e.getMessage());
            }
        } else {
            Log.e("packll", "Below API 19 cannot invoke!");
        }
        return false;
    }

    public static class ReflectUtils {
        @SuppressLint("NewApi")
        public static int invokeMethod(AppOpsManager manager, String method, int op, int callingUid, String packageName) throws Exception {
            Class<AppOpsManager> clazz = AppOpsManager.class;
            Method dispatchMethod = clazz.getMethod(method, new Class[]{int.class, int.class, String.class});
            int mode = (Integer) dispatchMethod.invoke(manager, new Object[]{op, callingUid, packageName});
            return mode;
        }
    }

    /**
     * 经测试V5版本是有区别的
     *
     * @param context
     */
    public static void openMiuiPermissionActivity(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");

        if ("V5".equals(getProperty())) {
            Intent intent1 = null;
            String packageName = context.getPackageName();
            intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent1.setData(uri);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                Log.e(TAG, "intent is not available!");
            }
        } else if ("V6".equals(getProperty())) {
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                Log.e(TAG, "Intent is not available!");
            }
        } else if ("V7".equals(getProperty())) {
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                Log.e(TAG, "Intent is not available!");
            }

        } else if ("V8".equals(getProperty()) || "V9".equals(getProperty())|| "V10".equals(getProperty())) {
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
//        intent.setPackage("com.miui.securitycenter");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if (isIntentAvailable(intent, context)) {
                context.startActivity(intent);
            } else {
                intent.setPackage("com.miui.securitycenter");
                intent.putExtra("extra_pkgname", context.getPackageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (isIntentAvailable(intent, context)) {
                    context.startActivity(intent);
                } else {
                    Log.e(TAG, "Intent is not available!");
                }
            }
        }
        if (isActivityAvailable(context, intent)) {
            if (context instanceof Activity) {
                Activity a = (Activity) context;
                a.startActivityForResult(intent, 2);
            }
        } else {
            Log.e("canking", "Intent is not available!");
        }
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        if (intent == null) {
            return false;
        }
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    public static boolean isActivityAvailable(Context cxt, Intent intent) {
        PackageManager pm = cxt.getPackageManager();
        if (pm == null) {
            return false;
        }
        List<ResolveInfo> list = pm.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }

    public static String getProperty() {
        String property = "null";
        if (!"Xiaomi".equals(Build.MANUFACTURER)) {
            return property;
        }
        try {
            Class<?> spClazz = Class.forName("android.os.SystemProperties");
            Method method = spClazz.getDeclaredMethod("get", String.class, String.class);
            property = (String) method.invoke(spClazz, "ro.miui.ui.version.name", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

    /**
     * 检查手机是否是miui
     *
     * @return
     * @ref http://dev.xiaomi.com/doc/p=254/index.html
     */
    public static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {
            System.out.println("this is a xiaomi device");
            return true;
        } else {
            return false;
        }
    }
}
