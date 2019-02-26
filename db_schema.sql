-- 创建数据库
create database if not exists db0 character set utf8;
create database if not exists db1 character set utf8;

use db0;
use db1;

drop table if exists `tbl_order_0`;
drop table if exists `tbl_order_1`;
drop table if exists `tbl_order_item_0`;
drop table if exists `tbl_order_item_1`;

create table `tbl_order_0` (
  `id` bigint(20) unsigned not null auto_increment,
  `order_id` varchar(128) not null,
  `user_id` bigint(20) not null,
  `order_name` varchar(256) null,
  primary key (`id`)
) engine=innodb auto_increment=10001 default charset=utf8 ;

create table `tbl_order_1` (
  `id` bigint(20) unsigned not null auto_increment,
  `order_id` varchar(128) not null,
  `user_id` bigint(20) not null,
  `order_name` varchar(256) null,
  primary key (`id`)
) engine=innodb auto_increment=10001 default charset=utf8 ;

create table `tbl_order_item_0` (
  `id` bigint(20) unsigned not null auto_increment,
  `order_id` varchar(128) not null,
  `user_id` bigint(20) not null,
  `item_id` varchar(128) not null,
  `item_name` varchar(256) null,
  primary key (`id`)
) engine=innodb auto_increment=10001 default charset=utf8 ;

create table `tbl_order_item_1` (
  `id` bigint(20) unsigned not null auto_increment,
  `order_id` varchar(128) not null,
  `user_id` bigint(20) not null,
  `item_id` varchar(128) not null,
  `item_name` varchar(256) null,
  primary key (`id`)
) engine=innodb auto_increment=10001 default charset=utf8 ;


