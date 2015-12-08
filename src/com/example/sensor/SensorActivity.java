package com.example.sensor;


import android.R.xml;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;


public class SensorActivity extends BaseActivity {

	SensorManager manager;
	ImageView compassImageView;
	ImageView arrowImageView;
	TextView infoTextView;
	TextView info1TextView;
	TextView info2TextView;
	
	
	SensorEventListener listener = new SensorEventListener() {
		//这个是类变量，保证一直有数据
		float[] accelerometerValues = new float[3];
		float[] magneticValues = new float[3];
		double lastRotationDegree;
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			
			// TODO Auto-generated method stub
			//主要逻辑在这里，每次数据更新都会促发此方法
			if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				//如果是加速器传过来的数据 执行逻辑
				accelerometerValues = event.values.clone();
				
			}else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				//如果是磁力器传过来的数据执行的逻辑
				magneticValues = event.values.clone();
			}
			
			float[] R = new float[9];
			float[] orientationValues = new float[3];
			manager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
			manager.getOrientation(R, orientationValues);
			double X = Math.toDegrees(orientationValues[0]);
			Log.d("sensor", "orientationX:" + X);
			Log.d("sensor", "orientationY:" + Math.toDegrees(orientationValues[1]) );
			Log.d("sensor", "orientationZ:" + Math.toDegrees(orientationValues[2]) );
			infoTextView.setText("orientationX:" + X);
			info1TextView.setText("orientationY:" + Math.toDegrees(orientationValues[1]));
			info2TextView.setText("orientationZ:" + Math.toDegrees(orientationValues[2]));
			
			double currentRotationDegree = -X;
			//设置动画 如果角度大于1
			if (Math.abs(currentRotationDegree - lastRotationDegree) > 2) {
				
				Log.d("sensor", "start animation ---------------------------------");
				RotateAnimation animation = new RotateAnimation((float)lastRotationDegree, (float)currentRotationDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setFillAfter(true);
				compassImageView.startAnimation(animation);
				lastRotationDegree = currentRotationDegree;
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sensor);
		
		infoTextView = (TextView) findViewById(R.id.info_sensor_textview);
		info1TextView = (TextView) findViewById(R.id.info_sensor1_textview);
		info2TextView = (TextView) findViewById(R.id.info_sensor2_textview);
		compassImageView = (ImageView) findViewById(R.id.compass_imageview);
		arrowImageView = (ImageView) findViewById(R.id.arrow_imageview);
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		//获取传感器实例，情切注册监听器
		Sensor accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		manager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
		manager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
		
		
	}//onCreate() end of it
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		manager.unregisterListener(listener); //important must be unregister
	}
	
}
