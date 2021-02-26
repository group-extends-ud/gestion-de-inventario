--productos
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 1,'Arroz', 1200.00, 10, 15);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 2,'Natilla de la abuela', 2000.0, 0, 5);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 3,'Cerveza importada', 5400.0, 90, 60);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 4,'Cerveza nacional', 3100.0, 120,60);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 5,'Botella de vino', 30100.0, 10, 5);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 6,'Agua', 2700.0, 10, 5);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 7,'Lechuga', 2500.0, 5, 3);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 8,'Cebollas', 2900.0, 5, 3);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES( 9,'Patatas', 2500, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(10,'Tomates', 3600.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(11,'Naranjas', 3500.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(12,'Plátanos', 2700.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(13,'Manzanas', 7000.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(14,'Ternera', 16400.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(15,'Queso fresco', 11600.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(16,'Huevos (docena)', 5200.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(17,'Arroz (1Kg)', 3300.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(18,'Pan (1kg)', 3000.0, 15, 10);
INSERT INTO producto(IDProducto, nombre, precio, stock, stockMinimo) VALUES(19,'Leche', 2600.0, 15, 10);
--clientes
INSERT INTO cliente(idCliente, nombre, apellido) VALUES(1, 'DEFAULT', 'N/A');
--facturas

--usuarios
INSERT INTO usuario(UserName, Pass, IsAdmin) VALUES('admin', 1234, true);
INSERT INTO usuario(UserName, Pass, IsAdmin) VALUES('employee', 1234, false);