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

    @Schema(description = "宠物ID（主键）")
    private Long id;

    @Schema(description = "关联用户ID")
    private Long userId;

    @Schema(description = "宠物昵称")
    private String petName;

    @Schema(description = "宠物类型（1-猫，2-狗，3-其他）")
    private Byte type;

    @Schema(description = "宠物品种（如“布偶猫”）")
    private String breed;

    @Schema(description = "宠物性别（0-不确定，1-公，2-母）")
    private Byte gender;

    @Schema(description = "宠物年龄（岁）")
    private Byte age;

    @Schema(description = "体重（kg）")
    private BigDecimal weight;

    @Schema(description = "宠物描述，性格描述+绝育信息+疫苗信息")    // 特殊备注（如“怕生人”）
    private String remark;

    @Schema(description = "宠物头像URL")
    private String avatar;
}