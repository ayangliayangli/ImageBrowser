package com.example.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;
import com.example.service.MyService.downLoadBinder;

public class ControlMyServiceActivity extends BaseActivity implements OnClickListener {

	private int isServiceStarted = 0; //判断进程是否绑定了服务，默认0 没有绑定，非0表示已经绑定
	
	Button startServiceButton;
	Button stopServiceButton;
	Button bindServiceButton;
	Button unbindServiceButton;
	TextView infoTextView;
	
	
	downLoadBinder downLoadBinder;
	ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		
		//此方法中使用服务传过来的binder ，使用的位置
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			String infoString = "";
			downLoadBinder = (com.example.service.MyService.downLoadBinder) service;
			infoString += downLoadBinder.startDownload() + "\n";
			infoString += downLoadBinder.getStatus() + "\n";
			//show the infomation in foreground
			infoTextView.setText(infoString);
			
		}
	};
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_control_my_service);
		
		
		startServiceButton = (Button) findViewById(R.id.start_my_servcie);
		stopServiceButton = (Button) findViewById(R.id.stop_my_servcie);
		bindServiceButton = (Button) findViewById(R.id.bind_my_servcie);
		unbindServiceButton = (Button) findViewById(R.id.unbind_my_servcie);
		infoTextView = (TextView) findViewById(R.id.info_control_my_service);
		
		startServiceButton.setOnClickListener(this);
		stopServiceButton.setOnClickListener(this);
		bindServiceButton.setOnClickListener(this);
		unbindServiceButton.setOnClickListener(this);
		
		
		
	}//end of onCreate() -- ControlMyServiceActivity
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent intent2 = new Intent(this, MyService.class);
		stopService(intent2);
		
	}


	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start_my_servcie:
			Intent intent = new Intent(this, MyService.class);
			startService(intent);
			
			break;
		case R.id.stop_my_servcie:
			Intent intent2 = new Intent(this, MyService.class);
			stopService(intent2);
			
			break;
		case R.id.bind_my_servcie:
			if (isServiceStarted == 0) {
				Intent intent3 = new Intent(this, MyService.class);
				bindService(intent3, connection, Context.BIND_AUTO_CREATE);
				isServiceStarted = 1;
			}else {
				infoTextView.setText("已经绑定了，不能再绑定了");
			}
			
			break;
		case R.id.unbind_my_servcie:
			if (isServiceStarted != 0) {
				String infoString = downLoadBinder.finishDownload();
				infoTextView.setText(infoString);
				unbindService(connection);
				isServiceStarted = 0;
			} else {
				infoTextView.setText("服务还没有绑定，不能解绑");
			}
			
			
			break;

		default:
			break;
		}
		
		
	}
	
	
	
}
