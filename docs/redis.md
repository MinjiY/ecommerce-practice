## Redis의 Lock
Redis가 Lock을 구현할 수 있는 배경에는 `SET ... NX`라는 원자적 연산이 있다.
이 명령어가 분산 환경에서의 락 구현의 토대가 된다.
```shell
SET key, value NX PX 30000
```
key, value를 저장하는데 NotExists일 경우에 저장하고 30초(30000ms) 유지

```shell
DEL key
```
위 잠금에 대한 해제를 한다.



# Redis를 이용한 랭킹 시스템
## Redis 명령어를 통해 생각해본 로직 검증해보기
### 1. 3일동안 가장 많이 팔린 제품 5개
랭킹 API는 확장 가능하도록 설계하며 현재 기능 중 3일 동안 가장 많이 팔린 5개 제품을 조회하는 API가 있으므로 Redis를 이용한 랭킹 시스템을 붙여 구현한다.

```bash
rank:products:YYYYMMDD {score: 주문수량} {member: productId}
rank:products:3days {score: 주문수량} {member:productId}
```
⇒ 관리해야하는 key값

```bash
ZINCRBY rank:products:20250815 3 123
```
⇒ 주문시 오늘 날짜의 key값으로 123 productId를 가지는 제품 3개 증가 (=ZADD rank:products:20250815 INCR 3 123)


매일 23시 59분에 스케줄러를 등록해서 오늘의 producdId에 해당 하는 제품의 주문 수량을 rank:products:YYYYMMDD에 저장한다.
1) 최근 3일 합산을 캐시 키에 바로 저장
```bash
ZUNIONSTORE rank:products:day-3 3 \
rank:products:20250819 \
rank:products:20250818 \
rank:products:20250817 \
AGGREGATE SUM
```

2) 캐시 TTL (짧게)
```bash
EXPIRE {rank}:products:3day 86400 NX
```
NX: NotExists일 경우에만 TTL 설정(존재하지 않을 경우에만 TTL 설정)

3) 조회
```bash
ZREVRANGE rank:products:3day 0 4 WITHSCORES
```

5) DB조회
member인 productId 리스트로 제품정보와 주문 몇개 기록인지 Response

만약 요구사항이 변경되어 7일 동안 가장 많이 팔린 제품을 조회해야 한다면, rank:products:3days 키를 삭제하고 rank:products:7days 키를 새로 생성하여 관리하며
기존에 작성한 Redis를 사용한 랭킹 시스템이 topN개, Mdays로 메서드를 제공하기 때문에 재사용하도록한다.



