package com.example.listview;

import java.util.ArrayList;

import com.example.imagebrowser.MainActivity;
import com.example.imagebrowser.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class FruitActivity extends Activity {
	ArrayList<Fruit> fruits = new ArrayList<Fruit>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.blank);
		
		initfruits();
		FruitAdapter adapter = new FruitAdapter(FruitActivity.this, R.layout.fruit_item, fruits);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(adapter);
		
	}
	
	private void initfruits() {
		for(int i =0;i<100;i++){
			Fruit juan1 = new Fruit("apple", R.drawable.juan1);
			fruits.add(juan1);
			Fruit juan2 = new Fruit("apple", R.drawable.juan2);
			fruits.add(juan2);
			Fruit juan3 = new Fruit("apple", R.drawable.juan3);
			fruits.add(juan3);
			Fruit yang6 = new Fruit("apple", R.drawable.yang6);
			fruits.add(yang6);
			Fruit yang5 = new Fruit("apple", R.drawable.yang5);
			fruits.add(yang5);
		}
		
		
	}
	

}
