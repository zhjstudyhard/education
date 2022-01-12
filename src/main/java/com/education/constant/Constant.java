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
    //-1
    public static final String NUMBER_NEGATIVE_ONE = "-1";
    //0
    public static final Integer NUMBER_ZERO = 0;
    //1
    public static final Integer NUMBER_ONE = 1;
    //刷新令牌redis的key
    public final static String SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    //标志时间戳
    public final static String CURREN_TIIME_MILLIS = "currentTimeMillis";
    //redis文章周榜缓存的key
    public final static String REDIS_ARTICE_KEY = "ARTICE:WEEK:RANK";
    //redis缓存文章数据
    public final static String REDIS_ARTICE_CACHE = "ARTICE:CACHE";
    //评论回答通知
    public final static String MESSAGE_MESSAGE_ANSWER = "MESSAGE_MESSAGE_ANSWER";
    //评论通知
    public final static String MESSAGE_COMMENT = "MESSAGE_COMMENT";
    //系统通知
    public final static String MESSAGE_SYSTEM = "MESSAGE_SYSTEM";
    //es文章索引
    public final static String ARTICLE_INDEX = "article";
    //普通用户角色
    public final static String ROLE_USER = "user";

}
