package com.junze.eshop.inventory;

import com.junze.eshop.inventory.listener.InitListener;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Junze
 * @Date: Create in 14:22 2017/6/23
 * @Description:springboot  入口类
 */

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@MapperScan("com.junze.eshop.inventory.mapper")
public class Application {
    //数据源
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(){
        return new DataSource();
    }
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    //事务
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    //redis集群
    @Bean
    public JedisCluster jedisClusterFactory(){
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("192.168.1.165",7001));
        jedisClusterNodes.add(new HostAndPort("192.168.1.163",7002));
        jedisClusterNodes.add(new HostAndPort("192.168.1.164",7003));
        return new JedisCluster(jedisClusterNodes);
    }
    //注册监听器
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitListener());
        return servletListenerRegistrationBean;
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
