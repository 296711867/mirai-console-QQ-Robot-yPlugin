package yplugin.AWriteTalk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;

import java.io.IOException;

public class SoupTalk extends PureCommand {
    public SoupTalk(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送毒鸡汤";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        String jsonURL = "https://api.likepoems.com/ana/dujitang";

        try {
            Request request = new Request.Builder().url(jsonURL).build();
            Response response = Plugin.globalClient.newCall(request).execute();

            Plugin.INSTANCE.getLogger().info("毒鸡汤获取成功！");

            if (response.body() == null) throw new IOException("Response body is null.");

            String str = response.body().string();

            Plugin.INSTANCE.getLogger().info(str);

            MessageChain msg = new MessageChainBuilder()
                    .append("毒鸡汤来啦~\n")
                    .append("--------------------\n")
                    .append(str)
                    .build();
            event.getSubject().sendMessage(msg);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
