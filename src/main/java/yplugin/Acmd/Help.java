package yplugin.Acmd;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import yplugin.Commands.PureCommand;
import yplugin.Config.Config;
import yplugin.Plugin;

/**
 * @Description 发送功能列表
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class Help extends PureCommand {
    public Help( String command, String name) {
        super(command, name);
    }

    @Override
    public void onCommand(GroupMessageEvent event) {


//        stringBuilder.append("小y的功能列表\n");



//        MessageChainBuilder mb = new MessageChainBuilder()
//                .append("小y的功能列表\n")
//                .append("---------新闻---------\n")
//                .append("新闻 -> 发送新闻图片\n")
//                .append("更多 -> 发送更多新闻\n")
//                .append("---------段子---------\n")
//                .append("笑话 -> 发送一段笑话\n")
//                .append("舔狗 -> 发送舔狗日记\n")
//                .append("鸡汤 -> 发送带毒鸡汤\n")
//                .append("一言 -> 发送一言语句\n")
//                .append("---------图片---------\n")
//                .append("猫咪 -> 发送猫咪图片\n")
//                .append("修狗 -> 发送修狗图片\n")
//                .append("古风 -> 发送古风图片\n")
//                .append("摸鱼 -> 发送摸鱼日历\n")
//                .append("---------其他---------\n")
//                .append("作者 -> 发送作者信息\n")
//                .append("功能 -> 发送功能列表\n");

//        for (Command c : CommandsManager.COMMAND_LIST) {
//            mb.append(c.info()).append("\n");
//        }
        event.getSubject().sendMessage(Plugin.pluginHelpList.toString());
    }

    @Override
    public void friendCommand(FriendMessageEvent event) {
        MessageChainBuilder mb = new MessageChainBuilder()
                .append("小y的功能列表\n")
                .append("---------新闻---------\n")
                .append("新闻 -> 发送新闻图片\n")
                .append("新闻文字版 -> 发送新闻文字版\n")
                .append("---------段子---------\n")
                .append("笑话 -> 发送一段笑话\n")
                .append("舔狗 -> 发送舔狗日记\n")
                .append("鸡汤 -> 发送带毒鸡汤\n")
                .append("一言 -> 发送一言语句\n")
                .append("---------图片---------\n")
                .append("猫咪 -> 发送猫咪图片\n")
                .append("修狗 -> 发送修狗图片\n")
                .append("古风 -> 发送古风图片\n")
                .append("摸鱼 -> 发送摸鱼日历\n")
                .append("---------其他---------\n")
                .append("作者 -> 发送作者信息\n")
                .append("功能 -> 发送功能列表\n");

//        for (Command c : CommandsManager.COMMAND_LIST) {
//            mb.append(c.info()).append("\n");
//        }
        event.getSubject().sendMessage(mb.build());
    }

}
