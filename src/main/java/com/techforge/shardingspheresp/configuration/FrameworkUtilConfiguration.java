package com.techforge.shardingspheresp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        DataSourceConfiguration.class
})
public class FrameworkUtilConfiguration {

}
