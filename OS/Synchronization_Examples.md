## 7.1 고전적인 동기화 문제들

- 많은 클래스의 병행제어 문제들은 새롭게 제안된 동기화 방법들을 검증하는 데 사용된다.
    - 동기화 문제에 대한 해결책을 제시할 때 전통적으로 세마포를 사용해 왔다.
    - 그러나 실제 구현할 때에는 **이진 세마포 대신에 mutex lock이 사용**될 수 있다.

### 7.1.1 유한 버퍼 문제

- **유한 버퍼 문제** 는 일반적으로 동기화 프리미티브(primitive)들의 능력을 설명하기 위해 사용된다.
    
    🤔 **유한 버퍼 문제란(= 생산자-소비자 문제)** 여러 개의 프로세스를 어떻게 동기화할 것인가에 관한 고전적인 문제이다.  
    

- 해결하려는 문제에서 소비자와 생산자는 다음과 같은 자료구조를 공유한다.

![image](https://user-images.githubusercontent.com/87460638/235679606-1f4f4858-4a79-40e2-b5a0-cd058df94883.png)

- n개의 버퍼로 구성된 풀(pool)이 있고, 각 버퍼는 한 item을 저장할 수 있다고 가정하자.
    - mutex 이진 세마포는 버퍼 풀에 접근하기 위한 상호 배제 기능을 제공하며, 1로 초기화된다.
    - empty와 full 세마포들은 각각 비어 있는 버퍼의 수와 꽉 찬 버퍼의 수를 기록한다.
    - 세마포 empty는 n값으로 초기화되고, 세마포 full은 0으로 초기화 된다.

- **생산자 프로세스**의 구조 코드
    
    ![image](https://user-images.githubusercontent.com/87460638/235679698-6b634848-7337-40ff-80b5-c5fecc3af799.png)
    
- **소비자 프로세스**의 구조 코드
    
    ![image](https://user-images.githubusercontent.com/87460638/235679732-68b7de26-22d0-4699-b75c-22ef82241067.png)

⇒ ***생산자가 소비자를 위해 꽉 찬 버퍼를 생산해내고, 소비자는 생산자를 위해 비어 있는 버퍼를 생산한다.***

### 7.1.2 Readers-Writers 문제

- Readers와 Writers 구분하기
    - **Readers** 는 DB의 내용을 읽기만 한다.
    - **Writers** 는 DB의 내용을 갱신(즉, 읽고 쓰기)할 수 있다.

- 만약 두 reader가 동시에 공유 데이터에 접근하더라도 문제는 발생하지 않는다.
    - 그러나 서로 다른 스레드가 동시에 DB에 접근하면, 혼란이 야기될 수 있다.

- 이러한 문제점이 발생하지 않도록 보잫아기 위해, writer가 쓰기 작업 동안에 공유 DB에 대해 배타적 접근 권한을 가지게 할 필요가 있다.
    - 이 동기화 문제를 **readers-writers 문제** 라고 한다.

> 이 문제에는 여러가지 변형들이 있는데, ***모두 우선순위와 연관된 변형들***이다.
> 
1. writer가 공유 객체를 사용할 수 있는 허가를 아직 얻지 못했다면, 어느 reader도 기다리게 해서는 안된다.
- 즉, 단순히 writer가 기다리고 있기 때문에, 다른 reader들이 끝날 때까지 기다리는 reader가 있어서는 안된다.
- *writer가 기아 상태에 빠질 수 있다.*

1. writer가 준비되면 가능한 한 빨리 쓰기를 수행할 것을 요구한다.
- 즉, writer가 객체에 접근하려고 기다리고 있다면, 새로운 reader들은 읽기를 시작하지 못한다.
- *reader가 기아 상태에 빠질 수 있다.*

> 위 두 문제에 대한 해결안이 기아 문제를 낳을 수 있기 때문에, 다음과 같은 해결안이 제안된다.
> 
1. 첫번째 문제에 대한 해결안
- reader 프로세스는 다음과 같은 자료구조를 공유한다.
    - rw_mutex 세마포는 reader 와 writer가 모두 공유한다.
    - mutex 세마포는 read_count를 갱신할 때 상호 배제를 보장하기 위해 사용된다.
    - read_count는 현재 몇 개의 프로세스들이 객체를 읽고 있는지 알려준다.

![image](https://user-images.githubusercontent.com/87460638/235679795-e73de76e-8788-4262-b9ff-973b0140c878.png)

- **Writer 프로세스**의 구조
    
    ![image](https://user-images.githubusercontent.com/87460638/235679842-990c885c-200b-41d4-ad99-91e610cc58bc.png)
    
- **Reader 프로세스**의 구조
    
    ![image](https://user-images.githubusercontent.com/87460638/235679896-425bdb23-576d-4306-856a-5d3681c04631.png)

- Readers-Writers 문제와 그의 해결안들은 일반화 되어 몇몇 시스템에서는 **read-writer lock**을 제공한다.
    - 이 lock을 획득할 때에는 읽기/쓰기 모드를 지정해야만 한다.
    - 프로세스가 *공유 데이터를 읽기만* 원한다면 ⇒ **읽기 모드 요청**
    - 프로세스가 *공유 데이터의 수정*을 원한다면 ⇒ **쓰기 모드 요청**

- 읽기 모드의 reader-writer lock은 여러 개의 프로세스가 동시에 획득하는 것이 가능하다.
    - 쓰기 모드의 reader-writer lock은 writer는 공유 데이터를 배타적으로 접근해야 하므로 오직 하나의 프로세스만이 획득할 수 있다.

> Reader-writer lock은 다음과 같은 상황에서 가장 유용하다.
> 
- 공유 데이터를 읽기만 하는 프로세스와 쓰기만 하는 스레드를 식별하기 쉬운 응용
- Wrtier보다 reader의 개수가 많은 응용
    - 일반적으로 reader-writer lock을 설정하는 데에 드는 오버헤드가 세마포나 상호 배제 락을 설정할 때보다 크다.
    - 이 오버헤드는 동시에 여러 reader가 읽게 하여 병행성을 높임으로써 상쇄할 수 있다.

### 7.1.3 식사하는 철학자들 문제

- **식사하는 철학자들 문제**는 많은 부류의 병행 제어 문제의 한 예로, 고전적인 동기화 문제로 간주한다.
    - 교착 상태와 기아를 발생시키지 않고 여러 스레드에게 여러 자원을 할당해야 할 필요를 단순하게 표현한 것이다.

![image](https://user-images.githubusercontent.com/87460638/235679969-ae9d79f5-10a6-4b53-87af-a0d96561b963.png)

- 철학자 다섯 명이 원형 식탁에 앉아 밥을 먹으려고 한다. 그들의 양쪽엔 각각 젓가락 한 짝씩 놓여 있고, 밥을 먹으려 할 땐 다음의 과정을 따른다.
    1. 왼쪽 젓가락부터 집어 든다. 
    … 다른 철학자가 이미 왼쪽 젓가락을 쓰고 있다면 그가 내려놓을 때까지 대기한다.
    2. 왼쪽을 들었으면 오른쪽 젓가락을 든다. 
    … 들 수 없다면 대기합니다.
    3. 두 젓가락을 모두 들었다면 일정 시간 동안 식사 한다.
    4. 식사를 마쳤으면 오른쪽 젓가락을 내려 놓은 후 왼쪽 젓가락을 내려놓는다.
    5. 다시 배고프면 1번부터 진행한다.
    
- 철학자는 프로세스 젓가락은 자원으로 가정한 후 모든 철학자가 왼쪽 젓가락을 들은 상황을 가정해보자.
    - 그렇다면 **모든 철학자는 오른쪽 젓가락을 집기 위해 대기하는 교착상태가 발생한다.**
    - 조금 더 자세하게 교착상태 발생의 4가지 필요충분조건을 만족하는지 확인해보자.
        
        > 상호 배제 → 젓가락은 한 번에 한 철학자만 사용할 수 있다.
        > 
        > 
        > 점유와 대기 → 왼쪽 젓가락을 점유하면서 오른쪽 젓가락을 대기한다.
        > 
        > 비선점 → 이미 누군가가 집어 든 젓가락을 강제로 뺏을 수 없다.
        > 
        > 환형 대기 → 모든 철학자들이 오른쪽에 앉은 철학자가 젓가락을 놓기를 기다린다.
        > 

**7.1.3.1 세마포 해결안** 

간단한 해결책은 각 젓가락을 하나의 세마포로 표현하는 것이다.

- 철학자는 그 세마포에 `wait()` 연산을 실행하여 젓가락을 집으려고 시도한다.
    - 또한 해당 세마포에 `signal()` 연산을 실행하여 자신의 젓가락을 놓는다.
    - 따라서 공유 자료는 다음과 같다.
    
        ![image](https://user-images.githubusercontent.com/87460638/235680064-fb56b10c-077f-4565-b4dd-6a36bf26e79b.png)
   
    - 철학자 i의 구조
        
        ![image](https://user-images.githubusercontent.com/87460638/235680141-ae38f8e6-a397-4fc3-9b26-be35784518f4.png)
        
    - 이 해결안은 인접한 두 철학자가 동시에 식사하지 않는다는 것을 보장하지만, 교착 상태를 야기할 가능성이 있기 때문에 채택할 수 없다.

- 5명의 철학자 모두가 동시에 배가 고프게 되어, 각각 자신의 왼쪽 젓가락을 잡는다고 가정해 보자.
    - 각 철학자가 그의 오른쪽 젓가락을 집으려고 하면, 영원히 기다려야 하는 교착 상태가 발생할 것이다.

> 교착 상태 문제에 대한 해결책
> 
- 최대 4명의 철학자만이 테이블에 동시에 앉을 수 있도록 한다.
- 한 철학자가 젓가락 두 개를 모두 집을 수 있을 때만 젓가락을 집도록 허용한다.
    - 이렇게 하기 위해선 철학자는 임계구역 안에서만 젓가락을 집어야 한다.
- 비대칭 해결안을 사용한다.
    - 즉, 홀수 번호의 철학자는 먼저 왼쪽 젓가락을 집고 다음에 오른쪽 젓가락을 집는다.
    - 반면에, 짝수 번호의 철학자는 오른쪽 젓가락을 집고 다음에 왼쪽 젓가락을 집는다.

**7.1.3.2 모니터 해결안**

- 교착 상태가 없는 해결안으로써 **모니터 해결안**이 있다.
    - 이 해결안은 철학자는 양쪽 젓가락 모두 얻을 수 있을 때만 젓가락을 집을 수 있다는 제한을 강제한다.
    
- 이 해결안을 구현하려면, 철학자가 처할 수 있는 세가지 상태들을 구분할 필요가 있다.
    - 다음의 자료구조를 도입한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680194-48008da2-16b0-4288-8eb3-d9bc5983c1ab.png)
    
    ⇒ 철학자 i는 그의 양쪽 두 이웃이 식사하지 않을 때만 변수 `state[i] = EATING` 으로 설정할 수 있다.
    
    - 다음 코드도 선언해야 한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680253-e3cee95f-c671-4d3d-89aa-b2c539d53d4d.png)
    
    ⇒ self는 철학자 i가 배고프지만 자신이 원하는 젓가락을 집을 수 없을 때 젓가락 집기를 미룰 수 있게 한다.
    

- 젓가락 분배는 모니터 `DiningPhilosophers` 에 의해 제어된다.
    - `pickup()` 연산 : 식사하기 전에 반드시 호출해야 한다.
    - `putdown()` 연산 : 식사를 성공적으로 마친 후에 호출한다.
    - 식사하는 철학자 문제에 대한 모니터 해결안
        
        ![image](https://user-images.githubusercontent.com/87460638/235680339-fe461de4-6c85-47c0-ab28-be72cd5de47d.png)

- 이 해결안이 이웃한 두 철학자가 동시에 식사하지 않는다는 것과 교착 상태가 발생하지 않는다는 것을 보장하는 것을 보이기는 쉽다.
    - 그러나 철학자가 굶어 죽는 것이 가능하다는 것에 유의해야 한다.

## 7.2 커널 안에서의 동기화

### 7.2.1 Windows 동기화

- Windows 운영체제는 실시간 응용과 다중 처리기 지원을 제공하는 다중 스레드 커널이다.
    - Windows 커널이
    - 단일 처리기 시스템에서는 전역 정보를 액세스할 때, 동일한 전역 정보를 액세스할 가능성이 있는 인터럽트 핸들러가 실행되지 않도록 인터럽트를 잠시동안 못 걸리게 막는다.
    - 다중 처리기 시스템에서는 스핀락을 써서 전역 정보 액세스를 통제한다.

 

- 커널 외부에서 스레드를 동기화하기 위해 **dispatcher 객체**를 제공한다.

- 스레드는 dispatch 객체를 사용하여 mutex lock, 세마포, event 및 타이머를 포함한 다양한 기법에 맞춰 동기화할 수 있다.
    - 시스템은 데이터에 접근하기 위해서 스레드가 mutex의 소유권을 획득한 후, 필요한 작업이 끝난 후에는 다시 반납하게 함으로써 공동으로 사용하는 데이터를 보호한다.
    - 세마포는 공유 자원에 접근 가능한 (혹은 한번에 실행 가능한) 작업 수를 명시하고, 임계 구역에 들어갈 때는 semaphore의 `wait()`를, 나올 때는 `signal()`을 호출한다.
    - Event는 기다리는 조건이 만족하면 기다리고 있는 스레드에 통지해 줄 수 있다.
    - 타이머는 지정한 시간이 만료되면 하나(or 둘 이상의) 스레드에 통지하는 데 사용된다.

- Dispatcher 객체는 `signaled` 상태와 `nonsignaled` 상태로 나눌 수 있다.
    - `**signaled` 상태**는 객체가 사용 가능하고 그 객체를 얻을 때 그 스레드가 봉쇄되지 않음을 말한다.
    - `**nonsignaled` 상태**는 객체가 사용할 수 없고 그 객체를 얻으려고 시도하면 그 스레드가 봉쇄됨을 말한다.
    
- Dispatcher 객체의 상태와 스레드 상태 간에는 관련성이 있다.
    - 스레드가 `nonsignaled` 상태에 있는 dispatcher 객체 때문에 봉쇄되면 스레드의 상태는 ***준비 에서 대기 상태***로 바뀌고 객체의 대기 큐에 넣어진다.
    - dispatcher 객체의 상태가 `signaled` 상태로 바뀌면 커널은 그 객체를 기다리는 스레드가 있는지 여부를 알아내어 있으면 그 하나의 스레드(가능하다면 여러 스레드)를 **대기에서 준비 상태**로 바꾸어 다시 실행을 재개할 수 있도록 한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680409-b2559e14-161f-41ad-9522-675961c87233.png)

- 커널이 대기 큐로부터 선택하는 스레드의 개수는 각 스레드가 기다리고 있는 dispatcher 객체의 유형에 달려있다.
    - Mutex 객체는 오직 하나의 스레드만 소유할 수 있다. 
    ⇒ mutex의 대기 큐에서 오직 하나의 스레드만 선택한다.
    - Event 객체는 이 이벤트를 기다리고 있는 모든 스레드를 선택하게 된다.

- **Critical-section 객체**는 커널의 개입 없이 획득하거나 방출할 수 있는 사용자 모드 mutex이다.
    - 다중 처리기 시스템에서 이 객체는 처음엔 스핀락을 사용하여 다른 스레드가 객체를 방출하기를 기다린다.
    - 회전이 길어지게 되면 락을 획득하려는 프로세스는 커널 mutex를 할당하고 CPU를 양도한다.

- 커널 mutex는 객체에 대한 경쟁이 발생할 때만 할당되기 때문에 Critical-section 객체가 특히 효율적이다.
    - 실제로 경쟁은 거의 발생하지 않기 때문에 CPU 절약이 상당히 좋아진다.

### 7.2.2 Linux 동기화

- 지금의 Linux 커널은 선점형 커널로써, 커널 모드에서 실행 중일 때 태스크가 선점될 수 있다.

- Linux는 커널 안에서 동기화 할 수 있는 많은 기법을 제공한다.
    - 가장 간단한 동기화 기법은 원자적 정수이다.
    - 이러한 정수는 차단된 데이터 형인 `atomic_t` 데이터 형을 사용하여 표현된다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680503-131346ad-48d0-4d2c-854b-4b55d698eeff.png)
    
    - 다음 코드는 다양한 원자적 연산을 수행한 효과를 보여준다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680557-fb78be48-a90a-4f6e-9600-406d2cd60203.png)
    
- Linux에서 커널 안의 임계구역을 보호하기 위해 mutex lock이 제공된다.
    - 여기서 태스크는 임계구역에 들어가기 전에 `mutex_lock()` 함수를 호출해야 하고,
    나오기 전에 `mutex_unlock()` 함수를 호출해야 한다.

- Linux 커널은 커널 안에서의 락킹을 위하여 스핀락과 세마포 및 두 락의 reader-writer 버전도 제공한다.
    - 하나의 처리 코어를 가진 시스템에서는 스핀락을 획득하는 것이 아니라 커널이 커널 선점을 불가능하게 한다.
    - 또한 커널은 스핀락을 방출하는 것이 아니라 커널 선점을 가능하게 한다.
    - 요약하면 다음과 같다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680634-b616c835-f6b8-4d66-9139-3103693ecb43.png)

- **Linux 커널에서 스핀락과 mutex lock은 재귀적이지 않다.**
    - 즉, 스레드가 이러한 락 중 하나를 획득했다면 획득한 락은 해제하지 않고는 같은 락을 다시 획득할 수 없다.
    - 해제하지 않으면 락을 획득하려는 두번째 시도는 봉쇄된다.

<aside>
📌 요약하면,

***스핀락과 커널 선점 불능 및 가능은 오직 락(or 커널 선점 불가능)이 짧은 시간 동안만 유지될 때 사용된다. 락이 오랜 시간동안 유지되어야 한다면 세마포 or mutex lock을 사용하는 것이 적절하다 !***

</aside>

## 7.3 POSIX 동기화

- Pthread 및 POSIX API에서 제공하는 mutex lock, 세마포 및 조건 변수에 관해 설명한다.

### 7.3.1 POSIX mutex lock

- Mutex lock은 Pthreads에서 사용할 수 있는 기본적인 동기화 기법을 대표한다.
    - Mutex lock은 임계구역을 보호하기 위해 사용된다.
    - 즉, 스레드는 임계구역에 진입하기 전에 락을 획득하고 임계구역에서 나갈 때 락을 방출한다.

- Pthreads는 mutex lock의 데이터 형으로 `pthread_mutex_t` 데이터 형을 사용한다.
- Mutex는 `pthread_mutex_init()` 함수를 호출하여 생성하낟.
    - 첫 번째 매개변수 ⇒ mutex를 가리키는 포인터
    - 두 번째 매개변수 ⇒ NULL을 전달하여 속성을 디폴트 값으로 초기화
    
    ![image](https://user-images.githubusercontent.com/87460638/235680742-ac9fef2e-5c24-4072-8911-961e7f0aa0bc.png)
    
- Mutex는 `pthread_mutex_lock()` 과 `pthread_mutex_unlock()` 함수를 통해 각각 획득하고 방출된다.
    - Mutex lock을 획득할 수 없는 경우에 획득을 요청한 스레드는 락을 가지고 있는 스레드가 `pthread_mutex_unlock()` 함수를 호출할 때까지 봉쇄된다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680803-f3f8f0e9-9008-467a-ae04-1d6d2e9b05ba.png)
    
    - 모든 mutex 함수는 연산이 성공했을 경우 0을 반환한다.

### 7.3.2 POSIX 세마포

- 세마포는 Pthreads 표준의 일부분이 POSIX SEM 확장판의 일부이지만, Pthreads를 구현하는 많은 시스템은 세마포도 함께 제동한다.
    - POSIX는 **기명(named)**과 **무명(un-named)**의 두 유형의 세마포를 명기하고 있다.

**7.3.2.1 POSIX 기명 세마포**

![image](https://user-images.githubusercontent.com/87460638/235680865-c945ce68-6c77-43b2-9393-0e579ea39f1d.png)

- `sem_open()` 함수는 POSIX 기명 세마포를 생성하고 여는 데 사용된다.
    - SEM = 세마포
    - O_CREAT 플래그 = 세마포가 존재하지 않는 경우 생성될 것임을 나타낸다.
    - 세마포는 매개변수 0666 을 통해 다른 프로세스에 읽기 및 쓰기 접근 권한을 부여하고, 1로 초기화된다.

- 기명 세마포의 장점은 여러 관련 없는 프로세스가 세마포 이름만 참조하여 동기화 기법으로 공통 세마포를 동기화 기법으로 쉽게 사용할 수 있다는 것이다.
    - 위 예에서, 세마포 SEM이 생성되면 다른 프로세스가 `sem_opn()` 을 호출하면(매개변수는 동일한 상태로) 기본 세마포의 디스크립터로 반환한다.

- POSIX는 `wait()` `signal()` 연산을 각각 `sem_wait()` `sem_post()` 로 선언한다.
    - 다음 코드 예제는 생성된 기명 세마포를 사용하여 임계구역을 보호하는 방법을 보여준다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235680963-c6ba8570-8147-465f-b916-8f8b22cce5a7.png)

- Linux 및 macOS 시스템은 모두 POSIX 라는 세마포를 제공한다.

**7.3.2.2 POSIX 무명 세마포**

- 무명 세마포는 `sem_init()` 함수를 사용하여 생성 및 초기화되며 3개의 매개변수가 전달된다.
    1. 세마포를 가리키는 포인터
    2. 공유 수준을 나타내는 플래그
    3. 세마포의 초기 값

- 프로그래밍 예제
    
    ![image](https://user-images.githubusercontent.com/87460638/235681135-6a9c82e8-e16f-4a44-ad66-fcf49c3d7d16.png)
    
    - 플래그 0을 전달하면 세마포를 만든 프로세스에 속하는 스레드만이 이 세마포를 공유할 수 있다.
    
- POSIX 무명 세마포는 기명 세마포와 동일한 `sem_wait()` `sem_post()` 연산을 사용한다.
    - 다음 예제는 무명 세마포를 사용하여 임계구역을 보호하는 방법을 보여준다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235681230-ac4bc04e-f1a9-4853-94c7-3415b96fd71c.png)
    

### 7.3.3 POSIX 조건 변수

- Pthread는 일반적으로 C프로그램에서 사용되며 C에는 모니터가 없으므로 **조건변수에 mutex lock을 연결하여 락킹을 제공한다.**

- Pthread의 조건 변수는 `pthread_cond_t` 데이터 유형을 사용하고 `pthread_cond_init()` 함수를 사용하여 초기화된다.

- 다음 코드는 조건 변수 및 관련 mutex lock을 생성하고 초기화 한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235681395-7f211927-d646-4f90-81c1-12ec77e1d646.png)
    
    - `pthread_cond_wait()` 함수는 조건변수를 기다리는 데 사용된다.
    
- 다음 코드는 Pthread 조건 변수를 사용하여 스레드가 조건 a==b가 true가 될 때까지 대기하는 방법이다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235681448-85c87335-b06c-404d-bb6a-bc9e606a2e8b.png)
    
    - 조건 변수와 연관된 mutex lock은 `pthread_cond_wait()` 함수가 호출되기 전에 획득되어야 한다.
    - lock이 획득되면 스레드가 조건을 확인할 수 있다.
    - 조건이 true가 아닌 경우 `pthread_cond_wait()` 를 호출하게 되고, 이때 mutex lock이 해제되어 다른 스레드가 공유 데이터에 접근한다. 이후 해당 값을 갱신하여 조건 절이 true로 변경된다.
    
- 공유 데이터를 변경하는 스레드는 `pthread_cond_signal()` 함수를 호출하여 조건 변수를 기다리는 하나의 스레드에 신호할 수 있다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235681780-b01861d2-c28e-45ee-b0a3-6775835ebb2f.png)
    
    - `pthread_cond_signal()` 호출은 mutex lock을 해제한 후에 호출된다.
    - mutex lock이 해제되면 신호받은 스레드는 mutex lock의 소유자가 되고, `pthread_cond_wait()` 호출에서부터 제어를 넘겨받아 실행을 재개한다.

## 7.4 Java에서의 동기화

- Java 언어와 API는 언어의 기원부터 스레드 동기화에 대한 풍부한 자원을 제공했다.
    
    > 1. Java 모니터
    2. 재진입 락
    3. 세마포
    4. 조건 변수
    > 
    

### 7.4.1 Java 모니터

- Java는 스레드 동기화를 위한 모니터와 같은 병생성 기법을 제공한다.
    - `BoundedBuffer` 클래스는 생산자와 소비자 문제의 해결안을 구현하며, 생산자와 소비자는 각각 `insert()` `remove()` 메소드를 호출한다.
    - Java 동기화를 사용한 유한 버퍼 코드
        
        ![image](https://user-images.githubusercontent.com/87460638/235681602-0510084a-fa42-4454-889a-2e4c06d98efe.png)

- Java의 모든 객체는 하나의 락과 연결되어 있다.
    - 메소드가 synchronized 로 선언된 경우, 메소드를 호출하려면 그 객체와 연결된 락을 획득해야 한다.
    - `BoundedBuffer` 클래스의 `insert()` `remove()` 메소드와 같은 메소드를 정의할 때 `synchronized` 를 선언하면 `synchronized` 메소드가 된다.

- `synchronized` 메소드를 호출하려면 `BoundedBuffer` 객체 인스턴스와 연결된 락을 소유해야 한다.
    - 다른 메소드가 이미 락을 소유한 경우 `synchronized` 메소드를 호출한 스레드는 봉쇄되어 객체의 락에 설정된 **진입 집합(entry set)**에 추가된다.

- 진입 집합은 락이 가용해지기를 기다리는 스레드의 집합을 나타낸다.
- 진입 집합 작동 방식
    - `synchronized` 메소드를 호출할 때 락이 가용한 경우, 호출 스레드는 객체 락의 소유자가 되어 메소드로 진입한다.
    - 스레드가 메소드를 종료하면 락이 해제된다.
    - 이때 락에 대한 진입 집합이 비어 있지 않으면 JVM은 이 집합에서 락 소유자가 될 스레드를 임의로 선택한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235681888-ebd9619f-a88e-42b6-af68-39a54628776f.png)
    

- 락을 갖는 것 외에도 모든 객체는 스레드 집합으로 구성된 **대기 집합**과 연결된다.
    - 대기 집합은 처음에 비어 있다.
    - 스레드가 `synchronized` 메소드에 들어가면 객체 락을 소유한다.
    - 그러나 이 스레드는 특정 조건이 충족되지 않아 계속할 수 없다고 결정할 수 있다.
    
- 스레드가 `wait()` 메소드를 호출하면 다음이 발생한다.
    1. 스레드가 객체의 락을 해제한다.
    2. 스레드 상태가 봉쇄됨을 설정된다.
    3. 스레드는 그 객체의 대기 집합에 넣어진다.

- wait() 과 notify() 를 사용하는 insert() 와 remove() 메소드
    
    ![image](https://user-images.githubusercontent.com/87460638/235682104-9d2cbbcd-af03-480d-932e-a9d31b874be4.png)
    
- 위 코드는 다음과 같이 설명된다.
    - 생산자가 `inert()` 메소드를 호출하고 버퍼가 가득 찬 걸 확인하면 `wait()` 메소드를 호출한다.
        - 이 호출은 락을 해제하고, 생산자를 봉쇄하여 생산자를 개체의 대기 집합에 둔다.
    - 생산자가 락을 해제했기 때문에 소비자는 궁극적으로 `remove()` 메소드로 진입하여 생산자를 위한 버퍼의 공간을 비운다.
    - 락을 위한 진입 및 대기 집합을 보여준다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235682030-e9cc3fce-e575-44f5-9cf8-f33a6a2eaa1d.png)
    

**❓ 소비자 스레드는 생산자가 이제 진행할 수 있다는 것을 어떻게 알리는가 ?**

🅰️ `insert()` 및 `remove()` 메소드 끝에서 `**notify()` 메소드를 호출한다.**

- `notify` 메소드는 다음과 같은 일을 수행한다.
    1. 대기 집합의 스레드는 리스트에서 임의의 스레드 T를 선택한다.
    2. 스레드 T를 대기 집합에서 진입 집합으로 이동한다.
    3. T의 상태를 봉쇄됨에서 실행 가능으로 설정한다.

### 7.4.2 재진입 락

- API에서 사용 가능한 가장 간단한 락 기법은 **ReentrantLock** 이다.
    - **ReentrantLock**은 단일 스레드가 소유하며 공유 자원에 대한 상호 배타적 액세스를 제공하는 데 사용된다
    - 그러나 ReentrantLock은 *공정성* 매개변수 설정과 같은 추가적인 기능을 제공한다.

- 스레드는 `lock()` 메소드를 호출하여 ReentrantLock 락을 획득한다.
    - 락을 사용할 수 있거나 `lock()` 을 호출한 스레드가 이미 락을 소유하고 있는 경우, `lock()` 은 호출 스레드에게 락 소유권을 주고 제어를 반환한다.
    - 락을 사용할 수 없는 경우, 호출 스레드는 소유자가 `unlock()` 을 호출하여 락이 배정될 때까지 봉쇄된다.

- ReentrantLock 은 Lock 인터페이스를 구현한다.

![image](https://user-images.githubusercontent.com/87460638/235682344-4d35b8eb-7151-4400-9e42-0989f0bfccd3.png)

- ReentrantLock 은 상호 배제를 제공하지만 여러 스레드가 공유 데이터를 읽기만 하고 쓰지 않을 때에는 너무 보수적인 전략일 수 있다.
    - 이를 해결하기 위해 Java API는 ReentrantReadWriteLock을 제공한다.
    - ReentrantReadWriteLock은 reader는 여러 개일 수 있지만 writer는 반드시 하나여야 한다.

### 7.4.3 세마포

- Java API는 카운팅 세마포를 제공한다.
- 세마포의 생성자는 다음과 같다.
    
    ![image](https://user-images.githubusercontent.com/87460638/235682399-993dd5b4-e854-4a60-85fd-6eb95a533498.png)
    
    - value는 세마포의 초기 값을 지정한다 (음수 값 허용)

- 상호 배제를 위해 세마포를 사용하는 방법
    
    ![image](https://user-images.githubusercontent.com/87460638/235682436-cb2ae0a6-4957-47eb-9584-cb71c7656060.png)
    
    - 락을 획득하려는 스레드가 인터럽트 되면 `acquire()` 메소드가 IntterruptedException을 발생시킨다.
    - 세마포가 반드시 해제될 수 있도록 `realease()` 호출을 finally 절 안에 배치한다.

### 7.4.4 조건 변수

- **조건 변수**는 특정 조건을 만족하기를 기다리는 변수라는 의미이다.
    - 따라서 이를 이용하여 주로 스레드간의 신호 전달을 위해 사용한다.
    - 조건 변수는 `wait()` `notify()` 메소드와 유사한 기능을 제공한다.
    - 하나의 스레드가 **waiting** 중이면 조건을 만족한 스레드에서 변수를 바꾸고 **signaling**을 통해 깨우는 방식이다.
    
- 조건 변수 사용법
    - 조건 변수는 waiting과 signaling을 사용한다.
    - 따라서 기본적으로 cond_wait()와 cond_signal() 함수를 사용하게 된다.
    - 또한 wait와 signal 내부적으로 `unlock()`과 `lock()`이 각각 앞 뒤로 있기 때문에 외부를 `lock()`과 `unlock()`으로 감싸야 한다.
- Java 조건 변수를 사용하는 예제
    
    ![image](https://user-images.githubusercontent.com/87460638/235682501-d336fa3c-736f-4141-a803-88c8187a83e2.png)
    

## 7.5 대체 방안들

- 다중 코어 시스템의 등장과 함께 여러 처리 코어의 이점을 극대화할 수 있는 병행 응용이 증가하게 되었다.
    - 그러나 병행 응용은 경쟁 조건과 교착 상태와 같은 라이브니스 위험을 증가시킨다.
    - 이를 해결하기 위해 mutex lock, 세마포, 모니터와 같은 기법들이 사용되어 왔다.
    - 그러나 이러한 해결안은 처리 코어의 개수가 증가할수록 처리가 어렵다.
- 따라서 스레드-안전 병행 응용 설계에 도움을 줄 수 있는 프로그래밍 언어와 하드웨어의 다양한 기능을 살펴보자.

### 7.5.1 트랜잭션 메모리

- **트랜잭션 메모리**는 DB 이론 분야에서 출발한 아이디어지만 프로세스 동기화 전략을 제공한다.
- **메모리 트랜잭션**은 메모리 읽기와 쓰기 연산의 원자적인 연속적 순서이다.

- 한 트랜잭션의 모든 연산이 완수되면 메모리 트랜잭션은 확장(commit)된다.
    - 그렇지 않다면, 그 시점까지 완수된 모든 연산은 취소되고 트랜잭션 시작 이전의 상태로 되돌려야(roll-back)한다.
    - 트랜잭션 메모리의 이점을 활용하기 위해서는 프로그래밍 언어에 이를 위한 새로운 기능을 추가해야 한다.

- 공유 데이터를 수정하는 update() 함수가 있다고 가정하자.

![image](https://user-images.githubusercontent.com/87460638/235682572-3747a4a8-60ce-4311-afe6-3d5693bb46c0.png)

- 전통적으로 이러한 함수는 mutex lock 또는 세마포로 구현된다.
- 하지만 이 기법을 사용하는 것은 교착 상태와 같은 많은 잠재적인 문제를 야기할 수 있다.

![image](https://user-images.githubusercontent.com/87460638/235682611-0156466e-e64d-4b6f-9023-bb0923b94130.png)

- 전통적인 기법에 대한 대안으로 프로그래밍 언어를 추가한다.
- 새로운 구조물 `atomic{S}` 를 추가함으로써 메모리 시스템이 *원자성을 보장할 책임*이 있다.
- 또한 락이 전혀 사용되지 않기 때문에 *교착 상태가 발생하는 것은 불가능*하다.
- 게다가 트랜잭션 메모리 시스템은 원자적 믈록 내에서 공유 변수에 대한 병행 읽기 연산과 같은 *병행 실행될 수 있는 명령문들을 구별할 수 있다.*

- 트랜잭션 메모리는 **소프트웨어 or 하드웨어**로 구현될 수 있다.
    - **소프트웨어 트랜잭션 메모리(STM)**는 특별한 하드웨어 없이 소프트웨어만으로 구현된다.
    - **하드웨어 트랜잭션 메모리(HTM)**는 개별 처리기 캐시에 존재하는 공유 데이터의 충돌을 해결하고 관리하기 위하여 하드웨어 캐시 계층 구조와 캐시 일관성 프로토콜을 사용한다.
    
- STM은 트랜잭션 블록 안에 검사 코드를 삽입함으로써 동작한다.
    - 이 코드는 컴파일러에 의해 삽입되어 명령문들이 동시에 실행될 수 있는 지점과 저수준 락킹이 필요한 지점을 검사함으로써 각 트랜잭션을 관리한다.

- HTM은 코드 계측이 필요 없다.
    - 따라서 STM보다 적은 오버헤드를 가진다.
    - 그러나 기존의 캐시 계층 구조와 캐시 일관성 프로토콜을 트랜잭션 메모리를 지원하기 위하여 변경해야 한다.

### 7.5.2 OpenMP

- **OpenMP**는 컴파일러 디렉티브와 API로 구성된다는 것을 기억하자.
    - OpenMP가 가지는 장점은 스레드의 생성과 관리가 OpenMP 라이브러리에 의해서 처리되어 응용 개발자들은 신경쓰지 않아도 된다는 것이다.

- `#pragme omp parallel` 컴파일러 디렉티브와 함께 OpenMP는 `#pragme omp ciritical` 디렉티브를 제공한다.
    - 이 디렉티브는 디렉티브 이후에 나오는 코드 구역을 임계구역으로 지정하여 한 번에 하나의 스레드만이 실행할 수 있게 한다.
    - 이러한 식으로 OpenMP는 스레드가 경쟁 조건을 발생시키지 않는다는 것을 보장할 수 있도록 지원한다.

- OpenMP에서 임계구역 컴파일러 디렉티브를 사용할 때의 이점은 표준 mutex lock보다 쉽게 사용할 수 있다.
    - 그러나 응용 개발자가 가능한 경쟁 조건을 직접 발견 해야만 하고 컴파일러 디렉티브를 이용하여 공유 메모리를 직접 보호해야 한다는 단점이 있다.
    - 추가로 임계구역 컴파일러 디렉티브는 mutex lock처럼 동작하기 때문에 두 개 이상의 임계구역이 관여되었을 때 여전히 교착 상태가 발생할 수 있다.
    

### 7.5.3 함수형 프로그래밍 언어

- 가장 널리 알려진 프로그래밍 언어인 C, C++, Java와 C#은 **명령형** 또는 **절차형** 언어이다.
    - 이러한 언어에서 알고리즘의 흐름은 올바른 동작에 필수적이고, 상태는 변수와 다른 자료구조를 통해서 표현된다.

- 다중 코어 시스템에서 병행 및 병렬 프로그래밍이 주목받으면서 **함수형 프로그래밍 언어**에 대한 관심이 증가했다.
    - **함수형 프로그래밍 언어**는 명령형 언어가 제공하는 패러다임과는 많이 다른 패러다임을 따른다.
    - 변수가 정의되어 값을 배정받으면 그 값을 변경될 수 없기 때문에 변하지 않는다.
    - 함수형 언어는 변경 가능 상태를 허용하지 않기 때문에 경쟁 조건이나 교착 상태와 같은 쟁점에 대해 신경 쓸 필요가 없다.
    
- 함수형 언어 중 Erlang과 Scala가 있다.
    - Erlang 언어는 병행성과 병령 시스템에서 실행되는 응용을 개발하기 쉬운 언어라는 점에서 큰 주목을 받았다.
    - Scala 는 함수형 언어이면서 객체지향이기도 하다.