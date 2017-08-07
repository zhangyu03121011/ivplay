/*
SQLyog v10.2 
MySQL - 5.6.16 : Database - ivplay
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ivplay` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `ivplay`;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `open_id` varchar(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '-1' COMMENT 'openId',
  `pass_word` varchar(255) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '登录密码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '登录名',
  `phone` char(11) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '手机号码',
  `sex` char(2) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `last_login_ip_address` varchar(20) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '最后登录IP地址',
  `attenation` char(2) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。',
  `attenation_time` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `language` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '微信端用户的语言，简体中文为zh_CN',
  `un_attenation` char(2) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '是否取消关注状态（1:未取消 2：已取消）',
  `country` varchar(20) CHARACTER SET utf8mb4 DEFAULT '中国' COMMENT '国家，如中国为',
  `province` varchar(20) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '用户个人资料填写的省份',
  `city` varchar(30) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '普通用户个人资料填写的城市',
  `headimgurl` varchar(500) CHARACTER SET utf8mb4 DEFAULT '-1' COMMENT '微信头像地址',
  `rank` varchar(10) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '用户等级（1：初级）',
  `type` char(2) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '用户类型（1：微信）',
  `del_flag` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '删除标记（1：否 2：是）',
  `state` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '1:有效 2：失效',
  `descr` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid_unique` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`nick_name`,`open_id`,`pass_word`,`user_name`,`phone`,`sex`,`last_login_ip_address`,`attenation`,`attenation_time`,`language`,`un_attenation`,`country`,`province`,`city`,`headimgurl`,`rank`,`type`,`del_flag`,`state`,`descr`,`create_time`,`update_time`,`create_by`,`update_by`) values ('402881e45db12f52015db12f52710000','Jacky?','o5z7ywOP7qycrtAAxIqDfgMbfcFY',NULL,'Jacky%F0%9F%98%9C',NULL,'1','127.0.0.1','1','2017-08-05 14:50:19','zh_CN','2','中国','广东','深圳','http://wx.qlogo.cn/mmopen/NuHgcYg3e72iaTMfC76W5jvI1EH8p0Ab0kibI1mtkQ6uTXZicsYESwOEia1MdmpUH0rXlDHemkOkMsATdibrIy6xzrMibTAicNZibX49/0','1','1','1',NULL,NULL,'2017-08-05 14:57:35','2017-06-12 21:25:55',NULL,NULL);

/*Table structure for table `t_user_files` */

DROP TABLE IF EXISTS `t_user_files`;

CREATE TABLE `t_user_files` (
  `id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `open_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'openId',
  `file_names` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '文件全名',
  `file_new_names` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT 'uuib文件全名',
  `file_size` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '文件大小 单位B',
  `file_path` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '文件存储路径',
  `file_suffic` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '文件后缀',
  `file_category` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '文件分类（1：图片 2：视频）',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '打赏标题',
  `random` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '是否随机打赏金额（1：是 2：否）',
  `price` decimal(8,2) DEFAULT '0.00' COMMENT '打赏金额最大值',
  `price_min` decimal(8,2) DEFAULT '0.00' COMMENT '打赏金额最小值',
  `blur` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT '20' COMMENT '打赏图片模糊度',
  `state` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '状态（1：待审 2：违规 3:通过）',
  `del_flag` char(2) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '删除标记（1：未 2：是）',
  `descr` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '-1' COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `t_user_files` */

insert  into `t_user_files`(`id`,`open_id`,`file_names`,`file_new_names`,`file_size`,`file_path`,`file_suffic`,`file_category`,`title`,`random`,`price`,`price_min`,`blur`,`state`,`del_flag`,`descr`,`create_time`,`update_time`,`create_by`,`update_by`) values ('402881e45dbd26a1015dbd26a17b0000','o5z7ywOP7qycrtAAxIqDfgMbfcFY','IMG_3794.PNG','402881e45dbd26a1015dbd26a17b0000.PNG','477773','upload\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\402881e45dbd26a1015dbd26a17b0000.PNG','PNG','1','测试标题','1','102.00','102.00','60','1','1','-1','2017-08-07 22:43:48','2017-08-07 22:43:47.737',NULL,NULL),('402881e55db699b3015db699b3ad0000','o5z7ywOP7qycrtAAxIqDfgMbfcFY','IMG_4219.PNG','402881e55db699b3015db699b3ad0000.PNG','71578','upload\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\402881e55db699b3015db699b3ad0000.PNG','PNG','1','长按保存分享朋友','2','3.00','2.00','60','1','1','-1','2017-08-06 16:11:53','2017-08-06 16:11:52.715',NULL,NULL),('402881e55db6a106015db6a106420000','o5z7ywOP7qycrtAAxIqDfgMbfcFY','IMG_4088.JPG','402881e55db6a106015db6a106420000.JPG','502497','upload\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\402881e55db6a106015db6a106420000.JPG','JPG','1','长按保存分享朋友','2','3.00','2.00','60','3','2','-1','2017-08-06 16:19:55','2017-08-06 16:19:54.811',NULL,NULL),('402881e55db6a106015db6a3479c0001','o5z7ywOP7qycrtAAxIqDfgMbfcFY','IMG_4093.JPG','402881e55db6a106015db6a3479c0001.JPG','1639558','upload\\o5z7ywOP7qycrtAAxIqDfgMbfcFY\\402881e55db6a106015db6a3479c0001.JPG','JPG','1','长按保存分享朋友','2','3.00','2.00','60','3','1','-1','2017-08-06 16:22:34','2017-08-06 16:22:34.352',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
