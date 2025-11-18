-- Crear tabla para las Comunidades Autónomas de España
CREATE TABLE IF NOT EXISTS regions (
id INT AUTO_INCREMENT PRIMARY KEY,
code VARCHAR(10) NOT NULL UNIQUE,
name VARCHAR(100) NOT NULL,
image VARCHAR(100)
);

-- Crear tabla de provincias
CREATE TABLE IF NOT EXISTS provinces (
id INT AUTO_INCREMENT PRIMARY KEY,
code VARCHAR(10) NOT NULL UNIQUE,
name VARCHAR(100) NOT NULL
);

-- Crear la tabla 'tickets'
CREATE TABLE IF NOT EXISTS tickets (
 id INT AUTO_INCREMENT PRIMARY KEY,
 date DATETIME NOT NULL,
 discount DECIMAL(5, 2) NOT NULL,
 location_id INT
);

-- Crear la tabla 'products'
CREATE TABLE IF NOT EXISTS products (
 id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(255) NOT NULL,
 price DECIMAL(10, 2) NOT NULL
);

-- Crear la tabla 'product_ticket'
CREATE TABLE IF NOT EXISTS product_ticket (
 product_id INT NOT NULL,
 ticket_id INT NOT NULL,
 PRIMARY KEY (product_id, ticket_id),
 FOREIGN KEY (product_id) REFERENCES products(id),
 FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);
