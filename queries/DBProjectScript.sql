-- create a database --
CREATE DATABASE LibraryDatabase; --

-- use specific database --
USE LibraryDatabase;

-- create new tables with optional constraints --
CREATE TABLE Cardholder (
cardNumber INTEGER NOT NULL,
lastName VARCHAR(40) NOT NULL,
firstName VARCHAR(40) NOT NULL,
address VARCHAR(100),
phoneNumber DOUBLE NOT NULL,
email VARCHAR(40) NOT NULL,
PRIMARY KEY (cardNumber)
);

CREATE TABLE Publisher (
publisherID INTEGER NOT NULL,
publisherName VARCHAR(40) NOT NULL,
PRIMARY KEY(publisherID)
);

CREATE TABLE Location (
floorNumber INTEGER NOT NULL,
studyrooms INTEGER NOT NULL,
computers INTEGER NOT NULL,
availableRooms INTEGER NOT NULL,
availableComputers INTEGER NOT NULL,
PRIMARY KEY(floorNumber)
);

CREATE TABLE Employee (
employeeID INTEGER NOT NULL,
lastName VARCHAR(40) NOT NULL,
firstName VARCHAR(40) NOT NULL,
extension INTEGER,
floorNumber INTEGER NOT NULL,
salary INTEGER NOT NULL,
PRIMARY KEY (employeeID),
FOREIGN KEY (floorNumber) REFERENCES Location(floorNumber)
);

-- figure out way to update available copies using generated columns maybe? --
CREATE TABLE Book (
bookID INTEGER NOT NULL AUTO_INCREMENT,
publisherID INTEGER NOT NULL,
floorLocation INTEGER NOT NULL,  
ISBN VARCHAR(20) NOT NULL,
title VARCHAR(250) NOT NULL,
author VARCHAR(50) NOT NULL,
copies INTEGER NOT NULL,
availability INTEGER,
publicationYear INTEGER NOT NULL,
bookInfo MEDIUMTEXT NOT NULL,
CHECK (copies > 0),
PRIMARY KEY (bookID),
FOREIGN KEY (floorLocation) REFERENCES Location(floorNumber),
FOREIGN KEY (publisherID) REFERENCES Publisher(publisherID)
);

CREATE TABLE Borrows (
borrowerID INTEGER NOT NULL,
bookID INTEGER NOT NULL,
borrowDate DATE NOT NULL,
dueDate DATE NOT NULL,
returnDate DATE NULL,
CHECK (dueDate > borrowDate),
FOREIGN KEY (borrowerID) REFERENCES Cardholder(cardNumber) ON UPDATE CASCADE,
FOREIGN KEY (bookID) REFERENCES Book(bookID) ON UPDATE CASCADE
);

CREATE TABLE Reserves (
reservationID INTEGER NOT NULL,
floorLocation INTEGER NOT NULL,
computer BOOLEAN,
studyroom BOOLEAN,
PRIMARY KEY (reservationID),
FOREIGN KEY (floorLocation) REFERENCES Location(floorNumber)
);

