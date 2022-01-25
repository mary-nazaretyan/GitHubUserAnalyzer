package com.disqo.mary.gitanalyzer.job;

import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
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
        return jobBuilderFactory.get("github-users").start(scraper).build();
    }

    @Bean
    public Step scraper(GitHubUserReader reader, GitHubUserWriter writer) {
        return stepBuilderFactory.get("Collecting User Data")
            .<UserDetails, UserDetails>chunk(1)
            .reader(reader)
            .writer(writer).build();
    }
}
