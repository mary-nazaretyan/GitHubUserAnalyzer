package com.disqo.mary.gitanalyzer.service;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;

import com.disqo.mary.gitanalyzer.model.dto.UserDetailsDTO;
import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.repository.GitHubUserRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GitHubUserService {

    private final GitHubUserRepository gitHubUserRepository;

    public void save(UserDetails userDetails) {
        gitHubUserRepository.save(userDetails);
    }

    public List<UserDetailsDTO> getUsers(Pageable pageable) {
        return gitHubUserRepository.findAll(pageable).stream().map(UserDetailsDTO::new).collect(toUnmodifiableList());
    }

    public List<UserDetailsDTO> getUserByCompany(String company) {
        return gitHubUserRepository.findAll().stream().filter(userDetails -> company.equals(userDetails.getCompany()))
            .map(UserDetailsDTO::new).collect(toUnmodifiableList());
    }

    public List<UserDetailsDTO> getUserByLocation(String location) {
        return gitHubUserRepository.findAll().stream().filter(userDetails -> location.equals(userDetails.getLocation()))
            .map(UserDetailsDTO::new).collect(toUnmodifiableList());
    }

    public List<UserDetailsDTO> getUserByPartialUsername(String username) {
        return gitHubUserRepository.findAll().stream()
            .filter(userDetails -> StringUtils.isNotBlank(userDetails.getLogin()))
            .filter(userDetails -> userDetails.getLogin().contains(username)).map(UserDetailsDTO::new)
            .collect(toUnmodifiableList());
    }
}
