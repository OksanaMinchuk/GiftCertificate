package com.epam.esm.config;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Bean;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class AppConfiguration {

    private static final Logger LOGGER = Logger.getRootLogger();

    @Bean
    public ModelMapper modelMapper() {
        LOGGER.debug("create ModelMapper bean");
        return new ModelMapper();
    }

}
