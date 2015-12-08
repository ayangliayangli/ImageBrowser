package com.example.SQLite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StudentsProvider extends ContentProvider {

	private static final int STUDENT_DIR = 0;
	private static final int STUDENT_ITEM = 1;
	private static final String AUTHORITY = "com.example.imagebrowser.provider";
	private static UriMatcher uriMatcher;
	private MySQLiteHelper myHelper;
	
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "student", STUDENT_DIR);
		uriMatcher.addURI(AUTHORITY, "student/#", STUDENT_ITEM);
	}
	
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		myHelper = new MySQLiteHelper(getContext(), "studentsM.db", null, 1);
		return true;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = myHelper.getReadableDatabase();
		long newstudentID = -1;
		Uri uriReturn = null;
		switch (uriMatcher.match(uri)) {
		case STUDENT_DIR:
		case STUDENT_ITEM:
			newstudentID = db.insert("student", null, values);
			uriReturn = Uri.parse("content://" + AUTHORITY + "/student/" + newstudentID);
			break;
		default:
			break;
		}
		
		return uriReturn;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = myHelper.getReadableDatabase();
		int returnID = -1;
		
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case STUDENT_DIR:
			returnID = db.delete("student", selection, selectionArgs);
			break;
		case STUDENT_ITEM:
			String studentID = uri.getPathSegments().get(1);
			returnID = db.delete("student", "id=?", new String[]{studentID});
			break;
		default:
			break;
		}
		return returnID;
	}





	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = myHelper.getReadableDatabase();
		int returnid = -1;
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case STUDENT_DIR:
			returnid = db.update("student", values, selection, selectionArgs);
			break;
		case STUDENT_ITEM:
			String studentID = uri.getPathSegments().get(1);
			returnid = db.update("student", values, "id=?", new String[]{studentID});
			break;
		default:
			break;
		}
		return returnid;
	}
	
	


	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = myHelper.getReadableDatabase();
		Cursor returnCursor = null;
		// TODO Auto-generated method stub
		switch(uriMatcher.match(uri)){
		case STUDENT_DIR:
			returnCursor = db.query("student", projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case STUDENT_ITEM:
			String studentID = uri.getPathSegments().get(1);
			returnCursor = db.query("student", projection, "id=?", new String[]{studentID}, null, null, sortOrder);
			break;
		default:
			break;
		}
		return returnCursor;
	}
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

}
