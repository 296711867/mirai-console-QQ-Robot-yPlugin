package yplugin.Config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value


object Config : AutoSavePluginConfig("${Resources.NAME}Config") {

    // 群发配置
    var groupList: List<Long> by value(
        arrayListOf(620615452L, 456906519L, 133808502L, 399742302L, 318251170L, 1035459278L))

    var friendList: List<Long> by value(
        arrayListOf(296711867L, 2779072927L, 3110985822L))

    // 图片存储地方
    var imageStorage: String by value(Resources.IMG_STORAGE_PATH)



    // Command Switch Config
    var commandMap: Map<String,Boolean> by value(mapOf(
        "NewsImg" to true, "Anews" to true,
        "LaughTalk" to true,"OneTalk" to true,"DogTalk" to true,"SoupTalk" to true,
        "CatImg" to true,"DogImg" to true,"AncientImg" to true,
        "CalendarImg" to true, "CourseImage" to true,
        "GirlImage" to true, "PixivImg" to true,
        "Author" to true, "Help" to true,
    ))


    // Customized Setting for Command
    var getRandImageCD: Int by value(10000)


    // 群发新闻任务
    var groupNewsJob: Map<String,String> by value(mapOf(
        "Open" to "true",
        "Cron" to "0 20 8 * * ?",
        "Zone" to "Asia/Shanghai",
    ))
    // 群发摸鱼人日历
    var groupCanlendarJob: Map<String,String> by value(mapOf(
        "Open" to "true",
        "Cron" to "0 0 12 * * ?",
        "Zone" to "Asia/Shanghai",
    ))


    //    var cmd: List<String> by value(arrayListOf("calendar", "img"))
//    var calendar: Boolean by value(Resources.cl)
//    var img: Boolean by value(Resources.img)
}