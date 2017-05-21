package com.example.newsListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.netphotos.NewsContentActivity;
import com.example.netphotos.R;
import com.example.netphotos.R.layout;
import com.example.netphotos.R.menu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class NewsListViewActivity extends Activity {

	ImageView iamge = null;
	TextView title = null;
	TextView text = null;
	LinearLayout layout_bt = null;

	private String img_url1 = "http://01.imgmini.eastday.com/mobile/20160910/20160910085117_8f3fc6065965ac9878052b4716e40840_1_mwpm_03200403.jpeg";
	private String img_url2 = "http://05.imgmini.eastday.com/mobile/20160910/20160910084902_f2b436c94d385e586b288e33401594d9_1_mwpm_03200403.jpeg";
	private String img_url3 = "http://01.imgmini.eastday.com/mobile/20160910/20160910084901_5f774947666ec1db541402581bfe1db8_1_mwpm_03200403.jpeg";
	private String img_url4 = "http://01.imgmini.eastday.com/mobile/20160910/20160910084859_4bab00e4dd77ae7b7e41b8d3be6b0bdb_1_mwpm_03200403.jpeg";
	private String img_url5 = "http://09.imgmini.eastday.com/mobile/20160910/20160910084858_b29cbe1c8da445d0c86e8cb7c909e258_1_mwpm_03200403.jpeg";

	private String news_url1 = "http://mini.eastday.com/mobile/160910085117718.html";
	private String news_url2 = "http://mini.eastday.com/mobile/160910084902236.html";
	private String news_url3 = "http://mini.eastday.com/mobile/160910084901998.html";
	private String news_url4 = "http://mini.eastday.com/mobile/160910084859649.html";
	private String news_url5 = "http://mini.eastday.com/mobile/160910084858697.html";

	private String[] mListTitle = { "1","2", "3","4", "5" };
	private String[] mListStr = { "2016-09-10 08:45", "2016-09-10 08:45",
			"2016-09-10 08:45", "2016-09-10 08:45", "2016-09-10 08:45" };
	private String[] mListImg_url = { img_url1, img_url2, img_url3, img_url4,img_url5 };
	private String[] mListNews_url = { news_url1, news_url2, news_url3,news_url4, news_url5 };

	private NewsInfoBean news1 = new NewsInfoBean(mListTitle[0], mListStr[0],mListImg_url[0], mListNews_url[0]);
	private NewsInfoBean news2 = new NewsInfoBean(mListTitle[1], mListStr[1],mListImg_url[1], mListNews_url[1]);
	private NewsInfoBean news3 = new NewsInfoBean(mListTitle[2], mListStr[2],mListImg_url[2], mListNews_url[2]);
	private NewsInfoBean news4 = new NewsInfoBean(mListTitle[3], mListStr[3],mListImg_url[3], mListNews_url[3]);
	private NewsInfoBean news5 = new NewsInfoBean(mListTitle[4], mListStr[4],mListImg_url[4], mListNews_url[4]);

	private NewsInfoBean[] news = { news1, news2, news3, news4, news5 };

	private PullToRefreshListView news_listView;// listView�ؼ�
	private ArrayList<NewsInfoBean> entry_list;
	private MyListAdapter myAdapter = null;// ������
	protected Bitmap bitmap;//����һ��ȫ�ֱ�������������·ͼƬ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		entry_list = new ArrayList<NewsInfoBean>();
		for (int i = 0; i < 5; i++)
			entry_list.add(news[i]);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ����û�б�����
		myAdapter = new MyListAdapter(NewsListViewActivity.this, entry_list,
				R.layout.more_img_text_item);
		// ����activity����
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_list_view);

		news_listView = (PullToRefreshListView) findViewById(R.id.news_list);// �ܲ���
		news_listView.setMode(Mode.BOTH);// ����ģʽ��both��������������������
		// ������������ˢ�¼���
		news_listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);// ��ʾ�����µ�ʱ��
				new DownGetDataTask().execute();
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label); // ��ʾ�����µ�ʱ��
				new UpGetDataTask().execute();// ����ˢ�µĲ���
			}
		});
		news_listView.setAdapter(myAdapter);
	}

	private class DownGetDataTask extends AsyncTask<Void, Void, NewsInfoBean> {// ����ˢ��
		@Override
		protected NewsInfoBean doInBackground(Void... params) {// ��̨�¼�������
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			NewsInfoBean info = news1;
			return info;
		}
		// �����Ƕ�ˢ�µ���Ӧ����������addFirst������addLast()�������¼ӵ����ݼӵ�LISTView��
		// ����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		
		protected void onPostExecute(NewsInfoBean result) {
			entry_list.add(0, result);// ��ͷ��������������
			
			Log.i("ˢ�¼������Ԫ��", entry_list.get(0).getContext());
			for (int i = 0; i < entry_list.size(); i++)
				Log.i("��" + i + "��Ԫ��", entry_list.get(i).getTitle());
			
			// ֪ͨ�������ݼ��Ѿ��ı䣬�������֪ͨ����ô������ˢ��mListItems�ļ���
			
			myAdapter.notifyDataSetChanged();
			news_listView.onRefreshComplete();
			super.onPostExecute(result);
			
		}
	}

	private class UpGetDataTask extends AsyncTask<Void, Void, NewsInfoBean> {// ����ˢ��
		@Override
		protected NewsInfoBean doInBackground(Void... params) {// ��̨�¼�������
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			NewsInfoBean str = entry_list.get(entry_list.size()-1);
			Log.i("up str", str.getTitle());
			return str;
		}

		@Override
		protected void onPostExecute(NewsInfoBean result) {
			entry_list.add(result);
			// �����Ƿ��Ѿ���ӳɹ�
			myAdapter.notifyDataSetChanged();
			news_listView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	public class MyListAdapter extends ArrayAdapter<Object> {
		 
		
		int mTextViewResourceID = 0;
		private Context mContext;
		private ArrayList<NewsInfoBean> mlist;

		public MyListAdapter(Context context, ArrayList<NewsInfoBean> list,
				int textViewResourceId) {
			super(context, textViewResourceId);
			mTextViewResourceID = textViewResourceId;
			mContext = context;
			mlist = list;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			
			 if(convertView == null) {
				// ӳ���Ӧ�Ŀؼ�
				convertView = LayoutInflater.from(mContext).inflate(
						mTextViewResourceID, null);
				iamge = (ImageView) convertView.findViewById(R.id.array_image);
				title = (TextView) convertView.findViewById(R.id.array_title);
				text = (TextView) convertView.findViewById(R.id.array_text);
				layout_bt = (LinearLayout) convertView
						.findViewById(R.id.layout_bt);// ����
			}
			try {
				UIupdate(mlist, position);
			} catch (IOException e) {
				
				e.printStackTrace();
			}// ����UI
			return convertView;
		}

		public int getCount() {
			return entry_list.size();
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		
	}
	// ����UI
	public void UIupdate(final ArrayList<NewsInfoBean> tempList,
			final int position) throws IOException {
		
		final Handler handler = new Handler();  
		  
	    final Runnable myRunnable= new Runnable() {    
	        public void run() {   
	        	iamge.setImageBitmap(bitmap);
	        }  
	    };

		
		// ����listView����Ĳ�������
		layout_bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("news_url", tempList.get(position)
						.getNews_url());
				intent.setClass(NewsListViewActivity.this,
						NewsActivity.class);// ��ת����ҳ
				startActivity(intent);
			}
		});
		Log.i("position", position + "");
		title.setText(tempList.get(position).getTitle());
		Log.i("title", tempList.get(position).getTitle());
		text.setText(tempList.get(position).getContext());

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					bitmap=getBitmap(tempList.get(position).getImg_url());
					handler.post(myRunnable);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}).start();
		
	}
	// ���������ַӳ���ͼƬ
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
