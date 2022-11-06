DROP TABLE RIDER;

create table RIDER
(
    USER_ID    VARCHAR(255) not null,
    COMMISSION INT,
    STATUS     VARCHAR(255),
    REG_DT     DATETIME,
    UDT_DT     DATETIME,
    primary key (USER_ID)
);