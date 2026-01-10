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
@Schema(description = "宠托师资金流水表（所有资金变动记录）")
public class SitterFundFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "流水ID（主键）")
    private Long id;

    @Schema(description = "宠托师ID")
    private Long sitterId;

    @Schema(description = "流水类型（1-订单收益入账，2-提现申请（冻结），3-提现成功（扣减），4-提现失败（解冻），5-订单退款（扣减））")
    private Byte flowType;

    @Schema(description = "变动金额（正数=增加，负数=减少）")
    private BigDecimal amount;

    @Schema(description = "变动后余额")
    private BigDecimal balanceAfter;

    @Schema(description = "关联单号（结算ID/提现单号/订单号）")
    private String relatedNo;

    @Schema(description = "关联类型（settlement/withdraw/order）")
    private String relatedType;

    @Schema(description = "流水备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
