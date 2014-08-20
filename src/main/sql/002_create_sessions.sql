
create table sessions (
  player_id varchar(255) not null,
  session_id varchar(255) not null,
  primary key (session_id)
);

update schema_info set version = 2;