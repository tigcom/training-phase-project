
CREATE
DATABASE IF NOT EXISTS user_service_db;
USE
user_service_db;

CREATE TABLE users (
   username    VARCHAR(100) PRIMARY KEY,
   password    VARCHAR(255) NOT NULL,
   full_name   VARCHAR(100) NOT NULL,
   email       VARCHAR(100) NOT NULL,
   enabled     BOOLEAN DEFAULT TRUE,
   address     VARCHAR(100) NOT NULL,
   gender      BOOLEAN NOT NULL,
   image       VARCHAR(100),
   created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   date_updated TIMESTAMP
);
INSERT INTO users (username, password, full_name, email, enabled, address, gender, image, created_at, date_updated)
VALUES
    ('user01', '$2a$10$OS3VTEhd5PtjO0/lswdKLuw.EUsQ0cmda9sy8xwWiuFoVNe.4pkVy', 'Nguyễn Văn A',
     'a.nguyen@example.com', TRUE, '123 Lê Lợi, Q1', TRUE, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user02', '$2a$10$OS3VTEhd5PtjO0/lswdKLuw.EUsQ0cmda9sy8xwWiuFoVNe.4pkVy', 'Trần Thị B',
     'b.tran@example.com', TRUE, '456 Nguyễn Trãi, Q5', FALSE, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user03', '$2a$10$OS3VTEhd5PtjO0/lswdKLuw.EUsQ0cmda9sy8xwWiuFoVNe.4pkVy', 'Lê Văn C',
     'c.le@example.com', FALSE, '789 Cách Mạng Tháng 8, Q10', TRUE, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

CREATE TABLE authorities
(
    username  VARCHAR(100) NOT NULL,
    authority VARCHAR(50)  NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username)
);


INSERT INTO authorities(username, authority)
VALUES ('user01', 'ROLE_ADMIN'),
       ('user02', 'ROLE_USER'),
       ('user03', 'ROLE_USER');


CREATE
DATABASE IF NOT EXISTS product_service_db;
USE
product_service_db;

CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    stock       INT            NOT NULL
);

INSERT INTO products (name, description, price, stock)
VALUES ('iPhone 15', 'Điện thoại Apple mới nhất', 24990000, 50),
       ('MacBook Air M2', 'Laptop Apple M2 13 inch', 29990000, 20),
       ('Chuột Logitech MX Master 3S', 'Chuột không dây cao cấp', 2490000, 100),
       ('Tai nghe Sony WH-1000XM5', 'Tai nghe chống ồn', 7490000, 30);

CREATE
DATABASE IF NOT EXISTS order_service_db;
USE
order_service_db;

CREATE TABLE orders
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      username VARCHAR(100) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

INSERT INTO orders (user_id, total_amount)
VALUES ('user01', 32480000);


INSERT INTO order_items (order_id, product_id, quantity, price)
VALUES (1, 1, 1, 24990000),
       (1, 3, 3, 2490000);
