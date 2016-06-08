package org.bjox.prc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bjox.http.WebHttpGetTread;

/**
 * 
 * @author Mir
 * 
 */
public class HttpLink {

	Logger logger = Logger.getLogger(HttpLink.class);

	/**
	 * 获得入口页的所有的子 URL
	 * 
	 * @param url入口 URL
	 *            
	 * @return 子 URL 的 url 和 标题
	 */
	
	public Map<String, UrlBean> getLink(String parenturl) {
		
		/** 获取源码 */
		String host=StringUtils.substringBeforeLast(parenturl,"/");
		
		List<String> urllist=new ArrayList<String>();
		urllist.add(parenturl);
		Map<String, String> results = WebHttpGetTread.threadGet(urllist);
		//System.out.println(results.get(parenturl));
		
		/** 链接正则匹配 */
		Map<String, UrlBean> urlmap = new ConcurrentHashMap<String, UrlBean>();
		new Regex();
		Matcher all=Regex.l_rex(Regex.defaults.get("all"), results.get(parenturl));
		while(all.find()){
			
			Matcher m_anchortxt =Regex.l_rex(Regex.defaults.get("anchortxt"), all.group());
			Matcher m_href=Regex.l_rex(Regex.defaults.get("href"), all.group());
		
		while(m_href.find()){
			
			String anchortxt="";
			if(m_anchortxt.find()){
				 anchortxt=m_anchortxt.group(1).replace("&nbsp;", "").replace("&amp;", "").replace("quot;", "");
			}
			String href=m_href.group(1).replace("amp;", "");
			if(href.startsWith("/")){
				href=host+href;
			}
			UrlBean urlBean = new UrlBean();
			urlBean.setLinktitle(anchortxt);
			urlBean.setParenturl(parenturl);
			urlBean.setUrl(href);
			// 将提取的链接放到map
			urlmap.put( href,urlBean);
			//logger.info(href+">>>>"+anchortxt);
		}
		}
		return urlmap;
	}

public static void main(String[] args) {
	Map<String, UrlBean> linksMap = new HttpLink().getLink("http://mp.weixin.qq.com/profile?src=3&timestamp=1464859414&ver=1&signature=6hGMcRpJjQNV4fVMBmJV2BPusRbMTnM8qbxzmJoF9n0zJ5k5jbPSw4AKUnI4r5aZVexz5FxvyBj8yAwoUtXMag==");
}
}
