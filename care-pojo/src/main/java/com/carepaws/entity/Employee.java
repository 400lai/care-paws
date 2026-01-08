package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工信息表")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 用户名
    private String username;

    // 姓名
    private String name;

    // 密码
    private String password;

    // 手机号
    private String phone;

    // 性别
    private String sex;

    // 身份证号
    private String idNumber;

    // 账号状态
    private Integer status;

    // 创建时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 修改时间
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 创建人id
    private Long createUser;

    // 修改人id
    private Long updateUser;

}
