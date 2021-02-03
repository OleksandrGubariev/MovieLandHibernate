package com.gubarev.movieland.config;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(value = "com.gubarev.movieland", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "com.gubarev.movieland.web.*"))
@EnableScheduling
public class RootApplicationContext {
}
