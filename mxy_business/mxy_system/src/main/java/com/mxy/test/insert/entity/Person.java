package com.mxy.test.insert.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mengyao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private Long id;
    private String name;
    private Long phone;
    private BigDecimal salary;
    private String company;
    private Integer ifSingle;
    private Integer sex;
    private String address;
    private LocalDateTime createTime;
    private String createUser;
}
