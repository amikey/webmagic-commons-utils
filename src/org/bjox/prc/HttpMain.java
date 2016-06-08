package org.bjox.prc;

import java.util.Map;

public class HttpMain {

	public static void main(String[] args) {
		String url = "http://mp.weixin.qq.com/profile?src=3&timestamp=1465289511&ver=1&signature=6hGMcRpJjQNV4fVMBmJV2BPusRbMTnM8qbxzmJoF9n0zJ5k5jbPSw4AKUnI4r5aZdjVtlmwVvHtyMhPe6TYL1w==";

		HtmlFormat txt = new HtmlFormat();
		Map<String, UrlBean> mapp = txt.getText(url);
		System.out.println(mapp.size());
		
		/*for (String stt : mapp.keySet()) {
			
			String regkey = (String) stt;
			UrlBean regvalue = mapp.get(regkey);
			//System.out.println(regvalue.getAuthor());
			System.out.println("正文"+regvalue.getContent());
	}*/
}
	}
