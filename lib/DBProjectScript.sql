-- create a database --
CREATE DATABASE LibraryDatabase; --

-- use specific database --
USE LibraryDatabase;

-- create new tables with optional constraints --
CREATE TABLE Cardholder (
cardNumber INTEGER NOT NULL AUTO_INCREMENT,
lastName VARCHAR(40) NOT NULL,
firstName VARCHAR(40) NOT NULL,
address VARCHAR(100),
phoneNumber DOUBLE NOT NULL,
email VARCHAR(40) NOT NULL,
PRIMARY KEY (cardNumber)
);

CREATE TABLE Publisher (
publisherID INTEGER NOT NULL AUTO_INCREMENT,
publisherName VARCHAR(40) NOT NULL,
PRIMARY KEY(publisherID)
);

CREATE TABLE Employee (
employeeID INTEGER NOT NULL AUTO_INCREMENT,
lastName VARCHAR(40) NOT NULL,
firstName VARCHAR(40) NOT NULL,
deskNumber INTEGER NOT NULL,
salary INTEGER NOT NULL,
PRIMARY KEY (employeeID)
);

CREATE TABLE Location (
bookIndex VARCHAR(20) NOT NULL,
supervisorID INTEGER NOT NULL,
floor INTEGER NOT NULL,
section VARCHAR(40),
aisle VARCHAR(10),
shelf INTEGER,
PRIMARY KEY(bookIndex),
FOREIGN KEY (supervisorID) REFERENCES Employee(employeeID) ON UPDATE CASCADE
);

-- figure out way to update available copies using generated columns maybe? --
CREATE TABLE Book (
bookID INTEGER NOT NULL AUTO_INCREMENT,
publisherID INTEGER,
bookIndex VARCHAR(20),  
ISBN VARCHAR(20) NOT NULL,
title VARCHAR(250) NOT NULL,
author VARCHAR(50) NOT NULL,
copies INTEGER NOT NULL,
availability INTEGER,
publicationYear INTEGER NOT NULL,
bookInfo MEDIUMTEXT NOT NULL,
CHECK (copies > 0),
PRIMARY KEY (bookID),
FOREIGN KEY (publisherID) REFERENCES Publisher(publisherID),
FOREIGN KEY (bookIndex) REFERENCES Location(bookIndex) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Borrows (
borrowerID INTEGER NOT NULL,
bookID INTEGER NOT NULL,
-- bookTitle VARCHAR(100) NOT NULL, can delete this soon
borrowDate DATE NOT NULL,
dueDate DATE NOT NULL,
returnDate DATE NULL,
CHECK (dueDate > borrowDate),
FOREIGN KEY (borrowerID) REFERENCES Cardholder(cardNumber) ON DELETE CASCADE,
FOREIGN KEY (bookID) REFERENCES Book(bookID)
);


