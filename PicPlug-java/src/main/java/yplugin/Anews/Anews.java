package yplugin.Anews;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.ForwardMessage;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.Utils.getNews;

import java.io.IOException;
import java.net.URL;

public class Anews extends PureCommand {

    public Anews(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送今日新闻";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {

        String url = "https://www.zhihu.com/people/mt36501";

        MessageChainBuilder mcb = new MessageChainBuilder()
                .append("今日新闻来啦~\n")
                .append("--------------------\n");

        try {
            Plugin.INSTANCE.getLogger().info("爬取新闻中...");

            Document document = Jsoup.parse(new URL(url), 5000);
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


                ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getGroup());

                builder.add(2656067564L, "采琴的机器人", new PlainText("新闻来啦！ ♪(^∇^*)"));
                builder.add(3110985822L,"采琴", new PlainText(msg.toString()));

                String todayHistory = getNews.getTodayHistory();
                if(todayHistory != null){
                    builder.add(3110985822L,"采琴", new PlainText(todayHistory));
                }


                StringBuilder interFaceRank = getNews.getBilibiliInterFaceRank();
                if(interFaceRank != null){
                    builder.add(3110985822L,"采琴", new PlainText(interFaceRank.toString()));
                }
                StringBuilder cartoonRank = getNews.getBilibiliCartoonRank();
                if(cartoonRank != null){
                    builder.add(3110985822L,"采琴", new PlainText(cartoonRank.toString()));
                }


                // 可添加 MessageEvent
                // builder.add(event);

                builder.setDisplayStrategy(new ForwardMessage.DisplayStrategy() {
                }); // 可选, 修改显示策略

                ForwardMessage forward = builder.build();
                event.getGroup().sendMessage(forward);


                // 发送新闻
//                event.getSubject().sendMessage(mcb.build());
                Plugin.INSTANCE.getLogger().info("爬取成功！");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Plugin.INSTANCE.getLogger().info("错误："+ e);
        }

    }
}
