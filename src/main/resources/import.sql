INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('bibi', '67', 'test1', 'bibi6666667@kakao.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('noname', '67', 'test2', 'non_named@naver.com');

INSERT INTO QUESTION (id, writer_id, title, contents, date_time) VALUES (1, 1, 'test1', 'test1 contents', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, date_time) VALUES (2, 2, 'test2', 'test2 contents', CURRENT_TIMESTAMP());
