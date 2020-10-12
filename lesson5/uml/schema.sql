create table school (
        id integer not null primary key,
        name varchar(255) not null,
        address varchar(255) not null
);

comment on column school.name
is 'Наименование школы';

comment on column school.address
is 'Адрес';

create table student (
        id integer not null primary key,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        patronymic varchar(255),
        age integer not null,
        sex enum('Женский', 'Мужской') not null,
        school_id integer not null references school(id)
);

comment on column student.first_name
is 'Имя';

comment on column student.last_name
is 'Фамилия';

comment on column student.patronymic
is 'Отчество';

comment on column student.age
is 'Возраст';

comment on column student.sex
is 'Пол';

comment on column student.school_id
is 'Идентификатор школы, которой принадлежит ученик';

create table teacher (
        id integer not null primary key,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        patronymic varchar(255),
        age integer not null,
        sex enum('Женский', 'Мужской') not null,
        school_id integer not null references school(id)
);

comment on column teacher.first_name
is 'Имя';

comment on column teacher.last_name
is 'Фамилия';

comment on column teacher.patronymic
is 'Отчество';

comment on column teacher.age
is 'Возраст';

comment on column teacher.sex
is 'Пол';

comment on column teacher.school_id
is 'Идентификатор школы, которой принадлежит учитель';

create table school_subject (
    id integer not null primary key,
    name varchar(255) not null
);

comment on column school_subject.name
is 'Наименование предмета';

create table school_subject_studied (
    student_id integer not null references student(id),
    school_subject_id integer not null references school_subject(id)
);

comment on table school_subject_studied
is 'Связь "Ученик - изучает - предмет"';

create table school_subject_taught (
    teacher_id integer not null references teacher(id),
    school_subject_id integer not null references school_subject(id)
);

comment on table school_subject_studied
is 'Связь "Учитель - ведет - предмет"';
