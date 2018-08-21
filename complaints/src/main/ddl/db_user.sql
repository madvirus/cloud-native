create user 'demo'@'localhost' identified by 'demo';

create database demo character set=utf8;

grant all privileges on demo.* to 'demo'@'localhost';