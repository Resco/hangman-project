create table games (
  game_id varchar(255) not null,
  player varchar(255) not null,
  started date,
  finisched date,
  points varchar(255) not null,
  code varchar(255) not null,
  primary key (game_id, player)
);

update schema_info set version = 3;