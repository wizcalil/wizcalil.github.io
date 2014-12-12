-- MySQL dump 10.11
--
-- to install this database, from a terminal, type:
-- mysql -u USERNAME -p -h SERVERNAME cheapodb < cheapodb.sql
--
-- Host: localhost    Database: cheapodb
-- ------------------------------------------------------
-- Server version   5.0.45-log

/*DROP DATABASE IF EXISTS cheapodb;
CREATE DATABASE cheapodb;
USE cheapodb;*/


DROP TABLE IF EXISTS User;
CREATE TABLE User (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
first_name VARCHAR(32) NOT NULL, 
last_name VARCHAR(32) NOT NULL, 
password VARCHAR(32) NOT NULL, 
username VARCHAR(32) NOT NULL
);

DROP TABLE IF EXISTS Message;
CREATE TABLE Message (
id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY, 
body TEXT NOT NULL,
subject VARCHAR(32) DEFAULT NULL, 
user_id INT NOT NULL,
recipient_ids VARCHAR(255) NOT NULL 
);

DROP TABLE IF EXISTS Message_read;
CREATE TABLE Message_read (
id INT  NOT NULL AUTO_INCREMENT PRIMARY KEY, 
message_id INT UNSIGNED NOT NULL,
reader_id INT UNSIGNED NOT NULL, 
date VARCHAR(25) DEFAULT NULL 
);

-- debug output to just show the contents of all tables
/*

SHOW TABLES;
SELECT * FROM users;
SELECT * FROM messages;
SELECT * FROM message_read;

*/
