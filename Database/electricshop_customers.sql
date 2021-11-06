-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: electricshop
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `photo_url` varchar(45) DEFAULT NULL,
  `rank` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `last_login` varchar(45) DEFAULT NULL,
  `email_verified` tinyint DEFAULT '0',
  `verification_code` varchar(256) DEFAULT NULL,
  `auth_provider` varchar(45) DEFAULT NULL,
  `enabled` tinyint DEFAULT '1',
  `cart_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjxjx98awyiiuruvg3ar8stm7y` (`cart_id`),
  CONSTRAINT `FKjxjx98awyiiuruvg3ar8stm7y` FOREIGN KEY (`cart_id`) REFERENCES `cartinfo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (13,'nh161297@gmail.com','$2a$12$UG1aoIIQQMD9xeQmL9BY9.UMf/ZkJwff0gL2loORMs4I832xIiMuS','asds5','hatakeabc','0818700094',NULL,'profile1.png',NULL,'2021-07-24 11:25:04','2021-09-04 15:53:49.598',NULL,NULL,'BASIC',1,NULL),(14,'omoach@gmail.com',NULL,'Minh123','Vu','08187000945',NULL,NULL,NULL,'2021-07-24 12:55:41','2021-09-05 19:22:27.857',0,NULL,'GOOGLE',1,NULL),(16,'1@gmail.com','$2a$10$TWHcGIuVI5r.SU2K2kPP8.RqFATZURIWFdDpNRwuYEeMDhASLOXte','laoCai','uchiha','123456789129',NULL,NULL,NULL,'2021-08-20 20:54:11','2021-08-26 23:25:30.422',NULL,NULL,'BASIC',1,NULL),(18,'thanh.minh161297@gmail.com','$2a$10$SSEIQkfGPjuagPEA9oHecePBvTQN6IMMiYnkosfS5gXFL3cNb.Fze','kakashi','hatake','081870009456',NULL,'profilee.jpg',NULL,'2021-09-05 16:02:46','2021-09-05 16:05:40.218',NULL,NULL,'BASIC',1,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-10 16:28:16
