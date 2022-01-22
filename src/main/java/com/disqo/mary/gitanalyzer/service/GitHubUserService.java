package com.disqo.mary.gitanalyzer.service;

import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.repository.GithubUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GitHubUserService {

    private final GithubUserRepository githubUserRepository;

    public void save(UserDetails userDetails) {
        githubUserRepository.save(userDetails);
    }
}