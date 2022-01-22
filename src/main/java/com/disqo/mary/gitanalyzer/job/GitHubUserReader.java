package com.disqo.mary.gitanalyzer.job;

import java.util.Iterator;
import java.util.List;

import com.disqo.mary.gitanalyzer.model.entity.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GitHubUserReader implements ItemStreamReader<UserInfo> {
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    private Iterator<UserInfo> iterator;


    @SneakyThrows
    @Override
    public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        final var document = restTemplate.exchange("https://api.github.com/users?since=0&per_page=100", HttpMethod.GET, entity, String.class).getBody();

        var list = mapper.readValue(document, new TypeReference<List<UserInfo>>(){});


        iterator = list.iterator();
    }

    @Override
    public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }

    @Override
    public UserInfo read() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
