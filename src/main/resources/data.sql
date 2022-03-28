insert into sys_role (id, created_at, updated_at, role_desc, role_name)
values (1, CURRENT_DATE, CURRENT_DATE, 'USER', 'USER')
ON CONFLICT (role_name) DO NOTHING;



INSERT INTO algo VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'test1', 'test1', '/test/1', 1);


INSERT INTO category VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', '图像识别', '图像识别');


INSERT INTO data_set VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'ImageNet', '/dataset/imagenet', 1);


INSERT INTO menu_role VALUES (1, 1);


INSERT INTO sys_menu VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 1, NULL, '/icon', 'test', 'sys:admin', 1, 0, 1, '/test', NULL);


INSERT INTO sys_role VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'admin', 'ADMIN');


INSERT INTO sys_user VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', '$2a$10$Dh2dAqCB6Wkq2MoEU738L.B/12JJbOiXjPY/8/9LVWDBEdOf18OFq', 'tester');


INSERT INTO user_role VALUES (1, 1);

