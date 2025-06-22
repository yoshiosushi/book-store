insert into users (user_name, email, phone, password, authority, default_address_id) values (
'tom', 'tom@sample.com', '000-0000-0000',
 'b84613bc899fa1d5a6bfc57118e13ec8093a79cab120debbb7ecaa579d56d7dd1f20c913aaea759b2118b9e96887952c', 'ADMIN', '1'
);
insert into users (user_name, email, phone, password, authority, default_address_id) values (
'bob', 'bob@sample.com', '111-1111-1111',
 '91907322811a5fa84be7efb24079cebe2a7d89df7102820df12b8b5daca458b6feb13a340f580c70c63cd4ea6bc1d113', 'USER', null
);

insert into products (product_name, stock, price, note, image)
values ('dog_0001', '20', '1500', 'Smiling dog', '/images/dog_0001.png');
insert into products (product_name, stock, price, note, image)
values ('cat_0001', '25', '1600', 'Wet and thin cat', '/images/cat_0001.png');
insert into products (product_name, stock, price, note, image)
values ('squirrel_0001', '10', '1700', 'Squirrel eating nuts', '/images/squirrel_0001.png');
insert into products (product_name, stock, price, note, image)
values ('raccoon_0001', '10', '4999', 'Raccoon riding a bicycle', '/images/raccoon_0001.png');
insert into products (product_name, stock, price, note, image)
values ('dog_0002', '20', '1000', 'Sleeping dog', '/images/dog_0002.png');

insert into carts (user_id) values (1);
insert into carts (user_id) values (2);

insert into cart_items (cart_id, product_id, quantity) values (1, 1, 2);
insert into cart_items (cart_id, product_id, quantity) values (1, 3, 5);

insert into addresses (user_id, postal_code_f, postal_code_b, forward_address, last_name, first_name, phone)
values ('1', '605', '0862', '京都府京都市東山区清水1丁目294', 'Smith', 'Tom', '111-1111-1111');
insert into addresses (user_id, postal_code_f, postal_code_b, forward_address, last_name, first_name, phone)
values ('1', '111', '0032', '東京都台東区浅草2丁目3-1', 'Test', 'Bob', '222-2222-2222');