DROP TABLE order_menu;

CREATE TABLE ORDER_MENU (
	menu_id	VARCHAR(255)	NOT NULL,
	option_id	VARCHAR(255)	NOT NULL,
	sub_option_id	VARCHAR(255)	NOT NULL,
	count	INT	NULL	DEFAULT 1,
	price	INT	NULL
);

ALTER TABLE ORDER_MENU ADD CONSTRAINT PK_ORDER_MENU PRIMARY KEY (
	menu_id
);