package yplugin.AWriteTalk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;

import java.io.IOException;

public class DogTalk extends PureCommand {
    public DogTalk(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送舔狗日记";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        String jsonURL = "https://api.likepoems.com/ana/lickdog";

        try {
            Request request = new Request.Builder().url(jsonURL).build();
            Response response = Plugin.globalClient.newCall(request).execute();

            Plugin.INSTANCE.getLogger().info("舔狗日记获取成功！");

            if (response.body() == null) throw new IOException("Response body is null.");

            String str = response.body().string();

            Plugin.INSTANCE.getLogger().info(str);

            MessageChain msg = new MessageChainBuilder()
                    .append("舔狗日记来啦~\n")
                    .append("--------------------\n")
                    .append(str)
                    .build();
            event.getSubject().sendMessage(msg);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
