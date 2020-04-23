package cn.nodemedia.pusher;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.view.View;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.nodemedia.pusher.widget.HintDialog;
import xyz.tanwb.airship.App;
import xyz.tanwb.airship.BaseApplication;
import xyz.tanwb.airship.rxjava.RxBus;
import xyz.tanwb.airship.utils.Log;
import xyz.tanwb.airship.utils.NetUtils;

public class MyApplication extends BaseApplication {

    private ConcurrentHashMap<String, Dialog> dialogs;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

//        if (App.isNamedProcess(getPackageName())) {
//            // MyMQTTService.startAndConnect(this);
//            // intent = new Intent(this, MyPrinterService.class);
//
//            rxBusManage = new RxBusManage();
//            rxBusManage.on(ConnectivityManager.CONNECTIVITY_ACTION, new Action1<Object>() {
//                @Override
//                public void call(Object o) {
//                    Log.i("收到网络改变广播");
//                    managerNetworkDialog((boolean) o);
//                }
//            });
//
//            rxBusManage.on(Manifest.permission.WRITE_SETTINGS, new Action1<Object>() {
//                @Override
//                public void call(Object o) {
//                    Log.e("MyApplication收到了启动网络广播");
//                    Log.e("启动网络广播");
//                    receiveNetwork();
//                }
//            });
//        } else {
//            Log.i("启动其他进程");
//        }
    }

    /**
     * 注册网络监听广播
     */
    private void receiveNetwork() {
        if (broadcastReceiver != null) {
            return;
        }
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("My intent.getAction():" + intent.getAction());
                switch (intent.getAction()) {
                    case ConnectivityManager.CONNECTIVITY_ACTION:
                        boolean isconnect = NetUtils.isConnected();
                        if (isconnect) {
                            // BaseMQTTService.startAndConnect(context);
                        }
                        managerNetworkDialog(NetUtils.isConnected());
                        break;
                    case Intent.ACTION_TIME_TICK:
                        RxBus.getInstance().post(Intent.ACTION_TIME_TICK);
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        break;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.e("NetworkCallback onAvailable:" + NetUtils.isConnected());
                    // BaseMQTTService.startAndConnect(getApplicationContext());
                    managerNetworkDialog(true);
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    Log.e("NetworkCallback onLost:" + NetUtils.isConnected());
                    managerNetworkDialog(false);
                }
            });
        } else {
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);// 网络改变广播,每分钟发出一次
        }
        intentFilter.addAction(Intent.ACTION_TIME_TICK);// 时间改变广播,每分钟发出一次
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);// 手机熄屏广播
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);// 手机亮屏广播
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 管理网络提示弹框
     */
    private void managerNetworkDialog(boolean isConnect) {
        if (isConnect) {
            if (dialogs != null && dialogs.size() > 0) {
                for (Map.Entry<String, Dialog> dialog : dialogs.entrySet()) {
                    if (dialog.getValue().isShowing()) {
                        dialog.getValue().dismiss();
                    }
                }
                dialogs.clear();
            }
        } else {
            if (dialogs == null) {
                dialogs = new ConcurrentHashMap<>();
            }
            Activity activity = getLastActivity();
            if (activity != null && !dialogs.containsKey(activity.getClass().getName())) {
                Dialog dialog = new HintDialog.Builder(activity).
                        setTitle("网络提示").
                        setMessage("糟糕，您与地球网络暂时失联啦！请重新建立连接。").
                        setCancelable(false).
                        setLeftButton("退出使用").
                        setRightButton("设置网络").
                        setOnClickListener(new HintDialog.OnClickListener() {
                            @Override
                            public void onClick(HintDialog.Builder builder, View view, int position, Object param) {
                                dialogs.remove(builder.getDialog());
                                switch (position) {
                                    case HintDialog.LEFT:
                                        exit();
                                        break;
                                    case HintDialog.RIGHT:
                                        App.openNetSetting(getApplicationContext());
                                        break;
                                }
                            }
                        }).show();
                dialogs.put(activity.getClass().getName(), dialog);
            }
        }
    }

    @Override
    public void exit() {
//        if (rxBusManage != null) {
//            rxBusManage.clear();
//            rxBusManage = null;
//        }
        super.exit();
    }


}
