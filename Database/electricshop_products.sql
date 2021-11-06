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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `sale_price` float DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  FULLTEXT KEY `Search` (`name`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'a02-01','Dynamic AMOLED 2X, Chính 6.7\" & Phụ 1.9\", Full HD+',1,'Samsung Galaxy Z Flip3 5G 128GB','samsung-galaxy-z-flip-3-cream-1-600x600.jpg',500,0,300,5),(2,'a01-01','IPS LCD6.1\"Liquid Retina',1,'iPhone 11 64GB','iphone-xi-do-600x600.jpg',1200,0,1199,4),(3,'a02-02','Super AMOLED Plus6.7\"Full HD+',1,'Samsung Galaxy Note 20','samsung-galaxy-note-20-062220-122200-fix-600x600.jpg',500,0,499,5),(4,'a02-03','Dynamic AMOLED 2XChính 7.6\" & Phụ 6.2\"Full HD+',1,'Samsung Galaxy Z Fold3 5G 256GB','samsung-galaxy-z-fold-3-silver-1-600x600.jpg',300,0,200,5),(5,'a02-04','IPS LCD6.1\"Liquid Retina',1,'iPhone 11 128GB','iphone-11-xanhla-600x600.jpg',490,1,450,5),(6,'a02-05','Super AMOLED6.5\"Full HD+',1,'Samsung Galaxy A52 5G','samsung-galaxy-a52-5g-thumb-black-600x600-600x600.jpg',510,1,450,5),(7,'a02-06','Super AMOLED Plus6.7\"Full HD+',1,'Điện thoại Samsung Galaxy M51','samsung-galaxy-m51-den-new-600x600-600x600.jpg',600,2,450,5),(8,'a02-07','Super AMOLED6.4\"Full HD+',1,'Điện thoại Samsung Galaxy A32','samsung-galaxy-a32-4g-thumb-tim-600x600-600x600.jpg',530,3,400,5),(9,'a01-02','OLED5.4\"Super Retina XDR',1,'Điện thoại iPhone 12 mini 64GB','iphone-12-mini-do-4-600x600.jpg',543,1,500,4),(10,'a01-03','OLED6.7\"Super Retina XDR',1,'Điện thoại iPhone 12 Pro Max 128GB','iphone-12-pro-max-xanh-duong-new-600x600-600x600.jpg',430,1,400,4),(11,'a01-04','IPS LCD4.7\"Retina HD',1,'Điện thoại iPhone SE 64GB (2020) (Hộp mới)','iphone-se-2020-trang-600x600-600x600.jpg',422,1,390,4),(12,'a01-05','IPS LCD6.1\"Liquid Retina',1,'Điện thoại iPhone XR 64GB','iphone-xr-hopmoi-den-600x600-2-600x600.jpg',500,1,490,4),(13,'a01-06','OLED6.1\"Super Retina XDR',1,'Điện thoại iPhone 12 Pro 128GB','iphone-12-pro-bac-new-600x600-600x600.jpg',600,1,550,4),(14,'a03-01','IPS LCD6.53\"Full HD+',1,'Điện thoại Xiaomi Redmi 9T (6GB/128GB)','xiaomi-redmi-9t-6gb-110621-080650-600x600.jpg',700,1,690,6),(15,'a03-02','IPS LCD6.5\"Full HD+',1,'Điện thoại Xiaomi Redmi Note 10 5G 8GB','xiaomi-redmi-note-10-5g-xanh-bong-dem-1-600x600.jpg',550,1,540,6),(16,'a03-03','AMOLED6.81\"Quad HD+ (2K+)',1,'Điện thoại Xiaomi Mi 11 5G','xiaomi-mi-11-xanhduong-600x600-600x600.jpg',550,1,540,6),(17,'a03-04','AMOLED6.55\"Full HD+',1,'Điện thoại Xiaomi Mi 11 Lite','xiaomi-mi-11-lite-4g-blue-600x600.jpg',800,1,720,6),(18,'a03-05','IPS LCD6.67\"Full HD+',1,'Xiaomi POCO X3 NFC','xiaomi-poco-x3-nfc-xam-600x600.jpg',750,1,720,6);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
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
