package com.disqo.mary.gitanalyzer.config;

import java.util.stream.IntStream;

import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.repository.GitHubUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomTestConfig {

    /**
     * Loads 10 entries to the "userDetail" table.
     *
     * @param gitHubUserRepository GitHubUserRepository repository.
     *
     * @return {@link CommandLineRunner} instance.
     */
//    @Bean
//    public CommandLineRunner commandLineRunner(GitHubUserRepository gitHubUserRepository) {
//
//        return args -> {
//            gitHubUserRepository.deleteAll();
//
//            IntStream.range(1, 20).forEach(i -> {
//                UserDetails userDetails = new UserDetails();
//                userDetails.setId((long) i);
//                userDetails.setLogin("Login" + i);
//                gitHubUserRepository.save(userDetails);
//            });
//        };
//    }
}
