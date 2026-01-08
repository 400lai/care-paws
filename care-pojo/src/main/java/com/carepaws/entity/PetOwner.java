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
@Schema(description = "宠物主人拓展信息表")
public class PetOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "用户ID，关联user表，唯一")
    private Long userId;

    @Schema(description = "粉丝数")
    private Integer fansCount;

    @Schema(description = "关注数")
    private Integer followCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "余额")
    private BigDecimal balance;

    @Schema(description = "优惠券数")
    private Integer couponCount;

    @Schema(description = "积分")
    private Integer points;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}