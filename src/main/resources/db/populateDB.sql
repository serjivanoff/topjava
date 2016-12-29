DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
insert into meals(user_id,description,calories)VALUES (100000,'завтрак',2000);
insert into meals(user_id,description,calories)VALUES (100001,'завтрак',20);
insert into meals(user_id,description,calories)VALUES (100001,'обед',1800);
insert into meals(user_id,description,calories)VALUES (100001,'ужин',181);