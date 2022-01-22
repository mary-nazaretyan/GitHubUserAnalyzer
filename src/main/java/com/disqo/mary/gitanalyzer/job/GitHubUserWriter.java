package com.disqo.mary.gitanalyzer.job;

import java.util.List;

import com.disqo.mary.gitanalyzer.model.entity.UserInfo;
import com.disqo.mary.gitanalyzer.service.GitHubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GitHubUserWriter implements ItemWriter<UserInfo> {

    private final GitHubUserService gitHubUserService;

    @Override
    public void write(List<? extends UserInfo> items) {
        items.forEach(gitHubUserService::save);
    }
}
