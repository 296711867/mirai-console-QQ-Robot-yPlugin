package yplugin.Aimg;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.Adownload;
import yplugin.Utils.Cooler;
import net.mamoe.mirai.message.data.Image;

import java.util.concurrent.Future;

/**
 * @Description 发送古风图片
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class AncientImg extends PureCommand {
    public AncientImg( String command, String name) {
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
                ExternalResource externalResource = Adownload.getdownloadResource(Resources.ANCIENT_API);
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
                ExternalResource externalResource = Adownload.getdownloadResource(Resources.ANCIENT_API);
                Image image = event.getSubject().uploadImage(externalResource);
                event.getSubject().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                event.getSubject().sendMessage("图片获取失败 >_<");
            }
        });
    }



}
