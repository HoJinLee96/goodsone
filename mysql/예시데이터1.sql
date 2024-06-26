INSERT INTO `user` (user_email, user_password, user_oldpassword, user_name, user_nickname, user_birth, user_phone_agency, user_phone_number, user_address, created_at, user_status, user_signtype)
VALUES
('chulsoo@example.com','passwd1A@!', 'oldpasswd1A@!', '김철수', '철수123', '1990-01-01', 'skt', '010-1234-5678', '서울시 강남구 테헤란로 123', NOW(), 'normal', 'naver'),
('younghee@example.com','passwd2B#$', 'oldpasswd2B#$', '박영희', '영희234', '1985-05-15', 'kt', '010-2345-6789',  '서울시 서초구 서초대로 456', NOW(), 'stay', 'kakao'),
('minho@example.com', 'passwd3C%^', 'oldpasswd3C%^', '이민호', '민호345', '1992-12-22', 'lg', '010-3456-7890',  '서울시 송파구 올림픽로 789', NOW(), 'stop', 'normal'),
('jieun@example.com', 'passwd4D&*', 'oldpasswd4D&*', '최지은', '지은456', '1995-08-08', 'skt', '010-4567-8901',  '부산시 해운대구 해운대로 789', NOW(), 'normal', 'naver'),
( 'woojin@example.com', 'passwd5E()_', 'oldpasswd5E()_', '장우진', '우진567', '1988-03-20', 'kt', '010-5678-9012', '대전시 유성구 대학로 123', NOW(), 'stay', 'kakao'),
('yura@example.com', 'passwd6F+{}', 'oldpasswd6F+{}', '한유라', '유라678', '1991-11-11', 'lg', '010-6789-0123',  '광주시 동구 금남로 456', NOW(), 'stop', 'normal'),
('jiho@example.com', 'passwd7G|?><', 'oldpasswd7G|?><', '정지호', '지호789', '1993-07-25', 'skt', '010-7890-1234',  '인천시 연수구 센트럴로 789', NOW(), 'normal', 'naver'),
('mijin@example.com',  'passwd8H@#$', 'oldpasswd8H@#$', '김미진', '미진890', '1987-02-28', 'kt', '010-8901-2345', '울산시 남구 삼산로 123', NOW(), 'stay', 'kakao');

select * from user;


INSERT INTO `category` (category_name, parents_category_seq)
VALUES
('액세서리', NULL), -- 1
('키링', 1), -- 2
('미디어', NULL), -- 3
('CD/DVD', 3), -- 4
('포토북', 3), -- 5 
('의류', NULL), -- 6
('티셔츠', 6), -- 7
('후드티', 6), -- 8
('모자', 6), -- 9
('포스터 & 아트 프린트', NULL), -- 10
('포토카드', 3), -- 11 
('그룹 포스터', 10), -- 12
('개인 포스터', 10) -- 13 
;

select*from category;

INSERT INTO `affiliation` (affiliation_name, parents_agency_seq)
VALUES
('하이브', NULL), -- 1
('JYP', NULL), -- 2
('뉴진스', 1), -- 3
('하니', 3), -- 4
('민지', 3), -- 5
('다니엘', 3), -- 6
('혜인', 3), -- 7
('해린', 3), -- 8
('정지훈', 2), -- 9
('SM', NULL), -- 10
('레드벨벳', 10), -- 11
('아이린', 11), -- 12
('슬기', 11), -- 13
('웬디', 11), -- 14
('조이', 11), -- 15
('예리', 11); -- 16

select*from affiliation;

INSERT INTO `goods` (category_seq, affiliation_seq, goods_name, goods_price, goods_image_url, goods_description, goods_release_date, goods_color, goods_size, created_at)
VALUES
(2, 1, '뉴진스 키링', 15000, 'http://example.com/keyring.jpg', '뉴진스 테마 키링', '2023-07-01', '파란색', '프리 사이즈', NOW()),
(4, 3, '뉴진스 앨범', 25000, 'http://example.com/album.jpg', '뉴진스의 최신 앨범', '2023-08-01', '블랙', '표준 사이즈', NOW()),
(4, 4, '뉴진스 CD', 20000, 'http://example.com/cd.jpg', '뉴진스 CD 한정판', '2023-09-01', '화이트', 'CD', NOW()),
(12, 5, '뉴진스 포스터', 10000, 'http://example.com/poster.jpg', '뉴진스 포스터 세트', '2023-10-01', '컬러', 'A2', NOW()),
(4, 1, '레드벨벳 앨범', 30000, 'http://example.com/red_album.jpg', '레드벨벳 최신 앨범', '2023-11-01', '빨강', '표준 사이즈', NOW()),
(12, 10, '레드벨벳 포스터', 12000, 'http://example.com/red_poster.jpg', '레드벨벳 포스터 세트', '2023-12-01', '컬러', 'A2', NOW()),
(11, 11, '아이린 포토카드', 8000, 'http://example.com/irene_photo.jpg', '아이린 한정판 포토카드', '2023-10-15', '컬러', '5x7', NOW()),
(11, 12, '슬기 포토카드', 8000, 'http://example.com/seulgi_photo.jpg', '슬기 한정판 포토카드', '2023-10-20', '컬러', '5x7', NOW()),
(11, 13, '웬디 포토카드', 8000, 'http://example.com/wendy_photo.jpg', '웬디 한정판 포토카드', '2023-10-25', '컬러', '5x7', NOW()),
(11, 14, '조이 포토카드', 8000, 'http://example.com/joy_photo.jpg', '조이 한정판 포토카드', '2023-10-30', '컬러', '5x7', NOW()),
(11, 15, '예리 포토카드', 8000, 'http://example.com/yeri_photo.jpg', '예리 한정판 포토카드', '2023-11-05', '컬러', '5x7', NOW()),
(11, 4, '하니 포토카드', 8000, 'http://example.com/yeri_photo.jpg', '하니 한정판 포토카드', '2023-11-05', '컬러', '5x7', NOW());

select*from goods;

INSERT INTO `wishlist` (user_seq, goods_seq)
VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5),
(4, 6),
(5, 7),
(6, 8),
(7, 9),
(8, 10),
(1, 11),
(2, 12);

select*from wishlist;

INSERT INTO `purchase_post` (goods_seq, user_seq, post_title, post_description, post_amount, goods_status, view_cnt, chat_cnt, created_at)
VALUES
(1, 1, '뉴진스 키링 구매 희망', '뉴진스 키링을 구하고 있습니다.', 1, '새 제품', 50, 3, NOW()),
(2, 2, '뉴진스 앨범 구매 원해요', '뉴진스의 최신 앨범을 찾고 있습니다.', 1, '중고', 70, 5, NOW()),
(3, 3, '뉴진스 CD 구매 희망', '뉴진스 CD 한정판 구합니다.', 1, '새 제품', 60, 4, NOW()),
(4, 4, '뉴진스 포스터 구입 희망', '뉴진스 포스터 세트를 사고 싶습니다.', 1, '중고', 80, 6, NOW()),
(5, 5, '레드벨벳 앨범 찾습니다', '레드벨벳의 최신 앨범을 구하고 있습니다.', 1, '새 제품', 100, 7, NOW());

select*from purchase_post;

INSERT INTO `sale_post` (goods_seq, user_seq, post_title, post_description, post_amount, goods_status, view_cnt, chat_cnt, created_at)
VALUES
(6, 1, '레드벨벳 포스터 판매', '레드벨벳 포스터 세트 팝니다.', 1, '새 제품', 120, 10, NOW()),
(7, 2, '아이린 포토카드 판매', '아이린의 한정판 포토카드를 판매합니다.', 1, '중고', 140, 8, NOW()),
(8, 3, '슬기 포토카드 판매합니다', '슬기의 한정판 포토카드를 팝니다.', 1, '새 제품', 110, 9, NOW()),
(9, 4, '웬디 포토카드 판매', '웬디의 한정판 포토카드를 팝니다.', 1, '새 제품', 130, 6, NOW()),
(10, 5, '조이 포토카드 판매', '조이의 한정판 포토카드를 판매합니다.', 1, '중고', 150, 11, NOW());

select*from sale_post;

INSERT INTO `comment` (user_seq, purchase_seq, sale_seq, comment_content, created_at, parents_seq)
VALUES
(1, 1, NULL, '구매가 매우 만족스러웠습니다!', NOW(), NULL),
(2, NULL, 1, '판매하신 물건이 정말 좋네요.', NOW(), NULL),
(4, 2, NULL, '원활한 거래였습니다. 감사합니다.', NOW(), NULL),
(5, NULL, 3, '가격을 좀 더 낮출 수 있을까요?', NOW(), NULL),
(6, 3, NULL, '구매가 아주 만족스러웠어요!', NOW(), NULL),
(7, NULL, 4, '판매자가 매우 친절했습니다.', NOW(), NULL),
(8, NULL, 5, '좋은 물건을 저렴하게 구입했습니다.', NOW(), NULL),
(1, 4, NULL, '거래가 매우 원활하게 진행되었습니다.', NOW(), NULL);

INSERT INTO `comment` (user_seq, purchase_seq, sale_seq, comment_content, created_at, parents_seq)
VALUES (3, NULL, 2, '감사합니다.', NOW(), 1);

select*from comment;