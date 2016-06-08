package org.bjox.prc;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author Mir
 * 
 */
public class Regex {
	/**
	 * 处理正则匹配
	 * 
	 * @param regex
	 *            
	 * @return 截取后的内容和字段名称
	 */
	
	public static final Map<String, String> custom = new LinkedHashMap<String, String>();//自定义
	public static final Map<String, String> defaults = new LinkedHashMap<String, String>();//个性化
	
	public Regex(){
		
			defaults.put( "all","(\\{&quot;title[\\s\\S]*?source_url)");//源代码快
			defaults.put( "href", "content_url&quot;:&quot;\\\\\\\\([\\S\\s]*?)&quot;");//链接
			defaults.put("anchortxt", "\\{&quot;title&quot;:&quot;([\\S\\s]*?)&quot;");//链接标题
			
			defaults.put("lastmodify", "<em id=\"post-date\" class=\"rich_media_meta rich_media_meta_text\">(.*?)</em>");
			defaults.put("z_contentregexp", "<p>([\\s\\S]*)</p>");
			defaults.put("author","<a class=[\\s\\S]*?href=[\\s\\S]*?id=[\\s\\S]*?>([\\s\\S]*?)</a>");
			defaults.put("title", "<title>([\\s\\S]*?)</title>");
			
			defaults.put( "uall","(<div target=\"_blank\"[\\S\\s]*?</h3>)");//源代码快
			defaults.put( "uhref", "href=\"(.*?)\"");
		
			/*custom.put("CONSTRUCTIONSTARTQUARTER ","Construction Start Quarter[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("CONSTRUCTIONTYPE","Construction Type[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("FUNDINGMODE","Funding Mode[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("LASTUPDATE","Last Update:</b>[\\s\\S]*?<td>\\s*(.*?)\\s*</td>");
			custom.put("LOCATIONTYPE","Location Type[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PRIMARYTAXONOMY","Primary Taxonomy[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROFILESTATUS","Profile Status:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTANNOUNCEMENTQUARTER","Project Announcement Quarter[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTCOUNTRY","Project Country:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTENDQUARTER","Project End Quarter:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTSTAGE","Project Stage:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTTYPE","Project Type:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("PROJECTVALUE","Project Value:[\\s\\S]*?<td[\\s\\S]*?>\\s*([\\s\\S]*?)\\s*</td>");
			custom.put("MAP_X", "center: new Microsoft.Maps.Location\\((.*?),");
			custom.put("MAP_Y", "center: new Microsoft.Maps[\\S\\s]*?,(.*?)\\)");
			custom.put("MAP_ZOOM", "zoom:(.*?),");*/
			
	}
	
	
	/**
	 * 正则匹配方法
	 * 
	 * @param 匹配关键字,匹配字符
	 *            
	 * @return 匹配
	 */
	public static Matcher  l_rex(String word,String html){
		
		Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		return m;
	}
	
	/**
	 * 获取默认属性
	 * 
	 * @param 基本字段
	 *            
	 * @return 匹配结果
	 */
	public static void z_rex (String html,UrlBean urlbean ){
		
		Matcher m_contentregexp=Regex.l_rex(Regex.defaults.get("z_contentregexp"), html);
		Matcher m_lastmodify=Regex.l_rex(Regex.defaults.get("lastmodify"), html);
		Matcher m_author=Regex.l_rex(Regex.defaults.get("author"), html);
		Matcher m_title=Regex.l_rex(Regex.defaults.get("title"), html);
		
		String content="";
		String lastmodify="";
		String author="";
		String title="";
		
		if(m_contentregexp.find()){
			
			content=m_contentregexp.group(1);
			content=StringUtils.replacePattern(content, "<script[\\s\\S]*>", "");
			content=StringUtils.replacePattern(content, "<.*?>", "");
			urlbean.setContent(content);
		}
		if(m_lastmodify.find()){
			lastmodify=m_lastmodify.group(1);
			urlbean.setLastmodify(lastmodify);
		}
		if(m_author.find()){
			author=m_author.group(1);
			urlbean.setAuthor(author);
		}
		if(m_title.find()){
			title=m_title.group(1);
			urlbean.setTitle(title);
		}
		
		/**获取自定义属性*/
		Map<String, String> customMap = new ConcurrentHashMap<String, String>();
		if(custom!=null||!custom.isEmpty()){
			
			for(String str : custom.keySet()){
				
				String regkey = (String)str; 
				String regvalue=custom.get(regkey);
				
				Pattern patt = Pattern.compile(regvalue, Pattern.CASE_INSENSITIVE);
				Matcher mat = patt.matcher(html);
				
				
				if(mat.find()) {
					customMap.put(regkey, mat.group(1));
					System.out.println(regkey+">>>>"+mat.group(1));
					urlbean.setElsemap(customMap);
				}
				
				else if (!mat.find()) {
					customMap.put(regkey, "");//如果没有匹配到 设置属性为空字符
					urlbean.setElsemap(customMap);
				}
			}
		}
	}
}
