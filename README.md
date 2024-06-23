### 프로젝트 기간

2023.07 ~ 2023.09

### 개발 인원

1명 (개인 프로젝트)

### 기술스택

- SpringBoot(2.7.14)
- (Spring Data) JPA
- MySQL
- Spring Security

## **어떤 프로젝트인가요?**

- Spring Boot를 이용한 개인 프로젝트입니다.
- 운동 용품 후기 공유 게시판입니다.

## **구현 기능 및 제약조건**

![gym DB diagram](https://github.com/LeeHusung/gym/assets/128116509/7717d674-c4ae-460e-8599-0467a0d1d12a)

주문

- 회원 한명은 여러 주문을 할 수 있다.
    - User(1) - Order(N)
- 하나의 주문은 여러 상품을 담을 수 있다.
    - 다대다 관계이므로 주문과 상품 사이에 주문상품 테이블을 생성한다.
    - 다대다 관계로 풀어야 하는 이유
        - 직접 구현하는 것은 복잡하고, 일부 DBRMS는 지원하지 않는다.
        - 확장성 : 추가적인 정보를 연결 테이블에 저장 가능하다
        - 질의 용이성 : 일대다, 다대일 관계를 갖는 테이블에서 데이터를 검색하는 것이 더 간단하다.
- 하나의 주문은 하나의 배송지를 가진다.
    - Order(1) - Delivery(1)
- 하나의 주문은 주문 상태(주문 or 취소)를 가진다.

주문상품

- 주문에 대한 상품 정보들이 담겨있다.
    - 한 개의 주문은 여러 상품을 담을 수 있다.
    - Order(1) - OrderItem(N)
    - 한 개의 상품은 여러 주문에 담길 수 있다.
    - Item(1) - OrderItem(N)
    

상품

- 한 개의 상품은 여러 이미지를 가질 수 있다.
    - Item(1) - UploadFile(N)
- 상품은 Category를 가진다.
    - *`SHOULDER*, *CHEST*, *BACK*, *LEG*, *ARM*, *CORE*`

장바구니

- 어떤 회원의 장바구니인지, 담은 상품들은 무엇인지 정보를 가지고 있다.
    - 한 명의 회원은 장바구니에 여러 상품을 담을 수 있다..
    - User(1) - MyBag(N)
    - 한 개의 상품은 여러 회원의 장바구니에 담길 수 있다.
    - MyBag(1) - Item(N)

회원은 여러 후기 글을 작성할 수 있다.

회원은 중복해서 여러 답글, 댓글을 작성할 수 있다.

회원은 글, 답글, 댓글에 한 번씩만 추천할 수 있다.

[제약조건 (1)](https://www.notion.so/a4a4ee37fbae450184bf8f31927ad521?pvs=21)

# API Docs

- 커뮤니티 (Community)
    
    [/api/community](https://www.notion.so/982b39e4b63b47dea85d5aa75e87dcf2?pvs=21)
    
- 댓글 (Comment)
    
    [/api](https://www.notion.so/017f570f7b9c419f9589593c6b25e6bd?pvs=21)
    
- 상품 (Item)
    
    [/api/items](https://www.notion.so/5b206da5517c4f9b8e8ac71cccb4b57b?pvs=21)
    
- 회원 (User)
    
    [/api/user   ](https://www.notion.so/abe5f52fc75e477e80be9cb49693560a?pvs=21)
    
- 주문 (Order)
    
    [/api/order](https://www.notion.so/6f99d6722cf647b2886a3a786bea21ea?pvs=21)
    
- 장바구니 (myBag)
    
    [/api/myBag](https://www.notion.so/4f6ddd21a8064de8bb2e611c1a9c43f4?pvs=21)
    
