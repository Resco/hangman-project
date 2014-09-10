
create table sessions (
  player_id varchar(255) not null,
  session_id varchar(255) not null,
  primary key (session_id),
  FOREIGN KEY (player_id) REFERENCES players (player_id) ON UPDATE CASCADE ON DELETE CASCADE

);

update schema_info set version = 2;