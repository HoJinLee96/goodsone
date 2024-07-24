CREATE TABLE `user` (
	`user_seq`	INT AUTO_INCREMENT PRIMARY KEY,
	`email`	VARCHAR(100)	NOT NULL,
	`password`	VARCHAR(100)	NOT NULL,
	`oldpassword`	VARCHAR(100)	,
	`name`	VARCHAR(100)	NOT NULL,
	`nickname`	VARCHAR(100) ,
	`birth`	VARCHAR(100)	NOT NULL,
	`mobile_carrier`	ENUM("skt","kt","lg")	,
	`phone`	VARCHAR(20)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	,
	`status`	ENUM("public","stay","stop")	NOT NULL,
	`marketing_received_status`	BOOLEAN	NOT NULL,
	`tier_name`	VARCHAR(20)	NOT NULL
);

CREATE TABLE `address` (
	`address_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT	NOT NULL,
	`postcode`	INT	NOT NULL,
	`main_address`	VARCHAR(255)	NOT NULL,
	`detail_address`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `oauth` (
	`oauth_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT,
	`provider`	ENUM ("NAVER", "KAKAO")	NOT NULL,
	`id`	VARCHAR(255)	NOT NULL,
	`email`	VARCHAR(255)	NOT NULL,
	`name`	VARCHAR(50)	NOT NULL,
	`birth`	VARCHAR(50)	NOT NULL,
	`phone`	VARCHAR(50)	NOT NULL,
	`created_at`	DATETIME	NOT NULL
);

CREATE TABLE `cart` (
	`cart_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT	NOT NULL,
	`option_seq`	INT	NOT NULL,
	`count`	INT	NOT NULL
);

CREATE TABLE `review` (
	`review_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT	NOT NULL,
	`order_goods_seq`	INT	NOT NULL,
	`review_content`	VARCHAR(255)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NULL
);

CREATE TABLE `goods` (
	`goods_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`category_seq`	INT	NOT NULL,
	`goods_name`	VARCHAR(100)	NOT NULL,
	`goods_description`	VARCHAR(255)	NOT NULL,
	`goods_origin`	VARCHAR(100)	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME	NULL
);

CREATE TABLE `category` (
	`category_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`category_name`	VARCHAR(255)	NOT NULL,
	`parents_category_seq`	INT	NULL
);

CREATE TABLE `order` (
	`order_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT	NOT NULL,
	`price`	INT	NOT NULL,
	`status`	VARCHAR(50)	NOT NULL,
	`recipient`	VARCHAR(50)	NOT NULL,
	`recipient_phone`	VARCHAR(50)	NOT NULL,
	`recipient_address`	VARCHAR(255)	NOT NULL,
	`memo`	VARCHAR(255)	NULL,
	`created_at`	DATETIME	NOT NULL
);

CREATE TABLE `goods_main_image` (
	`image_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`goods_seq`	INT	NOT NULL,
	`image_url`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `goods_detail_image` (
	`image_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`goods_seq`	INT	NOT NULL,
	`image_url`	VARCHAR(255)	NOT NULL
);

CREATE TABLE `goods_option` (
	`option_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`goods_seq`	INT	NOT NULL,
	`public_price`	VARCHAR(50)	NULL,
	`sell_price`	VARCHAR(50)	NOT NULL,
	`weight`	VARCHAR(50)	NOT NULL
);

CREATE TABLE `order_goods` (
	`order_goods_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`order_seq`	INT	NOT NULL,
	`option_seq`	INT	NOT NULL,
	`count`	INT	NOT NULL,
	`courier`	VARCHAR(50)	NULL,
	`courier_seq`	VARCHAR(50)	NULL,
	`status`	VARCHAR(50)	NOT NULL,
	`created_at`	DATETIME	NOT NULL
);

CREATE TABLE `return` (
	`return_key`	INT	AUTO_INCREMENT PRIMARY KEY, 
	`order_goods_seq`	INT	NOT NULL,
	`count`	INT	NOT NULL,
	`courier`	VARCHAR(50)	NULL,
	`courier_seq`	VARCHAR(50)	NULL,
	`status`	VARCHAR(50)	NULL,
	`apply_return_at`	DATETIME	NOT NULL
);

CREATE TABLE `tier` (
	`tier_name`	VARCHAR(20)	PRIMARY KEY,
	`benefit`	INT	NOT NULL,
	`condition`	INT	NOT NULL
);

CREATE TABLE `point_history` (
	`point_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`order_goods_seq`	INT	NOT NULL,
	`user_seq`	INT	NOT NULL,
	`point`	INT	NOT NULL,
	`create_at`	DATETIME	NOT NULL
);

create table `verification`(
`verification_seq` INT AUTO_INCREMENT PRIMARY KEY ,
`to` VARCHAR(50)	NOT NULL,
`verification_code` VARCHAR(20)	NOT NULL,
`status` VARCHAR(20)	NOT NULL,
`create_at` DATETIME	NOT NULL
);

ALTER TABLE `user` ADD CONSTRAINT `FK_tier_TO_user_1` FOREIGN KEY (
	`tier_name`
)
REFERENCES `tier` (
	`tier_name`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_user_TO_cart_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

ALTER TABLE `cart` ADD CONSTRAINT `FK_goods_option_TO_cart_1` FOREIGN KEY (
	`option_seq`
)
REFERENCES `goods_option` (
	`option_seq`
);

ALTER TABLE `review` ADD CONSTRAINT `FK_user_TO_review_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

ALTER TABLE `review` ADD CONSTRAINT `FK_order_goods_TO_review_1` FOREIGN KEY (
	`order_goods_seq`
)
REFERENCES `order_goods` (
	`order_goods_seq`
);

ALTER TABLE `goods` ADD CONSTRAINT `FK_category_TO_goods_1` FOREIGN KEY (
	`category_seq`
)
REFERENCES `category` (
	`category_seq`
);

ALTER TABLE `category` ADD CONSTRAINT `FK_category_TO_category_1` FOREIGN KEY (
	`parents_category_seq`
)
REFERENCES `category` (
	`category_seq`
);

ALTER TABLE `order` ADD CONSTRAINT `FK_user_TO_order_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

ALTER TABLE `goods_main_image` ADD CONSTRAINT `FK_goods_TO_goods_main_image_1` FOREIGN KEY (
	`goods_seq`
)
REFERENCES `goods` (
	`goods_seq`
);

ALTER TABLE `goods_detail_image` ADD CONSTRAINT `FK_goods_TO_goods_detail_image_1` FOREIGN KEY (
	`goods_seq`
)
REFERENCES `goods` (
	`goods_seq`
);

ALTER TABLE `goods_option` ADD CONSTRAINT `FK_goods_TO_goods_option_1` FOREIGN KEY (
	`goods_seq`
)
REFERENCES `goods` (
	`goods_seq`
);

ALTER TABLE `order_goods` ADD CONSTRAINT `FK_order_TO_order_goods_1` FOREIGN KEY (
	`order_seq`
)
REFERENCES `order` (
	`order_seq`
);

ALTER TABLE `order_goods` ADD CONSTRAINT `FK_goods_option_TO_order_goods_1` FOREIGN KEY (
	`option_seq`
)
REFERENCES `goods_option` (
	`option_seq`
);

ALTER TABLE `return` ADD CONSTRAINT `FK_order_goods_TO_return_1` FOREIGN KEY (
	`order_goods_seq`
)
REFERENCES `order_goods` (
	`order_goods_seq`
);

ALTER TABLE `address` ADD CONSTRAINT `FK_user_TO_address_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

ALTER TABLE `point_history` ADD CONSTRAINT `FK_order_goods_TO_point_history_1` FOREIGN KEY (
	`order_goods_seq`
)
REFERENCES `order_goods` (
	`order_goods_seq`
);

ALTER TABLE `point_history` ADD CONSTRAINT `FK_user_TO_point_history_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

ALTER TABLE `oauth` ADD CONSTRAINT `FK_user_TO_oauth_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);