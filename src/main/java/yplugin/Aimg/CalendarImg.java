package yplugin.Aimg;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.Adownload;
import yplugin.Utils.Cooler;

import java.util.concurrent.Future;

/**
 * @Description 发送摸鱼日历
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class CalendarImg extends PureCommand {
    public static Long time = 0L;

    public CalendarImg( String command, String name) {
        super(command, name);
    }



    @Override
    public void onCommand(GroupMessageEvent event) {
        if (Cooler.isLocked(Resources.LOCK_UID)) {
            event.getSubject().sendMessage("请求冷却中，请待会再试！>_<");
            return;
        }
        Cooler.lock(Resources.LOCK_UID, Resources.LOCK_TIME);
        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                ExternalResource externalResource = Adownload.downloadAndParse(Resources.CALENDAR_API, "url");
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                event.getSubject().sendMessage("图片获取失败 >_<");
            }
        });
    }

    @Override
    public void friendCommand(FriendMessageEvent event) {
        if (Cooler.isLocked(Resources.LOCK_UID)) {
            event.getSubject().sendMessage("请求冷却中，请待会再试！>_<");
            return;
        }
        Cooler.lock(Resources.LOCK_UID, Resources.LOCK_TIME);
        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                ExternalResource externalResource = Adownload.downloadAndParse(Resources.CALENDAR_API, "url");
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                event.getSubject().sendMessage("图片获取失败 >_<");
            }
        });
    }




//    @Override
//    public void onCommand(GroupMessageEvent event){
//
//        long newTime = System.currentTimeMillis();
//
//        MessageChain msg = new MessageChainBuilder()
//                .append("快乐摸鱼每一天(*^▽^*)")
//                .build();
//        event.getSubject().sendMessage(msg);
//
//        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
//            time = newTime;
//
//            String myurl = "url";
//            try {
//                myurl = ImgDownloader.downloadAndParse(
//                        "https://api.vvhan.com/api/moyu?type=json",5);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String finalMyurl = myurl.replace("\\","");
//            Plugin.INSTANCE.getLogger().info(finalMyurl);
//            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
//                try {
//                    return ImgDownloader.download(finalMyurl, Config.INSTANCE.getImageStorage());
//                } catch (IOException e) {
//                    return "err";
//                }
//            });
//
//            MessageChainBuilder mcb = new MessageChainBuilder();
//
//            getImage.thenAccept((res) -> {
//                if (res.equalsIgnoreCase("err")) {
//                    event.getGroup().sendMessage(mcb.append("图片获取失败 >_<").build());
//                } else {
//                    mcb.append(Contact.uploadImage(event.getGroup(),
//                            new File(Config.INSTANCE.getImageStorage() + res)));
//                    event.getGroup().sendMessage(mcb.build());
//                }
//            });
//
//        } else {
//            event.getGroup().sendMessage(
//                    new MessageChainBuilder()
//                            .append(new QuoteReply(event.getSource()))
//                            .append(new At(event.getSender().getId()))
//                            .append("\n请求太频繁了，请冷静一会儿吧\n")
//                            .append("Left: ")
//                            .append(String.valueOf((Config.INSTANCE.getGetRandImageCD()-newTime + time) / 1000))
//                            .append(" s")
//                            .build()
//            );
//        }
//
//
//    }

}
