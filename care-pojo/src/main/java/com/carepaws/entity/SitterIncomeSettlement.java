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
@Schema(description = "宠托师收益结算表（用户确认后结算）")
public class SitterIncomeSettlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "结算ID（主键）")
    private Long id;

    @Schema(description = "关联订单ID")
    private Long orderId;

    @Schema(description = "宠托师ID")
    private Long sitterId;

    @Schema(description = "结算金额（宠托师实际可得）")
    private BigDecimal settlementAmount;

    @Schema(description = "平台服务费")
    private BigDecimal platformFee;

    @Schema(description = "订单总金额（快照）")
    private BigDecimal orderTotalAmount;

    @Schema(description = "结算状态（0-待结算，1-已结算（用户确认后），2-已退款，3-结算失败）")
    private Byte settlementStatus;

    @Schema(description = "结算完成时间（用户确认后）")
    private LocalDateTime settlementTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
