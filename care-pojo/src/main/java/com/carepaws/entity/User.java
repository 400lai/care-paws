package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "C端用户信息表")
public class User implements Serializable {


    private static final long serialVersionUID = 1L;

    @Schema(description = "主键，自增")
    private Long id;

    @Schema(description = "微信用户的唯一标识")
    private String openid;

    @Schema(description = "微信用户头像路径")
    private String avatar;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别（0-未知，1-男生，2-女生）")
    private String sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "当前身份（1-普通用户，2-喂养员）")
    private Byte identityType;

    @Schema(description = "喂养员申请状态（0-未申请，1-审核中，2-审核通过，3-审核驳回）")
    private Byte applyStaffStatus;

    @Schema(description = "省份编码")   // 如11=北京,50=重庆，32=江苏
    private String provinceCode;

    @Schema(description = "城市编码")
    private String cityCode;

    @Schema(description = "区县编码")   // 如110108=海淀区，320102=玄武区
    private String districtCode;

    @Schema(description = "个人简介")
    private String introduction;

    @Schema(description = "身份证号")
    private String idNumber;


    @Schema(description = "注册时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}