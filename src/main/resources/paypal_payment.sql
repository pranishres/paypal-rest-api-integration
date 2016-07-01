-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 01, 2016 at 02:09 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `paypal_payment`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing_address`
--

CREATE TABLE `billing_address` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `city` varchar(30) NOT NULL,
  `country_code` varchar(5) NOT NULL,
  `address1` varchar(100) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `state` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing_address`
--

INSERT INTO `billing_address` (`id`, `customer_id`, `city`, `country_code`, `address1`, `postal_code`, `state`) VALUES
(1, 1, 'Johnstown', 'US', '52 N Main ST', '43210', 'OH');

-- --------------------------------------------------------

--
-- Table structure for table `billing_agreement`
--

CREATE TABLE `billing_agreement` (
  `id` int(11) NOT NULL,
  `billing_agreement_id` varchar(50) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  `plan_id` int(11) NOT NULL,
  `token` varchar(50) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing_agreement`
--

INSERT INTO `billing_agreement` (`id`, `billing_agreement_id`, `customer_id`, `plan_id`, `token`, `state`) VALUES
(7, 'I-F6EWARCG262U', 1, 14, 'EC-90596489527673301', 'Active'),
(8, 'I-RUTB7DARATSE', 1, 17, 'EC-6SF75878R63312345', 'Active'),
(14, 'I-0J0YJDNNBDKB', 1, 19, 'EC-7K770192ED1135046', 'Active'),
(15, 'I-GREH4FFGWM62', 1, 19, NULL, 'Active'),
(16, 'I-0T8797617WBV', 1, 19, NULL, 'Active'),
(17, 'I-4AFRS9BGJT60', 1, 19, NULL, 'Active'),
(18, 'I-76KDRTJ2NPDS', 1, 18, NULL, 'Active'),
(19, 'I-4S3JS3VU4BHB', 1, 18, NULL, 'Active'),
(20, NULL, 1, 17, 'EC-00V37515US149152S', NULL),
(21, 'I-BUMSKC6E9SAW', 1, 17, NULL, 'Active'),
(22, 'I-PFW1Y98YTA16', 1, 22, NULL, 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `billing_plan_agreement`
--

CREATE TABLE `billing_plan_agreement` (
  `id` int(11) NOT NULL,
  `billing_agreement_id` varchar(50) NOT NULL,
  `plan_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `first_name`, `last_name`) VALUES
(1, 'Pranish', 'Shrestha');

-- --------------------------------------------------------

--
-- Table structure for table `customer_credit_card`
--

CREATE TABLE `customer_credit_card` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `card_number` varchar(30) NOT NULL,
  `card_id` varchar(30) NOT NULL,
  `payer_id` varchar(30) DEFAULT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `expiry_month` int(11) NOT NULL,
  `expiry_year` int(11) NOT NULL,
  `card_type` varchar(30) NOT NULL,
  `default_card` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_credit_card`
--

INSERT INTO `customer_credit_card` (`id`, `customer_id`, `card_number`, `card_id`, `payer_id`, `first_name`, `last_name`, `expiry_month`, `expiry_year`, `card_type`, `default_card`) VALUES
(2, 1, '4032035145786042', 'CARD-7A4946358R445564AK5ZURZQ', NULL, 'pranish card', 'shrestha_card', 1, 2017, 'visa', 0),
(3, 1, '4032035145786042', 'CARD-3MV33182SC787214PK52LYRQ', NULL, 'pranish card', 'shrestha_card', 1, 2017, 'visa', 1);

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

CREATE TABLE `plan` (
  `id` int(11) NOT NULL,
  `plan_id` varchar(50) NOT NULL,
  `state` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `plan`
--

INSERT INTO `plan` (`id`, `plan_id`, `state`) VALUES
(1, 'P-1EG51987FL216900WTO7EDOQ', 'CREATED'),
(14, 'P-6LD82494T2510952ATK3WSCI', 'ACTIVE'),
(17, 'P-8X867568GV8640429UALDD6A', 'ACTIVE'),
(18, 'P-8PU70099AP988423VTQL2RZY', 'ACTIVE'),
(19, 'P-9F390721R8864380YUS2EM6Y', 'ACTIVE'),
(21, 'P-9RW44043AF6386327UVML3PA', 'ACTIVE'),
(22, 'P-06N67232C3597860PUWL6IHY', 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `customer_credit_card_id` varchar(50) DEFAULT NULL,
  `payment_id` varchar(100) NOT NULL,
  `intent` varchar(30) NOT NULL,
  `pay_method` varchar(30) NOT NULL,
  `amount_currency` varchar(5) NOT NULL,
  `details_shipping` float NOT NULL,
  `details_sub_total` float NOT NULL,
  `details_tax` float NOT NULL,
  `date` datetime NOT NULL,
  `transaction_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `customer_id`, `customer_credit_card_id`, `payment_id`, `intent`, `pay_method`, `amount_currency`, `details_shipping`, `details_sub_total`, `details_tax`, `date`, `transaction_description`) VALUES
(1, 1, 'CARD-7A4946358R445564AK5ZURZQ', 'PAY-3R758455779757052K5ZVLRY', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-29 10:45:01', 'This is the payment transaction description.'),
(3, 1, 'CARD-3MV33182SC787214PK52LYRQ', 'PAY-3BM034519L2608749K52L4JI', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-30 12:22:35', 'This is the payment transaction description.'),
(4, 1, NULL, 'PAY-65N937711C0561943K52MCJQ', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-30 12:35:23', 'This is the payment transaction description.'),
(5, 1, NULL, 'PAY-4GP10974TF139383PK53BTTQ', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-01 13:05:50', 'This is the payment transaction description.'),
(6, 1, 'CARD-3MV33182SC787214PK52LYRQ', 'PAY-3YR94807V5193961AK53BT5I', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-01 13:06:29', 'This is the payment transaction description.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing_address`
--
ALTER TABLE `billing_address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `billing_agreement`
--
ALTER TABLE `billing_agreement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `billing_plan_agreement`
--
ALTER TABLE `billing_plan_agreement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer_credit_card`
--
ALTER TABLE `customer_credit_card`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `billing_address`
--
ALTER TABLE `billing_address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `billing_agreement`
--
ALTER TABLE `billing_agreement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `customer_credit_card`
--
ALTER TABLE `customer_credit_card`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;