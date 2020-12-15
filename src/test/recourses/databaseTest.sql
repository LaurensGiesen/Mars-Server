drop table if exists addresses, baskets, favorites, harvests, histories, locations, plants, seeds, subscriptions, users, users_harvests, users_histories;

create table subscriptions
(
    id    int auto_increment                not null,
    name  enum ('free', 'basic', 'premium') not null,
    price double                            not null,
    primary key (id)
);


create table addresses
(
    id     int auto_increment not null,
    street nvarchar(50)       not null,
    number int                not null,
    dome   nvarchar(50)       not null,
    primary key (id)
);

create table users
(
    userid          int auto_increment not null,
    firstname       nvarchar(255)      not null,
    lastname        nvarchar(255)      not null,
    email           nvarchar(255)      not null,
    date_of_birth   nvarchar(255)      not null,
    subscription_id int                not null,
    address_id      int                not null,
    primary key (userid),
    foreign key (subscription_id) references subscriptions (id)

);

create table seeds
(
    id    int auto_increment          not null,
    name  varchar(255)                not null,
    price double                      not null,
    type  ENUM ('fruit', 'vegetable') not null,
    primary key (id)
);

create table plants
(
    id       int auto_increment not null,
    name     varchar(255)       not null,
    price    double             not null,
    owner_id int                not null,
    date     nvarchar(255)      not null,
    amount   int                not null,
    image    nvarchar(1024),
    primary key (id),
    foreign key (owner_id) references users (userid)
);

create table histories
(
    history_id int auto_increment not null,
    plant_id   int,
    seed_id    int,
    date       date               not null,
    status     varchar(255)       not null,
    count      int                not null,
    primary key (history_id)

);

create table harvests
(
    harvestid int auto_increment not null,
    plantid   int                not null,
    amount    int                not null,
    primary key (harvestid),
    foreign key (plantid) references plants (id)
);
create table users_harvests
(
    harvestid int not null,
    userid    int not null,
    foreign key (harvestid) references harvests (harvestid),
    foreign key (userid) references users (userid)
);
create table users_histories
(
    historyid int not null,
    userid    int not null,
    foreign key (historyid) references histories (history_id),
    foreign key (userid) references users (userid)
);

create table baskets
(
    id           int auto_increment     not null,
    user_id      int                    not null,
    product_id   int                    not null,
    product_type enum ('seed', 'plant') not null,
    foreign key (user_id) references users (userid)
);

create table favorites
(
    id           int auto_increment     not null,
    user_id      int                    not null,
    product_id   int                    not null,
    product_type enum ('seed', 'plant') not null,
    foreign key (user_id) references users (userid)
);


create table locations
(
    longitude int                not null,
    latitude  int                not null,
    crop_id   int                not null,
    ratio     int                not null,
    primary key (longitude, latitude),
    foreign key (crop_id) references seeds (id)
);


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

insert into locations(longitude, latitude, crop_id, ratio)
values (-1.8567844, 3.213108, 1, 6);
insert into locations(longitude, latitude, crop_id, ratio)
values (-2.8472767, 2.218816, 1, 5);
insert into locations(longitude, latitude, crop_id, ratio)
values (-3.8209738, 4.256325, 2, 3);
insert into locations(longitude, latitude, crop_id, ratio)
values (-5.8690081, 4.256325, 2 ,7);
insert into locations(longitude, latitude, crop_id, ratio)
values (-2.8209758, 4.256925, 2, 1);
insert into locations(longitude, latitude, crop_id, ratio)
values (-6.9846515, 8.641328, 3, 3);
insert into locations(longitude, latitude, crop_id, ratio)
values (-1.8522288, 4.203834, 4, 9);
insert into locations(longitude, latitude, crop_id, ratio)
values (-6.9841382, 4.203834, 4, 2);
insert into locations(longitude, latitude, crop_id, ratio)
values (-5.0165485, 3.213108, 5, 2);
insert into locations(longitude, latitude, crop_id, ratio)
values (-9.6548321, 5.659879, 6, 8);