package com.example.testmap;

//package com.example.testmap;
//
//import android.app.Application;
//import android.util.Log;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
import android.app.Application;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MyApplication extends Application {
	public LocationClient mLocationClient = null;
	public GeofenceClient mGeofenceClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public static String TAG = "MyApplication";

	private static MyApplication mInstance = null;

	@Override
	public void onCreate() {
		mInstance = this;

		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		mGeofenceClient = new GeofenceClient(this);

		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	/**
	 * ֹͣ��λ
	 */
	public void stopLocationClient() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

	/**
	 * ����λ
	 */
	public void requestLocationInfo() {
		setLocationOption();

		if (mLocationClient != null && !mLocationClient.isStarted()) {
			mLocationClient.start();
		}

		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		}
	}

	/**
	 * ������ز���
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��GPS
		option.setCoorType("bd09ll"); // ������������
		option.setServiceName("com.baidu.location.service_v2.9");// ���ðٶȵ�ͼ��λ����
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * �����������и���λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				sendBroadCast(-1.0, -1.0, "");
				return;
			}

			sendBroadCast(location.getLatitude(), location.getLongitude(),
					location.getAddrStr());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// if (poiLocation == null) {
			// sendBroadCast("��λʧ��!");
			// return;
			// }
			// sendBroadCast(poiLocation.getAddrStr());
		}

	}

	/**
	 * �õ����͹㲥
	 * 
	 * @param address
	 */
	public void sendBroadCast(Double latitude, Double longitude, String add) {
		stopLocationClient();

		Intent intent = new Intent(MainActivity.LOCATION_BCR);
		intent.putExtra("latitude", latitude);
		intent.putExtra("longitude", longitude);
		intent.putExtra("add", add);
		sendBroadcast(intent);
	}
}
