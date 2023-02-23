package yplugin.Ajob;

import net.mamoe.mirai.contact.Friend;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import yplugin.Plugin;
import yplugin.Utils.AJobScheduler;

public class JobTip implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Plugin.INSTANCE.getLogger().info("喝水提醒 任务启动成功");

        // 指定人发送信息
        Friend friend = AJobScheduler.bot.getFriend(296711867L);
        if(friend == null){
            Plugin.INSTANCE.getLogger().info("您还没有该好友");
        }else {
            friend.sendMessage("身体是革命的本钱，请注意喝水~");
        }




    }


}
