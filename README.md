# yPlug功能简介

**小y助手：一个自用的机器人插件，集成了一些小功能，执行一些简单的命令**



| 功能名称   | 发送类型 | 配置名称    | 功能命令 |
| ---------- | -------- | ----------- | -------- |
| 新闻图片   | 图片     | NewsImg     | 新闻     |
| 更多新闻   | 图片     | Anews       | 更多     |
| 笑话       | 文字     | LaughTalk   | 笑话     |
| 一言语句   | 文字     | OneTalk     | 一言     |
| 舔狗日记   | 文字     | DogTalk     | 舔狗     |
| *毒鸡汤*   | 文字     | SoupTalk    | 鸡汤     |
| 猫咪图片   | 图片     | CatImg      | 猫咪     |
| *修狗图片* | 图片     | DogImg      | 修狗     |
| 古风图片   | 图片     | AncientImg  | 古风     |
| 摸鱼日历   | 图片     | CalendarImg | 摸鱼     |
| 美女图片   | 图片     | GirlImage   | 美女     |
| 二次元图片 | 图片     | PixivImg    | 二次元   |
| 功能列表   | 文字     | Help        | 功能     |

# 使用说明

## 安装插件

去`release`界面下载jar包，然后放置到`plugins`文件下即可**（最好先简单的配置一下yPluginConfig.yml）**

==注意== : [mirai-compose](https://github.com/sonder-joker/mirai-compose)因为mirai版本可能过旧，无法使用此插件，推荐使用[mcl](https://github.com/iTXTech/mirai-console-loader)

## 配置文件

**创建配置文件**

可以先在/config/yPlug.yplug的目录中，创建一个）

**说明**

为了避免打扰群友与好友的考虑，插件默认不对任何群启用功能(~~除非你的群正好命中了配置文件里默认生成的示例群号~~)

==注意==

如果想使命令生效：

需向`groupList`配置属性下添加对应的群号

需向`friendList`配置属性下添加对应的好友QQ号

**配置文件结构（里面有详细的注释，可以按照自己的喜好，自行修改）**

```yaml
# 开放群组
groupList: 
  - 123456789
  - 987654321

# 开放好友
friendList: 
  - 1234567
  - 7654321

# 图片存储路径
imageStorage: './data/image/yPlug/'

# 所有功能（true:开，false:关）
commandMap: 
  NewsImg: true           # 新闻图片
  Anews: true             # 更多新闻
  LaughTalk: true         # 笑话
  OneTalk: true           # 一言
  DogTalk: true           # 舔狗日记
  SoupTalk: true          # 毒鸡汤
  CatImg: true            # 猫咪图鉴
  DogImg: true            # 修狗图片
  AncientImg: true        # 古风图片
  CalendarImg: true       # 摸鱼日历
  CourseImage: true       # 课程图片
  GirlImage: true         # 美女图片
  PixivImg: true          # 二次元图片
  Author: true            # 作者信息
  Help: true              # 功能列表

# 获取图片的冷却时间
getRandImageCD: 10000

# 群发新闻任务 
# Open:（true为开，false为关闭）
# Cron:（cron表达式:设置定时任务【请自行百度】默认:'0 20 8 * * ?' ---每天早上8点20群发新闻）
# Zone:（时区设置:根据你所在的位置，可自行更换）
groupNewsJob: 
  Open: 'true'
  Cron: '0 20 8 * * ?'
  Zone: 'Asia/Shanghai'

# 群发摸鱼人日历 （每天中午12点群发日历）
groupCanlendarJob: 
  Open: 'true'
  Cron: '0 0 12 * * ?'
  Zone: 'Asia/Shanghai'
```
