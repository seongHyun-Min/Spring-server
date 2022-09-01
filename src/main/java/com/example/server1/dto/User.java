package com.example.server1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private int age;
    //loombok을 사용해서 게터 세터 안만들고 tostring 오버라이딩 필요없음

}
