package com.exmaple.contentprovider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

/**
 * 使用contentresolve().query()方法把联系方式查询出来，放在一个cursor中，然后利用listView显示出来
 * 使用listview 直接使用系统自带的arrayadapter 以及 android.R.layout.simple_list_item_1 这个layout
 */
public class readContactsActivity extends BaseActivity {
	ListView readcontactsListView;
	ArrayAdapter<String> adapter;
	List<String> contacts = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_contacts);
		
		readcontactsListView = (ListView) findViewById(R.id.read_contacts_list_view);
		readContacts();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
		readcontactsListView.setAdapter(adapter);
		
	}
	
	private void readContacts() {
		Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contacts.add(name + "\n" + number);
			} while (cursor.moveToNext());
			
		}
		
	}
}
