## 🌿 DNS
### 🔎 DNS란 ?
> DNS(Domain Name System)는 _**IP 주소 및 기타 데이터를 저장하고 이름별로 쿼리할 수 있게 해주는 계층형 분산 데이터베이스**_입니다. 

즉, DNS는 컴퓨터가 서로 통신하는 데 사용하는 숫자 IP 주소로 변환되는, 쉽게 읽을 수 있는 도메인 이름의 디렉터리입니다.

### 🔎 웹 통신의 큰 흐름 https://www.google.com/을 접속할 때 일어나는 일을 설명해봐라
> 1. 호스트가 google.com을 검색하면 OS에서 NIC(Network Interface Card, NIC 하나당 IP 하나씩 받을 수 있음)를 통해 전송을 요청해야 한다. 이때 도메인으로 요청을 보낼 수 없기 때문에 DNS Lookup을 수행한다.
2. 브라우저 → 호스트 → DNS cache 순으로 도메인에 매칭되는 IP를 찾는다.
3. 요청한 URL 캐시가 없으면 ISP의 DNS 서버가 www.google.com을 호스팅하고 있는 서버의 IP 주소를 찾기 위해 DNS query를 날린다.
4. 브라우저는 올바른 IP 주소를 받게 되면, 서버와 TCP connetion을 빌드한다.
5. TCP connection이 완성되면, 브라우저가 웹 서버에 HTTP 요청을 한다.
6. 서버가 요청을 처리하고 response를 생성해 HTTP response를 보낸다.
7. 브라우저가 HTML content를 보여준다.
