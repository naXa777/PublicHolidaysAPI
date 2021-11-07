DROP DATABASE IF EXISTS holidays;
CREATE DATABASE holidays;

USE holidays;

CREATE TABLE country
(
    id CHAR(2) NOT NULL DEFAULT '' COMMENT 'ISO identifier',
    name VARCHAR (64) NOT NULL DEFAULT '' COMMENT 'name of the country',
    PRIMARY KEY (id)
)ENGINE=innodb CHARSET=utf8;

CREATE TABLE province
(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    country_id CHAR(2) NOT NULL DEFAULT '' COMMENT 'ISO country identifier',
    province_id VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'Province identifier',
    province_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'Province name',
    PRIMARY KEY (id),
    CONSTRAINT fk_province_country FOREIGN KEY (country_id) REFERENCES country (id)
)ENGINE=innodb CHARSET=utf8;

CREATE TABLE public_holiday
(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    country_id CHAR(2) NOT NULL DEFAULT '' COMMENT 'ISO country identifier',
    name VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'Name of the holiday',
    holiday_date DATE NOT NULL DEFAULT '1970-01-01',
    holiday_date_value VARCHAR(10) NOT NULL DEFAULT '1970-01-01',
    national_holiday BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'If this holiday is a national holiday for the country',
    PRIMARY KEY (id),
    CONSTRAINT fk_public_holiday_country FOREIGN KEY (country_id) REFERENCES country (id)
)ENGINE=innodb CHARSET=utf8;

CREATE TABLE public_holiday_province
(
    public_holiday_id INT(10) UNSIGNED NOT NULL DEFAULT 0,
    province_id INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'The province this holiday is for',
    PRIMARY KEY (public_holiday_id, province_id),
    CONSTRAINT fk_public_holiday_province_public_holiday FOREIGN KEY (public_holiday_id) REFERENCES public_holiday (id),
    CONSTRAINT fk_public_holiday_province_province FOREIGN KEY (province_id) REFERENCES province (id)
)ENGINE=innodb CHARSET=utf8;
