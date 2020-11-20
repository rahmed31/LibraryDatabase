# Overview
This repository contains code to build and query from a database reminiscient of those being used by modern-day library systems. Essential entities include: Member, Book, Publisher, Location, and Employee. Each of these entities possess a set number of attributes pertaining to their essential metadata. The Member and Book entities are interconnected by a 1-M "borrows" associative entity relationship, the Book and Location entities are interconnected through a M-1 relationship called "located," the Book and Publisher are interconnected through a 1-1 relationship called "Published By," and the Employee and Location entities are interconnected through an optional participation M-N relationship called "Supervises." The metadata collected by each entity can either be atomic, composite, single-valued, multi-valued, stored, or derived, as demonstrated by the following entity relationship diagram. It is important to have a database like this in order to monitor the circulation and quantity of books amongst library cardholders, as well as to streamline everyday processes that would occur at a typical library such as: finding the location of a certain book, finding the publisher of a certain book, finding who has books that are overdue, or finding the employee that attends to a certain section of the library. 

*Please note that this project will be continually updated to include:*

- Additional entities and attributes that would most likely be incorporated within a real library database.
- A main driver with both simple and complex querying examples from the database.

# Entity Relationship Diagram
In order to build the database using MySQL Workbench, I have designed its structure using the following entity-relationship (ER) diagram. This allows us to model the behavior between each entity: 

![ER Diagram](lib/ER-Diagram.png?raw=true "ER Diagram")

# Creating the Relational Schema
The following relational model is a product of the ER diagram shown above. A relational schemas is built using the ideas from its corresponding ER diagram, while also using a structure and language consistent with first-order predicate logic. This allows our data to be represented in terms of tuples and subsequently grouped into relations:

![Relational Schema](lib/Relational-Schema.png?raw=true "Relational Schema")

Using this relational schema, we can now build the actual library database using MySQL. The script to do this is included in the repository under lib > DBProjectScript.sql

# Getting Started

After building the database, create a file called dbInfo.java to store your database credentials. This will be included in the .gitignore file and will not be commited. Within the file, put the following information (replacing where necessary): 

    public class dbInfo {
        private static String username = "username";
        private static String password = "password";

        public static String getUsername() {return username;}
        
        public static String getPassword() {return password;}
    }

# Adding Random, but Meaningful, Data

Random, yet meaningful, data can be added to the database (after connecting to it via MySQL Connector 8.0.22) using the following files:

1. UniquePublishers.java: This file uses a hashmap to find unique publishers present in the first 500 entries of the "book_metadata.csv" file.
2. InsertPublishers.java: This file inserts the unique publishers found in UniquePublishers.java into the database with their ID number. 
3. InsertBooks.java: This file is used to insert book metadata into the 'Book' table using the CSV file "book_metadata.csv" provided on Kaggle. An N amount of entries can be created; however, in this instance, I use a limit of 500 book entries. A hash function is used to assign publisher IDs to each book entry.
4. InsertCardholders.java: This file is used to randomly generate the first and last names of cardholders using first-names.txt and last-names.txt. Random phone numbers, emails, library card numbers, and addresses are generated as well from each of their associated TXT files. A limit of 50 library members are created.
5. InsertEmployees.java: This file is used to randomly generate the first and last names of 30 library employees, as well as their employee IDs, salary, and desk location.
6. InsertLocations.java: *To be created*
7. InsertBorrowers.java: *To be created*
