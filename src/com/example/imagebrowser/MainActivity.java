package com.example.imagebrowser;

import com.example.listview.FruitActivity;
import com.example.msg.MsgActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends BaseActivity {
	
	int[] images = new int[]{
			//R.drawable.juan1,
			//R.drawable.juan2,
			//R.drawable.juan3,
			R.drawable.yang5,
			R.drawable.yang6,
	};
	public static final int NOTIFICATION_ID_TO_MAIN = 100;
	private int current = 1;
	private int alpha = 255;
	private int countOnClickPic=0;
	private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //把notification 这个通知去掉
       // NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // manager.cancel(NOTIFICATION_ID_TO_MAIN);
        
       final Button plusButton = (Button) findViewById(R.id.plus);
       final Button minusButton = (Button) findViewById(R.id.minus);
       final Button nextoneButton = (Button) findViewById(R.id.nextone);
       final Button preoneButton = (Button) findViewById(R.id.preone);
       final ImageView imageView1 = (ImageView) findViewById(R.id.image1);
       final ImageView imageView2 = (ImageView) findViewById(R.id.image2);
       final TextView textView1 = (TextView) findViewById(R.id.textview1);
       final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
       final Button moreButton = (Button) findViewById(R.id.more);
       
       imageView1.setImageResource(images[current]);
       imageView2.setImageResource(images[current]);
       nextoneButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int tmp = ++current % images.length;
			Log.i("value", String.valueOf(current));
			imageView1.setImageResource(images[tmp]);
			//编写进度条
			progress = (tmp+1)*100/images.length;
			//调试当前进度
			Log.d("progress_tmp_length", tmp+"_"+images.length);
			Log.d("progress",progress+"");
			progressBar.setProgress(progress);
			
		}
	});
       preoneButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int tmp = --current % images.length;
			//如果图片是第一张了，那么调整tmp 和 current的值，显示最后一张图片
			if (tmp == -1) {
				tmp = images.length -1;
				current = tmp;
			}
			Log.i("value", String.valueOf(current));
			imageView1.setImageResource(images[tmp]);
			//编写进度条
			progress = (tmp+1)*100/images.length;
			progressBar.setProgress(progress);
		}
	});
       
       //点击明暗的监听器
       OnClickListener onClickListener = new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == plusButton) alpha = alpha + 20;
			if (v == minusButton) alpha = alpha - 20;
			if (alpha>255)alpha = 255;
			if (alpha<0) alpha = 0;
			imageView1.setAlpha(alpha);
		}
	};
	//为button1 he button2注册上面的监听器
	plusButton.setOnClickListener(onClickListener);
	minusButton.setOnClickListener(onClickListener);
    
	OnTouchListener onTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			//获取imageview1中的图片
			Bitmap bitmap = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
			WindowManager wm = getWindowManager();
			int screamwidgh = wm.getDefaultDisplay().getWidth();
			int scale = bitmap.getWidth()/screamwidgh;
			int X = (int) (event.getX()*scale);
			int Y = (int) (event.getY()*scale);
			imageView2.setImageBitmap(Bitmap.createBitmap(bitmap, X, Y, 120, 120));
			return false;
		}
	};
	imageView1.setOnTouchListener(onTouchListener);
	
	//统计imageview1点击次数
	imageView1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			countOnClickPic++;
			String str = "点击图片次数：" + countOnClickPic;
			textView1.setText(str);
		}
	});
	
	//跳转到SecondActivity的点击事件监听器
		OnClickListener toSecondActivityOnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SecondActivity.class);
				startActivity(intent);
			}
		};
		imageView2.setOnClickListener(toSecondActivityOnClickListener);
	
		//注册一个广播接收器
		MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.imagebrowser.MainActivity.MY_ACTION");
		intentFilter.setPriority(100);
		registerReceiver(myBroadcastReceiver, intentFilter);
		
		//注册另一个 本地  广播接收器
		LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
		intentFilter.addAction("com.example.imagebrowser.MainActivity.MY_LOCAL_ACTION");
		MyLocalBroadcastReceiver myLocalBroadcastReceiver = new MyLocalBroadcastReceiver();
		localBroadcastManager.registerReceiver(myLocalBroadcastReceiver, intentFilter);

		//显示更多按钮的点击监听器
		moreButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MoreActivity.class);
				startActivity(intent);
			}
		});
		
    }	//oncreate()结尾地方

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.Item_hiddenIntent) {
        		Intent intent = new Intent("com.example.imagebroser.my_action");
        		intent.addCategory("com.example.imagebroser.my_category");
        		startActivity(intent);
            return true;
        }else if (id == R.id.Item_openBroser) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http://www.baidu.com"));
			startActivity(intent);
			return true;
		}else if (id == R.id.Item_openLifeCyleNormal) {
			Intent intent = new Intent(MainActivity.this,LifeCyleNormalActivity.class	);
			startActivity(intent);
			return true;
		}else if(id == R.id.Item_openLifeCyleDialog){
			Intent intent = new Intent(MainActivity.this,LifeCyleDialogActivity.class	);
			startActivity(intent);
			return true;
		}else if(id == R.id.Item_testEditText){
			Intent intent = new Intent(MainActivity.this, TestEditTextActivity.class);
			startActivity(intent);
			return true;
		}else if(id == R.id.Item_AlertDialog){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
			alertDialogBuilder.setTitle("alertDialog by lee");
			alertDialogBuilder.setMessage("something important");
			alertDialogBuilder.setCancelable(true);
			//setPositiveButton上的文字以及点击事件的处理发方式
			alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			//setNegativeButton上的文字以及点击事件的处理发方式
			alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			alertDialogBuilder.show();
			return true;
		}else if (id == R.id.Item_ProgressDialog) {
			//弹出一个progressDialog
			ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setTitle("ProgressDialog by lee");
			progressDialog.setMessage("something important");
			progressDialog.setCancelable(true);
			progressDialog.show();
			return true;
		}else if (id == R.id.Item_ListView) {
			Intent intent = new Intent(MainActivity.this, com.example.listview.FruitActivity.class);
			startActivity(intent);
			return true;
		}else if (id == R.id.Item_MsgListView) {
			Intent intent = new Intent(MainActivity.this, MsgActivity.class);
			startActivity(intent);
		}else if (id == R.id.item_sendBoadcast) {
			//发送一个标准广播
			Intent intent = new Intent("com.example.imagebrowser.MainActivity.MY_ACTION");
			sendBroadcast(intent);
		}else if (id == R.id.item_send_orderd_boadcast) {
			//发送一个有序广播
			Intent intent = new Intent("com.example.imagebrowser.MainActivity.MY_ORDERD_ACTION");
			sendOrderedBroadcast(intent, null);
		}else if (id == R.id.item_sendLocalBoradcast) {
			//发送一个本地广播
			LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
			Intent intent = new Intent("com.example.imagebrowser.MainActivity.MY_LOCAL_ACTION");
			localBroadcastManager.sendBroadcast(intent);
		}else if (id == R.id.item_login_activity) {
			Intent intent = new Intent(this, com.example.loign.LoginActivity.class);
			startActivity(intent);
		}else if (id == R.id.item_sqlite_main) {
			Intent intent = new Intent(this,com.example.SQLite.ActivitySQLiteMain	.class);
			startActivity(intent);
		}else if (id == R.id.item_contentProvider_readContacts) {
			Intent intent = new Intent(this, com.exmaple.contentprovider.readContactsActivity.class);
			startActivity(intent);
		}else if (id == R.id.item_more_activity) {
			Intent intent = new Intent(this, MoreActivity.class);
			startActivity(intent);
		}
        return true;
    }
}
