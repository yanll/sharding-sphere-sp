package com.techforge.shardingspheresp;

import com.techforge.shardingspheresp.configuration.FrameworkUtilConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by breez on 2016/03/30.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(
        basePackages = {"com.techforge.shardingspheresp"},
        basePackageClasses = {FrameworkUtilConfiguration.class}
)
public class ShardingSphereSPApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereSPApplication.class, args);
    }
}