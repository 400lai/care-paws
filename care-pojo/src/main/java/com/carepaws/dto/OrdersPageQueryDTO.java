package com.carepaws.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrdersPageQueryDTO implements Serializable {
    // 当前页码
    private int page;
    // 每页显示记录数
    private int pageSize;

    private String number;
    //
    private  String phone;
    // 订单状态
    private Integer status;
    // 下单时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    // 结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    // 用户id
    private Long userId;

}
