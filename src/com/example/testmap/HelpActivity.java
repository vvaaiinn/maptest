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
			"���ʵʱ��λʵ�ֶ�λ���ܣ���λ׼ȷ�ȼ���",
			"����ѡ����ĳ����������Ҳ����ѡ�����������ģ�" + "�ڳ�����������Զ���ȫ�Ĺ��ܣ�ÿ�������ʾ10�������"
					+ "�����һ��鿴��һ����������б���Բ鿴�������������ϸ��Ϣ",
			"���Դӵ�ǰ�ص�ȥ������Ҳ��������λ��������·�ߣ�������ʽ�����֣��ֱ�Ϊ�ݳ������У������������ϸ��Ϣ���Բ鿴������߷����ڵ���ð�ť���Իص���ͼָʾ",
			"Write By Zhangjia  ��1107 201192245 " };

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
				// TODO �Զ����ɵķ������
				tv.setText(help[0]);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				tv.setText(help[1]);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				tv.setText(help[2]);
			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				tv.setText(help[3]);
			}
		});
	}
}
