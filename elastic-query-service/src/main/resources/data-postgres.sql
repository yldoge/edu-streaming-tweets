CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


INSERT INTO keycloak.users(
	id, username, firstname, lastname)
	VALUES ('4fd0af8a-8260-4bf6-845d-3559f7e57f73', 'app_user', 'Standard', 'User');
INSERT INTO keycloak.users(
	id, username, firstname, lastname)
	VALUES ('22cf7923-7942-4b63-b88a-b08f9b3eae57', 'app_admin', 'Admin', 'User');
INSERT INTO keycloak.users(
	id, username, firstname, lastname)
	VALUES ('7b75f3ef-b21f-4c3c-8d7a-f841d292df41', 'app_super_user', 'Super', 'User');


insert into documents(id, document_id)
values ('c1df7d01-4bd7-40b6-86da-7e2ffabf37f7', '2372974142031158047');
insert into documents(id, document_id)
values ('f2b2d644-3a08-4acb-ae07-20569f6f2a01', '6546642076695032680');
insert into documents(id, document_id)
values ('90573d2b-9a5d-409e-bbb6-b94189709a19', '7771110656328384825');

insert into user_permissions(user_permission_id, user_id, document_id, permission_type)
values (uuid_generate_v4(),'4fd0af8a-8260-4bf6-845d-3559f7e57f73', 'c1df7d01-4bd7-40b6-86da-7e2ffabf37f7', 'READ');

insert into user_permissions(user_permission_id, user_id, document_id, permission_type)
values (uuid_generate_v4(),'22cf7923-7942-4b63-b88a-b08f9b3eae57', 'c1df7d01-4bd7-40b6-86da-7e2ffabf37f7', 'READ');

insert into user_permissions(user_permission_id, user_id, document_id, permission_type)
values (uuid_generate_v4(),'22cf7923-7942-4b63-b88a-b08f9b3eae57', 'f2b2d644-3a08-4acb-ae07-20569f6f2a01', 'READ');

insert into user_permissions(user_permission_id, user_id, document_id, permission_type)
values (uuid_generate_v4(), '22cf7923-7942-4b63-b88a-b08f9b3eae57', '90573d2b-9a5d-409e-bbb6-b94189709a19', 'READ');

insert into user_permissions(user_permission_id, user_id, document_id, permission_type)
values (uuid_generate_v4(), '7b75f3ef-b21f-4c3c-8d7a-f841d292df41', 'c1df7d01-4bd7-40b6-86da-7e2ffabf37f7', 'READ');


