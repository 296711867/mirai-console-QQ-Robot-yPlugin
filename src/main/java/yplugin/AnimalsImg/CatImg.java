package yplugin.AnimalsImg;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.Adownload;
import yplugin.Utils.Cooler;

import java.util.concurrent.Future;
/**
 * @Description 发送猫咪图片
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class CatImg extends PureCommand {

    public CatImg( String command, String name) {
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
                String catUrl = Adownload.getUrlIndex(Resources.CAT_API,7);
                ExternalResource externalResource = Adownload.getdownloadResource((catUrl));
                Image image = event.getGroup().uploadImage(externalResource);
                event.getGroup().sendMessage(image);
                externalResource.close();
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info(ex);
                event.getGroup().sendMessage(this.getCommond()+"迷路了,请稍后再试!\n");
            }
        });
    }

    @Override
    public void friendCommand(FriendMessageEvent event) {
        if (Cooler.isLocked(Resources.LOCK_UID)) {
            event.getFriend().sendMessage("请求冷却中，请待会再试！>_<");
            return;
        }
        Cooler.lock(Resources.LOCK_UID, Resources.LOCK_TIME);
        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                String catUrl = Adownload.getUrlIndex(Resources.CAT_API,7);
                ExternalResource externalResource = Adownload.getdownloadResource(catUrl);
                Image image = event.getFriend().uploadImage(externalResource);
                event.getFriend().sendMessage(image);

                externalResource.close();
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info(ex);
                event.getFriend().sendMessage(this.getCommond()+"迷路了,请稍后再试!\n");
            }
        });
    }


}
