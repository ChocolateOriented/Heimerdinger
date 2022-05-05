/*
 Navicat Premium Data Transfer

 Source Server         : home
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : heimerdinger

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 05/05/2022 17:42:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock_trace
-- ----------------------------
DROP TABLE IF EXISTS `stock_trace`;
CREATE TABLE `stock_trace`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '代码',
  `price` decimal(24, 3) NULL DEFAULT NULL COMMENT '追踪价格',
  `pb` decimal(24, 3) NULL DEFAULT NULL COMMENT '追踪pb',
  `pb_min` decimal(24, 3) NULL DEFAULT NULL COMMENT '预计最低市净率',
  `pb_max` decimal(24, 3) NULL DEFAULT NULL COMMENT '预计最高市净率',
  `pb_fit` decimal(24, 3) NULL DEFAULT NULL COMMENT '预计合理市净率',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始追踪时间',
  `keep_data` datetime(0) NULL DEFAULT NULL COMMENT '持有时间',
  `amount` decimal(24, 3) NULL DEFAULT NULL COMMENT '开始持有金额',
  `amount_min` decimal(24, 3) NULL DEFAULT NULL COMMENT '最小持有金额',
  `amount_max` decimal(24, 3) NULL DEFAULT NULL COMMENT '最大持有金额',
  `amount_fit` decimal(24, 3) NULL DEFAULT NULL COMMENT '合理持有金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '股票追踪' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stock_trace
-- ----------------------------
INSERT INTO `stock_trace` VALUES (1, '0', NULL, NULL, NULL, NULL, '分众传媒', 'sz002027', 5.590, 4.810, 3.500, 7.000, 6.300, '2022-04-16 00:00:00', '2023-04-12 00:00:00', 15000.000, 20000.000, 50000.000, 40000.000);
INSERT INTO `stock_trace` VALUES (2, '0', NULL, NULL, NULL, NULL, '牧原股份', 'sz002714', 55.000, 5.550, 4.200, 8.800, 8.000, '2022-04-12 00:00:00', '2022-07-31 00:00:00', 22000.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (3, '0', NULL, NULL, NULL, NULL, '基建50', 'sh516970', 1.100, 0.900, 0.750, 1.800, 1.200, '2022-04-05 00:00:00', '2022-11-30 00:00:00', 10000.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (4, '0', NULL, NULL, NULL, NULL, '中概互联', 'sh513050', 1.204, 3.030, 2.000, 8.000, 6.000, '2022-01-01 00:00:00', '2022-12-31 00:00:00', 0.000, 40000.000, 50000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (5, '0', NULL, NULL, NULL, NULL, '医疗', 'sz159847', 0.574, 6.460, 5.300, 10.000, 8.000, '2022-01-20 00:00:00', '2023-04-30 00:00:00', 0.000, 20000.000, 80000.000, 60000.000);
INSERT INTO `stock_trace` VALUES (6, '0', NULL, NULL, NULL, NULL, '金融地产', 'sz159940', 0.935, 0.750, 0.740, 1.200, 1.050, '2022-04-30 00:00:00', '2022-12-31 00:00:00', 46192.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (8, '0', NULL, NULL, NULL, NULL, '半导体', 'sh512480', 0.868, 4.910, 2.500, 8.000, 5.000, '2022-04-30 00:00:00', '2023-12-31 00:00:00', 7959.000, 20000.000, 40000.000, 30000.000);
INSERT INTO `stock_trace` VALUES (9, '0', NULL, NULL, NULL, NULL, '养老', 'sh516560', 0.786, 2.410, 2.310, 3.780, 3.150, '2022-04-30 00:00:00', '2022-12-31 00:00:00', 2959.000, 20000.000, 40000.000, 30000.000);
INSERT INTO `stock_trace` VALUES (10, '0', NULL, NULL, NULL, NULL, '蓝筹', 'sz399370', 4792.630, 3.800, 2.000, 4.800, 2.990, '2022-04-01 00:00:00', '2023-12-31 00:00:00', 8054.000, 10000.000, 40000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (11, '0', NULL, NULL, NULL, NULL, '光伏', 'sh516880', 1.005, 4.160, 3.000, 4.340, 3.490, '2022-05-05 00:00:00', '2023-12-31 00:00:00', 100.000, 100.000, 500.000, 300.000);

SET FOREIGN_KEY_CHECKS = 1;
