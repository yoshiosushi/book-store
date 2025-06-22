create table users (
user_id BIGINT not null primary key AUTO_INCREMENT,
user_name varchar(50) not null,
email varchar(50) not null unique,
phone varchar(50) not null,
password varchar(500) not null,
authority enum('ADMIN', 'USER') not null,
default_address_id BIGINT
);

create table products (
product_id BIGINT not null primary key AUTO_INCREMENT,
product_name varchar(50) not null,
stock int not null,
price int not null,
note varchar(500),
image varchar(500)
);

create table carts (
cart_id BIGINT not null primary key AUTO_INCREMENT,
user_id BIGINT not null unique,
foreign key (user_id) references users(user_id)
);

create table cart_items (
cart_item_id BIGINT not null primary key AUTO_INCREMENT,
cart_id BIGINT not null,
product_id BIGINT not null,
quantity int not null,
foreign key (cart_id) references carts(cart_id)
);

create table addresses (
address_id BIGINT not null primary key AUTO_INCREMENT,
user_id BIGINT not null,
postal_code_f varchar(3) not null,
postal_code_b varchar(4) not null,
forward_address varchar(50) not null,
last_name varchar(50) not null,
first_name varchar(50) not null,
phone varchar(50) not null,
foreign key (user_id) references users(user_id)
);
