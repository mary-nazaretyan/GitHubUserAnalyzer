package com.disqo.mary.gitanalyzer.repository;

import com.disqo.mary.gitanalyzer.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GithubUserRepository extends JpaRepository<UserInfo, Long> {
}
