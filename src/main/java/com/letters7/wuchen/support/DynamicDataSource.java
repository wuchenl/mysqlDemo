package com.letters7.wuchen.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
   public static final Map<DatabaseTypeEnum,List<String>> METHOD_TYPE_MAP=new HashMap<>();

    /**
     * 获取当前数据源,如果为空则使用设置的默认数据源
     * @return
     */
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        DatabaseTypeEnum databaseType=DatabaseContextHolder.getDatabaseType();
        logger.info("当前数据源为："+databaseType);
        return databaseType;
    }

    /**
     * 根据读写的前缀，关联对应的数据源，做读写分离
     * @param typeEnum
     * @param content
     */
    void setMethodType(DatabaseTypeEnum typeEnum,String content){
        List<String> list= Arrays.asList(content.split(","));
        METHOD_TYPE_MAP.put(typeEnum,list);
    }
}
