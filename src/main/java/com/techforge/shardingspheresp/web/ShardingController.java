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
import java.util.UUID;

/**
 * Created by YANLL on 2016/03/30.
 */

@RestController
@RequestMapping("/sharding")
public class ShardingController {
    private final static Logger logger = LoggerFactory.getLogger(ShardingController.class);


    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public Object index() throws Exception {
        String sql = "insert into tbl_order(order_id,user_id) values(?,?)";
        for (int i = 10000; i < 10100; i++) {
            jdbcTemplate.update(sql, UUID.randomUUID().toString(), i);
        }
        return "OK";
    }

    @RequestMapping(value = "/limit", method = RequestMethod.GET)
    public String limit() throws Exception {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,user_id from tbl_order order by user_id limit ?,?", 2, 4);
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/select_object", method = RequestMethod.GET)
    public String select_object() throws Exception {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from tbl_order where user_id = ?", 10002);
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/select_list", method = RequestMethod.GET)
    public String select_list() throws Exception {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from tbl_order where user_id in (10001,10002,10005,10020)");
        System.out.println(UtilJackson.toJSON(list));
        return "OK";
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear() throws Exception {
        jdbcTemplate.update("delete from tbl_order");
        return "OK";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String count() throws Exception {
        Long c = jdbcTemplate.queryForObject("select count(*) from tbl_order", Long.class);
        System.out.println(c);
        return "OK";
    }

}