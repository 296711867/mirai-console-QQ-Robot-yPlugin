package yplugin.Utils;

import io.ktor.http.ContentType;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Config.Config;
import yplugin.Plugin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;

import java.io.IOException;
import java.util.Objects;
import java.io.*;
import java.net.URL;
import java.util.function.Function;


public class Adownload {

    /**
     * @Title: 根据 URL 下载资源
     * @param url 下载路径
     * @return ExternalResource 资源
     * @throws IOException 异常处理
     */
    public static ExternalResource getdownloadResource(String url) throws IOException {

        Request imageRequest = new Request.Builder().url(url).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");
        Plugin.INSTANCE.getLogger().info("资源下载成功!");

        return ExternalResource.create(imageResponse.body().bytes());
    }
    /**
     * @Title: 获取URL
     * @param url 下载路径
     * @return ExternalResource 资源
     * @throws IOException
     */
    /**
     * @Title: 获取URL （使用麻烦）
     * @param url 下载路径
     * @param ArrayIndex 数组索引
     * @return URL
     * @throws IOException
     */
    public static String getArrayUrl(String url, int ArrayIndex) throws IOException {
        //1.发送Get请求先获取图片下载链接
        Request imageRequest = new Request.Builder().url(url).build();
        Response imgAddrRes = Plugin.globalClient.newCall(imageRequest).execute();

        //2.判空，并转换为数组对象
        if (Objects.isNull(imgAddrRes.body())){
            throw new IOException("图片下载链接获取失败！");
        }

        JSONArray array = (JSONArray) JSON.parse(imgAddrRes.body().toString());
        com.alibaba.fastjson.JSONObject jsonObject = array.getJSONObject(ArrayIndex);
        //3.返回图片URL
        return jsonObject.getString("url");
    }

    /**
     * @Title: 根据索引获取URL （不建议使用）
     * @param jsonURL 解析地址
     * @param index 索引
     * @return url 目标地址
     * @throws IOException 异常
     */
    public static String getUrlIndex(String jsonURL, int index) throws IOException
    {

        Request request = new Request.Builder().url(jsonURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();

        Plugin.INSTANCE.getLogger().info("获取成功！");

        if (response.body() == null) throw new IOException("Response body is null.");

        // 该方法只可以使用一次！！ response.body().string()
        String[] strs = response.body().string().split("\"");
        String url;

        if (strs[index].isEmpty()){
            throw new IOException("图片下载链接获取失败！");
        }else {
            Plugin.INSTANCE.getLogger().info(strs[index]);
            return strs[index];
        }

    }
//---------------------------------------------------------图片下载到本地
    /**
     * @param picURL 图片下载地址
     * @return fileName 文件名
     * @throws IOException 下载错误或文件读写错误
     */
    public static String downloadPicture(String picURL) throws IOException {
        URL url = new URL(picURL);
        String fileName = System.currentTimeMillis() + ".jpg";
        InputStream in = url.openConnection().getInputStream();

        FileOutputStream out = new FileOutputStream(Config.INSTANCE.getImageStorage() +  fileName);
        for (int imgByte; (imgByte = in.read()) != -1; ) {
            out.write(imgByte);
            out.flush();
        }
        in.close();
        out.close();

        Plugin.INSTANCE.getLogger().info("图片下载成功！");
        return fileName;
    }

    /**
     * @Title: downloadPicture 根据 URL 下载图片 （方法2）
     * @Description: 链接url下载图片到服务器
     * @param picURL 图片链接地址，如：https://game.gtimg.cn/images/lol/act/img/rune/Predator.png
     */
    public static void downloadPictureToServer(String picURL) {

        try {
            URL url = new URL(picURL);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            String fileName = Config.INSTANCE.getImageStorage() + System.currentTimeMillis() + ".jpg";
            File file = new File(fileName);

            if(!file.exists()){
                file.getParentFile().mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: downloadAndParse 根据 URL 下载图片 （方法3）
     * @Description: 从response中获取jsonKey中的URL
     * @param jsonURL
     * @param jsonKey
     * @param getObjectFunction
     * @return
     * @throws IOException
     */
    public static ExternalResource downloadAndParse(String jsonURL,
                                                    String jsonKey,
                                                    Function<String, org.json.JSONObject> getObjectFunction) throws IOException {
        // 1、获取图鉴下载链接
        Request request = new Request.Builder().url(jsonURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();

        if (response.body() == null) throw new IOException("Response body is null.");
        org.json.JSONObject jsonObject = getObjectFunction.apply(response.body().string());

        // 2、下载图片
        Request imageRequest = new Request.Builder().url(jsonObject.getString(jsonKey).replace("\\","")).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");

        Plugin.INSTANCE.getLogger().info("图片下载成功!");

        return ExternalResource.create(imageResponse.body().bytes());
    }

    public static ExternalResource downloadAndParse(String jsonURL, String jsonKey) throws IOException {
        return downloadAndParse(jsonURL, jsonKey, org.json.JSONObject::new);
    }


    /**
     * @Title: 获取URL的下载图片，并发送消息
     * @param url 下载路径
     * @return ExternalResource 资源
     * @throws IOException 异常处理
     */
    public static void sendGroupResource(String url, GroupMessageEvent event) throws IOException {

        Request imageRequest = new Request.Builder().url(url).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");
        Plugin.INSTANCE.getLogger().info("资源下载成功!");

        ExternalResource externalResource = ExternalResource.create(imageResponse.body().bytes());
        Image image = event.getGroup().uploadImage(externalResource);
        event.getGroup().sendMessage(image);

        externalResource.close();
    }

    /**
     * @Title: 获取URL的下载图片，并发送消息
     * @param url 下载路径
     * @return ExternalResource 资源
     * @throws IOException 异常处理
     */
    public static void sendFriendResource(String url, FriendAddEvent event) throws IOException {

        Request imageRequest = new Request.Builder().url(url).build();
        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();

        if (imageResponse.body() == null) throw new IOException("Response body is null.");
        Plugin.INSTANCE.getLogger().info("资源下载成功!");

        ExternalResource externalResource = ExternalResource.create(imageResponse.body().bytes());
        Image image = event.getFriend().uploadImage(externalResource);
        event.getFriend().sendMessage(image);

        externalResource.close();
    }


}
