package com.techforge.shardingspheresp.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Value("${druid.datasource.username}")
    private String username;
    @Value("${druid.datasource.password}")
    private String password;
    @Value("${druid.datasource.driverClassName}")
    private String driverClassName;
    @Value("${druid.datasource.url0}")
    private String url0;
    @Value("${druid.datasource.url1}")
    private String url1;
    @Value("${druid.datasource.url2}")
    private String url2;
    @Value("${druid.datasource.url3}")
    private String url3;


    @Bean
    public DataSource dataSource0() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url0);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(60);
        datasource.setValidationQuery("select 'x'");
        return datasource;
    }

    @Bean
    public DataSource dataSource1() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url1);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(60);
        datasource.setValidationQuery("select 'x'");
        return datasource;
    }

    @Bean
    public DataSource dataSource2() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url2);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(60);
        datasource.setValidationQuery("select 'x'");
        return datasource;
    }

    @Bean
    public DataSource dataSource3() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url3);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(60);
        datasource.setValidationQuery("select 'x'");
        return datasource;
    }

    @Bean
    public DataSource dataSource() throws SQLException {

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", dataSource0());
        dataSourceMap.put("ds1", dataSource1());
//        dataSourceMap.put("ds2", dataSource2());
//        dataSourceMap.put("ds3", dataSource3());

        //
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("tbl_order");
        orderTableRuleConfig.setActualDataNodes("ds${0}.tbl_order_${0..1}");
        // 配置分库 + 分表策略
//        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 4}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "tbl_order_${user_id % 2}"));

        //
//        TableRuleConfiguration itemTableRuleConfig = new TableRuleConfiguration();
//        itemTableRuleConfig.setLogicTable("tbl_order_item");
//        itemTableRuleConfig.setActualDataNodes("ds${0..3}.tbl_order_item_${0..1}");
        // 配置分库 + 分表策略
//        itemTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 4}"));
//        itemTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("item_id", "tbl_order_item_${item_id % 2}"));


        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
//        shardingRuleConfig.getTableRuleConfigs().add(itemTableRuleConfig);


        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        return dataSource;
    }


    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

}
