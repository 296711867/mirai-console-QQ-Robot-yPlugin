package yplugin.Utils;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.Request;
import okhttp3.Response;
import yplugin.Plugin;
import yplugin.vo.ImgResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class DownloadUtilNew {

    @SneakyThrows
    public static Image downloadCatNew(GroupMessageEvent event){

        //1.发送Get请求先获取图片下载链接
        Request getImgAddrReq = new Request.Builder().get().url("https://api.thecatapi.com/v1/images/search").build();
        //获取返回结果
        Response imgAddrRes = Plugin.globalClient.newCall(getImgAddrReq).execute();
        //2.判空，并转换为数组对象
        if (Objects.isNull(imgAddrRes.body())){
            throw new IOException("图片下载链接获取失败！");
        }
        List<ImgResponseDTO> imtResList = JSONObject.parseArray(imgAddrRes.body().string(), ImgResponseDTO.class);
        if (CollectionUtil.isNotEmpty(imtResList)){
            //3.发送Get请求下载图片并返回
            ImgResponseDTO imgUrlRes = imtResList.get(0);
            Request imgReq = new Request.Builder().get().url(imgUrlRes.getUrl()).build();
            Response imgRes = Plugin.globalClient.newCall(imgReq).execute();
            if (Objects.isNull(imgRes.body())){
                throw new IOException("图片下载失败！");
            }
            ExternalResource res = ExternalResource.create(imgRes.body().bytes());
            Image img = event.getSubject().uploadImage(res);
            res.close();
            return img;
        }
        throw new IOException("图片下载失败！");
    }

}
