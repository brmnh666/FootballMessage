package com.ying.administrator.footballmessagedemo01.crawl;

import com.ying.administrator.footballmessagedemo01.entity.HeadBean;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author Administrator
 *轮播栏信息
 */
public class HeadManager {
public HeadManager() {
	// TODO Auto-generated constructor stub
}
public List<HeadBean> getHeadBeans(Document document){
	List<HeadBean> headBeans =new ArrayList<HeadBean>();
	 Elements lis = document.select("#show li");
	 
	for (Element element : lis) {
		 String title = element.select("h3").text();
		 String href =element.select("a").attr("href");
		 String img =element.select("img").attr("src");
		 
		 HeadBean bean =new HeadBean();
		 bean.setTitle(title);
		 bean.setImg(img);
		 bean.setHref(href);
		 headBeans.add(bean);
	}
	return headBeans;
}



}
