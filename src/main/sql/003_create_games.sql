create table games (
  num varchar(255) not null,
  player varchar(255) not null,
  started date,
  finisched date,
  points varchar(255) not null,
  code varchar(255) not null,
  primary key (num, player)
);

update schema_info set version = 3;