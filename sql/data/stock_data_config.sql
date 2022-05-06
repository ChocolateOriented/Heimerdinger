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

 Date: 06/05/2022 16:07:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock_data_config
-- ----------------------------
DROP TABLE IF EXISTS `stock_data_config`;
CREATE TABLE `stock_data_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `data_index` int(11) NULL DEFAULT NULL COMMENT '索引',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '股票数据映射配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_data_config
-- ----------------------------
INSERT INTO `stock_data_config` VALUES (1, '0', NULL, NULL, NULL, NULL, 3, 'price');

SET FOREIGN_KEY_CHECKS = 1;
