-- 雷霆路亚俱乐部积分管理系统 - 完整数据库初始化脚本
-- 包含数据库创建、表结构创建、索引创建、示例数据插入
-- 执行前请确保MySQL服务已启动且有足够权限

-- ========================================
-- 第一部分：数据库创建和配置
-- ========================================

-- 删除已存在的数据库（谨慎使用）
-- DROP DATABASE IF EXISTS lureclub_points;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS lureclub_points
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE lureclub_points;

-- 设置SQL模式
SET sql_mode = 'STRICT_TRANS_TABLES,NO_ZERO_DATE,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO';

-- ========================================
-- 第二部分：表结构创建
-- ========================================

-- 1. 用户表
DROP TABLE IF EXISTS users;
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
                       INDEX idx_create_time (create_time),
                       INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 管理员表
DROP TABLE IF EXISTS admins;
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
                        INDEX idx_create_time (create_time),
                        INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 3. 积分表
DROP TABLE IF EXISTS points;
CREATE TABLE points (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '积分ID',
                        user_id BIGINT NOT NULL COMMENT '用户ID',
                        today_points INT NOT NULL DEFAULT 0 COMMENT '当日积分',
                        effective_points INT NOT NULL DEFAULT 0 COMMENT '有效积分',
                        last_points_date DATE COMMENT '最后积分更新日期',
                        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                        UNIQUE KEY uk_user_id (user_id),
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                        INDEX idx_last_points_date (last_points_date),
                        INDEX idx_effective_points (effective_points),
                        INDEX idx_today_points (today_points)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分表';

-- 4. 积分历史表
DROP TABLE IF EXISTS points_history;
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
                                INDEX idx_user_date (user_id, operation_date),
                                INDEX idx_points_type_date (points_type, operation_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分历史表';

-- 5. 公告表
DROP TABLE IF EXISTS announcements;
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
                               INDEX idx_create_time (create_time),
                               INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

-- 6. 奖品表
DROP TABLE IF EXISTS prizes;
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
                        INDEX idx_create_time (create_time),
                        INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='奖品表';

-- 7. 留言表
DROP TABLE IF EXISTS messages;
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
                          INDEX idx_create_time (create_time),
                          INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='留言表';

-- 8. 留言回复表
DROP TABLE IF EXISTS message_replies;
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
                                 INDEX idx_create_time (create_time),
                                 INDEX idx_is_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='留言回复表';

-- ========================================
-- 第三部分：示例数据插入
-- ========================================

-- 插入默认管理员账号
-- 用户名：admin，密码：admin123
INSERT INTO admins (id, username, password, real_name, is_enabled, create_time, update_time, is_deleted)
VALUES (1, 'admin', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '系统管理员', 1, NOW(), NOW(), 0);

-- 插入示例公告
INSERT INTO announcements (id, title, content, publish_date, is_top, create_time, update_time, is_deleted)
VALUES
    (1, '欢迎来到雷霆路亚俱乐部', '欢迎各位钓友加入雷霆路亚俱乐部！在这里您可以体验专业的路亚钓鱼，获得积分奖励，参与排行榜竞赛。我们提供：\n1. 专业的路亚钓鱼指导\n2. 完善的积分奖励体系\n3. 丰富的奖品兑换\n4. 友好的钓友交流环境\n\n期待您的加入！', CURDATE(), 1, NOW(), NOW(), 0),

    (2, '积分系统使用说明', '积分系统详细说明：\n\n【积分获得】\n- 当日钓鱼获得的积分会记录到您的账户\n- 当日积分次日自动转为有效积分\n\n【积分使用】\n- 有效积分可用于抵扣门票费用\n- 兑换比例：1积分 = 1元\n- 当日积分不能立即使用，需等到次日\n\n【注意事项】\n- 请及时查看积分余额\n- 如有疑问请联系管理员', CURDATE(), 1, NOW(), NOW(), 0),

    (3, '钓场开放时间调整通知', '尊敬的钓友们：\n\n根据季节变化和实际情况，现对钓场开放时间进行调整：\n\n【新开放时间】\n周一至周日：06:00 - 18:00\n\n【注意事项】\n1. 请在规定时间内进行钓鱼活动\n2. 超时将影响积分计算\n3. 恶劣天气可能临时关闭，请提前确认\n\n感谢您的理解与配合！', CURDATE(), 0, NOW(), NOW(), 0),

    (4, '安全钓鱼须知', '为确保大家的安全，请遵守以下规定：\n\n1. 必须穿着救生衣\n2. 严禁在危险区域钓鱼\n3. 注意用电安全\n4. 保护环境，不乱扔垃圾\n5. 相互帮助，文明钓鱼\n\n违反规定将取消积分奖励，严重者禁止入场。', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 0, NOW(), NOW(), 0);

-- 插入示例奖品
INSERT INTO prizes (id, name, description, image_url, sort_order, is_enabled, create_time, update_time, is_deleted)
VALUES
    (1, '专业路亚竿套装', '高品质碳素路亚竿套装，包含：\n- 2.1米中调路亚竿\n- 高速比纺车轮\n- 专业路亚线\n- 基础配件包\n\n适合各种路亚钓法，手感轻便，灵敏度高，是路亚爱好者的首选装备。', '/uploads/prizes/lure_rod_set.jpg', 1, 1, NOW(), NOW(), 0),

    (2, '精选路亚饵大礼包', '包含20+种不同类型的路亚饵：\n- 米诺饵 x 8个\n- VIB饵 x 6个\n- 软虫饵 x 10个\n- 亮片饵 x 5个\n- 配套钩子和配件\n\n涵盖各种水域和鱼种，让您的钓鱼之旅更加丰富多彩。', '/uploads/prizes/lure_baits_pack.jpg', 2, 1, NOW(), NOW(), 0),

    (3, '多功能钓具收纳箱', '专业级多功能钓具箱：\n- 多层分格设计\n- 防水密封性能\n- 大容量储物空间\n- 便携提手设计\n- 耐用ABS材质\n\n可存放路亚饵、工具、配件等，是路亚钓手的必备装备。', '/uploads/prizes/tackle_box_pro.jpg', 3, 1, NOW(), NOW(), 0),

    (4, '专业防晒钓鱼服', '高档防晒钓鱼服套装：\n- UPF50+防紫外线\n- 速干透气面料\n- 多口袋实用设计\n- 防蚊虫处理\n- 多尺码可选\n\n专为户外钓鱼设计，舒适透气，是夏日钓鱼的最佳选择。', '/uploads/prizes/fishing_apparel.jpg', 4, 1, NOW(), NOW(), 0),

    (5, '便携式钓鱼椅', '轻便折叠钓鱼椅：\n- 铝合金框架\n- 牛津布座面\n- 折叠便携设计\n- 承重150KG\n- 配收纳袋\n\n让您在钓鱼过程中享受舒适的休息体验。', '/uploads/prizes/fishing_chair.jpg', 5, 1, NOW(), NOW(), 0),

    (6, '夜钓照明套装', '专业夜钓装备：\n- LED头灯 x 1\n- 钓箱灯 x 1\n- 浮漂灯 x 5\n- 充电宝 x 1\n- 各种配件\n\n为夜钓爱好者提供充足照明，享受夜钓的乐趣。', '/uploads/prizes/night_fishing_kit.jpg', 6, 1, NOW(), NOW(), 0);

-- 插入测试用户（开发环境使用）
INSERT INTO users (id, username, password, phone, create_time, update_time, is_deleted)
VALUES
    (1, 'zhangsan', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '13800138001', NOW(), NOW(), 0),
    (2, 'lisi', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '13800138002', NOW(), NOW(), 0),
    (3, 'wangwu', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '13800138003', NOW(), NOW(), 0),
    (4, 'zhaoliu', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '13800138004', NOW(), NOW(), 0),
    (5, 'sunqi', '$2a$10$7BtZKr8gVrwCWxvbXPZFB.Dly0Z9h5nJ7VtYH6kKcH5MjXfQg5K5W', '13800138005', NOW(), NOW(), 0);

-- 为测试用户初始化积分记录
INSERT INTO points (id, user_id, today_points, effective_points, last_points_date, create_time, update_time)
VALUES
    (1, 1, 0, 28, CURDATE(), NOW(), NOW()),
    (2, 2, 5, 15, CURDATE(), NOW(), NOW()),
    (3, 3, 0, 42, CURDATE(), NOW(), NOW()),
    (4, 4, 8, 6, CURDATE(), NOW(), NOW()),
    (5, 5, 0, 33, CURDATE(), NOW(), NOW());

-- 插入积分历史数据（最近一周的数据）
INSERT INTO points_history (user_id, points_type, points, operation_date, remark, create_time)
VALUES
    -- 张三的积分历史
    (1, 'EARNED', 15, DATE_SUB(CURDATE(), INTERVAL 6 DAY), '周末钓鱼大丰收', NOW()),
    (1, 'EARNED', 8, DATE_SUB(CURDATE(), INTERVAL 5 DAY), '工作日钓鱼', NOW()),
    (1, 'DEDUCTED', -5, DATE_SUB(CURDATE(), INTERVAL 5 DAY), '门票抵扣', NOW()),
    (1, 'EARNED', 12, DATE_SUB(CURDATE(), INTERVAL 4 DAY), '钓获大鱼奖励', NOW()),
    (1, 'DEDUCTED', -2, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '饮料抵扣', NOW()),

    -- 李四的积分历史
    (2, 'EARNED', 6, DATE_SUB(CURDATE(), INTERVAL 5 DAY), '新手体验', NOW()),
    (2, 'EARNED', 10, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '技术进步奖励', NOW()),
    (2, 'DEDUCTED', -1, DATE_SUB(CURDATE(), INTERVAL 2 DAY), '小额抵扣', NOW()),
    (2, 'EARNED', 5, CURDATE(), '今日钓鱼', NOW()),

    -- 王五的积分历史
    (3, 'EARNED', 20, DATE_SUB(CURDATE(), INTERVAL 6 DAY), '钓鱼高手', NOW()),
    (3, 'EARNED', 18, DATE_SUB(CURDATE(), INTERVAL 4 DAY), '连续钓获', NOW()),
    (3, 'EARNED', 8, DATE_SUB(CURDATE(), INTERVAL 2 DAY), '日常钓鱼', NOW()),
    (3, 'DEDUCTED', -4, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '装备抵扣', NOW()),

    -- 赵六的积分历史
    (4, 'EARNED', 4, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '初学者练习', NOW()),
    (4, 'EARNED', 6, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '技能提升', NOW()),
    (4, 'DEDUCTED', -4, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '门票抵扣', NOW()),
    (4, 'EARNED', 8, CURDATE(), '今日收获', NOW()),

    -- 孙七的积分历史
    (5, 'EARNED', 25, DATE_SUB(CURDATE(), INTERVAL 5 DAY), '周末大丰收', NOW()),
    (5, 'EARNED', 12, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '中等收获', NOW()),
    (5, 'DEDUCTED', -4, DATE_SUB(CURDATE(), INTERVAL 2 DAY), '装备购买抵扣', NOW());

-- 插入测试留言
INSERT INTO messages (id, user_id, content, status, create_time, update_time, is_deleted)
VALUES
    (1, 1, '今天钓鱼收获真不错！感谢俱乐部提供这么好的环境和服务，管理很到位，设施也很完善。', 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 2 HOUR), NOW(), 0),
    (2, 2, '作为新手，在这里学到了很多路亚技巧，教练很专业，其他钓友也很友善，希望能继续进步！', 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 4 HOUR), NOW(), 0),
    (3, 3, '建议增加一些夜钓的照明设备，夜钓体验会更好。另外希望能有更多种类的路亚饵供选择。', 'REPLIED', DATE_SUB(NOW(), INTERVAL 6 HOUR), NOW(), 0),
    (4, 4, '积分系统很棒，很有激励性！建议能增加一些团队活动或比赛，让大家有更多交流机会。', 'PENDING', DATE_SUB(NOW(), INTERVAL 1 HOUR), NOW(), 0),
    (5, 1, '钓场环境优美，鱼的品种丰富，是路亚爱好者的天堂！推荐给更多朋友来体验。', 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 30 MINUTE), NOW(), 0);

-- 插入管理员回复
INSERT INTO message_replies (id, message_id, content, is_visible, create_time, update_time, is_deleted)
VALUES
    (1, 1, '谢谢您的支持和认可！我们会继续努力提供更好的服务，让每位钓友都能享受愉快的钓鱼体验。', 1, DATE_SUB(NOW(), INTERVAL 1 HOUR), NOW(), 0),
    (2, 2, '很高兴您在这里有所收获！我们会继续完善教学体系，为新手钓友提供更好的指导。欢迎常来交流！', 1, DATE_SUB(NOW(), INTERVAL 3 HOUR), NOW(), 0),
    (3, 3, '感谢您的宝贵建议！夜钓照明设备我们正在采购中，预计下月安装完成。路亚饵的种类我们也会持续丰富，敬请期待！', 1, DATE_SUB(NOW(), INTERVAL 5 HOUR), NOW(), 0);

-- ========================================
-- 第四部分：数据统计和验证
-- ========================================

-- 显示创建结果统计
SELECT 'Database and tables created successfully!' AS status;

SELECT
    '管理员账号' AS item,
    COUNT(*) AS count,
    '默认账号: admin/admin123' AS note
FROM admins;

SELECT
    '用户数量' AS item,
    COUNT(*) AS count,
    '测试密码: 123456' AS note
FROM users;

SELECT
    '公告数量' AS item,
    COUNT(*) AS count,
    '' AS note
FROM announcements;

SELECT
    '奖品数量' AS item,
    COUNT(*) AS count,
    '' AS note
FROM prizes;

SELECT
    '积分记录' AS item,
    COUNT(*) AS count,
    '' AS note
FROM points;

SELECT
    '积分历史' AS item,
    COUNT(*) AS count,
    '' AS note
FROM points_history;

SELECT
    '留言数量' AS item,
    COUNT(*) AS count,
    '' AS note
FROM messages;

SELECT
    '回复数量' AS item,
    COUNT(*) AS count,
    '' AS note
FROM message_replies;

-- 显示积分排行榜（验证数据）
SELECT
    u.username AS '用户名',
        p.effective_points AS '有效积分',
        p.today_points AS '当日积分',
        (p.effective_points + p.today_points) AS '总积分'
FROM users u
         JOIN points p ON u.id = p.user_id
ORDER BY (p.effective_points + p.today_points) DESC;

-- 完成提示
SELECT
    '🎉 数据库初始化完成！' AS message,
    '可以启动SpringBoot应用了' AS next_step;