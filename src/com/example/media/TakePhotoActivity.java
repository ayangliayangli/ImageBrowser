package com.example.media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;
import com.example.imagebrowser.R.string;

public class TakePhotoActivity extends BaseActivity {

	Button takePhotoButton;
	ImageView showPicImageView;
	Uri imageUri;
	
	public static final int TAKE_PHOTO = 0;
	public static final int CROP_PHOTO = 1;
	public static String imageDirectionPath;
	public static String imagePathsString;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_take_photo);
		
		takePhotoButton = (Button) findViewById(R.id.take_photo_button);
		showPicImageView = (ImageView) findViewById(R.id.show_picture_textview);
		
		//判断是否存在目录 imageDirection ，如果不存在，就新建
		imageDirectionPath = Environment.getExternalStorageDirectory().getPath() + "/00com.example.image/";
		imagePathsString = imageDirectionPath + "/output_image.jpg";
		File imageDirectionFile = new File(imageDirectionPath);
		File imageFile = new File(imagePathsString);
		if (!imageDirectionFile.exists()) {
			imageDirectionFile.mkdir();
		}
		if (imageFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePathsString);;
			showPicImageView.setImageBitmap(bitmap);
		}
		
		
		
		
		takePhotoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//创建一个File 用于存照片
				File outputimageFile = new File(imageDirectionPath, "output_image.jpg");
				//打印文件的路径
				String pathString = outputimageFile.getPath();
				Log.d("media","Environment.getExternalStorageDirectory():" + Environment.getExternalStorageDirectory());
				Log.d("media", "outputimageFile.getPath():---" + pathString);
				if (outputimageFile.exists()) {
					outputimageFile.delete();
				}else {
					try {
						outputimageFile.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//启动相机程序，使用隐式的intent
				imageUri = Uri.fromFile(outputimageFile);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);
				
			}
		});
		
		
	}//onCreate() 结束
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case TAKE_PHOTO:
			Log.d("media", "拍照完成");
			
			try {
				InputStream is = getContentResolver().openInputStream(imageUri);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				showPicImageView.setImageBitmap(bitmap);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case CROP_PHOTO:
			
			break;

		default:
			break;
		}
		
	}
	
	
}
