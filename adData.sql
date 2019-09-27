

drop DATABASE ad_data ;
CREATE DATABASE ad_data character set utf8;

use ad_data;


CREATE TABLE `ad_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL DEFAULT '',
  `token` varchar(256) NOT NULL DEFAULT '' ,
  `user_status` tinyint(4) NOT NULL DEFAULT '0' ,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' ,
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


-- 推广计划表
CREATE TABLE `ad_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `user_id` bigint(20) NOT NULL DEFAULT '0' ,
  `plan_name` varchar(48) NOT NULL ,
  `plan_status` tinyint(4) NOT NULL DEFAULT '0' ,
  `start_date` datetime NOT NULL ,
  `end_date` datetime NOT NULL ,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ;

-- 推广单元表
CREATE TABLE `ad_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `plan_id` bigint(20) NOT NULL DEFAULT '0',
  `unit_name` varchar(48) NOT NULL ,
  `unit_status` tinyint(4) NOT NULL DEFAULT '0',
  `position_type` tinyint(4) NOT NULL DEFAULT '0' ,
  `budget` bigint(20) NOT NULL ,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


-- 创意表
CREATE TABLE `ad_creative` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(48) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `material_type` tinyint(4) NOT NULL DEFAULT '0',
  `height` int(10) NOT NULL DEFAULT '0' ,
  `width` int(10) NOT NULL DEFAULT '0',
  `size` bigint(20) NOT NULL DEFAULT '0',
  `duration` int(10) NOT NULL DEFAULT '0' ,
  `audit_status` tinyint(4) NOT NULL DEFAULT '0',
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `url` varchar(256) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' ,
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ;


-- 创意与推广单元的关联表
CREATE TABLE `creative_unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creative_id` bigint(20) NOT NULL DEFAULT '0',
  `unit_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


-- 推广单元关键词 Feature
CREATE TABLE `ad_unit_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_id` int(11) NOT NULL ,
  `keyword` varchar(30) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ;


-- 推广单元兴趣 Feature
CREATE TABLE `ad_unit_it` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_id` int(11) NOT NULL,
  `it_tag` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


-- 推广单元地域 Feature
CREATE TABLE `ad_unit_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_id` int(11) NOT NULL ,
  `province` varchar(30) NOT NULL,
  `city` varchar(30) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;