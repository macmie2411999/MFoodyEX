-- dispose db
DROP DATABASE IF EXISTS MFoodyEx;

-- create db
CREATE DATABASE IF NOT EXISTS MFoodyEx;

-- use db
USE MFoodyEx;

-- create table USER_MFOODY
CREATE TABLE
    IF NOT EXISTS USER_MFOODY(
                                 ID_USER INT NOT NULL AUTO_INCREMENT,
                                 EMAIL_USER NVARCHAR(50) NOT NULL,
    PASSWORD_USER NVARCHAR(50) NOT NULL,
    NAME_USER NVARCHAR(50) NOT NULL,
    PHONE_NUMBER_USER NVARCHAR(20) NOT NULL,
    ADDRESS_USER NVARCHAR(100) NOT NULL,
    ROLE_USER NVARCHAR(20) NOT NULL,
    PRIMARY KEY (ID_USER)
    );

-- create table CREDIT_CARD_MFOODY
CREATE TABLE
    IF NOT EXISTS CREDIT_CARD_MFOODY(
                                        ID_CARD INT NOT NULL AUTO_INCREMENT,
                                        NAME_USER_CARD NVARCHAR(50) NOT NULL,
    NUMBER_CARD NVARCHAR(20) NOT NULL,
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
                                    NAME_PRODUCT NVARCHAR(100) NOT NULL,
    ALBUM_PRODUCT NVARCHAR(20) NOT NULL,
    DESCRIPTION_PRODUCT TEXT NOT NULL,
    FULL_PRICE_PRODUCT INT NOT NULL,
    SALE_PRICE_PRODUCT INT NOT NULL,
    WEIGHT_PRODUCT TEXT NOT NULL,
    IMPORT_QUANTITY_PRODUCT INT NOT NULL,
    IMPORT_DATE_PRODUCT NVARCHAR(20) NOT NULL,
    STOREHOUSE_QUANTITY_PRODUCT INT NOT NULL,
    RATING_PRODUCT INT NOT NULL,
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
                                 SALE_PRICE_CART INT NOT NULL,
                                 FULL_PRICE_CART INT NOT NULL,
                                 ID_USER INT NOT NULL UNIQUE,
                                 PRIMARY KEY (ID_CART),
    CONSTRAINT FK_CART_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER)
    );

-- create table DETAIL_PRODUCT_CART_MFOODY
CREATE TABLE
    IF NOT EXISTS DETAIL_PRODUCT_CART_MFOODY(
                                                ID_CART INT NOT NULL AUTO_INCREMENT,
                                                ID_PRODUCT INT NOT NULL,
                                                QUANTITY_DETAIL_PRODUCT_CART INT NOT NULL,
                                                SALE_PRICE_DETAIL_PRODUCT_CART INT NOT NULL,
                                                FULL_PRICE_DETAIL_PRODUCT_CART INT NOT NULL,
                                                PRIMARY KEY (ID_CART, ID_PRODUCT),
    CONSTRAINT FK_DETAILPRODUCTCARD_VS_CART FOREIGN KEY (ID_CART) REFERENCES CART_MFOODY(ID_CART),
    CONSTRAINT FK_DETAILPRODUCTCARD_VS_PRODUCT FOREIGN KEY (ID_PRODUCT) REFERENCES PRODUCT_MFOODY(ID_PRODUCT)
    );

-- create table ORDER_MFOODY
CREATE TABLE
    IF NOT EXISTS ORDER_MFOODY(
                                  ID_ORDER INT NOT NULL AUTO_INCREMENT,
                                  DATE_ORDER NVARCHAR(20) NOT NULL,
    DATE_RECEIPT_ORDER NVARCHAR(20) NOT NULL,
    SHIPPING_PRICE_ORDER INT NOT NULL,
    SHIPPING_METHOD_ORDER NVARCHAR(50) NOT NULL,
    TOTAL_FULL_PRICE_ORDER INT NOT NULL,
    TOTAL_SALE_PRICE_ORDER INT NOT NULL,
    PAYMENT_METHOD_ORDER NVARCHAR(50) NOT NULL,
    STATUS_ORDER NVARCHAR(20) NOT NULL,
    ID_USER INT NOT NULL,
    PRIMARY KEY (ID_ORDER),
    CONSTRAINT FK_ORDER_VS_USER FOREIGN KEY (ID_USER) REFERENCES USER_MFOODY(ID_USER)
    );

-- create table DETAIL_PRODUCT_ORDER_MFOODY
CREATE TABLE
    IF NOT EXISTS DETAIL_PRODUCT_ORDER_MFOODY(
                                                 ID_PRODUCT INT NOT NULL AUTO_INCREMENT,
                                                 ID_ORDER INT NOT NULL,
                                                 QUANTITY_DETAIL_PRODUCT_ORDER INT NOT NULL,
                                                 SALE_PRICE_DETAIL_PRODUCT_ORDER INT NOT NULL,
                                                 FULL_PRICE_DETAIL_PRODUCT_ORDER INT NOT NULL,
                                                 PRIMARY KEY (ID_PRODUCT, ID_ORDER),
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
VALUES (215230, 'christblack@gmail.com', 'christblack123', 'Christ Black', '89653453345', 'New York, USA', 'USER'),
       (215231, 'robinsonhank@gmail.com', 'robinsonhank123', 'Robinson Hank', '89346783005', 'Texas, USA', 'ADMIN'),
       (215232, 'williamsswift@gmail.com', 'williamsswift123', 'Williams Swift', '89005453428', 'Arizona, USA', 'USER');

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
    SALE_PRICE_CART,
    FULL_PRICE_CART,
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

-- insert data to PRODUCT_MFOODY
INSERT INTO
    PRODUCT_MFOODY (
    ID_PRODUCT,
    NAME_PRODUCT,
    ALBUM_PRODUCT,
    DESCRIPTION_PRODUCT,
    FULL_PRICE_PRODUCT,
    SALE_PRICE_PRODUCT,
    WEIGHT_PRODUCT,
    IMPORT_QUANTITY_PRODUCT,
    IMPORT_DATE_PRODUCT,
    STOREHOUSE_QUANTITY_PRODUCT,
    RATING_PRODUCT,
    CATEGORY_PRODUCT,
    BRAND_PRODUCT
)
VALUES (530020, 'Картофель мытый', 'Vegetable_Tomato_1', 'Срок хранения макс: 9 дней. Страна: Россия', 80, 50, '1 Kg', 150, '22/01/2023', 100, 5, 'Овощи', 'Без Бренда'),
       (530021, 'Картофель', 'Vegetable_Tomato_2', 'Срок хранения макс: 8 дней. Страна: Россия', 40, 40, '1 Kg', 100, '19/02/2023', 30, 4, 'Овощи', 'Без Бренда'),
       (530022, 'Картофель красный мытый', 'Vegetable_Tomato_3', 'Срок хранения макс: 6 дней. Страна: Россия', 50, 50, '1 Kg', 250, '22/01/2023', 100, 5, 'Овощи', 'Без Бренда');

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
       (608752, 3, 'The products is TERRIBLE', 215232, 530020);

-- insert data to ORDER_MFOODY
INSERT INTO
    ORDER_MFOODY (
    ID_ORDER,
    DATE_ORDER,
    DATE_RECEIPT_ORDER,
    SHIPPING_PRICE_ORDER,
    SHIPPING_METHOD_ORDER,
    TOTAL_FULL_PRICE_ORDER,
    TOTAL_SALE_PRICE_ORDER,
    PAYMENT_METHOD_ORDER,
    STATUS_ORDER,
    ID_USER
)
VALUES (750020, '22/03/2023', '21/03/2023', 0, "Recieve at Store", 50, 50, 'Credit Card', 'Processing', 215231),
       (750021, '19/03/2023', '19/03/2023', 0, "Recieve at Store", 40, 40, 'By Cash', 'Processing', 215230),
       (750022, '24/03/2023', '25/03/2023', 0, "Recieve at Store", 50, 50, 'Credit Card', 'Processing', 215232);

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
    ID_PRODUCT,
    ID_ORDER,
    QUANTITY_DETAIL_PRODUCT_ORDER,
    SALE_PRICE_DETAIL_PRODUCT_ORDER,
    FULL_PRICE_DETAIL_PRODUCT_ORDER
)
VALUES (530022, 750020, 1, 50, 50),
       (530021, 750021, 1, 40, 40),
       (530020, 750022, 1, 50, 80);
