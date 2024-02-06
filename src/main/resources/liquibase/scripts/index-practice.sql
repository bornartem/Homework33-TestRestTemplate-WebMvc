-- liquibase formatted sql
-- changeset bornartem:1
select name from student;
-- changeset bornartem:2
alter table car
add japanese_car varchar;
--changeset bornartem:3
alter table car
drop japanese_car;
--changeset bornartem:4
select color, name from faculty;