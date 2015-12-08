package com.example.service;

import java.util.Date;

import com.example.imagebrowser.MainActivity;
import com.example.imagebrowser.R;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends Service {
	
	LongRunningReceiver longRunningReceiver;
	//onBind() 会把此对象返回给主线程
	downLoadBinder downLoadBinder = new downLoadBinder();
	
	/**
	 * 进程操作服务的具体实现在这个binder里面
	 * @author lee
	 *
	 */
	class downLoadBinder extends Binder{
		
		public String startDownload() {
			//开始下载  逻辑
			Log.d("service", "start download");
			String returnString = "start down load";
			return returnString;
		}
		
		public String getStatus() {
			//返回状态  逻辑
			Log.d("service", "get status");
			String returnString = "getstatus";
			return returnString;
		}
		
		
		public String finishDownload(){
			Log.d("service", "finish download");
			String returnString = "finish download";
			return returnString;
		}
		
	}
	
	
	
	
	/**
	 * 进程要控制服务的时候使用该方法，该方法可以返回一个binder给主线程的
	 * serviceConnection--onServiceConnection()
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("service", "onBind()");
		return downLoadBinder;
	}
	
	/**
	 * 第一次创建服务的时候会调用该方法
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("service", "oncreate()");	
		
		//构造一个前台服务
		//服务高级知识
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		Notification notification = new Notification(R.drawable.juan1, "我是前台服务", System.currentTimeMillis());
		notification.setLatestEventInfo(this, "我是前台服务的标题哦", "内容：点击可以跳转到主页", pi);
		long[] vibrates = {0,1000,500,1000,500,1000};
		notification.vibrate = vibrates;
		startForeground(1, notification);
		
		
		//动态注册receiver ，这样可以在service的ondestroy()中，unregistered
		Log.d("longrun", "registerReceiver(longRunningReceiver, intentFilter);");
		longRunningReceiver = new LongRunningReceiver();
		IntentFilter intentFilter = new IntentFilter(); //注意，没有ACTION，因为 service显示制定了recevier
		intentFilter.addAction("com.example.service.action.startreceiver");
		registerReceiver(longRunningReceiver, intentFilter);
		
	}//onCreate()
	
	
	/**
	 * 每次启动服务的时候都会调用
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("service", "onStartCommand");
		
		
		
		//构造一个长时间运行的service 
		//利用alarmManager pendingIntent broadcast
		//
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String nowDateString = new Date().toString();
				Log.d("longrun", "现在的时间" + nowDateString);
			}
		}).start();
		
		
		Intent intent2 = new Intent("com.example.service.action.startreceiver");
		PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent2, 0);
		long triggerAtMillis = SystemClock.elapsedRealtime() + 5*1000;
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, operation);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	/**
	 * 销毁服务的时候会调用
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("service", "onDestroy");
		//service 停止的时候，同时把receiver注销
		unregisterReceiver(longRunningReceiver);
		Log.d("longrun", "unregisterReceiver");
	}

}
