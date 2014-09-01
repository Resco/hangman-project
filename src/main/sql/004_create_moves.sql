create table moves (
  game_id varchar(255) not null,
  player_id varchar(255) not null,
  move_id varchar(255) not null,
  move varchar(255) not null,
  result varchar(255) not null,
  primary key (game_id, move_id)
);

update schema_info set version = 4;