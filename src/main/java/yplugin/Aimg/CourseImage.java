package yplugin.Aimg;


import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.Adownload;
import yplugin.Utils.Cooler;

import java.io.File;
import java.util.concurrent.Future;

/**
 * @Description 发送课程图片
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class CourseImage extends PureCommand {

    public CourseImage( String command, String name) {
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
                MessageChainBuilder mcb = new MessageChainBuilder();
                mcb.append(Contact.uploadImage(event.getGroup(),
                        new File(Config.INSTANCE.getImageStorage() + "course.jpg")));
                event.getSender().sendMessage(mcb.build());
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
                MessageChainBuilder mcb = new MessageChainBuilder();
                mcb.append(Contact.uploadImage(event.getSender(),
                        new File(Config.INSTANCE.getImageStorage() + "course.jpg")));
                event.getSender().sendMessage(mcb.build());
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info(ex);
                event.getSubject().sendMessage(this.getCommond()+"获取失败,请稍后再试!\n");
            }
        });
    }
}
