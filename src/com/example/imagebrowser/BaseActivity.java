package com.example.imagebrowser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
/**
 * 该方法中会重写Activity中与生命周期有关的所有方法 ,在该方法中打印执行了改方法的日志
 * onCreate onStart onResume onPause onStop onDestroy
 * @author lee
 *
 */
public class BaseActivity extends Activity {
	public static final String TAG = "Base Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()"+getClass().getSimpleName());
		//添加一个
		ActivityCollector.addActivity(this);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG, "onStart()"+getClass().getSimpleName());
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume()"+getClass().getSimpleName());
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(TAG, "onPause()"+getClass().getSimpleName());
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d(TAG, "onStop()"+getClass().getSimpleName());
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy()"+getClass().getSimpleName());
		ActivityCollector.remoteActivity();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(TAG, "onRestart()"+getClass().getSimpleName());
	}

}
