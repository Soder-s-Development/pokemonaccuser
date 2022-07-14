create table account_validation(
    id bigint not null auto_increment primary key,
    acc_id bigint not null,
    email varchar(50) not null,
    code int not null
)