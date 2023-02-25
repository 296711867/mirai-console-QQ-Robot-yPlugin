package yplugin.Commands;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public interface Command {
    String getName();
    String getCommond();

    void onCommand(GroupMessageEvent event);

    void friendCommand(FriendMessageEvent event);
}
