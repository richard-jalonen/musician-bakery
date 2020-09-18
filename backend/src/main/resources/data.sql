DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  product_name VARCHAR(250) NOT NULL,
  product_price DECIMAL,
  product_stock NUMERIC DEFAULT NULL,
  product_abbreviation CHAR
);

INSERT INTO products (product_name, product_price, product_stock, product_abbreviation) VALUES
  ('Brownie', 0.65, 48, 'B'),
  ('Muffin', 1.00, 36, 'M'),
  ('Cake Pop', 1.35, 24, 'C'),
  ('Water', 1.50, 30, 'W');