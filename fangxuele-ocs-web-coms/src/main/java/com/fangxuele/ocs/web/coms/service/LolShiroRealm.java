package com.fangxuele.ocs.web.coms.service;

import com.fangxuele.ocs.common.bean.loms.SysMenu;
import com.fangxuele.ocs.common.constant.SecurityConstant;
import com.fangxuele.ocs.mapper.domain.TLogInfo;
import com.fangxuele.ocs.mapper.domain.TSysPermission;
import com.fangxuele.ocs.mapper.domain.TSysRole;
import com.fangxuele.ocs.mapper.domain.TSysUser;
import com.fangxuele.ocs.mapper.mapper.TLogInfoMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.text.EncodeUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 身份校验核心类
 *
 * @author rememberber(https : / / github.com / rememberber)
 */
public class LolShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TLogInfoMapper logInfoMapper;

    /**
     * 给ShiroDbRealm提供编码信息，用于密码密码比对 描述
     */
    public LolShiroRealm() {
        super();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SecurityConstant.ALGORITHM);
        matcher.setHashIterations(SecurityConstant.INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 认证信息.(身份验证)
     * Authentication 是用来验证用户身份
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();

        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        TSysUser sysUser = sysUserService.getSysUserInfoByUserName(username);
        if (sysUser == null) {
            return null;
        }

        if (sysUser.getStatus() != 1) {
            throw new DisabledAccountException();
        }

        /*
         * 获取权限信息:这里没有进行实现，
         * 请自行根据UserInfo,Role,Permission进行实现；
         * 获取之后可以在前端for循环显示所有链接;
         */
        //userInfo.setPermissions(userService.findPermissions(user));


        //账号判断;

        //加密方式;
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        byte[] salt = EncodeUtil.decodeHex(sysUser.getSalt());
        ShiroUser shiroUser = new ShiroUser(sysUser.getId(), sysUser.getUserName(), sysUser.getNickName());
        shiroUser.setTheme(sysUser.getTheme());

        List<TSysRole> list = sysUserService.getSysRoleByUserId(sysUser.getId());
        StringBuilder roleStringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            roleStringBuilder.append(list.get(i).getRole());
            if (i < list.size() - 1) {
                roleStringBuilder.append("/");
            }
        }
        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        shiroUser.setIpAddress(authcToken.getHost());
        shiroUser.setRole(roleStringBuilder.toString());
        shiroUser.setHeadImage(sysUser.getHeadImage());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                shiroUser, //用户
                sysUser.getPassword(), //密码
                ByteSource.Util.bytes(salt),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();

        TSysUser sysUser = sysUserService.getSysUserInfoByUserName(shiroUser.loginName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 将用户的角色名称保存到角色信息中
        Collection<String> hasRoles = new HashSet<>();
        List<TSysRole> list = sysUserService.getSysRoleByUserId(sysUser.getId());
        for (TSysRole role : list) {
            hasRoles.add(role.getRole());
        }
        info.addRoles(hasRoles);
        // 将用户的权限保存到权限信息中
        List<String> permissions = sysUserService.getAllPermissionsByUserId(sysUser.getId());
        info.addStringPermissions(permissions);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        if (session.getAttribute("menu") == null) {
            List<SysMenu> menuList = sysUserService.getMainMenu(shiroUser.getId());
            session.setAttribute("menu", menuList);
        }
        session.setAttribute("headImage", sysUser.getHeadImage());
        session.setAttribute("theme", sysUser.getTheme());

        session.setTimeout(7200000);

        // 记录登录操作日志
        TLogInfo logInfo = new TLogInfo();
        logInfo.setCreateTime(new Date());
        logInfo.setUserId(shiroUser.getId());
        logInfo.setUsername(shiroUser.getName());
        logInfo.setMessage(new StringBuilder(shiroUser.getName()).append("[").append(shiroUser.getId()).append("]登录了系统。").toString());
        logInfo.setIpAddress(shiroUser.getIpAddress());
        logInfo.setLogLevel("TRACE");
        logInfo.setModule(1);
        logInfoMapper.insert(logInfo);

        return info;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {

        private static final long serialVersionUID = 8759475262318556175L;

        private Long id;
        private String loginName;
        private String name;
        private String ipAddress;
        private String language = "zh_CN";
        private String role;
        private String headImage;
        private String theme;

        private List<TSysRole> roleList;// 一个用户具有多个角色

        private TSysPermission sysPermission;

        public ShiroUser() {
        }

        public ShiroUser(String loginName) {
            this.loginName = loginName;
        }

        public ShiroUser(Long id, String loginName, String name) {
            this.id = id;
            this.loginName = loginName;
            this.name = name;
        }

        /**
         * @return the id
         */
        public Long getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Long id) {
            this.id = id;
        }

        /**
         * @return the loginName
         */
        public String getLoginName() {
            return loginName;
        }

        /**
         * @param loginName the loginName to set
         */
        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the ipAddress
         */
        public String getIpAddress() {
            return ipAddress;
        }

        /**
         * @param ipAddress the ipAddress to set
         */
        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public TSysPermission getSysPermission() {
            return sysPermission;
        }

        public void setSysPermission(TSysPermission sysPermission) {
            this.sysPermission = sysPermission;
        }

        public List<TSysRole> getRoleList() {
            return roleList;
        }

        public void setRoleList(List<TSysRole> roleList) {
            this.roleList = roleList;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return loginName;
        }
    }

}
