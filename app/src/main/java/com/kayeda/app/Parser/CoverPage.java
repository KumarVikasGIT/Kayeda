package com.kayeda.app.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class CoverPage {

    public static String getCover(String s){
        Document doc= Jsoup.parse(s);
        Elements img=doc.getElementsByTag("img");
        if (!img.isEmpty()){
            return img.get(0).attr("src");
        }

        return null;
    }
}
