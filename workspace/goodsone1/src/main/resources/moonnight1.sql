CREATE TABLE `user` (
	`user_seq`	INT AUTO_INCREMENT PRIMARY KEY,
	`email`	VARCHAR(100)	NOT NULL,
	`password`	VARCHAR(100)	NOT NULL,
	`name`	VARCHAR(100)	NOT NULL,
	`birth`	VARCHAR(100)	NOT NULL,
	`mobile_carrier`	ENUM("SKT","KT","LG")	,
	`phone`	VARCHAR(20)	NOT NULL,
    `status`	ENUM("NORMAL","STAY","STOP")	NOT NULL,
    `marketing_received_status`	BOOLEAN	NOT NULL,
	`created_at`	DATETIME	NOT NULL,
	`updated_at`	DATETIME
);
SELECT * FROM `user`;

DELETE FROM `user`;
DROP TABLE IF EXISTS `user`;
ALTER TABLE `user` AUTO_INCREMENT = 1;


CREATE TABLE `address` (
	`address_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT	NOT NULL,
	`postcode`	INT	NOT NULL,
	`main_address`	VARCHAR(255)	NOT NULL,
	`detail_address`	VARCHAR(255)	NOT NULL
);

ALTER TABLE `address` ADD CONSTRAINT `FK_user_TO_address_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

SELECT * FROM `address`;

DELETE FROM `address`;
DROP TABLE IF EXISTS `address`;
ALTER TABLE `address` AUTO_INCREMENT = 1;


create table `verification`(
`verification_seq` INT AUTO_INCREMENT PRIMARY KEY ,
`to` VARCHAR(50)	NOT NULL,
`verification_code` VARCHAR(20)	NOT NULL,
`status` VARCHAR(20)	NOT NULL,
`create_at` DATETIME	NOT NULL
);
SELECT * FROM `verification`;

DELETE FROM `verification`;
DROP TABLE IF EXISTS `verification`;
ALTER TABLE `verification` AUTO_INCREMENT = 1;

CREATE TABLE `estimate` (
    `estimate_seq` INT AUTO_INCREMENT PRIMARY KEY,  -- 고유한 식별자, 자동 증가
    `user_seq` INT,
    `name` VARCHAR(100) ,                   -- 이름
    `phone` VARCHAR(20) NOT NULL,                            -- 전화번호
    `email` VARCHAR(100),                           -- 이메일
    `emailAgree` BOOLEAN DEFAULT FALSE,             -- 이메일 동의 여부, 기본값은 FALSE
    `smsAgree` BOOLEAN DEFAULT FALSE,               -- SMS 동의 여부, 기본값은 FALSE
    `callAgree` BOOLEAN DEFAULT FALSE,              -- 전화 동의 여부, 기본값은 FALSE
    `postcode` VARCHAR(10) ,                         -- 우편번호
    `mainAddress` VARCHAR(255) NOT NULL,                     -- 메인 주소
    `detailAddress` VARCHAR(255) ,                   -- 상세 주소
    `content` TEXT,                                 -- 내용
    `imagesPath` TEXT,                              -- 이미지 경로
    `status` ENUM("RECEIVED", "IN_PROGRESS", "COMPLETED") NOT NULL,         
    `created_at` DATETIME NOT NULL -- 생성일시, 기본값은 현재 시간 
);
ALTER TABLE `estimate` ADD CONSTRAINT `FK_user_TO_estimate_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);
SELECT * FROM `estimate`;
SELECT * FROM estimate ORDER BY created_at DESC LIMIT 50 OFFSET 0;

DELETE FROM `estimate`;
DROP TABLE IF EXISTS `estimate`;
ALTER TABLE `estimate` AUTO_INCREMENT = 1;


CREATE TABLE `oauth` (
	`oauth_seq`	INT	AUTO_INCREMENT PRIMARY KEY,
	`user_seq`	INT,
	`provider`	ENUM ("NAVER", "KAKAO")	NOT NULL,
	`id`	VARCHAR(255)	NOT NULL,
	`email`	VARCHAR(255)	NOT NULL,
	`name`	VARCHAR(50)	,
	`birth`	VARCHAR(50)	,
	`phone`	VARCHAR(50)	,
    `status`	ENUM("NORMAL","STAY","STOP")	NOT NULL,
	`created_at`	DATETIME	NOT NULL
);

ALTER TABLE `oauth` ADD CONSTRAINT `FK_user_TO_oauth_1` FOREIGN KEY (
	`user_seq`
)
REFERENCES `user` (
	`user_seq`
);

SELECT * FROM `oauth`;

DELETE FROM `oauth`;
DROP TABLE IF EXISTS `oauth`;
ALTER TABLE `oauth` AUTO_INCREMENT = 1;
