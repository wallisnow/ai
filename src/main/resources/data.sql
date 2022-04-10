insert into sys_role (id, created_at, updated_at, role_desc, role_name)
values (1, CURRENT_DATE, CURRENT_DATE, 'USER', 'USER')
ON CONFLICT (role_name) DO NOTHING;

INSERT INTO category VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', '图像识别', '图像识别') ON CONFLICT DO NOTHING;

INSERT INTO data_set VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'ImageNet', '/Users/liujun/Desktop/AI/datasets/heart.csv', 1) ON CONFLICT DO NOTHING;

insert into repository (id, created_at, updated_at, name, path)
values (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'test_repo', '/Users/liujun/Desktop/AI/repo/1/') ON CONFLICT DO NOTHING;

insert into sys_user (id, created_at, updated_at, password, repo_key, username)
values (1,'2022-01-01 00:00:00', '2022-01-01 00:00:00', '$2a$10$Dh2dAqCB6Wkq2MoEU738L.B/12JJbOiXjPY/8/9LVWDBEdOf18OFq', 'key123321key', 'tester') ON CONFLICT DO NOTHING;

insert into algo (id, created_at, updated_at, description, is_completed, name, path, dataset_id, repository_id, userid)
values (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'test1', true, 'test1', '/Users/liujun/Desktop/AI/algos/1/print.py', 1, 1, 1) ON CONFLICT DO NOTHING;

INSERT INTO sys_menu VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 1, NULL, '/icon', 'test', 'sys:admin', 1, 0, 1, '/test', NULL) ON CONFLICT DO NOTHING;

INSERT INTO menu_role VALUES (1, 1) ON CONFLICT DO NOTHING;


INSERT INTO sys_role VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'admin', 'ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO sys_role VALUES (2, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'USER', 'USER') ON CONFLICT DO NOTHING;

INSERT INTO user_role VALUES (1, 1);

INSERT INTO repository VALUES (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', '行为分析', 'https://gitee.com/academy/drivermonitor-action') ON CONFLICT DO NOTHING;

