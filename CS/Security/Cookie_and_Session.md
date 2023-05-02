### 🔎 쿠키란 ?
> 쿠키는 _**클라이언트(브라우저) 로컬에 저장되는 키와 값이 들어있는 작은 데이터 파일**_이다.

- 사용자 인증이 유효한 시간을 명시할 수 있고, 유효 시간이 정해지면 **브라우저가 종료되어도 인증이 유지된다**는 특징이 있다.

- 쿠키 동작방식은 다음과 같다.
1. 클라이언트가 페이지를 요청하면
2. 서버에서 쿠키를 생성하고
3. HTTP 헤더에 쿠키를 포함시켜 응답한다 
4. 브라우저가 종료되어도 쿠기 만료 기간이 있다면 클라이언트에서 보관하고 있다가
5. 같은 요청을 할 경우 HTTP 헤더에 쿠키를 함께 보낸다. 
6. 서버에서 쿠키를 읽어 이전 상태 정보를 변경할 필요가 있을 때 쿠키를 업데이트하여 변경된 쿠키를 HTTP 헤더에 포함시켜 응답한다.

### 🔎 세션이란 ?
> 세션은 쿠키를 기반하고 있지만, 쿠키와 다르게 _**사용자 정보 파일을 서버 측에서 관리한다.**_

- 서버에서는 클라이언트를 구분하기 위해 **세션ID를 부여**하여 웹 브라우저가 서버에 접속해서 브라우저를 종료할 때까지 인증 상태를 유지한다.
- 서버에 사용자 정보를 관리하기 때문에, _웹 사이트에 동시 접속자가 많을수록 서버 과부하로 인해 성능이 저하될 수 있다._

- 세션 동작 방식은 다음과 같다.
1. 클라이언트가 서버에 접속 시 세션ID를 발급받는다.
2. 클라이언트는 세션ID에 대해 쿠키를 사용해서 저장하고 가지고 있다가
3. 서버에 요청할 때 쿠키의 세션ID를 서버에 전달해서 사용한다.
4. 서버는 세션ID를 받으면 별다른 작업없이 세션ID로 세션에 있는 클라이언트 정보를 가져온다.
5. 이 정보를 가지고 서버 요청을 처리하여 클라이언트에게 응답한다.

### 🔎 쿠키와 세션의 차이를 말해봐라
> 1. 가장 큰 차이점은 _**사용자의 정보가 저장되는 위치**_이다.

- 쿠키는 서버의 자원을 전혀 사용하지 않으며, 세션은 서버의 자원을 사용한다.

> 2. _**보안은 세션이 우수하다.**_

- 쿠키는 클라이언트 로컬에 저장되기 때문에 변질되거나 request에서 스니핑 당할 우려가 있다.
- 하지만 세션은 쿠키를 이용해서 세션ID만 저장하고 그것으로 구분해서 서버에서 처리하기 때문에 보안성이 좋다.

> 3. _**요청 속도는 쿠키가 우수하다.**_

- 쿠키에 정보가 있기 때문에 서버에 요청시 속도가 빠르다.
- 반면 세션은 정보가 서버에 있기 때문에 처리가 요구되므로 비교적 속도가 느린 편이다.

### 🔎 쿠키와 세션을 사용하는 이유
> HTTP 프로토콜의 특징이자 약점을 보완하기 위해서 사용한다.

### 🔎 JWT란 ?
> JWT(JSON Web Token)는 _**Json 포맷을 이용하여 Self-Contained 방식으로 사용자에 대한 속성을 저장하는 Claim 기반의 웹 토큰**_이다.

![](https://velog.velcdn.com/images/leeseunghee00/post/e3c591ab-149d-41b3-9899-10937a70cc88/image.png)


- JWT 기반 인증은 **JWT 토큰(Access Token)을 HTTP 헤더에 실어 서버가 클라이언트를 식별하는 방식**이다.
- JWT는 Json 데이터를 **base64 URL-safe Encode**를 통해 인코딩하여 직렬화한 것이다.
- 토큰 내부에는 위조/변조 방지를 위해 **개인키를 통한 전자서명**이 들어있다.
- 따라서 사용자가 JWT를 서버로 전송하면 서버는 서명을 검증하는 과정을 거치게 되며, 검증이 완료되면 요청한 응답을 돌려준다.