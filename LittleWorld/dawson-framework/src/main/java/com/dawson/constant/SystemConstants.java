package com.dawson.constant;


//常量类，就是用来代替明示参数的
public class SystemConstants
{
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;


    public static final String  STATUS_NORMAL = "0";
    /**
     * 友链状态为审核通过
     */
    public static final String  LINK_STATUS_NORMAL = "0";
    /**
     * 评论类型为：文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为：友联评论
     */
    public static final String LINK_COMMENT = "1";
    public static final String MENU = "C";
    public static final String BUTTON = "F";
    /** 正常状态 */
    public static final String NORMAL = "0";
    public static final String ADMAIN = "1";

    //文章阅读次数的redis的Key
    public static final String ARTICLE_UPDATE = "article:viewCount";
    //将阅读量往数据库里面写的时间
    public static final String UPDATE_VIEW_CORN = "* 0/59 * * * ?";
}