## 🌿 Rest
### 🔎 REST 란 ? (구체적으로)
> REST는 Representational State Transfer의 약어로, **표현 상태 전송**을 나타낸다.

HTTP URI을 통해 자원을 명시하고, HTTP Method(Post, Get, Put, Delete)를 통해 해당 자원에 대한 CRUD Operation을 적용한다. REST는 자원 기반의 구조(ROA, Resource Oriented Architecture) 설계의 중심에 Resource가 있고, HTTP Method를 통해 Resource를 처리하도록 설계된 아키텍처를 말한다.


### 🔎 REST vs SOAP (페이로드 처리 방식)
> SOAP는 프로토콜이고, REST는 아키텍처 스타일이며 페이로드를 처리하는 방식에 차이가 있다.

SOAP는 서비스 인터페이스를 이용해서 서버에 접근하는 반면, REST는 URI를 이용해서 접근한다. REST는 HTTP와 JSON을 사용하기 때문에 페이로드의 무게를 가볍게 할 수 있다. 그러나 SOAP는 XML에만 의존하기 때문에 페이로드가 무거운 경우에는 더 많은 리소스가 필요하다.

![](https://velog.velcdn.com/images/leeseunghee00/post/e25e5362-f45f-452b-8dcf-d0bdc9dc5190/image.png)


### 🔎 REST가 왜 필요할까 ?
> - 어플리케이션의 분리 및 통합하기 위함이다.
- 다양한 클라이언트 등장으로 서버 프로그램이 다양한 브라우저와 모바일 환경에서도 통신할 수 있도록 하기 위함이다.

### 🔎 REST 구성요소 3가지 말해봐
> _REST 구성요소는 **URI, HTTP Method, Resource** 가 있다._

- URI은 서버에 존재하며, 자원을 구별하는 ID는 HTTP URI이다.
- HTTP Method는 **GET, POST, PUT, DELETE**와 같은 메소드를 제공한다.
- Resource는 클라이언트가 자원 상태에 대한 조작을 요청하면 서버는 이에 대한 적절한 응답을 보내는데, 여기서 자원은 JSON, XML 등을 말한다.

### 🔎 REST 장단점 말해봐
[장점]
1. 여러 가지 서비스 디자인에서 생길 수 있는 문제를 최소화 해준다.
2. Hypermedia API의 기본을 충실히 지키면서 범용성을 보장한다.
3. HTTP 프로토콜 표준을 최대한 활용하여 여러 추가적인 장점을 함께 가져갈 수 있게 해준다.

[단점]
1. 브라우저를 통해 테스트할 일이 많은 서비스일 경우, 쉽게 고칠 수 있는 URI 보다 Header 값이 어렵게 느껴진다.
2. 구형 브라우저가 아직 제대로 지원하지 되지 않는다.


### 🔎 REST 특징은 ?
- **서버-클라이언트 구조 (Server-Client Structure)**
\- 자원을 가지고 있는 쪽이 서버, 자원을 요청하는 쪽이 클라이언트가 된다.
\- 서버는 API를 제공하고 비즈니스 로직을 처리 및 저장하는 역할을 한다.
\- 클라이언트는 사용자 인증이나 정보를 직접 관리한다.
- **무상태성 (Stateless)**
\- 클라이언트의 context를 서버에 저장하지 않고, 
\- 서버는 각각의 요청을 완전히 별개의 것으로 인식하고 처리한다.
- **캐치 처리 가능 (Cacheable)**
- **계층화**
- **Code-One-Demend**
- **인터페이스 일관성**
\- URI로 지정한 자원에 대한 조작을 통일되고 한정적인 인터페이스로 수행한다.
 \- HTTP 표준 프로토콜에 따르는 모든 플랫폼에서 사용이 가능하다.


### 🔎 REST API 란 ?
> REST API는 **두 데이터 간의 정보 교환을 가능하도록 하는 인터페이스**를 말한다.

- REST API는 REST 기반으로 서비스 API를 구현한다.

### 🔎 REST API 특징은 ?
> - 사내 시스템들도 REST 기반으로 시스템을 분산해 확장성과 재사용성을 높여 유지보수 및 운용을 편리하게 할 수 있다.
- REST는 HTTP 표준을 기반으로 구현하므로, HTTP를 지원하는 프로그램 언어로 클라이언트, 서버를 구현할 수 있다.

ex) REST API 설계
![](https://velog.velcdn.com/images/leeseunghee00/post/19cb966a-818c-4207-a83a-9559ab7182cd/image.png)

### 🔎 REST API 설계 규칙
1. 슬래시 구분자(/ )는 계층 관계를 나타내는데 사용한다.
Ex) `http://restapi.example.com/houses/apartments`

2. URI 마지막 문자로 슬래시(/ )를 포함하지 않는다.
URI에 포함되는 모든 글자는 리소스의 유일한 식별자로 사용되어야 하며 URI가 다르다는 것은 리소스가 다르다는 것이고, 역으로 리소스가 다르면 URI도 달라져야 한다.
Ex) `http://restapi.example.com/houses/apartments/` (X)

3. 하이픈(- )은 URI 가독성을 높이는데 사용
불가피하게 긴 URI경로를 사용하게 된다면 하이픈을 사용해 가독성을 높인다.

4. 밑줄(_ )은 URI에 사용하지 않는다.
밑줄은 보기 어렵거나 밑줄 때문에 문자가 가려지기도 하므로 가독성을 위해 밑줄은 사용하지 않는다.

5. URI 경로에는 소문자가 적합하다.
URI 경로에 대문자 사용은 피하도록 한다.
RFC 3986(URI 문법 형식)은 URI 스키마와 호스트를 제외하고는 대소문자를 구별하도록 규정하기 때문이다.

6. 파일확장자는 URI에 포함하지 않는다.
REST API에서는 메시지 바디 내용의 포맷을 나타내기 위한 파일 확장자를 URI 안에 포함시키지 않는다.
Accept header를 사용한다.
Ex) `http://restapi.example.com/members/soccer/345/photo.jpg` (X)
Ex) GET / members/soccer/345/photo HTTP/1.1 Host: restapi.example.com Accept: image/jpg (O)

7. 리소스 간에는 연관 관계가 있는 경우: `/리소스명/리소스 ID/관계가 있는 다른 리소스명`
Ex) GET : /users/{userid}/devices (일반적으로 소유 ‘has’의 관계를 표현할 때)
`https://gmlwjd9405.github.io/2018/09/21/rest-and-restful.html`
