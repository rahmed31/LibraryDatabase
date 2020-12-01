USE LibraryDatabase;

/* 
NOTE: 
The example statements above each query have been generalized to avoid redundancy. 
However, the constructed queries are tailored such that the desirable output is yielded.
*/

-- Query 1 --
/* Select the publishers, and count of books that each publisher has in the database, that 
have at least 20 books in the library's inventory */
SELECT publisherName, COUNT(bookID) 
FROM Publisher 
JOIN Book ON Book.publisherID = Publisher.publisherID
GROUP BY publisherName
HAVING COUNT(bookID) >= 20
ORDER BY COUNT(bookID) ASC;
          
-- Query 2 --
-- Find the names and emails of people who have checked out books and not yet returned them -- 
SELECT lastName, firstName, email, COUNT(borrowerID)
FROM Cardholder
JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID
WHERE returnDate IS NULL 
GROUP BY cardNumber
ORDER BY lastName;

-- Query 3 --
-- Find the names and emails of people who returned a book late --
SELECT lastName, firstName, email, COUNT(borrowerID)
FROM Cardholder
JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID
WHERE MONTH(returnDate) - MONTH(dueDate) = 0 AND DAY(returnDate) - DAY(dueDate) > 0 AND returnDate IS NOT NULL 
GROUP BY cardNumber
ORDER BY lastName;

-- Query 4 --
-- Find the amount of employees working on each floor -- 
SELECT floorNumber, COUNT(employeeID)
FROM Employee
GROUP BY floorNumber;

-- Query 5 --
-- Find the number of available studyrooms on the first floor --
SELECT 
(SELECT studyrooms FROM Location WHERE floorNumber = 1) - 
(SELECT COUNT(studyroom) FROM Reserves WHERE floorLocation = 1 AND studyroom = 1) 
AS Difference;

-- Query 6 --
-- Find the names of employees, and their salaries, that make more than the average employee salary. --
SELECT firstName, lastName, salary FROM Employee WHERE salary > (SELECT AVG(salary) FROM Employee);

-- Query 7 --
-- Find the names and extensions of employees that can help with finding the book 'Goodbye to the Buttermilk Sky' -- 
SELECT firstName, lastName, extension 
FROM Employee 
WHERE floorNumber = (SELECT floorLocation FROM Book WHERE title = 'Goodbye to the Buttermilk Sky');

-- Query 8 --
-- Find the names of the top 8 most frequently checked out books of the month and their information -- 
SELECT Book.title, bookInfo
FROM Book
INNER JOIN 
(SELECT title
FROM Borrows
LEFT JOIN Book ON Book.bookID = Borrows.bookID
GROUP BY title
ORDER BY COUNT(title) DESC
LIMIT 8) AS result
ON result.title = Book.title
ORDER BY Book.title ASC;