package com.bezkoder.spring.datajpa.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages ={"com.bezkoder.spring.datajpa.model"})
@EnableJpaRepositories(basePackages = "com.bezkoder.spring.datajpa.repository")
public class DaoConfig {
}