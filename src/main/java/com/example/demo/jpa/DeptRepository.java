package com.example.demo.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author zyl
 * @date 2020/7/14 11:00
 */

public interface DeptRepository extends CrudRepository<Dept, Integer> {

}
