package com.disqo.mary.gitanalyzer;

import static org.assertj.core.api.Assertions.assertThat;

import com.disqo.mary.gitanalyzer.model.dto.UserDetailsDTO;
import org.junit.jupiter.api.Test;
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

//    @Test
//    void shouldGetUsers(@Autowired TestRestTemplate restTemplate) {
//        //given, when
//        final ResponseEntity<UserDetailsDTO[]> responseEntity =
//            restTemplate.getForEntity("/api/users?page=2&size=5", UserDetailsDTO[].class);
//        final UserDetailsDTO[] users = responseEntity.getBody();
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(users).hasSize(5);
//    }
}
