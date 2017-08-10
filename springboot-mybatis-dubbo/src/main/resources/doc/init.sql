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



Create Table

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



