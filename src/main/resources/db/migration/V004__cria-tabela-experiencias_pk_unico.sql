create table pku_experiencias(
	id bigint not null primary key auto_increment,
	id_pokemon_unico bigint not null,
	batalhas_vencidas int,
	batalhas_derrotas int,
	titulos varchar(512),
	experiencia  int not null
);
alter table pku_experiencias add constraint fk_experienecias
foreign key (id_pokemon_unico) references pokemon_unico(id);