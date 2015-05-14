package com.example.testmap;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class mapshow extends Activity{

	MapView mMapView = null;  
	private BaiduMap mBaiduMap;
	
    protected void onCreate(Bundle savedInstanceState) {
   	 super.onCreate(savedInstanceState);   
   	 requestWindowFeature(Window.FEATURE_NO_TITLE);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.activity_main);  
        //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);  
        mBaiduMap = mMapView.getMap();
    }  
    @Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
	
}
