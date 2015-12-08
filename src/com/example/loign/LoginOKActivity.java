package com.example.loign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class LoginOKActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_ok);
		
		
		
	}

	public static void startAction(Context context,String nameString,String passwordString){
		Intent intent = new Intent(context, com.example.loign.LoginOKActivity.class);
		intent.putExtra("name", nameString);
		intent.putExtra("passwd", passwordString);
		context.startActivity(intent);
		
	}
}
