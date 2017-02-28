DROP DATABASE IF EXISTS holidays;
CREATE DATABASE holidays;

USE holidays;

CREATE TABLE country
(
    id CHAR(2) NOT NULL DEFAULT '' COMMENT 'ISO identifier',
    name VARCHAR (64) NOT NULL DEFAULT '' COMMENT 'name of the country',
    PRIMARY KEY (id)
)ENGINE=innodb CHARSET=utf8;

INSERT INTO country (id, name) VALUES ('AU', 'Australia');
INSERT INTO country (id, name) VALUES ('NZ', 'New Zealand');
INSERT INTO country (id, name) VALUES ('SG', 'Singapore');
INSERT INTO country (id, name) VALUES ('HK', 'Hong Kong');

CREATE TABLE province
(
    id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    country_id CHAR(2) NOT NULL DEFAULT '' COMMENT 'ISO country identifier',
    province_id VARCHAR(32) NOT NULL DEFAULT '' COMMENT 'Province identifier',
    province_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'Province name',
    PRIMARY KEY (id),
    CONSTRAINT fk_province_country FOREIGN KEY (country_id) REFERENCES country (id)
)ENGINE=innodb CHARSET=utf8;

INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'NSW', 'New South Wales');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'VIC', 'Victoria');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'QLD', 'Queenslad');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'ACT', 'Australian Capital Territory');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'TAS', 'Tasmania');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'SA', 'South Australia');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'NT', 'Northern Territory');
INSERT INTO province (country_id, province_id, province_name) VALUES ('AU', 'WA', 'Western Australia');

INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'NTL', 'Northland');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'AUK', 'Auckland');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'WKO', 'Waikato');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'BOP', 'Bay Of Plenty');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'GIS', 'Gisborne');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'HKB', "Hawke's Bay");
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'TKI', 'Taranaki');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'MWT', 'Manawatu-Whanganui');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'WGN', 'Wellington');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'TAS', 'Tasman');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'NSN', 'Nelson');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'MBH', 'Marlborough');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'WTC', 'West Coast');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'CAN', 'Canterbury');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'OTA', 'Otago');
INSERT INTO province (country_id, province_id, province_name) VALUES ('NZ', 'STL', 'Southland');

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

-- New Zealand 2017
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-01', '2017-01-01', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-02', '2017-01-02', "Day after New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-03', '2017-01-03', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-02-06', '2017-02-06', "Waitangi Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-04-14', '2017-04-14', "Good Friday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-04-17', '2017-04-17', "Easter Monday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-04-25', '2017-04-25', "Anzac Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-06-05', '2017-06-05', "Queens Birthday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-10-23', '2017-10-23', "Labour Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-12-25', '2017-12-25', "Christmas Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-12-26', '2017-12-26', "Boxing Day", TRUE);

INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-30', '2017-01-30', "Auckland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "AUK";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-09-25', '2017-09-25', "Canterbury (South) Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "CAN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-11-17', '2017-11-17', "Canterbury Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "CAN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-10-20', '2017-10-20', "Hawke's Bay Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "HKB";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-10-30', '2017-10-30', "Marlborough Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "MBH";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-30', '2017-01-30', "Nelson Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "NSN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-30', '2017-01-30', "Northland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "NTL";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-03-20', '2017-03-20', "Otago Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "OTA";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-04-18', '2017-04-18', "Southland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "STL";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-03-13', '2017-03-13', "Taranaki Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "TKI";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-01-23', '2017-01-23', "Wellington Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "WGN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2017-12-04', '2017-12-04', "Westland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "WTC";

-- New Zealand 2018
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-01', '2018-01-01', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-02', '2018-01-02', "Day after New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-02-06', '2018-02-06', "Waitangi Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-03-30', '2018-03-30', "Good Friday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-04-02', '2018-04-02', "Easter Monday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-04-25', '2018-04-25', "Anzac Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-06-04', '2018-06-04', "Queens Birthday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-10-22', '2018-10-22', "Labour Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-12-25', '2018-12-25', "Christmas Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-12-26', '2018-12-26', "Boxing Day", TRUE);

INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-29', '2018-01-29', "Auckland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "AUK";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-09-24', '2018-09-24', "Canterbury (South) Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "CAN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-11-16', '2018-11-16', "Canterbury Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "CAN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-10-19', '2018-10-19', "Hawke's Bay Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "HKB";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-10-29', '2018-10-29', "Marlborough Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "MBH";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-29', '2018-01-29', "Nelson Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "NSN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-29', '2018-01-29', "Northland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "NTL";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-03-26', '2018-03-26', "Otago Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "OTA";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-04-03', '2018-04-03', "Southland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "STL";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-03-12', '2018-03-12', "Taranaki Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "TKI";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-01-22', '2018-01-22', "Wellington Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "WGN";
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('NZ', '2018-12-03', '2018-12-03', "Westland Anniversary Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) SELECT LAST_INSERT_ID(), id FROM province WHERE province_id = "WTC";

-- Australia 2017
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-01-01', '2017-01-01', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-01-02', '2017-01-02', "New Year's Day Holiday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-01-26', '2017-01-26', "Australia Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-14', '2017-04-14', "Good Friday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-17', '2017-04-17', "Easter Monday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-25', '2017-04-25', "Anzac Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-12-25', '2017-12-25', "Christmas Day", TRUE);

INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-03-06', '2017-03-06', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-03-13', '2017-03-13', "Canberra Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-03-13', '2017-03-13', "March Public Holiday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-03-13', '2017-03-13', "Eight Hours Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-03-13', '2017-03-13', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-15', '2017-04-15', "Easter Saturday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-16', '2017-04-16', "Easter Sunday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-04-18', '2017-04-18', "Easter Tuesday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-05-01', '2017-05-01', "May Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-05-01', '2017-05-01', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-06-05', '2017-06-05', "Western Australia Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-06-12', '2017-06-12', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-08-07', '2017-08-07', "Picnic Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-09-25', '2017-09-25', "Family & Community Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-09-25', '2017-09-25', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-10-02', '2017-10-02', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-10-02', '2017-10-02', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-11-07', '2017-11-07', "Melbourne Cup Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-12-26', '2017-12-26', "Boxing Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2017-12-26', '2017-12-26', "Proclamation Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);

-- Australia 2018
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-01-01', '2018-01-01', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-01-26', '2018-01-26', "Australia Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-30', '2018-03-30', "Good Friday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-04-02', '2018-04-02', "Easter Monday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-04-25', '2018-04-25', "Anzac Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-12-25', '2018-12-25', "Christmas Day", TRUE);

INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-05', '2018-03-05', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-12', '2018-03-12', "Canberra Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-12', '2018-03-12', "March Public Holiday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-12', '2018-03-12', "Eight Hours Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-12', '2018-03-12', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-03-31', '2018-03-31', "Easter Saturday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-04-01', '2018-04-01', "Easter Sunday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-04-03', '2018-04-03', "Easter Tuesday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-05-07', '2018-05-07', "May Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-05-07', '2018-05-07', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-06-04', '2018-06-04', "Western Australia Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-06-11', '2018-06-11', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-08-06', '2018-08-06', "Picnic Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-09-24', '2018-09-24', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-10-01', '2018-10-01', "Labour Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-10-08', '2018-10-08', "Family & Community Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-10-01', '2018-10-01', "Queens Birthday", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-11-06', '2018-11-06', "Melbourne Cup Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-12-26', '2018-12-26', "Boxing Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 1);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 2);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 3);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 4);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 5);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 7);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 8);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('AU', '2018-12-26', '2018-12-26', "Proclamation Day", FALSE);
INSERT INTO public_holiday_province (public_holiday_id, province_id) VALUES (LAST_INSERT_ID(), 6);

-- Singapore 2017
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-01-01', '2017-01-01', "New Year's Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-01-02', '2017-01-02', "New Year's Day Holiday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-01-28', '2017-01-28', "Chinese New Year", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-01-29', '2017-01-29', "Chinese New Year Holiday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-01-30', '2017-01-30', "Chinese New Year Holiday (Day 3)", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-04-14', '2017-04-14', "Good Friday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-05-01', '2017-05-01', "Labour Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-05-10', '2017-05-10', "Vesak Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-06-25', '2017-06-25', "Hari Raya Puasa", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-06-26', '2017-06-26', "Hari Raya Puasa Holiday", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-08-09', '2017-08-09', "National Day", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-09-01', '2017-09-01', "Hari Raya Haji", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-10-18', '2017-10-18', "Deepavali", TRUE);
INSERT INTO public_holiday (country_id, holiday_date, holiday_date_value, name, national_holiday) VALUES ('SG', '2017-12-25', '2017-12-25', "Christmas Day", TRUE);
