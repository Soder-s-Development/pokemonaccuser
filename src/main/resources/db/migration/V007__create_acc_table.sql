create table account(
	id bigint not null auto_increment primary key,
	first_name varchar(100) not null,
	sure_name varchar(100) not null,
	sucoin int,
	coin int,
	password varchar(256) not null,
	age int,
	email varchar(256) not null,
	actived varchar(6) not null
);