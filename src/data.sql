-- 雷霆路亚俱乐部积分管理系统数据库结构
-- 创建数据库
CREATE DATABASE IF NOT EXISTS lureclub_points CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE lureclub_points;

-- 1. 用户表
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
                       username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
                       password VARCHAR(255) NOT NULL COMMENT '密码',
                       phone VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号',
                       create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                       INDEX idx_username (username),
                       INDEX idx_phone (phone),
                       INDEX idx_create_time (create_time)
) COMMENT '用户表';

-- 2. 管理员表
CREATE TABLE admins (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员ID',
                        username VARCHAR(50) NOT NULL UNIQUE COMMENT '管理员用户名',
                        password VARCHAR(255) NOT NULL COMMENT '密码',
                        real_name VARCHAR(50) COMMENT '真实姓名',
                        is_enabled TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                        INDEX idx_username (username),
                        INDEX idx_create_time (create_time)
) COMMENT '管理员表';

-- 3. 积分表
CREATE TABLE points (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '积分ID',
                        user_id BIGINT NOT NULL COMMENT '用户ID',
                        today_points INT NOT NULL DEFAULT 0 COMMENT '当日积分',
                        last_points_date DATE COMMENT '最后积分更新日期',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                        UNIQUE KEY uk_user_id (user_id),
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        INDEX idx_last_points_date (last_points_date)
) COMMENT '积分表';

-- 4. 积分历史表
CREATE TABLE points_history (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '历史记录ID',
                                user_id BIGINT NOT NULL COMMENT '用户ID',
                                points_type VARCHAR(20) NOT NULL COMMENT '积分类型(EARNED:获得 DEDUCTED:抵扣 ADMIN_ADJUSTMENT:调整)',
                                points INT NOT NULL COMMENT '积分数量(正数为获得，负数为抵扣)',
                                operation_date DATE NOT NULL COMMENT '操作日期',
                                remark VARCHAR(500) COMMENT '备注说明',
                                create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                INDEX idx_user_id (user_id),
                                INDEX idx_operation_date (operation_date),
                                INDEX idx_points_type (points_type),
                                INDEX idx_user_date (user_id, operation_date)
) COMMENT '积分历史表';

-- 5. 公告表
CREATE TABLE announcements (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '公告ID',
                               title VARCHAR(200) NOT NULL COMMENT '公告标题',
                               content TEXT NOT NULL COMMENT '公告内容',
                               publish_date DATE NOT NULL COMMENT '发布日期',
                               is_top TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
                               create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                               INDEX idx_publish_date (publish_date),
                               INDEX idx_is_top (is_top),
                               INDEX idx_create_time (create_time)
) COMMENT '公告表';

-- 6. 奖品表
CREATE TABLE prizes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '奖品ID',
                        name VARCHAR(200) NOT NULL COMMENT '奖品名称',
                        description TEXT NOT NULL COMMENT '奖品描述',
                        image_url VARCHAR(500) COMMENT '奖品图片URL',
                        sort_order INT NOT NULL DEFAULT 0 COMMENT '排序序号',
                        is_enabled TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                        INDEX idx_sort_order (sort_order),
                        INDEX idx_is_enabled (is_enabled),
                        INDEX idx_create_time (create_time)
) COMMENT '奖品表';

-- 7. 留言表
CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '留言ID',
                          user_id BIGINT NOT NULL COMMENT '用户ID',
                          content TEXT NOT NULL COMMENT '留言内容',
                          status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '留言状态(PENDING:待审核 PUBLISHED:已公开 REPLIED:已回复 HIDDEN:已隐藏)',
                          create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          INDEX idx_user_id (user_id),
                          INDEX idx_status (status),
                          INDEX idx_create_time (create_time)
) COMMENT '留言表';

-- 8. 留言回复表
CREATE TABLE message_replies (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '回复ID',
                                 message_id BIGINT NOT NULL COMMENT '留言ID',
                                 content TEXT NOT NULL COMMENT '回复内容',
                                 is_visible TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否公开可见',
                                 create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 is_deleted TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0:未删除 1:已删除)',

                                 FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
                                 INDEX idx_message_id (message_id),
                                 INDEX idx_is_visible (is_visible),
                                 INDEX idx_create_time (create_time)
) COMMENT '留言回复表';

-- 初始化数据

-- 插入默认管理员账号
-- 用户名：admin，密码：admin123
INSERT INTO admins (id, username, password, real_name, is_enabled, create_time, update_time, is_deleted)
VALUES (1, 'admin', '$2a$10$N.zmDo8Qb9XqjIghFDkF8.YNMtU0wPW8qPa0g7Q8Q5xF8qJ8YgK1K', '系统管理员', 1, NOW(), NOW(), 0)
    ON DUPLICATE KEY UPDATE id = id;

-- 插入示例公告
INSERT INTO announcements (id, title, content, publish_date, is_top, create_time, update_time, is_deleted)
VALUES
    (1, '欢迎来到雷霆路亚俱乐部', '欢迎各位钓友加入雷霆路亚俱乐部！在这里您可以体验专业的路亚钓鱼，获得积分奖励，参与排行榜竞赛。', CURDATE(), 1, NOW(), NOW(), 0),
    (2, '积分系统使用说明', '积分系统说明：当日获得的积分将在次日转为有效积分，有效积分可用于抵扣门票费用。1积分=1元。', CURDATE(), 1, NOW(), NOW(), 0),
    (3, '钓场开放时间调整', '自本月起，钓场开放时间调整为：周一至周日 06:00-18:00，请各位钓友合理安排时间。', CURDATE(), 0, NOW(), NOW(), 0)
    ON DUPLICATE KEY UPDATE id = id;

-- 插入示例奖品
INSERT INTO prizes (id, name, description, image_url, sort_order, is_enabled, create_time, update_time, is_deleted)
VALUES
    (1, '专业路亚竿', '高品质碳素路亚竿，长度2.1米，适合各种路亚钓法，手感轻便，灵敏度高。', '/uploads/prizes/lure_rod.jpg', 1, 1, NOW(), NOW(), 0),
    (2, '路亚饵套装', '包含各种颜色和类型的路亚饵，适合不同水域和鱼种，让您的钓鱼之旅更加丰富。', '/uploads/prizes/lure_baits.jpg', 2, 1, NOW(), NOW(), 0),
    (3, '专业钓箱', '多功能钓箱，内含多个储物格，可存放路亚饵、工具等，是路亚钓手的必备装备。', '/uploads/prizes/tackle_box.jpg', 3, 1, NOW(), NOW(), 0),
    (4, '防晒钓鱼服', '专业防晒钓鱼服，UPF50+防护，透气舒适，是户外钓鱼的最佳选择。', '/uploads/prizes/fishing_shirt.jpg', 4, 1, NOW(), NOW(), 0)
    ON DUPLICATE KEY UPDATE id = id;