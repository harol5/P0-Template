
-- dbeaver is currently set to send all queries I write to the public schema in the example database

-- we will be using Structured Query Language to interact with our database
-- the queries we write to create/edit tables in our database are part of
-- the Data Definition sublanguage of SQL
--DDL (data definition language)
create table people(
   name varchar(50), -- create a column that allows 50 characters.
   age int -- create a column that takes whole numbers.
);


-- to add information into a table you use Data Manipulation langauge: this will let
-- you create, read, update, and delete information inside a table. These kinds of operations
-- are called the CRUD operations
--DML (data manipulation language, CRUD Operations.)

-- Create
-- varchar values in a script should be wrapped in single quotes
insert into people values('Billy Bob', 23); --create.

-- Read
-- you can select one or more columns to get, or use * to get all column info
-- select tells the db what info we want
-- where is used to filter what records should have their info returned
select * from people where age = 23; --read.

--update.
update people -- this tells the db we are updating the people table
set age = 24, name = 'Harol' -- this tells the db what we want the new value/s to be
where name = 'Billy Bob'; -- this tells the db how to filter which entries are changed

--delete.
delete from people -- what table are we removing records from
where age = 23; -- what data determines which records to delete.





-- Use this script to setup your Planetarium database

create table users(
    -- because the type of id is set to serial, any time we create a record in this table.
    -- the id value will automatically be set for us.
    -- the "primary" indicator sets a constraint on the column: any column marked.
    -- as a primary key must be unique and it must NOT be null.
	id serial primary key,
	username varchar(20) unique, -- usernames must be unique because of the unique constraint we added
	password varchar(20)
);

create table planets(
	id serial primary key,
	name varchar(20),
	ownerId int references users(id) on delete cascade
	-- anytime you see "references" you are looking at a "foreign key". All foreign keys
    -- must point to a primary key on another table
);

create table moons(
	id serial primary key,
	name varchar(20),
	myPlanetId int references planets(id) on delete cascade
);