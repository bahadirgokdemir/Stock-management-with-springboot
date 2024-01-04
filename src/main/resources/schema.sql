DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS order_details CASCADE;
DROP TABLE IF EXISTS orders CASCADE;


CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(50),
                       company_name VARCHAR(255),
                       UNIQUE (username),
                       UNIQUE (email)
);

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            total_stock INT DEFAULT 0
);


CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          category_id BIGINT REFERENCES categories(id),
                          stock_quantity INT NOT NULL,
                          price DECIMAL(10,2) NOT NULL
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT REFERENCES users(id),
                        order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(50) NOT NULL,
                        total_sales INT DEFAULT 0,
                        total_revenue DECIMAL(10,2) DEFAULT 0.00,
                        delivery_company VARCHAR(255),
                        delivery_date DATE
);

CREATE TABLE order_details (
                               id BIGSERIAL PRIMARY KEY,
                               order_id BIGINT REFERENCES orders(id),
                               product_id BIGINT REFERENCES products(id),
                               quantity INT NOT NULL,
                               price_per_unit DECIMAL(10, 2),
                               total_revenue DECIMAL(10,2) DEFAULT 0.00,
                               delivery_company VARCHAR(255),
                               delivery_date DATE,
                               category VARCHAR(255),
                               status BOOLEAN
);


