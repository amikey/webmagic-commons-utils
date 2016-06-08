package org.bjox.prc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bjox.http.WebHttpGetTread;

/**
 * 
 * @author Mir
 * 
 */
public class HtmlFormat {

	private static Logger logger = Logger.getLogger(HtmlFormat.class);
	public static Map<String, UrlBean> invalidurlmap = new ConcurrentHashMap<String, UrlBean>();// 存放异常URL(包括超时)

	/**
	 * 获取子URL网页所需内容
	 * 
	 * @param text
	 * 
	 * @return map集合:子url>>>字段内容bean
	 */
	public Map<String, UrlBean> getText(String parenturl) {

		// String parenturl=Parenturl.getUrl(url).replace("&amp;", "&");

		/** 获取列表 */
		HttpLink ss = new HttpLink();
		Map<String, UrlBean> urlmap = ss.getLink(parenturl);
		urlmap=HtmlFormat.webSource(urlmap);
		return urlmap;
	}

	/**
	 * 获取子URL网页所需内容
	 * 
	 * @param text
	 * 
	 * @return map集合:子url>>>字段内容bean
	 */
	public static  Map<String, UrlBean> webSource(Map<String, UrlBean> urlmap) {
		List<String> urlList = new ArrayList<String>();
		for (String str : urlmap.keySet()) {
			String urlkey = (String) str;
			UrlBean urlvalue = (UrlBean) urlmap.get(urlkey);
			// URL标题排重
			int bo = JDomXml.readXML(DigestUtils.md5Hex(urlkey),
					urlvalue.getLinktitle(), 1);// 0：不排重 1：公有排重 2：私有排重
			if (bo == 0) {
				urlmap.remove(urlkey);
				continue;
			}
			// logger.info(urlkey+">>>>"+valueString.getLinktitle());
			urlList.add(urlkey);
		}
		Map<String, String> results = WebHttpGetTread.threadGet(urlList);

		for (String str : results.keySet()) {
			String url = (String) str;
			String result = (String) results.get(url);

			for (String stt : urlmap.keySet()) {
				String urlkey = (String) stt;
				UrlBean urlvalue = (UrlBean) urlmap.get(urlkey);

				JDomXml.updateXML(DigestUtils.md5Hex(url),
						urlvalue.getLinktitle(), urlkey);
				JDomXml.updateXML("public", urlvalue.getLinktitle(), urlkey);
				if (result != null) {
					urlmap.remove(url);// 如果源码为空删除键值并执行下一才循环
					invalidurlmap.put(url, urlvalue);
					continue;
				}
				/** 正则匹配 */
				Regex.z_rex(result, urlvalue);
			}
		}
		return urlmap;
	}

}