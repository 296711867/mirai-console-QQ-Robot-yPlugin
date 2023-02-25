# yPlug

一个自用的机器人插件

## 使用说明

**安装**

去`release`界面下载jar包，然后放置到`plugins`文件下即可

==注意== : [mirai-compose](https://github.com/sonder-joker/mirai-compose)因为mirai版本可能过旧，无法使用此插件，推荐使用[mcl](https://github.com/iTXTech/mirai-console-loader)

**说明**

出于避免打扰群友的考虑，插件默认不对任何群启用功能(~~除非你的群正好命中了配置文件里默认生成的示例群号~~), 启用插件需向`groupList`配置属性下添加对应的群号

**配置文件结构**

```yaml
# 插件生效的群列表
groupList: 
  - 1234567890
  - 9876543210
  
# 插件的指令前缀，仅此前缀开头的聊天信息会被识别为命令 默认无
commandPrefix: ''

# 插件下载的图片储存位置
imageStorage: './data/image/PicPlug/'

# 获取图片的cd时间，单位ms
getRandImageCD: 0
```
