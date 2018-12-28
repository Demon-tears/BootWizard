package cn.com.techvision.bootwizard.wifi;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.List;

import cn.com.techvision.bootwizard.R;
import cn.com.techvision.bootwizard.util.BaseList.BaseFragment;
import cn.com.techvision.bootwizard.util.BaseList.ItemBean;
import cn.com.techvision.bootwizard.util.RequestPermissionHelp;

public class WifiSettingActivity extends Activity implements BaseFragment.OnItemFragmentClickListener {

    BaseFragment baseFragment;
    private WifiManager wifiManager;
    private IntentFilter mWifiStateFilter;
    List<ItemBean> itemBeanList;
    RequestPermissionHelp requestPermissionHelp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.wifi_laytout);

        requestPermissionHelp = new RequestPermissionHelp(this);
        requestPermissionHelp.checkPermission();

        initView();
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initDate();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mWifiStateReceiver);
        super.onPause();
    }

    private void initView() {
        baseFragment = new BaseFragment();
        baseFragment.setList(itemBeanList);
        baseFragment.setOnItemFragmentClickListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_content,baseFragment);
        transaction.commit();

    }

    private void initDate(){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        mWifiStateFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mWifiStateFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mWifiStateFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mWifiStateFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiStateReceiver,mWifiStateFilter);

    }

    private  BroadcastReceiver mWifiStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {//wifi状态改变
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN);
                if (state == WifiManager.WIFI_STATE_ENABLED) {//wifi打开
                    wifiManager.startScan();//wifi开始搜索
                }
            }else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)){
                refreshScanResults();
            }else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)){

            }
        }
    };

    private void refreshScanResults() {
        List<ScanResult> list = wifiManager.getScanResults();
        itemBeanList = baseFragment.getList();
        itemBeanList.clear();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                final ScanResult scanResult = list.get(i);

                if (scanResult == null) {
                    continue;
                }

                if (TextUtils.isEmpty(scanResult.SSID)) {
                    continue;
                }
                ItemBean itemBean = new ItemBean();
                itemBean.setItemTitle(scanResult.SSID);
                Log.d("lrh","SSID = " + scanResult.SSID);
                itemBean.setItemContent(Integer.toString(WifiManager.calculateSignalLevel(scanResult.level, 5)));
                itemBeanList.add(itemBean);
            }

            baseFragment.refreshAdapter();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseFragment.refreshAdapter();
                        }
                    });

                }
            }).start();

        }
    }


    @Override
    public void onItemClick(View view, int position) {
        connectWifi(position);
        Log.d("lrh","WifiSettingActivity " + position);
    }

    private void connectWifi(int position) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        requestPermissionHelp.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
