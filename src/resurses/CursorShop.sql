CREATE TABLE `messages` (
  `idmessages` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `author` varchar(45) NOT NULL,
  `message` varchar(1000) NOT NULL,
  `keyUser` varchar(45) NOT NULL,
  PRIMARY KEY (`idmessages`),
  UNIQUE KEY `idmessages_UNIQUE` (`idmessages`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `nickname` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `secondname` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  `statususer` tinyint(4) NOT NULL DEFAULT '1',
  `statusorder` tinyint(4) NOT NULL DEFAULT '0',
  `money` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
  `idproducts` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `model` varchar(45) NOT NULL,
  `buyer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idproducts`),
  UNIQUE KEY `idproducts_UNIQUE` (`idproducts`)
) ENGINE=InnoDB AUTO_INCREMENT=475 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000001, HP, 100, 15-rb012ur, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000002, LENOVO, 5000, IdeaPad-S145, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000003, ACER,	18000, Aspire-A315-42-R94P, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000042, MSI, 60000, ProVAG-12, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000045, MSI, 30000, GF63, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000461, Prestigio, 12500,	Smartbook-141C2, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000463, ASUS, 14999, VivoBook-Max, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000464, HP, 17999, 15-rb082ur, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000465, Lenovo, 16000, Ideapad-S145, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000466, ASUS,	70899, TUF-Gaming,	null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000467, Acer,	71000, Predator-Helios, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000468, Apple, 80000,	MacBook-Air, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000469, Apple, 84000,	MacBook-Pro-Retina,	null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000470, Alienware, 101998, R55, null);
INSERT INTO `cursor`.`products`(`idproducts`,`name`,`price`,`model`,`buyer`) VALUES (00000000471, ASUS, 487000,	StudioBook, null);
