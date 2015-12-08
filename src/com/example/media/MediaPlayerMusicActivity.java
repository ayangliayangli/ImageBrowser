package com.example.media;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class MediaPlayerMusicActivity extends BaseActivity implements OnClickListener {

	MediaPlayer mediaPlayer = new MediaPlayer();
	Button startButton;
	Button pauseButton;
	Button resetButton;
	LinearLayout mediaPlayerMusicLayout;
	TextView infoTextView;
	ImageView showPictrueImageView;
	Bitmap showPictrueBitmap;
	VideoView videoView;
	MediaController mediaController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.media_player_music_activity);
		
		startButton = (Button) findViewById(R.id.start_mediaplayer_button);
		pauseButton = (Button) findViewById(R.id.pause_mediaplayer_button);
		resetButton = (Button) findViewById(R.id.reset_mediaplayer_button);
		infoTextView = (TextView) findViewById(R.id.info_mediaplayer_music_textview);
		mediaPlayerMusicLayout = (LinearLayout) findViewById(R.id.media_player_music_linearlayout);
		showPictrueImageView = (ImageView) findViewById(R.id.show_pictrue_music_mediaplayer_imageview);
		videoView = (VideoView) findViewById(R.id.video_view);
		
		
		startButton.setOnClickListener(this);
		pauseButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);
		//初始化播放器
		initMediaPlayer();
		
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}


	/**
	 * 自己就是一个 OnClicklistener(),所以实现 onClick()方法
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.start_mediaplayer_button:
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
				infoTextView.setText("正在播放 蓝莲花 ，请欣赏...");
				showPictrueImageView.setImageBitmap(showPictrueBitmap);
				
				//播放视频
				videoView.start();
				videoView.requestFocus();
			}
			break;
			
		case R.id.pause_mediaplayer_button:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				infoTextView.setText("暂停了 蓝莲花 ，为什么呢...");
				
				//暂停视频
				videoView.pause();
			}
			break;
		case R.id.reset_mediaplayer_button:
			mediaPlayer.reset();
			initMediaPlayer();
			infoTextView.setText("居然停止了 蓝莲花 ，快点击开始看帅哥了...");
			
			//停止视频
			videoView.resume();
			videoView.pause();
			break;

		default:
			break;
		}
		
	}
	
	
	
	private void initMediaPlayer() {
		// TODO Auto-generated method stub
		String musicDirectionsString = Environment.getExternalStorageDirectory() + "/00com.example.image/";
		File file = new File(musicDirectionsString, "music.mp3");
		if (file.exists()) {
			//播放器的准备工作
			try {
				//初始化音乐 mediaplayer
				mediaPlayer.setDataSource(file.getPath());
				mediaPlayer.prepare();
				//初始化 videoview
				Log.d("media", "videoview开始初始化。。。");
				String videoPathString = musicDirectionsString + "videoview.mp4";
				videoView.setVideoPath(videoPathString);
				videoView.requestFocus();
				//videoview 与 mediacontroller 相互绑定
				mediaController = new MediaController(this);
				videoView.setMediaController(mediaController);
				mediaController.setMediaPlayer(videoView);
				
				
				//把帅哥照片读取进来
				String shuaigePictruePathString = musicDirectionsString + "output_image.jpg";
				showPictrueBitmap = BitmapFactory.decodeFile(shuaigePictruePathString);
				
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				infoTextView.setText("播放失败，请检查原因");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				infoTextView.setText("播放失败，请检查原因");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				infoTextView.setText("播放失败，请检查原因");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				infoTextView.setText("播放失败，请检查原因");
			}
			
		}else {
			infoTextView.setText("文件不存在，请根据文档说明把文件移动到相应的位置");
		}
	}
	
	
}
