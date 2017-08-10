CREATE DATABASE IF NOT EXISTS ivplay DEFAULT CHARACTER SET utf8mb4;

USE ivplay;

/*Table structure for table `act_ge_bytearray` */

DROP TABLE IF EXISTS `t_user`;


CREATE TABLE `t_user` (
  `id` VARCHAR(64) NOT NULL,
  `nick_name` VARCHAR(50) NOT NULL COMMENT '微信昵称',
  `open_id` VARCHAR(50) NOT NULL DEFAULT '-1' COMMENT 'openId',
  `pass_word` VARCHAR(255) NOT NULL DEFAULT '-1' COMMENT '登录密码',
  `user_name` VARCHAR(255) NOT NULL DEFAULT '-1' COMMENT '登录名',
  `phone` CHAR(11) NOT NULL DEFAULT '-1' COMMENT '手机号码',
  `sex` CHAR(2) NOT NULL DEFAULT '1' COMMENT '1:男 2:女',
  `last_login_ip_address` VARCHAR(20) NOT NULL DEFAULT '-1' COMMENT '最后登录IP地址',
  `attenation` CHAR(2) NOT NULL DEFAULT '1' COMMENT '关注状态（1:未关注 2：已关注）',
  `un_attenation` CHAR(2) NOT NULL DEFAULT '1' COMMENT '是否取消关注状态（1:未取消 2：已取消）',
  `country` VARCHAR(20) NOT NULL DEFAULT 'CN' COMMENT '国家，如中国为CN',
  `province` VARCHAR(20) NOT NULL DEFAULT '-1' COMMENT '用户个人资料填写的省份',
  `city` VARCHAR(30) NOT NULL DEFAULT '-1' COMMENT '普通用户个人资料填写的城市',
  `headimgurl` VARCHAR(100) NOT NULL DEFAULT '-1' COMMENT '微信头像地址',
  `rank` VARCHAR(10) NOT NULL DEFAULT '1' COMMENT '用户等级（1：初级）',
  `type` CHAR(2) NOT NULL DEFAULT '1' COMMENT '用户类型（1：微信）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_unique` (`openid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4


CREATE TABLE `t_recommend` (
  `id` varchar(64) NOT NULL,
  `ref_openid` varchar(255) NOT NULL COMMENT '推荐人openid',
  `refed_openid` varchar(255) NOT NULL COMMENT '被推荐人openid',
  `del_flag` char(2) NOT NULL DEFAULT '1' COMMENT '删除标记（1：否 2：是）',
  `state` char(2) DEFAULT '1' COMMENT '1:有效 2：失效',
  `descr` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_key` (`ref_openid`,`refed_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='扫码推荐表'


Create Table

CREATE TABLE `t_order_payment` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `account` varchar(32) DEFAULT NULL COMMENT '收款账号',
  `bank` varchar(32) DEFAULT NULL COMMENT '收款银行',
  `fee` decimal(6,2) DEFAULT NULL COMMENT '支付手续费',
  `payer` varchar(32) DEFAULT NULL COMMENT '付款人',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '付款金额',
  `payment_status` varchar(2) DEFAULT '1' COMMENT '支付状态（1：待支付 2:支付成功 3：支付失败）',
  `payment_method` varchar(2) DEFAULT '1' COMMENT '支付方式(1:微信支付)',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `payment_bank` varchar(32) DEFAULT NULL COMMENT '付款银行',
  `del_flag` char(2) NOT NULL DEFAULT '1' COMMENT '删除标记（1：否 2：是）',
  `state` char(2) DEFAULT '1' COMMENT '1:有效 2：失效',
  `descr` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `payer_index` (`payer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付订单表'



CREATE TABLE `t_withdrawal_apply` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `apply_open_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '申请人openId',
  `apply_userName` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '申请人用户名',
  `apply_phone` varchar(11) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '申请人手机号',
  `apply_amount` decimal(10,2) DEFAULT NULL COMMENT '申请提现金额',
  `apply_status` varchar(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '提现状态,0:未处理,1:处理失败,2:处理成功',
  `apply_time` timestamp NULL DEFAULT NULL COMMENT '申请提现时间',
  `card_no` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '提现卡号',
  `card_person_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '持卡人姓名',
  `province_city` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '所属城市',
  `bank_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '提现银行',
  `branch_bank_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '分行名称',
  `bank_trade_no` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '银行交易流水',
  `dispose_time` timestamp NULL DEFAULT NULL COMMENT '处理时间',
  `operator` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '操作人',
  `is_default` varchar(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '1.是  2.否 是否默认',
  `del_flag` char(2) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '删除标记（1：否 2：是）',
  `state` char(2) DEFAULT '1' COMMENT '1:有效 2：失效',
  `descr` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `apply_open_id_index` (`apply_open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现管理表'
