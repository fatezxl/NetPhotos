package com.example.netphotos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;

public class NewsContentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent=this.getIntent();
		WebView webView;
		String news_url="http://mini.eastday.com/mobile/160910085117718.html";

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
		setContentView(R.layout.activity_news_content);
		webView= (WebView) findViewById(R.id.news);
		webView.loadUrl(news_url);
	}


}
