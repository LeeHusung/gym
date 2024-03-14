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
    

# 진행하면서 생긴 문제들 & 해결

### setter를 이용한 구조 설계 문제
    - 개발 초기에 setter를 이용한 자바 빈즈 패턴으로 개발을 진행하였지만, 이후 리팩토링 과정에서 불변성, 일관성 문제를 파악하고 이로 부터 안전한 Builder 패턴으로 변경하면서 인스턴스 생성 패턴의 중요성을 알게 되었고,
    이로 인해 같은 결과를 내는 코드라도, 어떻게 개발하냐에 따라 앞으로의 유지보수적인 측면과 안정성 등에서 엄청난 차이를 가져올 수 있다는 걸 깨달았습니다.

### 예외 처리
     - 문제 상황: 값을 받아올 때 NPE 문제, 런타임 중 사용자의 잘못된 입력 등 다양한 에러가 발생하였습니다. 처리하기 위해 Exception 패키지를 만들어 RuntimeException을 상속받은 클래스로 각각 관리하며 예외를 처리하였습니다.
     - 해결 방법: NPE를 방지하기 위해 Optional로 리팩토링, 또한 예외 처리를 위한 Exception 패키지를 만들어 RuntimeException을 상속받은 클래스로 각각 관리하며 해결하였습니다.
     

### 보안 문제
    • 문제 상황: Spring Security를 사용하여 인증과 인가를 구현하는 과정에서, 어떤 요청에 어떤 권한을 부여해야 하는지, 세션 관리에 어떤 전략을 사용할지 결정하는 것이 어려웠습니다.
    특히, 사용자의 권한에 따라 접근 가능한 리소스를 제한하는 것이 큰 고민이었습니다.
    • 해결 방법: 이 문제를 해결하기 위해, 먼저 웹 보안에 대한 깊은 이해가 필요했습니다. 따라서, Spring Security와 OAuth, JWT 등에 대한 다양한 자료를 찾아보고 공부하였습니다.
    그리고 이를 바탕으로 각 요청에 대한 적절한 권한을 부여하고, 세션을 효과적으로 관리하는 전략을 세웠습니다.
