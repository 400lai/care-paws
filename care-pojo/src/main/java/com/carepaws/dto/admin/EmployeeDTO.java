package com.carepaws.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;        // 员工唯一标识ID

    private String username;    // 员工登录用户名

    private String name;    // 员工姓名

    private String phone;   // 员工联系电话

    private String sex;     // 员工性别

    private String idNumber;    // 员工身份证号码

}
