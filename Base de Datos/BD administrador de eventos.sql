-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 01, 2013 at 05:22 PM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `admeventos`
--
CREATE DATABASE IF NOT EXISTS `admeventos` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `admeventos`;

-- --------------------------------------------------------

--
-- Table structure for table `cargos`
--

CREATE TABLE IF NOT EXISTS `cargos` (
  `id_cargo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_cargo` varchar(25) NOT NULL,
  PRIMARY KEY (`id_cargo`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `cargos`
--

INSERT INTO `cargos` (`id_cargo`, `nombre_cargo`) VALUES
(1, 'Adminitrador'),
(2, 'Portero');

-- --------------------------------------------------------

--
-- Table structure for table `eventos`
--

CREATE TABLE IF NOT EXISTS `eventos` (
  `id_evento` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_evento` varchar(50) NOT NULL,
  `ubicacion_evento` varchar(50) NOT NULL,
  `fecha_evento` date NOT NULL,
  `hora_evento` time NOT NULL,
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `eventos`
--

INSERT INTO `eventos` (`id_evento`, `nombre_evento`, `ubicacion_evento`, `fecha_evento`, `hora_evento`) VALUES
(1, 'Happy Birthday', 'ave. Maximo Gomez Hotel Lina', '2013-12-01', '17:00:00'),
(2, 'Concierto Ricardo Arjona', 'ave. Kennedy Mega Centro', '2013-12-05', '18:00:00'),
(3, 'Despedida Fin de Año', 'ave. Kennedy Centro Sambil', '2013-12-31', '23:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `invitados`
--

CREATE TABLE IF NOT EXISTS `invitados` (
  `id_invitado` int(11) NOT NULL AUTO_INCREMENT,
  `id_evento` int(11) NOT NULL,
  `nombre_invitado` varchar(30) NOT NULL,
  `apellido_invitado` varchar(30) NOT NULL,
  `telefono_invitado` char(10) NOT NULL,
  `direccion_invitado` varchar(70) NOT NULL,
  `sexo_invitado` char(1) NOT NULL,
  `asistencia_invitado` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_invitado`),
  KEY `gdsd_ss` (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=31 ;


-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `id_evento` int(11) NOT NULL,
  `id_cargo` int(11) NOT NULL,
  `nombre_usuario` varchar(25) NOT NULL,
  `apellido_usuario` varchar(25) NOT NULL,
  `usuario` varchar(15) NOT NULL,
  `clave_usuario` varchar(20) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `usuario` (`usuario`),
  KEY `fk_eeveveee` (`id_evento`),
  KEY `fk_fffff` (`id_cargo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;


-- --------------------------------------------------------

--
-- Table structure for table `visitas_imprevistas`
--

CREATE TABLE IF NOT EXISTS `visitas_imprevistas` (
  `id_imprevista` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_imprevista` varchar(30) NOT NULL,
  `apellido_imprevista` varchar(30) NOT NULL,
  `razon_imprevista` varchar(100) NOT NULL,
  `autorizante_imprevista` varchar(25) NOT NULL,
  PRIMARY KEY (`id_imprevista`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `visitas_invitados`
--

CREATE TABLE IF NOT EXISTS `visitas_invitados` (
  `id_visita` int(11) NOT NULL AUTO_INCREMENT,
  `id_invitado` int(11) NOT NULL,
  `nombre_visita` varchar(25) NOT NULL,
  `apellido_visita` varchar(25) NOT NULL,
  `telefono_visita` char(10) NOT NULL,
  `direccion_visita` varchar(50) NOT NULL,
  `sexo_visita` char(1) NOT NULL,
  PRIMARY KEY (`id_visita`),
  KEY `fk_ddd` (`id_invitado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `invitados`
--
ALTER TABLE `invitados`
  ADD CONSTRAINT `gdsd_ss` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_eeveveee` FOREIGN KEY (`id_evento`) REFERENCES `eventos` (`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_fffff` FOREIGN KEY (`id_cargo`) REFERENCES `cargos` (`id_cargo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `visitas_invitados`
--
ALTER TABLE `visitas_invitados`
  ADD CONSTRAINT `fk_ddd` FOREIGN KEY (`id_invitado`) REFERENCES `invitados` (`id_invitado`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
