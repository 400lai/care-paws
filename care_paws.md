```sql
-- 创建数据库（若不存在）
CREATE DATABASE IF NOT EXISTS care_paws CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE care_paws;

-- 1. 基础用户表
CREATE TABLE IF NOT EXISTS `user` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID（普通用户/宠托师通用，主键）',
    openid VARCHAR(45) NOT NULL COMMENT '微信用户唯一标识',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '微信用户头像路径',
    nickname VARCHAR(32) DEFAULT NULL COMMENT '昵称',
    phone VARCHAR(11) DEFAULT NULL COMMENT '手机号',
    sex VARCHAR(2) DEFAULT NULL COMMENT '性别（0-未知，1-男生，2-女生）',
    age TINYINT DEFAULT NULL COMMENT '年龄',
    identity_type TINYINT(2) NOT NULL DEFAULT '1' COMMENT '当前身份（1-普通用户，2-宠托师）',
    apply_sitter_status TINYINT(2) NOT NULL DEFAULT '0' COMMENT '宠托师申请状态（0-未申请，1-审核中，2-审核通过，3-审核驳回）',
    province_code CHAR(2) DEFAULT NULL COMMENT '省份编码（如11=北京）',
    city_code CHAR(4) DEFAULT NULL COMMENT '城市编码',
    district_code CHAR(6) DEFAULT NULL COMMENT '区县编码（如110108=海淀区）',
    introduction VARCHAR(200) DEFAULT NULL COMMENT '个人简介',
    id_number VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_openid (openid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基础用户表（C端用户信息）';

-- 2. 申请认证表
CREATE TABLE IF NOT EXISTS applications (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '认证ID（主键）',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    id_number VARCHAR(18) NOT NULL COMMENT '身份证号（实名认证用）',
    id_front_photo VARCHAR(255) NOT NULL COMMENT '身份证正面照URL',
    id_back_photo VARCHAR(255) NOT NULL COMMENT '身份证反面照URL',
    skill VARCHAR(255) DEFAULT NULL COMMENT '技能标签（如“擅长大型犬遛狗”）',
    apply_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    audit_status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '审核状态（1-审核中，2-审核通过，3-审核驳回）',
    reject_reason VARCHAR(255) DEFAULT NULL COMMENT '驳回原因',
    audit_time DATETIME DEFAULT NULL COMMENT '审核时间',
    audit_admin VARCHAR(32) DEFAULT NULL COMMENT '审核管理员',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='申请认证表（宠托师实名认证）';

-- 3. 宠物主人拓展信息表
CREATE TABLE IF NOT EXISTS pet_owner (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    fans_count INT NOT NULL DEFAULT '0' COMMENT '粉丝数',
    follow_count INT NOT NULL DEFAULT '0' COMMENT '关注数',
    collect_count INT NOT NULL DEFAULT '0' COMMENT '收藏数',
    balance DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '账户余额',
    coupon_count INT NOT NULL DEFAULT '0' COMMENT '优惠券数',
    points INT NOT NULL DEFAULT '0' COMMENT '积分',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id),
    CONSTRAINT fk_pet_owner_user FOREIGN KEY (user_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠物主人拓展信息表';

-- 4. 宠托师拓展信息表
CREATE TABLE IF NOT EXISTS pet_sitter (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    total_income DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '累计收益（元）',
    available_balance DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '可提现余额（元）',
    frozen_balance DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结余额（提现中/待结算）',
    deposit DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '保证金（元）',
    order_count INT NOT NULL DEFAULT '0' COMMENT '订单数量',
    service_score DECIMAL(3,1) DEFAULT '5.0' COMMENT '服务评分（0.0-5.0）',
    receive_order_status TINYINT(2) NOT NULL DEFAULT '0' COMMENT '接单状态（0-不接单，1-可接单）',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id),
    CONSTRAINT fk_pet_sitter_user FOREIGN KEY (user_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠托师拓展信息表';

-- 5. 优惠券表
CREATE TABLE IF NOT EXISTS coupon (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '优惠券ID（主键）',
    user_id BIGINT(20) NOT NULL COMMENT '关联用户ID',
    coupon_name VARCHAR(32) NOT NULL COMMENT '优惠券名称（如“满50减10”）',
    denomination DECIMAL(10,2) NOT NULL COMMENT '面额（元）',
    min_amount DECIMAL(10,2) NOT NULL COMMENT '使用门槛（元）',
    start_time DATETIME NOT NULL COMMENT '生效时间',
    end_time DATETIME NOT NULL COMMENT '过期时间',
    status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '状态（1-未使用，2-已使用，3-已过期）',
    use_time DATETIME DEFAULT NULL COMMENT '使用时间',
    order_no VARCHAR(32) DEFAULT NULL COMMENT '关联订单号',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_order_no (order_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 6. 宠物表
CREATE TABLE IF NOT EXISTS pet (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '宠物ID（主键）',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    pet_name VARCHAR(32) NOT NULL COMMENT '宠物昵称',
    `type` TINYINT NOT NULL COMMENT '宠物类型（1-猫，2-狗，3-其他）',
    breed VARCHAR(50) DEFAULT NULL COMMENT '宠物品种（如“布偶猫”）',
    gender TINYINT NOT NULL DEFAULT '0' COMMENT '宠物性别（0-不确定，1-公，2-母）',
    age TINYINT NOT NULL DEFAULT '0' COMMENT '宠物年龄（岁）',
    weight DECIMAL(5,2) DEFAULT NULL COMMENT '体重（kg）',
    remark VARCHAR(255) DEFAULT NULL COMMENT '特殊备注（如“怕生人”）',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '宠物头像URL',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    CONSTRAINT fk_pet_user FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠物档案信息表';

-- 7. 地址簿表
CREATE TABLE IF NOT EXISTS address_book (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    consignee VARCHAR(32) NOT NULL COMMENT '收货人',
    sex VARCHAR(2) DEFAULT NULL COMMENT '性别',
    phone VARCHAR(11) NOT NULL COMMENT '手机号',
    province_code VARCHAR(12) DEFAULT NULL COMMENT '省份编码',
    province_name VARCHAR(32) DEFAULT NULL COMMENT '省份名称',
    city_code VARCHAR(12) DEFAULT NULL COMMENT '城市编码',
    city_name VARCHAR(32) DEFAULT NULL COMMENT '城市名称',
    district_code VARCHAR(12) DEFAULT NULL COMMENT '区县编码',
    district_name VARCHAR(32) DEFAULT NULL COMMENT '区县名称',
    detail VARCHAR(200) NOT NULL COMMENT '详细地址（门牌号）',
    latitude DECIMAL(10,8) DEFAULT NULL COMMENT '纬度',
    longitude DECIMAL(11,8) DEFAULT NULL COMMENT '经度',
    label VARCHAR(100) DEFAULT NULL COMMENT '标签（公司/家/学校）',
    is_default TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址（1-是，0-否）',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地址簿表（C端用户收货地址）';

-- 8. 社区帖子表
CREATE TABLE IF NOT EXISTS community_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '帖子ID（主键）',
    user_id BIGINT NOT NULL COMMENT '发布用户ID',
    title VARCHAR(64) DEFAULT NULL COMMENT '帖子标题（可选）',
    content VARCHAR(2000) NOT NULL COMMENT '帖子内容',
    images VARCHAR(2000) DEFAULT NULL COMMENT '图片URL（逗号分隔）',
    like_count INT(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
    comment_count INT(11) NOT NULL DEFAULT '0' COMMENT '评论数',
    collect_count INT(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
    status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '状态（1-正常，2-审核中，3-已删除，4-违规下架）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='社区帖子表';

-- 9. 帖子评论表
CREATE TABLE IF NOT EXISTS post_comment (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID（主键）',
    post_id BIGINT NOT NULL COMMENT '关联帖子ID',
    user_id BIGINT NOT NULL COMMENT '评论用户ID',
    content VARCHAR(500) NOT NULL COMMENT '评论内容',
    parent_id BIGINT DEFAULT NULL COMMENT '父评论ID（NULL为一级评论）',
    like_count INT(11) NOT NULL DEFAULT '0' COMMENT '评论点赞数',
    status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '状态（1-正常，2-已删除，3-违规）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_post_id (post_id),
    KEY idx_user_id (user_id),
    KEY idx_parent_id (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子评论区表（支持多级回复）';

-- 10. 点赞记录表
CREATE TABLE IF NOT EXISTS like_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '点赞ID（主键）',
    user_id BIGINT NOT NULL COMMENT '点赞用户ID',
    target_type TINYINT(2) NOT NULL COMMENT '点赞类型（1-帖子，2-评论）',
    target_id BIGINT NOT NULL COMMENT '点赞目标ID（帖子ID/评论ID）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    is_cancel TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否取消点赞（0-未取消，1-已取消）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_target (user_id, target_type, target_id),
    KEY idx_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞记录表（防重复点赞）';

-- 11. 帖子收藏表
CREATE TABLE IF NOT EXISTS post_collect (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '收藏ID（主键）',
    user_id BIGINT(20) NOT NULL COMMENT '收藏用户ID',
    post_id BIGINT(20) NOT NULL COMMENT '收藏帖子ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    is_cancel TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否取消收藏（0-未取消，1-已取消）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_post (user_id, post_id),
    KEY idx_post_id (post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子收藏表';

-- 12. 服务类型表
CREATE TABLE IF NOT EXISTS service_type (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '服务ID（主键）',
    service_name VARCHAR(32) NOT NULL COMMENT '服务名称（如宠物喂养）',
    service_category TINYINT(2) NOT NULL COMMENT '服务分类（1-基础服务，2-可选服务，3-更多服务）',
    price_type TINYINT(2) NOT NULL COMMENT '计价类型（1-计次，2-计时）',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价（计次：元/次；计时：元/分钟）',
    pet_type TINYINT(2) NOT NULL COMMENT '适用宠物类型（1-猫，2-狗，3-通用）',
    status TINYINT(1) NOT NULL DEFAULT '1' COMMENT '状态（0-下架，1-上架）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_service_name (service_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务类型表';

-- 初始化服务数据（贴合价格规则）
INSERT INTO `service_type` (`service_name`, `service_category`, `price_type`, `unit_price`, `pet_type`) VALUES
('宠物喂养', 1, 1, 50.00, 3),  -- 基础服务-计次-50元/次-通用
('遛狗', 2, 2, 1.00, 2),       -- 可选服务-计时-1元/分钟-仅狗
('陪玩', 2, 2, 1.00, 3),       -- 可选服务-计时-1元/分钟-通用
('清洁', 3, 1, 40.00, 3),      -- 更多服务-计次-40元/次-通用
('喂药', 3, 1, 20.00, 3),      -- 更多服务-计次-20元/次-通用
('护理', 3, 1, 20.00, 3);      -- 更多服务-计次-20元/次-通用

-- 13. 订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID（主键）',
    order_no VARCHAR(32) NOT NULL COMMENT '订单编号（唯一）',
    status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '订单状态（1-待付款，2-待接单，3-待上门，4-服务中，5-宠托师已完成，6-用户已确认（订单结束），7-已取消）',
    user_id BIGINT(20) NOT NULL COMMENT '下单用户ID',
    address_book_id BIGINT(20) NOT NULL COMMENT '地址ID',
    sitter_id BIGINT(20) DEFAULT NULL COMMENT '服务宠托师ID',
    dispatch_type TINYINT(2) NOT NULL DEFAULT '1' COMMENT '派单方式（1-系统派单，2-自选）',
    key_handover VARCHAR(32) DEFAULT NULL COMMENT '钥匙交接方式',
    expect_time DATETIME DEFAULT NULL COMMENT '期望上门时间',
    expect_sitter_gender TINYINT(2) DEFAULT '0' COMMENT '期望人员性别（0-不限，1-男，2-女）',
    service_remark VARCHAR(500) DEFAULT NULL COMMENT '服务叮嘱',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    discount_amount DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '优惠金额',
    pay_amount DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    pay_time DATETIME DEFAULT NULL COMMENT '支付时间',
    finish_time DATETIME DEFAULT NULL COMMENT '订单最终完成时间（用户确认后）',
    cancel_time DATETIME DEFAULT NULL COMMENT '取消时间',
    cancel_reason VARCHAR(255) DEFAULT NULL COMMENT '取消原因',
    phone VARCHAR(11) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号（冗余）',
    address VARCHAR(255) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址（冗余）',
    consignee VARCHAR(32) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人（冗余）',
    pay_status TINYINT NOT NULL DEFAULT '0' COMMENT '支付状态（0-未支付，1-已支付，2-退款）',
    last_fulfillment_status TINYINT(2) DEFAULT '0' COMMENT '最新履约状态（关联service_fulfillment表）',
    settlement_id BIGINT(20) DEFAULT NULL COMMENT '关联收益结算ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_sitter_id (sitter_id),
    KEY idx_address_book_id (address_book_id),
    KEY idx_status (status),
    KEY idx_last_fulfillment_status (last_fulfillment_status),
    KEY idx_settlement_id (settlement_id),
    CONSTRAINT fk_order_settlement FOREIGN KEY (settlement_id) REFERENCES sitter_income_settlement(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- 14. 订单-宠物关联表
CREATE TABLE IF NOT EXISTS order_pet_relation (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID（主键）',
    order_id BIGINT(20) NOT NULL COMMENT '订单ID',
    pet_id BIGINT(20) NOT NULL COMMENT '宠物ID',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_pet_id (pet_id),
    CONSTRAINT fk_relation_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_relation_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-宠物关联表（一对多）';

-- 15. 服务日期表
CREATE TABLE IF NOT EXISTS service_date (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日期ID（主键）',
    order_id BIGINT(20) NOT NULL COMMENT '订单ID',
    service_date DATE NOT NULL COMMENT '服务日期（YYYY-MM-DD）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    CONSTRAINT fk_date_order FOREIGN KEY (order_id) REFERENCES orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务日期表（多日期预约）';

-- 16. 订单明细表
CREATE TABLE IF NOT EXISTS order_detail (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '服务明细ID（主键）',
    order_id BIGINT(20) NOT NULL COMMENT '订单ID',
    service_date_id BIGINT(20) NOT NULL COMMENT '关联服务日期ID',
    service_id BIGINT(20) NOT NULL COMMENT '关联服务类型ID',
    quantity INT(11) NOT NULL COMMENT '数量（计次：次数；计时：分钟数）',
    unit_price DECIMAL(10,2) NOT NULL COMMENT '单价（快照）',
    sub_total DECIMAL(10,2) NOT NULL COMMENT '该项小计（单价×数量）',
    is_completed TINYINT(1) NOT NULL DEFAULT '0' COMMENT '该服务项是否完成（0-未完成，1-已完成）',
    complete_time DATETIME DEFAULT NULL COMMENT '服务项完成时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id),
    KEY idx_service_date_id (service_date_id),
    KEY idx_service_id (service_id),
    CONSTRAINT fk_detail_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_detail_date FOREIGN KEY (service_date_id) REFERENCES service_date(id),
    CONSTRAINT fk_detail_service FOREIGN KEY (service_id) REFERENCES service_type(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表（每日服务项）';

-- 17. 支付记录表
CREATE TABLE IF NOT EXISTS pay_record (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '支付记录ID（主键）',
    order_no VARCHAR(32) NOT NULL COMMENT '关联订单编号',
    pay_amount DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    transaction_id VARCHAR(64) DEFAULT NULL COMMENT '微信支付交易号',
    status TINYINT(2) NOT NULL DEFAULT '0' COMMENT '支付状态（0-待支付，1-支付成功，2-支付失败）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_order_no (order_no),
    KEY idx_transaction_id (transaction_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';

-- 18. 服务评价表
CREATE TABLE IF NOT EXISTS service_comment (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID（主键）',
    order_id BIGINT NOT NULL COMMENT '关联订单ID',
    user_id BIGINT NOT NULL COMMENT '评价用户ID（普通用户）',
    sitter_id BIGINT NOT NULL COMMENT '被评价宠托师ID',
    score TINYINT(1) NOT NULL COMMENT '评分（1-5分）',
    content VARCHAR(500) DEFAULT NULL COMMENT '评价内容',
    images VARCHAR(1000) DEFAULT NULL COMMENT '评价图片URL（逗号分隔）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_id (order_id),
    KEY idx_user_id (user_id),
    KEY idx_sitter_id (sitter_id),
    CONSTRAINT fk_comment_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES `user`(id),
    CONSTRAINT fk_comment_sitter FOREIGN KEY (sitter_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务评价表';

-- 19. 服务履约记录表
CREATE TABLE IF NOT EXISTS service_fulfillment (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '履约记录ID（主键）',
    order_id BIGINT(20) NOT NULL COMMENT '关联订单ID',
    service_date_id BIGINT(20) NOT NULL COMMENT '关联服务日期ID（每日履约）',
    sitter_id BIGINT(20) NOT NULL COMMENT '履约宠托师ID',
    check_in_time DATETIME DEFAULT NULL COMMENT '上门打卡时间',
    check_out_time DATETIME DEFAULT NULL COMMENT '完成服务打卡时间',
    service_photos VARCHAR(2000) DEFAULT NULL COMMENT '服务照片URL（逗号分隔）',
    fulfillment_status TINYINT(2) NOT NULL DEFAULT '0' COMMENT '履约状态（0-未开始，1-已上门，2-已完成（宠托师端），3-用户已确认，4-用户驳回）',
    user_confirm_time DATETIME DEFAULT NULL COMMENT '用户确认时间',
    user_reject_reason VARCHAR(255) DEFAULT NULL COMMENT '用户驳回原因',
    audit_status TINYINT(2) DEFAULT '1' COMMENT '照片审核状态（1-未审核，2-审核通过，3-审核驳回）',
    audit_admin VARCHAR(32) DEFAULT NULL COMMENT '审核管理员',
    audit_time DATETIME DEFAULT NULL COMMENT '审核时间',
    audit_remark VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_date (order_id, service_date_id) COMMENT '一个订单日期仅一条履约记录',
    KEY idx_order_id (order_id),
    KEY idx_service_date_id (service_date_id),
    KEY idx_sitter_id (sitter_id),
    KEY idx_fulfillment_status (fulfillment_status),
    CONSTRAINT fk_fulfillment_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_fulfillment_date FOREIGN KEY (service_date_id) REFERENCES service_date(id),
    CONSTRAINT fk_fulfillment_sitter FOREIGN KEY (sitter_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务履约记录表（宠托师上门/完成/上传照片+用户确认）';

-- 20. 宠托师收益结算表
CREATE TABLE IF NOT EXISTS sitter_income_settlement (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '结算ID（主键）',
    order_id BIGINT(20) NOT NULL COMMENT '关联订单ID',
    sitter_id BIGINT(20) NOT NULL COMMENT '宠托师ID',
    settlement_amount DECIMAL(10,2) NOT NULL COMMENT '结算金额（宠托师实际可得）',
    platform_fee DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '平台服务费',
    order_total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额（快照）',
    settlement_status TINYINT(2) NOT NULL DEFAULT '0' COMMENT '结算状态（0-待结算，1-已结算（用户确认后），2-已退款，3-结算失败）',
    settlement_time DATETIME DEFAULT NULL COMMENT '结算完成时间（用户确认后）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_id (order_id) COMMENT '一个订单仅一条结算记录',
    KEY idx_sitter_id (sitter_id),
    KEY idx_settlement_status (settlement_status),
    CONSTRAINT fk_settlement_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_settlement_sitter FOREIGN KEY (sitter_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠托师收益结算表（用户确认后结算）';

-- 21. 宠托师提现申请表
CREATE TABLE IF NOT EXISTS sitter_withdraw_apply (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '提现ID（主键）',
    sitter_id BIGINT(20) NOT NULL COMMENT '宠托师ID',
    withdraw_no VARCHAR(32) NOT NULL COMMENT '提现单号（唯一）',
    withdraw_amount DECIMAL(10,2) NOT NULL COMMENT '提现金额',
    available_balance DECIMAL(10,2) NOT NULL COMMENT '申请时可提现余额（快照）',
    bank_name VARCHAR(64) DEFAULT NULL COMMENT '开户银行（如微信零钱/支付宝/工商银行卡）',
    account_name VARCHAR(32) DEFAULT NULL COMMENT '收款人姓名',
    account_no VARCHAR(64) DEFAULT NULL COMMENT '收款账号（微信openid/支付宝账号/银行卡号）',
    withdraw_status TINYINT(2) NOT NULL DEFAULT '1' COMMENT '提现状态（1-待审核，2-审核通过，3-提现中，4-提现成功，5-审核驳回，6-提现失败）',
    apply_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    audit_time DATETIME DEFAULT NULL COMMENT '审核时间',
    audit_admin VARCHAR(32) DEFAULT NULL COMMENT '审核管理员',
    audit_remark VARCHAR(255) DEFAULT NULL COMMENT '审核备注',
    pay_time DATETIME DEFAULT NULL COMMENT '打款时间',
    fail_reason VARCHAR(255) DEFAULT NULL COMMENT '失败原因',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_withdraw_no (withdraw_no),
    KEY idx_sitter_id (sitter_id),
    KEY idx_withdraw_status (withdraw_status),
    CONSTRAINT fk_withdraw_sitter FOREIGN KEY (sitter_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠托师提现申请表';

-- 22. 宠托师资金流水表
CREATE TABLE IF NOT EXISTS sitter_fund_flow (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '流水ID（主键）',
    sitter_id BIGINT(20) NOT NULL COMMENT '宠托师ID',
    flow_type TINYINT(2) NOT NULL COMMENT '流水类型（1-订单收益入账，2-提现申请（冻结），3-提现成功（扣减），4-提现失败（解冻），5-订单退款（扣减））',
    amount DECIMAL(10,2) NOT NULL COMMENT '变动金额（正数=增加，负数=减少）',
    balance_after DECIMAL(10,2) NOT NULL COMMENT '变动后余额',
    related_no VARCHAR(32) DEFAULT NULL COMMENT '关联单号（结算ID/提现单号/订单号）',
    related_type VARCHAR(32) DEFAULT NULL COMMENT '关联类型（settlement/withdraw/order）',
    remark VARCHAR(255) DEFAULT NULL COMMENT '流水备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_sitter_id (sitter_id),
    KEY idx_flow_type (flow_type),
    KEY idx_related_no (related_no),
    CONSTRAINT fk_fund_sitter FOREIGN KEY (sitter_id) REFERENCES `user`(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='宠托师资金流水表（所有资金变动记录）';
```

