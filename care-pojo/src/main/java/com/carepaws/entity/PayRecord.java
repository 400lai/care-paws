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
@Schema(description = "支付记录表")
public class PayRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "支付记录ID（主键）")
    private Long id;

    @Schema(description = "关联订单编号")
    private String orderNo;

    @Schema(description = "支付金额")
    private BigDecimal payAmount;

    @Schema(description = "微信支付交易号")
    private String transactionId;

    @Schema(description = "支付状态（0-待支付，1-支付成功，2-支付失败）")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
