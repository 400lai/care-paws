package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "管理员ID，主键，自增")
    private Long id;

    @Schema(description = "管理员账号（登录用），唯一登录凭证")
    private String username;

    @Schema(description = "登录密码（MD5加密），建议使用更安全的哈希算法（如 bcrypt）")
    private String password;

    @Schema(description = "管理员姓名，真实姓名或显示名称")
    private String adminName;

    @Schema(description = "管理员手机号，可用于找回密码或通知")
    private String adminPhone;

    @Schema(description = "关联角色ID（控制权限），外键指向角色表")
    private Long roleId;

    @Schema(description = "账号状态（1-正常，2-禁用），控制是否允许登录")
    private Byte status;

    @Schema(description = "最后登录时间，记录最近一次成功登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "创建时间，自动填充账号创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间，自动记录信息修改时间")
    private LocalDateTime updateTime;
}
