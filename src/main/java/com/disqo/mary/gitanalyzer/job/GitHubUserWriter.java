package com.disqo.mary.gitanalyzer.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import com.disqo.mary.gitanalyzer.config.AnalyzerConfig;
import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.service.GitHubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GitHubUserWriter implements ItemWriter<UserDetails> {

    private final GitHubUserService gitHubUserService;
    private final AnalyzerConfig config;

    private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void write(List<? extends UserDetails> items) throws ParseException {
        items.stream()
            .filter(reposMoreThan(config.getRepos()))
            .filter(followersMoreThan(config.getRepos()))
            .filter(profileCreatedAfter(FORMATTER.parse(config.getCreatedAfter())))
            .forEach(gitHubUserService::save);
    }

    private Predicate<UserDetails> reposMoreThan(int repos) {
        return userDetails -> userDetails.getPublicRepos() > repos;
    }

    private Predicate<UserDetails> followersMoreThan(int followers) {
        return userDetails -> userDetails.getFollowers() > followers;
    }

    private Predicate<UserDetails> profileCreatedAfter(Date date) {
        return userDetails -> userDetails.getCreatedAt().after(date);
    }
}
