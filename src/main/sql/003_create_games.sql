create table games (
  num integer not null,
  player varchar(255) not null,
  started date,
  finisched date,
  points integer not null,
  code varchar(255) not null,
  primary key (num, player)
);

update schema_info set version = 3;