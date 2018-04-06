package app;


import com.spring_ballet.lovemusic.R;

public class AppConstants {

    /*
    * type: 1、新歌榜，2、热歌榜，6、KTV热歌，11、摇滚榜，12、爵士，16、流行
    * 21、欧美金曲榜，22、经典老歌榜，23、情歌对唱榜，24、影视金曲榜，25、网络歌曲榜
     */

    //博客链接
    //   https://fddcn.cn/music-api-wang-yi-bai-du.html

    //    后接歌单种类
    public static final String MUSIC_LIST_API = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.billboard.billList&format=json&type=";

    //    后接查询字符串
    public static final String MUSIC_SEARCH_API = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.search.catalogSug&format=json&query=";

    //获取歌手歌曲列表,后接ting_uid
    public static final String SINGER_MUSIC_LIST = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.artist.getSongList&format=json&tinguid=";

    //获取歌手信息,后接ting_uid
    public static final String SINGER_INFO_API = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.artist.getInfo&format=json&tinguid=";

    //音乐播放链接，后接songid
    public static final String PLAY_MUSIC_LINK = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=webapp_music&method=baidu.ting.song.play&songid=";

    //列表播放模式
    public static final String[] PLAY_MODE_NAME = {"列表循环", "随机播放", "单曲循环"};

    public static final int[] PLAY_MODE_ICON = {R.drawable.play_icon_loop, R.drawable.play_icon_random, R.drawable.play_icon_one};

    public static final int PLAY_MODE_NUMBER = PLAY_MODE_NAME.length;

}
