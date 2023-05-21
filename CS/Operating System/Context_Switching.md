## 🌿 문맥 교환
### 🔎 문맥 교환이란 ?
> 문맥 교환(Context Switching) 은 컴퓨터 시스템에서 실행 중인 프로세스나 스레드 간에 작업을 전환하는 과정을 말한다.

- 문맥 교환은 현재 실행 중인 프로세스/스레드의 상태를 저장하고 다음 실행될 프로세스/스레드의 상태를 복원하는 과정을 거친다.

<br>

### 🔎 문맥 교환이 이루어지는 과정을 설명해봐
![image](https://github.com/leeseunghee00/TIL/assets/87460638/a953dbd8-bcce-40cb-89d1-f48756ba30c5)

1. 현재 실행 중인 프로세스/스레드의 상태(Context)를 저장한다.
2. 스케줄러에 의해 다음 실행될 프로세스/스레드가 선택된다.
3. 선택된 프로세스/스레드의 상태를 복원한다.
  → 이는 저장된 정보를 다시 레지스터에 적재하고 실행을 계속하는 것을 의미한다.
4. 선택된 프로세스/스레드가 실행된다.

<br>

### 🔎 문맥 교환 시 나타나는 문제점과 이를 해결하기 위한 방법에 대해 말해봐
> CPU는 한 번에 하나이 작업만을 실행할 수 있기 때문에 문맥 교환 중에는 다른 작업을 작업을 할 수 없다. <br> 
> 이로 인해 **오버헤드** 가 발생한다.

✅ 오버헤드를 해결하기 위해선 다음과 같은 방법이 있다.
- 다중 프로그래밍의 정도를 낮춰 문맥 교환이 자주 발생하지 않도록 한다.
- 스레드를 이용하여 문맥 교환 부하를 최소화시킨다.
- 스택 중심의 장비에서는 Stack 포인터 레지스터를 변경하여 프로세스 간 문맥 교환을 수행한다.