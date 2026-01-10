package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "优惠券表")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券ID（主键）")
    private Long id;

    @Schema(description = "关联用户ID")
    private Long userId;

    @Schema(description = "优惠券名称（如“满50减10”）")
    private String couponName;

    @Schema(description = "面额（元）")
    private BigDecimal denomination;

    @Schema(description = "使用门槛（元）")
    private BigDecimal minAmount;

    @Schema(description = "生效时间")
    private LocalDateTime startTime;

    @Schema(description = "过期时间")
    private LocalDateTime endTime;

    @Schema(description = "状态（1-未使用，2-已使用，3-已过期）")
    private Byte status;

    @Schema(description = "使用时间")
    private LocalDateTime useTime;

    @Schema(description = "关联订单号")
    private String orderNo;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
