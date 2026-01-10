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
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * 订单状态 1待付款 2待接单 3待上门 4进行中 5已完成 6已取消
     */
    public static final Byte PENDING_PAYMENT = 1;
    public static final Byte TO_BE_CONFIRMED = 2;
    public static final Byte TO_BE_VISITED  = 3;
    public static final Byte IN_PROGRESS = 4;
    public static final Byte COMPLETED = 5;
    public static final Byte CANCELLED = 6;

    /**
     * 支付状态 0未支付 1已支付 2退款
     */
    public static final Byte UN_PAID = 0;
    public static final Byte PAID = 1;
    public static final Byte REFUND = 2;

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID（主键）")
    private Long id;

    @Schema(description = "订单编号（唯一）")
    private String orderNo;

    @Schema(description = "订单状态（1-待付款，2-待接单，3-待上门，4-进行中，5-已完成，6-已取消）")
    private Byte status;

    @Schema(description = "下单用户ID")
    private Long userId;

    @Schema(description = "地址ID")
    private Long addressBookId;

    @Schema(description = "服务喂养员ID")
    private Long staffId;

    @Schema(description = "派单方式（1-系统派单，2-自选）")
    private Byte dispatchType;

    @Schema(description = "钥匙交接方式")
    private String keyHandover;

    @Schema(description = "期望上门时间")
    private LocalDateTime expectTime;

    @Schema(description = "期望人员性别（0-不限，1-男，2-女）")
    private Byte expectStaffGender;

    @Schema(description = "服务叮嘱")
    private String serviceRemark;

    @Schema(description = "订单总金额")
    private BigDecimal totalAmount;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "实付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "完成时间")
    private LocalDateTime finishTime;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "手机号（冗余）")
    private String phone;

    @Schema(description = "详细地址（冗余）")
    private String address;

    @Schema(description = "收货人（冗余）")
    private String consignee;

    @Schema(description = "支付状态（0-未支付，1-已支付，2-退款）")
    private Byte payStatus;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
