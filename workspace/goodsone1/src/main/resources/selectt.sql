SELECT * FROM `tier`;
SELECT * FROM `user`;
SELECT * FROM `address`;
SELECT * FROM `oauth`;

SELECT * FROM `category`;
SELECT * FROM `goods`;
SELECT * FROM `goods_option`;
SELECT * FROM `goods_main_image`;
SELECT * FROM `goods_detail_image`;
SELECT * FROM `cart`;

SELECT * FROM `order`;
SELECT * FROM `order_goods`;
SELECT * FROM `review`;
SELECT * FROM `return`;
SELECT * FROM `point_history`;
SELECT * FROM `verification`;

update user set updated_at = '2024-07-23 00:23:56' where user_seq='21';

SELECT * FROM `estimate`;
DELETE FROM `estimate`;
DROP TABLE IF EXISTS `estimate`;

SELECT * FROM estimate ORDER BY created_at DESC LIMIT 50 OFFSET 50;

CREATE TABLE `estimate` (
    `estimate_seq` INT AUTO_INCREMENT PRIMARY KEY,  -- 고유한 식별자, 자동 증가
    `name` VARCHAR(100) ,                   -- 이름, null 허용 안됨
    `phone` VARCHAR(20) NOT NULL,                            -- 전화번호
    `email` VARCHAR(100),                           -- 이메일
    `emailAgree` BOOLEAN DEFAULT FALSE,             -- 이메일 동의 여부, 기본값은 FALSE
    `smsAgree` BOOLEAN DEFAULT FALSE,               -- SMS 동의 여부, 기본값은 FALSE
    `callAgree` BOOLEAN DEFAULT FALSE,              -- 전화 동의 여부, 기본값은 FALSE
    `postcode` VARCHAR(10) NOT NULL,                         -- 우편번호
    `mainAddress` VARCHAR(255) NOT NULL,                     -- 메인 주소
    `detailAddress` VARCHAR(255) NOT NULL,                   -- 상세 주소
    `content` TEXT,                                 -- 내용
    `imagesPath` TEXT,                              -- 이미지 경로
    `status` ENUM("접수","답변중","완료") DEFAULT '접수',         -- 상태, 기본값은 'pending'
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP -- 생성일시, 기본값은 현재 시간
);