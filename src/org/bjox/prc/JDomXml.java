package org.bjox.prc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class JDomXml {

	public static int readXML(String filename,String url,int i){
		
		String str="";
		
		if(i==1){
			str="e:\\blf\\public\\okg.xml";//公有
		}else if(i==2){
			str="e:\\blf\\"+filename+"\\okg.xml";//私有
		}else if(i==0){
			return 1;//不排重
		}
		
		SAXBuilder sb = new SAXBuilder();
		Document doc=null;
		File fi=new File(str);
		
		if(fi.exists()){
			try {
				 doc = sb.build(fi);
				Element root = doc.getRootElement();
				List<Element> list = root.getChildren("link");
				
				for(Element el : list){
					if(url.equals(el.getChildText("title"))){
						//System.out.println(el.getChildText("title"));
						return 0;
					}
				}
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	public static void createXML(File filename,String title,String url){
		
		//创建文档
		Document document=new Document();
		//创建根元素
		Element root=new Element("root");
		//把根元素写入Doucment中
		document.addContent(root);
		
		//创建注释
		Comment rComment=new Comment("排重数据");
		root.addContent(rComment);
		
		Element elm=new Element("link");
        //把元素加入到根元素中
		root.addContent(elm);
        //设置data1元素属性
        elm.setAttribute("id", DigestUtils.md5Hex(url));
        
        Attribute data1_gender = new Attribute("gender", "male");
        elm.setAttribute(data1_gender);
        
        Element _title = new Element("title");
        _title.setText(title);
        elm.addContent(_title);
        
        Element _url = new Element("url");
        _url.setText(url);
        elm.addContent(_url);
        
        saveXML(document,filename);
	}
	
	public static void updateXML(String  filename,String title,String url){
		
		SAXBuilder sb = new SAXBuilder();
		Document doc=null;
		File fii=new File("e:\\blf\\"+filename);
		File fi=new File("e:\\blf\\"+filename+"\\okg.xml");
		
		if(!fii.isDirectory()&&!fi.exists()){
				fii.mkdirs();
				try {
					fi.createNewFile();
					createXML(fi, title, url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
			try {
				doc = sb.build(fi);
				Element root = doc.getRootElement();
				Element elm=new Element("link");
				root.addContent(elm);
		        //设置data1元素属性
		        elm.setAttribute("id", DigestUtils.md5Hex(url));
		        
		        Attribute data1_gender = new Attribute("gender", "male");
		        elm.setAttribute(data1_gender);
		        
		        Element _title = new Element("title");
		        _title.setText(title);
		        elm.addContent(_title);
		        
		        Element _url = new Element("url");
		        _url.setText(url);
		        elm.addContent(_url);
				saveXML(doc,fi);
				
			} catch (JDOMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	/**
	 * 修改XML
	 * 
	 * @param Document,文件名
	 *            
	 * @return 无返回值
	 */
	public static void saveXML(Document doc,File filename){

		//设置xml输出格式
        Format format = Format.getPrettyFormat();
        format.setEncoding("utf-8");//设置编码
        format.setIndent("    ");//设置缩进
        
		try {
			//创建xml输出流
			XMLOutputter xmlopt=new XMLOutputter(format);
			// 创建文件输出流
			FileWriter 	write = new FileWriter(filename);
			// 将doc写入到指定的文件中
			xmlopt.output(doc, write);
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将doc写入指定文件
	}
	public static void main(String[] args) {
		
	}
}
