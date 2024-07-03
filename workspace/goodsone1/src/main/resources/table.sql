CREATE TABLE user (
	user_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_email VARCHAR(100) NOT NULL UNIQUE,
	user_password VARCHAR(100) NOT NULL,
	user_oldpassword VARCHAR(100) NOT NULL,
	user_name VARCHAR(100) NOT NULL,
	user_nickname VARCHAR(100) NOT NULL,
	user_birth VARCHAR(100) NOT NULL,
	user_phone_agency ENUM('skt', 'kt', 'lg') NOT NULL,
	user_phone_number VARCHAR(20) NOT NULL,
	user_address VARCHAR(255) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    lastlogin_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    ctn_login INT,
	user_status ENUM('normal', 'stay', 'stop') NOT NULL,
	user_signtype ENUM('naver', 'kakao', 'normal') NOT NULL
);

select * from user;

CREATE TABLE login_log(
user_seq INT NOT NULL ,
create_at DATETIME NOT NULL
);

select * from login_log;

CREATE TABLE wishlist (
	user_seq INT NOT NULL,
	goods_seq INT NOT NULL
);

CREATE TABLE comment (
	comment_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_seq INT NOT NULL,
	purchase_seq INT NULL,
	sale_seq INT NULL,
	comment_content VARCHAR(255) NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
	parents_seq INT NULL
);

CREATE TABLE goods (
	goods_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	category_seq INT NOT NULL,
	affiliation_seq INT NOT NULL,
	goods_name VARCHAR(100) NOT NULL,
	goods_price INT NOT NULL,
	goods_image_url VARCHAR(255) NULL,
	goods_description VARCHAR(255) NULL,
	goods_release_date DATETIME NULL,
	goods_color VARCHAR(100) NULL,
	goods_size VARCHAR(100) NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
	category_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	category_name VARCHAR(255) NOT NULL,
	parents_category_seq INT NULL
);

CREATE TABLE affiliation (
	affiliation_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	affiliation_name VARCHAR(255) NOT NULL,
	parents_agency_seq INT NULL
);

CREATE TABLE purchase_post (
	purchase_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	goods_seq INT NOT NULL,
	user_seq INT NOT NULL,
	post_title VARCHAR(255) NOT NULL,
	post_description VARCHAR(255) NULL,
	post_amount INT NOT NULL,
	goods_status VARCHAR(255) NOT NULL,
	view_cnt INT NOT NULL,
	chat_cnt INT NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE sale_post (
	sale_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	goods_seq INT NOT NULL,
	user_seq INT NOT NULL,
	post_title VARCHAR(255) NOT NULL,
	post_description VARCHAR(255) NULL,
	post_amount INT NOT NULL,
	goods_status VARCHAR(255) NOT NULL,
	view_cnt INT NOT NULL,
	chat_cnt INT NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

create table sms(
sms_seq INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
phone INT NOT NULL,
athu INT NOT NULL,
create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 외래 키 제약 조건 추가
ALTER TABLE wishlist 
    ADD CONSTRAINT `FK_user_TO_wishlist_1` FOREIGN KEY (user_seq) REFERENCES `user` (user_seq),
    ADD CONSTRAINT `FK_goods_TO_wishlist_1` FOREIGN KEY (goods_seq) REFERENCES `goods` (goods_seq);
    
    ALTER TABLE login_log 
    ADD CONSTRAINT `FK_user_TO_login_log_1` FOREIGN KEY (user_seq) REFERENCES `user` (user_seq);

ALTER TABLE comment
    ADD CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (user_seq) REFERENCES `user` (user_seq),
    ADD CONSTRAINT `FK_purchase_post_TO_comment_1` FOREIGN KEY (purchase_seq) REFERENCES `purchase_post` (purchase_seq),
    ADD CONSTRAINT `FK_sale_post_TO_comment_1` FOREIGN KEY (sale_seq) REFERENCES `sale_post` (sale_seq),
    ADD CONSTRAINT `FK_comment_TO_comment_1` FOREIGN KEY (parents_seq) REFERENCES `comment` (comment_seq);

ALTER TABLE goods
    ADD CONSTRAINT `FK_category_TO_goods_1` FOREIGN KEY (category_seq) REFERENCES `category` (category_seq),
    ADD CONSTRAINT `FK_affiliation_TO_goods_1` FOREIGN KEY (affiliation_seq) REFERENCES `affiliation` (affiliation_seq);

ALTER TABLE category
    ADD CONSTRAINT `FK_category_TO_category_1` FOREIGN KEY (parents_category_seq) REFERENCES `category` (category_seq);

ALTER TABLE affiliation
    ADD CONSTRAINT `FK_affiliation_TO_affiliation_1` FOREIGN KEY (parents_agency_seq) REFERENCES `affiliation` (affiliation_seq);

ALTER TABLE purchase_post
    ADD CONSTRAINT `FK_goods_TO_purchase_post_1` FOREIGN KEY (goods_seq) REFERENCES `goods` (goods_seq),
    ADD CONSTRAINT `FK_user_TO_purchase_post_1` FOREIGN KEY (user_seq) REFERENCES `user` (user_seq);

ALTER TABLE sale_post
    ADD CONSTRAINT `FK_goods_TO_sale_post_1` FOREIGN KEY (goods_seq) REFERENCES `goods` (goods_seq),
    ADD CONSTRAINT `FK_user_TO_sale_post_1` FOREIGN KEY (user_seq) REFERENCES `user` (user_seq);