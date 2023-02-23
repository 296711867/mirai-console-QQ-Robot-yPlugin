package yplugin.Ajob;

import net.mamoe.mirai.contact.Friend;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import yplugin.Plugin;
import yplugin.Utils.AJobScheduler;

public class YWXJobTip implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Plugin.INSTANCE.getLogger().info("YWX打卡提醒任务 启动成功");

        // 指定人发送信息 宝贝
        Friend friend = AJobScheduler.bot.getFriend(2779072927L);
        if(friend == null){
            Plugin.INSTANCE.getLogger().info("您还没有该好友");
        }else {
            friend.sendMessage("宝贝~你今天打卡了么？ \n （づ￣3￣）づ╭❤～");
        }

        // 指定人发送信息 田璐
        Friend tian = AJobScheduler.bot.getFriend(1512396422L);
        if(tian == null){
            Plugin.INSTANCE.getLogger().info("您还没有该好友");
        }else {
            tian.sendMessage("嗨~你今天打卡了吗 \n ~(๑•̀ㅂ•́)و✧");
        }




    }


}
