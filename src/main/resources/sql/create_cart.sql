DROP TABLE CART;

create table CART
(
    USER_ID VARCHAR(255) not null,
    INDEX   INT          not null,
    MENU_ID INT,
    COUNT   INT,
    REG_DT  DATETIME,
    UDT_DT  DATETIME,
    primary key (USER_ID, INDEX)
);