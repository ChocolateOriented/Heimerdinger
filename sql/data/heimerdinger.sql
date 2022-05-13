DROP TABLE IF EXISTS stock_trace;
CREATE TABLE stock_trace(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    del_flag char(1)   DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' ,
    create_by VARCHAR(64)    COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(64)    COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    name VARCHAR(255)    COMMENT '名称' ,
    code VARCHAR(255)    COMMENT '代码' ,
    price DECIMAL(24,3)    COMMENT '追踪价格' ,
    pb DECIMAL(24,3)    COMMENT '追踪pb' ,
    pb_min DECIMAL(24,3)    COMMENT '预计最低市净率' ,
    pb_max DECIMAL(24,3)    COMMENT '预计最高市净率' ,
    pb_fit DECIMAL(24,3)    COMMENT '预计合理市净率' ,
    start_time DATETIME    COMMENT '开始追踪时间' ,
    keep_data DATETIME    COMMENT '持有时间' ,
    amount DECIMAL(24,3)    COMMENT '开始持有金额' ,
    amount_min DECIMAL(24,3)    COMMENT '最小持有金额' ,
    amount_max DECIMAL(24,3)    COMMENT '最大持有金额' ,
    amount_fit DECIMAL(24,3)    COMMENT '合理持有金额' ,
    PRIMARY KEY (id)
)  COMMENT = '股票追踪';

DROP TABLE IF EXISTS stock_data_config;
CREATE TABLE stock_data_config(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    del_flag char(1)   DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' ,
    create_by VARCHAR(64)    COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(64)    COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    data_index INT    COMMENT '索引' ,
    name VARCHAR(255)    COMMENT '名称' ,
    PRIMARY KEY (id)
)  COMMENT = '股票数据映射配置';

DROP TABLE IF EXISTS stock_position_plan;
CREATE TABLE stock_position_plan(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    del_flag char(1)   DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' ,
    create_by VARCHAR(64)    COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(64)    COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    trace_id BIGINT(20)    COMMENT '追踪id' ,
    advice_price DECIMAL(24,3)    COMMENT '触发价格' ,
    gridding_amount DECIMAL(24,3)    COMMENT '网格持仓' ,
    gridding_percent DECIMAL(24,3)    COMMENT '网格持仓百分比' ,
    advice_date DATETIME    COMMENT '触发日期' ,
    advice_amount DECIMAL(24,3)    COMMENT '定投持仓金额' ,
    advice_percent DECIMAL(24,3)    COMMENT '定投持仓百分比' ,
    trade_type VARCHAR(255)    COMMENT '交易类型' ,
    PRIMARY KEY (id)
)  COMMENT = '股票持仓计划';

DROP TABLE IF EXISTS finance_position_plan;
CREATE TABLE finance_position_plan(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    del_flag char(1)   DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' ,
    create_by VARCHAR(64)    COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(64)    COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    trace_id BIGINT(20)    COMMENT '追踪id' ,
    name VARCHAR(255)    COMMENT '资产名称' ,
    reality_amount DECIMAL(24,3)    COMMENT '实际持仓' ,
    target_amount DECIMAL(24,3)    COMMENT '计划持仓' ,
    PRIMARY KEY (id)
)  COMMENT = '持仓计划';

DROP TABLE IF EXISTS finance_event_trace;
CREATE TABLE finance_event_trace(
    id BIGINT(20) NOT NULL AUTO_INCREMENT  COMMENT 'id' ,
    del_flag char(1)   DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' ,
    create_by VARCHAR(64)    COMMENT '创建人' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(64)    COMMENT '更新人' ,
    update_time DATETIME    COMMENT '更新时间' ,
    occurred_time DATETIME    COMMENT '发生时间' ,
    ammount DECIMAL(24,3)    COMMENT '数值' ,
    change_rate DECIMAL(24,3)   DEFAULT 0 COMMENT '较上期变化率' ,
    event_type VARCHAR(255)    COMMENT '事件类型' ,
    name VARCHAR(255)    COMMENT '事件名称' ,
    PRIMARY KEY (id)
)  COMMENT = '经济事件追踪';

/* 插入字典总表[trade_type-交易类型] */
INSERT INTO sys_dict_type(dict_type,dict_name,remark) VALUES('trade_type','交易类型','');
/* 插入字典明细表 */
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '买入', 'BUY', 'trade_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '卖出', 'SALE', 'trade_type', 'Y', '0');

/* 插入字典总表[finance_event_type-经济事件类型] */
INSERT INTO sys_dict_type(dict_type,dict_name,remark) VALUES('finance_event_type','经济事件类型','');
/* 插入字典明细表 */
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '黄金', 'GOLD', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '大宗商品', 'COMMODITY', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '股票', 'STOCK', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '债券', 'BOND', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '货币政策', 'CURRENCY', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '信用政策', 'CREDIT', 'finance_event_type', 'Y', '0');
INSERT INTO `sys_dict_data`( `dict_label`, `dict_value`, `dict_type`, `is_default`, `status`) VALUES ( '国际事件', 'INTERNATIONAL_EVENT', 'finance_event_type', 'Y', '0');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪', '2011', '1', 'financeEventTrace', 'finance/financeEventTrace/index', 1, 0, 'C', '0', '0', 'finance:financeEventTrace:list', '#', 'admin', sysdate(), '', null, '经济事件追踪菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'finance:financeEventTrace:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'finance:financeEventTrace:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'finance:financeEventTrace:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'finance:financeEventTrace:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('经济事件追踪导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'finance:financeEventTrace:export',       '#', 'admin', sysdate(), '', null, '');
