package com.fangxuele.ocs.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public class ConfigurationUtil {

    /**
     * 微信注册用户默认初始密码
     */
    public static final String INIT_PASSWORD = "InitPassword";

    /**
     * 微信注册用户默认初始头像
     */
    public static final String DEFAULT_HEAD_IMG_URL = "DefaultHeadImgUrl";

    /**
     * 微信关注默认自动回复消息
     */
    public static final String WX_SUBSCRIBE_DEFAULT_MSG = "WxSubscribeDefaultMsg";

    /**
     * LOMS注册邀请码
     */
    public static final String LOMS_INVITE_CODE = "LomsInviteCode";

    /**
     * 员工/店员/领导openid
     */
    public static final String CLERK_OPENIDS = "ClerkOpenids";

    /**
     * 免押金信用分最低标准
     */
    public static final String DEPOSIT_FREE_SCORE_LEVEL = "DepositFreeScoreLevel";

    /**
     * 福袋活动白名单开关
     */
    public static final String LUCKY_BAG_WHITE_LIST = "LuckyBagWhiteList";

    /**
     * 圆通快递是否下单开关
     */
    public static final String YT_EXPRESS_SWITCH = "YtExpressSwitch";

    /**
     * H5缓存版本
     */
    public static final String H5_CACHE_VERSION = "H5CacheVersion";

    /**
     * 海报模板图片路径
     */
    public static final String POSTER_TEMPLATE_URL = "PosterTemplateUrl";

    public static Map<String, String> propertiesMap = new HashMap<>();

}
