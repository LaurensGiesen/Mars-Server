insert  into subscriptions(id, name, price) values(1,'FREE',0);
insert  into subscriptions(id, name, price) values(2,'BASIC',10);
insert  into subscriptions(id, name, price) values(3,'PREMIUM',100);

insert into addresses(id, street, number, dome) values(1, 'The Moon', 404, 1337);
insert into users(firstname, lastname,email,date_of_birth, subscription_id,address_id) values('Kurt','Sys', 'Kurt.Sys@hotmail.com',DATE '2030-05-20',3,1);


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

insert into locations(longitude, latitude, crop_id, ratio)
values (3.213108, -1.8567844, 1, 6);
insert into locations(longitude, latitude, crop_id, ratio)
values (2.218816, -2.8472767, 1, 5);
insert into locations(longitude, latitude, crop_id, ratio)
values (4.256325, -3.8209738, 2, 3);
insert into locations(longitude, latitude, crop_id, ratio)
values (4.256325, -5.8690081, 2 ,7);
insert into locations(longitude, latitude, crop_id, ratio)
values (4.256925, -2.8209758, 2, 1);
insert into locations(longitude, latitude, crop_id, ratio)
values (8.641328, -6.9846515, 3, 3);
insert into locations(longitude, latitude, crop_id, ratio)
values (4.203834, -1.8522288, 4, 9);
insert into locations(longitude, latitude, crop_id, ratio)
values (4.203834, -6.9841382, 4, 2);
insert into locations(longitude, latitude, crop_id, ratio)
values (3.213108, -5.0165485, 5, 2);
insert into locations(longitude, latitude, crop_id, ratio)
values (5.659879, -9.6548321, 6, 8);