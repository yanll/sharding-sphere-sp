package com.techforge.shardingspheresp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by YANLL on 2016/03/30.
 */

@RestController
@RequestMapping
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String ignore() {
        return "this is index page.";
    }

}