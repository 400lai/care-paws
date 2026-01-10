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
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="用户地址表")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "收货人")
    private String consignee;

    @Schema(description = "性别：1男 2女") // 1男 2女
    private Byte sex;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "省级区划编号")
    private String provinceCode;

    @Schema(description = "省份名称")
    private String provinceName;

    @Schema(description = "市级区划编号")
    private String cityCode;

    @Schema(description = "市级名称")
    private String cityName;

    @Schema(description = "区级区划编号")
    private String districtCode;

    @Schema(description = "区县名称")
    private String districtName;

    @Schema(description = "详细地址（门牌号）")
    private String detail;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "标签（公司/家/学校）")
    private String label;

    @Schema(description = "是否默认地址（1-是，0-否）")
    private Byte isDefault;

}
