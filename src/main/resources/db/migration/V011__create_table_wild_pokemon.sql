create table wild_pokemon(
	id bigint not null auto_increment,
	id_pokemon bigint not null,
    tipo varchar(100) not null,
   	nivel int not null,
   	nome varchar(100),
   	novo_atk int,
   	novo_def int,
   	novo_spa int,
   	novo_spd int,
   	novo_spe int,
   	novo_hp int,
   	
   	poder1 bigint,
   	poder2 bigint,
   	poder3 bigint,
   	poder4 bigint, 	
   	
   	genero varchar(10) not null,
	vivo  bool not null,
	hp_atual int,
	stamina int,
	
	
    primary key (id)
);
