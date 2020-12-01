USE LibraryDatabase;

-- find people, and their email addresses, who have checked out books that have not yet returned them -- 
SELECT lastName, firstName, email, COUNT(borrowerID)
FROM Cardholder
JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID
WHERE returnDate IS NULL GROUP BY cardNumber
ORDER BY lastName;

-- find people who returned a book late
SELECT lastName, firstName, email, COUNT(borrowerID)
FROM Cardholder
JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID
WHERE MONTH(returnDate) - MONTH(dueDate) = 0 AND DAY(returnDate) - DAY(dueDate) > 0 GROUP BY cardNumber
ORDER BY lastName;

-- find the count of employees working on each floor -- 
SELECT floorNumber, COUNT(employeeID)
FROM Employee
GROUP BY floorNumber;

-- find the number of available studyrooms on the 3rd floor --
SELECT (SELECT studyrooms FROM Location WHERE floorNumber = 1) - (SELECT COUNT(studyroom) FROM Reserves WHERE floorLocation = 1 AND studyroom = 1) AS Difference;

-- find employees who make more than average salary --
SELECT firstName, lastName FROM Employee WHERE salary > (SELECT AVG(salary) FROM Employee);

-- find the employees who can assist with finding "Goodbye to the Buttermilk Sky" -- 
SELECT firstName, lastName, extension FROM Employee WHERE floorNumber = (SELECT floorLocation FROM Book WHERE title = 'Goodbye to the Buttermilk Sky');

-- find the names of the top 8 checked out books and their information -- 

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