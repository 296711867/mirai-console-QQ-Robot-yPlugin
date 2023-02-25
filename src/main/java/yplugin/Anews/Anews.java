package yplugin.Anews;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.ForwardMessage;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.getNews;

import java.io.IOException;
import java.net.URL;
/**
 * @Description 发送今日新闻
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class Anews extends PureCommand {

    public Anews( String command, String name) {
        super(command, name);
    }

    @Override
    public void onCommand(GroupMessageEvent event) {

        MessageChainBuilder mcb = new MessageChainBuilder()
                .append("今日新闻来啦~\n")
                .append("--------------------\n");

        try {
            ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getGroup());
            builder.add(2656067564L, "采琴的机器人", new PlainText("新闻来啦！ ♪(^∇^*)"));

            // 1、爬取新闻文字
            StringBuilder newsText = getNews.getNewsText();
            builder.add(3110985822L,"采琴", new PlainText(newsText.toString()));
            // 2、历史上的今天
            String todayHistory = getNews.getTodayHistory();
            if(todayHistory != null){
                builder.add(3110985822L,"采琴", new PlainText(todayHistory));
            }
            // 3、b站首页
            StringBuilder interFaceRank = getNews.getBilibiliInterFaceRank();
            if(interFaceRank != null){
                builder.add(3110985822L,"采琴", new PlainText(interFaceRank.toString()));
            }
            // 4、b站动漫
            StringBuilder cartoonRank = getNews.getBilibiliCartoonRank();
            if(cartoonRank != null){
                builder.add(3110985822L,"采琴", new PlainText(cartoonRank.toString()));
            }
            // 可选, 修改显示策略
            builder.setDisplayStrategy(new ForwardMessage.DisplayStrategy() {
            });
            // 5、发送信息
            ForwardMessage forward = builder.build();
            event.getGroup().sendMessage(forward);

        } catch (IOException e) {
            Plugin.INSTANCE.getLogger().info("错误："+ e);
        }

    }


    @Override
    public void friendCommand(FriendMessageEvent event) {

        MessageChainBuilder mcb = new MessageChainBuilder()
                .append("今日新闻来啦~\n")
                .append("--------------------\n");

        try {
            ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSender());
            builder.add(2656067564L, "采琴的机器人", new PlainText("新闻来啦！ ♪(^∇^*)"));

            // 1、爬取新闻文字
            StringBuilder newsText = getNews.getNewsText();
            builder.add(3110985822L,"采琴", new PlainText(newsText.toString()));
            // 2、历史上的今天
            String todayHistory = getNews.getTodayHistory();
            if(todayHistory != null){
                builder.add(3110985822L,"采琴", new PlainText(todayHistory));
            }
            // 3、b站首页
            StringBuilder interFaceRank = getNews.getBilibiliInterFaceRank();
            if(interFaceRank != null){
                builder.add(3110985822L,"采琴", new PlainText(interFaceRank.toString()));
            }
            // 4、b站动漫
            StringBuilder cartoonRank = getNews.getBilibiliCartoonRank();
            if(cartoonRank != null){
                builder.add(3110985822L,"采琴", new PlainText(cartoonRank.toString()));
            }
            // 可选, 修改显示策略
            builder.setDisplayStrategy(new ForwardMessage.DisplayStrategy() {
            });
            // 5、发送信息
            ForwardMessage forward = builder.build();
            event.getSender().sendMessage(forward);

        } catch (IOException e) {
            Plugin.INSTANCE.getLogger().info("错误："+ e);
        }

    }


}
