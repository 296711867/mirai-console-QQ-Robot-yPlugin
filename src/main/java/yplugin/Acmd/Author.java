package yplugin.Acmd;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Plugin;

/**
 * @Description 作者信息
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class Author extends PureCommand {
    public Author( String command, String name) {
        super(command, name);
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        MessageChain msg = new MessageChainBuilder()
                .append("我的主人是:采琴~")
                .build();
        event.getSubject().sendMessage(msg);


    }

    @Override
    public void friendCommand(FriendMessageEvent event) {
        MessageChain msg = new MessageChainBuilder()
                .append("我的主人是:采琴~")
                .build();
        event.getSubject().sendMessage(msg);
    }
}
