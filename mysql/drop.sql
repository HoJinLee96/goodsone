-- Comment 데이터 삭제 (외래 키로 다른 테이블을 참조하므로 먼저 삭제)
DELETE FROM `comment`;

-- Wishlist 데이터 삭제 (외래 키로 다른 테이블을 참조하므로 삭제)
DELETE FROM `wishlist`;

-- Purchase Post 데이터 삭제
DELETE FROM `purchase_post`;

-- Sale Post 데이터 삭제
DELETE FROM `sale_post`;

-- Goods 데이터 삭제 (외래 키로 다른 테이블을 참조하므로 삭제)
DELETE FROM `goods`;

-- Category 데이터 삭제 (외래 키로 다른 테이블을 참조하므로 삭제)
DELETE FROM `category`;

-- Affiliation 데이터 삭제 (외래 키로 다른 테이블을 참조하므로 삭제)
DELETE FROM `affiliation`;

-- User 데이터 삭제
DELETE FROM `user`;

-- Comment 테이블 삭제
DROP TABLE IF EXISTS `comment`;

-- Wishlist 테이블 삭제
DROP TABLE IF EXISTS `wishlist`;

-- Purchase Post 테이블 삭제
DROP TABLE IF EXISTS `purchase_post`;

-- Sale Post 테이블 삭제
DROP TABLE IF EXISTS `sale_post`;

-- Goods 테이블 삭제
DROP TABLE IF EXISTS `goods`;

-- Category 테이블 삭제
DROP TABLE IF EXISTS `category`;

-- Affiliation 테이블 삭제
DROP TABLE IF EXISTS `affiliation`;

-- User 테이블 삭제
DROP TABLE IF EXISTS `user`;





