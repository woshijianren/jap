package com.example.demo;

import cn.hutool.core.util.StrUtil;
import com.example.demo.jpa.CityRepository;
import com.example.demo.jpa.Dept;
import com.example.demo.jpa.PagingAndSortingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PagingAndSortingRepository repository;

    @Test
    public void test1() {
//        Page<Dept> depts = repository.findAll(PageRequest.of(1, 20));
        Page<Dept> depts = repository.findAll(Pageable.unpaged());
        Sort sort = Sort.by("name").ascending().and(Sort.by("name").descending());

        Sort.TypedSort<Dept> dept = Sort.sort(Dept.class);
        Sort sort1 = dept.by(Dept::getDeptId).ascending().and(dept.by(Dept::getDeptId).descending());
        System.out.println(depts);

    }


    @Test
    public void test2() {
        Iterable<Dept> all = repository.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void test3() {

    }

    @Test
    @Transactional
    public void test4() {
        long aa = repository.deleteByName("南京办");
        System.out.println(aa);
    }

    @Autowired
    private CityRepository cityRepository;
    @Test
    @Transactional
    public void test5() {
        cityRepository.save(new Dept(3, "日本4"));
    }

    @Test
    public void test6() {
//        Optional<Dept> riben = cityRepository.findFirst2ByNameLikeOrderByDeptIdDesc("日本%", PageRequest.of(1,1));
//        System.out.println(riben);

        Slice<Dept> ribens = cityRepository.findFirst2ByNameLike("日本%", PageRequest.of(0, 1));
        ribens.forEach(System.out::println);
        System.out.println(ribens);
    }

    @Test
    public void test7() {
        Streamable<Dept> nan = cityRepository.findByName("拉萨办");
        Streamable<Dept> dept = cityRepository.findByDeptIdLessThan(20);
        Streamable<Dept> and = nan.and(dept);
        System.out.println(and.toList());
    }

    @Test
    @Transactional
    public void test8() {
        Stream<Dept> allByDeptIdLessThan = repository.findAllByDeptIdLessThan(20);
        allByDeptIdLessThan.forEach(System.out::println);
        allByDeptIdLessThan.close();
    }

    @Test
    public void test9() {
        System.out.println(repository.u());
    }

    @Test
    public void test10() {
        System.out.println(repository.findByName("AA"));
        System.out.println(repository.findByNameIgnoreCase("AA"));
    }

    @Test
    public void test11() {
        System.out.println(repository.like1("日本"));
        System.out.println(repository.like2("日本%"));
        System.out.println(repository.like2("日本"));
        System.out.println(repository.like3("日本", PageRequest.of(0, 1)).getContent());
    }

    @Test
    public void test12() {
        System.out.println(repository.findByAndSort("南京办", "日本%"));
    }

    @Test
    public void test13() {
//        List<Dept> riben = cityRepository.findByNameWithSpelExpression("日本");
//        System.out.println(riben);
    }

    @Test
    public void test14() {
        System.out.println(repository.setName("ee", 1));
    }

    @Test
    public void test15() {
        repository.deleteAllByDeptId(2);
    }

    @Test
    public void test16() {
        repository.deleteInDeptIds(1,2,3,4,5);
    }

    @Test
    public void test17() {
        Page<Dept> byDeptIdLessThan = repository.findByDeptIdLessThan(100, PageRequest.of(1, 3));
        System.out.println(byDeptIdLessThan.getContent());
    }

    @Test
    public void test18() {
        System.out.println(StrUtil.indexOfIgnoreCase("1231231.Hk", ".hk"));
        System.out.println("1231231.Hk".substring(0, 7));
        System.out.println(StrUtil.subPre("1231231.Hk", StrUtil.indexOfIgnoreCase("1231231.Hk", ".hk")));
    }
}
