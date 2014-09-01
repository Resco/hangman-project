
create table players (
  player_id varchar(255) not null,
  mail varchar(255) not null,
  salt varchar(255) not null,
  cript varchar(255) not null,
  num_games integer not null,
  average real not null,
  primary key (player_id)
);

update schema_info set version = 1;