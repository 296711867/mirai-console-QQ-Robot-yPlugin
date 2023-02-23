package yplugin.Utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import yplugin.Plugin;

import java.io.IOException;
import java.util.function.Function;

public class DownloadUtil {

    public static ExternalResource downloadCat() throws IOException {

        //链式构建请求
        String result = HttpRequest
                .get("https://api.thecatapi.com/v1/images/search")
                .timeout(20000)//超时，毫秒
                .execute().body();
//        String result = HttpRequest
//                .get("https://api.thecatapi.com/v1/images/search")
//                .header("x-api-key", "x-api-key")
//                .timeout(20000)//超时，毫秒
//                .execute().body();

        JSONArray array = (JSONArray) JSON.parse(result);

        com.alibaba.fastjson.JSONObject jsonObject = array.getJSONObject(0);

        String url = jsonObject.getString("url");

        Request imageRequest = new Request.Builder().url(url).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");

        Plugin.INSTANCE.getLogger().info("图片下载成功!");

        return ExternalResource.create(imageResponse.body().bytes());
    }

    public static ExternalResource downloadAndParse(String jsonURL,
                                                    String jsonKey,
                                                    Function<String, JSONObject> getObjectFunction) throws IOException {
        Request request = new Request.Builder().url(jsonURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();


        if (response.body() == null) throw new IOException("Response body is null.");
        JSONObject jsonObject = getObjectFunction.apply(response.body().string());

        Request imageRequest = new Request.Builder().url(jsonObject.getString(jsonKey)).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");

        Plugin.INSTANCE.getLogger().info("图片下载成功!");

        return ExternalResource.create(imageResponse.body().bytes());
    }

    public static ExternalResource downloadAndParse(String jsonURL, String jsonKey) throws IOException {
        return downloadAndParse(jsonURL, jsonKey, JSONObject::new);
    }

    public static ExternalResource downloadAndConvert(String imageURL) throws IOException {
        Request request = new Request.Builder().url(imageURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();

        if (response.body() == null) throw new IOException("Response body is null.");

        return ExternalResource.create(response.body().bytes());
    }
}
