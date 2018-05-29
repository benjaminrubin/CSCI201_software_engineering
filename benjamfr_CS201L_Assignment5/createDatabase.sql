
DROP DATABASE IF EXISTS RestaurantDB;
CREATE DATABASE RestaurantDB;


USE RestaurantDB;


-- The first line in this table represents the start time, end time and total time of the full execution
CREATE TABLE orderTimes (
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    startTime VARCHAR(25) NOT NULL,
    endTime VARCHAR(25) NOT NULL,
    totalTime VARCHAR(25)
);


CREATE TABLE orderQuantity (
	orderID INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    frenchFries INT(10) NULL,
    funnelCake INT(10) NULL,
    cornDog INT(10) NULL,
    hamburger INT(10) NULL,
    veggieburger INT(10) NULL,
    chocolateShake INT(10) NULL,
    strawberryShake INT(10) NULL,
    vanillaShake INT(10) NULL,
    small INT(10) NULL,
    medium INT(10) NULL,
    large INT(10) NULL,
    coffee INT(10) NULL
);


-- https://stackoverflow.com/questions/6046514/how-can-i-share-the-same-primary-key-across-two-tables