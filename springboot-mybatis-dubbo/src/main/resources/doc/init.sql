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
  `id` VARCHAR(64) NOT NULL,
  `ref_openid` VARCHAR(255) NOT NULL COMMENT '推荐人openid',
  `refed_openid` VARCHAR(255) NOT NULL COMMENT '被推荐人openid',
  `is_effect` CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否生效 0-未生效，1-生效',
  `create_by` VARCHAR(64) DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  `update_by` VARCHAR(64) DEFAULT NULL,
  `update_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_key` (`ref_openid`,`refed_openid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='扫码推荐表'


