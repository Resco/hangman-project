create table moves (
  num_game integer not null,
  player varchar(255) not null,
  num_move integer not null,
  move varchar(255) not null,
  primary key (num_game, player)
);

update schema_info set version = 4;