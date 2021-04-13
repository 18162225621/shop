/*
MySQL Data Transfer
Source Host: localhost
Source Database: datageneration
Target Host: localhost
Target Database: datageneration
Date: 2019/4/19 ������ ���� 3:51:58
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for node_news
-- ----------------------------
CREATE TABLE `node_news` (
  `newsId` int(11) NOT NULL,
  `nodeId` int(11) NOT NULL,
  `inresultpage` int(11) NOT NULL COMMENT '是否允许搜索到',
  `onlinetime` datetime NOT NULL,
  `status` int(11) NOT NULL COMMENT '是在线还是下线',
  `offlinetime` datetime DEFAULT NULL,
  PRIMARY KEY (`newsId`,`nodeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for system_param
-- ----------------------------
CREATE TABLE `system_param` (
  `param_name` varchar(255) NOT NULL,
  `param_value` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `system_param` VALUES ('incrementVersion', '0');
INSERT INTO `system_param` VALUES ('openAllAsset', 'true');
