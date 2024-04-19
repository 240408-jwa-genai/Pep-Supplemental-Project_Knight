-- Use this script to set up your Planetarium database

-- needed for referential integrity enforcement
PRAGMA foreign_keys = 1;

create table users(
	id integer primary key,
	username text unique,
	password text
);

create table planets(
	id integer primary key,
	name text unique,
	ownerId integer references users(id)
);

create table moons(
	id integer primary key,
	name text unique,
	myPlanetId integer references planets(id)
);
