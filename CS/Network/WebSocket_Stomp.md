## 🌿 WebSocket & Stomp
### 🔎 WebSocket 이란 ?
> Websocket은 클라이언트와 서버를 연결하고 실시간으로 통신이 가능하도록 하는 양방향 통신 기술이다.

쉽게 얘기하면 웹 버전의 TCP 또는 Socket 이라고 이해하면 된다.

<br>

### 🔎 socket.io 와 websocket 의 차이점 설명해줘
> - `socket.io` 는 브라우저의 종류에 상관없이 다양한 방법의 웹 기술을 실시간으로 구현할 수 있도록 한 기술이다. 
- `websocket` 은 사용할 수 있는 웹 프라우저의 종류에 제약이 있다.

<br>

### 🔎 HTTP 통신과 WebSocket 의 차이점 설명해줘
> 결정적인 차이는 **프로토콜** 이다.

- WebSocket 은 TCP 기반의 양방향 프로토콜로, 클라이언트와 서버 간의 연결을 유지하면서 실시간 데이터를 주고 받을 수 있다.
- HTTP는 요청-응답 프로토콜로, 주로 클라이언트가 서버에게 요청을 보내고 서버가 그에 대한 응답을 반환하는 방식으로 작동한다.

<br>

### 🔎 WebSocket 의 handshake 프로토콜은 어떻게 작동하는지 설명해봐
> WebSocket은 초기에 클라이언트와 서버 간의 handshake 과정을 거친다.

![](https://velog.velcdn.com/images/leeseunghee00/post/95375a47-3379-4a7b-a445-353ed74d8b69/image.png)

1. 클라이언트가 서버에 WebSocket 연결을 요청한다.
\- 이를 위해 클라이언트는 HTTP 프로토콜을 사용 → 특정 URL에 GET 요청을 보낸다.

2. 서버는 클라이언트 요청을 받고, 요청에 대한 응답으로 `HTTP 상태코드 101` 을 반환한다.

3. 클라이언트는 서버의 응답을 받으면, 웹 소켓으로 업그레이드하기 위한 추가적인 헤더와 함께 HTTP 응답을 수신한다.

4. 이후 클라이언트와 서버 간의 WebSocket 프로토콜로 통신이 이루어진다.
\- 클라이언트와 서버는 각각 WebSocket 프레임을 사용하여 데이터를 주고 받을 수 있다.
\- WebSocket 프레임은 헤어돠 페이로드로 구성되며, 이를 통해 실시간 데이터 교환을 수행한다.

_☞ 위 과정은 일반적으로 **한번만** 이루어지며, 이후 클라이언트와 서버 간에 지속적인 양방향 통신이 가능해진다._

<br>

### 🔎 STOMP 이란 ?
> STOMP(Simple Text Oriented Messaging Protocol) 의 약자로, WebSocket 위에서 동작하는 텍스트 기반 메세징 프로토콜이다.

- STOMP은 여러 프레임워크와 메시징 브로커(Broker)에서 지원되며, 브로커를 통해 다른 사용자에게 메시지를 보내거나 서버가 특정 작업을 수행하도록 메시지를 보낼 수 있다.
- STOMP은 다양한 실시간 웹 애플리케이션에서 실시간 통신과 메시징 기능을 구현하는 데에 활용된다.

<br>

### 🔎 WebSocket 대신 STOMP 를 사용하는 이유
> 각 커넥션마다 WebSocketHandler를 구현하는 것보다 Controller Annotation 이 적용한 객체를 이용해 조직적으로 관리할 수 있다.
