

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
                       password VARCHAR(100) NOT NULL
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
