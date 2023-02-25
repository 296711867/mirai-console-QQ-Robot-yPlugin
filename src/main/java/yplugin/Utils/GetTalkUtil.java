package yplugin.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Plugin;

import java.io.IOException;

public class GetTalkUtil {

    public static String GetTalk(String jsonURL, String beginning) {

        try {
            Request request = new Request.Builder().url(jsonURL).build();
            Response response = Plugin.globalClient.newCall(request).execute();

            if (response.body() == null) throw new IOException("Response body is null.");

            String body = response.body().string();
            JSONObject jsonObject = (JSONObject) JSON.parse(body);
            String result = jsonObject.getString("content");

            Plugin.INSTANCE.getLogger().info(beginning);

            return (beginning + "\n--------------------\n" + result);

        } catch (IOException e) {
            Plugin.INSTANCE.getLogger().info(e);
            return null;
        }
    }
}
