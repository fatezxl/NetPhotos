package com.example.newsListView;

import java.io.Serializable;

public class NewsInfoBean implements Serializable{
	private String title;
	private String context;
	private String img_url;//ͼƬ����
	private String news_url;//��������
	private transient static NewsInfoBean instance=null;
	
	public NewsInfoBean(String title, String context, String img_url,String news_url) {
		super();
		this.title = title;
		this.context = context;
		this.img_url = img_url;
		this.news_url = news_url;
	}
	/**
	 * java ��transient�ؼ��ֵ���������Ҫʵ��Serilizable�ӿڣ�������Ҫ���л�������ǰ��ӹؼ���transient�����л������ʱ��������ԾͲ������л���ָ����Ŀ�ĵ��С�
 transientʹ��С��
1��һ��������transient���Σ������������Ƕ���־û���һ���֣��ñ������������л����޷���÷��ʡ�
2��transient�ؼ���ֻ�����α��������������η������ࡣע�⣬���ر����ǲ��ܱ�transient�ؼ������εġ�����������û��Զ�����������������Ҫʵ��Serializable�ӿڡ�
3����transient�ؼ������εı��������ܱ����л���һ����̬���������Ƿ�transient���Σ������ܱ����л���
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	public static NewsInfoBean getInstance() {
		return instance;
	}
	public static void setInstance(NewsInfoBean instance) {
		NewsInfoBean.instance = instance;
	}
	
	
}
