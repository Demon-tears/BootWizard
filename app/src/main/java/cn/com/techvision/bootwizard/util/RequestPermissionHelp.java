package cn.com.techvision.bootwizard.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class RequestPermissionHelp {

    Activity activity;

              /*Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.READ_EXTERNAL_STORAGE*/

    private String[] needPermissions = new String[] {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
             };
    private int PERMISSION_REQUEST_CODE = 1;


    public RequestPermissionHelp(Activity activity) {
        this.activity = activity;
    }

    public void checkPermission() {
        /** * 判断哪些权限未授予 * 以便必要的时候重新申请 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            activity.requestPermissions(needPermissions, PERMISSION_REQUEST_CODE);

        }
        /** * 判断存储委授予权限的集合是否为空 */
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {

                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        // 判断是否勾选禁止后不再询问
                        boolean showRequestPermission = activity.shouldShowRequestPermissionRationale(permissions[i]);
                        if (showRequestPermission) {
                            Toast.makeText(activity, permissions[i]+"权限被拒", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        }
    }

}
