select * from student
where 9 < age and age < 20;

select name from student;

select * from student
where name like '%a%';

select * from student
where age < 11;

select * from student
order by age;

// задача 4
select student.id, student."name", student.age from student, faculty
where student.faculty_id = faculty.id
and faculty."name" = 'Hufflepuff';

select faculty.id, faculty.color, faculty."name" from student, faculty
where faculty.id = student.faculty_id
and student."name" = 'ann';