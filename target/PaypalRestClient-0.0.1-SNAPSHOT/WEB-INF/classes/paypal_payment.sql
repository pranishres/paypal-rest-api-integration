-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 08, 2016 at 06:36 AM
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
  `state` varchar(30) DEFAULT NULL,
  `exipry_date` date DEFAULT NULL,
  `next_billing_date` date DEFAULT NULL,
  `amount` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing_agreement`
--

INSERT INTO `billing_agreement` (`id`, `billing_agreement_id`, `customer_id`, `plan_id`, `token`, `state`, `exipry_date`, `next_billing_date`, `amount`) VALUES
(7, 'I-F6EWARCG262U', 1, 14, 'EC-90596489527673301', 'Active', NULL, NULL, NULL),
(8, 'I-RUTB7DARATSE', 1, 17, 'EC-6SF75878R63312345', 'Active', NULL, NULL, NULL),
(14, 'I-0J0YJDNNBDKB', 1, 19, 'EC-7K770192ED1135046', 'Active', NULL, NULL, NULL),
(15, 'I-GREH4FFGWM62', 1, 19, NULL, 'Active', NULL, NULL, NULL),
(16, 'I-0T8797617WBV', 1, 19, NULL, 'Cancelled', NULL, NULL, NULL),
(17, 'I-4AFRS9BGJT60', 1, 19, NULL, 'Active', NULL, NULL, NULL),
(18, 'I-76KDRTJ2NPDS', 1, 18, NULL, 'Active', NULL, NULL, NULL),
(19, 'I-4S3JS3VU4BHB', 1, 18, NULL, 'Active', NULL, NULL, NULL),
(21, 'I-BUMSKC6E9SAW', 1, 17, NULL, 'Active', NULL, NULL, NULL),
(22, 'I-PFW1Y98YTA16', 1, 22, NULL, 'Active', NULL, NULL, NULL),
(23, 'I-92V4P0UF1PGS', 1, 26, NULL, 'Active', NULL, NULL, NULL),
(26, 'I-12W8XV04JV7K', 1, 28, NULL, 'Active', NULL, NULL, NULL),
(27, 'I-MMCB0EBHAPHV', 1, 28, 'EC-5N055275CX284222W', 'Active', NULL, NULL, NULL),
(29, 'I-949994AUSLGK', 1, 30, NULL, 'Active', NULL, NULL, NULL),
(31, 'I-S550M28ARK1K', 1, 30, 'EC-0M439602NB566580T', 'Active', NULL, NULL, NULL),
(32, 'I-SJNEMV7C32BK', 1, 30, NULL, 'Active', NULL, NULL, NULL),
(33, 'I-61242L6Y6RGF', 1, 32, NULL, 'Active', NULL, NULL, NULL),
(34, 'I-D3VB3VS0N21A', 1, 34, NULL, 'Active', NULL, NULL, 89.57),
(35, 'I-0UY6J3EFJJCS', 1, 36, NULL, 'Active', NULL, NULL, NULL),
(36, 'I-46DYXJPLKGEM', 1, 37, 'EC-13332299WX9101110', 'Active', NULL, NULL, 79.57),
(37, 'I-PHNYHKJ6B6US', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(38, 'I-JP0B7RR31RRL', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(39, 'I-NYPXRM34DML1', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(40, 'I-F4W013KGMWFS', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(41, 'I-UG1MFF5MSX5A', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(42, 'I-D4YS0SA2KHL1', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(43, 'I-P8KY6N5SA1BG', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(44, 'I-H2LRSYA6HAU4', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(45, 'I-1RFLWMSR4BF4', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(46, 'I-PFC20F667FWT', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(47, 'I-4HNCJPBFT5R1', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(48, 'I-0XMJTDAXY0DH', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(49, 'I-DX8ALEAYR08X', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(50, 'I-L0JBFDSKXGXE', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(51, 'I-AY0VG3312CSB', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(52, 'I-40BNJ326KP68', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(53, 'I-PGVUPAH2FV5S', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(54, 'I-CN8JVHVTH472', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(55, 'I-755GE2UM5LS2', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(56, 'I-PRA0FKTJMDHJ', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(57, 'I-2FN05DFG578N', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(58, 'I-PA533UM7MKSV', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(59, 'I-8E4D65L7BPDC', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(60, 'I-GWXPCWLF3B5W', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(61, 'I-A7351WE8E9F6', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(62, 'I-SHECGT8G8JCK', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(63, 'I-GLY8NFJAVKDD', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(64, 'I-6XSRGXJ1MG4T', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(65, 'I-JJKG4Y7P17S0', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(66, 'I-91BNCY1HPAMT', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(67, 'I-C6KXHCRDTRJM', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(68, 'I-FT66C5G1G7LC', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(69, 'I-DBXUBG1J96Y1', 1, 38, 'EC-5PH55598RP158384E', 'Active', NULL, NULL, NULL),
(70, 'I-HH88CENFSW1S', 1, 38, 'EC-2UY732938B702501M', 'Active', NULL, NULL, NULL),
(71, 'I-G2AMAE8CNAKD', 1, 38, NULL, 'Active', NULL, NULL, NULL),
(72, 'I-LKSV3S1WHLDP', 1, 38, NULL, 'Active', NULL, NULL, NULL);

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

INSERT INTO `customer_credit_card` (`id`, `customer_id`, `card_number`, `card_id`, `first_name`, `last_name`, `expiry_month`, `expiry_year`, `card_type`, `default_card`) VALUES
(2, 1, '4032035145786042', 'CARD-7A4946358R445564AK5ZURZQ', 'pranish card', 'shrestha_card', 1, 2017, 'visa', 0),
(3, 1, '4032035145786042', 'CARD-3MV33182SC787214PK52LYRQ', 'pranish card', 'shrestha_card', 1, 2017, 'visa', 0),
(9, 1, '4032035145786042', 'CARD-1A754479Y9809451RK55T4BI', 'pranish credit card', 'shrestha_card', 1, 2019, 'visa', 1);

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
(22, 'P-06N67232C3597860PUWL6IHY', 'ACTIVE'),
(23, 'P-9NX2466406107210LWQRI3IY', 'ACTIVE'),
(24, 'P-2A6503756E7825524WQYG3HQ', 'ACTIVE'),
(26, 'P-0HD49836PH6868035WWP4TPI', 'ACTIVE'),
(27, 'P-6W246298KY286331SWXHAQ4Q', 'ACTIVE'),
(28, 'P-1U396282LX704753RXEYPMDQ', 'ACTIVE'),
(29, 'P-1KN27391TS969944JXFXDWAA', 'ACTIVE'),
(30, 'P-38099620EY328773AXFY75YA', 'ACTIVE'),
(31, 'P-6K042366NW034282BXKECWNQ', 'CREATED'),
(32, 'P-64W08156ND335382TXZLPOVA', 'ACTIVE'),
(33, 'P-0TR43917UY5364931X4DTKSA', 'CREATED'),
(34, 'P-34X12221LT724415DX4LEPFY', 'ACTIVE'),
(35, 'P-30140192E22291845X5C6FQI', 'CREATED'),
(36, 'P-4880969905935801AX6MJM2A', 'ACTIVE'),
(37, 'P-5W381520JM548552RX7UAYBA', 'ACTIVE'),
(38, 'P-5Y408520K8883834VYPOAZJI', 'ACTIVE');

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
  `transaction_description` text NOT NULL,
  `transaction_id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `customer_id`, `customer_credit_card_id`, `payment_id`, `intent`, `pay_method`, `amount_currency`, `details_shipping`, `details_sub_total`, `details_tax`, `date`, `transaction_description`, `transaction_id`) VALUES
(1, 1, 'CARD-7A4946358R445564AK5ZURZQ', 'PAY-3R758455779757052K5ZVLRY', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-29 10:45:01', 'This is the payment transaction description.', NULL),
(3, 1, 'CARD-3MV33182SC787214PK52LYRQ', 'PAY-3BM034519L2608749K52L4JI', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-30 12:22:35', 'This is the payment transaction description.', NULL),
(4, 1, NULL, 'PAY-65N937711C0561943K52MCJQ', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-06-30 12:35:23', 'This is the payment transaction description.', NULL),
(5, 1, NULL, 'PAY-4GP10974TF139383PK53BTTQ', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-01 13:05:50', 'This is the payment transaction description.', NULL),
(6, 1, 'CARD-3MV33182SC787214PK52LYRQ', 'PAY-3YR94807V5193961AK53BT5I', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-01 13:06:29', 'This is the payment transaction description.', NULL),
(7, 1, NULL, 'PAY-7ER67133BS9547503K55S24I', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 09:30:57', 'This is the payment transaction description.', NULL),
(8, 1, NULL, 'PAY-3J9743131V0007014K55S3RY', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 09:32:29', 'This is the payment transaction description.', NULL),
(9, 1, 'CARD-3MV33182SC787214PK52LYRQ', 'PAY-25917405LD233971TK55S4JA', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 09:33:55', 'This is the payment transaction description.', NULL),
(10, 1, NULL, 'PAY-3WR68120XW4133439K55S4PI', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 09:34:29', 'This is the payment transaction description.', NULL),
(11, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-3HJ27507LJ744120PK55T4WI', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 10:43:12', 'This is the payment transaction description.', NULL),
(12, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-5548063794020415EK55ULCQ', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 11:13:52', 'This is the payment transaction description.', NULL),
(13, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-80Y18522B9374553DK55YHTI', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 15:39:32', 'This is the payment transaction description.', NULL),
(14, 1, NULL, 'PAY-4E409065P36951726K55YPMA', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 15:56:08', 'This is the payment transaction description.', NULL),
(15, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-5PT57777254983612K55YV5I', 'sale', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 16:09:57', 'This is the payment transaction description.', NULL),
(16, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-3MG08871WV054061WK55YWTA', 'authorize', 'credit_card', 'USD', 1, 1, 1, '2016-07-05 16:11:24', 'This is the payment transaction description.', NULL),
(17, 1, NULL, 'PAY-3NU3597856627884BK56KZ3Q', 'authorize', 'credit_card', 'USD', 1, 2, 1, '2016-07-06 12:47:10', 'This is the payment transaction description.', NULL),
(18, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-0LW841745M851223KK56LZFY', 'authorize', 'credit_card', 'USD', 1, 2, 1, '2016-07-06 13:54:06', 'This is the payment transaction description.', NULL),
(19, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-0734992446509022HK56MHYA', 'authorize', 'credit_card', 'USD', 1, 2, 1, '2016-07-06 14:25:04', 'This is the payment transaction description.', NULL),
(20, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-10K079686U643825TK56M5FA', 'authorize', 'credit_card', 'USD', 1, 2, 1, '2016-07-06 15:10:50', 'This is the payment transaction description.', NULL),
(21, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-169071147X1536603K57AKWI', 'authorize', 'credit_card', 'USD', 1, 2, 1, '2016-07-07 13:16:42', 'This is the payment transaction description.', NULL),
(22, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-8UD22080KW8115343K57AXTY', 'sale', 'credit_card', 'USD', 1, 2, 1, '2016-07-07 13:44:24', 'This is the payment transaction description.', NULL),
(23, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-4L484262X3578940YK57A3NA', 'sale', 'credit_card', 'USD', 1, 2, 1, '2016-07-07 13:52:26', 'This is the payment transaction description.', NULL),
(24, 1, 'CARD-1A754479Y9809451RK55T4BI', 'PAY-77W80707DC253952JK57DWTY', 'sale', 'credit_card', 'USD', 1, 2, 1, '2016-07-07 17:06:56', 'This is the payment transaction description.', NULL);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;
--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `customer_credit_card`
--
ALTER TABLE `customer_credit_card`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;