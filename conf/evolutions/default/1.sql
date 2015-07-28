# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table student (
  student_number            varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  gender                    varchar(255),
  email                     varchar(255),
  disability                boolean,
  constraint pk_student primary key (student_number))
;

create sequence student_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists student_seq;

