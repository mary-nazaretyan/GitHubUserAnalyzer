package com.disqo.mary.gitanalyzer.job;

import com.disqo.mary.gitanalyzer.model.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitHubUserAnalyzerJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(Step scraper) {
        return jobBuilderFactory.get("github-users6").start(scraper).build();
    }

    @Bean
    public Step scraper(GitHubUserReader reader, GitHubUserWriter writer) {
        return stepBuilderFactory.get("grabStep")
            .<UserInfo, UserInfo>chunk(1024)
            .reader(reader)
            .writer(writer)
            .build();
    }
}
