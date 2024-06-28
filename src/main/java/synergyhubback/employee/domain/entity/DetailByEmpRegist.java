package synergyhubback.employee.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "detail_by_emp_regist")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DetailByEmpRegist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int erd_code;

    private String erd_num;

    private String erd_title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_code")
    private Employee employee;

    public DetailByEmpRegist(String erd_num, String erd_title, Employee employee) {

        this.erd_num = erd_num;
        this.erd_title = erd_title;
        this.employee = employee;
        employee.addEmpRegistDetail(this); // Employee 엔티티의 메서드를 통해 연결
    }
}
