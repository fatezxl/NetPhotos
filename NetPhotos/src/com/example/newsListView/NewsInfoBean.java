package com.example.newsListView;

import java.io.Serializable;

public class NewsInfoBean implements Serializable{
	private String title;
	private String context;
	private String img_url;//图片链接
	private String news_url;//新闻链接
	private transient static NewsInfoBean instance=null;
	
	public NewsInfoBean(String title, String context, String img_url,String news_url) {
		super();
		this.title = title;
		this.context = context;
		this.img_url = img_url;
		this.news_url = news_url;
	}
	/**
	 * java 的transient关键字的作用是需要实现Serilizable接口，将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。
 transient使用小结
1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
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
