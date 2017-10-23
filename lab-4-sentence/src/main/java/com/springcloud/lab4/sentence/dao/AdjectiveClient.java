package com.springcloud.lab4.sentence.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.springcloud.lab4.sentence.domain.Word;

@FeignClient("ADJECTIVE")
public interface AdjectiveClient {

    @GetMapping("/")
    public Word getWord();

    static class HystrixClientFallback implements AdjectiveClient {

        @Override

        public Word getWord() {

            return new Word();

        }
    }
}
