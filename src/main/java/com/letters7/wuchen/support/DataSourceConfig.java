package com.letters7.wuchen.support;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author itw_wangjb03
 * @date 2018/5/1
 * sprint by itw_wangjb03：用于
 */
@Configuration
@MapperScan("com.letters7.wuchen.dao")
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties properties;

    @Autowired
    private Environment env;

    /**
     * 使用SpringJdbc的方式快速创建DataSource
     * 创建主数据源
     *
     * @return
     */
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 使用SpringJdbc的方式快速创建DataSource
     * 创建从数据源
     *
     * @return
     */
    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 放置数据源连接池
     *
     * @param masterData
     * @param slaveData
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterData,
                                        @Qualifier("slaveDataSource") DataSource slaveData) {
        // 放置数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseTypeEnum.master, masterData);
        targetDataSources.put(DatabaseTypeEnum.slave, slaveData);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 将数据源放置进去
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(slaveData);

        // 读相关的使用从数据库
        String read=env.getProperty("datasource.read");
        dynamicDataSource.setMethodType(DatabaseTypeEnum.slave,read);

        // 写相关的使用主数据库
        String write=env.getProperty("datasource.write");
        dynamicDataSource.setMethodType(DatabaseTypeEnum.master,write);
        return dynamicDataSource;
    }

    /**
     * 统一管理所有数据源
     * @param masterData
     * @param slaveData
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource masterData,
                                               @Qualifier("slaveDataSource") DataSource slaveData) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean=new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(this.dataSource(masterData, slaveData));
        sessionFactoryBean.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return sessionFactoryBean.getObject();

    }
}
