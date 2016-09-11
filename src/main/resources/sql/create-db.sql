CREATE TABLE player (
  player_id int NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  avatar varchar(45) DEFAULT '',
  banned tinyint(1) DEFAULT 0,
  PRIMARY KEY (player_id)
);

CREATE TABLE scores (
  score_id int NOT NULL AUTO_INCREMENT,
  player_id_fk int NOT NULL,
  score int NOT NULL,
  score_timestamp datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (score_id),
  FOREIGN KEY (player_id_fk) REFERENCES player(player_id)
);
