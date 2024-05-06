-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 10, 2023 at 06:45 AM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `duias`
--

-- --------------------------------------------------------

--
-- Table structure for table `assign_dates`
--

DROP TABLE IF EXISTS `assign_dates`;
CREATE TABLE IF NOT EXISTS `assign_dates` (
  `assign_id` int(10) NOT NULL,
  `assign_no` int(10) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `assign_detail`
--

DROP TABLE IF EXISTS `assign_detail`;
CREATE TABLE IF NOT EXISTS `assign_detail` (
  `assign_detail_id` int(10) NOT NULL AUTO_INCREMENT,
  `assign_id` int(10) NOT NULL,
  `stud_id` int(10) NOT NULL,
  `assign_no` int(10) NOT NULL,
  `assign_received` varchar(1) NOT NULL,
  `marks` int(10) NOT NULL,
  PRIMARY KEY (`assign_detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assign_detail`
--

INSERT INTO `assign_detail` (`assign_detail_id`, `assign_id`, `stud_id`, `assign_no`, `assign_received`, `marks`) VALUES
(1, 1, 1, 1, 'Y', 20),
(2, 1, 1, 2, 'N', 0),
(3, 1, 2, 1, 'N', 0),
(4, 1, 2, 2, 'N', 0),
(5, 1, 3, 1, 'N', 0),
(6, 1, 3, 2, 'N', 0),
(7, 1, 4, 1, 'N', 0),
(8, 1, 4, 2, 'N', 0),
(9, 1, 5, 1, 'N', 0),
(10, 1, 5, 2, 'N', 0),
(11, 1, 6, 1, 'N', 0),
(12, 1, 6, 2, 'N', 0),
(13, 1, 7, 1, 'N', 0),
(14, 1, 7, 2, 'N', 0),
(15, 1, 8, 1, 'N', 0),
(16, 1, 8, 2, 'N', 0);

-- --------------------------------------------------------

--
-- Table structure for table `assign_master`
--

DROP TABLE IF EXISTS `assign_master`;
CREATE TABLE IF NOT EXISTS `assign_master` (
  `assign_id` int(10) NOT NULL,
  `program_id` int(10) NOT NULL,
  `faculty_id` int(10) NOT NULL,
  `sub_id` int(10) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `div_id` int(10) NOT NULL,
  `no_of_assign` int(5) NOT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assign_master`
--

INSERT INTO `assign_master` (`assign_id`, `program_id`, `faculty_id`, `sub_id`, `semester`, `div_id`, `no_of_assign`) VALUES
(1, 1, 1, 1, 'SEM6', 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `attend_detail`
--

DROP TABLE IF EXISTS `attend_detail`;
CREATE TABLE IF NOT EXISTS `attend_detail` (
  `attend_id` int(10) NOT NULL,
  `stud_id` int(10) NOT NULL,
  `rno` int(10) NOT NULL,
  `present_absent` varchar(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attend_detail`
--

INSERT INTO `attend_detail` (`attend_id`, `stud_id`, `rno`, `present_absent`) VALUES
(1, 1, 1, 'P'),
(1, 2, 2, 'P'),
(1, 3, 3, 'P'),
(1, 4, 4, 'P'),
(1, 5, 5, 'A'),
(1, 6, 6, 'P'),
(1, 7, 7, 'P'),
(1, 8, 8, 'P'),
(2, 1, 1, 'P'),
(2, 2, 2, 'P'),
(2, 3, 3, 'P'),
(2, 4, 4, 'P'),
(2, 5, 5, 'P'),
(2, 6, 6, 'P'),
(2, 7, 7, 'P'),
(2, 8, 8, 'P'),
(3, 1, 1, 'P'),
(3, 2, 2, 'P'),
(3, 3, 3, 'P'),
(3, 4, 4, 'P'),
(3, 5, 5, 'P'),
(3, 6, 6, 'P'),
(3, 7, 7, 'P'),
(3, 8, 8, 'P'),
(6, 8, 8, 'P'),
(6, 7, 7, 'P'),
(6, 6, 6, 'P'),
(6, 5, 5, 'P'),
(6, 4, 4, 'P'),
(6, 3, 3, 'P'),
(6, 2, 2, 'P'),
(6, 1, 1, 'P'),
(5, 8, 8, 'P'),
(5, 7, 7, 'P'),
(5, 6, 6, 'P'),
(5, 5, 5, 'P'),
(5, 4, 4, 'P'),
(5, 3, 3, 'P'),
(5, 2, 2, 'P'),
(5, 1, 1, 'P'),
(4, 8, 8, 'P'),
(4, 7, 7, 'P'),
(4, 6, 6, 'P'),
(4, 5, 5, 'P'),
(4, 4, 4, 'P'),
(4, 3, 3, 'P'),
(4, 2, 2, 'P'),
(4, 1, 1, 'P'),
(7, 1, 1, 'A'),
(7, 2, 2, 'A'),
(7, 3, 3, 'P'),
(7, 4, 4, 'P'),
(7, 5, 5, 'P'),
(7, 6, 6, 'P'),
(7, 7, 7, 'P'),
(7, 8, 8, 'P'),
(8, 1, 1, 'A'),
(8, 2, 2, 'A'),
(8, 3, 3, 'P'),
(8, 4, 4, 'P'),
(8, 5, 5, 'P'),
(8, 6, 6, 'P'),
(8, 7, 7, 'P'),
(8, 8, 8, 'P'),
(9, 1, 1, 'A'),
(9, 2, 2, 'A'),
(9, 3, 3, 'P'),
(9, 4, 4, 'P'),
(9, 5, 5, 'P'),
(9, 6, 6, 'P'),
(9, 7, 7, 'P'),
(9, 8, 8, 'P'),
(10, 1, 1, 'A'),
(10, 2, 2, 'A'),
(10, 3, 3, 'P'),
(10, 4, 4, 'P'),
(10, 5, 5, 'P'),
(10, 6, 6, 'P'),
(10, 7, 7, 'P'),
(10, 8, 8, 'P');

-- --------------------------------------------------------

--
-- Table structure for table `attend_master`
--

DROP TABLE IF EXISTS `attend_master`;
CREATE TABLE IF NOT EXISTS `attend_master` (
  `attend_id` int(10) NOT NULL,
  `attend_date` date NOT NULL,
  `faculty_id` int(10) NOT NULL,
  `lec_no` int(3) NOT NULL,
  `program_id` int(10) NOT NULL,
  `sub_id` int(10) NOT NULL,
  `semester` varchar(8) NOT NULL,
  `lec_time` varchar(15) NOT NULL,
  `div_id` int(10) NOT NULL,
  PRIMARY KEY (`attend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attend_master`
--

INSERT INTO `attend_master` (`attend_id`, `attend_date`, `faculty_id`, `lec_no`, `program_id`, `sub_id`, `semester`, `lec_time`, `div_id`) VALUES
(1, '2023-04-04', 1, 1, 1, 1, 'SEM6', '10:00 to 11:00', 5),
(2, '2023-04-04', 1, 2, 1, 1, 'SEM6', '11:00 to 12:00', 5),
(3, '2023-04-04', 2, 3, 1, 2, 'SEM6', '12:00 to 01:00', 5),
(4, '2023-04-08', 1, 1, 1, 1, 'SEM6', '10:00 to 11:00', 5),
(5, '2023-04-08', 1, 2, 1, 1, 'SEM6', '11:00 to 12:00', 5),
(6, '2023-04-08', 1, 3, 1, 1, 'SEM6', '12:00 to 1:00', 5),
(7, '2023-04-08', 1, 1, 1, 1, 'SEM6', '10 to 11', 5),
(8, '2023-04-09', 1, 2, 1, 1, 'SEM6', '11 to 12', 5),
(9, '2023-04-09', 1, 1, 1, 1, 'SEM6', '12 to 1', 5),
(10, '2023-04-09', 2, 4, 1, 2, 'SEM6', '2 to 3', 5);

-- --------------------------------------------------------

--
-- Table structure for table `clerk_detail`
--

DROP TABLE IF EXISTS `clerk_detail`;
CREATE TABLE IF NOT EXISTS `clerk_detail` (
  `clerk_id` int(10) NOT NULL,
  `clerk_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `pwd` varchar(10) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `security_que` varchar(100) NOT NULL,
  `security_ans` varchar(100) NOT NULL,
  `clerk_status` int(1) NOT NULL,
  PRIMARY KEY (`clerk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clerk_detail`
--

INSERT INTO `clerk_detail` (`clerk_id`, `clerk_name`, `address`, `city`, `mobile_no`, `email_id`, `pwd`, `gender`, `security_que`, `security_ans`, `clerk_status`) VALUES
(1, 'Krishna', 'Tithal Road', 'Valsad', '9876543210', 'krishna@yahoo.com', '111111', 'MALE', 'First Pet Name', 'cow', 0),
(2, 'Grisma', 'halar civil road', 'Valsad', '8876543210', 'grisma@yahoo.com', '111111', 'FEMALE', 'Favourite Color', 'blue', 0);

-- --------------------------------------------------------

--
-- Table structure for table `dep_master`
--

DROP TABLE IF EXISTS `dep_master`;
CREATE TABLE IF NOT EXISTS `dep_master` (
  `dep_id` int(10) NOT NULL,
  `dep_name` varchar(50) NOT NULL,
  `dep_status` int(1) NOT NULL,
  PRIMARY KEY (`dep_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dep_master`
--

INSERT INTO `dep_master` (`dep_id`, `dep_name`, `dep_status`) VALUES
(1, 'COMPUTER SCIENCE', 0),
(2, 'Chemistry', 0),
(3, 'Microbiology', 0),
(4, 'Commerce', 0);

-- --------------------------------------------------------

--
-- Table structure for table `division_detail`
--

DROP TABLE IF EXISTS `division_detail`;
CREATE TABLE IF NOT EXISTS `division_detail` (
  `div_id` int(10) NOT NULL,
  `academic_year` varchar(10) NOT NULL,
  `program_id` int(10) NOT NULL,
  `class_name` varchar(10) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `division` varchar(1) NOT NULL,
  `clerk_id` int(10) NOT NULL,
  `div_status` int(1) NOT NULL,
  PRIMARY KEY (`div_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `division_detail`
--

INSERT INTO `division_detail` (`div_id`, `academic_year`, `program_id`, `class_name`, `semester`, `division`, `clerk_id`, `div_status`) VALUES
(1, '2022-2023', 1, 'FYBCA', 'SEM2', 'A', 1, 0),
(2, '2022-2023', 1, 'FYBCA', 'SEM2', 'B', 1, 0),
(3, '2022-2023', 1, 'SYBCA', 'SEM4', 'A', 1, 0),
(4, '2022-2023', 1, 'SYBCA', 'SEM4', 'B', 1, 0),
(5, '2022-2023', 1, 'TYBCA', 'SEM6', 'A', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `faculty_detail`
--

DROP TABLE IF EXISTS `faculty_detail`;
CREATE TABLE IF NOT EXISTS `faculty_detail` (
  `faculty_id` int(10) NOT NULL,
  `faculty_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `pwd` varchar(10) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `security_que` varchar(100) NOT NULL,
  `security_ans` varchar(100) NOT NULL,
  `program_id` int(10) NOT NULL,
  `clerk_id` int(10) NOT NULL,
  `faculty_status` int(1) NOT NULL,
  PRIMARY KEY (`faculty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `faculty_detail`
--

INSERT INTO `faculty_detail` (`faculty_id`, `faculty_name`, `address`, `city`, `mobile_no`, `email_id`, `pwd`, `gender`, `security_que`, `security_ans`, `program_id`, `clerk_id`, `faculty_status`) VALUES
(1, 'Dr Snehal Joshi', 'rina park', 'valsad', '8596321470', 'snehal@yahoo.com', '111111', 'MALE', 'Favourite Food', 'Gujarati', 1, 1, 0),
(2, 'Dr Rachana Shukla', 'chipwad', 'valsad', '9632587410', 'rachana@yahoo.com', '111111', 'FEMALE', 'First School Name', 'avabai', 1, 1, 0),
(3, 'Dr Darshna Rajput', 'tithal road', 'valsad', '7412589630', 'darshna@yahoo.com', '111111', 'FEMALE', 'First Pet Name', 'kitu', 1, 1, 0),
(4, 'Dr Premal Shah', 'chipwad', 'valsad', '7412589630', 'premal@yahoo.com', '111111', 'FEMALE', 'Favourite Color', 'hotpink', 1, 1, 0),
(5, 'Dr Akruti Naik', 'Gandevi', 'Gandevi', '8485123690', 'akruti@yahoo.com', '111111', 'FEMALE', 'Favourite Sports', 'hockey', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `hod_detail`
--

DROP TABLE IF EXISTS `hod_detail`;
CREATE TABLE IF NOT EXISTS `hod_detail` (
  `hod_id` int(10) NOT NULL,
  `hod_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `pwd` varchar(10) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `security_que` varchar(100) NOT NULL,
  `security_ans` varchar(100) NOT NULL,
  `dep_id` int(10) NOT NULL,
  `hod_status` int(1) NOT NULL,
  PRIMARY KEY (`hod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hod_detail`
--

INSERT INTO `hod_detail` (`hod_id`, `hod_name`, `address`, `city`, `mobile_no`, `email_id`, `pwd`, `gender`, `security_que`, `security_ans`, `dep_id`, `hod_status`) VALUES
(1, 'Dr Rachana Shukla', 'Chipwad', 'Valsad', '9876543210', 'rachana@yahoo.com', '111111', 'FEMALE', 'Favourite Food', 'Gujarati', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `internal_marks_detail`
--

DROP TABLE IF EXISTS `internal_marks_detail`;
CREATE TABLE IF NOT EXISTS `internal_marks_detail` (
  `marks_id` int(10) NOT NULL,
  `stud_id` int(10) NOT NULL,
  `marks` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `internal_marks_detail`
--

INSERT INTO `internal_marks_detail` (`marks_id`, `stud_id`, `marks`) VALUES
(1, 1, 25),
(1, 2, 26),
(1, 3, 27),
(1, 4, 28),
(1, 5, 29),
(1, 6, 20),
(1, 7, 19),
(1, 8, 18),
(2, 1, 20),
(2, 2, 21),
(2, 3, 22),
(2, 4, 23),
(2, 5, 20),
(2, 6, 19),
(2, 7, 18),
(2, 8, 15),
(3, 1, 20),
(3, 2, 26),
(3, 3, 26),
(3, 4, 29),
(3, 5, 25),
(3, 6, 27),
(3, 7, 24),
(3, 8, 25);

-- --------------------------------------------------------

--
-- Table structure for table `internal_marks_master`
--

DROP TABLE IF EXISTS `internal_marks_master`;
CREATE TABLE IF NOT EXISTS `internal_marks_master` (
  `marks_id` int(10) NOT NULL,
  `marks_date` date NOT NULL,
  `program_id` int(10) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `sub_id` int(10) NOT NULL,
  `faculty_id` int(10) NOT NULL,
  `internal_exam` int(5) NOT NULL,
  `total_marks` int(10) NOT NULL,
  `academic_year` varchar(15) NOT NULL,
  `div_id` int(10) NOT NULL,
  PRIMARY KEY (`marks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `internal_marks_master`
--

INSERT INTO `internal_marks_master` (`marks_id`, `marks_date`, `program_id`, `semester`, `sub_id`, `faculty_id`, `internal_exam`, `total_marks`, `academic_year`, `div_id`) VALUES
(1, '2023-03-07', 1, 'SEM6', 1, 1, 1, 30, '2022-2023', 5),
(2, '2023-03-07', 1, 'SEM6', 1, 1, 2, 30, '2022-2023', 5),
(3, '2023-04-09', 1, 'SEM6', 2, 2, 1, 30, '2022-2023', 5);

-- --------------------------------------------------------

--
-- Table structure for table `principal_detail`
--

DROP TABLE IF EXISTS `principal_detail`;
CREATE TABLE IF NOT EXISTS `principal_detail` (
  `principal_id` int(10) NOT NULL,
  `principal_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `pwd` varchar(10) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `security_que` varchar(100) NOT NULL,
  `security_ans` varchar(50) NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`principal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `principal_detail`
--

INSERT INTO `principal_detail` (`principal_id`, `principal_name`, `address`, `city`, `mobile_no`, `email_id`, `pwd`, `gender`, `security_que`, `security_ans`, `status`) VALUES
(1, 'Dr Snehal Joshi', 'rina park', 'valsad', '9685321470', 'snehal@yahoo.com', '111111', 'MALE', 'what is favourite food', 'gujarati', 0);

-- --------------------------------------------------------

--
-- Table structure for table `program_master`
--

DROP TABLE IF EXISTS `program_master`;
CREATE TABLE IF NOT EXISTS `program_master` (
  `program_id` int(10) NOT NULL,
  `program_name` varchar(50) NOT NULL,
  `dep_id` int(10) NOT NULL,
  `clerk_id` int(10) NOT NULL,
  `program_status` int(1) NOT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `program_master`
--

INSERT INTO `program_master` (`program_id`, `program_name`, `dep_id`, `clerk_id`, `program_status`) VALUES
(1, 'BCA', 1, 1, 0),
(2, 'BSC Computer Science', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `student_detail`
--

DROP TABLE IF EXISTS `student_detail`;
CREATE TABLE IF NOT EXISTS `student_detail` (
  `stud_id` int(10) NOT NULL,
  `rno` int(10) NOT NULL,
  `stud_name` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(50) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `email_id` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(8) NOT NULL,
  `program_id` int(10) NOT NULL,
  `class_name` varchar(10) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `div_id` int(10) NOT NULL,
  `clerk_id` int(10) NOT NULL,
  PRIMARY KEY (`stud_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_detail`
--

INSERT INTO `student_detail` (`stud_id`, `rno`, `stud_name`, `address`, `city`, `mobile_no`, `email_id`, `dob`, `gender`, `program_id`, `class_name`, `semester`, `div_id`, `clerk_id`) VALUES
(1, 1, 'Janvi Tandel', 'Hingraj', 'Valsad', '8876543210', 'janvii@yahoo.com', '2002-12-09', 'FEMALE', 1, 'TYBCA', 'SEM6', 5, 1),
(2, 2, 'krupali tandel', 'kosamba', 'valsad', '9876543210', 'krupali@yahoo.com', '2002-12-20', 'FEMALE', 1, 'TYBCA', 'SEM6', 5, 1),
(3, 3, 'hani Prajapati', 'kosamba', 'valsad', '9876540123', 'hani@yahoo.com', '2003-04-11', 'FEMALE', 1, 'TYBCA', 'SEM6', 5, 1),
(4, 4, 'Aditi Tandel', 'kosamba', 'valsad', '7412589630', 'aditi@yahoo.com', '2002-09-25', 'FEMALE', 1, 'TYBCA', 'SEM6', 5, 1),
(5, 5, 'Kamal Prajapati', 'Kosamba', 'valsad', '7485123690', 'kamal@yahoo.com', '2002-11-24', 'MALE', 1, 'TYBCA', 'SEM6', 5, 1),
(6, 6, 'Sahil Patil', 'abrama', 'valsad', '7485963210', 'sahil@yahoo.com', '2003-03-29', 'MALE', 1, 'TYBCA', 'SEM6', 5, 1),
(7, 7, 'Sneh Vaishnav', 'bhadeli', 'valsad', '7485963210', 'sneh@yahoo.com', '2003-07-15', 'MALE', 1, 'TYBCA', 'SEM6', 5, 1),
(8, 8, 'Smith Kansara', 'pardi', 'pardi', '7412589630', 'smith@yahoo.com', '2002-02-28', 'MALE', 1, 'TYBCA', 'SEM6', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `subject_master`
--

DROP TABLE IF EXISTS `subject_master`;
CREATE TABLE IF NOT EXISTS `subject_master` (
  `sub_id` int(10) NOT NULL,
  `sub_name` varchar(50) NOT NULL,
  `program_id` int(10) NOT NULL,
  `sub_code` varchar(5) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `hod_id` int(10) NOT NULL,
  `sub_status` int(1) NOT NULL,
  PRIMARY KEY (`sub_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject_master`
--

INSERT INTO `subject_master` (`sub_id`, `sub_name`, `program_id`, `sub_code`, `semester`, `hod_id`, `sub_status`) VALUES
(1, 'Cloud Computing', 1, '601', 'SEM6', 1, 0),
(2, 'ECommerce', 1, '602', 'SEM6', 1, 0),
(3, 'Seminar', 1, '603', 'SEM6', 1, 0),
(4, 'Project', 1, '604', 'SEM6', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `sub_assign_detail`
--

DROP TABLE IF EXISTS `sub_assign_detail`;
CREATE TABLE IF NOT EXISTS `sub_assign_detail` (
  `assign_id` int(10) NOT NULL,
  `faculty_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sub_assign_detail`
--

INSERT INTO `sub_assign_detail` (`assign_id`, `faculty_id`) VALUES
(1, 1),
(2, 2),
(3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `sub_assign_master`
--

DROP TABLE IF EXISTS `sub_assign_master`;
CREATE TABLE IF NOT EXISTS `sub_assign_master` (
  `assign_id` int(10) NOT NULL,
  `assign_year` varchar(10) NOT NULL,
  `sub_id` int(10) NOT NULL,
  `hod_id` int(10) NOT NULL,
  `program_id` int(10) NOT NULL,
  `semester` varchar(5) NOT NULL,
  `div_id` int(10) NOT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sub_assign_master`
--

INSERT INTO `sub_assign_master` (`assign_id`, `assign_year`, `sub_id`, `hod_id`, `program_id`, `semester`, `div_id`) VALUES
(1, '2022-2023', 1, 1, 1, 'SEM6', 5),
(2, '2022-2023', 2, 1, 1, 'SEM6', 5),
(3, '2022-2023', 3, 1, 1, 'SEM6', 5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
