

CREATE TABLE IF NOT EXISTS WINE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    type VARCHAR(50),
    vintage VARCHAR(50),
    country VARCHAR(50),
    winery VARCHAR(100),
    price DECIMAL(10, 2),
    quantity INT,
    imageUrl VARCHAR(255)
    );


CREATE TABLE USERS (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       address VARCHAR(255),
                       country VARCHAR(50),
                       city VARCHAR(50)
);

CREATE TABLE ROLE (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE USER_ROLE (
                           user_id INT,
                           role_id INT,
                           FOREIGN KEY (user_id) REFERENCES USERS(id),
                           FOREIGN KEY (role_id) REFERENCES ROLE(id),
                           PRIMARY KEY (user_id, role_id)
);
CREATE TABLE cart (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      session_id VARCHAR(255) UNIQUE
);

-- Table for Cart Items
CREATE TABLE cart_item (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           cart_id INT,
                           wine_id INT,
                           quantity INT,
                           FOREIGN KEY (cart_id) REFERENCES cart(id),
                           FOREIGN KEY (wine_id) REFERENCES wine(id)
);

-- Table for Orders
-- Table for Orders
CREATE TABLE orders (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        cart_id INT,
                        total_price DECIMAL(10, 2),
                        order_date TIMESTAMP,
                        payment_method VARCHAR(50),
                        user_id INT,
                        FOREIGN KEY (cart_id) REFERENCES cart(id),
                        FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE login_logs (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(255) NOT NULL,
                            login_time TIMESTAMP NOT NULL,
                            ip_address VARCHAR(45) NOT NULL
);
