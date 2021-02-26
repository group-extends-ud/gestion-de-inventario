--productos
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Arroz', 1200.00, 10, 15);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Natilla de la abuela', 2000.0, 0, 5);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Cerveza importada', 5400.0, 90, 60);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Cerveza nacional', 3100.0, 120,60);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Botella de vino', 30100.0, 10, 5);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Agua', 2700.0, 10, 5);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Lechuga', 2500.0, 5, 3);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Cebollas', 2900.0, 5, 3);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Patatas', 2500, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Tomates', 3600.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Naranjas', 3500.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Plátanos', 2700.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Manzanas', 7000.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Ternera', 16400.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Queso fresco', 11600.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Huevos (docena)', 5200.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Arroz (1Kg)', 3300.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Pan (1kg)', 3000.0, 15, 10);
INSERT INTO producto(nombre, precio, stock, stockMinimo) VALUES('Leche', 2600.0, 15, 10);

--clientes
INSERT INTO cliente(nombre, apellido) VALUES('DEFAULT', 'N/A');
--facturas
INSERT INTO factura(IDCliente) VALUES(1);
INSERT INTO facturaproducto(Cantidad, valor, IDFactura, IDProducto) VALUES(1, 1200, 1, 1);

--usuarios
INSERT INTO usuario(UserName, Pass, IsAdmin) VALUES('admin', 1234, true);
INSERT INTO usuario(UserName, Pass, IsAdmin) VALUES('employee', 1234, false);