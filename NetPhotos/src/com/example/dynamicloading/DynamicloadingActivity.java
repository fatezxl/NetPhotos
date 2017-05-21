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
	private String[] mStrings = {"1", "2", "3", "4", "5","6", "7", "8", "9" };//listview����
	private LinkedList<String> mListItems;//��ʾ���б��Ӧԭ�ַ��� 
	private PullToRefreshListView mPullRefreshListView;//PullToRefreshListViewʵ��
	private ArrayAdapter<String> mAdapter;//ListView�������� 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamicloading);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);//����ģʽ��both��������������������
		//������������ˢ�¼���
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                  String label = DateUtils . formatDateTime (getApplicationContext (),System.currentTimeMillis(),
                          DateUtils . FORMAT_SHOW_TIME| DateUtils . FORMAT_SHOW_DATE| DateUtils . FORMAT_ABBREV_ALL ) ;

               // ��ʾ�����µ�ʱ�� 
                  refreshView . getLoadingLayoutProxy ( ). setLastUpdatedLabel ( label ) ;
                  i++;
                  new DownGetDataTask(). execute ( ) ; 
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				 String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),  
	                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);  
	                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);  // ��ʾ�����µ�ʱ�� 
	                j++;
	                new UpGetDataTask().execute();// ����ˢ�µĲ���
			}
		});

		//�����б����� 
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);

		//�����ְ󶨷���
		//����һ��  
		mPullRefreshListView.setAdapter(mAdapter);
		//������:
		/*ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mAdapter);*/
	}

	private class DownGetDataTask extends AsyncTask<Void, Void, String> {//����ˢ��
		@Override
		protected String doInBackground(Void... params) {//��̨�¼�������
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String str="down fresh + "+i;
			return str;
		}

		//�����Ƕ�ˢ�µ���Ӧ����������addFirst������addLast()�������¼ӵ����ݼӵ�LISTView��
		//����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		@Override
		protected void onPostExecute(String result) {
			
			mListItems.addFirst(result);//��ͷ��������������
			//֪ͨ�������ݼ��Ѿ��ı䣬�������֪ͨ����ô������ˢ��mListItems�ļ���
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	private class UpGetDataTask extends AsyncTask<Void, Void, String> {//����ˢ��
		@Override
		protected String doInBackground(Void... params) {//��̨�¼�������
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
