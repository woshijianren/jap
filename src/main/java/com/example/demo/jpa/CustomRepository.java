package com.example.demo.jpa;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.function.Predicate;

/**
 * @author zyl
 * @date 2020/7/13 16:16
 */
public interface CustomRepository  {

    Dept aa(String name);
}
