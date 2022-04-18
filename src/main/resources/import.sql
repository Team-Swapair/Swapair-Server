-- 초기화 스크립트

--USER초기화
INSERT INTO USER(USER_ID, EMAIL) VALUES(1, 'test1@gmail.com');

--GOODS 초기화
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(1, '이승기 LP 1집');
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(2, '잔나비 LP 1집');
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(3, '혁오 LP 1집');
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(4, '브라운아이즈소울 LP 1집');
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(5, '싸이 LP 1집');
INSERT INTO GOODS(GOODS_ID, GOODS_NAME) VALUES(6, '태연 LP 1집');

--CATEGORY 초기화
INSERT INTO CATEGORY(CATEGORY_ID, CATEGORY_NAME) VALUES(1,'LP');

--POST 초기화
INSERT INTO POST(POST_ID, USER_ID, POST_TITLE, POST_CONTENT, CATEGORY_ID, WANT_IMAGE, HAVE_IMAGE, IS_CLOSED, IS_CHECKED, CREATED_AT) VALUES(1,1,'이승기 LP 팝니다', '하자없고 미개봉입니다.', 1, 'https://pbs.twimg.com/media/EVykiMUUEAAliYf?format=jpg&name=large','https://pbs.twimg.com/media/EVykiMUUEAAliYf?format=jpg&name=large', FALSE, FALSE, '2021-08-01T15:41:20')
INSERT INTO POST(POST_ID, USER_ID, POST_TITLE, POST_CONTENT, CATEGORY_ID, WANT_IMAGE, HAVE_IMAGE, IS_CLOSED, IS_CHECKED, CREATED_AT) VALUES(2,1,'잔나비 LP 팝니다', '하자없고 미개봉입니다.', 1, 'https://pbs.twimg.com/media/EVykiMUUEAAliYf?format=jpg&name=large','http://m.drgroove.co.kr/web/product/big/201905/bae7ada1843143c79fc71e636803d144.jpg', FALSE, FALSE, '2021-08-01T15:41:20')
INSERT INTO POST(POST_ID, USER_ID, POST_TITLE, POST_CONTENT, CATEGORY_ID, WANT_IMAGE, HAVE_IMAGE, IS_CLOSED, IS_CHECKED, CREATED_AT) VALUES(3,1,'울루불루 LP 팝니다', '하자없고 미개봉입니다.', 1, 'https://pbs.twimg.com/media/EVykiMUUEAAliYf?format=jpg&name=large','https://image.yes24.com/usedshop/2020/0818/524046f2-0581-4a7e-85bd-510965f088f4.jpg', FALSE, FALSE, '2021-08-01T15:41:20')
INSERT INTO POST(POST_ID, USER_ID, POST_TITLE, POST_CONTENT, CATEGORY_ID, WANT_IMAGE, HAVE_IMAGE, IS_CLOSED, IS_CHECKED, CREATED_AT) VALUES(4,1,'얄리얄라 LP 팝니다', '하자없고 미개봉입니다.', 1, 'https://pbs.twimg.com/media/EVykiMUUEAAliYf?format=jpg&name=large','https://media.bunjang.co.kr/product/181740740_1_1647066353_w360.jpg', FALSE, FALSE, '2021-08-01T15:41:20')

--HAVE_GOODS 초기화
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(1, 1, 1);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(2, 3, 2);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(3, 4, 2);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(4, 5, 3);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(5, 6, 3);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(6, 2, 4);
INSERT INTO HAVE_GOODS(HAVE_ID, GOODS_ID, POST_ID) VALUES(7, 6, 4);

--WANT_GOODS 초기화
INSERT INTO WANT_GOODS(WANT_ID, GOODS_ID, POST_ID) VALUES(1, 3, 1);
INSERT INTO WANT_GOODS(WANT_ID, GOODS_ID, POST_ID) VALUES(2, 5, 2);
INSERT INTO WANT_GOODS(WANT_ID, GOODS_ID, POST_ID) VALUES(3, 6, 2);
INSERT INTO WANT_GOODS(WANT_ID, GOODS_ID, POST_ID) VALUES(4, 2, 3);
INSERT INTO WANT_GOODS(WANT_ID, GOODS_ID, POST_ID) VALUES(5, 1, 4);