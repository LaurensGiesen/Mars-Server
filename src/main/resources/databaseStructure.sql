drop table if exists addresses, baskets, favorites, harvests, histories, plants, seeds, subscriptions, users, users_harvests, users_histories, locations;

create table subscriptions(
id int auto_increment not null,
name enum('free', 'basic', 'premium') not null,
price double not null,
primary key (id)
);

create table addresses (
id int auto_increment not null ,
street nvarchar(50) not null,
number int not null,
dome nvarchar(50) not null,
primary key (id)
);

create table users(
userid int auto_increment not null,
firstname nvarchar(255) not null,
lastname nvarchar(255) not null,
email nvarchar(255) not null,
date_of_birth nvarchar(255) not null,
subscription_id int not null,
address_id int not null,
primary key (userid),
foreign key (subscription_id) references subscriptions(id)
);

create table seeds(
id int auto_increment not null,
name varchar(255) not null,
price double not null,
owner_id int not null,
date nvarchar(255) not null,
amount int not null,
image nvarchar(1024),
primary key (id),
foreign key (owner_id) references users(userid)
);

create table plants(
id int auto_increment not null,
name varchar(255) not null,
price double not null,
owner_id int not null,
date nvarchar(255) not null,
amount int not null,
image nvarchar(1024),
primary key (id),
foreign key (owner_id) references users(userid)
);

create table histories(
history_id int auto_increment not null,
plant_id int,
seed_id int,
date date not null,
status varchar(255) not null,
count int not null,
primary key (history_id),
foreign key (plant_id) references seeds(id),
foreign key (seed_id) references plants(id)
);

create table harvests (
harvestid int auto_increment not null,
plantid int not null,
amount int not null,
primary key (harvestid),
foreign key (plantid) references plants(id)
);
create table users_harvests(
harvestid int not null,
userid int not null,
foreign key (harvestid) references harvests(harvestid),
foreign key (userid) references users(userid)
);
create table users_histories(
historyid int not null,
userid int not null,
foreign key (historyid) references histories(history_id),
foreign key (userid) references users(userid)
);

create table baskets(
id int auto_increment not null,
user_id int not null,
product_id int not null,
product_type enum('seed', 'plant') not null,
foreign key (user_id) references users(userid)
);

create table favorites(
id int auto_increment not null,
user_id int not null,
product_id int not null,
product_type enum('seed', 'plant') not null,
foreign key (user_id) references users(userid)
);