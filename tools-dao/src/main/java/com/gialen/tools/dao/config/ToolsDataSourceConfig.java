package com.gialen.tools.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 工具数据源配置
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.gialen.tools.dao.repository.tools"}, sqlSessionTemplateRef = "toolsSqlSessionTemplate")
public class ToolsDataSourceConfig {
    @Bean(name = "toolsDataSource")
    @ConfigurationProperties(prefix = "tools.datasource")
    public DataSource toolsDataSource() {
        log.info("=============");
        DataSource dataSource = new DruidDataSource();
        return dataSource;
    }


    @Bean
    public SqlSessionFactory toolsSqlSessionFactory(@Qualifier("toolsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:mapper/tools/tools/*.xml");
            Resource[] resourcesExtent = resolver.getResources("classpath*:mapper/tools/tools/extend/*.xml");
            Resource[] resourceTarget = new Resource[resources.length+resourcesExtent.length];
            System.arraycopy(resources,0,resourceTarget,0,resources.length);
            System.arraycopy(resourcesExtent,0,resourceTarget,resources.length,resourcesExtent.length);
            bean.setMapperLocations(resourceTarget);
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Bean
    public SqlSessionTemplate toolsSqlSessionTemplate(@Qualifier("toolsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }

    /******配置事务管理********/

    @Bean
    public PlatformTransactionManager toolsTransactionManager(@Qualifier("toolsDataSource") DataSource prodDataSource) {
        return new DataSourceTransactionManager(prodDataSource);
    }
}

