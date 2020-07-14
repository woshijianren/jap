package com.example.demo.jpa;

import org.springframework.data.domain.DomainEvents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQuery;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author zyl
 * @date 2020/7/13 9:02
 */
public interface PagingAndSortingRepository extends CrudRepository<Dept, Integer>, CustomRepository {

    Iterable<Dept> findAll(Sort sort);

    Page<Dept> findAll(Pageable pageable);

    long countByName(String name);

    long deleteByName(String name);

    Stream<Dept> findAllByDeptIdLessThan(Integer deptId);

    List<Dept> findByNameIgnoreCase(String name);

    Dept findByName(String name);

    @Query("select u from Dept u")
    List<Dept> u();

    @Query("select u from Dept u where u.name like ?1%")
    List<Dept> like1(String name);

    @Query(value="select u from Dept u where u.name like ?1")
    List<Dept> like2(String name);

    @Query(nativeQuery=true, value="select * from sys_dept u where u.name like ?1%")
    Page<Dept> like3(String name, Pageable pageable);

    @Query(value="select u from #{#entityName}  u where u.name like :name or u.name = :namea")
    List<Dept> findByAndSort(String namea, String name);

//    List<Dept> findByAndSort(String name,)

    @Modifying
    @Transactional
    @Query("update Dept set name = ?1 where deptId = ?2")
    String setName(String name, Integer id);

    @Transactional
    int deleteAllByDeptId(Integer deptId);

    @Modifying
    @Query("delete from Dept where deptId in :deptIds")
    @Transactional
    void deleteInDeptIds(Integer... deptIds);

    @QueryHints(value={@QueryHint(name="name", value="value")}, forCounting=false)
    Page<Dept> findByDeptIdLessThan(Integer deptId, Pageable page);
}

