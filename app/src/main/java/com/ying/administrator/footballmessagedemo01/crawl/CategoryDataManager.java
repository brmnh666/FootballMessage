package com.ying.administrator.footballmessagedemo01.crawl;


import com.ying.administrator.footballmessagedemo01.entity.CategoriesBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class CategoryDataManager {
public CategoryDataManager(){

	
}
public List<CategoriesBean> getCategoriesBeans(Document document){
	List<CategoriesBean> categoriesBeans=new ArrayList<CategoriesBean>();
	
	Elements elements=document.select("#tab a");
	for (Element element : elements) {
		String title=element.text();
		String href=element.attr("abs:href");

		String rel = element.attr("rel");
		CategoriesBean bean=new CategoriesBean();
		bean.setTitle(title);
		bean.setHref(href);
		bean.setRel(rel);

		categoriesBeans.add(bean);
	}
	return categoriesBeans;
}

}
