/*
 Navicat Premium Data Transfer

 Source Server         : iceiceice
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : wasaidb

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 21/05/2022 12:54:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `number` int(11) NULL DEFAULT NULL COMMENT '参与人数',
  `start_time` datetime NOT NULL COMMENT '开始时间（格式2022-01-02）',
  `end_time` datetime NOT NULL COMMENT '结束时间（格式2022-01-02）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间 (格式2022-01)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.8 活动表（t_activity）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_activity
-- ----------------------------
INSERT INTO `t_activity` VALUES (6, '活动简述:为了满足广大儿童与家长的娱乐、交流、学习需求,让我们的孩子们有-块 专属于自己的屏幕与天空，晚报拟将晚报内部电影厅打造成专属的儿童亲子影院，定期播放专属儿童的各类电 影与演出活动,如英语专场、爸爸去看电影专场、假期电影周、动漫专场等等,让亲子家庭在娱乐中提升、在快 乐中沉思。同时影院将并与商业机构合作,开展各类知识讲座与交流,如儿童保险、家庭理财、儿童教育、母婴 用品交流等等，在丰富儿童的业余生活，服务亲子家庭的基本需求的同时，促进家庭和谐，让父母与子女相互 尊重、共同教育、-起成长。', 3, '2021-04-01 00:00:00', '2021-04-03 00:00:00', '2021-03-31 17:04:58', '2021-03-31 17:04:58');

-- ----------------------------
-- Table structure for t_arrangement
-- ----------------------------
DROP TABLE IF EXISTS `t_arrangement`;
CREATE TABLE `t_arrangement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fid` int(11) NOT NULL COMMENT '电影主键（关联t_film表）',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电影名',
  `seat_number` int(11) NOT NULL COMMENT '开放多少座位',
  `box_office` int(11) NOT NULL COMMENT '票房统计',
  `price` decimal(10, 1) NOT NULL COMMENT '价格',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '放映类型 2D 3D',
  `date` date NOT NULL COMMENT '电影放映日期（格式2022-01-\n14）',
  `start_time` time NOT NULL COMMENT '电影开始时间（格式11:37:03',
  `end_time` time NOT NULL COMMENT '电影结束时间（格式11:37:03）',
  `founder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '排片人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.11 排片表（t_arrangement）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_arrangement
-- ----------------------------
INSERT INTO `t_arrangement` VALUES (7, 1, '刺杀小说家', 40, 0, 35.5, '3D放映', '2021-04-05', '12:00:00', '14:10:00', 'admin', '2022-05-18 00:02:18', '2022-05-18 00:02:20');
INSERT INTO `t_arrangement` VALUES (8, 1, '刺杀小说家', 30, 0, 30.0, '2D放映', '2021-04-04', '11:00:00', '11:10:00', 'admin', '2022-05-18 00:02:18', '2022-05-18 00:02:20');
INSERT INTO `t_arrangement` VALUES (9, 1, '刺杀小说家', 20, 0, 30.0, '2D放映', '2021-04-06', '11:00:00', '11:10:00', 'admin', '2022-05-18 00:02:18', '2022-05-18 00:02:20');

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(255) NOT NULL COMMENT '用户主键（关联t_user表）',
  `aid` int(255) NOT NULL COMMENT '排片id（关联\nt_arrangement）',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理结果',
  `seats` int(255) NOT NULL COMMENT '座位',
  `price` decimal(10, 0) NOT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.15 购物车表（t_cart）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cart
-- ----------------------------
INSERT INTO `t_cart` VALUES (50, 1, 8, 0, '21312323', 19, 30, '2022-05-20 07:22:53', '2022-05-20 07:22:53');

-- ----------------------------
-- Table structure for t_film
-- ----------------------------
DROP TABLE IF EXISTS `t_film`;
CREATE TABLE `t_film`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电影名',
  `release_time` datetime NOT NULL COMMENT '上映时间（格式2022-01-\n14）',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `status` tinyint(4) NOT NULL COMMENT '状态 1-上架；0-下架',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区',
  `hot` int(11) NOT NULL COMMENT '热度',
  `introduction` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '介绍',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片id（关联t_film.id）',
  `duration` int(11) NOT NULL COMMENT '电影时长 单位：分钟',
  `is_delete` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '删除标志 1-已被删除 0-未\n删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.10 电影表（t_film）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_film
-- ----------------------------
INSERT INTO `t_film` VALUES (1, '刺杀小说家', '2021-02-12 00:00:00', '科幻', 1, '内地', 7, '\"异世界皇都，天神赤发鬼残暴统治，滥杀无辜。少年空文因被赤发鬼追杀，决 定奋起反击。在黑甲的指引下，踏上了凡人弑神之路。这是小说家路空文笔下的奇幻世界。没想到小说的进 程，竟然影响着现实世界。这时一名男子接下了刺杀他的任务…\\n', 'https://s1.ax1x.com/2022/03/31/qRNxkd.jpg', 130, '0', '2022-05-16 09:32:48', '2022-05-16 09:32:51');
INSERT INTO `t_film` VALUES (2, '哥斯拉大战金刚 Godzilla vs Kong (2021)', '2021-03-26 00:00:00', '科幻', 1, '美国', 2, '这两位宛如神衹一般强大的对手于一场壮观的战争中相遇，彼时世界命运正悬 于一线。为了找到真正的家园，金刚与他的保护者们踏上了一次艰难的旅程。与他们一道前行的还有一个年轻 的孤儿女孩——吉雅，这个女孩与金刚之间存在着一种独特而强大的紧密联系。但意想不到的是，他们在前行的 航道上与愤怒的哥斯拉狭路相逢，也由此在全球引起了一系列破坏。一股无形的力量造成了这两只巨兽之间的 巨大冲突，深藏在地心深处的奥秘也由此揭开。', 'https://s1.ax1x.com/2022/03/31/qRaB2q.jpg', 116, '0', '2022-05-16 09:38:54', '2022-05-16 09:38:57');
INSERT INTO `t_film` VALUES (3, '神奇动物:邓布利多之谜', '2022-04-08 16:41:44', '冒险', 1, '美国', 12, '该片讲述了邓布利多意识到强大的黑巫师格林德沃正试图夺取魔法世界的控制权，于是委托魔法动物学家纽特带领一支精良的团队执行对抗格林德沃的危险任务的故事', 'https://s1.ax1x.com/2022/05/20/OOuAnH.jpg', 142, '0', '2022-05-20 16:43:03', '2022-05-20 16:43:05');
INSERT INTO `t_film` VALUES (4, '封神榜:哪吒重生', '2021-02-12 16:48:14', '动作', 1, '内地', 9, '三千年前，天下动荡，人神共遇大劫，不想哪吒的一缕魂魄逃脱天罗地网，世世转世投胎，这一世的他与东海市酷爱机车的热血青年李云祥（杨天翔 配音）人神共生。然而龙族对哪吒恩怨并未善罢甘休，有着哪吒元神的李云祥，亦无法逃脱被龙族赶尽杀绝的宿命。此后，东海危在旦夕，而李云祥则最终与哪吒元神并肩作战，拯救了东海市的子民，并成为了抵抗龙族的英雄', 'https://s1.ax1x.com/2022/03/31/qRtebQ.jpg', 116, '0', '2022-05-20 16:47:48', '2022-05-20 16:47:52');

-- ----------------------------
-- Table structure for t_film_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_film_evaluate`;
CREATE TABLE `t_film_evaluate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fid` int(11) NOT NULL COMMENT '电影主键（关联t_film表）',
  `uid` int(11) NOT NULL COMMENT '用户主键（关联t_user表）',
  `star` int(11) NOT NULL COMMENT '评分',
  `comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.12 电影评论表（t_film_evaluate）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_film_evaluate
-- ----------------------------
INSERT INTO `t_film_evaluate` VALUES (17, 1, 2, 3, '还不错', '2022-05-16 13:34:03', '2022-05-16 13:34:06');
INSERT INTO `t_film_evaluate` VALUES (18, 1, 1, 4, '好看啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2022-05-16 20:42:26', '2022-05-16 20:42:29');
INSERT INTO `t_film_evaluate` VALUES (21, 2, 1, 5, '还行', '2022-05-17 15:57:09', '2022-05-17 15:57:09');
INSERT INTO `t_film_evaluate` VALUES (22, 2, 1, 4, '一般男男女女', '2022-05-17 15:57:32', '2022-05-17 15:57:32');
INSERT INTO `t_film_evaluate` VALUES (23, 2, 1, 4, '呵呵了', '2022-05-18 06:43:30', '2022-05-18 06:43:30');

-- ----------------------------
-- Table structure for t_leaving_message
-- ----------------------------
DROP TABLE IF EXISTS `t_leaving_message`;
CREATE TABLE `t_leaving_message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) NOT NULL COMMENT '用户主键（关联t_user表）',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `reply` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '回复内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.6 影院留言表（t_leaving_message）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_leaving_message
-- ----------------------------
INSERT INTO `t_leaving_message` VALUES (10, 2, '还要多加努力', '收到！', '2022-05-17 09:11:41', '2022-05-17 09:11:43');
INSERT INTO `t_leaving_message` VALUES (11, 1, '马马虎虎', '感谢你的反馈', '2022-05-17 13:24:26', '2022-05-17 13:24:29');
INSERT INTO `t_leaving_message` VALUES (18, 1, '呵呵了，能不能把BUG多修一点啊', '等待回复', '2022-05-17 15:59:05', '2022-05-17 15:59:05');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) NOT NULL COMMENT '用户主键（关联t_user表）',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话号',
  `aid` int(11) NOT NULL COMMENT '排片id（关联t_arrangement）',
  `seats` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '座位',
  `price` decimal(10, 0) NOT NULL COMMENT '金额',
  `status` int(11) NOT NULL COMMENT '状态 0-等待支付；1-支付失败；2-支付成\n功；3-已被撤销',
  `pay_at` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14 11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.13 订单表（t_order）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (26, 1, '567890', 9, '4', 30, 3, NULL, '2022-05-19 15:16:54', '2022-05-19 15:16:54');
INSERT INTO `t_order` VALUES (27, 1, '567890', 9, '16', 30, 2, NULL, '2022-05-19 15:16:54', '2022-05-19 15:16:54');
INSERT INTO `t_order` VALUES (28, 1, '567890', 9, '9', 30, 3, NULL, '2022-05-19 15:16:54', '2022-05-19 15:16:54');
INSERT INTO `t_order` VALUES (29, 1, '12322222', 7, '15', 36, 2, NULL, '2022-05-19 16:00:33', '2022-05-19 16:00:33');
INSERT INTO `t_order` VALUES (30, 1, '12322222', 7, '30', 36, 2, NULL, '2022-05-19 16:00:33', '2022-05-19 16:00:33');
INSERT INTO `t_order` VALUES (31, 1, '21312323', 8, '3', 30, 2, NULL, '2022-05-20 07:22:53', '2022-05-20 07:22:53');
INSERT INTO `t_order` VALUES (32, 1, '21312323', 8, '7', 30, 1, NULL, '2022-05-20 07:22:53', '2022-05-20 07:22:53');
INSERT INTO `t_order` VALUES (33, 1, '21312323', 8, '19', 30, 0, NULL, '2022-05-20 07:22:53', '2022-05-20 07:22:53');
INSERT INTO `t_order` VALUES (34, 2, '13075918709', 9, '4', 30, 3, NULL, '2022-05-21 02:13:17', '2022-05-21 02:13:17');
INSERT INTO `t_order` VALUES (35, 2, '13075918709', 9, '5', 30, 3, NULL, '2022-05-21 02:13:17', '2022-05-21 02:13:17');

-- ----------------------------
-- Table structure for t_poster
-- ----------------------------
DROP TABLE IF EXISTS `t_poster`;
CREATE TABLE `t_poster`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fid` int(11) NOT NULL COMMENT '电影id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片id（关联t_film.id）',
  `status` int(4) NOT NULL COMMENT '状态1-上架；0-下架',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.5 轮播图广告表（t_poster）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_poster
-- ----------------------------
INSERT INTO `t_poster` VALUES (1, 1, '狮子王', '/images123/szw.png', 0, '2022-05-13 10:23:07', '2022-05-13 10:23:10');
INSERT INTO `t_poster` VALUES (2, 2, '小小的愿望', '/images123/xxdyw.png', 0, '2022-05-13 10:31:53', '2022-05-13 10:31:56');
INSERT INTO `t_poster` VALUES (3, 3, '神奇动物-邓布利多之谜', '/images123/sqdwp.jpg', 1, '2022-05-20 16:40:09', '2022-05-20 16:40:11');
INSERT INTO `t_poster` VALUES (4, 4, '受益人', '/images123/syr.png', 0, '2022-05-13 10:33:12', '2022-05-13 10:33:14');
INSERT INTO `t_poster` VALUES (5, 5, '朝花夕誓', '/images123/zhxs.jpg', 1, '2022-05-20 16:36:50', '2022-05-20 16:36:51');
INSERT INTO `t_poster` VALUES (6, 6, '一代宗师', '/images123/ydzs.jpg', 1, '2022-05-20 16:37:18', '2022-05-20 16:37:21');
INSERT INTO `t_poster` VALUES (7, 7, '海兽之子', '/images123/hszz.jpg', 1, '2022-05-13 10:32:45', '2022-05-13 10:32:48');

-- ----------------------------
-- Table structure for t_registration
-- ----------------------------
DROP TABLE IF EXISTS `t_registration`;
CREATE TABLE `t_registration`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `aid` int(11) NOT NULL COMMENT '活动主键（关联t_activity表）',
  `uid` int(11) NOT NULL COMMENT '用户主键（关联t_user表）',
  `create_time` datetime NOT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.9 活动参与表（t_registration）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_registration
-- ----------------------------
INSERT INTO `t_registration` VALUES (2, 6, 1, '2022-05-19 04:31:53', '2022-05-19 04:31:53');

-- ----------------------------
-- Table structure for t_seat
-- ----------------------------
DROP TABLE IF EXISTS `t_seat`;
CREATE TABLE `t_seat`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `aid` int(11) NOT NULL COMMENT '排片id',
  `seat` int(11) NOT NULL COMMENT '已选座位号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_seat
-- ----------------------------
INSERT INTO `t_seat` VALUES (44, 9, 16, '2022-05-19 15:16:54', '2022-05-19 15:16:54');
INSERT INTO `t_seat` VALUES (46, 7, 15, '2022-05-19 16:00:33', '2022-05-19 16:00:33');
INSERT INTO `t_seat` VALUES (47, 7, 30, '2022-05-19 16:00:33', '2022-05-19 16:00:33');
INSERT INTO `t_seat` VALUES (48, 8, 3, '2022-05-20 07:22:53', '2022-05-20 07:22:53');
INSERT INTO `t_seat` VALUES (49, 8, 7, '2022-05-20 07:22:53', '2022-05-20 07:22:53');
INSERT INTO `t_seat` VALUES (50, 8, 19, '2022-05-20 07:22:53', '2022-05-20 07:22:53');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '\'主键\'',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'用户名\'',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'密码\'',
  `salt` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '\'盐\'',
  `salt_index` int(11) NOT NULL COMMENT '\'盐索引\'',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'昵称\'',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'邮箱\'',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日（格式2022-01-02）\n',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'性别（男生；女生）\'',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\'个人简介\'',
  `create_time` datetime NULL DEFAULT NULL COMMENT '\'创建时间（格式2022-01-14\'',
  `update_time` datetime NULL DEFAULT NULL COMMENT '\'更新时间（格式2022-01-14\'',
  `error_num` int(11) NULL DEFAULT 0,
  `status` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.1 用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'lapkin007', '8789614D47E9919D8E34AD34FAB6BC00', 'IHyusPWRWf', 5, 'https://s1.ax1x.com/2022/03/31/qRY9pV.jpg', 'Ice龟', '1659771776@qq.com', NULL, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `t_user` VALUES (2, 'lisi123', '5F781C20347E78C98A1EA1F15DB471F1', 'RnGNHsjUUq', 8, 'https://s1.ax1x.com/2022/04/01/qhEMtg.png', 'LISI', '1659771776@qq.com', NULL, NULL, NULL, NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for t_worker
-- ----------------------------
DROP TABLE IF EXISTS `t_worker`;
CREATE TABLE `t_worker`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像（图片id，关联t_film.id）',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `entry` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态1-在职 0-离职',
  `gender` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别（男；女）',
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间（格式2022-01-14\\n11:37:03）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.3 客服表（t_worker）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_worker
-- ----------------------------
INSERT INTO `t_worker` VALUES (6, 'Lemon', '$2a$10$I6xrzPoOJ9/JSX3UPfaZguSbm3.zxnWuOTom/YQLT6Yreo9mwnZgK', 'https://s1.ax1x.com/2022/04/01/qhEQhQ.jpg', 'Lemon', '12391821823', 'true', '男', '客服部', '2022-05-17 09:07:11', '2022-05-17 09:07:14');
INSERT INTO `t_worker` VALUES (7, 'Shixinyu', '$2a$10$NYp9CGE1Co/J3SemIFALn.9df7KJOMNJmdFx4DW.2toNTJ1IEc2ni', 'https://s1.ax1x.com/2022/04/01/qhE31s.jpg', '新语', '13847639183', 'true', '女', '客服部', '2022-05-17 09:09:07', '2022-05-17 09:09:10');

-- ----------------------------
-- Table structure for t_worker_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `t_worker_evaluate`;
CREATE TABLE `t_worker_evaluate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wid` int(11) NOT NULL COMMENT '客服主键（关联t_worker表）',
  `uid` int(11) NOT NULL COMMENT '用户主键（关联t_user表）',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型(满意；不满意；非常满意)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间（格式2022-01-14\\n11:37:03）',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '4.2.7 客服评价表（t_worker_evaluate）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_worker_evaluate
-- ----------------------------
INSERT INTO `t_worker_evaluate` VALUES (12, 6, 2, '不错！', '非常满意', '2022-05-17 09:10:19', '2022-05-17 09:10:22');
INSERT INTO `t_worker_evaluate` VALUES (13, 6, 1, '还行', '一般', '2022-05-17 16:19:33', '2022-05-17 16:19:35');
INSERT INTO `t_worker_evaluate` VALUES (14, 7, 2, '差', '不满意', '2022-05-17 16:20:07', '2022-05-17 16:20:09');
INSERT INTO `t_worker_evaluate` VALUES (15, 7, 1, '希望下次态度好点', '不满意', '2022-05-17 16:20:07', '2022-05-17 16:20:09');
INSERT INTO `t_worker_evaluate` VALUES (17, 6, 1, '啊啊啊啊', '一般', '2022-05-17 13:56:21', '2022-05-17 13:56:21');
INSERT INTO `t_worker_evaluate` VALUES (18, 7, 1, '啊啊啊啊', '一般', '2022-05-17 13:56:30', '2022-05-17 13:56:30');

SET FOREIGN_KEY_CHECKS = 1;
