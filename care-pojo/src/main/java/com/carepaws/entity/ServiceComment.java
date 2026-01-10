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
@Schema(description = "服务评价表")
public class ServiceComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "评价ID（主键）")
    private Long id;

    @Schema(description = "关联订单ID")
    private Long orderId;

    @Schema(description = "评价用户ID（普通用户）")
    private Long userId;

    @Schema(description = "被评价宠托师ID")
    private Long sitterId;

    @Schema(description = "评分（1-5分）")
    private Byte score;

    @Schema(description = "评价内容")
    private String content;

    @Schema(description = "评价图片URL（逗号分隔）")
    private String images;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
