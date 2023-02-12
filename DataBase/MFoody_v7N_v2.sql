-- dispose db
DROP DATABASE IF EXISTS MFoodyEx;

-- create db
CREATE DATABASE IF NOT EXISTS MFoodyEx;

-- use db
USE MFoodyEx;

START TRANSACTION;

-- create table USER_MFOODY
CREATE TABLE
    IF NOT EXISTS USER_MFOODY(
                                 ID_USER INT NOT NULL AUTO_INCREMENT,
                                 EMAIL_USER NVARCHAR(50) NOT NULL UNIQUE,
    PASSWORD_USER NVARCHAR(100) NOT NULL,
    NAME_USER NVARCHAR(50) NOT NULL,
    PHONE_NUMBER_USER NVARCHAR(20) NOT NULL UNIQUE,
    ADDRESS_USER NVARCHAR(100) NOT NULL,
    ROLE_USER NVARCHAR(20) NOT NULL,
    PRIMARY KEY (ID_USER)
    );

-- create table CREDIT_CARD_MFOODY
CREATE TABLE
    IF NOT EXISTS CREDIT_CARD_MFOODY(
                                        ID_CARD INT NOT NULL AUTO_INCREMENT,
                                        NAME_USER_CARD NVARCHAR(50) NOT NULL,
    NUMBER_CARD NVARCHAR(20) NOT NULL UNIQUE,
    EXPIRATION_CARD NVARCHAR(10) NOT NULL,
    SECURITY_CODE_CARD NVARCHAR(5) NOT NULL,
    ID_USER INT NOT NULL,
    PRIMARY KEY (ID_CARD),
    CONSTRAINT FK_CARD_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER)
    );

-- create table PRODUCT_MFOODY
CREATE TABLE
    IF NOT EXISTS PRODUCT_MFOODY(
                                    ID_PRODUCT INT NOT NULL AUTO_INCREMENT,
                                    NAME_PRODUCT NVARCHAR(100) NOT NULL UNIQUE,
    ALBUM_PRODUCT NVARCHAR(20) NOT NULL UNIQUE,
    DESCRIPTION_PRODUCT TEXT NOT NULL,
    SALE_PRICE_PRODUCT FLOAT NOT NULL,
    FULL_PRICE_PRODUCT FLOAT NOT NULL,
    WEIGHT_PRODUCT TEXT NOT NULL,
    IMPORT_QUANTITY_PRODUCT INT NOT NULL,
    IMPORT_DATE_PRODUCT NVARCHAR(20) NOT NULL,
    STOREHOUSE_QUANTITY_PRODUCT INT NOT NULL,
    RATING_PRODUCT FLOAT NOT NULL,
    CATEGORY_PRODUCT NVARCHAR(50) NOT NULL,
    BRAND_PRODUCT NVARCHAR(50),
    PRIMARY KEY (ID_PRODUCT)
    );

-- create table COMMENT_MFOODY
CREATE TABLE
    IF NOT EXISTS COMMENT_MFOODY(
                                    ID_COMMENT INT NOT NULL AUTO_INCREMENT,
                                    RATING_COMMENT INT NOT NULL,
                                    CONTENT_COMMENT TEXT,
                                    ID_USER INT NOT NULL,
                                    ID_PRODUCT INT NOT NULL,
                                    PRIMARY KEY (ID_COMMENT),
    CONSTRAINT FK_COMMENT_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER),
    CONSTRAINT FK_COMMENT_VS_PRODUCT FOREIGN KEY (ID_PRODUCT) REFERENCES PRODUCT_MFOODY(ID_PRODUCT)
    );

-- create table CART_MFOODY
CREATE TABLE
    IF NOT EXISTS CART_MFOODY(
                                 ID_CART INT NOT NULL AUTO_INCREMENT,
                                 QUANTITY_ALL_PRODUCTS_IN_CART INT NOT NULL,
                                 TOTAL_SALE_PRICE_CART FLOAT NOT NULL,
                                 TOTAL_FULL_PRICE_CART FLOAT NOT NULL,
                                 ID_USER INT NOT NULL UNIQUE,
                                 PRIMARY KEY (ID_CART),
    CONSTRAINT FK_CART_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER)
    );

-- create table DETAIL_PRODUCT_CART_MFOODY
CREATE TABLE
    IF NOT EXISTS DETAIL_PRODUCT_CART_MFOODY(
                                                ID_CART INT NOT NULL,
                                                ID_PRODUCT INT NOT NULL,
                                                QUANTITY_DETAIL_PRODUCT_CART INT NOT NULL,
                                                SALE_PRICE_DETAIL_PRODUCT_CART FLOAT NOT NULL,
                                                FULL_PRICE_DETAIL_PRODUCT_CART FLOAT NOT NULL,
                                                PRIMARY KEY (ID_CART, ID_PRODUCT),
    CONSTRAINT FK_DETAILPRODUCTCART_VS_CART FOREIGN KEY (ID_CART) REFERENCES CART_MFOODY(ID_CART),
    CONSTRAINT FK_DETAILPRODUCTCART_VS_PRODUCT FOREIGN KEY (ID_PRODUCT) REFERENCES PRODUCT_MFOODY(ID_PRODUCT)
    );

-- create table ORDER_MFOODY
CREATE TABLE
    IF NOT EXISTS ORDER_MFOODY(
                                  ID_ORDER INT NOT NULL AUTO_INCREMENT,
                                  DATE_ORDER NVARCHAR(20) NOT NULL,
    DATE_RECEIPT_ORDER NVARCHAR(20) NOT NULL,
    SHIPPING_PRICE_ORDER FLOAT NOT NULL,
    SHIPPING_METHOD_ORDER NVARCHAR(50) NOT NULL,
    QUANTITY_ALL_PRODUCTS_IN_ORDER INT NOT NULL,
    TOTAL_SALE_PRICE_ORDER FLOAT NOT NULL,
    TOTAL_FULL_PRICE_ORDER FLOAT NOT NULL,
    PAYMENT_METHOD_ORDER NVARCHAR(50) NOT NULL,
    STATUS_ORDER NVARCHAR(20) NOT NULL,
    ID_USER INT NOT NULL,
    PRIMARY KEY (ID_ORDER),
    CONSTRAINT FK_ORDER_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER)
    );

-- create table DETAIL_PRODUCT_ORDER_MFOODY
CREATE TABLE
    IF NOT EXISTS DETAIL_PRODUCT_ORDER_MFOODY(
                                                 ID_ORDER INT NOT NULL,
                                                 ID_PRODUCT INT NOT NULL,
                                                 QUANTITY_DETAIL_PRODUCT_ORDER INT NOT NULL,
                                                 SALE_PRICE_DETAIL_PRODUCT_ORDER FLOAT NOT NULL,
                                                 FULL_PRICE_DETAIL_PRODUCT_ORDER FLOAT NOT NULL,
                                                 PRIMARY KEY (ID_ORDER, ID_PRODUCT),
    CONSTRAINT FK_DETAILPRODUCTORDER_VS_ORDER FOREIGN KEY (ID_ORDER) REFERENCES ORDER_MFOODY(ID_ORDER),
    CONSTRAINT FK_DETAILPRODUCTORDER_VS_PRODUCT FOREIGN KEY (ID_PRODUCT) REFERENCES PRODUCT_MFOODY(ID_PRODUCT)
    );

-- create table FEEDBACK_MAIL
CREATE TABLE
    IF NOT EXISTS FEEDBACK_MAIL(
                                   ID_FEEDBACK_MAIL INT NOT NULL AUTO_INCREMENT,
                                   NAME_USER_FEEDBACK_MAIL NVARCHAR(50) NOT NULL,
    EMAIL_USER_FEEDBACK_MAIL NVARCHAR(50) NOT NULL,
    TITLE_FEEDBACK_MAIL NVARCHAR(20) NOT NULL,
    CONTENT_FEEDBACK_MAIL TEXT NOT NULL,
    PRIMARY KEY (ID_FEEDBACK_MAIL)
    );

-- insert data to FEEDNACK_MAIL
INSERT INTO
    FEEDBACK_MAIL (
    ID_FEEDBACK_MAIL,
    NAME_USER_FEEDBACK_MAIL,
    EMAIL_USER_FEEDBACK_MAIL,
    TITLE_FEEDBACK_MAIL,
    CONTENT_FEEDBACK_MAIL
)
VALUES (101230,'Peter Parker','peterparker@gmail.com', 'Error Service A', 'Function A does not work properly'),
       (101231,'Jame Dion','jamedion@gmail.com', 'Error Service B', 'Function B does not work properly'),
       (101232,'Justin Evan','justinevan@gmail.com', 'Error Service C', 'Function C does not work properly');

-- INSERT INTO
--     FEEDBACK_MAIL (
--         ID_FEEDBACK_MAIL,
--         NAME_USER_FEEDBACK_MAIL,
--         EMAIL_USER_FEEDBACK_MAIL,
--         TITLE_FEEDBACK_MAIL,
--         CONTENT_FEEDBACK_MAIL
--     )
-- VALUES
--         (101232,'Justin Evan','justinevan@gmail.com', 'Error Service C', 'Function C does not work properly');

-- insert data to USER_MFOODY
INSERT INTO
    USER_MFOODY (
    ID_USER,
    EMAIL_USER,
    PASSWORD_USER,
    NAME_USER,
    PHONE_NUMBER_USER,
    ADDRESS_USER,
    ROLE_USER
)
VALUES (215230, 'christblack@gmail.com', '$2a$12$hTiorzLNiuPgbeUvA2c/GuFxGMZkua4l8eydOyE0BS2WgHRv2dL3a', 'Christ Black', '89653453345', 'New York, USA', 'ADMIN'),
       (215231, 'robinsonhank@gmail.com', '$2a$12$iXPkaN0Ey.LFhPLbDdg0Suo07MCyDk9F4AaBMCYsEYTUw6HDetQDm', 'Robinson Hank', '89346783005', 'Texas, USA', 'ADMIN'),
       (215232, 'williamsswift@gmail.com', '$2a$12$S1BjZ6IWaNvpiioknCu4xe2Kj0mAZWg/eOorRc1u4gjJA66b3Nub6', 'Williams Swift', '89005453428', 'Arizona, USA', 'USER'),
       (215233, 'janedoe@gmail.com', '$2a$12$56.dbVqxnei/8rv1nQKHEOcxHUBE.2O.UwNyFAVJzSMff0LxPL2sK', 'Jane Doe', '89653453348', 'Dallas, USA', 'ADMIN'),
       (215234, 'mikejohnson@gmail.com', '$2a$12$O/Fls8pzIZJrghAXuiPniOONs7/hyeFDf8oIXmuKdi5tQZD/4TJOC', 'Mike Johnson', '89653453349', 'Houston, USA', 'ADMIN'),
       (215235, 'sarahjohnson@gmail.com', '$2a$12$KUVtbyKq85Y5L6ZwY1RrruUvbcAFfOrZDf8bXtj7w/yG7B/wVnY/y', 'Sarah Johnson', '89653453350', 'Phoenix, USA', 'USER'),
       (215236, 'lukejohnson@gmail.com', '$2a$12$mcqiTlGb7YLnWFvVC5tiSuoN/gMNXj.Fo9t7VbtUC7FXnXG8rX2La', 'Luke Johnson', '89653453351', 'San Francisco, USA', 'USER'),
       (215237, 'sarahevan@gmail.com', '$2a$12$4KybVBIzB48d4jcX8t5Szes9OS6BfIy923M9hjlkJb48uV/lFydBq', 'Sarah Evan', '89653453352', 'Seattle, USA', 'USER'),
       (215238, 'robertsmith@gmail.com', '$2a$12$/rOzefadRaSs.plujaAk3.UTJQVVF1OF6OhMYXGNngrwrg7IpZTPe', 'Robert Smith', '89653453353', 'Denver, USA', 'USER'),
       (215239, 'jennysmith@gmail.com', '$2a$12$1as9gKZySe8yTPLW7pBtdOBSN3tsCcKINDTwhUZfii6i5D8ZcGrEO', 'Jenny Smith', '89653453354', 'Boston, USA', 'USER');
-- BCrypt: https://bcrypt-generator.com/
-- PASSWORD_USER = PASSWORD_USER (not includes '@gmail.com' + 123)
-- insert data to USER_MFOODY
-- INSERT INTO
--     USER_MFOODY (
--         ID_USER,
--         EMAIL_USER,
--         PASSWORD_USER,
--         NAME_USER,
--         PHONE_NUMBER_USER,
--         ADDRESS_USER,
--         ROLE_USER
--     )
-- VALUES (215230, 'christblack@gmail.com', 'christblack123', 'Christ Black', '89653453345', 'New York, USA', 'USER'),
-- 		(215231, 'robinsonhank@gmail.com', 'robinsonhank123', 'Robinson Hank', '89346783005', 'Texas, USA', 'ADMIN'),
-- 		(215232, 'williamsswift@gmail.com', 'williamsswift123', 'Williams Swift', '89005453428', 'Arizona, USA', 'USER');

-- insert data to CART_MFOODY
INSERT INTO
    CART_MFOODY (
    ID_CART,
    QUANTITY_ALL_PRODUCTS_IN_CART,
    TOTAL_SALE_PRICE_CART,
    TOTAL_FULL_PRICE_CART,
    ID_USER
)
VALUES (350060, 1, 50, 50, 215231),
       (350061, 1, 40, 40, 215230),
       (350062, 1, 50, 80, 215232);

-- insert data to CREDIT_CARD_MFOODY
INSERT INTO
    CREDIT_CARD_MFOODY (
    ID_CARD,
    NAME_USER_CARD,
    NUMBER_CARD,
    EXPIRATION_CARD,
    SECURITY_CODE_CARD,
    ID_USER
)
VALUES (430710, 'Christ Black', '5379653043547446', '20/12/22', '505', 215230),
       (430711, 'Robinson Hank', '6589652343548896', '01/01/23', '714', 215231),
       (430712, 'Williams Swift', '5238759847350934', '06/12/24', '272', 215232);

-- INSERT INTO
--     CREDIT_CARD_MFOODY (
--     ID_CARD,
--     NAME_USER_CARD,
--     NUMBER_CARD,
--     EXPIRATION_CARD,
--     SECURITY_CODE_CARD,
--     ID_USER
-- )
-- VALUES (430713, 'Williams Swift', '2305759847357788', '06/11/25', '525', 215232);

-- insert data to PRODUCT_MFOODY
INSERT INTO
    PRODUCT_MFOODY (
    ID_PRODUCT,
    NAME_PRODUCT,
    ALBUM_PRODUCT,
    DESCRIPTION_PRODUCT,
    SALE_PRICE_PRODUCT,
    FULL_PRICE_PRODUCT,
    WEIGHT_PRODUCT,
    IMPORT_QUANTITY_PRODUCT,
    IMPORT_DATE_PRODUCT,
    STOREHOUSE_QUANTITY_PRODUCT,
    RATING_PRODUCT,
    CATEGORY_PRODUCT,
    BRAND_PRODUCT
)
VALUES (530020, 'Картофель мытый', 'Vegetable_Potatp_1', 'Срок хранения макс: 9 дней. Страна: Россия', 50, 80, '1 Kg', 150, '22/01/2023', 100, 3, 'Овощи', 'Без Бренда'),
       (530021, 'Картофель', 'Vegetable_Potato_2', 'Срок хранения макс: 8 дней. Страна: Россия', 40, 40, '1 Kg', 100, '19/02/2023', 30, 4, 'Овощи', 'Без Бренда'),
       (530022, 'Картофель красный мытый', 'Vegetable_Potato_3', 'Срок хранения макс: 6 дней. Страна: Россия', 50, 50, '1 Kg', 250, '22/01/2023', 100, 5, 'Овощи', 'Без Бренда');

-- insert data to COMMENT_MFOODY
INSERT INTO
    COMMENT_MFOODY (
    ID_COMMENT,
    RATING_COMMENT,
    CONTENT_COMMENT,
    ID_USER,
    ID_PRODUCT
)
VALUES (608750, 5, 'The products is GOOD', 215230, 530022),
       (608751, 4, 'The products is OKAY', 215231, 530021),
       (608752, 3, 'The products is TERRIBLE', 215232, 530020),
       (608753, 3, 'The products is NOT GOOD', 215239, 530020);

-- insert data to ORDER_MFOODY
INSERT INTO
    ORDER_MFOODY (
    ID_ORDER,
    DATE_ORDER,
    DATE_RECEIPT_ORDER,
    SHIPPING_PRICE_ORDER,
    SHIPPING_METHOD_ORDER,
    QUANTITY_ALL_PRODUCTS_IN_ORDER,
    TOTAL_SALE_PRICE_ORDER,
    TOTAL_FULL_PRICE_ORDER,
    PAYMENT_METHOD_ORDER,
    STATUS_ORDER,
    ID_USER
)
VALUES (750020, '22/03/2023', '21/03/2023', 0, "Recieve at Store", 1, 50, 50, 'Credit Card', 'Processing', 215231),
       (750021, '19/03/2023', '19/03/2023', 0, "Recieve at Store", 1, 40, 40, 'By Cash', 'Processing', 215230),
       (750022, '24/03/2023', '25/03/2023', 0, "Recieve at Store", 1, 50, 80, 'Credit Card', 'Processing', 215232);

-- insert data to DETAIL_PRODUCT_CART_MFOODY
INSERT INTO
    DETAIL_PRODUCT_CART_MFOODY (
    ID_CART,
    ID_PRODUCT,
    QUANTITY_DETAIL_PRODUCT_CART,
    SALE_PRICE_DETAIL_PRODUCT_CART,
    FULL_PRICE_DETAIL_PRODUCT_CART
)
VALUES (350060, 530022, 1, 50, 50),
       (350061, 530021, 1, 40, 40),
       (350062, 530020, 1, 50, 80);

-- insert data to DETAIL_PRODUCT_ORDER_MFOODY
INSERT INTO
    DETAIL_PRODUCT_ORDER_MFOODY (
    ID_ORDER,
    ID_PRODUCT,
    QUANTITY_DETAIL_PRODUCT_ORDER,
    SALE_PRICE_DETAIL_PRODUCT_ORDER,
    FULL_PRICE_DETAIL_PRODUCT_ORDER
)
VALUES (750020, 530022, 1, 50, 50),
       (750021, 530021, 1, 40, 40),
       (750022, 530020, 1, 50, 80);

ROLLBACK;
