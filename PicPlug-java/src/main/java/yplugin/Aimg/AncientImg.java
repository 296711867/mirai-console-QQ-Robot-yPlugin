package yplugin.Aimg;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.Utils.Cooler;
import yplugin.Utils.DownloadUtil;
import net.mamoe.mirai.message.data.Image;

import java.util.concurrent.Future;

public class AncientImg extends PureCommand {
    public static Long time = 0L;

    public AncientImg(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送古风图片";
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
                ExternalResource externalResource = DownloadUtil.downloadAndConvert("https://cdn.seovx.com/ha/?mom=302");
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



}
