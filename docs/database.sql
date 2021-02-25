
/* Drop Tables */

DROP TABLE IF EXISTS Cliente CASCADE
;

DROP TABLE IF EXISTS Factura CASCADE
;

DROP TABLE IF EXISTS FacturaProducto CASCADE
;

DROP TABLE IF EXISTS Producto CASCADE
;

DROP TABLE IF EXISTS Usuario CASCADE
;

/* Create Tables */

CREATE TABLE Cliente
(
	IDCliente SERIAL NOT NULL,
	Nombre varchar(50) NOT NULL,
	Apellido varchar(50) NOT NULL
)
;

CREATE TABLE Factura
(
	IDFactura SERIAL NOT NULL,
	Fecha timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	IDCliente integer NULL
)
;

CREATE TABLE FacturaProducto
(
	Cantidad integer NOT NULL,
	valor money NOT NULL,
	IDFactura integer NOT NULL,
	IDProducto integer NOT NULL
)
;

CREATE TABLE Producto
(
	IDProducto SERIAL NOT NULL,
	Nombre varchar(50) NOT NULL,
	Precio money NOT NULL,
	Stock integer NOT NULL,
	StockMinimo integer NOT NULL
)
;

CREATE TABLE Usuario
(
	UserName varchar(50) NOT NULL,
	Pass varchar(50) NOT NULL,
	IsAdmin boolean NOT NULL DEFAULT FALSE
)
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE Cliente ADD CONSTRAINT PK_Cliente
	PRIMARY KEY (IDCliente)
;

ALTER TABLE Factura ADD CONSTRAINT PK_Factura
	PRIMARY KEY (IDFactura)
;

CREATE INDEX IXFK_Factura_Cliente ON Factura (IDCliente ASC)
;

ALTER TABLE FacturaProducto ADD CONSTRAINT PK_FacturaProducto
	PRIMARY KEY (IDFactura,IDProducto)
;

CREATE INDEX IXFK_FacturaProducto_Factura ON FacturaProducto (IDFactura ASC)
;

CREATE INDEX IXFK_FacturaProducto_Producto ON FacturaProducto (IDProducto ASC)
;

ALTER TABLE Producto ADD CONSTRAINT PK_Producto
	PRIMARY KEY (IDProducto)
;

ALTER TABLE Usuario ADD CONSTRAINT PK_Usuario
	PRIMARY KEY (UserName)
;

/* Create Foreign Key Constraints */

ALTER TABLE Factura ADD CONSTRAINT FK_Factura_Cliente
	FOREIGN KEY (IDCliente) REFERENCES Cliente (IDCliente) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE FacturaProducto ADD CONSTRAINT FK_FacturaProducto_Factura
	FOREIGN KEY (IDFactura) REFERENCES Factura (IDFactura) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE FacturaProducto ADD CONSTRAINT FK_FacturaProducto_Producto
	FOREIGN KEY (IDProducto) REFERENCES Producto (IDProducto) ON DELETE No Action ON UPDATE No Action
;

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE Cliente
	IS 'Tabla que representa los datos basicos del cliente en cuestion'
;

COMMENT ON TABLE Factura
	IS 'Tabla que representa la factura y almacena la fecha'
;

COMMENT ON TABLE FacturaProducto
	IS 'Tabla de rompiento con los detalles de la factura'
;

COMMENT ON TABLE Producto
	IS 'Tabla donde se almacenan todos los productos disponibles en la tienda'
;

COMMENT ON TABLE Cliente
	IS 'Tabla que almacena los usuarios Empleado o Administrador de la tienda'
;