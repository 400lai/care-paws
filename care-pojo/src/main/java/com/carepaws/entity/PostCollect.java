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
@Schema(description = "帖子收藏表")
public class PostCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "收藏ID（主键）")
    private Long id;

    @Schema(description = "收藏用户ID")
    private Long userId;

    @Schema(description = "收藏帖子ID")
    private Long postId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否取消收藏（0-未取消，1-已取消）")
    private Byte isCancel;
}
