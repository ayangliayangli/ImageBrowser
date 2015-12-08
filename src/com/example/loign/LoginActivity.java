package com.example.loign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.R.string;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class LoginActivity extends BaseActivity {
	EditText userEditText;
	EditText passwdEditText;
	TextView userLastTime;
	TextView passwdLastTime;
	Button loginButton;
	
	//用于存储数据的对象
	FileOutputStream outputStream;
	BufferedWriter writer;
	//用于从文件中娶数据的对象
	FileInputStream inputStream;
	BufferedReader reader;
	//用于存储数据的对象，使用SharedPreferences方法 方法二
	SharedPreferences userInfoSharedPreferences;
	Editor editor;
	
	//正确的用户名和密码
	 static final String NAME_OK = "11";
	 static final String PASSWD_OK = "22";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		userEditText = (EditText) findViewById(R.id.login_name);
		passwdEditText = (EditText) findViewById(R.id.login_passwd);
		userLastTime = (TextView) findViewById(R.id.login_name_last_time);
		passwdLastTime = (TextView) findViewById(R.id.login_passwd_last_time);
		loginButton = (Button) findViewById(R.id.login_ok);
		//如果有数据，加载,且放在相应的位置上
		//String[] userinfos = reloadByFile();
		String[] userinfos = reloadBySharedPreferences();
		userLastTime.setText(userinfos[0]);
		passwdLastTime.setText(userinfos[1]);
		
		
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String user = userEditText.getText().toString();
				String passwd = passwdEditText.getText().toString();
				Log.d("login", "输入用户： " + user);
				Log.d("login", "输入密码：" + passwd);
				Log.d("login", isLegal(user, passwd).toString());
				//初步测试用户名和密码是否为空
				if(userInfoIsNull(user, passwd)){
					//如果部位空，执行下面的程序
					//把数据保存在文件中
					saveDataByFile(user, passwd);
					//把数据保存在 sharedpreferences
					saveDataBySharedPreferences(user, passwd);
					//验证用户名和密码是否正确
					if (isLegal(user, passwd)) {
						//用户名密码正确，跳转到成功页面
						Toast.makeText(LoginActivity.this, "输入正确，准备跳转", Toast.LENGTH_LONG).show();
						LoginOKActivity.startAction(LoginActivity.this, user, passwd);
					}else {
						//用户名或者密码错误，提示错误
						Toast.makeText(LoginActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
						userEditText.setText("");
						passwdEditText.setText("");
					}
				}
			
			}
		});
		
	}
	
	public Boolean isLegal(String user,String passwd){
			if (user.equals(NAME_OK)&&passwd.equals(PASSWD_OK)) {
				return true;
			}
		return false;
	}
	
	void saveDataByFile(String user,String passwd){
		Log.d("save", "save_data_by_file");
		try {
			outputStream = openFileOutput("user", MODE_PRIVATE);
			writer = new BufferedWriter(new OutputStreamWriter(outputStream)) ;
			writer.write(user);
			Log.d("save", "user:" + user);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		try {
			outputStream = null;
			writer = null;
			outputStream = openFileOutput("passwd", 0);
			writer = new BufferedWriter(new OutputStreamWriter(outputStream)) ;
			writer.write(passwd);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	String[] reloadByFile(){
		String[] userInfos = {"","","","","","","","","","",""};
		String line = "";
		try {
			//读取用户
			inputStream = openFileInput("user");
			reader = new BufferedReader(new InputStreamReader(inputStream)); 
			while ((line = reader.readLine()) != null) {
				Log.d("reload","line-user:" + line);
				userInfos[0] = line;
			}
			//读取密码
			inputStream = null;
			reader = null;
			inputStream = openFileInput("passwd");
			reader =  new BufferedReader(new InputStreamReader(inputStream));
			while ((line = reader.readLine()) != null) {
				Log.d("reload","line-passwd:" + line);
				userInfos[1] = line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("reload","现有的数据：" + userInfos[0] + userInfos[1]);
		return userInfos;
	}
	
	
	void saveDataBySharedPreferences(String name,String passwd){
		Log.d("save", "save_data_by_sharedpreferences");
		userInfoSharedPreferences = getSharedPreferences("data", MODE_APPEND);
		editor = userInfoSharedPreferences.edit();
		editor.putString("name", name);
		editor.putString("passwd", passwd);
		editor.commit();
		
	}
	
	protected String[] reloadBySharedPreferences() {
		String[] userInfos = {"","","","","","","","","","",""};
		//获取数据放在数组中
		SharedPreferences sp = getSharedPreferences("data", MODE_APPEND);
		userInfos[0] = sp.getString("name", "defaylt");
		userInfos[1] = sp.getString("passwd", "default");
		
		return userInfos;
	}
	
	protected boolean userInfoIsNull(String name,String passwd){
		if(TextUtils.isEmpty(name)){
			Log.d("test_null", "输入名为空，请输入用户名");
			return false;
		}
		if(TextUtils.isEmpty(passwd)){
			Log.d("test_null", "密码为空，请输入密码");
			return false;
		}else {
			return true;
		}
	}
	

}
