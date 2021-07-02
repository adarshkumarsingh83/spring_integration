DROP TABLE IF EXISTS `Employee`;
CREATE TABLE `Employee` (
  `employeeId` int(6) NOT NULL,
  `employeeFirstName` varchar (20) NOT NULL,
  `employeeLastName` varchar (20) NOT NULL,
  `employeeEmail` varchar (20) NOT NULL,
  `employeePhone` varchar (10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO Employee (employeeId,employeeFirstName,employeeLastName,employeeEmail,employeePhone)
 VALUES  (1,'Adarsh','kumar','adarsh@kumar','99999999')
       , (2,'Amit','kumar','amit@kumar','888888888')
       , (3,'Radha','Singh','radha@singh','7777777');