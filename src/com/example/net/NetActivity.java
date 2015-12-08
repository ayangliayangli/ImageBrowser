package com.example.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class NetActivity extends BaseActivity {
	
	HttpResponse httpResponse = null;
	String responString = "";
	private static  String HTTP_GET_URL_SELF = "http://192.168.0.102/app.xml";
	private static final String HTTP_GET_URL_BAIDU = "http://www.baidu.com";
	private static  String HTTP_GET_URL_SELF_JASON = "http://192.168.0.102/app.json";
	private static final int IS_RESULT_XML = 0;
	private static final int IS_RESULT_JSON = 1;
	Button httpClientButton;
	Button httpClientBaiduButton;
	Button httpUrlConnectionButton;
	Button httpClientJasonButton;
	Button hostIPSubmitButton;
	EditText hostIPEditText;
	TextView netInfoTextView;
	TextView netInfoSmalTextView;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IS_RESULT_XML:
				responString = (String)msg.obj;
				//netInfoTextView.setText(responString);
				netInfoTextView.setText("源代码如下：\n" + responString);
				Log.d("mynet", "handler------------" + responString);
				
				//解析出来的数据，显示
				List<APP> currentList = parseXMLWithPull(responString);
				String displayString = "XML数据，下面是解析的结果：\n";
				if (currentList.size() != 0) {
					for (int i = 0; i <currentList.size() ; i++) {
						APP currentApp = currentList.get(i);
						displayString += "id:" + currentApp.getId()  + "--"
								+ "name:" + currentApp.getName() + "--"
								+ "version:" + currentApp.getVersion() 
								+ "\n";
					}
				}else {
					displayString += "解析失败，请重新获取网页";
				}
				netInfoSmalTextView.setText(displayString);
				break;
				
			case IS_RESULT_JSON:
				netInfoSmalTextView.setText("JSON数据，解析结果如下");
				netInfoTextView.setText((String)msg.obj);
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_net);
		httpClientButton = (Button) findViewById(R.id.httpclient_button);
		httpClientBaiduButton = (Button) findViewById(R.id.httpclient_button_baidu);
		httpUrlConnectionButton = (Button) findViewById(R.id.http_url_connection_button);
		httpClientJasonButton = (Button) findViewById(R.id.http_client_json_button);
		netInfoTextView = (TextView) findViewById(R.id.net_info_textview);
		netInfoSmalTextView = (TextView) findViewById(R.id.net_info_small_textview);
		hostIPEditText = (EditText) findViewById(R.id.host_ip_edittext);
		hostIPSubmitButton = (Button) findViewById(R.id.host_ip_submit_button);
		
		
		hostIPSubmitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String hostIP = hostIPEditText.getText().toString();
				HTTP_GET_URL_SELF = "http://" + hostIP 
						+ "/app.xml";
				HTTP_GET_URL_SELF_JASON = "http://" + hostIP 
						+ "/app.json";
				//在APP界面上反馈修改成功
				hostIPEditText.setText("");
				netInfoSmalTextView.setText("地址修改成功，我们将向下列地址发出请求");
				netInfoTextView.setText(HTTP_GET_URL_SELF + "\n"
						+ HTTP_GET_URL_SELF_JASON);
				
			}
		});
		
		httpClientButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				httpResponse = null;
				// TODO Auto-generated method stub
				//通过requestDataByHttpClient( ) 方法获取数据
				Log.d("mynet", "开始执行：requestDataByHttpClient");
				requestDataByHttpClient(HTTP_GET_URL_SELF);
				//把数据换成字符串 和显示在handler中进行
				
			}
		});
		
		httpClientBaiduButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//通过requestDataByHttpClient( ) 方法获取数据
				Log.d("mynet", "开始执行：requestDataByHttpClient");
				requestDataByHttpClient(HTTP_GET_URL_BAIDU);
				//把数据换成字符串 和显示在handler中进行
			}
		});
		
		
		httpUrlConnectionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestDataByHttpUrlConnection(HTTP_GET_URL_SELF);
			}
		});
		
		
		/**
		 * 改按钮 使用HttpClient得到数据json，然后使用JSONArray把数据解析出来
		 * 未使用类的方法，全部自己实现
		 * 新的进程中实现
		 * 
		 */
		httpClientJasonButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						// TODO Auto-generated method stub
						String responsString = ""; //服务器返回的数据，转换成字符串
						StringBuilder parsedBuilder = new StringBuilder(); //解析后的数据
						int id;
						String name;
						String version;
						
						HttpClient httpClient = new DefaultHttpClient();
						HttpGet httpGet = new HttpGet(HTTP_GET_URL_SELF_JASON);
						try {
							//打印日志
							Log.d("mynet", "开始向服务器请求数据");
							HttpResponse httpResponse = httpClient.execute(httpGet);
							if (httpResponse.getStatusLine().getStatusCode() == 200) {
								responsString = EntityUtils.toString(httpResponse.getEntity());
							}else {
								responsString = "请求失败，请检查网络";
							}
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							responsString = "请求失败，请检查网络";
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							responsString = "请求失败，请检查网络";
							e.printStackTrace();
						}
						//打印日志 服务器上请求到的源代码
						Log.d("mynet", responsString);
						parsedBuilder.append("服务器上的源码：\n" + responsString + "\n" );
						parsedBuilder.append("解析后的数据：" + "\n");
					
						
						//得到responsString，开始解析 JASON数据
						try {
							//打印日志
							Log.d("mynet", "开始解析数据");
							JSONArray jsonArray = new JSONArray(responsString);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								id = jsonObject.getInt("id");
								name = jsonObject.getString("name");
								version = jsonObject.getString("version");
								parsedBuilder.append("id:" + id + "--" + "name:" + name+ "--"  + "version:" + version + "\n");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							parsedBuilder.append("请求失败，请检车网络");
							e.printStackTrace();
						}
						
						//放在message
						Log.d("mynet", "开始--往Message中方数据");
						Message message = new Message();
						message.what = IS_RESULT_JSON;
						message.obj = parsedBuilder.toString();
						handler.sendMessage(message);
						Log.d("mynet", "结束--往Message中方数据");
					}
				}).start();
			}//onClick()结束
			
		});
		
		
	}//oncreate()
	
	
	
	/**
	 *  该方法会使用httpClient的方法去服务器上请求数据，整个过程在新的进程中
	 * 执行，结果使用message返回，在handler中处理结果--把数据显示在textview中
	 * @param httpGetUrl 需要请求的数据
	 */
	private void requestDataByHttpClient(final String httpGetUrl) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpResponse httpResponse = null;
				String currentString = "";
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(httpGetUrl);
				try {
					//获取数据
					httpResponse = httpClient.execute(httpGet);
					if (httpResponse == null) {
						currentString = "请求失败，请检查网络";
					}else {
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							//打印日志
							currentString = EntityUtils.toString(httpResponse.getEntity(),"gb_2312");
							Log.d("mynet", "开始输出string 在新进程中");
							Log.d("mynet", currentString);
							//打印日志结束
						}else {
							currentString = "请求失败，请检查网络";
						}
					}
					
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					currentString = "请求失败，请检查网络";
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					currentString = "请求失败，请检查网络";
				}finally{
					
				}
				//把数据放在message中
				Message message = new Message();
				message.what = IS_RESULT_XML;
				message.obj = currentString;
				handler.sendMessage(message);
				Log.d("mynet", "进程返回message完毕");
			}
		}).start(); //注意一定要start 否则新进程不会执行
		
	}
	
	
	/**
	 * 该方法会使用httpUrlConnection的方法去服务器上请求数据，整个过程在新的进程中
	 * 执行，结果使用message返回，在handler中处理结果--把数据显示在textview中
	 * @param urlstring 要请求的url
	 */
	private void requestDataByHttpUrlConnection(final String urlstring) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					URL url = new URL(urlstring);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					
					//接下来把数据从reader中读出来，转换成字符串
					StringBuilder responsBuilder = new StringBuilder();
					String line = "";
					while ((line = reader.readLine()) != null) {
						responsBuilder.append(line);
					}
					//把数据放在message中 并且发送出去
					Message message = new Message();
					message.what = IS_RESULT_XML;
					message.obj = "使用HttpUrlConnection得到的数据-----------\n" + responsBuilder.toString();
					handler.sendMessage(message);
					
					
					
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
				
				
			}
		}).start(); //注意一定要.start()
	}
	
	
	
	
	
	/**
	 * 使用得到的XML源代码进行解析出我们要的数据
	 * @param responsString
	 */
	private List<APP> parseXMLWithPull(String responsString) {
		// TODO Auto-generated method stub
		int eventType;
		String nodeName;
		int id = -1;
		String name = "";
		String version = "";
		List<APP> responsList = new ArrayList<APP>();
		
		if (responsString.indexOf("------") != -1) {
			responsString = responsString.substring(36);
			Log.d("mynet", "截取后的字符串parseXMLWithPull():" + responsString);
		}
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(responsString));
			eventType = xmlPullParser.getEventType();
			
			//开始解析，把数据放在responsList中
			while (eventType != XmlPullParser.END_DOCUMENT) {
				nodeName = xmlPullParser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if ("id".equals(nodeName)) {
						id = Integer.parseInt(xmlPullParser.nextText());
					}else if ("name".equals(nodeName)) {
						name = xmlPullParser.nextText();
					}else if ("version".equals(nodeName)) {
						version = xmlPullParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					if ("app".equals(nodeName)) {
						APP currentApp = new APP(); //存放当前的APP
						currentApp.setId(id);
						currentApp.setName(name);
						currentApp.setVersion(version);
						responsList.add(currentApp);
					}

				default:
					break;
				}
				
				eventType = xmlPullParser.next();
			}//循环找数据完成
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responsList;
	}
	
	
	
	
	
	
//	private String parseHttpResponseToString(HttpResponse httpResponse) {
//		String returnString = "";
//		
//		// TODO Auto-generated method stub
//		if (httpResponse.getStatusLine().getStatusCode() == 200) {
//			HttpEntity httpEntity = httpResponse.getEntity();
//			try {
//				returnString = EntityUtils.toString(httpEntity);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}else{
//			returnString = "请求数据失败";
//		}
//		
//		return returnString;
//	}
	
	

}//netActivity
