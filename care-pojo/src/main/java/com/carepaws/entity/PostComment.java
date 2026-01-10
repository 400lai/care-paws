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
@Schema(description = "帖子评论区表（支持多级回复）")
public class PostComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "评论ID（主键）")
    private Long id;

    @Schema(description = "关联帖子ID")
    private Long postId;

    @Schema(description = "评论用户ID")
    private Long userId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论ID（NULL为一级评论）")
    private Long parentId;

    @Schema(description = "评论点赞数")
    private Integer likeCount;

    @Schema(description = "状态（1-正常，2-已删除，3-违规）")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
