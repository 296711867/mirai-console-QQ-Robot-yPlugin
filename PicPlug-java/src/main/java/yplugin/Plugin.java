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
import yplugin.Aimg.AncientImg;
import yplugin.Aimg.CalendarImg;
import yplugin.Acmd.Help;
import yplugin.AWriteTalk.LaughTalk;
import yplugin.Aimg.CourseImage;
import yplugin.Aimg.PixivImg;
import yplugin.Anews.Anews;
import yplugin.Anews.NewsImg;
import yplugin.AnimalsImg.DogImg;
import yplugin.AnimalsImg.RandomCat;
import yplugin.Commands.CommandsManager;
import yplugin.Config.Config;
import yplugin.Utils.AJobScheduler;
import yplugin.Utils.Resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Plugin extends JavaPlugin {

    public static ArrayList<String> stringArrayList = new ArrayList<>();

    public static final ExecutorService watchExecutorService = Executors.newFixedThreadPool(8);
    public static final ExecutorService downloadExecutorService = Executors.newFixedThreadPool(8);

    // 网络请求
    public static final OkHttpClient globalClient = new OkHttpClient.Builder().
            connectTimeout(3, TimeUnit.SECONDS).
            readTimeout(10, TimeUnit.SECONDS).
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
        commandsManager = new CommandsManager(getLogger(), Config.INSTANCE.getCommandPrefix());

        getLogger().info("插件："+Resources.NAME_CN+"注册指令中...");
        getLogger().info("Plugin: "+Resources.NAME+" registering commands...");

        // 注册自建指令

        // 检查是否开启自建指令

        // 获取指令名称
//        try {
//            Class resourcesClass = Resources.class;
//            Field help = resourcesClass.getField("help");
//            Field cl = resourcesClass.getField("cl");
//            Field img = resourcesClass.getField("img");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }

        // 注册Console命令


//        commandsManager.registerCommand(new GetImage("图片"));
        commandsManager.registerCommand(new NewsImg("新闻"));
        commandsManager.registerCommand(new Anews("新闻文字版"));

        commandsManager.registerCommand(new LaughTalk("笑话"));
        commandsManager.registerCommand(new OneTalk("一言"));
        commandsManager.registerCommand(new DogTalk("舔狗"));
        commandsManager.registerCommand(new SoupTalk("鸡汤"));

        commandsManager.registerCommand(new RandomCat("猫咪"));
        commandsManager.registerCommand(new DogImg("修狗"));

        commandsManager.registerCommand(new AncientImg("古风"));
        commandsManager.registerCommand(new CalendarImg("摸鱼"));

        commandsManager.registerCommand(new Help("功能"));
        commandsManager.registerCommand(new Author("作者"));

        commandsManager.registerCommand(new PixivImg("二次元"));
        commandsManager.registerCommand(new CourseImage("课表"));



        // 发送课表
        GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            Plugin.INSTANCE.getLogger().info("好友信息:"+event.getMessage().contentToString());

            if(event.getMessage().contentToString().equals("课表")){
                for (Long obj: Config.INSTANCE.getFriendList()) {
                    if (obj ==event.getSender().getId()){
                        event.getSender().sendMessage("课表来啦！o(*≧▽≦)ツ");

                        MessageChainBuilder mcb = new MessageChainBuilder();
                        mcb.append(Contact.uploadImage(event.getSender(),
                                new File(Config.INSTANCE.getImageStorage() + "course.jpg")));
                        event.getSender().sendMessage(mcb.build());
                    }
                }
            }
        });

        // 注入自建的指令处理系统
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