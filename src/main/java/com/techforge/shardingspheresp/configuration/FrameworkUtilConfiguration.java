package com.techforge.shardingspheresp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        MySQLDataSourceConfiguration.class
})
public class FrameworkUtilConfiguration {

}
