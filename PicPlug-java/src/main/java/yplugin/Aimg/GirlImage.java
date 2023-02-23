package yplugin.Aimg;


import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Utils.ImgDownloader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class GirlImage extends PureCommand {
    public static Long time = 0L;

    public GirlImage(String name) {
        super(name);
    }


    @Override
    public String info() {
        return super.info() + " -> 发送一张图片";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        long newTime = System.currentTimeMillis();
        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;
            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
                try {
                    List<String> urls  = Config.INSTANCE.getImageAPIList();
                    Random random = new Random();
                    return ImgDownloader.download(urls.get(random.nextInt(urls.size())), Config.INSTANCE.getImageStorage());
                } catch (IOException e) {
                    return "err";
                }
            });
            MessageChainBuilder mcb = new MessageChainBuilder()
                    .append(new QuoteReply(event.getSource()))
                    .append(new At(event.getSender().getId()));
            getImage.thenAccept((result) -> {
                if (result.equalsIgnoreCase("err")) {
                    event.getGroup().sendMessage(mcb.append("图片获取失败 >_<").build());
                } else {
                    mcb.append(Contact.uploadImage(event.getGroup(),
                            new File(Config.INSTANCE.getImageStorage() + result)));
                    event.getGroup().sendMessage(mcb.build());
                }
            });
        } else {
            event.getGroup().sendMessage(
                    new MessageChainBuilder()
                            .append(new QuoteReply(event.getSource()))
                            .append(new At(event.getSender().getId()))
                            .append("\n请求太频繁了，请冷静一会儿吧\n")
                            .append("Left: ")
                            .append(String.valueOf((Config.INSTANCE.getGetRandImageCD()-newTime + time) / 1000))
                            .append(" s")
                            .build()
            );
        }
    }

}
