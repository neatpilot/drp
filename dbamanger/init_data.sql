--delete from t_user;

delete from t_data_dict;

delete from t_client;

delete from t_temi_client;

delete from t_table_id;

--初始系统管理员
--insert into t_user(user_id, user_name, password) values('root', '系统管理员', 'root123');

--初始化分销商级别
insert into t_data_dict(id, name, category) values('100', '1lDistributor', 'A');
insert into t_data_dict(id, name, category) values('101', '2lDistributor', 'A');
insert into t_data_dict(id, name, category) values('102', '3lDistributor', 'A');
insert into t_data_dict(id, name, category) values('103', '4lDistributor', 'A');

--初始化终端客户级别
insert into t_data_dict(id, name, category) values('200', 'AHospital', 'B');
insert into t_data_dict(id, name, category) values('201', 'BHospital', 'B');
insert into t_data_dict(id, name, category) values('202', 'CHospital', 'B');
insert into t_data_dict(id, name, category) values('203', 'pharmacy', 'B');
insert into t_data_dict(id, name, category) values('204', 'other', 'B');

--初始化物料类别
insert into t_data_dict(id, name, category) values('300', 'MedInstrument', 'C');
insert into t_data_dict(id, name, category) values('301', 'zhMed', 'C');
insert into t_data_dict(id, name, category) values('302', 'westMed', 'C');

--初始化计量单位
insert into t_data_dict(id, name, category) values('400', 'Case', 'D');
insert into t_data_dict(id, name, category) values('401', 'Slice', 'D');
insert into t_data_dict(id, name, category) values('402', 'Box', 'D');

--初始化分销商
insert into t_client(id, pid, name, is_leaf, is_client) values(10000, 0, 'ALL', 'N', 'N');
insert into t_client(id, pid, name, is_leaf, is_client) values(10001, 10000, 'North', 'N', 'N');
insert into t_client(id, pid, name, is_leaf, is_client) values(10002, 10000, 'EastNorth', 'Y', 'N');
insert into t_client(id, pid, name, is_leaf, is_client) values(10003, 10000, 'South', 'Y', 'N');
insert into t_client(id, pid, name, is_leaf, is_client) values(10004, 10001, 'Beijing', 'N', 'N');
insert into t_client(id, pid, name, client_level_id, client_id, is_leaf, is_client) values(10005, 10004, 'BjMedInc', '100', 'A0001', 'Y', 'Y');

--初始化终端客户
insert into t_temi_client(id, pid, name, is_leaf, is_temi_client) values(20000, 0, 'allClients', 'N', 'N');
insert into t_temi_client(id, pid, name, is_leaf, is_temi_client) values(20001, 20000, 'North', 'N', 'N');
insert into t_temi_client(id, pid, name, is_leaf, is_temi_client) values(20002, 20000, 'EastNorth', 'Y', 'N');
insert into t_temi_client(id, pid, name, is_leaf, is_temi_client) values(20003, 20000, 'South', 'Y', 'N');
insert into t_temi_client(id, pid, name, is_leaf, is_temi_client) values(20004, 20001, 'Beijing', 'N', 'N');
insert into t_temi_client(id, pid, name, temi_client_level_id, temi_client_id, is_leaf, is_temi_client) values(20005, 20004, 'BjMedHospital', '200', 'B0001', 'Y', 'Y');

--初始化主键维护表
insert into t_table_id(table_name, value) values('t_client', 10005);
insert into t_table_id(table_name, value) values('t_temi_client', 20005);
insert into t_table_id(table_name, value) values('t_fiscal_year_period', 0);
commit;



