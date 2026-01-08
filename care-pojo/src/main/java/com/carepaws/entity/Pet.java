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
@Schema(description = "宠物信息表")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "宠物ID，自增")
    private Long id;

    @Schema(description = "用户ID，关联user表")
    private Long userId;

    @Schema(description = "宠物昵称")
    private String nickname;

    @Schema(description = "宠物类型：1猫 2狗 3其他")
    private Integer type;

    @Schema(description = "宠物品种，如布偶猫，暹罗猫")
    private String breed;

    @Schema(description = "宠物性别：0不确定 1公 2母")
    private Integer gender;

    @Schema(description = "宠物年龄")
    private Integer age;

    @Schema(description = "体重")
    private BigDecimal weight;

    @Schema(description = "宠物头像路径")
    private String avatar;

    @Schema(description = "宠物描述，性格描述+绝育信息+疫苗信息")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}