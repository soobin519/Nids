package com.aws.codestar.projecttemplates.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.ApplicationContext;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import org.apache.ibatis.session.*;

import org.mybatis.spring.*;
import org.mybatis.spring.SqlSessionFactoryBean;

@Configuration
@ComponentScan({ "com.aws.codestar.projecttemplates.configuration" })
@PropertySource("classpath:application.properties")
public class DataConfig {
    
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() {
        //mysqlnidsp01.cop0izop1enu.us-east-1.rds.amazonaws.com
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUsername("nids");
        dataSource.setUrl("jdbc:mysql://mysqlnidsp01.cop0izop1enu.us-east-1.rds.amazonaws.com:3306/nids_test?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf8");
        dataSource.setPassword("nidsroot!23");
        
        return dataSource;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));
        
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate;
    }
}