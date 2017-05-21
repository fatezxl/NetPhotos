package com.example.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.dynamicloading.DynamicloadingActivity;
import com.example.netphotos.NetphotosActivity;
import com.example.netphotos.NewsContentActivity;
import com.example.netphotos.R;
import com.example.newsListView.NewsListViewActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.style.DynamicDrawableSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener{
	private Intent intent=new Intent();
	private Button btn_netPhotos;
	private Button btn_Dynamicloading;
	private Button btn_newsListView;
	private Button btn_newsContent;
	private Button btn_cachePhotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();//³õÊ¼»¯¿Ø¼þ
	}
	
	public void initView() {
		
		btn_netPhotos=(Button)findViewById(R.id.btn_netPhotos);
		btn_netPhotos.setOnClickListener(this);
		
		btn_Dynamicloading=(Button)findViewById(R.id.btn_Dynamicloading);
		btn_Dynamicloading.setOnClickListener(this);
		
		btn_newsListView=(Button)findViewById(R.id.btn_newsListView);
		btn_newsListView.setOnClickListener(this);
		
		btn_newsContent=(Button)findViewById(R.id.btn_newsContent);
		btn_newsContent.setOnClickListener(this);
		

	}
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btn_netPhotos:
			intent.setClass(MainActivity.this, NetphotosActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_Dynamicloading:
			intent.setClass(MainActivity.this, DynamicloadingActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_newsListView:
			intent.setClass(MainActivity.this, NewsListViewActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_newsContent:
			intent.setClass(MainActivity.this, NewsContentActivity.class);
			startActivity(intent);
			break;
			
		}
	}
}
