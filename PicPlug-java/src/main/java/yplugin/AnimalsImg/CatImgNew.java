package yplugin.AnimalsImg;

import lombok.SneakyThrows;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import yplugin.Commands.PureCommand;
import yplugin.Plugin;
import yplugin.Utils.Cooler;
import yplugin.Utils.DownloadUtil;
import yplugin.Utils.DownloadUtilNew;

import java.io.InterruptedIOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CatImgNew extends PureCommand {

    public CatImgNew(String name) {
        super(name);
    }

    @Override
    public String info() {
        return super.info() + " -> 发送修勾图片";
    }

    @Override
    @SneakyThrows
    public void onCommand(GroupMessageEvent event) {

        long uid = event.getSender().getId();
        if (Cooler.isLocked(uid)) {
            event.getSubject().sendMessage("冷却中，请待会再试！>_<");
            return;
        }

        Cooler.lock(uid, 3000L);
        CompletableFuture.supplyAsync(() -> DownloadUtilNew.downloadCatNew(event), Plugin.downloadExecutorService)
                .whenComplete((img, exp) -> {
                    if (Objects.nonNull(exp)) {
                        Plugin.INSTANCE.getLogger().info(exp.getMessage());
                        event.getSubject().sendMessage("猫咪迷路了,请稍后再试!\n");
                    }
                    event.getSubject().sendMessage(img);
                });
    }


}
