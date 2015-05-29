package com.example.testmap;

import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup
// implements OnGetGeoCoderResultListener
{
	private BroadcastReceiver broadcastReceiver;
	public static String LOCATION_BCR = "location_bcr";

	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	GeoCoder mSearch = null;
	EditText editCity = null;
	EditText editAdd = null;
	private LinearLayout one, two, three, four;
	private LinearLayout bodyView;
	private int flag = 0;
	String city = null;
	String add = null;
	Double lat = null;
	Double lon = null;
	LatLng p = null;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.main);

		registerBroadCastReceiver();

		Toast.makeText(MainActivity.this, "欢迎使用地图", Toast.LENGTH_LONG).show();
		initMainView();
		// InitLocation();
		MyApplication.getInstance().requestLocationInfo();
		// System.out.println("haha" + lat);

		p = new LatLng(38.92, 121.62);
		mMapView = new MapView(this,
				new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(
						p).build()));
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);

		bodyView.addView(mMapView);

		// else {
		// mMapView = new MapView(this);
		// // mBaiduMap = mMapView.getMap();
		// bodyView.addView(mMapView);
		// }
		// 初始化搜索模块，注册事件监听
		// mSearch = GeoCoder.newInstance();
		// mSearch.setOnGetGeoCodeResultListener(this);

		one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				flag = 0;
				showView(flag);
			}
		});
		two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				flag = 1;
				showView(flag);
			}
		});
		three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				flag = 2;
				showView(flag);
			}
		});
		four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				flag = 3;
				showView(flag);
			}
		});

	}

	public void initMainView() {

		bodyView = (LinearLayout) findViewById(R.id.body);
		one = (LinearLayout) findViewById(R.id.one);
		two = (LinearLayout) findViewById(R.id.two);
		three = (LinearLayout) findViewById(R.id.three);
		four = (LinearLayout) findViewById(R.id.four);

	}

	public void showView(int flag) {
		switch (flag) {
		case 0:
			bodyView.removeAllViews();
			mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(p).build()));
			mBaiduMap = mMapView.getMap();
			mBaiduMap.setMyLocationEnabled(true);
			bodyView.addView(mMapView);
			MyApplication.getInstance().requestLocationInfo();

			// // final EditText editCity = new EditText(this);
			// // editCity.setHint("请输入城市");
			// // final EditText editAdd = new EditText(this);
			// // editAdd.setHint("请输入地址名字");
			// city = null;
			// add = null;
			// LayoutInflater inflater = getLayoutInflater();
			// final View layout = inflater.inflate(R.layout.alartview,
			// (ViewGroup) findViewById(R.id.dialog));
			//
			// AlertDialog.Builder builder = new AlertDialog.Builder(this)
			// .setTitle("请输入地址信息")
			// .setIcon(android.R.drawable.ic_dialog_info).setView(layout);
			// // /会出现空指针！！！！！
			// editCity = (EditText) layout.findViewById(R.id.editCity);
			// editAdd = (EditText) layout.findViewById(R.id.editAdd);
			// builder.setPositiveButton("查询",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // TODO 自动生成的方法存根
			//
			// city = editCity.getText().toString();
			// add = editAdd.getText().toString();
			// // System.out.println( "hahahahaa"+ city +add);
			// // Toast.makeText(MainActivity.this, city,
			// // Toast.LENGTH_LONG).show();
			// mSearch.geocode(new GeoCodeOption().city(city)
			// .address(add));
			//
			// }
			// });
			// builder.setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // TODO 自动生成的方法存根
			// }
			// });
			// builder.create().show();
			break;
		// bodyView.removeAllViews();
		// Intent intent1 = new Intent(MainActivity.this,mapshow.class);
		//
		// bodyView.addView(getLocalActivityManager().startActivity("one",
		// intent1)
		// .getDecorView());

		case 1:
			bodyView.removeAllViews();
			Intent intent1 = new Intent(MainActivity.this,
					PoiSearchActivity.class);
			bodyView.addView(getLocalActivityManager().startActivity("two",
					intent1).getDecorView());

			break;
		case 2:
			bodyView.removeAllViews();
			Intent intent2 = new Intent(MainActivity.this,
					RoutePlanActivity.class);
			bodyView.addView(getLocalActivityManager().startActivity("three",
					intent2).getDecorView());
			break;
		case 3:
			bodyView.removeAllViews();
			Intent intent3 = new Intent(MainActivity.this, HelpActivity.class);
			bodyView.addView(getLocalActivityManager().startActivity("four",
					intent3).getDecorView());
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// @Override
	// public void onGetGeoCodeResult(GeoCodeResult result) {
	// // TODO 自动生成的方法存根
	//
	// if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	// Toast.makeText(MainActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
	// .show();
	// return;
	// }
	// // mBaiduMap.clear();
	// // mBaiduMap.addOverlay(new
	// // MarkerOptions().position(result.getLocation())
	// // .icon(BitmapDescriptorFactory
	// // .fromResource(R.drawable.icon_marka)));
	// // mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
	// // .getLocation()));
	// String strInfo = String.format("纬度：%f 经度：%f",
	// result.getLocation().latitude, result.getLocation().longitude);
	// LatLng p = new LatLng(result.getLocation().latitude,
	// result.getLocation().longitude);
	// // Toast.makeText(MainActivity.this, strInfo, Toast.LENGTH_LONG).show();
	// bodyView.removeAllViews();
	// Intent intent = new Intent(MainActivity.this, mapshow.class);
	// intent.putExtra("city", result.getLocation().latitude);
	// intent.putExtra("add", result.getLocation().longitude);
	// bodyView.addView(getLocalActivityManager().startActivity("one", intent)
	// .getDecorView());
	// // mMapView = new MapView(this,
	// // new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(
	// // p).build()));
	// // mBaiduMap = mMapView.getMap();
	// // bodyView.addView(mMapView);
	//
	// }
	//
	// @Override
	// public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
	// // TODO 自动生成的方法存根
	//
	// }
	private void registerBroadCastReceiver() {
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				lat = intent.getDoubleExtra("latitude", -1);
				lon = intent.getDoubleExtra("longitude", -1);
				String add = intent.getStringExtra("add");
				Toast.makeText(MainActivity.this, add.toString(),
						Toast.LENGTH_LONG).show();
				p = new LatLng(lat, lon);
				// System.out.println(lat + "haha" + lon);
				mBaiduMap.clear();
				mBaiduMap.addOverlay(new MarkerOptions().position(p).icon(
						BitmapDescriptorFactory
								.fromResource(R.drawable.icon_marka)));
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));

			}
		};
		IntentFilter intentToReceiveFilter = new IntentFilter();
		intentToReceiveFilter.addAction(LOCATION_BCR);
		registerReceiver(broadcastReceiver, intentToReceiveFilter);
	}

}
