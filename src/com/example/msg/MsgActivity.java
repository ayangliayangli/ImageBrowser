package com.example.msg;

import java.util.ArrayList;
import java.util.List;

import com.example.imagebrowser.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MsgActivity extends Activity {

	ArrayList<Msg> msgs = new ArrayList<Msg>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Msg msg;
		final MsgAdapter msgAdapter;
		final ListView msgListView;
		Button receiveButton;
		Button sendButton;
		final EditText msgContentEditText;
		
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_msg);
		
		initMsgs();//初始化消息，打开就显示的几个消息
		msgListView = (ListView) findViewById(R.id.msg_listView);
		msgAdapter = new MsgAdapter(MsgActivity.this, R.layout.msg_item, msgs);
		msgListView.setAdapter(msgAdapter);
		
		//send meg
		msgContentEditText = (EditText) findViewById(R.id.msg_contentEidtText);
		sendButton = (Button) findViewById(R.id.msg_send_button);
		receiveButton = (Button) findViewById(R.id.msg_receive_button);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Msg currentMsg = new Msg(msgContentEditText.getText().toString(), Msg.STAT_SEND);
				Log.d("content", msgContentEditText.getText().toString());
				msgs.add(currentMsg);
				msgAdapter.notifyDataSetChanged();
				msgListView.setSelection(msgs.size());
				msgContentEditText.setText("");
				//msgListView.setAdapter(msgAdapter);
			}
		});
		
receiveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Msg currentMsg = new Msg(msgContentEditText.getText().toString(), Msg.STAT_RECEIVED);
				Log.d("content", msgContentEditText.getText().toString());
				msgs.add(currentMsg);
				msgAdapter.notifyDataSetChanged();
				msgListView.setSelection(msgs.size());
				msgContentEditText.setText("");
				//msgListView.setAdapter(msgAdapter);
			}
		});
		
		
		
	}
	
	public void initMsgs(){
		Msg msg1 = new Msg("hello", Msg.STAT_RECEIVED);
		msgs.add(msg1);
		Msg msg2 = new Msg("hi", Msg.STAT_SEND);
		msgs.add(msg2);
		Msg msg3 = new Msg("我爱你，哥哥", Msg.STAT_RECEIVED);
		msgs.add(msg3);
	}

}
