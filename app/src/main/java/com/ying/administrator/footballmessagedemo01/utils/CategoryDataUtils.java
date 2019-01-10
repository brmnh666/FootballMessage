package com.ying.administrator.footballmessagedemo01.utils;

import com.ying.administrator.footballmessagedemo01.entity.CategoriesBean;

import java.util.ArrayList;
import java.util.List;
/*
* 加载tab里面的数据
* */
public class CategoryDataUtils {
    public static List<CategoriesBean>  getCategoryBeans(){
        List<CategoriesBean>  beans=new ArrayList<>();
        beans.add(new CategoriesBean("头条","http://www.dongqiudi.com/?tab/u003d1","1"));
        beans.add(new CategoriesBean("中超","http://www.dongqiudi.com/?tab=56","56"));
        beans.add(new CategoriesBean("闲情","http://www.dongqiudi.com/?tab=37","37"));
        beans.add(new CategoriesBean("专题","http://www.dongqiudi.com/?tab=99","99"));
        beans.add(new CategoriesBean("英超","http://www.dongqiudi.com/?tab=3","3"));
        beans.add(new CategoriesBean("西甲","http://www.dongqiudi.com/?tab=5","5"));
        beans.add(new CategoriesBean("德甲","http://www.dongqiudi.com/?tab=6","6"));
        beans.add(new CategoriesBean("意甲","http://www.dongqiudi.com/?tab=4","4"));

        return beans;
    }

}
