package com.example.testmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

public class HelpActivity extends Activity {

	private Button btn1, btn2, btn3, btn4;
	private TextView tv;
	public String help[] = {
			"点击实时定位实现定位功能，定位准确度极高",
			"可以选择在某城市搜索，也可以选择搜索附近的，" + "在场所输入会有自动补全的功能，每组最多显示10条结果，"
					+ "点击下一组查看下一组结果，点击列表可以查看搜索到结果的详细信息",
			"可以从当前地点去搜索，也可以输入位置来搜索路线，搜索方式有三种，分别为驾车，步行，公交，点击详细信息可以查看具体的走法，在点击该按钮可以回到地图指示",
			"Write By Zhangjia  软1107 201192245 " };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.help);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		tv = (TextView) findViewById(R.id.tv);

		tv.setText(help[3]);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				tv.setText(help[0]);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				tv.setText(help[1]);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				tv.setText(help[2]);
			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				tv.setText(help[3]);
			}
		});
	}
}
