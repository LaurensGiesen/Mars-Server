insert  into subscriptions(id, name, price) values(1,'FREE',0);
insert  into subscriptions(id, name, price) values(2,'BASIC',10);
insert  into subscriptions(id, name, price) values(3,'PREMIUM',100);

insert into users(firstname, lastname,email,date_of_birth, subscription_id,address_id) values('Kurt','Sys', 'Kurt.Sys@hotmail.com','20-05-2030',3,-1);

insert into plants(name, price,owner_id, date, amount) values('Apple',5,1,DATE '2052-08-20',5);
insert into plants(name, price,owner_id, date, amount) values('Carrot',3,1,DATE '2052-05-11',15);
insert into plants(name, price,owner_id, date, amount) values('Banana',5,1,DATE '2052-07-09', 8);
insert into plants(name, price,owner_id, date, amount) values('Tomato', 6,1, DATE '2052-06-05', 12);
insert into plants(name, price,owner_id, date, amount) values('Pear', 3,1, DATE '2052-03-03', 15);
insert into plants(name, price,owner_id, date, amount) values('Kiwi', 6,1, DATE '2052-02-06', 12);