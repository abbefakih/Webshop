    CREATE DATABASE IF NOT EXISTS webshop;

    USE webshop;

    DROP TABLE IF EXISTS order_items;
    DROP TABLE IF EXISTS orders;
    DROP TABLE IF EXISTS products;
    DROP TABLE IF EXISTS categories;
    DROP TABLE IF EXISTS users;
    -- =========================================================
    -- USERS
    -- =========================================================
    CREATE TABLE users (
                           id INT AUTO_INCREMENT PRIMARY KEY,

                           username VARCHAR(50) NOT NULL UNIQUE,

                           password VARCHAR(100) NOT NULL,

                           role VARCHAR(20) NOT NULL,

                           CONSTRAINT chk_user_role
                               CHECK (role IN ('CUSTOMER', 'ADMIN', 'WAREHOUSE'))
    );
    -- =========================================================
    -- CATEGORIES
    -- =========================================================

    CREATE TABLE categories (
                                id INT AUTO_INCREMENT PRIMARY KEY,

                                name VARCHAR(100) NOT NULL UNIQUE,

                                description VARCHAR(255)
    );
    -- =========================================================
    -- PRODUCTS
    -- =========================================================

    CREATE TABLE products (
                              id INT AUTO_INCREMENT PRIMARY KEY,

                              name VARCHAR(100) NOT NULL,

                              price DECIMAL(10,2) NOT NULL,

                              stock INT NOT NULL DEFAULT 0,

                              category_id INT NOT NULL,

                              CONSTRAINT fk_product_category
                                  FOREIGN KEY (category_id)
                                      REFERENCES categories(id),

                              CONSTRAINT chk_product_price
                                  CHECK (price >= 0),

                              CONSTRAINT chk_product_stock
                                  CHECK (stock >= 0)
    );
    -- =========================================================
    -- ORDERS
    -- =========================================================

    CREATE TABLE orders (
                            id INT AUTO_INCREMENT PRIMARY KEY,

                            user_id INT NOT NULL,

                            order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                            status VARCHAR(30) NOT NULL DEFAULT 'NEW',

                            CONSTRAINT fk_order_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id),

                            CONSTRAINT chk_order_status
                                CHECK (
                                    status IN (
                                               'NEW',
                                               'PROCESSING',
                                               'PACKED',
                                               'SHIPPED',
                                               'CANCELLED'
                                        )
                                    )
    );


    -- =========================================================
    -- ORDER ITEMS
    -- =========================================================

    CREATE TABLE order_items (
                                 id INT AUTO_INCREMENT PRIMARY KEY,

                                 order_id INT NOT NULL,

                                 product_id INT NOT NULL,

                                 quantity INT NOT NULL,

                                 price DECIMAL(10,2) NOT NULL,

                                 CONSTRAINT fk_order_item_order
                                     FOREIGN KEY (order_id)
                                         REFERENCES orders(id)
                                         ON DELETE CASCADE,

                                 CONSTRAINT fk_order_item_product
                                     FOREIGN KEY (product_id)
                                         REFERENCES products(id),

                                 CONSTRAINT chk_order_item_quantity
                                     CHECK (quantity > 0),

                                 CONSTRAINT chk_order_item_price
                                     CHECK (price >= 0)
    );
    -- =========================================================
    -- INSERT USERS
    -- =========================================================

    INSERT INTO users (username, password, role)
    VALUES
        ('admin', 'admin123', 'ADMIN'),
        ('warehouse', 'warehouse123', 'WAREHOUSE'),
        ('customer', 'customer123', 'CUSTOMER');


    -- =========================================================
    -- INSERT CATEGORIES
    -- =========================================================

    INSERT INTO categories (name, description)
    VALUES
        ('Laptops', 'Laptops and portable computers'),
        ('Phones', 'Smartphones and mobile devices'),
        ('Accessories', 'Computer and phone accessories');
    -- =========================================================
    -- INSERT PRODUCTS
    -- =========================================================

    INSERT INTO products (name, price, stock, category_id)
    VALUES
        ('MacBook Air', 12999.00, 10, 1),
        ('Dell Laptop', 8999.00, 7, 1),
        ('iPhone', 11999.00, 5, 2),
        ('Samsung Galaxy', 9999.00, 8, 2),
        ('Wireless Mouse', 299.00, 25, 3),
        ('Keyboard', 599.00, 15, 3);
    -- =========================================================
    -- TEST DATA
    -- =========================================================
    USE webshop;

    UPDATE webshop.users
    SET password = '1234',
        role = 'WAREHOUSE'
    WHERE username = 'warehouse';

    SELECT id, username, password, role
    FROM webshop.users;