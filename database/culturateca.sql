create table property (
	id bigserial primary key,
	name varchar(255) not null
);

create table category (
	id bigserial primary key,
	name varchar(255) not null
);

create table canonic (
	id bigserial primary key,
	property jsonb not null,
	category bigint references category(id)
);

create table location (
	id bigserial primary key,
	name varchar(255) not null,
	latitude float,
	longitude float,
    location bigint references location(id)
);

create table copy (
	id bigserial primary key,
	property jsonb not null,
	canonic bigint references canonic(id),
	location bigint references location(id)
);

create table curator (
	email varchar(255) primary key,
	location bigint references location(id),
	password varchar(255) not null,
	name varchar(255) not null,
	gender varchar(255),
	phone varchar(255),
	isPublic bool,
	bio text,
	following jsonb,
	profilePicture varchar(255)
);

create table collection (
	id bigserial primary key,
	curator varchar(255) references curator(email),
	name varchar(255) not null,
	isPublic bool
);

create table collection_copy (
	collection bigint references collection(id),
	copy bigint references copy(id),
	primary key (collection, copy)
);
