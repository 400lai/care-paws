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
@Schema(description = "服务类型表")
public class ServiceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "服务ID（主键）")
    private Long id;

    @Schema(description = "服务名称（如宠物喂养）")
    private String serviceName;

    @Schema(description = "服务分类（1-基础服务，2-可选服务，3-更多服务）")
    private Byte serviceCategory;

    @Schema(description = "计价类型（1-计次，2-计时）")
    private Byte priceType;

    @Schema(description = "单价（计次：元/次；计时：元/分钟）")
    private BigDecimal unitPrice;

    @Schema(description = "适用宠物类型（1-猫，2-狗，3-通用）")
    private Byte petType;

    @Schema(description = "状态（0-下架，1-上架）")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
