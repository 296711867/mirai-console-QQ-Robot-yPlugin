package yplugin.AnimalsImg;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Utils.ImgDownloader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class RandomCat extends PureCommand {
    public static Long time = 0L;

    public RandomCat(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送猫咪图片";
    }

    @Override
    public void onCommand(GroupMessageEvent event){

        long newTime = System.currentTimeMillis();



        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;

            String myurl = "url";
            try {
                myurl = ImgDownloader.downloadAndParse(
                        "https://api.thecatapi.com/v1/images/search", 7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalMyurl = myurl;
            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
                try {
                    return ImgDownloader.download(finalMyurl, Config.INSTANCE.getImageStorage());
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
                            .append("\ngkd太频繁了，请休息一会儿吧\n")
                            .append("Left: ")
                            .append(String.valueOf((Config.INSTANCE.getGetRandImageCD()-newTime + time) / 1000))
                            .append(" s")
                            .build()
            );
        }

    }
}
