package com.example.SQLite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class ActivitySQLiteMain extends BaseActivity {
	
	MySQLiteHelper mySQLiteHelper;
	SQLiteDatabase db;
	Button insertButton;
	Button insertDetailButton;
	Button updateButton;
	Button deleteButton;
	Button querybButton;
	ListView studentListView;
	StudentsAdapter studentsAdapter;
	//Students student = new Students();
	ArrayList<Students> students = new ArrayList<Students>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sqlite);
		//找到各个组建，以及初始化 MySQLiteHelper
		mySQLiteHelper = new MySQLiteHelper(this, "studentsM.db", null, 1);
		insertButton = (Button) findViewById(R.id.sqlite_insert);
		updateButton = (Button)findViewById(R.id.sqlite_update);
		deleteButton = (Button)findViewById(R.id.sqlite_delete);
		querybButton = (Button) findViewById(R.id.sqlite_query);
		insertDetailButton = (Button) findViewById(R.id.sqlite_insert_detail);
		db = mySQLiteHelper.getReadableDatabase();
		
		insertButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.clear();
				values.put("name", "yangli");
				values.put("age", 25);
				values.put("score", 99);
				values.put("isMarray", "是");
				db.insert("student", null, values);
				values.clear();
				values.put("name", "dujuan");
				values.put("age", 24);
				values.put("score", 98);
				values.put("isMarray","否");
				db.insert("student", null, values);
				
				//清除本地的数据-全局变量 ArrayList<Students> students
				students.clear();
			}
		});
		
		
		insertDetailButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivitySQLiteMain.this, ActivityInsertSQLiteDetail.class	);
				startActivity(intent);
			}
		});
		
		
		//更新数据
		updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("score", 100);
				db.update("student", values, "name = ?", new String[]{"yangli"});
				
				//清除本地的数据-全局变量 ArrayList<Students> students
				students.clear();
			}
		});
		
		//删除数据
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.delete("student", "name = ?",new String[]{"yangli"});
				db.delete("student", "name=?",new String[]{"dujuan"});
				
				//清除本地的数据-全局变量 ArrayList<Students> students
				students.clear();
			}
		});
		
		querybButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//查询之前,先清除上次的查询结果
				students.clear();
				Cursor cursor = null;
				cursor = db.query("student", null, null, null, null, null, null);
				if (cursor.moveToFirst()) {
					int i = 0;
					int count = cursor.getCount();
					do {
						i++;
						String name = cursor.getString(cursor.getColumnIndex("name"));
						int age = cursor.getInt(cursor.getColumnIndex("age"));
						Log.d("sql",i + "/" + count + "--" + name);
						Log.d("sql",i + "/" + count + "--" + age);
						//把数据取出来放在student中
						double score = cursor.getDouble(cursor.getColumnIndex("score"));
						String isMarray = cursor.getString(cursor.getColumnIndex("isMarray"));
						Students student = new Students();
						student .setName(i + "/" + count +":" + name);
						student.setAge(age);
						student.setScore(score);
						student.setIsMarray(isMarray);
						students.add(student);
					} while (cursor.moveToNext());
				}
				cursor.close();
				
				//在listview中显示出来
				studentListView = (ListView) findViewById(R.id.students_list_view);
				studentsAdapter = new StudentsAdapter(ActivitySQLiteMain.this, R.layout.students_item_list_view, students);
				studentListView.setAdapter(studentsAdapter);
			}
		});
		
	}





}
