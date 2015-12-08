package com.example.imagebrowser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyLocalBroadcastReceiver extends BroadcastReceiver {

	public MyLocalBroadcastReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "local broadcast recevier", Toast.LENGTH_SHORT).show();

	}

}
