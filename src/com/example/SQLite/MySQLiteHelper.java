package com.example.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private Context mContext;
	private static final String //创建学生表的SQL语句
		CREATE_BOOK = "create table student("
		+ "id integer primary key autoincrement,"
		+ "name text,"
		+ "age integer,"
		+ "score real,"
		+ "isMarray text"
		+ ")";
	
	
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_BOOK);
		//contentProvider中不支持Toast,暂时删除
		//Toast.makeText(mContext, "studentM.db create succeed", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
