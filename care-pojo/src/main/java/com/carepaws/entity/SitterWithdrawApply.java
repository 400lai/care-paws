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
@Schema(description = "宠托师提现申请表")
public class SitterWithdrawApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "提现ID（主键）")
    private Long id;

    @Schema(description = "宠托师ID")
    private Long sitterId;

    @Schema(description = "提现单号（唯一）")
    private String withdrawNo;

    @Schema(description = "提现金额")
    private BigDecimal withdrawAmount;

    @Schema(description = "申请时可提现余额（快照）")
    private BigDecimal availableBalance;

    @Schema(description = "开户银行（如微信零钱/支付宝/工商银行卡）")
    private String bankName;

    @Schema(description = "收款人姓名")
    private String accountName;

    @Schema(description = "收款账号（微信openid/支付宝账号/银行卡号）")
    private String accountNo;

    @Schema(description = "提现状态（1-待审核，2-审核通过，3-提现中，4-提现成功，5-审核驳回，6-提现失败）")
    private Byte withdrawStatus;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核管理员")
    private String auditAdmin;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "打款时间")
    private LocalDateTime payTime;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
