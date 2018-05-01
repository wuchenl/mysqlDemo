package com.letters7.wuchen.support;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于 多数据源配置
 */
public enum DatabaseTypeEnum {
    master("write","主数据库"),
    slave("read","从数据库");

    DatabaseTypeEnum(String action,String name){
        this.action=action;
        this.name=name;
    }
    // 作用
    private String action;
    // 名称
    private String name;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
