package yplugin.Ajob;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import yplugin.Config.Config;
import yplugin.Plugin;
import yplugin.Utils.AJobScheduler;
import yplugin.Utils.ImgDownloader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class GroupFoafCanlendarTip implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Plugin.INSTANCE.getLogger().info("群发日历 任务启动成功");

        //下载图片
        String myurl = "url";
        try {
            myurl = ImgDownloader.downloadAndParse(
                    "https://api.vvhan.com/api/moyu?type=json",5);
        } catch (IOException e) {
            Plugin.INSTANCE.getLogger().info("日历获取失败！");
            e.printStackTrace();
        }

        String finalMyurl = myurl.replace("\\","");
        Plugin.INSTANCE.getLogger().info(finalMyurl);
        CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
            try {
                return ImgDownloader.download(finalMyurl, Config.INSTANCE.getImageStorage());
            } catch (IOException e) {
                Plugin.INSTANCE.getLogger().info("日历 下载失败！");
                return "err";
            }
        });

        // 指定人发送信息
        for (Long groupId: Config.INSTANCE.getGroupList()){
            Group group = AJobScheduler.bot.getGroup(groupId);
            if(group != null){
                group.sendMessage("今天的摸鱼人日历来啦~ \n ヾ(≧▽≦*)o");

                MessageChainBuilder mcb = new MessageChainBuilder();
                getImage.thenAccept((result) -> {
                    if (result.equalsIgnoreCase("err")) {
                        group.sendMessage(mcb.append("图片获取失败 >_<").build());
                    } else {
                        mcb.append(Contact.uploadImage(group,
                                new File(Config.INSTANCE.getImageStorage() + result)));
                        group.sendMessage(mcb.build());
                    }
                });
            }

        }



    }


}
