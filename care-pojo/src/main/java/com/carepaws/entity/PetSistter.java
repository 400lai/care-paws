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
@Schema(description = "宠托师拓展信息表")
public class PetSistter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "用户ID，关联user表，唯一")
    private Long userId;

    @Schema(description = "累计收益（元）")
    private BigDecimal totalIncome;

    @Schema(description = "可提现余额（元）")
    private BigDecimal availableBalance;

    @Schema(description = "冻结余额（提现中/待结算）")
    private BigDecimal frozenBalance;

    @Schema(description = "保证金（元）")
    private BigDecimal deposit;

    @Schema(description = "订单数量")
    private Integer orderCount;

    @Schema(description = "服务评分（0.0-5.0）")
    private BigDecimal serviceScore;

    @Schema(description = "接单状态（0-不接单，1-可接单）")
    private Byte receiveOrderStatus;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}