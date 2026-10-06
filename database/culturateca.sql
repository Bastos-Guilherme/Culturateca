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
    location bigint references location(id),
	curator bigint references curator(id)
);

create table copy (
	id bigserial primary key,
	property jsonb not null,
	canonic bigint references canonic(id),
	location bigint references location(id)
);

create table curator (
    id bigserial primary key,
    email varchar(255) not null unique,
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
    curator bigint references curator(id),
    name varchar(255) not null,
    isPublic bool
);

create table collection_copy (
	collection bigint references collection(id),
	copy bigint references copy(id),
	primary key (collection, copy)
);

INSERT INTO property (name)
VALUES
    ('Título'),
    ('Autor'),
    ('Ano'),
    ('Editora');


INSERT INTO category (name)
VALUES
    ('Livro'),
    ('Revista'),
    ('Jogo');


INSERT INTO canonic (property, category)
VALUES
    (
        '{"1": "Dom Casmurro", "2": "Machado de Assis", "3": "1899", "4": "Editora Exemplo"}',
        1
    ),
    (
        '{"1": "O Cortiço", "2": "Aluísio Azevedo", "3": "1890", "4": "Editora Exemplo"}',
        1
    );


INSERT INTO location (
    name,
    latitude,
    longitude,
    location
)
VALUES
    (
        'São Paulo',
        -23.5505,
        -46.6333,
        NULL
    );


INSERT INTO copy (
    property,
    canonic,
    location
)
VALUES
    (
        '{"1": "Capa dura", "2": "Bom estado"}',
        1,
        1
    ),
    (
        '{"1": "Capa comum", "2": "Estado regular"}',
        1,
        1
    ),
    (
        '{"1": "Capa comum", "2": "Novo"}',
        2,
        1
    );

INSERT INTO curator (
    email,
    location,
    password,
    name,
    gender,
    phone,
    isPublic,
    bio,
    following,
    profilePicture
)
VALUES (
    'teste@culturateca.com',
    NULL,
    '$2a$10$wFSZNxkStzCxSHEJH2TYeO6sIqHqYTNEpk1ymxvALKA8tGyJbC9EK',
    'Usuário Teste',
    'MALE',
    '11999999999',
    true,
    'Usuário para demonstração.',
    '[]',
    NULL
);

INSERT INTO collection (
    curator,
    name,
    isPublic
)
VALUES
    (
        'teste@culturateca.com',
        'Minha coleção',
        true
    );

INSERT INTO collection_copy (collection, copy)
VALUES (1, 1);
