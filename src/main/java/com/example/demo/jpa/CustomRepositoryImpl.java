package com.example.demo.jpa;

/**
 * @author zyl
 * @date 2020/7/13 16:16
 */
public class CustomRepositoryImpl implements CustomRepository {
    @Override
    public Dept aa(String name) {
        System.out.println("aa");
        return null;
    }
}
