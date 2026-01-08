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

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "订单数量")
    private Integer orderCount;

    @Schema(description = "评分，满分5.0")
    private BigDecimal rating;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}