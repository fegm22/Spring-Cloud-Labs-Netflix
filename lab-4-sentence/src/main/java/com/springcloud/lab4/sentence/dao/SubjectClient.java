package com.springcloud.lab4.sentence.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.springcloud.lab4.sentence.domain.Word;

@FeignClient("SUBJECT")
public interface SubjectClient {

    @GetMapping("/")
    public Word getWord();

}
