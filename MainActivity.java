package edu.gupu.shiyanseven_two;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    private final  class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo= cm.getActiveNetworkInfo();
            if(networkInfo == null){
                // WIFI 和 移动网络都关闭 即没有有效网络
                Log.e("MainActivity", "当前无网络连接");
                return;
            }
            String typeName = "";
            if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                //WIFI网络跳转的页面.
                typeName = networkInfo.getTypeName();//==> WIFI
            }else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
                //无线网络跳转的页面
                typeName = networkInfo.getTypeName();//==> MOBILE
            }
            Log.e("MainActivity", "网络是==>" + typeName);
            Log.e("MainActivity", "状态是==>" + networkInfo.getDetailedState());
        }
    }
}