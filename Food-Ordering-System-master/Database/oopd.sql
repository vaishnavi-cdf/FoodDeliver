CREATE DATABASE  IF NOT EXISTS `oopd` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oopd`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: oopd
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `area_coordinates`
--

DROP TABLE IF EXISTS `area_coordinates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area_coordinates` (
  `city` varchar(20) NOT NULL,
  `area` varchar(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`city`,`area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_coordinates`
--

LOCK TABLES `area_coordinates` WRITE;
/*!40000 ALTER TABLE `area_coordinates` DISABLE KEYS */;
INSERT INTO `area_coordinates` VALUES ('Mumbai','Parel',18.9977,72.8376),('New_Delhi','Ashok_Vihar',28.691,77.1765),('New_Delhi','Bahapur',28.5576,77.2649),('New_Delhi','Chandni_Chowk',28.6505,77.2303),('New_Delhi','Connaught_Place',28.6304,77.2177),('New_Delhi','Gole_Market',28.6339,77.2053),('New_Delhi','Pragati_Vihar',28.5869,77.2338),('New_Delhi','Safdarjung',28.5647,77.1949);
/*!40000 ALTER TABLE `area_coordinates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `customer_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `food_quantity` int DEFAULT NULL,
  KEY `customer_id` (`customer_id`),
  KEY `restaurant_id` (`restaurant_id`),
  KEY `food_id` (`food_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `cart_ibfk_3` FOREIGN KEY (`food_id`) REFERENCES `menu` (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(45) NOT NULL,
  `customer_email` varchar(45) NOT NULL,
  `customer_password` varchar(45) NOT NULL,
  `customer_address` varchar(100) NOT NULL,
  `customer_city` varchar(20) NOT NULL,
  `customer_area` varchar(20) NOT NULL,
  `promo_code_1` int NOT NULL DEFAULT '1',
  `promo_code_2` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_email` (`customer_email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Shrey_Rastogi','shrey@outlook.com','Shrey.123','Netaji_Subhash Marg,_Chandni Chowk,_New Delhi,_Delhi_110006','New_Delhi','Chandni_Chowk',1,1),(2,'Jeet_Tewatia','jeet@gmail.com','Jeet.123','Lotus_Temple_Road,_Bahapur_Shambhu_Dayal_Bagh,_Kalkaji,_New_Delhi,_Delhi_110019','New_Delhi','Bahapur',1,1),(3,'Mitali_Gupta','mitali@outlook.com','Mitali.123','Mandir_Marg,_Gole Market,_New Delhi,_Delhi_110001','New_Delhi','Gole_Market',1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `restaurant_id` int DEFAULT NULL,
  `food_id` int NOT NULL AUTO_INCREMENT,
  `food_name` varchar(25) NOT NULL,
  `food_price` int NOT NULL,
  PRIMARY KEY (`food_id`),
  KEY `restaurant_id` (`restaurant_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,1,'Butter_Chicken_Kulcha',400),(1,2,'Paneer_Butter_Masala',250),(1,3,'Prawn_Makhani',350),(2,4,'Galouti_Kebab',350),(2,5,'Bhetki_Paturi',300),(2,6,'Palak_Paneer',200),(3,7,'Prawn_Makhani',350),(3,8,'Mutton_Biryani',500),(3,9,'Paneer_Butter_Masala',250),(4,10,'Mutton_Dak_Bungalow',600),(4,11,'Idli_Sambar ',150),(4,12,'Galouti_Kebab',400);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `order_time` double DEFAULT NULL,
  `order_amount` int DEFAULT NULL,
  `delivery_amount` int DEFAULT NULL,
  `total_amount` int DEFAULT NULL,
  `order_status` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `restaurant_id` int NOT NULL,
  `restaurant_rating` int NOT NULL,
  `app_rating` int NOT NULL,
  KEY `restaurant_id` (`restaurant_id`),
  KEY `rating_ibfk_2` (`app_rating`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`restaurant_id`) REFERENCES `menu` (`restaurant_id`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`app_rating`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `restaurant_id` int NOT NULL AUTO_INCREMENT,
  `restaurant_name` varchar(25) NOT NULL,
  `restaurant_city` varchar(25) NOT NULL,
  `restaurant_area` varchar(25) NOT NULL,
  PRIMARY KEY (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'Indian_Accent','New_Delhi','Pragati_Vihar'),(2,'The_Spice_Route','New_Delhi','Connaught_Place'),(3,'Bell_Pepperz','New_Delhi','Ashok_Vihar'),(4,'The_Bombay_Canteen','Mumbai','Parel');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `customer_id` int DEFAULT NULL,
  `restaurant_id` int DEFAULT NULL,
  `food_id` int DEFAULT NULL,
  `food_quantity` int DEFAULT NULL,
  KEY `customer_id` (`customer_id`),
  KEY `restaurant_id` (`restaurant_id`),
  KEY `food_id` (`food_id`),
  CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `wishlist_ibfk_3` FOREIGN KEY (`food_id`) REFERENCES `menu` (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'oopd'
--

--
-- Dumping routines for database 'oopd'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-10 22:49:12
