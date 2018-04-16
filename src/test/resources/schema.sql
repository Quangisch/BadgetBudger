CREATE TABLE User (
	 userID int(11) AUTO_INCREMENT,
	 username varchar(50) NOT NULL UNIQUE,
	 PRIMARY KEY(userID)
);

CREATE TABLE Item (
	userID int(11) NOT NULL,
	year int(4) unsigned NOT NULL,
	month int(2) unsigned NOT NULL,
	day int(2) unsigned NOT NULL,
	position int(11) unsigned NOT NULL,
	itemname varchar(50) NOT NULL,
	price DECIMAL(10,2) NOT NULL,
	location varchar(50) NOT NULL,
	PRIMARY KEY(userID, year, month, day, position)
);