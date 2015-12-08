package com.example.msg;

import java.util.List;

import com.example.imagebrowser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg> {

	int textViewResourceID;
	
	//构造方法，把textview的XML文件ID取出来
	public MsgAdapter(Context context, int resource, List<Msg> msgs) {
		super(context, resource, msgs);
		// TODO Auto-generated constructor stub
		textViewResourceID = resource;
	}
	
	/**
	 * getview( ) ，当listview中的某一个子项滑倒屏幕里面的时候调用该方法
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View msgItemView;
		ViewHolder viewHolder = new ViewHolder();
		TextView msgItemLeftTextView;
		LinearLayout msgItemLeftLinearLayout;
		TextView msgItemRightTextView;
		LinearLayout msgItemRightLinearLayout;
		Msg msg = getItem(position);
		
		if(convertView == null){
			msgItemView = LayoutInflater.from(getContext()).inflate(textViewResourceID, null);
			msgItemLeftLinearLayout = (LinearLayout) msgItemView.findViewById(R.id.msg_item_left_layout);
			msgItemLeftTextView = (TextView) msgItemView.findViewById(R.id.msg_item_left_textview);
			msgItemRightLinearLayout = (LinearLayout) msgItemView.findViewById(R.id.msg_item_right_layout);
			msgItemRightTextView = (TextView) msgItemView.findViewById(R.id.msg_item_right_textview);
			
			viewHolder.msgItemLeftLayout = msgItemLeftLinearLayout;
			viewHolder.msgItemLeftTextView = msgItemLeftTextView;
			viewHolder.msgItemRightLayout = msgItemRightLinearLayout;
			viewHolder.msgItemRightTextView = msgItemRightTextView;
			msgItemView.setTag(viewHolder);
		
		}else {
			msgItemView = convertView;
			viewHolder = (ViewHolder) msgItemView.getTag();
			msgItemLeftLinearLayout = viewHolder.msgItemLeftLayout;
			msgItemLeftTextView = viewHolder.msgItemLeftTextView;
			msgItemRightLinearLayout = viewHolder.msgItemRightLayout;
			msgItemRightTextView = viewHolder.msgItemRightTextView;
		}
		
		if(msg.getStat() == Msg.STAT_RECEIVED){
			msgItemLeftLinearLayout.setVisibility(View.VISIBLE);
			msgItemRightLinearLayout.setVisibility(View.GONE);
			msgItemLeftTextView.setText(msg.getContentString());
		}else if (msg.getStat() == Msg.STAT_SEND) {
			msgItemLeftLinearLayout.setVisibility(View.GONE);
			msgItemRightLinearLayout.setVisibility(View.VISIBLE);
			msgItemRightTextView.setText(msg.getContentString());
		}
		
		return msgItemView;
		
	}
	
	
	 public class ViewHolder{
		LinearLayout msgItemLeftLayout;
		LinearLayout msgItemRightLayout;
		TextView msgItemLeftTextView;
		TextView msgItemRightTextView;
	}

}
