create table poder_unico(
id bigint not null auto_increment primary key,
id_pokemon_unico bigint,
id_wild_pokemon bigint,
id_poder bigint not null,
level int not null,
some_effect varchar(100)
)