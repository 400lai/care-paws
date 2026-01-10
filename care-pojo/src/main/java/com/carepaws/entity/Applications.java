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
@Schema(description = "申请认证表（喂养员实名认证）")
public class Applications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "认证ID（主键）")
    private Long id;

    @Schema(description = "关联用户ID")
    private Long userId;

    @Schema(description = "身份证号（实名认证用）")
    private String idNumber;

    @Schema(description = "身份证正面照URL")
    private String idFrontPhoto;

    @Schema(description = "身份证反面照URL")
    private String idBackPhoto;

    @Schema(description = "技能标签（如“擅长大型犬遛狗”）")
    private String skill;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核状态（1-审核中，2-审核通过，3-审核驳回）")
    private Byte auditStatus;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核管理员")
    private String auditAdmin;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
