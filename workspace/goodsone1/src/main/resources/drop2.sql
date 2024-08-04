DELETE FROM `user`;
DELETE FROM `address`;
DELETE FROM `cart`;
DELETE FROM `review`;
DELETE FROM `goods`;
-- DELETE FROM `category` WHERE `category_seq` IN (15,16,17,18,19,20,21);DELETE FROM `order`;
DELETE FROM `goods_main_image`;
DELETE FROM `goods_detail_image`;
DELETE FROM `goods_option`;
DELETE FROM `order_goods`;
DELETE FROM `return`;
DELETE FROM `tier`;
DELETE FROM `point_history`;
DELETE FROM `oauth`;
DELETE FROM `oauth`;
DELETE FROM `verification`;


commit;

DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `address`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `goods`;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `goods_main_image`;
DROP TABLE IF EXISTS `goods_detail_image`;
DROP TABLE IF EXISTS `goods_option`;
DROP TABLE IF EXISTS `order_goods`;
DROP TABLE IF EXISTS `return`;
DROP TABLE IF EXISTS `tier`;
DROP TABLE IF EXISTS `point_history`;
DROP TABLE IF EXISTS `oauth`;
DROP TABLE IF EXISTS `verification`;


commit;

ALTER TABLE `user` AUTO_INCREMENT = 1;
ALTER TABLE `cart` AUTO_INCREMENT = 1;
ALTER TABLE `review` AUTO_INCREMENT = 1;
ALTER TABLE `goods` AUTO_INCREMENT = 1;
ALTER TABLE `category` AUTO_INCREMENT = 1;
ALTER TABLE `order` AUTO_INCREMENT = 1;
ALTER TABLE `goods_main_image` AUTO_INCREMENT = 1;
ALTER TABLE `goods_detail_image` AUTO_INCREMENT = 1;
ALTER TABLE `goods_option` AUTO_INCREMENT = 1;
ALTER TABLE `order_goods` AUTO_INCREMENT = 1;
ALTER TABLE `return` AUTO_INCREMENT = 1;
ALTER TABLE `address` AUTO_INCREMENT = 1;
ALTER TABLE `tier` AUTO_INCREMENT = 1;
ALTER TABLE `point_history` AUTO_INCREMENT = 1;

commit