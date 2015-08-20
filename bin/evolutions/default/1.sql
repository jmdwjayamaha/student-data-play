# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table school (
  school_id                 varchar(255) not null,
  name                      varchar(255),
  address                   varchar(255),
  gender_type               varchar(255),
  email                     varchar(255),
  constraint pk_school primary key (school_id))
;

create table student (
  student_number            varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  gender                    varchar(255),
  email                     varchar(255),
  disability                boolean,
  constraint pk_student primary key (student_number))
;

create sequence school_seq;

create sequence student_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists school;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists school_seq;

drop sequence if exists student_seq;

