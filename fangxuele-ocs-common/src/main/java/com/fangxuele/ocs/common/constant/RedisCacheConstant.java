package com.fangxuele.ocs.common.constant;

public interface RedisCacheConstant {

    /**
     * 缓存KEY的分隔符
     */
    String KeySeparator = ":";

    /**
     * 有序集合的顺序表示，升序，是递增的顺序
     */
    int ASC = 0;

    /**
     * 有序集合的顺序表示，降序，是递减的顺序
     */
    int DESC = 1;

    /**
     * 缓存数据空值标识，可以避免数据库不存在的数据在缓存中不命中而重复查询数据库
     */
    String DB_NULL_VALUE = "FXL*DB*NULL";

    /**
     * 全局配置参数（hash）
     */
    String CONFIG_PROP_KEY = "Configuration";

    /**
     * Token映射客户ID信息缓存Key（String结构）
     */
    String TokenToId = "TokenToId";

    /**
     * openid映射客户ID信息缓存Key（String结构）
     */
    String OpenidToId = "OpenidToId";

    /**
     * unionid映射客户ID信息缓存Key（String结构）
     */
    String UnionidToId = "UnionidToId";

    /**
     * 客户实体信息缓存KEY（Hash结构）
     */
    String CustomerEntity = "CustomerEntity";

    /**
     * 配送员实体信息缓存KEY（Hash结构）
     */
    String DispatcherEntity = "DispatcherEntity";

    /**
     * 订单实体信息缓存KEY（Hash结构）
     */
    String OrderEntity = "OrderEntity";

    /**
     * 主页搜索框热搜词缓存KEY（List结构）
     */
    String HotSearchWords = "HotSearchWords";

    /**
     * 用户借阅车缓存KEY（ZSET结构）
     */
    String ShoppingCart = "ShoppingCart";

    /**
     * 用户收藏缓存KEY（ZSET结构）
     */
    String CollectionList = "CollectionList";

    /**
     * 订单编号流水号缓存Key（SerialNumber的Hash结构的Key值）
     */
    String OrderNum = "OrderNum";

    /**
     * 押金(套餐)订单编号流水号缓存Key（SerialNumber的Hash结构的Key值）
     */
    String DepositOrderNum = "DepositOrderNum";

    /**
     * 阿里芝麻信用积分TransactionId流水号缓存Key（SerialNumber的Hash结构的Key值）
     */
    String AliZmTransactionIdNum = "AliZmTransactionIdNum";

    /**
     * 圆通物流号txLogisticID缓存Key（SerialNumber的Hash结构的Key值）
     */
    String YtTxLogisticIdNum = "YtTxLogisticIdNum";

    /**
     * 系统各种流水号缓存KEY（Hash结构）
     */
    String SerialNumber = "SerialNumber";

    /**
     * 云朵坐标缓存KEY（hash结构）
     */
    String CloudGPS = "CloudGPS";

    /**
     * 云朵表缓存大KEY（hash结构）
     */
    String CloudEntity = "CloudEntity";

    /**
     * 七牛图片服务活动图片Token缓存Key 空间名为 fxleventimage（String结构）
     */
    String QnEventImageToken = "QnEventImageToken";

    /**
     * 订单号对应订单详情图书id缓存KEY (list结构)
     */
    String OrderDetailBookIds = "OrderDetailBookIds";

    /**
     * 用户借阅中图书id缓存KEY (list结构)
     */
    String BorrowingBookIds = "BorrowingBookIds";

    /**
     * 快递员配送时间缓存KEY (hash结构)
     */
    String DispatchTime = "DispatchTime";

    /**
     * 猜你喜欢图书id缓存KEY (string结构)
     */
    String GuessLike = "GuessLike";

    /**
     * 微信自动回复配置缓存大Key（Hash结构）
     */
    String WxAutoReply = "WxAutoReply";

    /**
     * 防止快速重复提交缓存Key（String结构）
     */

    String LolPreventDuplicateSubmissions = "LolPDS";

    /**
     * 记录用户访问接口访问次数缓存Key (Hash结构)
     */

    String RecordInterfaceAccessCount = "RecordInterfaceAccessCount";

    /**
     * 记录搜索排行缓存Key (zset结构)
     */
    String SearchLog = "SearchLog";

    /**
     * 单次体验卡分渠道场景码 (set结构)
     */
    String OnceCardSceneCode = "OnceCardSceneCode";

    /**
     * 关注和扫码事件排除重复提交缓存Key（String结构）
     */
    String SubscribeAndScanExcludeDuplicateSubmissions = "SSEDS";

    /**
     * 支付回调事件排除重复提交缓存Key（String结构）
     */
    String PayConfirmExcludeDuplicateSubmissions = "PEDS";

    /**
     * 防止首页恶意搜索缓存key(string结构)
     */
    String PreventRepeatSearch = "PreventRepeatSearch";

    /**
     * 用户套餐缓存大key（hash结构）
     */
    String CustomerPrompt = "CustomerPrompt";

    /**
     * 促销活动大key（string）
     */
    String PROMOTION = "Promotion";

    /**
     * 福袋活动全局配置大key（hash结构）
     */
    String LUCKY_BAG = "LuckyBag";

    /**
     * 用户福袋全局配置大key（hash结构）
     */
    String CUSTOMER_LUCKY_BAG = "CustomerLuckyBag";

    /**
     * 福袋点数顺序列表（List结构）
     */
    String LUCKY_BAG_POINT_LIST = "LuckyBagPointList";

    /**
     * 云宝员工openid名单（List结构）
     */
    String EMP_OPENID = "EmpOpenid";

    /**
     * 图书关键词名单（List结构）
     */
    String BOOK_KEYWORD = "BookKeyword";

    /**
     * 图书详情七牛图片缓存大key（Set结构）
     */
    String BOOK_DETAIL_QINIU_IMG = "BookDetailQiniuImg";

    /**
     * 七牛垃圾图片缓存（Set结构）
     */
    String QINIU_WASTE_IMG = "QiniuWasteImg";

    /**
     * 推送跳转(hash结构)
     */
    String PUSH_REDIRECT = "PushRedirect";

    /**
     * 客户海报临时微信场景二维码流水号缓存Key（SerialNumber的Hash结构的Key值）
     */
    String WxCustomerSceneNum = "WxCustomerSceneNum";

    /**
     * 短信验证码大key
     */
    String SMS_VERIFY_CODE = "SmsVerifyCode";

    /**
     * 套餐渠道销售对应关系map(hash结构)
     */
    String PROMPT_CHANNEL_SALE_VALUE_MAP = "PromptChannelSaleValueMap";

    /*
     * 跳转微信链接的跳转地址Key（Hash结构）
     */
    String TO_WX_LINK_MAP = "ToWxLinkMap";

    /*
     * 跳转微信链接的跳转地址Ticket（String结构）
     */
    String TO_WX_LINK_TICKET = "ToWxLinkTicket";
}