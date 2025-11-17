CREATE DATABASE IF NOT EXISTS my_blackjack;

USE my_blackjack;

CREATE TABLE IF NOT EXISTS players (
    player_id INT PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(255) NOT NULL,
    total_games INT DEFAULT 0,
    games_won INT DEFAULT 0
);