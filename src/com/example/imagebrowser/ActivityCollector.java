package com.example.imagebrowser;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector extends Activity {

	public static List<Activity> activities = new ArrayList<Activity>();
	
	/**
	 * 向ActivityCollector中添加一个元素
	 * @param activity
	 */
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	/**
	 * 从ActivityCollector移除一个元素
	 */
	public static void remoteActivity(){
		activities.remove(activities.size()-1);
	}
	/**
	 * 移除 ActivityCollector中的所有活动
	 */
	public static void finishAll(){
		for(int i = 0;i<activities.size();i++){
			Activity activity = activities.get(i);
			activity.finish();
		}
	}

}
