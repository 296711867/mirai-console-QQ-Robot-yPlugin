package yplugin.Aimg;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.Adownload;
import yplugin.Utils.Cooler;

import java.util.Random;
import java.util.concurrent.Future;
/**
 * @Description 发送二次元图片
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class PixivImg extends PureCommand {
    public static Random r = new Random();

    public PixivImg( String command, String name) {
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
                ExternalResource externalResource = Adownload.getdownloadResource((getPixivImg()));
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info(ex);
                event.getSubject().sendMessage(this.getCommond()+"获取失败,请稍后再试!\n");
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
                ExternalResource externalResource = Adownload.getdownloadResource(getPixivImg());
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info(ex);
                event.getSubject().sendMessage(this.getCommond()+"获取失败,请稍后再试!\n");
            }
        });
    }

    public String getPixivImg(){
        String url;
        if(r.nextBoolean()){
            url = "https://www.uukey.cn/pay/dm/api";
        }else {
            url = "https://www.uukey.cn/pay/ecy/api";
        }
        return url;
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
