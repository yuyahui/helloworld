package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private IntentFilter intentfilter;
	private NetworkChangeReceiver networkchangereceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
				sendBroadcast(intent);
			}
		});   
		//用于定义intent的各种属性
		intentfilter = new IntentFilter();
		//接收一个值为android.net.conn.CONNECTIVITY_CHANGE的一个广播
		intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		//创建一个网络改变时的一个接收者
		networkchangereceiver = new NetworkChangeReceiver();
		//传进一个networkchangereceiver、intentfilter后可以实现监听网络的变化
		registerReceiver(networkchangereceiver,intentfilter);
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(networkchangereceiver);
	}
	
    class NetworkChangeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//用getSystemService得到一个系统服务类connectivitymanager，connectivitymanager专用于管理广播
			ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkinfo =  connectivitymanager.getActiveNetworkInfo();
			if(networkinfo!=null&&networkinfo.isAvailable()){
				Toast.makeText(context, "network is available!", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(context, "network is unavailable!", Toast.LENGTH_LONG).show();
			}
		}
    	
    }
	
}
