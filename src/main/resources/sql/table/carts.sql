drop table carts cascade constraints;
create table carts
(
    cart_id       number(5) primary key,
    menu_quantity number(3) default 0,
    created_at    date      default sysdate,
    updated_at    date      default sysdate,
    user_id       number(5) not null,
    menu_id       number(5) not null,
    constraint fk_carts_user foreign key (user_id) references users (user_id),
    constraint fk_carts_menu foreign key (menu_id) references menus (menu_id)
);

commit;