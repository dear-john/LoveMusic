package app;


public class CommonApis {
    //    后接歌单种类
    public static final String MUSIC_LIST_API = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.billboard.billList&format=json&type=";

    /*
        type: 1、新歌榜，2、热歌榜，11、摇滚榜，12、爵士，16、流行
        21、欧美金曲榜，22、经典老歌榜，23、情歌对唱榜，24、影视金曲榜，25、网络歌曲榜
    */
    public static final int[] MUSIC_TYPE = {1, 2, 11, 12, 16, 21, 22, 23, 24, 25};

    //    后接查询字符串
    public static final String MUSIC_SEARCH_API = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&query=";

    private static final String BLOG_LINK = "https://fddcn.cn/music-api-wang-yi-bai-du.html";
}
