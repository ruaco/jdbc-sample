#
# Structure for table "order"
#

# 建库
CREATE database test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

# 建表
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `uesr_id` bigint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime DEFAULT NULL,
  `attributes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


# 测试数据
INSERT INTO `order` VALUES (1,'Tom',9527,'2012-10-29 00:00:00','2012-12-30 00:00:00','a:1'),(2,'Jack',2938,'2015-12-29 00:00:00','2015-12-30 00:00:00','b:2;test:1');
