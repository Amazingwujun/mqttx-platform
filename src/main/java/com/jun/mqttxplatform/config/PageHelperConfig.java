package com.jun.mqttxplatform.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 分页插件配置
 *
 * @author Jun
 * @since 1.0.4
 */
@Configuration
public class PageHelperConfig {

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource, MybatisProperties mybatisProperties) {
        //初始化分页插件对象
        PageInterceptor pageInterceptor = new PageInterceptor();

        //插件属性配置
        Properties properties = new Properties();

        pageInterceptor.setProperties(properties);

        //初始化sqlSessionFactory
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
        sqlSessionFactoryBean.setConfiguration(mybatisProperties.getConfiguration());
        sqlSessionFactoryBean.setPlugins(pageInterceptor);

        return sqlSessionFactoryBean;
    }
}
