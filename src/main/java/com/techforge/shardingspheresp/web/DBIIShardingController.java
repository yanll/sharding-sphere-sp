package com.techforge.shardingspheresp.web;

import com.techforge.shardingspheresp.utils.UtilJackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by YANLL on 2016/03/30.
 */

@RestController
@RequestMapping("/dbii_sharding")
public class DBIIShardingController {
    private final static Logger logger = LoggerFactory.getLogger(DBIIShardingController.class);


    @Autowired
    JdbcTemplate dbii_jdbcTemplate;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Object insert() {
        String sql = "insert into tbl_order(order_id,user_id) values(?,?)";
        for (int i = 10000; i < 10100; i++) {
            dbii_jdbcTemplate.update(sql, i, 10000);
        }
        return "OK";
    }

    @RequestMapping(value = "/limit", method = RequestMethod.GET)
    public String limit() {
        List<Map<String, Object>> list = dbii_jdbcTemplate.queryForList("select id,order_id from tbl_order order by order_id limit ?,?", 2, 4);
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/select_object", method = RequestMethod.GET)
    public String select_object() {
        List<Map<String, Object>> list = dbii_jdbcTemplate.queryForList("select * from tbl_order where order_id = ?", 10002);
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/select_list", method = RequestMethod.GET)
    public String select_list() {
        List<Map<String, Object>> list = dbii_jdbcTemplate.queryForList("select * from tbl_order where order_id in (10001,10002,10005,10020)");
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() {
        dbii_jdbcTemplate.update("delete from tbl_order");
        return "OK";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String count() {
        Long c = dbii_jdbcTemplate.queryForObject("select count(*) from tbl_order", Long.class);
        System.out.println(c);
        return "OK";
    }

}