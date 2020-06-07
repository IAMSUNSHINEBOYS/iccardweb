--系统用户表
create table sys_admin(
  id NUMBER NOT NULL ,
  username varchar2(20) NOT NULL ,
  name varchar2(80)  NOT NULL ,
  password varchar2(40) NOT NULL ,
  sex varchar2(2) NULL ,
  phone varchar2(20)NULL ,
  valid char(1) NULL ,
  createTime TIMESTAMP NULL ,
  lastTime TIMESTAMP NULL ,
  lastIp varchar2(15)  NULL ,
  headImg varchar2(50) NULL ,
  CONSTRAINT pk_admin_id PRIMARY KEY (id),
  CONSTRAINT uq_admin_username UNIQUE (username)
)
--序列
CREATE SEQUENCE SEQUENCE_ADMIN_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

--角色表
create table sys_role(
  id int not null,
  name varchar2(50),
  remark varchar2(100),
  CONSTRAINT pk_role_id PRIMARY KEY (id)
)

--序列
CREATE SEQUENCE SEQUENCE_ROLE_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

--功能表
create table sys_menu(
  id int NOT NULL ,
  parent int NULL ,
  name varchar2(50) NULL ,
  menuType char(1) NULL ,
  menuUrl varchar2(100) NULL ,
  perms varchar2(500) NULL ,
  orderNo smallint NULL ,
  icon varchar2(20) NULL ,
  enable char(1) default '1' ,
  CONSTRAINT pk_menu_id PRIMARY KEY (id),
  CONSTRAINT fk_menu_parent FOREIGN KEY (parent) REFERENCES sys_menu (id) ON DELETE CASCADE
)
--创建序列
CREATE SEQUENCE SEQUENCE_MENU_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

--用户角色表
create table sys_admin_role(
  admin int not null,
  role int not null,
  CONSTRAINT pk_admin_role PRIMARY KEY (admin, role),
  CONSTRAINT fk_admin_role_admin FOREIGN KEY (admin) REFERENCES sys_admin (id) ON DELETE CASCADE,
  CONSTRAINT fk_admin_role_role FOREIGN KEY (role) REFERENCES sys_role (id) ON DELETE CASCADE
)

--角色菜单
create table sys_role_menu(
  role int not null,
  menu int not null,
  CONSTRAINT pk_role_menu PRIMARY KEY (role, menu),
  CONSTRAINT fk_role_menu_menu FOREIGN KEY (menu) REFERENCES sys_menu (id) ON DELETE CASCADE,
  CONSTRAINT fk_role_menu_role FOREIGN KEY (role) REFERENCES sys_role (id) ON DELETE CASCADE
)


--在线用户
CREATE TABLE ic_online (
  id int NOT NULL,
  adminId int NOT NULL ,
  adminName varchar2(20) NULL ,
  adminUserName varchar2(20) NULL ,
  sex varchar2(2) NULL ,
  phone varchar2(20) NULL ,
  loginTime TIMESTAMP NULL ,
  sessionId varchar2(50) NULL ,
  ip varchar2(50) NULL ,
  CONSTRAINT pk_online_id PRIMARY KEY (id)
)

--序列
CREATE SEQUENCE SEQUENCE_ONLINE_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;


--登录日志
CREATE TABLE ic_login_record (
  id int NOT NULL ,
  recordType char(1),
  adminId int NOT NULL ,
  adminName varchar2(20) NULL ,
  adminUserName varchar2(20) NULL ,
  sex varchar(2) NULL ,
  phone varchar(20) NULL ,
  loginTime TIMESTAMP NULL ,
  sessionId varchar(50) NULL ,
  exitTime TIMESTAMP NULL ,
  ip varchar(50) NULL ,
  CONSTRAINT pk_login_record_id PRIMARY KEY (id)
)

--序列
CREATE SEQUENCE SEQUENCE_LOGINRECORD_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;


--在线用户
CREATE TABLE ic_online (
  id int NOT NULL ,
  adminId int NOT NULL ,
  adminName varchar2(20) NULL ,
  adminUserName varchar2(20) NULL ,
  sex varchar(2) NULL ,
  phone varchar(20) NULL ,
  loginTime TIMESTAMP NULL ,
  sessionId varchar(50) NULL ,
  ip varchar(50) NULL ,
  CONSTRAINT pk_online_id PRIMARY KEY (id)
)

--序列
CREATE SEQUENCE SEQUENCE_ONLINE_ID
  MINVALUE 1
  MAXVALUE 999999999999999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 20;

--菜单列表存储过程
create or replace procedure proc_admin_perms(amdinId in int)
as
perms VARCHAR2(50);
begin
  select perms into perms from sys_menu m where
  exists(select r.id from sys_admin_role ar inner join sys_role r on ar.role=r.id
  inner join sys_role_menu rm on rm.role=r.id and ar.admin=amdinId where rm.menu=m.id);
end;


--用户菜单列表存储过程
create or replace procedure proc_admin_permsList(amdinId in int)
as
id int;
parent int;
name varchar(50);
menuType char(1);
orderNo varchar2(6);
begin
 select id,parent,name,menuType,orderNo into id,parent,name,menuType,orderNo from sys_menu m where enable='1' and
exists(select r.id from sys_admin_role ar inner join sys_role r on ar.role=r.id
inner join sys_role_menu rm on rm.role=r.id and ar.admin=amdinId where rm.menu=m.id)
order by parent,orderNo;
end;

--查找用户菜单存储过程
create or replace procedure proc_main_menu(amdinId in int)
as
id int;
parent int;
name varchar(50);
menuType char(1);
menuUrl varchar2(100);
orderNo varchar2(6);
icon varchar2(20);
begin
select id,parent,name,menuType,menuUrl,orderNo,icon into id,parent,name,menuType,menuUrl,orderNo,icon from sys_menu m where enable='1' and (menuType=1 or menuType=2) and
exists(select r.id from sys_admin_role ar inner join sys_role r on ar.role=r.id
inner join sys_role_menu rm on rm.role=r.id and ar.admin=amdinId where rm.menu=m.id) order by orderNo;
end;
