package com.gubarev.movieland.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(value = "com.gubarev.movieland", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "com.gubarev.movieland.web.*"))
@PropertySource("classpath:application.properties")
public class RootApplicationContext {
}
