package com.example.netphotos;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class NetphotosActivity extends Activity {
	private String url = "http://07.imgmini.eastday.com/mobile/20160908/20160908104951_8dd5265e95aaea073fec61a28498b28f_1_mwpm_03200403.jpeg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_netphotos);
		ImageView img = (ImageView) findViewById(R.id.img);
		try {
			img.setImageBitmap(getBitmap(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap getBitmap(String path) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			return bitmap;
		}
		return null;

	}

}
