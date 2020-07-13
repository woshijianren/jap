package com.example.demo.jpa;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author zyl
 * @date 2020/7/13 9:20
 */
@NoRepositoryBean
public interface MyBaseRepository<T, ID> extends Repository<T, ID> {


    Optional<T> findById(ID id);

    <S extends T> S save(S entity);


}
