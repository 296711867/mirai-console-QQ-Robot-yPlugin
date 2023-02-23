package yplugin.Utils;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import yplugin.Plugin;
import yplugin.vo.DSTserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class DST {

    public static ArrayList<DSTserver> postDSTserver(String jsonURL, RequestBody requestBody,
                                                     Function<String, JSONObject> getObjectFunction) throws IOException {

        Request request = new Request.Builder().url(jsonURL).method("POST", requestBody).build();
        Response response = Plugin.globalClient.newCall(request).execute();

        if (response.body() == null) throw new IOException("Response body is null.");
        JSONObject jsonObject = getObjectFunction.apply(response.body().string());

//        JSONObject object = jsonObject.getJSONObject("List");
        Plugin.INSTANCE.getLogger().info(jsonObject.toString());

        // 转换为数组
        JSONArray arr = jsonObject.getJSONArray("List");

        ArrayList<DSTserver> list = new ArrayList<>();

        for(Object ob: arr){
            Plugin.INSTANCE.getLogger().info("开始强转！");
            list.add((DSTserver) ob);
        }
        Plugin.INSTANCE.getLogger().info("信息获取成功！");

        return list;

//        ArrayList<DSTserver> list=new ArrayList();
//
//        for(int i=0; i<arr.length(); i++){
//
//            list.add(arr[i]);
//        }

//        Plugin.INSTANCE.getLogger().info(object.);

//        return ExternalResource.create(imageResponse.body().bytes());
    }
}
