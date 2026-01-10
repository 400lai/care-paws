package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单明细表（每日服务项）")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "服务明细ID（主键）")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "关联服务日期ID")
    private Long serviceDateId;

    @Schema(description = "关联服务类型ID")
    private Long serviceId;

    @Schema(description = "数量（计次：次数；计时：分钟数）")
    private Integer quantity;

    @Schema(description = "单价（快照）")
    private BigDecimal unitPrice;

    @Schema(description = "该项小计（单价×数量）")
    private BigDecimal subTotal;

    @Schema(description = "该服务项是否完成（0-未完成，1-已完成）")
    private Byte isCompleted;

    @Schema(description = "服务项完成时间")
    private LocalDateTime completeTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}