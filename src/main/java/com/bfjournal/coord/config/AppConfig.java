package com.bfjournal.coord.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Application configuration.
 * <p>
 * Created by grant on 8/20/15.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bfjournal.coord.web")
public class AppConfig extends WebMvcConfigurerAdapter {

}
