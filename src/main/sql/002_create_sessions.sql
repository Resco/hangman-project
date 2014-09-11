
create table sessions (
  player_id varchar(255) not null,
  session_id varchar(255) not null,
  started timestamp not null,
  primary key (session_id, started),
  FOREIGN KEY (player_id) REFERENCES players (player_id) ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE FUNCTION delete_old_rows()  RETURNS trigger 
LANGUAGE plpgsql 
AS $$ 
BEGIN DELETE FROM sessions WHERE started < NOW() - INTERVAL '2 days'; 
RETURN NEW; 
END; 
$$;

CREATE TRIGGER old_rows_gc AFTER INSERT ON sessions EXECUTE PROCEDURE delete_old_rows();



update schema_info set version = 2;