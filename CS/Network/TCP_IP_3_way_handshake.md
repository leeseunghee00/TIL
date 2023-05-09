## 🌿 TCP/IP 3 way handshake
### 🔎 TCP/IP 3 way handshake 란 ?
> TCP/IP 3 way handshake는 TCP/IP 통신 기법 중 하나로, _**TCP/IP 프로토콜을 이용하여 통신하기 전에 정확한 전송을 보장하고자 연결이 잘 되어있는지 확인하는 것**_을 말한다.

보통 **송수신 시작 전에** 이뤄진다. 순서는 다음과 같이 3단계로 나뉜다.

1. 클라이언트는 서버에게 접속 요청을 위한 **SYN 패킷**을 보낸다. 클라이언트는 SYN을 보낸 후 SYN/ACK 응답을 기다리는 **SYN-SENT 상태**가 된다.
2. **서버가 Listen 상태일 경우, SYN을 수신받는다.** 이후 요청 수락인 ACK와 SYN flag 패킷을 보낸다. 이때, 서버는 **SYN-RECEIVED 상태**가 된다.
3. 클라이언트는 **ACK를 서버에게 보내고**, 이후부터는 연결상태로 되어 데이터를 송수신한다.

※ SYN는 TCP에서 세션을 성립할 때 가장 보내는 패킷이다. 시퀀스 번호를 임의적으로 설정하여 세션을 연결하는 데에 사용되며, 초기에 시퀀스 번호를 보내게 된다.
![](https://velog.velcdn.com/images/leeseunghee00/post/dde48a39-2023-46a2-af48-7accb88351ef/image.png)
