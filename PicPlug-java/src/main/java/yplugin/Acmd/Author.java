package yplugin.Acmd;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import yplugin.Commands.PureCommand;

public class Author extends PureCommand {
    public Author(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送主人信息";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        MessageChain msg = new MessageChainBuilder()
                .append("我的主人是:采琴~")
                .build();
        event.getSubject().sendMessage(msg);
    }
}
