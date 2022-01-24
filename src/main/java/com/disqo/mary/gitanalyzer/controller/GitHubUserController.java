package com.disqo.mary.gitanalyzer.controller;

import java.util.List;

import com.disqo.mary.gitanalyzer.model.dto.UserDetailsDTO;
import com.disqo.mary.gitanalyzer.service.GitHubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A single REST API for {@link com.disqo.mary.gitanalyzer.model.dto.UserDetailsDTO} entity.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class GitHubUserController {

    private final GitHubUserService gitHubUserService;

    /**
     * Returns the list of {@link UserDetailsDTO} entities with requested pagination parameters.
     *
     * @param page Page in the result set to paginate to, default value is 0.
     * @param size Window pagination size, default value is 10.
     *
     * @return the list of {@link UserDetailsDTO} entities and response status.
     */
    @GetMapping()
    public ResponseEntity<List<UserDetailsDTO>> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return new ResponseEntity<>(gitHubUserService.getUsers(pageable), HttpStatus.OK);
    }

    /**
     * Returns the {@link UserDetailsDTO} entities list with specified company.
     *
     * @param company the company of {@link UserDetailsDTO}.
     *
     * @return {@link UserDetailsDTO} entities list and response status.
     */
    @GetMapping("/company/{company}")
    public ResponseEntity<List<UserDetailsDTO>> getUserByCompany(@PathVariable("company") String company) {
        return new ResponseEntity<>(gitHubUserService.getUserByCompany(company), HttpStatus.OK);
    }

    /**
     * Returns the {@link UserDetailsDTO} entities list with specified location.
     *
     * @param location the location of {@link UserDetailsDTO}.
     *
     * @return {@link UserDetailsDTO} entities list and response status.
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<UserDetailsDTO>> getUserByLocation(@PathVariable("location") String location) {
        return new ResponseEntity<>(gitHubUserService.getUserByLocation(location), HttpStatus.OK);
    }

    /**
     * Returns the {@link UserDetailsDTO} entities list with username that partially matching with the specified
     * username.
     *
     * @param username the username of {@link UserDetailsDTO}.
     *
     * @return {@link UserDetailsDTO} entities list and response status.
     */
    @GetMapping("/{username}")
    public ResponseEntity<List<UserDetailsDTO>> getUserByPartialUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(gitHubUserService.getUserByPartialUsername(username), HttpStatus.OK);
    }
}
