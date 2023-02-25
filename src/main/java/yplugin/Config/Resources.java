package yplugin.Config;

public class Resources {
    public static final String NAME_CN = "小y助手";
    public static final String NAME = "yPlug";
    public static final String AUTHOR = "pan";
//    public static final Long QQ = 296711867L;
    public static final String ID = "yPlug."+NAME.toLowerCase();
    public static final String VERSION = "1.0.1";
    public static final String DESCRIPTION = "小y助手";
    public static final String IMG_STORAGE_PATH = "./data/image/"+NAME+"/";

    public static final Long LOCK_TIME = 10000L;
    public static final Long LOCK_UID = 1L;

    // 请求API

    // 摸鱼人日历
    public static final String CALENDAR_API = "https://api.vvhan.com/api/moyu?type=json";

    // 新闻图片
    public static final String NEWS_PICTURE_API = "http://bjb.yunwj.top/php/tp/60.jpg";
    // 新闻内容
    public static final String NEWS_CONTENT_API = "https://www.zhihu.com/people/mt36501";
    // 历史上的今天
    public static final String HISTORY_API = "http://bjb.yunwj.top/php/ls/ls.php";
    // b站番剧排行
    public static final String CARTOON_API = "https://api.bilibili.com/pgc/web/rank/list?season_type=1&day=3";
    // b站首页排行
    public static final String INTERFACE_API = "https://api.bilibili.com/x/web-interface/ranking/region?rid=1&day=3&original=0";

    // 美女
    public static final String PEAUTY_API = "https://imgapi.cn/cos.php?return=img";
    // 二次元
    public static final String PIXIV_API_FIR = "https://www.uukey.cn/pay/dm/api";
    public static final String PIXIV_API_SEC = "https://www.uukey.cn/pay/ecy/api";
    // 古风
    public static final String ANCIENT_API = "https://www.uukey.cn/pay/gf/api";

    // 狗狗
    public static final String DOG_API = "https://random.dog/woof.json";
    // 猫咪
    public static final String CAT_API = "https://api.thecatapi.com/v1/images/search";

    // 舔狗日记
    public static final String DOG_TALK_API = "https://api.likepoems.com/ana/lickdog";
    // 笑话
    public static final String LAUGH_TALK_API = "https://api.andeer.top/API/joke.php";
    // 一言
    public static final String ONE_TALK_API = "https://api.likepoems.com/ana/yiyan";
    // 鸡汤
    public static final String SOUP_TALK_API = "https://api.likepoems.com/ana/dujitang";

    // 饥荒房间详情
    public static final String DST_DETAILS_API = "https://cdn.seovx.com/ha/?mom=302";

//    public static final String IMG_DOG_PATH = "./data/image/dog/";



}
