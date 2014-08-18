
create table players (
  nickname varchar(255) not null,
  mail varchar(255) not null,
  salt varchar(255) not null,
  cript varchar(255) not null,
  primary key (nickname)
);

update schema_info set version = 1;