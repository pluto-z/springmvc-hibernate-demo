INSERT INTO SYS_ROLES (ID,DESCRIPTION,NAME) SELECT 1,'超级管理员','admin' FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM SYS_ROLES T2 WHERE T2.NAME='admin');
INSERT INTO SYS_ROLES (ID,DESCRIPTION,NAME) SELECT 2,'用户','user' FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM SYS_ROLES T2 WHERE T2.NAME='user');

INSERT INTO SYS_USERS (ID,SALT,USERNAME,PASSWORD,ENABLED,FULLNAME,EMAIL,CREATED_AT,UPDATED_AT)
  SELECT 1,'65dd17f4856a01a017c112d8851081f8','pluto4321@163.com','3f98e5d9974c909934ffef6f6192c41ae3b5e63ce07f75ee51b2b6632417c490',1,'pluto4321@163.com','超级管理员',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP() FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM SYS_USERS T1 WHERE T1.ID=1);

DROP SEQUENCE SEQ_SYS_ROLES;
CREATE SEQUENCE SEQ_SYS_ROLES START WITH 10 INCREMENT BY 1;
DROP SEQUENCE SEQ_SYS_USERS;
CREATE SEQUENCE SEQ_SYS_USERS START WITH 10 INCREMENT BY 1;

INSERT INTO SYS_ROLES_USERS (ROLE_ID, USER_ID)  SELECT ID,1 FROM SYS_ROLES WHERE NAME = 'admin' AND NOT EXISTS(SELECT 1 FROM SYS_ROLES_USERS WHERE USER_ID = 1) ;
