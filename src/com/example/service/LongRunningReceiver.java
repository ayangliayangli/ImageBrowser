package com.example.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LongRunningReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("longrun", "receiver 准备启动service");
		Intent intent2 = new Intent(context, MyService.class);
		context.startService(intent2);
		
		
	}
	

}
