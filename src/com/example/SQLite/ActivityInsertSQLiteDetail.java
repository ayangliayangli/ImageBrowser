package com.example.SQLite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class ActivityInsertSQLiteDetail extends BaseActivity {

	EditText nameEditText;
	EditText ageEditText;
	EditText scoreEditText;
	EditText isMarrayEditText;
	Button submitButton;
	MySQLiteHelper openHelper;
	SQLiteDatabase db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert_sqlite_detail);
		
		//得到页面上的控件
		nameEditText = (EditText) findViewById(R.id.name_insert_sqlite_detail);
		ageEditText = (EditText) findViewById(R.id.age_insert_sqlite_detail);
		scoreEditText = (EditText) findViewById(R.id.score_insert_sqlite_detail);
		isMarrayEditText = (EditText) findViewById(R.id.ismarray_insert_sqlite_detail);
		submitButton = (Button) findViewById(R.id.submit_insert_sqlite_detail);
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openHelper = new MySQLiteHelper(ActivityInsertSQLiteDetail.this, "studentsM.db", null, 1);
				db = openHelper.getWritableDatabase();
				//保存数据
				saveDataBySQLite();
				//跳转到主页面
				Toast.makeText(ActivityInsertSQLiteDetail.this, "插入成功，准备跳转", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(ActivityInsertSQLiteDetail.this, ActivitySQLiteMain.class);
				startActivity(intent);
			}
		});
		
	}
	
	//把数据保存在数据库中
	private void saveDataBySQLite() {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("name", nameEditText.getText().toString());
		values.put("age", ageEditText.getText().toString());
		values.put("score", scoreEditText.getText().toString());
		values.put("isMarray", isMarrayEditText.getText().toString());
		db.insert("student", null, values);
	}
	
}
