-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 08, 2023 at 05:14 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `flat2dbms`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteWithParams` (IN `tempid` INT(10))   BEGIN
DELETE FROM `movie` WHERE `id`=tempid;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `display` ()   BEGIN
SELECT * FROM movie;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertWithParam` (IN `tempname` VARCHAR(30), IN `tempactors` VARCHAR(60), IN `tempdor` VARCHAR(20), IN `tempgenre` VARCHAR(15), IN `tempdirector` VARCHAR(20), IN `tempboxcollection` VARCHAR(8))   BEGIN
    INSERT INTO `movie` (name, actors, dor, genre, director, boxcollection)
    VALUES (tempname, tempactors, tempdor, tempgenre, tempdirector, tempboxcollection);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showFlop` ()   BEGIN
SELECT `name` FROM movie WHERE `boxcollection`<400000;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `showHit` ()   BEGIN
SELECT `name` FROM movie WHERE `boxcollection`>400000;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateWithParams` (IN `tempid` INT(10), IN `newName` VARCHAR(30), IN `newActors` VARCHAR(60), IN `newDor` VARCHAR(20), IN `newGenre` VARCHAR(15), IN `newDirector` VARCHAR(20), IN `newBoxCollection` VARCHAR(8))   BEGIN
    UPDATE `movie`
    SET
        `name` = newName,
        `actors` = newActors,
        `dor` = newDor,
        `genre` = newGenre,
        `director` = newDirector,
        `boxcollection` = newBoxCollection
    WHERE `id` = tempid;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `id` int(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `actors` varchar(60) NOT NULL,
  `dor` varchar(20) NOT NULL,
  `genre` varchar(15) NOT NULL,
  `director` varchar(20) NOT NULL,
  `boxcollection` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `name`, `actors`, `dor`, `genre`, `director`, `boxcollection`) VALUES
(7, 'Movie1', 'Actor1', '01/01/2001', 'Genre1', 'Director1', '100000'),
(8, 'Movie2', 'Actor2', '02/02/2002', 'Genre2', 'Director2', '200000'),
(9, 'Movie3', 'Actor3', '03/03/2003', 'Genre3', 'Director3', '300000'),
(10, 'Movie4', 'Actor4', '04/04/2004', 'Genre4', 'Director4', '400000'),
(11, 'Movie5', 'Actor5', '05/05/2005', 'Genre5', 'Director5', '500000'),
(16, 'Movie6', 'Actor6', '06/06/2006', 'Genre6', 'Director6', '600000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
