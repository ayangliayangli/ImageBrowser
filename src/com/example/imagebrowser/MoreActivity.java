package com.example.imagebrowser;

import java.io.File;

import com.example.location.ActivityShowMyLocation;
import com.example.media.MediaPlayerMusicActivity;
import com.example.media.TakePhotoActivity;
import com.example.net.NetActivity;
import com.example.net.WebViewActivity;
import com.example.sensor.SensorActivity;
import com.example.service.ControlMyServiceActivity;
import com.example.sms.SMSActivity;

import android.R.color;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MoreActivity extends BaseActivity implements OnClickListener{

	Button showMyLocationButton = null;
		 @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_more);
			
			showMyLocationButton = (Button) findViewById(R.id.second_button_show_my_location);
			showMyLocationButton.setOnClickListener(this);
			
			Button webViewButton = (Button) findViewById(R.id.second_button_web_view);
			webViewButton.setOnClickListener(this);
			
			Button netActivityButton = (Button) findViewById(R.id.second_button_net_activity);
			netActivityButton.setOnClickListener(this);
			
			Button notificationToMainButton = (Button) findViewById(R.id.second_button_notification_to_main_activity);
			notificationToMainButton.setOnClickListener(this);
			
			Button smsButton = (Button) findViewById(R.id.second_button_sms_activity);
			smsButton.setOnClickListener(this);
		
			Button takePhotoButton = (Button) findViewById(R.id.second_button_take_photo_activity);
			takePhotoButton.setOnClickListener(this);
			
			Button musicMediaPlayerButton = (Button) findViewById(R.id.second_button_music_mediaplayer_activity);
			musicMediaPlayerButton.setOnClickListener(this);
			
			Button serviceButton = (Button) findViewById(R.id.second_button_service_activity);
			serviceButton.setOnClickListener(this);
			
			Button sensorButton = (Button) findViewById(R.id.second_button_sensor_activity);
			sensorButton.setOnClickListener(this);
			
			
		 }
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.second_button_show_my_location:
				Intent intent = new Intent(this, ActivityShowMyLocation.class);
				startActivity(intent);
				break;
			case R.id.second_button_web_view:
				Intent intent2 = new Intent(this, WebViewActivity.class);
				startActivity(intent2);
				break;
			case R.id.second_button_net_activity:
				Intent intent3 = new Intent(this, NetActivity.class);
				startActivity(intent3);
				break;
			case R.id.second_button_notification_to_main_activity:
				//先构造一个用于跳转的PendingIntent
				Intent intent4 = new Intent(this, MainActivity.class);
				PendingIntent pi = PendingIntent.getActivity(this, 0, intent4, PendingIntent.FLAG_CANCEL_CURRENT);
				//构造一个 notification 点击之后跳转到主页
				NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification = new Notification(R.drawable.juan1, "我有件事儿要告诉你", System.currentTimeMillis());
				notification.setLatestEventInfo(this, "this is title", "this is content:点击后跳转到主页", pi);
				//声音 
				Uri uri = Uri.fromFile(new File("/system/media/audio/ringtones/MI.ogg"));
				notification.sound = uri;
				//振动，要申明权限 ??? 书上说要申明vibrate权限，但是我没有申明，也能使用
				long[] vibrates = new long[]{0,500,50,500,50,500,500,500,50,500,50,500,100};
				notification.vibrate = vibrates ;
				//LED灯光指示
				notification.ledARGB = color.holo_red_light;
				notification.ledOnMS = 1000;
				notification.ledOffMS = 1000;
				notification.flags = Notification.FLAG_SHOW_LIGHTS;
				//全部默认，又声音和振动
				//notification.defaults = Notification.DEFAULT_ALL;
				manager.notify(MainActivity.NOTIFICATION_ID_TO_MAIN, notification);
				break;
			case R.id.second_button_sms_activity:
				Intent intent5 = new Intent(this, SMSActivity.class);
				startActivity(intent5);
				break;
			case R.id.second_button_take_photo_activity:
				Intent intent6 = new Intent(this, TakePhotoActivity.class	);
				startActivity(intent6);
				break;
			case R.id.second_button_music_mediaplayer_activity:
				Intent intent7 = new Intent(this, MediaPlayerMusicActivity.class);
				startActivity(intent7);
				
				break;
			case R.id.second_button_service_activity:
				Intent intent8 = new Intent(this, ControlMyServiceActivity.class);
				startActivity(intent8);
				
				break;
			case R.id.second_button_sensor_activity:
				Intent intent9 = new Intent(this, SensorActivity.class);
				startActivity(intent9);
				
				break;
			default:
				break;
			}
			
		}
	
}
