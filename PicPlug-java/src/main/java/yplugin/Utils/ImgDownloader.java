package yplugin.Utils;

import okhttp3.Request;
import okhttp3.Response;
import yplugin.Plugin;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImgDownloader {
    /**
     * @param url 图片下载地址
     * @param imagePath 图片存放地址
     * @param id task id
     * @return file name
     * @throws IOException 下载错误或文件读写错误
     */
    public static String download(URL url, String imagePath, int id) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + id + ".jpg";
        InputStream in = url.openConnection().getInputStream();
        FileOutputStream out = new FileOutputStream(imagePath +  fileName);
        for (int imgByte; (imgByte = in.read()) != -1; ) {
            out.write(imgByte);
        }
        in.close();
        out.close();

        Plugin.INSTANCE.getLogger().info("图片下载成功！");
        return fileName;
    }
    public static String download(String url, String imagePath) throws IOException {
        return download(new URL(url),imagePath,0);
    }


//    public static ExternalResource downloadAndParse(String jsonURL,
//                                                    String jsonKey,
//                                                    Function<String, JSONObject> getObjectFunction) throws IOException {
//        Plugin.INSTANCE.getLogger().info(jsonURL);
//
//        Request request = new Request.Builder().url(jsonURL).build();
//        Response response = Plugin.globalClient.newCall(request).execute();
//
//        if (response.body() == null) throw new IOException("Response body is null.");
//        JSONObject jsonObject = getObjectFunction.apply(response.body().string());
//
//        Request imageRequest = new Request.Builder().url(jsonObject.getString(jsonKey)).build();
//        Response imageResponse = Plugin.globalClient.newCall(imageRequest).execute();
//
//        if (imageResponse.body() == null) throw new IOException("Response body is null.");
//
//        Plugin.INSTANCE.getLogger().info("图片下载成功!");
//
//        return ExternalResource.create(imageResponse.body().bytes());
//    }




    public static String downloadAndParse (String jsonURL, int index) throws IOException
    {

        Request request = new Request.Builder().url(jsonURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();

        Plugin.INSTANCE.getLogger().info("获取成功！");

        if (response.body() == null) throw new IOException("Response body is null.");

        // 该方法只可以使用一次！！ response.body().string()
        String[] strs = response.body().string().split("\"");

//        for (String s : strs){
//            Plugin.INSTANCE.getLogger().info(s);
//        }

//        Plugin.INSTANCE.getLogger().info(strs[7]);

        if (strs[index].isEmpty()){
            return "没有获取到url";
        }else {
            Plugin.INSTANCE.getLogger().info(strs[index]);
            return strs[index];
        }

    }


}
