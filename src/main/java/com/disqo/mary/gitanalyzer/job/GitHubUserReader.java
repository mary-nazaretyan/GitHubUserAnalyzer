package com.disqo.mary.gitanalyzer.job;

import static java.util.Collections.emptyIterator;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.disqo.mary.gitanalyzer.config.AnalyzerConfig;
import com.disqo.mary.gitanalyzer.model.entity.UserDetails;
import com.disqo.mary.gitanalyzer.model.entity.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GitHubUserReader implements ItemStreamReader<UserDetails> {
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;
    private final AnalyzerConfig config;

    private Iterator<String> iterator = emptyIterator();
    private ArrayDeque<Integer> sinceNumbers;

    @Override
    public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        sinceNumbers = sinceNumbers();
    }

    @Override
    public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        iterator = emptyIterator();
    }

    @Override
    public UserDetails read() {
        return Option.when(iterator.hasNext(), () -> iterator)
            .orElse(() -> Option.of(iterator = iterator()))
            .filter(Iterator::hasNext)
            .map(Iterator::next)
            .map(this::getJson)
            .map(s -> Try.of(() -> mapper.readValue(s, UserDetails.class)))
            .filter(Try::isSuccess)
            .map(Try::get)
            .getOrElse(() -> null);
    }

    private Iterator<String> iterator() {
        return Stream.ofNullable(sinceNumbers)
            .filter(not(CollectionUtils::isEmpty))
            .map(Queue::poll)
            .map(since -> config.getUrl() + since)
            .map(this::getJson)
            .map(s -> Try.of(() -> mapper.readValue(s, new TypeReference<List<UserInfo>>() {})))
            .filter(Try::isSuccess)
            .map(Try::get)
            .flatMap(Collection::stream)
            .map(UserInfo::getLogin)
            .map(login -> config.getUserUrl() + login)
            .distinct()
            .iterator();
    }

    private String getJson(String url) {
        //TODO: add RateLimiter to avoid the auth., limit problems of website
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class)
            .getBody();
    }

    private ArrayDeque<Integer> sinceNumbers() {
        return IntStream.iterate(0, since -> since < config.getRestriction(), since -> since += 100)
            .boxed()
            .collect(toCollection(ArrayDeque::new));
    }
}