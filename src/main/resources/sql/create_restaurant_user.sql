DROP TABLE RESTAURANT_USER;

create table RESTAURANT_USER
(
    USER_ID  VARCHAR(255) not null,
    REG_DT   DATETIME,
    UDT_DT   DATETIME,
    primary key (USER_ID)
);
