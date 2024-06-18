[웹에서 goodsone-ERD 보기](https://www.erdcloud.com/d/7xiHQwZxWYEkiJLYN)

# User 
user_id (PK): 고유 사용자 ID

user_password (NN): 사용자 비밀번호 (해시된 형태)
user_name (NN): 사용자 이름
user_nickname (NN): 사용자 닉네임
user_phone_agency (NN): 사용자 휴대폰 통신사
user_phone_number (NN): 사용자 휴대폰 번호
user_email (NN): 사용자 이메일
user_address (NN): 사용자 주소
created_at (NN): 사용자 등록 날짜
updated_at: 사용자 정보 수정 날짜

- Relationships
Comment 1:N

# Comment
comment_id (PK): 댓글 ID
user_id (FK): 댓글 작성 유저 ID
writing_id (FK): 댓글 상품 ID
comment_content (NN) : 댓글 내용
comment_date (NN) : 댓글 작성 일
parent_comment (FK): 답글할 댓글 ID

- Relationships
User 1:N
Buy 1:N
Sell 1:N

# Wishlist


# FavoriteBrands

# CelebrityCategory
category_id (PK) :
category_name :
parent_category_id :

# Annimation

# Goods
goods_id (PK): 고유 굿즈 ID
agency_id (FK) : 굿즈 소속 아이디
category_id (FK): 굿즈 카테고리 아이디
mkaer_id (FK): 굿즈 제작사 아이디

goods_name_eg: 굿즈 영어 이름
goods_name_kr: 굿즈 한국 이름
goods_price: 굿즈 가격
goods_image_url: 굿즈 이미지 URL
goods_description: 굿즈 설명
goods_release_date: 출시 날짜
color: 제품의 색상.
size: 제품의 크기 또는 사이즈 옵션.
created_at: 굿즈 등록 날짜
updated_at: 굿즈 정보 수정 날짜

- Relationships
Buy 1:
Sell

# PURCHASE_POST
buy_id (PK) : 구매 글 ID
user_id (FK): 글 작성한 ID
goods_id (FK): 구매 굿즈 ID
buy_date : 글 작성 날짜
buy_amount : 구매 총 금액
goods_status : 굿즈 상태 (미개봉,단순개봉,중고)
chat_count 

- Relationships
Comment 1:N

# SALE_POST
sell_id (PK)
user_id (FK): 글 작성한 ID
goods_id (FK): 판매 굿즈 ID
sell_date : 글 작성 날짜
sell_amount : 판매 금액
goods_status : 굿즈 상태 (미개봉,단순개봉,중고)
chat_count 

- Relationships
Comment 1:N