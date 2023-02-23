package yplugin.Aimg;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.Utils.Cooler;
import yplugin.Utils.DownloadUtil;

import java.util.Random;
import java.util.concurrent.Future;

public class PixivImg extends PureCommand {
    public static Random r = new Random();

    public PixivImg(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送 二次元图片";
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
                String url;
                if(r.nextBoolean()){
                    url = "https://www.uukey.cn/pay/dm/api";
                }else {
                    url = "https://www.uukey.cn/pay/ecy/api";
                }
                ExternalResource externalResource = DownloadUtil.downloadAndConvert(url);
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



//
//    @Override
//    public void onCommand(GroupMessageEvent event) {
//        long newTime = System.currentTimeMillis();
//        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
//            time = newTime;
//            CompletableFuture<String> getImage = CompletableFuture.supplyAsync(() -> {
//                try {
//                    Random random = new Random();
//                    return ImgDownloader.download("http://bjb.yunwj.top/php/tp/60.jpg", Config.INSTANCE.getImageStorage());
//                } catch (IOException e) {
//                    return "err";
//                }
//            });
//            MessageChainBuilder mcb = new MessageChainBuilder();
//            getImage.thenAccept((result) -> {
//                if (result.equalsIgnoreCase("err")) {
//                    event.getGroup().sendMessage(mcb.append("图片获取失败 >_<").build());
//                } else {
//                    mcb.append(Contact.uploadImage(event.getGroup(),
//                            new File(Config.INSTANCE.getImageStorage() + result)));
//                    event.getGroup().sendMessage(mcb.build());
//                }
//            });
//        } else {
//            event.getGroup().sendMessage(
//                    new MessageChainBuilder()
//                            .append(new QuoteReply(event.getSource()))
//                            .append(new At(event.getSender().getId()))
//                            .append("\n太频繁了，请冷静一会儿吧\n")
//                            .append("Left: ")
//                            .append(String.valueOf((Config.INSTANCE.getGetRandImageCD()-newTime + time) / 1000))
//                            .append(" s")
//                            .build()
//            );
//        }
//    }
}
