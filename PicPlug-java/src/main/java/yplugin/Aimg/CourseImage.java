package yplugin.Aimg;


import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;

import java.io.File;

public class CourseImage extends PureCommand {
    public static Long time = 0L;

    public CourseImage(String name) {
        super(name);
    }


    @Override
    public String info() {
        return super.info() + " -> 发送课程图片";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        long newTime = System.currentTimeMillis();
        if (newTime - time > Config.INSTANCE.getGetRandImageCD()) {
            time = newTime;

            MessageChainBuilder mcb = new MessageChainBuilder();
            mcb.append(Contact.uploadImage(event.getGroup(),
                    new File(Config.INSTANCE.getImageStorage() + "course.jpg")));
            event.getGroup().sendMessage(mcb.build());

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
