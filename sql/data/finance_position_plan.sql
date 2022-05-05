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

 Date: 05/05/2022 17:42:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for finance_position_plan
-- ----------------------------
DROP TABLE IF EXISTS `finance_position_plan`;
CREATE TABLE `finance_position_plan`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `trace_id` bigint NULL DEFAULT NULL COMMENT '追踪id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资产名称',
  `reality_amount` decimal(24, 3) NULL DEFAULT NULL COMMENT '实际持仓',
  `target_amount` decimal(24, 3) NULL DEFAULT NULL COMMENT '计划持仓',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '持仓计划' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of finance_position_plan
-- ----------------------------
INSERT INTO `finance_position_plan` VALUES (1, '0', NULL, '2022-04-28 12:03:50', NULL, '2022-04-28 13:29:38', 1, '分众传媒', 24803.920, 40000.000);
INSERT INTO `finance_position_plan` VALUES (2, '0', NULL, '2022-04-28 12:46:10', NULL, '2022-04-28 13:29:52', 2, '牧原股份', 31333.342, 50000.000);
INSERT INTO `finance_position_plan` VALUES (3, '0', NULL, '2022-04-28 12:47:10', NULL, '2022-04-28 13:30:03', 3, '基建50', 12352.942, 50000.000);
INSERT INTO `finance_position_plan` VALUES (4, '0', NULL, '2022-04-28 12:48:28', NULL, '2022-04-29 16:34:41', 4, '中概互联', 36538.444, 50000.000);
INSERT INTO `finance_position_plan` VALUES (5, '0', NULL, '2022-04-28 13:05:44', NULL, '2022-04-29 16:34:42', NULL, '现金', 46547.000, 10000.000);
INSERT INTO `finance_position_plan` VALUES (6, '0', NULL, '2022-04-28 13:07:42', NULL, '2022-04-28 15:49:39', NULL, '债券+封闭基金', 129210.000, 0.000);
INSERT INTO `finance_position_plan` VALUES (7, '0', NULL, '2022-04-28 13:13:34', NULL, '2022-04-30 19:43:13', 6, '地产+金融+农业', 46192.000, 50000.000);
INSERT INTO `finance_position_plan` VALUES (8, '0', NULL, '2022-04-28 13:21:14', NULL, '2022-04-29 16:46:23', 5, '医疗基金', 19922.000, 30000.000);
INSERT INTO `finance_position_plan` VALUES (9, '0', NULL, '2022-04-28 13:22:00', NULL, '2022-04-30 20:02:15', 9, '养老', 2959.000, 30000.000);
INSERT INTO `finance_position_plan` VALUES (10, '0', NULL, '2022-04-28 13:22:47', NULL, '2022-04-30 20:30:43', 10, '蓝筹', 8054.000, 50000.000);
INSERT INTO `finance_position_plan` VALUES (11, '0', NULL, '2022-04-28 15:29:31', NULL, '2022-04-30 19:54:32', 8, '半导体', 7959.000, 30000.000);

SET FOREIGN_KEY_CHECKS = 1;
