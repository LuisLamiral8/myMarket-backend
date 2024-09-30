CREATE TABLE IF NOT EXISTS logs (
	id INT AUTO_INCREMENT NOT NULL,
	type_log VARCHAR(50),
	origin_log VARCHAR(50),
	date_log TIMESTAMP,
	description VARCHAR(255),
	PRIMARY KEY (id)
);

DELIMITER //

/* Triggers de usuarios */

CREATE TRIGGER tg_create_user
AFTER insert ON `user`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('CREATE', 'USER', CURRENT_TIMESTAMP(), CONCAT('Created user: ', NEW.firstname, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_update_user
AFTER update ON `user`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('UPDATE', 'USER', CURRENT_TIMESTAMP(), CONCAT('Updated user: ', NEW.firstname, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_delete_user
AFTER delete ON `user`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('DELETE', 'USER', CURRENT_TIMESTAMP(), CONCAT('Delete user: ', OLD.firstname, ', id: ', OLD.id));
END;
//
/* End Triggers de usuarios */

/* Triggers de productos */

CREATE TRIGGER tg_create_product
AFTER insert ON `product`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('CREATE', 'PRODUCT', CURRENT_TIMESTAMP(), CONCAT('Product user: ', NEW.name, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_update_product
AFTER update ON `product`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('UPDATE', 'PRODUCT', CURRENT_TIMESTAMP(), CONCAT('Updated product: ', NEW.name, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_delete_product
AFTER delete ON `product`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('DELETE', 'PRODUCT', CURRENT_TIMESTAMP(), CONCAT('Delete product: ', OLD.name, ', id: ', OLD.id));
END;
//

/* End Triggers de productos */

/* Triggers de categorias */

CREATE TRIGGER tg_create_category
AFTER insert ON `category`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('CREATE', 'CATEGORY', CURRENT_TIMESTAMP(), CONCAT('Created category: ', NEW.name, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_update_category
AFTER update ON `category`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('UPDATE', 'CATEGORY', CURRENT_TIMESTAMP(), CONCAT('Updated category: ', NEW.name, ', id: ', NEW.id));
END;
//

CREATE TRIGGER tg_delete_category
AFTER delete ON `category`
FOR EACH ROW
BEGIN
    INSERT INTO logs (type_log, origin_log, date_log, description)
    VALUES ('DELETE', 'CATEGORY', CURRENT_TIMESTAMP(), CONCAT('Delete category: ', OLD.name, ', id: ', OLD.id));
END;
//

DELIMITER ;