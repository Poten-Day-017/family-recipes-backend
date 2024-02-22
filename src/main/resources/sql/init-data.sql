INSERT INTO recipe(capacity, order_no, cooking_image_url, cooking_video_url, created_at, created_by, modified_at, modified_by, user_id, origin, title, content, category, total_open_yn)
VALUES (1, 1, 'http://www.daedaesonson.site/static/sample1.jpg', '', NOW(), 1, NOW(), 1, 1, '엄마', '어머니의 김치찌개', '레시피 소개 샘플', '001', 'Y');

INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (1, 1, '돼지고기', '300g', 'Y');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (1, 2, '김치', '1포기', 'Y');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (1, 3, '양파', '1/2개', 'Y');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (1, 1, '설탕', '1스푼', 'N');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (1, 2, '다진마늘', '1스푼', 'N');

INSERT INTO procedures(recipe_id, order_no, image_url, description)
VALUES (1, 1, 'http://www.daedaesonson.site/static/sample3.jpg', '돼지고기를 넣고 끓인다');
INSERT INTO procedures(recipe_id, order_no, image_url, description)
VALUES (1, 2, 'http://www.daedaesonson.site/static/sample5.jpeg', '김치넣고 볶다가 끓인다');

INSERT INTO recipe(capacity, order_no, cooking_image_url, cooking_video_url, created_at, created_by, modified_at, modified_by, user_id, origin, title, content, category, total_open_yn)
VALUES (1, 2, 'http://www.daedaesonson.site/static/sample8.jpeg', '', NOW(), 1, NOW(), 1, 1, '아빠', '아빠의 김치볶음밥', '레시피 소개 샘플2', '001', 'Y');

INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (2, 1, '대파', '1/2개', 'Y');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (2, 2, '돼지고기', '600g', 'Y');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (2, 3, '김치', '300g', 'Y');

INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (2, 1, '간장', '2스푼', 'N');
INSERT INTO ingredient(recipe_id, order_no, name, amount, required_yn)
VALUES (2, 2, '고춧가루', '1스푼', 'N');

INSERT INTO procedures(recipe_id, order_no, image_url, description)
VALUES (2, 1, 'http://www.daedaesonson.site/static/sample6.jpeg', '김치를 볶고 돼지고기도 볶는다');
INSERT INTO procedures(recipe_id, order_no, image_url, description)
VALUES (2, 2, 'http://www.daedaesonson.site/static/sample7.jpeg', '다른 재료 넣고 마저 볶는다');