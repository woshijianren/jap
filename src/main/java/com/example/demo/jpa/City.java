package com.example.demo.jpa;

import org.springframework.data.domain.DomainEvents;

/**
 * @author zyl
 * @date 2020/7/13 9:25
 */
public class City {

    @DomainEvents
    void a() {
        System.out.println("aa");
    }
}
