package yplugin.AnimalsImg;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import yplugin.Config.Config;
import yplugin.Utils.ImgDownloadOld;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
/**
 * @Description 发送猫咪图片（淘汰版本）
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class RandomCat{
    public static Long time = 0L;

    public void onCommand(GroupMessageEvent event){
        long newTime = System.currentTimeMillis();
        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;
            String myurl = "url";
            try {
                myurl = ImgDownloadOld.downloadAndParse(
                        "https://api.thecatapi.com/v1/images/search", 7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalMyurl = myurl;
            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
                try {
                    return ImgDownloadOld.download(finalMyurl, Config.INSTANCE.getImageStorage());
                } catch (IOException e) {
                    return "err";
                }
            });
            MessageChainBuilder mcb = new MessageChainBuilder()
                    .append(new QuoteReply(event.getSource()))
                    .append(new At(event.getSender().getId()));
            getImage.thenAccept((res) -> {
                if (res.equalsIgnoreCase("err")) {
                    event.getGroup().sendMessage(mcb.append("图片获取失败 >_<").build());
                } else {
                    mcb.append(Contact.uploadImage(event.getGroup(),
                            new File(Config.INSTANCE.getImageStorage() + res)));
                    event.getGroup().sendMessage(mcb.build());
                }
            });
        } else {
            event.getGroup().sendMessage(
                    new MessageChainBuilder()
                            .append(new QuoteReply(event.getSource()))
                            .append(new At(event.getSender().getId()))
                            .append("\n太频繁了，请休息一会儿吧\n")
                            .append("Left: ")
                            .append(String.valueOf((Config.INSTANCE.getGetRandImageCD()-newTime + time) / 1000))
                            .append(" s")
                            .build()
            );
        }

    }
}
