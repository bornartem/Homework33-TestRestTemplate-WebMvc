alter table student add constraint age_constraint check(age<31);
alter table student add constraint name_unique unique(name);
alter table faculty add constraint color_name unique(color, name);
alter table student alter age set default 20;