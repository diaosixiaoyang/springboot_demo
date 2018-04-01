package com.fangxuele.ocs.common.bean.loms;

import java.io.Serializable;
import java.util.List;

/**
 * @author rememberber(https : / / github.com / rememberber)
 */
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 3993984085907429327L;

    private String title;

    private String url;

    private String cssId;

    private String iconClass;

    private List<SysMenu> childMenu;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCssId() {
        return cssId;
    }

    public void setCssId(String cssId) {
        this.cssId = cssId;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public List<SysMenu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<SysMenu> childMenu) {
        this.childMenu = childMenu;
    }
}
