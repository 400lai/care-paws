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
@Schema(description = "服务履约记录表（宠托师上门/完成/上传照片+用户确认）")
public class ServiceFulfillment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "履约记录ID（主键）")
    private Long id;

    @Schema(description = "关联订单ID")
    private Long orderId;

    @Schema(description = "关联服务日期ID（每日履约）")
    private Long serviceDateId;

    @Schema(description = "履约宠托师ID")
    private Long sitterId;

    @Schema(description = "上门打卡时间")
    private LocalDateTime checkInTime;

    @Schema(description = "完成服务打卡时间")
    private LocalDateTime checkOutTime;

    @Schema(description = "服务照片URL（逗号分隔）")
    private String servicePhotos;

    @Schema(description = "履约状态（0-未开始，1-已上门，2-已完成（宠托师端），3-用户已确认，4-用户驳回）")
    private Byte fulfillmentStatus;

    @Schema(description = "用户确认时间")
    private LocalDateTime userConfirmTime;

    @Schema(description = "用户驳回原因")
    private String userRejectReason;

    @Schema(description = "照片审核状态（1-未审核，2-审核通过，3-审核驳回）")
    private Byte auditStatus;

    @Schema(description = "审核管理员")
    private String auditAdmin;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
