package yplugin.AWriteTalk;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Commands.PureCommand;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.GetTalkUtil;

import java.io.IOException;
/**
 * @Description 发送一言语句
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class OneTalk extends PureCommand {

    public OneTalk( String command, String name) {
        super(command, name);
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        String msg = GetTalkUtil.GetTalk(Resources.LAUGH_TALK_API, this.getName()+"来啦~");

        if(msg != null){
            event.getSubject().sendMessage(msg);
        }else {
            event.getSubject().sendMessage("未获取到"+ this.getName());
        }
    }

    @Override
    public void friendCommand(FriendMessageEvent event){
        String msg = GetTalkUtil.GetTalk(Resources.DOG_TALK_API, this.getName()+"来啦~");

        if(msg != null){
            event.getSubject().sendMessage(msg);
        }else {
            event.getSubject().sendMessage("未获取到"+ this.getName());
        }
    }
}
