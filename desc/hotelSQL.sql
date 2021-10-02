create database hotel_system;
use hotel_system;

create table hotel(
	hotel_id int NOT NULL AUTO_INCREMENT,
	hotel_name varchar(60),
	street varchar(40),
	barangay varchar(60),
	city varchar(40),
	phone_number varchar(12),
	PRIMARY KEY(hotel_id)
);

create table employee(
	staff_id varchar(12) NOT NULL,
	hotel_id int NOT NULL,
	email varchar(30),
	emp_first varchar(60),
	emp_last varchar(50),
	phone_number varchar(12),
	emp_position varchar(20),
	password varchar(100),
	PRIMARY KEY(staff_id),
	FOREIGN KEY(hotel_id) REFERENCES hotel(hotel_id)
);

create table customer_detail(
	customer_id int NOT NULL AUTO_INCREMENT,
	email varchar(30),
	customer_first varchar(60),
	customer_last varchar(50),
	phone_number varchar(12),
	PRIMARY KEY(customer_id)
);

create table room(
	room_id int NOT NULL AUTO_INCREMENT,
	hotel_id int NOT NULL,
	room_description text,
	room_type varchar(20),
	room_rate double,
	PRIMARY KEY(room_id),
	FOREIGN KEY(hotel_id) REFERENCES hotel(hotel_id)
);

create table booking(
	booking_id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	room_id int NOT NULL,
	hotel_id int NOT NULL,
	book_date timestamp,
	checkin_date timestamp,
	checkout_date timestamp,
	number_nights int DEFAULT 0,
	PRIMARY KEY(booking_id),
	FOREIGN KEY(customer_id) REFERENCES customer_detail(customer_id),
	FOREIGN KEY(room_id) REFERENCES room(room_id),
	FOREIGN KEY(hotel_id) REFERENCES hotel(hotel_id)
);

create table customer_records(
	record_id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	booking_id int NOT NULL,
	PRIMARY KEY(record_id),
	FOREIGN KEY(customer_id) REFERENCES customer_detail(customer_id),
	FOREIGN KEY(booking_id) REFERENCES booking(booking_id)
);

create table arrival_history(
	arrival_id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	date_arrival timestamp,
	PRIMARY KEY(arrival_id),
	FOREIGN KEY(customer_id) REFERENCES customer_detail(customer_id)
);

create table departure_history(
	departure_id int NOT NULL AUTO_INCREMENT,
	room_id int NOT NULL,
	customer_id INT NOT NULL,
	date_departure timestamp,
	number_nights int,
	PRIMARY KEY(departure_id),
	FOREIGN KEY(room_id) REFERENCES room(room_id),
	FOREIGN KEY(customer_id) REFERENCES customer_detail(customer_id)
);

create table payment(
	payment_id int NOT NULL AUTO_INCREMENT,
	customer_id int NOT NULL,
	booking_id int NOT NULL,
	card_number int,
	amoount double,
	payment_date DATE,
	payment_type varchar(20),
	PRIMARY KEY(payment_id),
	FOREIGN KEY(customer_id) REFERENCES customer_detail(customer_id),
	FOREIGN KEY(booking_id) REFERENCES booking(booking_id)
);

INSERT INTO hotel (hotel_name,street,barangay,city,phone_number) VALUES('Oazis Hotel','Bonifacio St.','Agustin','Butuan City','09102234912');
INSERT INTO employee(staff_id,hotel_id,email,emp_first,emp_last,phone_number,emp_position,password) VALUES('18100001417',1,'ejeegarbo@gmail.com','Ian Jee',
'Garbo','09102234912','Hotel Manager','qwerty182');