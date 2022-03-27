insert into sys_role (id, created_at, updated_at, role_desc, role_name)
values (1, CURRENT_DATE, CURRENT_DATE, 'USER', 'USER')
ON CONFLICT (role_name) DO NOTHING;