# Overview
This repository contains code to build and administer a database reminiscient of those being used by modern-day library systems. Essential entities include: Member, Book, Publisher, Location, and Employee. Each of these entities possess a set number of attributes pertaining to their essential metadata. The Member and Book entities are interconnected by a 1-M "borrows" associative entity relationship, the Book and Location entities are interconnected through a M-1 relationship called "located," the Book and Publisher are interconnected through a 1-1 relationship called "Published By," and the Employee and Location entities are interconnected through an optional participation M-N relationship called "Supervises." The metadata collected by each entity can either be atomic, composite, single-valued, multi-valued, stored, or derived, as demonstrated by the following entity relationship diagram. It is important to have a database like this in order to monitor the circulation and quantity of books amongst library cardholders, as well as to streamline everyday processes that would occur at a typical library such as: finding the location of a certain book, finding the publisher of a certain book, finding who has books that are overdue, or finding the employee that attends to a certain section of the library. 

*Please note that this project will be continually updated to include:*

- Functionality to support querying from the database, as well as writing records into the database.
- Additional entities and attributes within the database that would be used by a real-life library.

# Entity Relationship Diagram
In order to build the database using MySQL Workbench, I have designed its structure using the following entity-relationship (ER) diagram. This allows us to model the behavior between each entity: 

![ER Diagram](lib/ER-Diagram.png?raw=true "ER Diagram")

# Creating the Relational Schema
The following relational model is a product of the ER diagram shown above. A relational schemas is built using the ideas from its corresponding ER diagram, while also using a structure and language consistent with first-order predicate logic. This allows our data to be represented in terms of tuples and subsequently grouped into relations:

![Relational Schema](lib/Relational-Schema.png?raw=true "Relational Schema")




