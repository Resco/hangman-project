create table moves (
  num_game varchar(255) not null,
  player varchar(255) not null,
  num_move integer not null,
  move varchar(255) not null,
  code varchar(255) not null,
  primary key (num_game, num_move)
);

update schema_info set version = 4;