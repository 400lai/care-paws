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
@Schema(description = "社区帖子表")
public class CommunityPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "帖子ID（主键）")
    private Long id;

    @Schema(description = "发布用户ID")
    private Long userId;

    @Schema(description = "帖子标题（可选）")
    private String title;

    @Schema(description = "帖子内容")
    private String content;

    @Schema(description = "图片URL（逗号分隔）")
    private String images;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "状态（1-正常，2-审核中，3-已删除，4-违规下架）")
    private Byte status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
