package yplugin.Utils;

import okhttp3.Request;
import okhttp3.Response;
import yplugin.Plugin;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImgDownloadOld {
    /**
     * @param url 图片下载地址
     * @param imagePath 图片存放地址
     * @param id 昵称ID
     * @return fileName 文件名
     * @throws IOException 下载错误或文件读写错误
     */
    public static String download(URL url, String imagePath, int id) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + id + ".jpg";
        InputStream in = url.openConnection().getInputStream();
        FileOutputStream out = new FileOutputStream(imagePath +  fileName);
        for (int imgByte; (imgByte = in.read()) != -1; ) {
            out.write(imgByte);
            out.flush();
        }
        in.close();
        out.close();

        Plugin.INSTANCE.getLogger().info("图片下载成功！");
        return fileName;
    }
    public static String download(String url, String imagePath) throws IOException {
        return download(new URL(url),imagePath,0);
    }

    public static String downloadAndParse (String jsonURL, int index) throws IOException
    {

        Request request = new Request.Builder().url(jsonURL).build();
        Response response = Plugin.globalClient.newCall(request).execute();
        Plugin.INSTANCE.getLogger().info("获取成功！");
        if (response.body() == null) throw new IOException("Response body is null.");
        // 该方法只可以使用一次！！ response.body().string()
        String[] strs = response.body().string().split("\"");

        if (strs[index].isEmpty()){
            return "没有获取到url";
        }else {
            Plugin.INSTANCE.getLogger().info(strs[index]);
            return strs[index];
        }
    }


}
