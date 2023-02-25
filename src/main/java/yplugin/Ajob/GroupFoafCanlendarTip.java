package yplugin.Ajob;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.ExternalResource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import yplugin.Config.Config;
import yplugin.Config.Resources;
import yplugin.Plugin;
import yplugin.Utils.AJobScheduler;
import yplugin.Utils.Adownload;

import java.util.concurrent.Future;

/**
 * @Description 群发日历
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class GroupFoafCanlendarTip implements Job {

    private ExternalResource externalResource;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Plugin.INSTANCE.getLogger().info("群发日历 任务启动成功");

        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                externalResource = Adownload.downloadAndParse(Resources.CALENDAR_API, "url");
                // 指定人发送信息
                for (Long groupId: Config.INSTANCE.getGroupList()){
                    Group group = AJobScheduler.bot.getGroup(groupId);
                    if(group != null){
                        group.sendMessage("今天的摸鱼人日历来啦~ \n ヾ(≧▽≦*)o");
                        group.sendMessage(group.uploadImage(externalResource));
                    }
                }
                externalResource.close();
            } catch (Exception ex) {
                Plugin.INSTANCE.getLogger().info("图片获取失败 >_<\"");
            }
        });
    }

}
