package yplugin.Aimg;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.Utils.Cooler;
import yplugin.Utils.DownloadUtil;

import java.util.concurrent.Future;

public class CalendarImg extends PureCommand {
    public static Long time = 0L;

    public CalendarImg(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送摸鱼日历";
    }


    @Override
    public void onCommand(GroupMessageEvent event) {

        long uid = event.getSender().getId();
        if (Cooler.isLocked(uid)) {
            event.getSubject().sendMessage("冷却中，请待会再试！>_<");
            return;
        }

        Cooler.lock(uid, 3000L);

        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                ExternalResource externalResource = DownloadUtil.downloadAndParse(
                        "https://api.vvhan.com/api/moyu?type=json", "url");
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);

                externalResource.close();
            } catch (Exception ex) {
                event.getSubject().sendMessage("图片获取失败 >_<");
            }
        });

        Plugin.watchExecutorService.submit(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException ex) {
                return;
            }
            if (task.isDone() || task.isCancelled()) {
                return;
            }
            task.cancel(true);
            event.getSubject().sendMessage("图片获取超时，请稍后再试!");
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
