package com.example.SQLite;

import java.util.List;

import com.example.imagebrowser.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StudentsAdapter extends ArrayAdapter<Students> {

	private int resourceTextViewID;
	private Context context;
	
	public StudentsAdapter(Context context, int resource, List<Students> students) {
		super(context, resource, students);
		resourceTextViewID = resource;
		this.context = context;
		// TODO Auto-generated constructor stub
	}


	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View studentsItemView;
		TextView textViewInStudentItem;
		Students currentStudents;
		String currentmsg;
		
		studentsItemView = LayoutInflater.from(context).inflate(resourceTextViewID, null);
		textViewInStudentItem = (TextView) studentsItemView.findViewById(R.id.students_item_list_view_text_view);
		currentStudents = getItem(position);
		currentmsg = "用户名:" + currentStudents.getName() + "年龄:" +currentStudents.getAge() +"分数:" + currentStudents.getScore() + "婚姻状态:" +currentStudents.getIsMarray();
		textViewInStudentItem.setText(currentmsg);
		
		
		return studentsItemView;
	}

}
