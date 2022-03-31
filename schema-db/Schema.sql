DROP DATABASE IF EXISTS testing_db;
CREATE DATABASE testing_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP USER IF EXISTS 'testing_user';
CREATE USER 'testing_user'@'localhost' IDENTIFIED BY 'testing_user';
GRANT ALL PRIVILEGES ON testing_db.* TO 'testing_user'@'localhost';
