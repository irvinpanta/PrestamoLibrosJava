-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 30-10-2019 a las 16:48:52
-- Versión del servidor: 10.1.40-MariaDB
-- Versión de PHP: 7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `prestamolibros`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaEliminar` (`_codCategoria` INT)  begin
	update categoria 
    set
        Estado = 0
	where CodCategoria = _codCategoria;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaGetCodigo` (IN `_nombre` VARCHAR(50))  BEGIN
	select CodCategoria from categoria Where NomBCategoria = _nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaGrabar` (`_nomCategoria` VARCHAR(100), `_descripCategoria` VARCHAR(100))  begin
	insert into categoria values(null, _nomCategoria, _descripCategoria, 1);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaLoadAll` ()  begin
	select * from categoria;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaLoadAllEstado` ()  begin
		select * from categoria where Estado = 1;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_CategoriaModificar` (`_codCategoria` INT, `_nomCategoria` VARCHAR(100), `_descripCategoria` VARCHAR(100), `_estado` TINYINT)  begin
	update categoria 
    set
		NomBCategoria =  _nomCategoria,
        DescripCategoria = _descripCategoria,
        Estado = _estado
	where CodCategoria = _codCategoria;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_ClientesEliminar` (`_codCliente` INT)  begin
	DECLARE error int;
    start transaction;
    
				update clientes
				set 
					Estado = 0 
				where CodCliente = _codCliente;
			
				update telefonos 
				set 
					Estado = 0
				where CodCliente =_codCliente;
				
				update email
				set 
					Estado = 0
				where CodCliente = _codCliente;
		
        
    SET error=(SELECT @error);
    IF(error = 0) then
		ROLLBACK;
    ELSE
		COMMIT;
    END IF;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_ClientesGrabar` (`_dni` CHAR(8), `_nombres` VARCHAR(50), `_apellidos` VARCHAR(100), `_direccion` VARCHAR(200), `_fechaNac` DATE, `_numero` CHAR(9), `_email` VARCHAR(100))  begin
	DECLARE error int;
    start transaction;
		
        insert into clientes values(null, _dni, _nombres, _apellidos, _direccion, _fechaNac, 1);
		SET @idClientes := last_insert_id();
        CALL pa_TelefonosGrabar(_numero, @idClientes);
        CALL pa_EmailGrabar(_email, @idClientes);
        
    SET error=(SELECT @error);
    IF(error = 0) then
		ROLLBACK;
    ELSE
		COMMIT;
    END IF;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_ClientesLoadAll` ()  begin
	select C.CodCliente, C.Dni, C.Nombres, C.Apellidos, C.Direccion, C.FechaNac, T.Numero, E.Email, C.Estado from clientes C
JOIN telefonos T ON T.CodCliente = C.CodCliente
JOIN email E ON E.CodCliente = C.CodCliente
group by C.CodCliente;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_ClientesModificar` (`_codCliente` INT, `_dni` CHAR(8), `_nombres` VARCHAR(50), `_apellidos` VARCHAR(100), `_direccion` VARCHAR(200), `_fechaNac` DATE, `_numero` CHAR(9), `_email` VARCHAR(100), `_estado` TINYINT)  begin
	DECLARE error int;
    start transaction;
		update clientes
        set 
			Dni = _dni,
            Nombres = _nombres,
            Apellidos = _apellidos,
            Direccion = _direccion,
            FechaNac = _fechaNac, 
            Estado = _estado 
		where CodCliente = _codCliente;

        CALL pa_TelefonosModificar(_numero, _codCliente, _estado);
        CALL pa_EmailModificar(_email, _codCliente, _estado);
        
    SET error=(SELECT @error);
    IF(error = 0) then
		ROLLBACK;
    ELSE
		COMMIT;
    END IF;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_EmailGrabar` (`_email` VARCHAR(100), `_codCliente` INT)  begin
	insert into email values(null, _email, 1, _codCliente);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_EmailModificar` (`_email` VARCHAR(100), `_codCliente` INT, `_estado` TINYINT)  begin
	update email
    set 
		Email = _email,
        Estado = _estado
	where CodCliente = _codCliente;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibroObtenerCodigo` (`_codLibro` INT)  begin
	select CodLibro from prestamo
	where CodLibro = _codLibro and Estado = 'Prestado';
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibrosEliminar` (`_codLibro` INT)  begin
	update libros
    set
        Estado = 0
	where CodLibro = _codLibro;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibrosGrabar` (`_ISBN` VARCHAR(50), `_tituloLibro` VARCHAR(100), `_autorLibro` VARCHAR(100), `_fecha` DATE, `_editorialLibro` VARCHAR(100), `_codCategoria` INT)  begin
	insert into libros
    values(null, _ISBN, _tituloLibro, _autorLibro, _fecha, _editorialLibro, _codCategoria, 1);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibrosLoadAll` ()  begin
	select l.CodLibro, l.ISBN, l.TituloLibro, l.AutorLibro, l.Fecha, l.EditorialLibro, c.NomBCategoria, l.Estado from libros l
    join categoria c on c.CodCategoria = l.CodCategoria;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibrosLoadAllEstado` (`_estado` TINYINT)  begin
	if _estado = 0 then
		select * from libros where Estado = 0;
    elseif _Estado = 1 then
		select * from libros where Estado = 1;
    end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_LibrosModificar` (`_codLibro` INT, `_ISBN` VARCHAR(50), `_tituloLibro` VARCHAR(100), `_autorLibro` VARCHAR(100), `_fecha` DATE, `_editorialLibro` VARCHAR(100), `_codCategoria` INT, `_estado` TINYINT)  begin
	update libros
    set
		ISBN = _ISBN,
        TituloLibro = _tituloLibro,
        AutorLibro = _autorLibro,
        Fecha = _fecha,
        EditorialLibro = _editorialLibro,
        CodCategoria = _codCategoria,
        Estado = _estado
	where CodLibro = _codLibro;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_Login` (`_usuario` VARCHAR(15), `_pass` VARCHAR(15))  BEGIN
	SELECT Usuario, Pass FROM usuarios WHERE Usuario = _usuario AND Pass = _pass and Estado = 1;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoDevolucionGrabar` (`_codPrestamo` INT)  begin
	update prestamo
    set
		Estado = 'Devuelto'
	where CodPrestamo = _codPrestamo;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoEliminar` (`_codPrestamo` INT)  begin
	update prestamo
    set 
		Estado = 'Anulado'
    where CodPrestamo = _codPrestamo;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoGrabar` (`_fechaEntrega` DATE, `_fechaDevolucion` DATE, `_dias` INT, `_codLibro` INT, `_codCliente` INT, `_codUsuario` INT)  begin
	declare _contar int;
    
    set _contar = (select count(*) from prestamo where CodCliente = _codCliente and Estado = 'Prestado');
    
    if _contar = 0 then
		begin
			insert into prestamo 
			values(null, _fechaEntrega, _fechaDevolucion, _dias, _codLibro, _codCliente, _codUsuario, 'Prestado');
		end;
	end if;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoLoadAll` ()  begin
	select p.CodPrestamo ,p.FechaPrestamo, p.FechaDevolucion, p.Dias, l.CodLibro, l.TituloLibro, c.CodCliente, concat(c.Dni, ' - ', c.Nombres, ' ', c.Apellidos), concat(u.Nombre, ' ', u.Apellidos), p.Estado from prestamo p
join libros l on l.CodLibro = p.CodLibro
join clientes c on c.CodCliente = p.CodCliente
join usuarios u on u.CodUsuario = p.CodUsuario;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoLoadAllPendiente` ()  begin
	select p.CodPrestamo ,p.FechaPrestamo, p.FechaDevolucion, p.Dias, l.CodLibro, l.TituloLibro, c.CodCliente, concat(c.Dni, ' - ', c.Nombres, ' ', c.Apellidos), concat(u.Nombre, ' ', u.Apellidos), p.Estado from prestamo p
	join libros l on l.CodLibro = p.CodLibro
	join clientes c on c.CodCliente = p.CodCliente
	join usuarios u on u.CodUsuario = p.CodUsuario
    where p.Estado = "Prestado";
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoModificar` (`_codPrestamo` INT, `_fechaEntrega` DATE, `_fechaDevolucion` DATE, `_dias` INT, `_codLibro` INT, `_codCliente` INT, `_codUsuario` INT)  begin
	update prestamo 
    set
		FechaPrestamo =  _fechaEntrega,
        FechaDevolucion = _fechaDevolucion,
        Dias = _dias,
        CodLibro = _codLibro,
        CodCliente = _codCliente,
        CodUsuario = _codUsuario
	where CodPrestamo = _codPrestamo;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoObtenerCodCliente` (`_codCliente` INT)  begin
	select CodCliente from prestamo
	where codCliente = _codCliente and Estado = 'Prestado';
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_PrestamoObtenerEstadoprestamo` (`_codPrestamo` INT)  begin
	select count(Estado) from prestamo
	where CodPrestamo = _codPrestamo and Estado = 'Devuelto';
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_TelefonosGrabar` (`_numero` CHAR(9), `_codCliente` INT)  begin
	insert into telefonos values(null, _numero, 1, _codCliente);
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pa_TelefonosModificar` (`_numero` CHAR(9), `_codCliente` INT, `_estado` TINYINT)  begin
	update telefonos 
    set 
		Numero = _numero, 
        Estado = _estado
	where CodCliente =_codCliente;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `CodCategoria` int(11) NOT NULL,
  `NomBCategoria` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `DescripCategoria` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Estado` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`CodCategoria`, `NomBCategoria`, `DescripCategoria`, `Estado`) VALUES
(6, 'NO ESPECIFICADO', 'NN.EE', 1),
(7, 'ALIENS', 'AL', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `CodCliente` int(11) NOT NULL,
  `Dni` char(8) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Nombres` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Apellidos` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Direccion` varchar(200) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `FechaNac` date DEFAULT NULL,
  `Estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`CodCliente`, `Dni`, `Nombres`, `Apellidos`, `Direccion`, `FechaNac`, `Estado`) VALUES
(5, '75831042', 'IRVIN POOL', 'PANTA GARCIA', 'SULLANA', '2000-01-01', 1),
(6, '12345678', 'EDSON', 'PANTA GARCIA', 'SULLANA', '2000-01-01', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `email`
--

CREATE TABLE `email` (
  `CodEmail` int(11) NOT NULL,
  `Email` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Estado` tinyint(4) DEFAULT NULL,
  `CodCliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `email`
--

INSERT INTO `email` (`CodEmail`, `Email`, `Estado`, `CodCliente`) VALUES
(25, 'irvinpanta96@gmail.com', 1, 5),
(26, '', 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `CodLibro` int(11) NOT NULL,
  `ISBN` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `TituloLibro` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `AutorLibro` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `Fecha` datetime DEFAULT NULL,
  `EditorialLibro` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `CodCategoria` int(11) DEFAULT NULL,
  `Estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`CodLibro`, `ISBN`, `TituloLibro`, `AutorLibro`, `Fecha`, `EditorialLibro`, `CodCategoria`, `Estado`) VALUES
(4, '30301', 'EL POEMA 20', 'ANONIMO', '2000-01-01 00:00:00', 'NO CONOCIDO', 6, 1),
(6, '30302', 'LOS HERALDOS NEGROS', 'ANONIMO', '2000-01-01 00:00:00', 'NO CONOCIDO', 6, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `CodPrestamo` int(11) NOT NULL,
  `FechaPrestamo` date DEFAULT NULL,
  `FechaDevolucion` date DEFAULT NULL,
  `Dias` int(11) DEFAULT NULL,
  `CodLibro` int(11) DEFAULT NULL,
  `CodCliente` int(11) DEFAULT NULL,
  `CodUsuario` int(11) DEFAULT NULL,
  `Estado` varchar(15) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`CodPrestamo`, `FechaPrestamo`, `FechaDevolucion`, `Dias`, `CodLibro`, `CodCliente`, `CodUsuario`, `Estado`) VALUES
(11, '2019-10-22', '2019-10-24', 2, 4, 6, 1, 'Devuelto'),
(12, '2019-10-22', '2019-10-23', 1, 6, 5, 1, 'Devuelto'),
(13, '2019-10-22', '2019-10-26', 4, 6, 6, 1, 'Devuelto'),
(14, '2019-10-22', '2019-10-26', 4, 4, 5, 1, 'Devuelto'),
(15, '2019-10-24', '2019-10-29', 5, 4, 6, 1, 'Anulado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

CREATE TABLE `telefonos` (
  `CodTelefono` int(11) NOT NULL,
  `Numero` char(9) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `Estado` tinyint(4) DEFAULT NULL,
  `CodCliente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `telefonos`
--

INSERT INTO `telefonos` (`CodTelefono`, `Numero`, `Estado`, `CodCliente`) VALUES
(29, '910815819', 1, 5),
(30, '', 1, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `CodUsuario` int(11) NOT NULL,
  `Nombre` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `Apellidos` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `Usuario` char(15) COLLATE utf8_spanish2_ci NOT NULL,
  `Pass` char(15) COLLATE utf8_spanish2_ci NOT NULL,
  `Estado` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`CodUsuario`, `Nombre`, `Apellidos`, `Usuario`, `Pass`, `Estado`) VALUES
(1, 'EDSON', 'PANTA', 'admin', 'admin', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`CodCategoria`),
  ADD UNIQUE KEY `NomBCategoria` (`NomBCategoria`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`CodCliente`),
  ADD UNIQUE KEY `Dni` (`Dni`);

--
-- Indices de la tabla `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`CodEmail`),
  ADD KEY `FK_EMAIL_CLIENTE` (`CodCliente`);

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`CodLibro`),
  ADD KEY `FK_LIBROS_CATEGORIA` (`CodCategoria`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`CodPrestamo`),
  ADD KEY `FK_PRESTAMO_CLIENTE` (`CodCliente`),
  ADD KEY `FK_PRESTAMO_LIBROS` (`CodLibro`),
  ADD KEY `FK_PRESTAMO_USUARIOS` (`CodUsuario`);

--
-- Indices de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`CodTelefono`),
  ADD KEY `FK_TELEFONOS_CLIENTE` (`CodCliente`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`CodUsuario`),
  ADD UNIQUE KEY `Usuario` (`Usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `CodCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `CodCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `email`
--
ALTER TABLE `email`
  MODIFY `CodEmail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `CodLibro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `CodPrestamo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `CodTelefono` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `CodUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `email`
--
ALTER TABLE `email`
  ADD CONSTRAINT `FK_EMAIL_CLIENTE` FOREIGN KEY (`CodCliente`) REFERENCES `clientes` (`CodCliente`) ON DELETE CASCADE;

--
-- Filtros para la tabla `libros`
--
ALTER TABLE `libros`
  ADD CONSTRAINT `FK_LIBROS_CATEGORIA` FOREIGN KEY (`CodCategoria`) REFERENCES `categoria` (`CodCategoria`);

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `FK_PRESTAMO_CLIENTE` FOREIGN KEY (`CodCliente`) REFERENCES `clientes` (`CodCliente`),
  ADD CONSTRAINT `FK_PRESTAMO_LIBROS` FOREIGN KEY (`CodLibro`) REFERENCES `libros` (`CodLibro`),
  ADD CONSTRAINT `FK_PRESTAMO_USUARIOS` FOREIGN KEY (`CodUsuario`) REFERENCES `usuarios` (`CodUsuario`);

--
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `FK_TELEFONOS_CLIENTE` FOREIGN KEY (`CodCliente`) REFERENCES `clientes` (`CodCliente`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
