## SIGNAL

> **정의**
> 
- 특정 이벤트가 발생했을 때 신호를 보내서 알려주는 것을 말한다.

> **종류**
> 

**대표적인 예: 9 vs 15**

- **signal 9**
- SIGKILL(KILL) 
****- 무조건적으로 즉시 중지한다.
- kill, 실행 중인 프로세스를 강제 종료할 때 사용한다.

- **signal 15**
- SIGTERM(TERM)
- 일반적으로 kill 시그널이 전송되기 전에 전송한다.
- 잡히는 시그널이기 때문에 종료되는 것을 트랙할 수 있다.

[리눅스마스터 1급 / 시그널 (signal) 종류](https://zidarn87.tistory.com/351)

## **SIGTERM vs SIGKILL**

> **공통점**
> 
- 프로세스를 종료시킨다.

> **차이점**
> 
- 어떻게 종료시키냐의 관점에서 차이점이 있다.

**SIGTERM**

- SIGTERM 은 signal + terminate 이다.
- 즉, 프로세스를 종료하라는 신호를 준다.
    - 강제종료보다 **종료 권고** 쪽에 가깝다.

```bash
kill processid
```

: 리눅스에서 프로세스에게 SIGTERM을 보내는 명령어 kill

```bash
kill -15 processid
```

: 시그널 번호로 kill 할 수 있다. 만약 번호를 부여하지 않을 경우, 디폴트값은 -15이다.

**SIGKILL**

- SIGKILL 은 signal + kill 이다.
- 즉, 프로세스를 **강제로 종료**시킨다.

```bash
kill -9 processid
```

> **SIGKILL 보다 SIGTERM 을 선호하는 이유**
> 
- 프로세스들이 갑자기 종료되면, 자신이 해야할 일을 다 마무리 할 수 없게 된다.
    - 그러면, 데이터나 프로세스 관점에서 비정상적인 상황이 발생할 가능성이 높아진다.

- 따라서 프로세스들이 정리할 시간을 주는 것이 좋다.
    - 이를 ***graceful shutdown*** 이라고 한다.
    - SIGTERM을 먼저 호출해야 graceful shutdown을 할 수 있다.
