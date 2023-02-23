package yplugin.Acmd;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;

public class DSTDetailinfo extends PureCommand {
    public DSTDetailinfo(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送房间详情";
    }

    @Override
    public void onCommand(GroupMessageEvent event) {


        String[] rawText = event.getMessage().contentToString().split(" ");
        String body = rawText[1];

        try {
            String key = Plugin.stringArrayList.get(Integer.parseInt(body) - 1);

            if(!key.isEmpty()){
                //链式构建请求
                String result = HttpRequest
                        .post("https://api.dstserverlist.top/api/details/"+key)
                        .timeout(20000)//超时，毫秒
                        .execute().body();

                JSONObject jsonObject = (JSONObject) JSON.parse(result);

                String Name = jsonObject.getString("Name");
                String Platform = jsonObject.getString("Platform");

                String Address = jsonObject.getString("Address");
                Long Port= jsonObject.getLong("Port");

                JSONArray Players = jsonObject.getJSONArray("Players");
                JSONArray ModsInfo = jsonObject.getJSONArray("ModsInfo");

                String Season = jsonObject.getString("Season");

                JSONObject days = jsonObject.getJSONObject("DaysInfo");
                Long Day = days.getLong("Day");
                Long DaysElapsedInSeason = days.getLong("DaysElapsedInSeason");
                Long DaysLeftInSeason = days.getLong("DaysLeftInSeason");

                long seasonDay = DaysLeftInSeason+DaysElapsedInSeason;

                MessageChainBuilder mcb = new MessageChainBuilder();

                String serverName = "☸"+Name+"("+Platform+")\n";
                String allDays = "⏰"+"第"+Day+"天 "+Season+"("+DaysElapsedInSeason+"/"+seasonDay+")"+"\n";
                String playerList = "🌏玩家列表:\n";

                // 开始添加内容
                mcb.append(serverName).append(allDays).append(playerList);

                // ------------------------添加玩家
                if(Players.size()==0){
                    mcb.append("无\n");
                }else {
                    for(int i=0; i<Players.size(); i++){
                        JSONObject object = Players.getJSONObject(i);

                        String PlayerName = object.getString("Name");
                        String Prefab = object.getString("Prefab");
                        String playerInfo = PlayerName + "(" + Prefab + ")\n";
                        // 加入输出
                        mcb.append(playerInfo);
                    }
                }

                // ------------------------添加模组
                mcb.append("✴模组列表:\n");

                if(ModsInfo.size()==0){
                    mcb.append("无\n");
                }else {
                    for(int i=0; i<ModsInfo.size(); i++){
                        JSONObject object = ModsInfo.getJSONObject(i);

                        String ModeName = object.getString("Name");
                        // 加入输出
                        mcb.append(ModeName).append("\n");
                    }
                }

                // ------------------------添加 直连信息
                String directConnection = "⏩ 直连代码: \nc_connect(\""+ Address +"\"," + Port + ")";

                mcb.append(directConnection);

                event.getSubject().sendMessage(mcb.build());

            }

        }catch (Exception e){
            event.getSubject().sendMessage("获取服务器失败，请稍后再试！>_<");
            Plugin.INSTANCE.getLogger().info(e);
        }
    }
}
