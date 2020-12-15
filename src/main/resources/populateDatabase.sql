insert  into subscriptions(id, name, price) values(1,'FREE',0);
insert  into subscriptions(id, name, price) values(2,'BASIC',10);
insert  into subscriptions(id, name, price) values(3,'PREMIUM',100);

insert into users(firstname, lastname,email,date_of_birth, subscription_id,address_id) values('Kurt','Sys', 'Kurt.Sys@hotmail.com','20-05-2030',3,-1);

insert into plants(name, price,owner_id, date, amount) values('Apple',2,1,DATE '2052-08-20',5);
insert into plants(name, price,owner_id, date, amount) values('Carrot',3,1,DATE '2052-05-11',15);
insert into plants(name, price,owner_id, date, amount) values('Banana',1,1,DATE '2052-07-09', 8);
insert into plants(name, price,owner_id, date, amount) values('Grapes', 3,1, DATE '2052-06-05', 12);
insert into plants(name, price,owner_id, date, amount) values('Strawberry', 3,1, DATE '2052-02-06', 12);

insert into seeds(name, price, type)
values ('Apple', 2, 0);
insert into seeds(name, price, type)
values ('Apricot', 3.5, 0);
insert into seeds(name, price, type)
values ('Banana', 1, 0);
insert into seeds(name, price, type)
values ('Asparagus', 5, 1);
insert into seeds(name, price, type)
values ('Broccoli', 4.5, 1);
insert into seeds(name, price, type)
values ('Tomato', 3, 1);

insert into locations(longitude, latitude, crop_id)
values (-1.8567844, 3.213108, 1);
insert into locations(longitude, latitude, crop_id)
values (-2.8472767, 2.218816, 1);
insert into locations(longitude, latitude, crop_id)
values (-3.8209738, 4.256325, 2);
insert into locations(longitude, latitude, crop_id)
values (-5.8690081, 4.256325, 2);
insert into locations(longitude, latitude, crop_id)
values (-3.8209738, 4.256325, 2);
insert into locations(longitude, latitude, crop_id)
values (-6.9846515, 8.641328, 3);
insert into locations(longitude, latitude, crop_id)
values (-1.8522288, 4.203834, 4);
insert into locations(longitude, latitude, crop_id)
values (-6.9841382, 4.203834, 4);
insert into locations(longitude, latitude, crop_id)
values (-5.0165485, 3.213108, 5);
insert into locations(longitude, latitude, crop_id)
values (-9.6548321, 5.659879, 6);