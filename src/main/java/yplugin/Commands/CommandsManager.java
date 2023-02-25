package yplugin.Commands;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import yplugin.Acmd.DSTDetailinfo;
import yplugin.Acmd.DSTinfo;
import yplugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandsManager {
    private final MiraiLogger logger;
    private static final ArrayList<PureCommand> PURE_COMMAND_LIST = new ArrayList<>();
//    public static final ArrayList<Command> COMMAND_LIST = new ArrayList<>();

    public CommandsManager(MiraiLogger logger) {
        this.logger = logger;
    }

    public void registerCommand(PureCommand pc) {
        PURE_COMMAND_LIST.add(pc);
        logger.info("注册指令：" + pc.getCommond());
    }

    /**
     * 解析群消息，提取合法的指令消息，并分配给对应的指令去处理事件
     *
     * @param event 机器人传入的群消息事件
     */
    public void handle(GroupMessageEvent event) {

        String[] rawText = event.getMessage().contentToString().split(" ");

        Plugin.INSTANCE.getLogger().info(Arrays.toString(rawText));

        // 进行查房   【查房 xxx】
        if (rawText.length == 2) {
            if(rawText[0].equals("查服")){
                PureCommand dsTinfo = new DSTinfo("查服","查服");
                dsTinfo.onCommand(event);
            }
            if(rawText[0].equals("房间")){
                PureCommand dstDetailinfo = new DSTDetailinfo("房间","房间");
                dstDetailinfo.onCommand(event);
            }
            return;
        }

        // 执行注册指令
        if (rawText.length == 1) {
            //分派解析后的指令到对应的command处理
            for (PureCommand pc : PURE_COMMAND_LIST) {
                if (rawText[0].equals(pc.getCommond())) {
                    pc.onCommand(event);
                }
            }
        }


//        if (rawText.length == 2 && rawText[0].startsWith(COMMAND_PREFIX)) {
//            String name = rawText[0].substring(COMMAND_PREFIX.length());
//            String text = rawText[1];
//            //分派解析后的指令到对应的command处理
//            for (SimpleCommand sc : SIMPLE_COMMAND_LIST) {
//                if (name.equals(sc.getName())) {
//                    sc.setContext(text);
//                    sc.onCommand(event);
//                }
//            }
//        }
    }

    public void fridentHandle(FriendMessageEvent event) {
        String[] rawText = event.getMessage().contentToString().split(" ");
        Plugin.INSTANCE.getLogger().info(Arrays.toString(rawText));
        // 执行注册指令
        if (rawText.length == 1) {
            //分派解析后的指令到对应的command处理
            for (PureCommand pc : PURE_COMMAND_LIST) {
                if (rawText[0].equals(pc.getCommond())) {
                    pc.friendCommand(event);
                }
            }
        }
    }
}
