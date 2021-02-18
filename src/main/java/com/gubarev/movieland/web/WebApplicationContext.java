package com.gubarev.movieland.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.gubarev.movieland.web")
@EnableWebMvc
public class WebApplicationContext implements WebMvcConfigurer {
}
