package com.example.demo.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author zyl
 * @date 2020/7/13 9:25
 */
public interface CityRepository extends MyBaseRepository<Dept, Integer> {

//    Dept findByName(String name);

//    Optional<Dept> findFirst2ByNameLikeOrderByDeptIdDesc(String name, Pageable pa);

    Slice<Dept> findFirst2ByNameLike(String name, Pageable pa);

    Streamable<Dept> findByName(String name);

    Streamable<Dept> findByDeptIdLessThan(Integer deptId);

    @Query("select u from Dept u where u.name like %:#{'日本'}%")
    List<Dept> findByNameWithSpelExpression(String name);
}
