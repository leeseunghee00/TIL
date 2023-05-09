## 🌿 교착상태
### 🔎 교착상태란 ?
> 교착상태란 _**두 개 이상의 트랜잭션이 특정 자원(행 또는 테이블)의 잠금(Lock)을 획득한 채 다른 트랜잭션이 소유하고 있는 자원을 요구하며 무한정 기다리는 현상**_을 말한다.

### 🔎 교착상태 발생의 필요충분 4가지 조건 말해봐
> 교착상태가 발생하기 위해서는 다음의 4가지 조건이 충족되어야 한다.

1. **상호배제** : 한 번에 한 개의 프로세스만이 공유 자원을 사용할 수 있다.
2. **점유와 대기** : 프로세스가 할당된 자원을 가진 상태에서 다른 자원을 기다린다. 
3. **비선점** : 다른 프로세스에 할당된 자원은 사용이 끝날 때까지 강제로 뺏을 수 없다.
4. **순환 대기** : 공유 자원과 공유 자원을 사용하기 위해 대기하는 프로세스들이 원형을 이루면서 대기한다. 각 프로세스는 순환적으로 앞/뒤에 있는 프로세스가 요구하는 자원을 가지고 있다.


### 🔎 교착 상태의 빈도를 낮추는 방법 설명해봐
> - 트랜잭션을 자주 커밋한다.
- 정해진 순서로 테이블에 접근한다.
- 읽기 잠금 획득 (SELECT ~ FOR UPDATE)의 사용을 피한다.
- 한 테이블의 복수 행을 복수의 연결에서 순서 없이 갱신하면 교착상태가 발생하기 쉽다. 이 경우에는 테이블 단위의 잠금을 획득해 갱신을 직렬화 하면 동시성은 떨어지지만 교착상태를 회피할 수 있다.