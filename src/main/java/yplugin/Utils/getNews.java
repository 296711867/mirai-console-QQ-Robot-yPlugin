package yplugin.Utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import yplugin.Config.Resources;
import yplugin.Plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class getNews {

    public static StringBuilder getBilibiliInterFaceRank(){

        try {
            String body = HttpRequest
                    .get("https://api.bilibili.com/x/web-interface/ranking/region?rid=1&day=3&original=0")
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            Plugin.INSTANCE.getLogger().info("b站首页排行 获取成功");

            JSONObject jsonObject = (JSONObject) JSON.parse(body);

            JSONArray list = jsonObject.getJSONArray("data");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("b站首页排行榜:\n");

            if(list.size()==0){
                stringBuilder.append("暂无\n");
            }else {
                for(int i=0; i<list.size(); i++){
                    JSONObject object = list.getJSONObject(i);

                    // https://www.bilibili.com/video/BV1Y54y1A7iy
                    String title = object.getString("title");
                    String url = "https://www.bilibili.com/video/" + object.getString("bvid");

                    String firstLine = (i+1) + "、"+ title + ":\n";
                    String secondLine = url + "\n";

                    // 加入输出
                    stringBuilder.append(firstLine).append(secondLine).append("\n");

                    if(i == 4){
                        return stringBuilder;
                    }
                }
            }

            return stringBuilder;
        } catch (HttpException e) {
            Plugin.INSTANCE.getLogger().info(e);
            return null;
        }

    }

    public static StringBuilder getBilibiliCartoonRank(){

        try {
            String body = HttpRequest
                    .get("https://api.bilibili.com/pgc/web/rank/list?season_type=1&day=3")
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            Plugin.INSTANCE.getLogger().info("b站番剧排行 获取成功");

            JSONObject jsonObject = (JSONObject) JSON.parse(body);

            JSONObject result = jsonObject.getJSONObject("result");

            JSONArray list = result.getJSONArray("list");

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("b站番剧排行榜:\n");

            if(list.size()==0){
                stringBuilder.append("暂无\n");
            }else {
                for(int i=0; i<list.size(); i++){
                    JSONObject object = list.getJSONObject(i);

                    String title = object.getString("title");
                    String url = object.getString("url");
                    String rating = object.getString("rating");

                    String firstLine = (i+1) + "、"+ title + "(" + rating + "):\n";
                    String secondLine = url + "\n";

                    // 加入输出
                    stringBuilder.append(firstLine).append(secondLine).append("\n");

                    if(i == 4){
                        return stringBuilder;
                    }
                }
            }

            return stringBuilder;
        } catch (HttpException e) {
            Plugin.INSTANCE.getLogger().info(e);
            return null;
        }

    }

    public static String getTodayHistory(){

        try {
            String body = HttpRequest
                    .get("http://bjb.yunwj.top/php/ls/ls.php")
                    .timeout(20000)//超时，毫秒
                    .execute().body();

            Plugin.INSTANCE.getLogger().info("历史上的今天 获取成功");

            JSONObject jsonObject = (JSONObject) JSON.parse(body);

            String wb = jsonObject.getString("wb");
            String today= DateUtil.today();

            return "历史上的今天(" + today +"): \n"
                    + wb.replace("【换行】", "\n\n");

        } catch (HttpException e) {
            Plugin.INSTANCE.getLogger().info(e);
            return null;
        }

    }


    public static StringBuilder getNewsText() throws IOException {
        Plugin.INSTANCE.getLogger().info("爬取新闻中...");
        Document document = Jsoup.parse(new URL(Resources.NEWS_CONTENT_API), 5000);
        Element ListNews= document.getElementById("Profile-activities");

        if( ListNews != null ){
            String articleUrl = ListNews.getElementsByTag("a").eq(0).attr("href");
            Document doc = Jsoup.parse(new URL("https:"+articleUrl), 5000);
            Elements news = doc.getElementsByClass("RichText ztext Post-RichText css-1g0fqss");
            StringBuilder sb = new StringBuilder();
            for (Element e : news){
                sb.append(e.getElementsByTag("p").text());
            }
            String[] ls = sb.toString().split(" ");
            StringBuilder msg = new StringBuilder();

            for (String s : ls){
                msg.append(s).append("\n");
            }
            return msg;
        }else {
            Plugin.INSTANCE.getLogger().info("爬取失败...");
            throw new IOException("爬取失败！");
        }

    }
}
