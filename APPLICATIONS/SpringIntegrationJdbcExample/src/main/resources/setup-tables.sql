DROP TABLE IF EXISTS `spring`.`users`;
CREATE TABLE  `spring`.`users` (
  `USERNAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spring`.`dummy`;
CREATE TABLE  `spring`.`dummy` (
  `DUMMY_VALUE` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spring`.`person`;
CREATE TABLE  `spring`.`person` (
  `personId` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  PRIMARY KEY (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO USERS(USERNAME, PASSWORD, EMAIL)
VALUES ('ADARSH', 'secret1', 'adarsh@kumar.com')
       ,('AMIT', 'secret2', 'amit@kumar.com')
       ,('RADHA', 'secret3', 'radha@singh.com');