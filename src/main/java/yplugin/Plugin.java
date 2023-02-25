package yplugin;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import okhttp3.OkHttpClient;

import org.quartz.*;
import yplugin.AWriteTalk.DogTalk;
import yplugin.AWriteTalk.OneTalk;
import yplugin.AWriteTalk.SoupTalk;
import yplugin.Acmd.Author;
import yplugin.Aimg.*;
import yplugin.Acmd.Help;
import yplugin.AWriteTalk.LaughTalk;
import yplugin.Anews.Anews;
import yplugin.Anews.NewsImg;
import yplugin.AnimalsImg.CatImg;
import yplugin.AnimalsImg.DogImg;
import yplugin.AnimalsImg.RandomCat;
import yplugin.Commands.CommandsManager;
import yplugin.Config.Config;
import yplugin.Utils.AJobScheduler;
import yplugin.Config.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Plugin extends JavaPlugin {

    public static ArrayList<String> stringArrayList = new ArrayList<>();

    public static StringBuilder pluginHelpList = new StringBuilder().append("小y的功能列表\n");

    public static final ExecutorService watchExecutorService = Executors.newFixedThreadPool(8);
    public static final ExecutorService downloadExecutorService = Executors.newFixedThreadPool(8);

    // 网络请求
    public static final OkHttpClient globalClient = new OkHttpClient.Builder().
            connectTimeout(15, TimeUnit.SECONDS).
            readTimeout(15, TimeUnit.SECONDS).
            build();

    public CommandsManager commandsManager;
    public static final Plugin INSTANCE = new Plugin();

    private Plugin() {
        super(new JvmPluginDescriptionBuilder(Resources.ID, Resources.VERSION)
                .name(Resources.NAME)
                .info(Resources.DESCRIPTION)
                .author(Resources.AUTHOR)
                .build());
    }
    @Override
    public void onEnable(){
        getLogger().info("插件："+Resources.NAME_CN+"正在加载...");
        getLogger().info("Plugin: "+Resources.NAME+" loading...");

        //读取配置文件
        reloadPluginConfig(Config.INSTANCE);
        // 初始化图片储存地址
        File imageStorage = new File(Config.INSTANCE.getImageStorage());
        if (!imageStorage.exists()){
            if (!imageStorage.mkdirs()){
                getLogger().error("创建图片文件夹失败");
                getLogger().error("Failed to create image storage folder");
            }
        }
        // 创建自建指令管理系统
        commandsManager = new CommandsManager(getLogger());

        getLogger().info("插件："+Resources.NAME_CN+"注册指令中...");
        getLogger().info("Plugin: "+Resources.NAME+" registering commands...");

        // 注册Console命令


        if(Config.INSTANCE.getCommandMap().get("NewsImg") || Config.INSTANCE.getCommandMap().get("Anews")){
            pluginHelpList.append("---------新闻---------\n");
            if(Config.INSTANCE.getCommandMap().get("NewsImg")){
                commandsManager.registerCommand(new NewsImg("新闻","新闻图片"));
                pluginHelpList.append("新闻 -> 发送新闻图片\n");
            }
            if(Config.INSTANCE.getCommandMap().get("Anews")){
                commandsManager.registerCommand(new Anews("更多","更多新闻"));
                pluginHelpList.append("更多 -> 发送更多新闻\n");
            }
        }

        if(Config.INSTANCE.getCommandMap().get("LaughTalk") || Config.INSTANCE.getCommandMap().get("OneTalk") ||
                Config.INSTANCE.getCommandMap().get("DogTalk") || Config.INSTANCE.getCommandMap().get("SoupTalk")){

            pluginHelpList.append("---------段子---------\n");

            if(Config.INSTANCE.getCommandMap().get("LaughTalk")){
                commandsManager.registerCommand(new LaughTalk("笑话","笑话"));
                pluginHelpList.append("笑话 -> 发送一段笑话\n");
            }
            if(Config.INSTANCE.getCommandMap().get("OneTalk")){
                commandsManager.registerCommand(new OneTalk("一言","一言语句"));
                pluginHelpList.append("舔狗 -> 发送舔狗日记\n");
            }
            if(Config.INSTANCE.getCommandMap().get("DogTalk")){
                commandsManager.registerCommand(new DogTalk("舔狗","舔狗日记"));
                pluginHelpList.append("鸡汤 -> 发送带毒鸡汤\n");
            }
            if(Config.INSTANCE.getCommandMap().get("SoupTalk")){
                commandsManager.registerCommand(new SoupTalk("鸡汤","带毒鸡汤"));
                pluginHelpList.append("一言 -> 发送一言语句\n");
            }

        }

        if(Config.INSTANCE.getCommandMap().get("DogImg") || Config.INSTANCE.getCommandMap().get("CatImg") ||
                Config.INSTANCE.getCommandMap().get("AncientImg") || Config.INSTANCE.getCommandMap().get("CalendarImg")){

            pluginHelpList.append("---------段子---------\n");

            if(Config.INSTANCE.getCommandMap().get("CatImg")){
                commandsManager.registerCommand(new CatImg("猫咪","猫咪图片"));
                pluginHelpList.append("猫咪 -> 发送猫咪图片\n");
            }

            if(Config.INSTANCE.getCommandMap().get("DogImg")){
                commandsManager.registerCommand(new DogImg("修狗", "修狗图片"));
                pluginHelpList.append("修狗 -> 发送修狗图片\n");
            }

            if(Config.INSTANCE.getCommandMap().get("AncientImg")){
                commandsManager.registerCommand(new AncientImg("古风","古风图片"));
                pluginHelpList.append("古风 -> 发送古风图片\n");
            }

            if(Config.INSTANCE.getCommandMap().get("CalendarImg")){
                commandsManager.registerCommand(new CalendarImg("摸鱼","摸鱼日历"));
                pluginHelpList.append("摸鱼 -> 发送摸鱼日历\n");
            }
        }

        if(Config.INSTANCE.getCommandMap().get("Help") || Config.INSTANCE.getCommandMap().get("Author")){

            pluginHelpList.append("---------其他---------\n");

            if(Config.INSTANCE.getCommandMap().get("Author")){
                commandsManager.registerCommand(new Author("作者","作者信息"));
                pluginHelpList.append("作者 -> 发送作者信息\n");
            }

            if(Config.INSTANCE.getCommandMap().get("Help")){
                commandsManager.registerCommand(new Help("功能","功能列表"));
                pluginHelpList.append("功能 -> 发送功能列表\n");
            }
        }
        pluginHelpList.append("--小y还有隐藏命令哟(≡ω≡)");

        // 隐藏命令 -----------默认不开启
        if(Config.INSTANCE.getCommandMap().get("GirlImage")){
            commandsManager.registerCommand(new GirlImage("美女", "美女图片"));
        }
        if(Config.INSTANCE.getCommandMap().get("PixivImg")){
            commandsManager.registerCommand(new PixivImg("二次元","二次元图片"));
        }
        if(Config.INSTANCE.getCommandMap().get("CourseImage")){
            commandsManager.registerCommand(new CourseImage("课表","课表"));
        }





        // 好友信息处理
        GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            for (Long obj: Config.INSTANCE.getFriendList()) {
                if (obj ==event.getFriend().getId()){
                    commandsManager.fridentHandle(event);
                }
            }

        });

        // 群消息处理
        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class,event -> {
            for (Long obj: Config.INSTANCE.getGroupList()) {
                if (obj ==event.getGroup().getId()){
                    commandsManager.handle(event);
                }
            }
        });

        // Bot登陆后执行
        GlobalEventChannel.INSTANCE.subscribeAlways(BotOnlineEvent.class, event ->{
            try {
                AJobScheduler.jobTips(event.getBot());
                getLogger().info("启动时间 ： " + new Date());

            } catch (SchedulerException e) {
                getLogger().error(e.getMessage());
            }

        });

        getLogger().info("插件："+Resources.NAME_CN+"加载完毕！");
        getLogger().info("Plugin: "+Resources.NAME+" loaded!");
    }
}