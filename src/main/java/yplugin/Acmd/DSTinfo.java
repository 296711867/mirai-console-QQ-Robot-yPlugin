package yplugin.Acmd;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.vo.DSTserver;

/**
 * @Description 查看房间
 * @author pan
 * @Since 2023年2月24日17:41:13
 */
public class DSTinfo extends PureCommand {
    public DSTinfo( String command, String name) {
        super(command, name);
    }

    @Override
    public void onCommand(GroupMessageEvent event) {


        String[] rawText = event.getMessage().contentToString().split(" ");
        String body = rawText[1];

        try {
            //链式构建请求
            String result = HttpRequest
                    .post("https://api.dstserverlist.top/api/list?name="+body+"&pageCount=5&page=0")
                    .timeout(20000)//超时，毫秒
                    .execute().body();

            DSTserver dst = BeanUtil.toBean(JSON.parse(result), DSTserver.class);
            Plugin.INSTANCE.getLogger().info(dst.toString());

            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            com.alibaba.fastjson.JSONArray array = jsonObject.getJSONArray("List");

            if (array.size() == 0){
                event.getSubject().sendMessage("未查询到服务器，请换个名称！>_<");
            }else {
                Plugin.stringArrayList.clear();
                MessageChainBuilder mcb = new MessageChainBuilder();
                mcb.append("服务器最多显示5个:（多的省略） \n");
                for(int i=0; i<array.size(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Plugin.stringArrayList.add(object.getString("RowId"));
                    String Name = object.getString("Name");
                    Plugin.INSTANCE.getLogger().info(Name);

                    Long Connected = object.getLong("Connected");
                    Long MaxConnections = object.getLong("MaxConnections");

                    // (0/5)
                    String id = String.valueOf(i+1);
                    String num = "("+Connected+"/"+MaxConnections+")";
                    String res = id+"."+Name+num;

                    // 加入输出
                    mcb.append(res).append("\n");

                }
                event.getSubject().sendMessage(mcb.build());
            }

        }catch (Exception e){
            event.getSubject().sendMessage("获取服务器失败，请稍后再试！>_<");
            Plugin.INSTANCE.getLogger().info(e);
        }
    }

    @Override
    public void friendCommand(FriendMessageEvent event) {

        String[] rawText = event.getMessage().contentToString().split(" ");
        String body = rawText[1];

        try {
            //链式构建请求
            String result = HttpRequest
                    .post("https://api.dstserverlist.top/api/list?name="+body+"&pageCount=5&page=0")
                    .timeout(20000)//超时，毫秒
                    .execute().body();

            DSTserver dst = BeanUtil.toBean(JSON.parse(result), DSTserver.class);
            Plugin.INSTANCE.getLogger().info(dst.toString());

            JSONObject jsonObject = (JSONObject) JSON.parse(result);
            com.alibaba.fastjson.JSONArray array = jsonObject.getJSONArray("List");

            if (array.size() == 0){
                event.getSubject().sendMessage("未查询到服务器，请换个名称！>_<");
            }else {
                Plugin.stringArrayList.clear();
                MessageChainBuilder mcb = new MessageChainBuilder();
                mcb.append("服务器最多显示5个:（多的省略） \n");
                for(int i=0; i<array.size(); i++){
                    JSONObject object = array.getJSONObject(i);
                    Plugin.stringArrayList.add(object.getString("RowId"));
                    String Name = object.getString("Name");
                    Plugin.INSTANCE.getLogger().info(Name);

                    Long Connected = object.getLong("Connected");
                    Long MaxConnections = object.getLong("MaxConnections");

                    // (0/5)
                    String id = String.valueOf(i+1);
                    String num = "("+Connected+"/"+MaxConnections+")";
                    String res = id+"."+Name+num;

                    // 加入输出
                    mcb.append(res).append("\n");

                }
                event.getSubject().sendMessage(mcb.build());
            }

        }catch (Exception e){
            event.getSubject().sendMessage("获取服务器失败，请稍后再试！>_<");
            Plugin.INSTANCE.getLogger().info(e);
        }
    }



}
