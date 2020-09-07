# 初始化用户
insert into t_user(mobile, password, avatar, nickname, email)
VALUES ('17620078988', '$2a$10$3NK7pYlkQQ8hHyDQfy9uWuTa91iFhrg9VXx7E2qBcHtxO6sKlDkUu', 'mqttx', 'admin',
        '85998282@qq.com');
insert into t_user(mobile, password, avatar, nickname, email)
VALUES ('18578458286', '$2a$10$3NK7pYlkQQ8hHyDQfy9uWuTa91iFhrg9VXx7E2qBcHtxO6sKlDkUu', 'tester', 'test',
        '85998282@qq.com');

# 初始化角色
insert into t_role(name)
values ('mqttx:admin');
insert into t_role(name)
values ('mqttx:test');

# 初始化用户-角色表
insert into t_user_role_bind(user_id, role_id)
VALUES (1, 1);
insert into t_user_role_bind(user_id, role_id)
VALUES (2, 2);

# 初始化权限
insert into t_permission(name)
values ('mqttx:admin_create');
insert into t_permission(name)
values ('mqttx:admin_delete');
insert into t_permission(name)
values ('mqttx:admin_update');
insert into t_permission(name)
values ('mqttx:admin_query');
insert into t_permission(name)
values ('mqttx:test_create');
insert into t_permission(name)
values ('mqttx:test_delete');
insert into t_permission(name)
values ('mqttx:test_update');
insert into t_permission(name)
values ('mqttx:test_query');


# 初始化角色、权限表
insert into t_role_permission_bind(role_id, permission_name)
values (1, 'mqttx:admin_create');
insert into t_role_permission_bind(role_id, permission_name)
values (1, 'mqttx:admin_delete');
insert into t_role_permission_bind(role_id, permission_name)
values (1, 'mqttx:admin_update');
insert into t_role_permission_bind(role_id, permission_name)
values (1, 'mqttx:admin_query');
insert into t_role_permission_bind(role_id, permission_name)
values (2, 'mqttx:test_create');
insert into t_role_permission_bind(role_id, permission_name)
values (2, 'mqttx:test_delete');
insert into t_role_permission_bind(role_id, permission_name)
values (2, 'mqttx:test_update');
insert into t_role_permission_bind(role_id, permission_name)
values (2, 'mqttx:test_query');

# 初始化主题，一个普通主题，一个共享主题
insert into t_topic(name)
values ('test/a');
insert into t_topic(name)
values ('$share/test/a');

# 初始化用户-主题绑定关系
insert into t_user_topic_bind(user_id, topic_id, rw)
values (1, 1, 3);
insert into t_user_topic_bind(user_id, topic_id, rw)
values (1, 2, 3);