package com.fangxuele.ocs.mapper.domain;

import java.io.Serializable;

public class TSysRolePermissionKey implements Serializable {
    private Long roleId;

    private Long permissionId;

    private static final long serialVersionUID = 1L;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}