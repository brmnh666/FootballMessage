package com.ying.administrator.footballmessagedemo01.crawl;

import com.ying.administrator.footballmessagedemo01.entity.HomeNewsBean;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HomeNewsManager {
public HomeNewsManager() {
	// TODO Auto-generated constructor stub
}
public List<HomeNewsBean> getHomeNews(Document document){
	List<HomeNewsBean> homeNewsBeans =new ArrayList<HomeNewsBean>();
	
	Elements lis = document.select("#news_list ol li");
	for (Element element : lis) {
		String href = element.select("a").attr("href");
		String img = element.select("a img").attr("src");
		String title = element.select("h2 a").text();
		String detail = element.select("p").text();
		String time = element.select(".info").select(".time").text();
		HomeNewsBean homeNewsBean =new HomeNewsBean();
		homeNewsBean.setWeb_url(href);
		homeNewsBean.setThumb(img);
		homeNewsBean.setTitle(title);
		homeNewsBean.setDescription(detail);
		homeNewsBean.setDisplay_time(time);
		homeNewsBeans.add(homeNewsBean);

	}
	
	return homeNewsBeans;
}

}
