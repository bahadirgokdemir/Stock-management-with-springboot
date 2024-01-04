INSERT INTO users (username, password, email, phone_number, company_name) VALUES
                                                                              ('user1', 'pass1', 'user1@example.com', '1234567890', 'Company1'),
                                                                              ('user2', 'pass2', 'user2@example.com', '0987654321', 'Company2');

INSERT INTO categories (name, description) VALUES
                                               ('Elektronik', 'Elektronik cihazlar ve aksesuarlar'),
                                               ('Giyim', 'Giyim ürünleri ve aksesuarlar');

INSERT INTO products (name, category_id, stock_quantity, price) VALUES
                                                                    ('Laptop', (SELECT id FROM categories WHERE name = 'Elektronik'), 10,5500.00),
                                                                    ('T-Shirt', (SELECT id FROM categories WHERE name = 'Giyim'), 20, 60.00);

INSERT INTO orders (user_id, status) VALUES
                                         ((SELECT id FROM users WHERE username = 'user1'), 'Hazırlanıyor'),
                                         ((SELECT id FROM users WHERE username = 'user2'), 'Kargolandı');

INSERT INTO order_details (order_id, product_id, quantity, price_per_unit) VALUES
                                                                               ((SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'user1')), (SELECT id FROM products WHERE name = 'Laptop'), 1, 5000.00),
                                                                               ((SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE username = 'user2')), (SELECT id FROM products WHERE name = 'T-Shirt'), 2, 50.00);