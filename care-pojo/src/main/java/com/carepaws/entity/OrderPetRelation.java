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
@Schema(description = "订单-宠物关联表（一对多）")
public class OrderPetRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "关联ID（主键）")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "宠物ID")
    private Long petId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
