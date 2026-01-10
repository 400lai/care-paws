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
@Schema(description = "点赞记录表（防重复点赞）")
public class LikeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "点赞ID（主键）")
    private Long id;

    @Schema(description = "点赞用户ID")
    private Long userId;

    @Schema(description = "点赞类型（1-帖子，2-评论）")
    private Byte targetType;

    @Schema(description = "点赞目标ID（帖子ID/评论ID）")
    private Long targetId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否取消点赞（0-未取消，1-已取消）")
    private Byte isCancel;
}
