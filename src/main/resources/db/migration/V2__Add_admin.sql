insert into users (id,name,password,active)
            values (1,'admin',MD5('123'),true);
insert into user_role (id,roles)
            values (1,'USER'),(1,'ADMIN');
update  hibernate_sequence set next_val = next_val+1;