/*
 Navicat Premium Data Transfer

 Source Server         : 演示mysql
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.1.252:3306
 Source Schema         : heimerdinger

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 06/05/2022 16:07:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock_trace
-- ----------------------------
DROP TABLE IF EXISTS `stock_trace`;
CREATE TABLE `stock_trace`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码',
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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '股票追踪' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_trace
-- ----------------------------
INSERT INTO `stock_trace` VALUES (1, '0', NULL, NULL, NULL, NULL, '分众传媒', 'sz002027', 5.890, 4.410, 3.500, 7.000, 6.300, '2022-04-16 00:00:00', '2023-04-12 00:00:00', 15000.000, 20000.000, 50000.000, 40000.000);
INSERT INTO `stock_trace` VALUES (2, '0', NULL, NULL, NULL, NULL, '牧原股份', 'sz002714', 52.240, 5.910, 4.200, 11.060, 8.440, '2022-04-12 00:00:00', '2022-07-31 00:00:00', 22000.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (3, '0', NULL, NULL, NULL, NULL, '基建50', 'sh516970', 1.191, 0.930, 0.750, 1.370, 1.210, '2022-04-05 00:00:00', '2022-11-30 00:00:00', 10000.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (4, '0', NULL, NULL, NULL, NULL, '中概互联', 'sh513050', 1.041, 2.590, 1.950, 7.080, 6.100, '2022-01-01 00:00:00', '2022-12-31 00:00:00', 0.000, 40000.000, 50000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (5, '0', NULL, NULL, NULL, NULL, '医疗', 'sz159847', 0.571, 6.490, 4.210, 9.600, 7.560, '2022-01-20 00:00:00', '2023-04-30 00:00:00', 0.000, 20000.000, 80000.000, 60000.000);
INSERT INTO `stock_trace` VALUES (6, '0', NULL, NULL, NULL, NULL, '金融地产', 'sz159940', 0.927, 0.740, 0.740, 1.250, 1.160, '2022-04-30 00:00:00', '2022-12-31 00:00:00', 46192.000, 40000.000, 60000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (8, '0', NULL, NULL, NULL, NULL, '半导体', 'sh512480', 0.874, 4.950, 2.500, 5.000, 4.620, '2022-04-30 00:00:00', '2023-12-31 00:00:00', 7959.000, 20000.000, 40000.000, 30000.000);
INSERT INTO `stock_trace` VALUES (9, '0', NULL, NULL, NULL, NULL, '养老', 'sh516560', 0.802, 2.180, 2.000, 3.300, 3.160, '2022-04-30 00:00:00', '2022-12-31 00:00:00', 2959.000, 20000.000, 40000.000, 30000.000);
INSERT INTO `stock_trace` VALUES (10, '0', NULL, NULL, NULL, NULL, '蓝筹', 'sz399370', 4782.340, 3.780, 2.000, 4.500, 3.800, '2022-04-01 00:00:00', '2023-12-31 00:00:00', 8054.000, 10000.000, 40000.000, 50000.000);
INSERT INTO `stock_trace` VALUES (11, '0', NULL, NULL, NULL, NULL, '光伏', 'sh516880', 1.005, 4.210, 3.000, 4.500, 3.490, '2022-05-05 00:00:00', '2023-12-31 00:00:00', 100.000, 100.000, 500.000, 300.000);

SET FOREIGN_KEY_CHECKS = 1;
