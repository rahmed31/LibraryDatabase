# Overview
This repository contains code to build and query from a MySQL database reminiscient of those being used by modern-day library systems. Essential entities include: Member, Book, Publisher, Location, and Employee. Each of these entities possess a set number of attributes pertaining to their essential metadata. The Member and Book entities are interconnected by a 1-M "borrows" associative entity relationship, the Book and Location entities are interconnected through a M-1 relationship called "located," the Book and Publisher are interconnected through a 1-1 relationship called "Published By," the Employee and Location entities are interconnected through an optional participation M-N relationship called "Supervises," and finally, the Member and Location entities are interconnected through a 1-1 associative entity relationship called "Reserves." The metadata collected by each entity can either be atomic, composite, single-valued, multi-valued, stored, or derived. However, I avoid using multi-valued attributes since that would require the database be normalized to increase its efficacy. Lastly, it is important to have a database like this in order to monitor the circulation and quantity of books amongst library cardholders, as well as to streamline everyday processes that would occur at a typical library such as: finding the location of a certain book, finding the publisher of a certain book, finding who has books that are overdue, or finding the employee that attends to a certain section of the library. 

*Please note that this project will be continually updated to include:*

- Additional entities and attributes that would most likely be incorporated within a real library database.
- A main driver with both simple and complex examples of querying from the database.

# Entity Relationship Diagram
In order to build the database using MySQL Workbench, I have designed its structure using the following entity-relationship (ER) diagram. This allows us to model the expected behavior between each entity: 

![ER Diagram](lib/ER-Diagram.png?raw=true "ER Diagram")

# Creating the Relational Schema
The following relational model is a product of the ER diagram shown above. A relational schemas is built using the ideas from its corresponding ER diagram, while also using a structure and language consistent with first-order predicate logic. This allows our data to be represented in terms of tuples, and subsequently grouped into relations:

![Relational Schema](lib/Relational-Schema.png?raw=true "Relational Schema")

# Getting Started

Using the relational schema above, we can now build the actual library database using MySQL Workbench. The script to accomplish this is included in the respository under queries > DBProjectScript.sql. Refer to [MySQL documentation](https://dev.mysql.com/doc/) to get started with downloading and configuring MySQL Workbench if necessary.

After building the database, create a file called dbInfo.java to store your database credentials. This will be included in the .gitignore file and will not be commited. Within the file, put the following information (replacing where necessary): 

    public class dbInfo {
        private static String username = "username";
        private static String password = "password";

        public static String getUsername() {return username;}
        
        public static String getPassword() {return password;}
    }

# Adding Random, but Real and Meaningful, Data

Random, yet meaningful, data can be added to the database (after connecting to it via MySQL Connector 8.0.22) using the following files in sequential order:

1. UniquePublishers.java: This file uses a hashmap to find unique publishers present in the first 500 entries of the "book_metadata.csv" file found on Kaggle.
2. InsertPublishers.java: This file inserts the unique publishers found in UniquePublishers.java into the database with their ID number.
3. InsertLocations.java: This file is used to randomly generate location information (up to four separate floors with computers and studyrooms) for the library. 
4. InsertBooks.java: This file is used to insert book metadata into the 'Book' table using the CSV file "book_metadata.csv". An N amount of entries can be created; however, for the sake of time and space, I use a limit of 500 book entries. A hash function is used to assign publisher IDs to each book entry.
5. InsertCardholders.java: This file is used to randomly generate the first and last names of cardholders using first-names.txt and last-names.txt. Random phone numbers, emails, library card numbers, and addresses are generated as well from each of their associated TXT files. A total of 50 library members are created.
6. InsertEmployees.java: This file is used to randomly generate the first and last names of 30 library employees, including their employee IDs, salary, and desk location.
7. InsertBorrows.java: This file is used to randomly generate book checkout information for 25 different library members, with up to 75 entries in total.
8. InsertReserves.java: This file is used to randomly generate computer or studyroom reservation information for 13 different library members.

# Example Program

A few example queries have been written in src/Main.java and can be executed according to the database architecture. Feel free to construct and execute your own queries according to the logic provided within the ER and Relational diagrams. 
