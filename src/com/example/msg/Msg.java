package com.example.msg;

import android.app.Activity;

public class Msg extends Activity {
	public static final int STAT_RECEIVED = 0;
	public static final int STAT_SEND = 1;
	
	private String contentString ; //聊天的内容
	private int stat; //信息发送的状态
	
	public Msg(String content,int stat) {
		// TODO Auto-generated constructor stub
		this.contentString = content;
		this.stat = stat;
	}

	public String getContentString() {
		return contentString;
	}

	public void setContentString(String contentString) {
		this.contentString = contentString;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}
	
	

}
