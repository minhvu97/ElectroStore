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
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
INSERT INTO `persistent_logins` VALUES ('hanh.minh161297@gmail.com','/jCplPDQTG8ANjnbeTIdyg==','SmBDic4PKbXSK43Mhz2gFw==','2021-09-04 04:14:20'),('hanh.minh161297@gmail.com','2xc593tTJECCLoEOaO6Kiw==','W+zWgDK4xpfmyaxbIhGs3g==','2021-09-04 08:32:41'),('anh.minh161297@gmail.com','arJwOy/0ypCuNzj6VtauAw==','E3ErYW8W4K9kvXNyPF0kAg==','2021-09-04 08:44:56'),('thanh.minh161297@gmail.com','EF1YVdOxnIZtGmCWcNenwA==','rnbaJmCpr80dBXxoOXViJg==','2021-09-07 14:16:31'),('h.minh161297@gmail.com','EMezUSHtOBK4yXClFiDKNw==','zKbQn4DjCh1pRogJHXzUqA==','2021-09-04 08:51:00'),('admin','EzPyq3FN5au57UfOX0HwJw==','26yUWKqx8KS+eYgATeqIzA==','2021-08-14 04:38:26'),('admin','ie9vIqaTgdkmUp2NnoMzYw==','wKgVDkkoeWjHCyhNzZOlMw==','2021-08-14 04:36:53'),('thanh.minh161297@gmail.com','k8bZfIQWWZTVK9AOcXrcRQ==','4xCVwFDy2jZHG3WLQbEhEA==','2021-09-06 16:20:55'),('minh161297@gmail.com','nUla2xasBaxN5LEBGIhHPA==','oXlwGB4ftLAxvqmsByMK+A==','2021-09-04 08:53:29'),('hanh.minh161297@gmail.com','Qx1WonnzCk5YWdoOBC+ljA==','h+zkCsIuwrHgXZGsEEbpwA==','2021-09-04 07:00:17'),('thanh.minh161297@gmail.com','RRxl6IvLKwpxZJDU9UaAMA==','9cBXzeKg0GWyGsGS8hBOiQ==','2021-09-06 15:03:28'),('nh.minh161297@gmail.com','tj84pWN0GrK16AeIKmJDzw==','UxPdVZUQjmnlfO3kxWF6cg==','2021-09-04 08:49:01'),('thanh.minh161297@gmail.com','uEVrMGDXk3Cy1ytmR3B65w==','rwf+PuNsJYjyiHiIyZ7zPA==','2021-09-06 13:38:23');
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-10 16:28:17
