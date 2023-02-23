package yplugin.Ajob;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.ExternalResource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import yplugin.Config.Config;
import yplugin.Plugin;
import yplugin.Utils.AJobScheduler;
import yplugin.Utils.DownloadUtil;

import java.util.concurrent.Future;

public class GroupNewsTip implements Job {

    private ExternalResource externalResource;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Plugin.INSTANCE.getLogger().info("群发新闻 任务启动成功");



        Future<?> task = Plugin.downloadExecutorService.submit(() -> {
            try {
                externalResource = DownloadUtil.downloadAndConvert(
                        "http://bjb.yunwj.top/php/tp/60.jpg");

                // 指定人发送信息
                for (Long groupId: Config.INSTANCE.getGroupList()){
                    Group group = AJobScheduler.bot.getGroup(groupId);
                    if(group != null){
                        group.sendMessage("今天的早报来啦~ \n ヾ(≧▽≦*)o");
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
