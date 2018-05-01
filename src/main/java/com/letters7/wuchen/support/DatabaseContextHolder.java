package com.letters7.wuchen.support;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于记录当前线程使用的是哪一个数据源
 */
public class DatabaseContextHolder {
    public static final ThreadLocal<DatabaseTypeEnum> contextHolder=new ThreadLocal<>();

    public static void setDatabaseType(DatabaseTypeEnum databaseType) {
        contextHolder.set(databaseType);
    }
    public static DatabaseTypeEnum getDatabaseType(){
        return contextHolder.get();
    }
}
