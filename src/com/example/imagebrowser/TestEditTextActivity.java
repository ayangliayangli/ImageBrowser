package com.example.imagebrowser;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestEditTextActivity extends BaseActivity implements OnClickListener{
	//用于获取界面上的几个控件，因为只有一个，所以定义为类的变量
	EditText editText;
	Button button;
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_view);
		
		editText = (EditText) findViewById(R.id.editTextInTestEditText);
		button = (Button)findViewById(R.id.buttonInTestEditText);
		textView = (TextView)findViewById(R.id.textViewInTestEditText);
		button.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String editTextDataString = editText.getText().toString();
		if(editTextDataString.equals("静静")){
			editTextDataString = editTextDataString + "是力哥的，真的，我发誓!妹妹，哥哥每天都爱你哦";
		}else {
			editTextDataString = editTextDataString + "   what? 输入『静静』试试";
		}
		textView.setText(editTextDataString);
		
	}

}
