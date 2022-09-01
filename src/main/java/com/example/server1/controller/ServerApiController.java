package com.example.server1.controller;


import com.example.server1.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

//서버에 log를 찍기 위해서 slf4j 어노테이션
@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {
    //https://openapi.naver.com/v1/search/local.json
    // ?query=%EA%B0%88%EB%B9%84%EC%A7%91
    // &display=10
    // &start=1
    // &sort=random
    @GetMapping("/naver")
    public String naver(){
        String query = "갈비집";
        String enocde = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query" ,"중국집")
                .queryParam("display", 10)
                .queryParam("start" ,1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8")) //자동으로 utf -8인코딩
                .build()
                .toUri();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id","p5kXlyqnG0_Ut43Uozo9")
                .header("X-Naver-Client-Secret","4rKGDZoKZX")
                .build();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();


    }

    @GetMapping("hello")
    public User hello(@RequestParam String name, @RequestParam int age){
        User user = new User();
        user.setName(name);
        user.setAge(age); //에코 형태 동작
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User post(@RequestBody User user,
                     @PathVariable int userId,
                     @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader)

            {
        log.info("userId : {}, userName {}", userId, userName);
        log.info("authorization : {}, header {}", authorization, customHeader);
        log.info("client req : {}",user);
        return user;

    }
}

