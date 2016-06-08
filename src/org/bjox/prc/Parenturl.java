package org.bjox.prc;

import java.util.regex.Matcher;

import org.bjox.http.WebHttpGet;

public class Parenturl {
	
	public static String  getUrl(String url) {

		String parenturl="";
		/** 获取源码 */
		WebHttpGet webhttpget = new WebHttpGet();
		String html = webhttpget.getHtml(url);//入口页源码
		//System.out.println(html);
		
		/** 链接正则匹配 */
		Matcher all=Regex.l_rex(Regex.defaults.get("uall"), html);
		while(all.find()){
			
			Matcher href=Regex.l_rex(Regex.defaults.get("uhref"), all.group());
		
		while(href.find()){
			// 将提取的链接放到map
			parenturl=href.group(1);
			//System.out.println(parenturl);
		}
		}
		return parenturl;

	
	}
	
	

	public static void main(String[] args) {
		new Parenturl();
		// TODO Auto-generated method stub
		System.out.println( Parenturl.getUrl("http://weixin.sogou.com/weixin?type=1&query=sichuandishui&ie=utf8&_sug_=n&_sug_type_="));
	}

}
