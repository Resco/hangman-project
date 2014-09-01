create table games (
  game_id varchar(255) not null,
  player_id varchar(255) not null,
  started timestamp not null,
  finished timestamp,
  score integer not null,
  code varchar(255) not null,
  primary key (game_id)
);

update schema_info set version = 3;