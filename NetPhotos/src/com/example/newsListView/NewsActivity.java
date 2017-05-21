package com.example.newsListView;

import com.example.netphotos.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.webkit.WebView;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent=this.getIntent();
		WebView webView;
		String news_url=intent.getStringExtra("news_url");

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
		setContentView(R.layout.activity_news);
		webView= (WebView) findViewById(R.id.news_content);
		webView.loadUrl(news_url);
	}


}
