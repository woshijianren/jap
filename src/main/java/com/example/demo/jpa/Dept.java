package com.example.demo.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zyl
 * @date 2020/7/13 8:49
 */
@Table(name = "sys_dept")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dept {

    public Dept(String name) {
        this.name = name;
    }

    //    @Column(name = "dept_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;

//    @Column(name = "pid")
//    private Integer pid;
//
////    @Column(name = "sub_count")
//    private Integer subCount;
//
    private String name;
//
//    private Integer deptSort;
//
//    private Integer enabled;
//
//    private String createBy;
//
//    private String updateBy;
//
//    private Timestamp createTime;
//
//    private Timestamp updateTime;

    @DomainEvents()
    void a() {
        System.out.println("aa");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void callbackMethod() {
        System.out.println("bb");
    }
}
