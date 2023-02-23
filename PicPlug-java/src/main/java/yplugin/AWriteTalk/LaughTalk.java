package yplugin.AWriteTalk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;

import java.io.IOException;

public class LaughTalk extends PureCommand {
    public LaughTalk(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送笑话";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {
        String jsonURL = "https://api.andeer.top/API/joke.php";

        try {
            Request request = new Request.Builder().url(jsonURL).build();
            Response response = Plugin.globalClient.newCall(request).execute();

            Plugin.INSTANCE.getLogger().info("笑话获取成功！");

            if (response.body() == null) throw new IOException("Response body is null.");

            String[] strs = response.body().string().split("\"");

            if (strs[4].isEmpty()){
                Plugin.INSTANCE.getLogger().info("越界啦！");
                return;
            }else {
                Plugin.INSTANCE.getLogger().info(strs[4]);

                MessageChain msg = new MessageChainBuilder()
                        .append("笑话来啦~\n")
                        .append("--------------------\n")
                        .append(strs[9])
                        .build();
                event.getSubject().sendMessage(msg);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        Plugin.INSTANCE.getLogger().info("获取成功！");


    }
}
