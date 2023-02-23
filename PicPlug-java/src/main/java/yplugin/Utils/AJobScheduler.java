package yplugin.Utils;

import net.mamoe.mirai.Bot;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import yplugin.Ajob.GroupFoafCanlendarTip;
import yplugin.Ajob.GroupNewsTip;
import yplugin.Ajob.JobTip;
import yplugin.Ajob.YWXJobTip;

import java.util.TimeZone;

public class AJobScheduler {

    public static Bot bot;

    public static void jobTips(@NotNull Bot bot) throws SchedulerException {
        AJobScheduler.bot = bot;

        // -----------------------------每过一个小时-----------------------任务1提醒自己
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        org.quartz.Scheduler scheduler = schedulerFactory.getScheduler();

        // 2、创建JobDetail实例，并与Job类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(JobTip.class)
                .withIdentity("job", "group").build();

        // 3、构建Trigger实例     每隔 1h 执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "triggerGroup")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                ).build();

        // 4、Scheduler绑定Job和Trigger，并执行
        scheduler.scheduleJob(jobDetail, trigger);

        // --------------------------------每天10,16,22点---------------------任务2 YWX baby

        JobDetail YWXjob = JobBuilder.newJob(YWXJobTip.class)
                .withIdentity("YWXjob", "YWXjob").build();

        Trigger YWXtrigger = TriggerBuilder.newTrigger()
                .withIdentity("YWXtrigger", "YWXtrigger")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10,16,22 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                ).build();

        scheduler.scheduleJob(YWXjob, YWXtrigger);

        // ------------------------------------每天8点30--------------------任务3 早报发送

        JobDetail GroupNewsJob = JobBuilder.newJob(GroupNewsTip.class)
                .withIdentity("GroupNewsJob", "GroupNewsJob").build();

        // 0 30 8 * * ?     0/30 * * * * ?
        Trigger GroupNewsTrigger = TriggerBuilder.newTrigger()
                .withIdentity("GroupNewsTrigger", "GroupNewsTrigger")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 20 8 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                ).build();

        scheduler.scheduleJob(GroupNewsJob, GroupNewsTrigger);

        // ------------------------------------每天12点--------------------任务4 日历发送

        JobDetail GFCjob = JobBuilder.newJob(GroupFoafCanlendarTip.class)
                .withIdentity("GFCjob", "GFCjob").build();

        // 0 30 8 * * ?     0/30 * * * * ?
        Trigger GFCtrigger = TriggerBuilder.newTrigger()
                .withIdentity("GFCtrigger", "GFCtrigger")
                .startNow()//立即生效
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                ).build();

        scheduler.scheduleJob(GFCjob, GFCtrigger);



        scheduler.start();
    }

}
