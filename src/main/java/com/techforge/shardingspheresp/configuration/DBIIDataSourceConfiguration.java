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

public class DBIIDataSourceConfiguration {

    private Logger logger = LoggerFactory.getLogger(DBIIDataSourceConfiguration.class);

    @Value("${druid.datasource.username.dbii}")
    private String username;
    @Value("${druid.datasource.password.dbii}")
    private String password;
    @Value("${druid.datasource.driverClassName.dbii}")
    private String driverClassName;
    @Value("${druid.datasource.url0.dbii}")
    private String url0;


    @Bean
    public DataSource dbii_dataSource0() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url0);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        datasource.setTimeBetweenEvictionRunsMillis(60);
        datasource.setValidationQuery("VALUES 'x'");
        return datasource;
    }

    @Bean
    public DataSource dbii_dataSource() throws SQLException {

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("dbii_ds0", dbii_dataSource0());

        //
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("tbl_order");
        orderTableRuleConfig.setActualDataNodes("dbii_ds${0}.tbl_order_${0..1}");
        // 配置分库 + 分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "tbl_order_${order_id % 2}"));


        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        return dataSource;
    }


    @Bean("dbii_jdbcTemplate")
    public JdbcTemplate dbii_jdbcTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dbii_dataSource());
        return jdbcTemplate;
    }

}
