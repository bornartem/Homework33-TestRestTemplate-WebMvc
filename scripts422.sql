create table car(
id integer primary key,
brand varchar,
model varchar,
drive_license boolean);

create table people(
id integer primary key,
name varchar,
age integer,
drive_license boolean,
car_id integer references Car(id));

select people.id, people."name", people.age, people.drive_license from people, car
where people.car_id = car.id
and car.id = 1;