package com.example.listview;

import java.util.List;

import com.example.imagebrowser.MainActivity;
import com.example.imagebrowser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit> {

	int resourceID ;
	
	public FruitAdapter(Context context, int resourceID, List<Fruit> fruits) {
		super(context, resourceID,fruits);
		this.resourceID = resourceID;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder viewHolder = new ViewHolder();
		Fruit fruit = getItem(position);
		
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceID, null);
			TextView fruitTextView = (TextView) view.findViewById(R.id.fruit_item_textview);
			ImageView fruitImageView = (ImageView) view.findViewById(R.id.fruit_item_imageview);
			viewHolder.fruitImageView = fruitImageView;
			viewHolder.fruitTextView = fruitTextView;
			view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder)view.getTag();
		}
		
		viewHolder.fruitImageView.setImageResource(fruit.getFruitImageViewID());
		viewHolder.fruitTextView.setText(fruit.getFruitNameString());
		return view;
	}
	
	

}
