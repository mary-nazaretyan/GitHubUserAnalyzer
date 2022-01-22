package com.disqo.mary.gitanalyzer.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "disqo.mary.git.analyzer")
public class AnalyzerConfig implements InitializingBean {
    private int restriction;
    private String url;
    private String userUrl;
    private String token;

    @Override
    public void afterPropertiesSet() {
        log.info("AnalyzerConfig initialized with: {}", this);
    }
}
