package com.example.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class SMSActivity extends BaseActivity {
	
	TextView smsFrom;
	TextView smsContent;
	MessageReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sms);
		
		smsFrom = (TextView) findViewById(R.id.sms_from_textview);
		smsContent = (TextView) findViewById(R.id.sms_content_textview);
		//注册广播接收器
		Log.d("sms", "注册接收器MessageReceiver()...");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		receiver = new MessageReceiver();
		registerReceiver(receiver, intentFilter);
		Log.d("sms", "注册完毕MessageReceiver()...");
		
	}//onCreate()
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	
	/**
	 * 改内部类用于处理接受到信息后的事宜
	 * 解析数据 并且 显示在页面上
	 * @author lee
	 *
	 */
	class MessageReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("sms", "接收到SMS，开始解析数据...");
			// TODO Auto-generated method stub
			Bundle bundle = intent.getExtras();
			Object[] pdus = (Object[])bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			}
			
			String address = messages[0].getOriginatingAddress();
			String content = "";
			for (SmsMessage message:messages) {
				content += message.getMessageBody();
			}
			
			Log.d("sms", "解析数据完毕，开始显示接收到的SMS...");
			//显示在页面上
			smsFrom.setText(address);
			smsContent.setText(content);
			
		}
		
		
	}//MessageReceiver 内部类
	
	
	

}
