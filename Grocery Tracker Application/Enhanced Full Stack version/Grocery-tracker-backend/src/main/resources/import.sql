-- Inserting sample categories
INSERT INTO categories (name) VALUES ('Dairy');
INSERT INTO categories (name) VALUES ('Meat');
INSERT INTO categories (name) VALUES ('Produce');
INSERT INTO categories (name) VALUES ('Baby');
INSERT INTO categories (name) VALUES ('Household');
INSERT INTO categories (name) VALUES ('Breakfast');

-- Inserting sample locations
INSERT INTO locations (name, address, city, state) VALUES ('Store A', '123 Main St', 'Los Angeles', 'CA');
INSERT INTO locations (name, address, city, state) VALUES ('Store B', '456 Oak St', 'New York', 'NY');
INSERT INTO locations (name, address, city, state) VALUES ('Store C', '789 Pine St', 'Chicago', 'IL');
INSERT INTO locations (name, address, city, state) VALUES ('Store D', '789 Riverside St', 'Miami', 'FL');
INSERT INTO locations (name, address, city, state) VALUES ('Store E', '789 BayHill St', 'Atlanta', 'GA');

-- Insert Products
INSERT INTO products (product_name, product_price, stock_quantity, category_id, location_id) VALUES
('Milk', 3.99, 50, (SELECT id FROM categories WHERE name = 'Dairy'), (SELECT id FROM locations WHERE name = 'Store A')),
('Chicken breast', 9.99, 30, (SELECT id FROM categories WHERE name = 'Meat'), (SELECT id FROM locations WHERE name = 'Store B')),
('Sirloin steak', 14.99, 30, (SELECT id FROM categories WHERE name = 'Meat'), (SELECT id FROM locations WHERE name = 'Store B')),
('Apple', 0.50, 100, (SELECT id FROM categories WHERE name = 'Produce'), (SELECT id FROM locations WHERE name = 'Store A')),
('Banana', 0.30, 100, (SELECT id FROM categories WHERE name = 'Produce'), (SELECT id FROM locations WHERE name = 'Store D')),
('Pineapple', 1.50, 100, (SELECT id FROM categories WHERE name = 'Produce'), (SELECT id FROM locations WHERE name = 'Store A')),
('Mango', 0.50, 120, (SELECT id FROM categories WHERE name = 'Produce'), (SELECT id FROM locations WHERE name = 'Store A')),
('Yogurt', 1.99, 100, (SELECT id FROM categories WHERE name = 'Dairy'), (SELECT id FROM locations WHERE name = 'Store C')),
('Cheese', 3.99, 130, (SELECT id FROM categories WHERE name = 'Dairy'), (SELECT id FROM locations WHERE name = 'Store C')),
('Baby wipes', 4.99, 100, (SELECT id FROM categories WHERE name = 'Baby'), (SELECT id FROM locations WHERE name = 'Store D')),
('Cereal', 3.85, 100, (SELECT id FROM categories WHERE name = 'Breakfast'), (SELECT id FROM locations WHERE name = 'Store A')),
('Coffee', 14.99, 200, (SELECT id FROM categories WHERE name = 'Breakfast'), (SELECT id FROM locations WHERE name = 'Store C')),
('Bread', 4.25, 38, (SELECT id FROM categories WHERE name = 'Breakfast'), (SELECT id FROM locations WHERE name = 'Store E')),
('Diapers', 19.99, 40, (SELECT id FROM categories WHERE name = 'Baby'), (SELECT id FROM locations WHERE name = 'Store C')),
('Detergent', 7.49, 20, (SELECT id FROM categories WHERE name = 'Household'), (SELECT id FROM locations WHERE name = 'Store E'));

-- Insert Sales Records
INSERT INTO sales (product_id, sale_date, quantity_sold, total_amount) VALUES
((SELECT product_id FROM products WHERE product_name = 'Milk'), '2024-10-01', 10, 39.90),
((SELECT product_id FROM products WHERE product_name = 'Chicken Breast'), '2024-10-01', 5, 49.95),
((SELECT product_id FROM products WHERE product_name = 'Apple'), '2024-10-01', 20, 10.00),
((SELECT product_id FROM products WHERE product_name = 'Mango'), '2024-10-01', 10, 5.00),
((SELECT product_id FROM products WHERE product_name = 'Baby wipes'), '2024-10-02', 20, 99.80),
((SELECT product_id FROM products WHERE product_name = 'Diapers'), '2024-10-02', 13, 259.87),
((SELECT product_id FROM products WHERE product_name = 'Detergent'), '2024-10-03', 2, 14.98),
((SELECT product_id FROM products WHERE product_name = 'Coffee'), '2024-10-03', 21, 314.79);