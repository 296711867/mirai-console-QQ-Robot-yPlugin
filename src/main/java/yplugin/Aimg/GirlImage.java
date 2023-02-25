package yplugin.Aimg;


import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Config.Resources;
import yplugin.Utils.Adownload;

import java.io.File;
import java.util.concurrent.CompletableFuture;
/**
 * @Description 发送美女图片
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class GirlImage extends PureCommand {
    public static Long time = 0L;

    public GirlImage( String command, String name) {
        super(command, name);
    }


    @Override
    public void onCommand(GroupMessageEvent event) {
        long newTime = System.currentTimeMillis();
        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;
            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
                try {
                    return Adownload.downloadPicture(Resources.PEAUTY_API);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
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

    @Override
    public void friendCommand(FriendMessageEvent event) {
        long newTime = System.currentTimeMillis();
        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;
            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
                try {
                    return Adownload.downloadPicture(Resources.PEAUTY_API);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });
            MessageChainBuilder mcb = new MessageChainBuilder()
                    .append(new QuoteReply(event.getSource()))
                    .append(new At(event.getSender().getId()));
            getImage.thenAccept((result) -> {
                if (result.equalsIgnoreCase("err")) {
                    event.getSender().sendMessage(mcb.append("图片获取失败 >_<").build());
                } else {
                    mcb.append(Contact.uploadImage(event.getSender(),
                            new File(Config.INSTANCE.getImageStorage() + result)));
                    event.getSender().sendMessage(mcb.build());
                }
            });
        } else {
            event.getSender().sendMessage(
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
