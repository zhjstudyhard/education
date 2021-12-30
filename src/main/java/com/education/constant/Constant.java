package com.education.constant;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-11-22-14-06
 */
public class Constant {
    //未删除
    public static final int ISDELETED_FALSE = 0;
    //已删除
    public static final int ISDELETED_TRUE = 1;
    //刷新令牌redis的key
    public final static String SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    //标志时间戳
    public final static String CURREN_TIIME_MILLIS = "currentTimeMillis";
    //redis文章周榜缓存的key
    public final static String REDIS_ARTICE_KEY = "ARTICE:WEEK:RANK";
    //redis缓存文章数据
    public final static String REDIS_ARTICE_CACHE = "ARTICE:CACHE";
}
