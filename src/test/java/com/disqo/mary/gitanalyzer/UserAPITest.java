package com.disqo.mary.gitanalyzer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.disqo.mary.gitanalyzer.model.dto.UserDetailsDTO;
import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.repository.GitHubUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserAPITest {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private GitHubUserRepository gitHubUserRepository;

    @BeforeEach
    void initData(){
        gitHubUserRepository.deleteAll();

        IntStream.range(1, 21).forEach(i -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setId((long) i);
            userDetails.setLogin("Login" + i);
            userDetails.setCompany("Company" + i % 2);
            userDetails.setLocation("Location" + i % 3);
            gitHubUserRepository.save(userDetails);
        });
    }

    @Test
    void shouldGetUsersWithPagination() {
        //given, when
        final ResponseEntity<UserDetailsDTO[]> responseEntity =
            restTemplate.getForEntity("/api/users?page=1&size=5", UserDetailsDTO[].class);
        final UserDetailsDTO[] users = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).hasSize(5);
    }

    @Test
    void shouldGetUsersFromSameCompany() {
        //given, when
        final ResponseEntity<UserDetailsDTO[]> responseEntity =
            restTemplate.getForEntity("/api/users/company/Company0", UserDetailsDTO[].class);
        final UserDetailsDTO[] users = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).hasSize(10);
        assertThat(users).isNotNull();
        assertThat(Arrays.stream(users).map(UserDetailsDTO::getCompany).distinct()).containsOnly("Company0");
    }

    @Test
    void shouldGetUsersFromSameLocation() {
        //given, when
        final ResponseEntity<UserDetailsDTO[]> responseEntity =
            restTemplate.getForEntity("/api/users/location/Location1", UserDetailsDTO[].class);
        final UserDetailsDTO[] users = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).hasSize(7);
        assertThat(users).isNotNull();
        assertThat(Arrays.stream(users).map(UserDetailsDTO::getLocation).distinct()).containsOnly("Location1");
    }

    @Test
    void shouldGetUsersWithPartialUsername() {
        //given, when
        final ResponseEntity<UserDetailsDTO[]> responseEntity =
            restTemplate.getForEntity("/api/users/2", UserDetailsDTO[].class);
        final UserDetailsDTO[] users = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).hasSize(3);
        assertThat(users).isNotNull();
        assertThat(Arrays.stream(users).map(UserDetailsDTO::getLogin).distinct()).containsOnly("Login2", "Login12",
                                                                                               "Login20");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/api/users/z", "/api/users/location/LocationZ", "/api/users/company/CompanyZ"})
    void shouldGetUsersEmptyList(String url) {
        //given, when
        final ResponseEntity<UserDetailsDTO[]> responseEntity = restTemplate.getForEntity(url, UserDetailsDTO[].class);
        final UserDetailsDTO[] users = responseEntity.getBody();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users).hasSize(0);
    }
}
