
## 📝 Step 3. 요구사항 정의 및 시퀀스 다이어그램, ERD 설계
👉🏻[요구사항 정의](https://github.com/MinjiY/ecommerce-practice/blob/step03/specification.md#-요구사항-정의)   
👉🏻[이벤트 스토밍](https://github.com/MinjiY/ecommerce-practice/blob/step03/specification.md#-이벤트-스토밍)   
👉🏻[시퀀스 다이어그램](https://github.com/MinjiY/ecommerce-practice/blob/step03/specification.md#-시퀀스다이어그램)   
👉🏻[ERD 설계](https://github.com/MinjiY/ecommerce-practice/blob/step03/specification.md#-erd-설계)   

## 💻 Step 4. Mock API, Swagger-UI 
👉🏻[Mock API](https://github.com/MinjiY/ecommerce-practice/blob/step04/specification.md#1-mock-api)   
👉🏻[Swagger-UI](https://github.com/MinjiY/ecommerce-practice/blob/step04/specification.md#2-swagger-ui)

---
## 📖 시나리오
### 주문 시나리오
사용자는 상품을 선택하여 주문을 위해 주문서 작성으로 넘어간다.
주문서 작성중에 쿠폰을 사용하고 싶다면 모달을 띄워 현재 사용 가능한 쿠폰을 조회한다, 사용 가능한 쿠폰은 여러개일 수 있다.
사용 가능한 쿠폰을 확인하고 리스트 중 사용할 쿠폰을 선택해서 현재 결제 금액에 할인된 금액을 적용한다.
결제를 위해서는 충전된 금액이 있어야 하며, 충전된 금액이 부족하면 결제를 할 수 없다.
결제 금액이 충전된 금액보다 크면 결제할 수 없다는 메시지를 띄운다.


## 📄 요구사항 정의
### 기능 요구사항
- 사용자는 결제에 사용될 금액을 충전한다.
- 고객이 상품을 주문한다.
- 주문하기 위해 충전된 금액으로 결제한다.
- 상품 다중선택으로 주문할 수 있다.
- 주문(결제)시 잔액을 차감한다.
- 주문이 완료되면 주문완료 상태로 전환된다.
- 주문이 완료되면 주문한 상품의 재고가 감소한다.
- 사용자는 자신의 잔액을 조회한다.
- 상품을 조회할 수 있다. (ID, 이름, 가격, 잔여수량)
- 사용자는 최근 3일간 가장 많이 팔린 상위 5개 상품을 조회한다.
- 사용자는 자신의 주문 상태를 확인한다.
- 주문을 취소하면 취소한 금액이 충전 잔액에 더해진다.
- 주문을 취소하면 재고가 다시 증가한다.
- 주문을 취소하면 쿠폰이 다시 사용가능 상태가 된다.
- 주문을 취소하면 주문취소 상태로 전환된다.
- 사용자는 선착순으로 쿠폰을 발급받는다.
- 쿠폰 100명의 선착순시 101명부터 쿠폰 발급은 실패해야한다.
- 쿠폰 사용시 전체 주문금액을 할인 받는다.
- 쿠폰은 만료시간에 만료된다.
- 쿠폰은 주문당 1개 사용한다.
- 주문할때 쿠폰 사용시, 할인 받은 금액만큼 충전금액을 차감한다.
- 사용자는 보유 쿠폰을 조회한다.

### 비기능 요구사항
- 트랜잭션
    - 주문 성공시 재고차감은 비동기적으로 반영한다.
    - 주문 성공시 주문 완료상태가 되어야 한다.
    - 주문이 실패하면 금액차감이 실패해야한다.
    - 금액이 부족하면 주문할 수 없다.
    - 주문 취소시 충전 잔액 증가가 실패하면 주문취소도 실패한다.
    - 모든 트랜잭션 실패는 Retry 3회 시도해야한다.
- 장애격리
    - 충전된 금액이 조회되지않으면 주문할 수 없다.
    - 주문(결제)시스템이 과중되면 사용자를 잠시 받지않고 결제를 잠시후에 하도록 유도한다. (Circuit breaker, fallback)
    - 최근 3일간 가장 많이 팔린 상위 5개 상품을 조회할 수 없다면 사용자가 가장 많이 구입했던 상품 카테고리에 해당하는 상품을 랜덤하게 추천한다.
- 성능
    - 시간당 평균 이용자가 10만명이라고 할때, 주문(결제)는 초당 1000명까지 요청 가능해야한다.

---
## 📊 이벤트 스토밍
1. 도메인 이벤트 도출
![image/event.png](image/event.png)

2. 커맨드 도출 (이벤트를 트리거하는 행동)
![image/event2.png](image/event2.png)

3. 액터 도출
![image/event3.png](image/event3.png)

4. 외부서비스 도출
![image/event4.png](image/event4.png)

5. 어그리게이터 도출 (상태가 변경되는 데이터 묶음, 연관되는 엔티티와 값 객체의 묶음)
![image/event5.png](image/event5.png)

6. Bounded Context 정의
![image/event6.png](image/event6.png)

7. 정책 도출
![image/event7.png](image/event7.png)

8. 호출관계 명확하게 하기
![image/event8.png](image/event8.png)


## 📌 시퀀스다이어그램

### 1. 금액 충전
![image/charge.png](image/charge.png)

### 2. 상품 주문
![image/order.png](image/order.png)

### 3. 쿠폰 발급
![image/coupon.png](image/coupon.png)

### 4. 쿠폰 만료
![image/expired_coupon.png](image/expired_coupon.png)


---
## 👩‍💻 ERD 설계
[DDL 파일](https://github.com/MinjiY/ecommerce-practice/blob/step03/ddl.sql)
![image/erd.png](image/erd.png)   
- 하나의 쿠폰을 여러 사용자가 발급받을 수 있다.
- 한명의 사용자는 여러 쿠폰을 발급받을 수 있다.   
=> `MAP_USER_COUPON` 테이블을 통해 다대다 관계를 표현한다.
- 하나의 쿠폰 정책을 여러 쿠폰이 가질 수 있다.   
=> ex) 1월 30% 할인 쿠폰, 선착순 30% 할인쿠폰


<details><summary> DDL </summary>

```sql
CREATE TABLE USERS
(
    USER_ID  BIGINT AUTO_INCREMENT PRIMARY KEY,
    ACCOUNTS VARCHAR(30) NOT NULL
);
CREATE TABLE COUPONS
(
    COUPON_ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    COUPON_POLICY_ID BIGINT      UNIQUE NOT NULL,
    COUPON_STATE     VARCHAR(20) NOT NULL,
    FOREIGN KEY (COUPON_POLICY_ID) REFERENCES COUPON_POLICIES(COUPON_POLICY_ID)
);
CREATE TABLE COUPON_POLICIES
(
    COUPON_POLICY_ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    EXPIRATION_DAYS    INT         NOT NULL,
    COUPON_ID          BIGINT      NOT NULL,
    COUPON_TYPE        VARCHAR(20) NOT NULL,
    ISSUABLE_QUANTITY  INT         NOT NULL,
    REMAINING_QUANTITY INT         NOT NULL,
    DISCOUNT_RATE      DECIMAL(5, 2),
    DISCOUNT_AMOUNT    INT
);
CREATE TABLE MAP_USER_COUPON
(
    USER_ID   BIGINT NOT NULL,
    COUPON_ID BIGINT NOT NULL,
    PRIMARY KEY (USER_ID, COUPON_ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID),
    FOREIGN KEY (COUPON_ID) REFERENCES COUPONS (COUPON_ID)
);
CREATE TABLE ORDERS
(
    ORDER_ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID         BIGINT      NOT NULL,
    ORDER_STATUS    VARCHAR(20) NOT NULL,
    TOTAL_AMOUNT    BIGINT      NOT NULL,
    DISCOUNT_AMOUNT BIGINT      NOT NULL,
    ORDERED_AT      TIMESTAMP   NOT NULL,
    UPDATED_AT      TIMESTAMP   NOT NULL
);
CREATE TABLE ORDER_ITEMS
(
    ORDER_ITEM_ID  BIGINT AUTO_INCREMENT PRIMARY KEY,
    ORDER_ID       BIGINT       NOT NULL,
    USER_ID        BIGINT       NOT NULL,
    PRODUCT_NAME   VARCHAR(255) NOT NULL,
    PRODUCT_AMOUNT BIGINT       NOT NULL,
    ORDER_QUANTITY INT          NOT NULL,
    PRODUCT_ID     BIGINT       NOT NULL,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID)
);
CREATE TABLE PAYMENT_HISTORY
(
    PAYMENT_HISTORY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ORDER_ID           BIGINT      NOT NULL,
    USER_ID            BIGINT      NOT NULL,
    AMOUNT             BIGINT      NOT NULL,
    DISCOUNT_AMOUNT    BIGINT      NOT NULL,
    COUPON_ID          BIGINT,
    PAYMENT_STATUS     VARCHAR(20) NOT NULL, -- SUCCESS, FAILED, CANCELED
    REQUESTED_AT       TIMESTAMP   NOT NULL,
    UPDATED_AT         TIMESTAMP   NOT NULL,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID)
);
CREATE TABLE PRODUCTS
(
    PRODUCT_ID    BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME          VARCHAR(255) NOT NULL,
    DESCRIPTION   TEXT         NOT NULL,
    CATEGORY      VARCHAR(20)  NOT NULL, --
    PRICE         BIGINT       NOT NULL,
    REGISTERED_AT TIMESTAMP    NOT NULL,
    UPDATED_AT    TIMESTAMP    NOT NULL,
    QUANTITY      INT          NOT NULL,
    STATES        VARCHAR(20)  NOT NULL  -- AVAILABLE, SOLD_OUT
);
CREATE TABLE PAY_MONEY
(
    PAY_MONEY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    BALANCE      BIGINT NOT NULL,
    USER_ID      BIGINT NOT NULL
);
CREATE TABLE PAY_MONEY_HISTORY
(
    PAY_MONEY_HISTORY_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID              BIGINT      NOT NULL,
    PAY_MONEY_ID         BIGINT      NOT NULL,
    AMOUNT               BIGINT      NOT NULL,
    TRANSACTION_TYPE     VARCHAR(20) NOT NULL, -- DEPOSIT, WITHDRAW
    REQUESTED_AT         TIMESTAMP   NOT NULL,
    UPDATED_AT           TIMESTAMP   NOT NULL,
    FOREIGN KEY (PAY_MONEY_ID) REFERENCES PAY_MONEY (PAY_MONEY_ID)
);
```

</details>
---

## 💎 Mock API

### 1. Mock API
[커밋링크](https://github.com/MinjiY/ecommerce-practice/commit/9c8502869767454cac46e7d568dd08a9f02bf72b)
### 2. Swagger-UI
![image/swagger-ui.png](image/swagger-ui.png)








