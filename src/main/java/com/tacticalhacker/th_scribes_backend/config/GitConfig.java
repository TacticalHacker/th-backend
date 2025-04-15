package com.tacticalhacker.th_scribes_backend.config;

import com.tacticalhacker.th_scribes_backend.model.GitData;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:git.properties", ignoreResourceNotFound = true)
@EnableConfigurationProperties(GitData.class)
public class GitConfig {
}
