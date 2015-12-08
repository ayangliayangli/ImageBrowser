package com.example.location;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagebrowser.BaseActivity;
import com.example.imagebrowser.R;

public class ActivityShowMyLocation extends BaseActivity {

	StringBuilder myLatLng = new StringBuilder();
	LocationManager locationManager;
	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			showlocationTextView.setText(showMyLatLng(location));
		}
	};
	TextView showlocationTextView = null;
	String provider;
	
	
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_show_my_location);
			showlocationTextView = (TextView) findViewById(R.id.textview_show_my_location);
			
			Location myLocation = requestMyLocation();
			String myLocationString = showMyLatLng(myLocation);
			showlocationTextView.setText(myLocationString);
			requestMyLocationForLong(3000, 3); //长时间的请求时间
			
		}
		
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			locationManager.removeUpdates(locationListener);
		}
	
		
		
		private Location requestMyLocation() {
			// TODO Auto-generated method stub
			Location myLocation = null;
			provider = "";
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			
			List<String> providerList = locationManager.getProviders(true);
			if (providerList.contains(LocationManager.GPS_PROVIDER)) {
				provider = LocationManager.GPS_PROVIDER;
			}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
				provider = LocationManager.NETWORK_PROVIDER;
			}else {
				Toast.makeText(this, "请打开【网络】或者【GPS】进行定位", Toast.LENGTH_LONG).show();
			}
			
			myLocation = locationManager.getLastKnownLocation(provider);
			
			return myLocation;
		}
		
		
		/**
		 * 长期不间断的请求新的地址
		 * @param interval 请求地址的最短 时间 间隔
		 * @param distance 请求地址的最短 距离 间隔
		 */
		private void requestMyLocationForLong(long interval,int distance) {
			// TODO Auto-generated method stub
			locationManager.requestLocationUpdates(provider, interval	, distance, locationListener);
		}
		
		/**
		 * 
		 * @param myLocation
		 * @return 经纬度的具体值组成的字符串
		 */
		private String showMyLatLng(Location myLocation) {
			// TODO Auto-generated method stub
			if (myLocation != null) {
				myLatLng.append("current provider:" + provider + " :"); //把当前使用的定位provider也返回
				myLatLng.append("latitude:" + myLocation.getLatitude());
				myLatLng.append("-------");
				myLatLng.append("longitude:" + myLocation.getLongitude());
			}else {
				myLatLng.append("没有获取到地址，请检查是打开了定位");
			}
			myLatLng.append("\n");
			return myLatLng.toString();
		}
		
}
