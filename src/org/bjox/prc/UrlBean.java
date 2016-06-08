package org.bjox.prc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlBean {

	/**
	 * 
	 */
	private String url = "";//子URL

	private String linktitle = "";//链接标题
	
	private String title="";//正文标题

	private String lastmodify = "";//最后更新时间

	private String parenturl = "";//副URL
	
	private String content = "";//正文
	
	private String author = "";//作者
	
	private String source = "";//作者

	private Map<String, String> elsemap = new ConcurrentHashMap<String, String>();//其他字段属性

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLinktitle() {
		return linktitle;
	}

	public void setLinktitle(String linktitle) {
		this.linktitle = linktitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastmodify() {
		return lastmodify;
	}

	public void setLastmodify(String lastmodify) {
		this.lastmodify = lastmodify;
	}

	public String getParenturl() {
		return parenturl;
	}

	public void setParenturl(String parenturl) {
		this.parenturl = parenturl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Map<String, String> getElsemap() {
		return elsemap;
	}

	public void setElsemap(Map<String, String> elsemap) {
		this.elsemap = elsemap;
	}

	
	
}
