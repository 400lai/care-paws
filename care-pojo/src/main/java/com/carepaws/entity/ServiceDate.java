package com.carepaws.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "服务日期表（多日期预约）")
public class ServiceDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "日期ID（主键）")
    private Long id;

    @Schema(description = "订单ID")
    private Long orderId;

    @Schema(description = "服务日期（YYYY-MM-DD）")
    private LocalDate serviceDate;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
