package com.fangxuele.ocs.common.util;

import com.fangxuele.ocs.common.constant.LolConstant;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class DictionaryUtil {

    /**
     * 上下架状态
     */
    public static final String OnOffSaleStatus = "lol.book.status";

    /**
     * 订单状态
     */
    public static final String OrderStatus = "lol.order.status";

    /**
     * 套餐类型
     */
    public static final String PromptType = "lol.prompt.type";

    /**
     * 取书状态
     */
    public static final String FetchStatus = "lol.fetch.status";

    /**
     * 送书状态
     */
    public static final String SendStatus = "lol.send.status";

    /**
     * 配送员状态
     */
    public static final String DispatcherStatus = "lol.dispatcher.status";

    /**
     * 索引/轮播图/频道/栏目等的状态
     */
    public static final String ChannelStatus = "lol.channel.status";

    /**
     * 首页书单栏目的类型
     */
    public static final String BookChannelType = "lol.book.channel.type";

    /**
     * 系统成员的状态
     */
    public static final String SYS_USER_STATUS = "lol.sys.user.status";

    /**
     * 系统公告的状态
     */
    public static final String SYS_NOTICE_STATUS = "lol.sys.notice.status";

    /**
     * 图书库存采购状态
     */
    public static final String BOOK_STORAGE_KEEP_STATUS = "lol.book.storage.keep.status";

    /**
     * 图书库存采购类型
     */
    public static final String BOOK_STORAGE_KEEP_TYPE = "lol.book.storage.keep.type";

    /**
     * 图书库存变更类型
     */
    public static final String BOOK_STORAGE_CHANGE_HIS_TYPE = "lol.book.storage.change.his.type";

    /**
     * 注册来源
     */
    public static final String R_FROM = "lol.fans.rfrom";

    /**
     * 用户性别
     */
    public static final String SEX = "lol.fans.sex";

    /**
     * 用户操作日志模块
     */
    public static final String OPERATE_LOG_MODULE = "lol.operate.log.module";

    /**
     * 订单类型
     */
    public static final String ORDER_TYPE = "lol.order.type";

    /**
     * 下单类型
     */
    public static final String ORDER_WAY = "lol.order.way";

    /**
     * 套餐到期类型
     */
    public static final String CUSTOMER_PROMPT_EXPIRE = "lol.customer.prompt.expire";

    /**
     * 预约还书类型
     */
    public static final String RETURN_APPOINT_TYPE = "lol.return.appoint.type";

    /**
     * 订单快递类型
     */
    public static final String ORDER_EXPRESS_TYPE = "lol.order.express.type";

    /**
     * 套餐/押金状态
     */
    public static final String PROMPT_STATUS = "lol.prompt.status";

    /**
     * 用户押金状态
     */
    public static final String CUSTOMER_DEPOSIT_STATUS = "lol.customer.deposit.status";

    /**
     * 用户信用状态
     */
    public static final String CUSTOMER_CREDIT_STATUS = "lol.customer.credit.status";

    /**
     * 扣除押金原因
     */
    public static final String DEPOSIT_DEDUCT_REASON = "lol.deposit.deduct.reason";

    /**
     * 图书评价来源
     */
    public static final String BOOK_COMMENT_SOURCE = "book.comment.source";

    /**
     * 活动状态
     */
    public static final String PROMOTION_STATUS = "lol.promotion.status";

    /**
     * 用户福袋状态
     */
    public static final String CUSTOMER_LUCKY_BAG_STATUS = "lol.customer.luckyBag.status";

    /**
     * 用户福袋来源
     */
    public static final String CUSTOMER_LUCKY_BAG_G_FROM = "lol.customer.luckyBag.gFrom";

    /**
     * 优惠券状态
     */
    public static final String COUPON_STATUS = "lol.coupon.status";

    /**
     * 优惠券类型
     */
    public static final String COUPON_TYPE = "lol.coupon.type";

    /**
     * 优惠券活动状态
     */
    public static final String PROMOTION_COUPON_STATUS = "lol.promotion.coupon.status";

    /**
     * 优惠券活动类型
     */
    public static final String PROMOTION_COUPON_TYPE = "lol.promotion.coupon.type";

    /**
     * 优惠券适用用户范围
     */
    public static final String COUPON_CUSTOMER_SCOPE = "lol.coupon.customer.scope";

    /**
     * 用户优惠券状态
     */
    public static final String CUSTOMER_COUPON_STATUS = "lol.customer.coupon.status";

    /**
     * 用户优惠券获得途径
     */
    public static final String CUSTOMER_COUPON_GFROM = "lol.customer.coupon.gFrom";

    /**
     * 采购订单商家平台
     */
    public static final String PURCHASE_ORDER_MERCHANT = "lol.purchase_order_merchant";

    /**
     * 采购订单有效状态
     */
    public static final String PURCHASE_ORDER_STATUS = "lol.purchase_order_status";

    /**
     * 用户海报邀请状态
     */
    public static final String POSTER_INVITATION_STATUS = "lol.customer.posterInvitation.status";

    /**
     * 套餐渠道销售购买渠道
     */
    public static final String PROMPT_CHANNEL_SALE_PURCHASE_CHANNEL = "lol.prompt.channelSale.purchaseChannel";

    /**
     * 套餐渠道销售激活状态
     */
    public static final String PROMPT_CHANNEL_SALE_ACTIVE_STATUS = "lol.prompt.channelSale.activeStatus";

    /**
     * 字典数据缓存容器
     */
    public static Map<String, Map<String, Map<Integer, String>>> statusClassMap = Maps.newHashMap();

    /**
     * name与code对应的数据字典缓存容器F
     */
    public static Map<String, Map<String, String>> nameCodeMap = Maps.newHashMap();

    public static String getString(String classCode, Integer value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        return getString(classCode, value, LolConstant.defaultLanguage);
    }

    public static String getString(String classCode, Integer value, String language) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        Map<String, Map<Integer, String>> codeMainMap = DictionaryUtil.statusClassMap.get(classCode);
        Map<Integer, String> codeMap = codeMainMap.get(language);
        return StringUtils.trimToEmpty(codeMap.get(value));
    }

    public static Map<Integer, String> getStatueMap(String classCode) {
        return getStatueMap(classCode, LolConstant.defaultLanguage);
    }

    public static Map<Integer, String> getStatueMap(String classCode, String language) {
        if (StringUtils.isEmpty(language)) {
            language = LolConstant.defaultLanguage;
        }
        Map<String, Map<Integer, String>> codeMap = DictionaryUtil.statusClassMap.get(classCode);
        return codeMap.get(language);
    }

    public static void clear() {
        statusClassMap = null;
        nameCodeMap = null;
    }

    public static Map<String, String> getOneNameCodeMap(String classCode) {
        return nameCodeMap.get(classCode);
    }

    public static String getName(String classCode, String code) {
        if (StringUtils.isEmpty(code)) {
            return StringUtils.EMPTY;
        }
        return nameCodeMap.get(classCode).get(code);
    }
}