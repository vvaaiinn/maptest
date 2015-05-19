package com.example.testmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import 	android.app.ActivityGroup;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.testmap.R.id;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup implements
OnGetGeoCoderResultListener{

	MapView mMapView = null;  
	BaiduMap mBaiduMap = null;
	GeoCoder mSearch = null; 
	EditText editCity = null;
	EditText editAdd = null;
	private LinearLayout one, two, three, four,five;
	private LinearLayout bodyView;
	private int flag = 0;
	String city = null;
	String add  = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);   
    	 requestWindowFeature(Window.FEATURE_NO_TITLE);
         //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
         //ע��÷���Ҫ��setContentView����֮ǰʵ��  
         SDKInitializer.initialize(getApplicationContext());  
         setContentView(R.layout.main); 
         Toast.makeText(MainActivity.this, "��ӭʹ�õ�ͼ"+VersionInfo.getApiVersion(), Toast.LENGTH_LONG).show();
         initMainView();         
         LatLng p = new LatLng(38.92,121.62);        
         mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(p).build()));
         mBaiduMap = mMapView.getMap();
         bodyView.addView(mMapView);
         
      // ��ʼ������ģ�飬ע���¼�����
 		mSearch = GeoCoder.newInstance();
 		mSearch.setOnGetGeoCodeResultListener(this);
         
         one.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 0;
				showView(flag);				
			}
		 });
         two.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 1;
				showView(flag);
			}
		});
         three.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 2 ;
				showView(flag);
			}
		});
         four.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 3;
				showView(flag);
			}
		});
         five.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 4;
				showView(flag);
			}
		});
     }     
    public void initMainView() {
		
		bodyView=(LinearLayout) findViewById(R.id.body);
		one=(LinearLayout) findViewById(R.id.one);
		two=(LinearLayout) findViewById(R.id.two);
		three=(LinearLayout) findViewById(R.id.three);
		four=(LinearLayout) findViewById(R.id.four);
		five=(LinearLayout)findViewById(R.id.five);
	}

	public void showView(int flag)
    {
    	switch(flag)
    	{
    	case 0:
//    		final EditText editCity  = new EditText(this);
//    		editCity.setHint("���������");
//    		final EditText editAdd = new EditText(this);
//    		editAdd.setHint("�������ַ����");
    	    LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.alartview,
                     (ViewGroup) findViewById(R.id.dialog));
            
    		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("�������ַ��Ϣ").setIcon(
    			     android.R.drawable.ic_dialog_info).setView(
    			    		layout);
    		///����ֿ�ָ�룡��������
    		editCity = (EditText)layout.findViewById(R.id.editCity);
            editAdd  = (EditText)layout.findViewById(R.id.editAdd);
    		builder.setPositiveButton("��ѯ", new DialogInterface.OnClickListener() {												
    				 @Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO �Զ����ɵķ������
    					 	
							city = editCity.getText().toString();
							add = editAdd.getText().toString();
//							System.out.println( "hahahahaa"+ city +add);
							//Toast.makeText(MainActivity.this, city, Toast.LENGTH_LONG).show();
							mSearch.geocode(new GeoCodeOption().city(
									city).address(
											add));
						}
					});
    		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO �Զ����ɵķ������
					
				}
			});
    		builder.create().show();
    		
    		break;
//    		bodyView.removeAllViews();
//    		Intent intent1 = new Intent(MainActivity.this,mapshow.class);
//    		
//    		bodyView.addView(getLocalActivityManager().startActivity("one",
//					intent1)	
//					.getDecorView());
    		
    	case 1:
			bodyView.removeAllViews();
			Intent intent1 = new Intent(MainActivity.this, PoiSearchDemo.class);
			bodyView.addView(getLocalActivityManager().startActivity("two",
					intent1).getDecorView());

			break;
    	case 2:
    		bodyView.removeAllViews();
    		Intent intent2 = new Intent(MainActivity.this,RoutePlanDemo.class);
    		bodyView.addView(getLocalActivityManager().startActivity("three", intent2).getDecorView());
    		break;
    	case 3:
    		bodyView.removeAllViews();
    		Intent intent3 = new Intent(MainActivity.this,BusLineSearchDemo.class);
    		bodyView.addView(getLocalActivityManager().startActivity("four", intent3).getDecorView());
    		break;
    	case 4:
    		bodyView.removeAllViews();
    		Intent intent4 = new Intent(MainActivity.this,OfflineDemo.class);
    		bodyView.addView(getLocalActivityManager().startActivity("five", intent4).getDecorView());
    		break;
    	default:
    		break;
    	}
    	
    }
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        mMapView.onPause();  
        }
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		// TODO �Զ����ɵķ������
		
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MainActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("γ�ȣ�%f ���ȣ�%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(MainActivity.this, strInfo, Toast.LENGTH_LONG).show();
	}
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		// TODO �Զ����ɵķ������
		
	}  
}
