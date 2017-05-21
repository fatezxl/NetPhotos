package com.example.dynamicloading;

import java.util.Arrays;
import java.util.LinkedList;

import com.example.netphotos.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DynamicloadingActivity extends Activity {
	private int i=0;
	private int j=0;
	private String[] mStrings = {"1", "2", "3", "4", "5","6", "7", "8", "9" };//listview内容
	private LinkedList<String> mListItems;//显示的列表对应原字符串 
	private PullToRefreshListView mPullRefreshListView;//PullToRefreshListView实例
	private ArrayAdapter<String> mAdapter;//ListView的适配器 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamicloading);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);//设置模式，both代表上拉和下拉都可以
		//设置下拉滑动刷新监听
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                  String label = DateUtils . formatDateTime (getApplicationContext (),System.currentTimeMillis(),
                          DateUtils . FORMAT_SHOW_TIME| DateUtils . FORMAT_SHOW_DATE| DateUtils . FORMAT_ABBREV_ALL ) ;

               // 显示最后更新的时间 
                  refreshView . getLoadingLayoutProxy ( ). setLastUpdatedLabel ( label ) ;
                  i++;
                  new DownGetDataTask(). execute ( ) ; 
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				 String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),  
	                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);  
	                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);  // 显示最后更新的时间 
	                j++;
	                new UpGetDataTask().execute();// 上拉刷新的操作
			}
		});

		//设置列表内容 
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

		//这两种绑定方法
		//方法一：  
		mPullRefreshListView.setAdapter(mAdapter);
		//方法二:
		/*ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mAdapter);*/
	}

	private class DownGetDataTask extends AsyncTask<Void, Void, String> {//下拉刷新
		@Override
		protected String doInBackground(Void... params) {//后台事件处理部分
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String str="down fresh + "+i;
			return str;
		}

		//这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		//根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(String result) {
			
			mListItems.addFirst(result);//在头部增加新添内容
			//通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	private class UpGetDataTask extends AsyncTask<Void, Void, String> {//上拉刷新
		@Override
		protected String doInBackground(Void... params) {//后台事件处理部分
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String str="up fresh + "+j;
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			
			mListItems.addLast(result);
			mAdapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
