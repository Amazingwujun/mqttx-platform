# 初始化权限
insert into t_permission(name) values ('mqttx:admin_create');
insert into t_permission(name) values ('mqttx:admin_delete');
insert into t_permission(name) values ('mqttx:admin_update');
insert into t_permission(name) values ('mqttx:admin_query');
insert into t_permission(name) values ('mqttx:test_create');
insert into t_permission(name) values ('mqttx:test_delete');
insert into t_permission(name) values ('mqttx:test_update');
insert into t_permission(name) values ('mqttx:test_query');

# 初始化角色
insert into t_role(name) values ('mqttx:admin');

# 初始化角色、权限表
insert into t_role_permission_bind(role_id, permission_id) values (1,1);
insert into t_role_permission_bind(role_id, permission_id) values (1,2);
insert into t_role_permission_bind(role_id, permission_id) values (1,3);
insert into t_role_permission_bind(role_id, permission_id) values (1,4);