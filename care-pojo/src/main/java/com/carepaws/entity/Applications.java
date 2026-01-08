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
@Schema(description = "宠托师认证申请表")
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "用户ID，关联user表")
    private Long userId;

    @Schema(description = "身份证号")
    private String idNumber;

    @Schema(description = "身份证正面照片路径")
    private String idFrontPhoto;

    @Schema(description = "身份证反面照片路径")
    private String idBackPhoto;

    @Schema(description = "审核状态：0待审核，1通过，2拒绝")
    private Integer status;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注，拒绝时填写原因")
    private String auditRemark;
}