package com.fangxuele.ocs.common.dictionary;

/**
 * 系统相关常量字典
 * Created by wfc on 2017/7/7.
 */
public interface SystemDictionary {

    /**
     * 系统成员状态：启用
     */
    byte SYS_USER_ENABLE_STATUS = 1;

    /**
     * 系统成员状态：禁用
     */
    byte SYS_USER_DISABLE_STATUS = 0;

    /**
     * 系统公告状态：编辑中
     */
    byte SYS_NOTICE_EDITING_STATUS = 10;

    /**
     * 系统公告状态：启用/显示
     */
    byte SYS_NOTICE_ENABLE_STATUS = 20;

    /**
     * 系统公告状态：禁用
     */
    byte SYS_NOTICE_DISABLE_STATUS = 30;

    /**
     * 系统公告状态：删除
     */
    byte SYS_NOTICE_DELETE_STATUS = 99;

}
