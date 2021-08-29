create database agiazoni character set utf8;
CREATE USER 'agiazoni' IDENTIFIED  WITH mysql_native_password BY 'agiazoni';
GRANT ALL PRIVILEGES ON agiazoni.* TO 'agiazoni'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;


create database agiazoni; -- Creates the new database
create user 'springuser'@'%' identified by 'ThePassword'; -- Creates the user
grant all on agiazoni.* to 'springuser'@'%'; -- Gives all privileges to the new user on the newly created database
