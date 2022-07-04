create table pokemon(
	id bigint not null auto_increment,
    nome varchar(50) not null,
    atk int not null,
    def int not null,
    spa int not null,
    spd int not null,
    spe int not null,
    hp int not null,
    tipo varchar(50),
    estado smallint,
    
    primary key (id)
);
