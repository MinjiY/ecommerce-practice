create table users
(
    user_id  bigint auto_increment primary key,
    accounts varchar(30) not null
);
create table coupon
(
    coupon_id          bigint auto_increment primary key,
    coupon_name        varchar(50) not null,
    expiration_date    date        not null,
    issuable_quantity  int         not null, -- 발급 가능한 쿠폰 수량
    issued_quantity    int         not null, -- 발급된 쿠폰 수량
    remaining_quantity int         not null, -- 남은 쿠폰 수량
    discount_rate      decimal(5, 2)
);
create table map_user_coupon
(
    user_id            bigint not null,
    coupon_id          bigint not null,
    coupon_state       varchar(20) not null, -- active, used, expired
    coupon_name        varchar(50) not null,
    primary key (user_id, coupon_id)
);
create table orders
(
    order_id        bigint auto_increment primary key,
    user_id         bigint      not null,
    order_status    varchar(20) not null, -- completed, canceled
    total_amount    bigint      not null, -- 총 주문 금액
    discount_amount bigint      not null, -- 할인 금액
    paid_amount     bigint      not null, -- 결제 금액 (총 주문 금액에서 할인받은 금액)
    order_date      date        not null,
    update_date     date        not null,
    ordered_at      timestamp   not null,
    updated_at      timestamp   not null
);
create table order_item
(
    order_item_id  bigint auto_increment primary key,
    order_id       bigint       not null,
    user_id        bigint       not null,
    product_name   varchar(255) not null,
    product_amount bigint       not null,
    order_quantity int          not null,
    product_id     bigint       not null,
    order_date     date         not null,
    update_date    date         not null,
    ordered_at      timestamp   not null,
    updated_at      timestamp   not null,
    foreign key (order_id) references orders (order_id)
);
create table payment_history
(
    payment_history_id bigint auto_increment primary key,
    user_id            bigint      not null,
    amount             bigint      not null,
    discount_amount    bigint      not null,
    coupon_id          bigint,
    payment_status     varchar(20) not null, -- success, canceled
    order_date         date        not null,
    update_date        date        not null,
    ordered_at         timestamp   not null,
    updated_at         timestamp   not null
);
create table product
(
    product_id    bigint auto_increment primary key,
    name          varchar(255) not null,
    description   text         not null,
    category      varchar(20)  not null, --
    price         bigint       not null,
    created_at    timestamp    not null,
    updated_at    timestamp    not null,
    quantity      int          not null,
    product_state        varchar(20)  not null  -- available, sold_out
);
create table point
(
    point_id     bigint auto_increment primary key,
    balance      bigint not null,
    user_id      bigint not null
);
create table point_history
(
    point_history_id     bigint auto_increment primary key,
    user_id              bigint      not null,
    point_id             bigint      not null,
    amount               bigint      not null,
    transaction_type     varchar(20) not null, -- deposit, withdraw
    created_at           timestamp   not null,
    updated_at           timestamp   not null,
    foreign key (point_id) references point (point_id)
);