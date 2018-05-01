package com.letters7.wuchen.aop;

import com.letters7.wuchen.support.DatabaseContextHolder;
import com.letters7.wuchen.support.DatabaseTypeEnum;
import com.letters7.wuchen.support.DynamicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于 使用aop 在dao层判断使用哪一个数据源
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {
    /**
     * 日志
     */
    private static Logger logger= LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 拦截所有进入mapper前的方法
     * 这里其实也可以做分发，只拦截比如query,select等开头的方法，更简单, 这里考虑的通用
     */
    @Pointcut("execution(* com.letters7.wuchen.dao.*.*(..))")
    public void aspect(){

    }

    /**
     * 判断方法开头，从而确定使用哪个数据源
     * @param point
     */
    @Before("aspect()")
    public void before(JoinPoint point){
        String className=point.getTarget().getClass().getName();
        String method=point.getSignature().getName();
        String args= StringUtils.join(point.getArgs(),",");
        logger.info("类:{},方法:{},参数:{}",className,method,args);
        try{
            for (DatabaseTypeEnum typeEnum:DatabaseTypeEnum.values()){
                List<String> values= DynamicDataSource.METHOD_TYPE_MAP.get(typeEnum);
                for (String key:values){
                    if (method.startsWith(key)){
                        logger.info(">>{} 方法预计使用的数据源为：{}<<",method,typeEnum);
                        DatabaseContextHolder.setDatabaseType(typeEnum);
                        DatabaseTypeEnum databaseTypeEnum=DatabaseContextHolder.getDatabaseType();
                        logger.info(">>{} 方法使用的数据源为：{}<<",method,databaseTypeEnum);
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
