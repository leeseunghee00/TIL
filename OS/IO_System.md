## 12.1 개관

- 입출력 장치 관련 기술들은 두 가지의 상충하는 궤도를 따라간다.
    - 관련 인터페이스의 표준화가 늘어나고 있다는 것
    - 입출력 장치가 갈수록 다양해진다는 것

⇒ *몇몇 장치들은 기존 장치와 너무 달라 운영체제와 결합하는 것이 하나의 커다란 숙제가 된다.*

- 이러한 문제는 하드웨어와 소프트웨어 기술들의 조합으로 해결할 수 있다.
    - 서로 다른 장치들의 다양성을 가려주기 위해서 운영체제 커널은 **장치 드라이버 모듈을 사용**하도록 구성되어 있다.

- **장치 드라이버**는 모든 하드웨어를 일관된 인터페이스로 표현해 주며 이러한 인터페이스를 그 보다 상위층인 커널의 입출력 서브시스템에 제공해 준다.

## 12.2 입출력 하드웨어

- 많은 종류의 장치들이 컴퓨터에서 작동된다.
    - 저장장치 (디스크, 테이프)
    - 전송 장치 (네트워크 연결, 블루투스)
    - 사용자 인터페이스 장치 (스크린, 키보드, 마우스, 오디오 입출력)
    - 등으로 나눌 수 있다.

- 하드웨어 장치는 케이블을 통하거나 무선으로 신호를 보냄으로써 컴퓨터 시스템과 통신한다.
    - 이들 장치는 **포트(port)**라 불리는 연결점을 통해 컴퓨터와 접속된다.
    - 하나 이상의 장치들이 공동으로 여러 선(wire)을 사용한다면, 이러한 선을 **버스(bus)**라고 한다.

- 장치 A가 B에 연결되고, B가 C, 이어 C가 컴퓨터의 포트에까지 연결되어 있으면 **데이지 체인(daisy chain)**이라고 부른다.
    - 데이지 체인은 통상 하나의 버스처럼 동작한다.

![image](https://user-images.githubusercontent.com/87460638/236810283-ba8d4f25-114e-4fb6-98f7-30fd4dd5d73b.png)

- 버스는 컴퓨터 구조에 널리 사용되고 신호 방식, 속도, 처리량 및 연결 방식에 따라 다양한 종류가 있다.
    - **PCI 버스(일반적인 PC system bus)**는 프로세서-메모리 서브시스템을 고속 장치와 키보드와 직렬, USB 포트처럼 상대적으로 느린 장치들을 연결하는 확장 버스(expansion bus)에 연결하는 모습을 나타낸다.
    - 4개의 디스크가 SAS 컨트롤러에 접속된 **직렬연결 SCSI(SAS) 버스에 연결**되어 있다.
    - **PCIe**는 하나 이상의 레인(lane)을 통해 데이터를 전송하는 유연한 버스이다.
    - 레인은 두 개의 신호 쌍으로 구성된다; 수신용, 전송용
    
- **컨트롤러**는 포트, 버스 또는 장치를 작동할 수 있는 전자장치 집합체이다.
    - 직렬 포트 컨트롤러는 간단한 장치 컨트롤러이다.
        - 전선에 나타나는 전기신호를 제어하는 데 쓰이고 하나의 칩(또는 칩의 일부)으로 나온다.
    - **광섬유 채널(FC)** 버스 컨트롤러는 복잡한 컨트롤러이다.
        - PC가 아닌 데이터 센터에서 사용되기 때문에 종종 컴퓨터의 버스에 연결되는 별도의 회로 보드 또는 **호스트 버스 어댑터(HBA)**로 구현된다.

### 12.2.1 메모리 맵드 입출력

> 입출력 전송을 하기 위해서 처리기는 어떻게 명령어와 데이터를 컨트롤러에 전달하는가 ?
> 
1. **특별한 입출력 명령어**를 사용하는 것 
    1. 입출력 명령어는 한 바이트나 워드를 어떤 입출력 포트 주소로 전달하도록 지정한다.
    2. 입출력 명령은 해당 장치에 맞는 버스 회선을 선택하여 장치 레지스터로 비트를 보내거나 읽어오도록 촉발한다.
2.  장치 제어 레지스터를 프로세서의 주소 공간으로 사상하는 것
    1. 이러한 방식을 **메모리 맵드 입출력 방식**이다.
    2. 각 주변 장치 레지스터들은 메모리 주소와 일대일 대응된다.
    3. CPU는 물리 메모리에 사상된 장치-제어 레지스터를 읽고 쓸 때 표준 데이터 전송 명령을 사용함으로써 입출력 요청을 수행하게 된다.

- 그래픽 컨트롤러는 기본적인 제어 연산을 위해 입출력 포트를 갖지만, 스크린에 출력할 내용을 저장하기 위해 큰 메모리 맵드 영역을 사용한다.
    - 이 메모리 영역에 데이터를 기록하면 컨트롤러가 자동으로 그것을 스크린에 출력하고,
    - 이 메모리를 기반으로 하여 스크린 이미지를 형성한다.

⇒ 이 방식은 단순해서 사용하기 좋고, 입출력 명령어를 사용하는 것보다 빠르다.

- 입출력 장치 컨트롤러는 보통 4개의 레지스터로 구성된다.
    - **입력 레지스터**
        - 호스트가 입력을 얻기 위해 읽기를 수행한다.
    - **출력 레지스터**
        - 호스트가 데이터를 출력하기 위해 쓰기를 수행한다.
    - **상태 레지스터**
        - 호스트가 읽는 용도로 사용된다.
        - 현재 명령이 완료되었는지, 오류가 있었는지와 같은 상태들을 보고한다.
    - **제어 레지스터**
        - 호스트가 주변 장치에 입출력 명령을 내리거나 장치의 모드를 변경하기 위해 쓰기를 수행하는 대상이다.

- 입출력 레지스터 등의 데이터 레지스터들은 보통 1~4바이트이다.
    - 어떤 컨트롤러들은 이러한 데이터 레지스터의 크기를 확장하기 위해 FIFO 칩들을 제공한다.
    - FIFO 칩은 데이터의 발생이 돌발적으로 많아지는 상황에서도 대응할 수 있다.

### 12.2.2 폴링

> 호스트와 입출력 하드웨어 사이의 프로토콜은 복잡하지만 기본적인 **핸드셰이킹(hand shaking)**개념은 간단하다.
> 

 예를 들어, 컨트롤러와 호스트 사이에 생산자 소비자 관계를 조정하기 위해 두 개의 비트가 사용된다고 가정하자.

1. 컨트롤러는 상태(status) 레지스터의 비지(busy) 비트를 통해 자신의 상태를 나타낸다.
2. 호스트가 반복적으로 비지 비트를 검사한다.
3. 호스트는 명령(command) 레지스터의 명령 준비 완료 비트(command-ready bit)를 통해 자신이 입출력을 원한다는 신호를 한다.
4. 컨트롤러는 바쁠 때는 비지 비트를 설정하고, 다음 명령을 받아들일 준비가 되었을 경우에는 비지 비트를 소거한다.
    1. 비트를 *설정*한다 = 비트에 1을 쓴다
    2. 비트를 *소거*한다 = 비트에 0을 쓴다

⇒ 이 루프는 바이트마다 반복된다.

- 위 단계 1에서 호스트는 **바쁜 대기(busy-waiting)**, 즉 **폴링(polling)**을 하게 된다.
    - 호스트는 이 루프를 계속 돌면서 비지 비트가 소거될 때까지 검사를 반복한다.

- 루프 반복 기간이 짧다면 문제 없지만 길어질 경우, 호스트는 다른 태스크로 전환하여 다른 일을 하다가 오는 것이 좋지 않을까 ?
    
    ⇒ 입출력 장치가 CPU에 자신의 상태 변화를 통보하는 하드웨어 기법을 **인터럽트(interrupt)**라고 한다.
    

### 12.2.3 인터럽트

![image](https://user-images.githubusercontent.com/87460638/236810333-e994848d-5cbd-41ff-ab0c-8e85905c1256.png)

1. CPU 하드웨어는 **인터럽트 요청 라인(interrupt request line)**이라고 불리는 선을 갖는다.
    1. CPU는 매 명령어를 끝내고 다음 명령어를 수행하기 전에 늘 이 선을 검사한다.
2. 입출력 하드웨어 컨트롤러가 이 요청 라인에 신호를 보내면
3. CPU가 알아채고 각종 레지스터 값과 상태 정보를 저장한 다음,
4. 메모리 상의 **인터럽트 핸들러 루틴**으로 이동(jump)한다.
5. 인터럽트 핸들러는 인터럽트의 발생 원인을 조사하고 필요한 작업을 수행한 후 CPU를 인터럽트 전의 실행 상태로 되돌리기 위해 복귀 명령(return from interrupt)을 실행한다.
6. 장치 컨트롤러는 인터럽트 요청 라인에 신호를 보냄으로써 인터럽트를 *야기하고*
7. CPU는 인터럽트 상황을 *알아차리고* 인터럽트 핸들러를 *수행한다.*
8. 핸들러는 입출력 장치를 서비스함으로써 인터럽트를 처리한다.

⇒ 이러한 기법은 CPU가 입출력 종료와 같은 비동기 이벤트에 응답할 수 있도록 한다.

> *하지만 현대 운영체제는 더욱 세분화된 인터럽트 핸들링 방법이 필요하다.*
> 
- 현대 컴퓨터는 CPU와 **인터럽트 컨트롤러 하드웨어**를 제공하고 있다.
- 대부분의 CPU는 두 종류의 인터럽트 요청 라인을 가진다.
    - 회복 불가능한 메모리 오류와 같은 이벤트를 위해 사용되는 **마스크 불가 인터럽트**
    - 필요하면 인터럽트 기능을 잠시 중단 시켜놓을 수 있는 **마스크 가능 인터럽트**

- 인터럽트 기법은 보통 **주소**라고 하는 하나의 작은 정수를 받아들인다.
    - 이 정수는 특정 인터럽트 핸들링 루틴을 선택하기 위해 사용된다.
    - 대부분의 아키텍처에서 이 주소는 **인터럽트 벡터**라고 불리는 테이블의 오프셋으로 사용된다.
    - 이 벡터는 인터럽트 핸들러들의 메모리 주소들을 가지고 있다.

- 서비스할 인터럽트를 결정하기 위해서는 모든 가능한 인터럽트의 진원지를 찾아야 할 필요를 줄여야 하지만,
    - 컴퓨터는 인터럽트 벡터 내에 있는 주소들보다 더 많은 수의 장치가 있다.
    - 이러한 문제를 해결하기 위해 **인터럽트 사슬화(chaining)** 기술을 사용한다.

- 인터럽트 사슬화에서는 인터럽트 벡터의 각 원소가 여러 인터럽트 핸들러들로 이루어진 리스트의 헤더를 가리키고 있다.
    - 인터럽트가 일어났을 대 해당 핸들러가 찾아질 때까지 리스트 상의 핸들러들을 하나씩 검사하게 된다.

![image](https://user-images.githubusercontent.com/87460638/236810377-262501cc-24ad-4d49-a51a-21f352c0d63c.png)

인텔 펜티엄 프로세서의 인터럽트 벡터 테이블

> 인터럽트 기법은 **인터럽트 우선순위 수준(interrupt priority levels)**의 구현을 가능하게 한다.
> 
- 이러한 수준들은 CPU가 모든 낮은 우선순위 인터럽트를 일일이 마스크 오프(mask-off)시키지 않더라도 자동으로 높은 우선순위 인터럽트가 낮은 우선순위 인터럽트의 실행을 선점할 수 있게 한다.

> 운영체제는 인터럽트를 사용하여 여러가지 **예외(exception)**를 처리한다.
> 
- 인터럽트를 야기한 이벤트들은 운영체제가 가능한 최단 시간 내에 독자적인 루틴을 수행해 줄 것을 필요로 한다.

- 대부분의 경우 인터럽트 처리는 시간과 자원이 제한되어 구현하기가 복잡하기 때문에 시스템은 인터럽트 관리를 **1차 인터럽트 처리기(FLIH)**와 **2차 인터럽트 처리기(SLIH)**로 나뉜다.
    - **FLIH**는 문맥교환, 상태 저장 및 처리 작업을 큐에 삽입하는 작업을 수행한다.
    - 별도로 스케줄 된 **SLIH**는 요청된 작업 처리를 수행한다.

> 운영체제는 또한 가상 메모리 페이징을 위해 인터럽트 기법을 사용하기도 한다.
> 

> 또 다른 사용으로는 시스템 콜의 수행이 있다.
> 
- 일반적으로 응용 프로그램은 시스템 콜을 수행하기 위해 라이브러리 루틴을 호출한다.
- 그 라이브러리 루틴은 호출 인자를 점검하고,
- 커널로 인자를 넘겨주기 위한 자료구조를 구성하고,
- **소프트웨어 인터럽트** 또는 **트랩(trap)**이라고 하는 특수한 명령어를 수행한다.

> 인터럽트는 또한 커널 안에서 제어의 흐름을 관리하는 데 사용될 수 있다.
> 

<aside>
💡  정리하면, ***인터럽트는 모든 현대 시스템에서 비동기적으로 일어나는 이벤트를 처리하고, 커널 내의 수퍼바이저 루틴으로 달려가기 위한 방도로 사용***된다. 또한 이러한 일 중에서도 가장 급한 일부터 차례로 수행하기 위해 ***우선순위를 부여한다.*** 
 ***인터럽트 구동 I/O는 폴링보다 훨신 일반적이며 폴링은 높은 처리량을 보이는 I/O에 사용된다.*** 일부 장치 드라이버는 I/O 발생률이 낮으면 폴링이 더 빠르고 효율적인 수준까지 발생률이 증가하면 폴링으로 전환된다.

</aside>

### 12.2.4 직접 메모리 접근

- CPU가 상태 비트를 반복적으로 검사하면서 1바이트씩 옮기는 입출력 방식을 **PIO(Programmed I/O)**라고 부른다.
    - 컴퓨터 CPU의 PIO 작업 중 일부를 **DMA 컨트롤러**라는 특수 프로세서에 위임함으로써
    - → CPU의 일을 줄여준다.

- **분산-수집(scatter-gather) 방법**을 사용하면 하나의 DMA 명령을 통해 여러 개의 전송을 실행할 수 있다.
    - CPU는 이 DMA 명령을 통해 여러 개의 전송을 실행할 수 있다.
    - 그리고 CPU는 이 DMA 명령 블록의 주소를 DMA에게 알려주고 자신은 다른 일을 한다.
    - 그러면 DMA는 CPU 도움 없이도 자신이 직접 버스를 통해 DMA 명령 블록을 액세스하여 입출력을 수행하게 된다.

- 대상 주소가
    - 사용자 공간에 있는 경우, 사용자는 전송 중에 해당 공간의 내용을 수정할 수 있기 때문에 일부 데이터를 잃을 수 있다.
    - 또한 DMA로 전송된 데이터를 스레드가 액세스할 수 있게 하려면 커널 메모리에서 사용자 메모리로 두 번째 복사 작업이 필요하다.
    - ⇒ 이 **이중 버퍼링**은 비효율적이다.

- DMA 컨트롤러와 장치 컨트롤러 간의 핸드셰이킹은 **DMA-request와 DMA-acknowledge**라는 두 개의 선을 통해 수행된다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810418-63be2192-4589-4ff5-b0af-e21d7259a773.png)
    
    - 장치 컨트롤러가 전송할 자료가 생기면 DMA-request 선에 신호를 보낸다.
    - 이 신호를 받으면 DMA 컨트롤러가 메모리 버스를 얻어 거기에 원하는 주소를 올려놓고 DMA-acknowledge 선에 신호를 보낸다.
    - 장치 컨트롤러가 DMA-acknowledge 신호를 받으면,
    - 컨트롤러는 한 워드를 메모리로 전송하고 DMA-request 신호를 제거한다.
    - 전송이 완전히 끝나면 DMA 컨트롤러는 CPU에 인터럽트를 건다.

- **사이클 스틸링(cycle stealing)**은 CPU의 속도를 저하하지만 입출력 작업을 DMA로 넘기는 것은 전체적으로 시스템 성능을 향상한다.
    - 어떠한 컴퓨터들은 DMA를 할 때 물리 주소를 사용하지만,
    - 다른 컴퓨터들은 **직접 가상 주소 접근(DVMA)**을 사용하기도 한다.
    - DVMA를 사용하면 CPU나 메모리의 개입 없이도 가상 주소로 두 개의 메모리 맵 장치 간에 데이터를 전송할 수 있게 된다.

- 보호 모드에서 돌아가는 커널을 가진 운영체제는 일반 프로세스들이 입출력 명령을 직접 내리는 것을 금지한다.
    - 이것은 접근 제어 뿐만 아니라 시스템 고장을 일으킬 수 있는 사고로부터 시스템을 보호하기 위해서이다.
    - 대신 운영체제는 충분한 특권을 가진 프로세스가 하드웨어에 대해 낮은 수준의 연산을 수행하게 할 수 있는 함수들을 제공한다.

### 12.2.5 입출력 하드웨어 요약

- 입출력 하드웨어를 전자회로 층까지 내려가서 보면 복잡하지만 운영체제의 입출력 측변을 이해하는 데는 앞에서 설명한 개념으로 충분하다.
    - 버스
    - 컨트롤러
    - 입출력 포트와 레지스터
    - 호스트와 장치 컨트롤러 간의 핸드셰이킹 관계
    - 폴링이나 인터럽트를 통한 핸드셰이킹의 수행
    - 큰 데이터 전송을 DMA 컨트롤러로 넘김

> *다양한 장치를 추가할 때 운영체제를 고치지 않고 수용하면서, 응용 프로그램에 편리하고 일관된 입출력 인터페이스를 제공해 줄 수 있을까 ?*
> 

⇒ 이러한 문제는 다음 절에서 확인해 보자.

## 12.3 응용 입출력 인터페이스

- 각 대표적인 종류의 장치들은 **인터페이스**라고 불리는 표준 함수들의 집합을 통하여 접근된다.
    - 장치 드라이버라고 부르는 커널 내의 모듈들은 각 입출력 장치를 위한 구체적인 코딩을 제공하여 인터페이스의 표준 함수들을 내부적으로 수행한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810459-06326552-06b6-4bec-9a42-f097dd532776.png)
    

- 장치 드라이버 층의 목적은 여러 입출력 하드웨어 간의 차이를 숨기고,
    - 이들은 간단한 표준 인터페이스들로 보이도록 포장해서,
    - 이것을 상위의 커널 입출력 서브시스템에 제공하는 것이다.

⇒ 이렇게 되면 입출력 서브 시스템은 하드웨어와 독립적으로 되어서 운영체제 개발자의 작업을 간단하게 해준다.

- 한 가지 문제는 운영체제마다 **장치 드라이버 인터페이스에 대한 규격이 다르다**는 것이다.
    - 따라서 새로운 장치는 Windows, Linux, AIX 및 macOS에 대한 드라이버와 같은 여러 개의 장치 드라이버와 함께 제공되어야 할 것이다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810498-6d051fe7-b838-48c3-bcd2-90b57333787f.png)
    

- **문자 스트림과 블록**
    - 문자 스트림 장치는 바이트를 하나씩 전송하지만 블록 장치는 블록 단위로 전송한다.
- **순차 접근과 임의 접근**
    - 순차 장치는 순차적 순서로만 자료를 전송한다.
    - 임의 접근 장치는 임의의 위치에 있는 자료도 입출력할 수있다.
- **동기식과 비동기식**
    - 동기식 장치는 시스템의 다른 측면과 조율하여 일정한 응답 시간을 보인다.
    - 비동기식 장치는 다른 이벤트와 조율 없이 불규칙한 혹은 예측 불가능한 응답 시간을 보인다.
- **공유와 전용**
    - 공유 가능한 장치는 몇 개의 프로세스나 스레드에 의해 동시에 사용될 수 있다.
    - 전용 가능한 장치는 혼자만 사용해야 한다.
- **동작 속도**
    - 장치 속도는 초당 몇 바이트에서 초당 수 기가바이트까지 다양하다.
- **읽기/쓰기, 읽기 전용, 한번만 쓰기**
    - 읽기와 쓰기를 모두 수행하지만 어떤 장치들은 하나만 지원하기도 한다.

> *응용의 접근 목적에 따라 운영체제는 장치들의 차이를 숨겨주고 장치들을 몇 개의 범주로 분류한다.*
> 
- 범주들이란 블록 입출력, 문자 스트림 입출력 등을 말한다.
- 운영체제는 현재 시각을 알리는 클록이나 타이머와 같은 몇몇 장치들을 접근하기 위한 특별 시스템 콜을 제공한다.
- 어떤 운영체제는 그래픽 기반 디스플레이, 비디오와 오디오 장치를 위한 시스템 콜 집합도 제공한다.

- 또한 대부분의 운영체제는 응용 프로그램이 입출력 장치로 임의의 명령을 전달하도록 하는 **escape**(또는 **back-door**) 시스템 콜을 갖고 있다.
    - Unix에서는 `ioctl()`이 이 시스템 콜에 해당한다.
    - 이 시스템 콜은 새로운 시스템 콜을 만들 필요 없이 응용 프로그램이 임의의 장치 드아리버가 제공하는 임의의 기능을 사용할 수 있게 한다.

### 12.3.1 블록 장치와 문자 장치

- **블록 장치 인터페이스**는 디스크나 이와 유사한 블록 지향 장치를 사용하기 위해 필요한 모든 요소를 제공하고 있다.
    - 일반적으로 읽기(read)와 쓰기(write), 다음 전송할 위치를 지정하는 탐색(seek)명령을 제공한다.
    - 응용 프로그램은 보통 파일 시스템 인터페이스를 통해 이 블록 인터페이스에 접근한다.

- 운영체제나 데이터베이스는 블록 장치를 마치 선형 배열이라고 이해하고 사용하기를 원할 것이다.
    - 이러한 접근 모드를 **비가공 입출력(raw I/O)**라고 불린다.
    
- 응용이 자체 버퍼링을 수행한다면 
→ 파일 시스템은 불필요하고 중복된 버퍼링을 하게 될 것이다.
- 응용이 파일의 블록이나 일부에 대한 자체 잠금(locking) 기능을 제공한다면
    
    → 운영체제의 잠금 기능은 최소한 중복된 기능이고, 최악의 경우에는 모순이 발생한다.
    

⇒ 이를 해결하기 위한 절충안으로 *운영체제가 버퍼링과 잠금을 하지 않는 모드로 파일에 대한 입출력 작업을 하는 것이다 !* 

… UNIX 시스템에서는 이러한 방식을 **직접 입출력**이라고 한다.

- 메모리 맵드(memory mapped) 파일 접근은 블록 장치 위의 층으로 구현할 수 있다.
    - 실제로 디바이스를 읽거나 쓰는 명령을 사용하는 대신 메모리의 특정 번지를 읽거나 쓰는 명령으로 파일 입출력을 대신하는 방식이다.
    - 메모리 맵 방식을 써서 실제 참조되어야만 실제로 메모리에 올라오는 방법은 파일 입출력 인터페이스를 쓰지 않고 요구 페이징을 사용하므로 더 효율적이다.
    
- 키보드는 **문자 스트림 인터페이스**를 통해 접근되는 장치이다.
    - 이러한 인터페이스의 시스템 콜은 응용 프로그램에 한 글자씩을 보내거나(put) 받아 오는(get) 명령을 제공한다.
    - 이 인터페이스 위에 라이브러리를 만들면 응용 프로그램이 한 줄(line)씩 읽게도 해주고, 버퍼링과 편집 기능을 제공해 줄수도 있다.

### 12.3.2 네트워크 장치

- UNIX나 Windows NT를 포함한 많은 운영체제에 사용하는 인터페이스는 네트워크 **소켓(socket)** 인터페이스이다.

- 소켓 인터페이스에서 시스템 콜은 응용 프로그램이 소켓을 생성하고
    - 로컬 소켓을 원격지 주소와 연결해 주고
    - 원격지의 응용 프로그램이 이 소켓으로 접속을 완료하였는지 알아보고
    - 연결되었으면 패킷(packet)을 주고받도록 한다.

- 또한 네트워크 서버의 구현을 지원하기 위해 소켓 인터페이스는 일련의 소켓들을 관리하는 select() 함수를 제공한다.
    - select()를 사용하면 응용 프로그램이 네트워크 상태를 폴링할 필요가 없어지고 네트워크 입출력을 위해 바쁜 대기를 할 필요도 없어진다.

⇒ 이러한 기능(function)들은 네트워크의 자세한 내부 사항들을 캡슐화함으로써 분산 응용을 개발할 때 임의의 네트워크 하드웨어나 프로토콜 스택을 사용할 수 있게 한다.

### 12.3.3 클록과 타이머

- 대부분의 컴퓨터는 하드웨어 클록과 타이머를 가지고 3가지 기본적인 기능들을 제공한다.
    - 현재 시각을 제공
    - 지난 시간을 제공
    - T 시각이 되면 X 오퍼레이션을 실행

⇒ 이 기능은 운영체제 뿐만 아니라 시간에 의존하는 응용들도 많이 사용하지만, 표준화되어 있지 않다 !

- 지나간 시간을 재고 특정 오퍼레이션을 실행시키는 하드웨어를 **프로그램 가능 인터벌 타이머(programmable interval timer)** 라고 한다.
    - 이것은 어느 시간만큼 지나면 인터럽트를 발생시키도록 설정할 수 있고,
    - 이 과정을 한번 또는 주기적으로 인터럽트를 발생하도록 반복해서 설정할 수 있다.

- 프로그램 가능 인터벌 타이머가 사용되는 경우
    - 스케줄러가 타임 슬라이스가 종료되면 현재 진행 중인 프로세스로부터 CPU를 빼앗기 위해 사용된다.
    - 디스크 입출력 서브시스템이 변경된 캐시 버퍼를 주기적으로 디스크에 쏟아내는 데 사용된다.
    - 네트워크 서브시스템이 네트워크 혼잡이나 오류로 인해 어떤 작업을 취소하는 데에 사용된다.

- 컴퓨터에는 다양한 용도로 사용되는 클록 하드웨어가 있다.
    - 최신 PC에는 **고성능 이벤트 타이머(HPET)**가 포함되어 있다.

- 저장하고 있는 값이 HPET의 값과 일치할 때 한번 또는 반복적으로 트리거 하도록 설정할 수 있는 여러 비교기가 있다.
    - 트리거는 인터럽트를 발생시키고 운영체제의 클록 관리 루틴은 타이머의 목적과 수행할 조치를 결정한다.
    - 타이거 발생 간격은 덜 정밀한 타이머 하드웨어 자체에도 이유가 있지만, 보다 정밀해지려면 가상 클록을 유지하는 데 드는 오버헤드가 매우 크다는 데에 기인한다.
    - 또한 타이머 틱이 시스템의 time-of-day 클록에 사용되면, 시스템 클록이 부정확해질 수 있다.
    
- 부정확성은 NTP와 같은 네트워크 시간 프로토콜을 사용하여 보정될 수 있다.
    - NTP는 정교한 지연 시간 계산을 사용하여 컴퓨터 시계를 거의 원자수준으로 정확하게 유지한다.
    - 따라서 대부분 컴퓨터에서는 하드웨어 클록을 카운터로 구현한다.
    - 카운터는 정밀도가 높은 시계 역할을 하게 된다.

⇒ 그러나 이러한 높은 정밀도를 가지는 클록은 통상 인터럽트를 생성하는 데 사용되지 못하며 단지 시간 간격의 정확한 측정을 제공한다.

### 12.3.4 봉쇄형과 비봉쇄형 입출력

- 응용 프로그램이 **봉쇄형(blocking)** 시스템 콜을 하면, 호출 스레드는 봉쇄 상태로 들어가게 된다.
    - 즉, 운영체제가 이 스레드를 실행 큐로부터 대키 큐로 옮긴다.
    - 입출력이 끝나면 이 스레드는 다시 실행 큐로 되돌아간다.
    - 그 후에는 다시 실행을 재시작할 수 있다.

- 입출력 장치에 의해 수행되는 작업은 일반적으로 비동기식으로 수행 시간이 다양하며 예측하기 어렵다.
    - 그럼에도 불구하고 운영체제는 응용 인터페이스에 대해 봉쇄형 시스템 콜을 제공한다.
    → ***봉쇄형 코드가 비봉쇄형 코드보다 작성하기 쉽기 때문이다.***

- 어떤 사용자 프로그램들은 **비봉쇄형(nonblocking)** 입출력이 필요하다.
    - ex) 스크린에 다른 자료를 표시하거나, 연산을 하는 중에 키보드와 마우스 입력을 받아들이는 경우 등

- 응용 프로그램 작성자는 연산과 입출력 간의 중첩을 최대한 도모하기 위해 **다중 스레드 방식**으로 프로그램을 작성할 수 있다.
    - 일부의 스레드는 봉쇄되는 시스템 콜을 하여 봉쇄되고,
    - 나머지 스레드들은 연산을 계속할 수 있다.

- 비봉쇄형 시스템 콜의 대안으로 **비동기식 시스템 콜**이 있다.
    - 이 호출에서 되돌아온 스레드는 자신의 코드를 계속 수행한다.
    - 입출력이 완료되면,
        - 그때 가서 운영체제가 입출력 완료됐다는 사실을 스레드의 변수를 세팅해 주거나
        - 시그널을 보내거나
        - 소프트웨어 인터럽트를 걸거나
        - 스레드와는 별도로 수행되는 콜백 루틴을 수행함
    - 으로써 알려준다.

- 비봉쇄형 시스템 콜과 비동기식 시스템 콜의 차이점
    - 비봉쇄형 읽기의 경우 → 그 시점에서 가지고 올 수 있는 데이터를 가지고 즉각 복귀한다.
    - 비동기식 시스템 콜 읽기의 경우 → 입력이 완전히 끝난 후 완전한 데이터를 전송해 달라고 요청한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810578-5c0a040e-a0ae-4009-81f6-9f71cded6ae8.png)
    
- 비동기적 호출의 좋은 예는 *‘보조저장장치와 네트워크 입출력’* 이다.
    - 응용이 네트워크 송신 요청 또는 보조저장장치 쓰기 요청을 한 경우, 운영체제는 요청을 주목하면 입출력 요청을 버퍼에 넣고 응용으로 되돌아간다.

- 비봉쇄형의 좋은 예는 *‘네트워크 소켓의 select() 시스템 콜’* 이다.
    - 인자를 0으로 지정하게 되면, 스레드는 봉쇄 없이 네트워크에 대한 폴링만 수행한다.
    - 그러나 select()를 사용하는 것은 추가적인 오버헤드가 될 수 있기 때문에, 실제 데이터를 전송하려면 read(), write() 명령어가 뒤따라야 한다.
- 이와는 다른 접근 방식으로 Mach는 *‘봉쇄형 다중 읽기 호출’*을 제공한다.
    - 이 시스템 콜은 한 번 부르면 몇 개의 입출력 장치를 검사하고 그 중 어느 하나라도 데이터를 가지고 있으면 데이터와 함께 즉시 복귀한다.

### 12.3.5 벡터형 입출력

- **벡터형 입출력**은 하나의 시스템 콜을 호출하여 복수의 위치에 여러 입출력 연산을 수행할 수 있게 한다.
    - 동일한 전송은 시스템 콜을 여러번 호출하여 수행할 수 있지만 이러한 경우에는 **분산-수집 방식**이 유용하다.
    - 여러 개의 개별 버퍼의 내용을 한 개의 시스템 콜을 통하여 입출력할 수 있어 *문맥 교환과 시스템 콜 오버헤드를 줄일 수 있다.*

## 12.4 벡터형 입출력

- 커널은 입출력과 관련된 많은 서비스를 제공한다.
    - 입출력 스케줄링, 버퍼링, 캐싱, 스풀링 등 여러 서비스를 제공한다.
    - 이들은 하드웨어와 장치 드라이버 구조를 바탕으로 한다.

### 12.4.1 I/O 스케줄링

- 입출력 요구를 스케줄 한다 = 그 요구를 실행할 순서를 결정한다
    - 응용 프로그램이 입출력을 요청하는 순서대로 스케줄링할 경우 최상의 성능을 내기 어렵다.
    - 스케줄링은 전반적인 시스템 성능을 향상하고 프로세스들 사이에 공평을 기해줄 수 있다.
    - 또한 입출력 완료까지의 평균대기 시간을 줄일 수 있다.

- 응용 프로그램이 봉쇄형 입출력 시스템 콜을 하면
    - 그 입출력 요청은 해당 장치의 큐에 넣어진다.
    - 입출력 스케줄러는 시스템의 성능과 각 응용에 대한 평균 응답 시간을 향상하기 위해 큐 안의 순서를 재배치한다.
    - 운영체제는 때때로 시간이 급한 작업 즉, 우선수위가 높은 작업은 빨리 처리해 주기도 한다.

- 커널이 비동기적 입출력을 제공한다면
    - 커널은 동시에 많은 입출력 요청을 추적해야 한다.
    - 이를 위해 운영체제는 각 **장치 상태 테이블(device-status table)**에 대기큐를 연동한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810610-67575ed8-d500-4eed-ba94-c79ba02ed34c.png)

    - 테이블 항목은 장치의 종류, 주소, 상태 등을 가리킨다.
    - 만약 장치가 요청을 처리하는 중(= 동작 중) 이면 
    → 같은 장치에 대한 요청은 그 장치에 해당하는 테이블 항목에 저장될 것이다.

- 입출력 작업 스케줄링은 입출력 서브 시스템이 컴퓨터의 효율성을 향상하는 방법 중 하나이다.

### 12.4.2 버퍼링

- **버퍼**는 두 장치 사이 또는 장치와 응용 프로그램 사이에 데이터가 전송되는 동안 전송 데이터를 임시로 저장하는 메모리 영역을 말한다.

> 버퍼링은 다음과 같이 3가지 이유 때문에 필요하다.
> 
1. 데이터의 생산자와 소비자 사이에 속도가 다른 것에 대처하기 위함이다.
    1. **이중 버퍼링**은 데이터 소비자로부터 생산자와의 밀접성을 해제하여 데이터 간의 타이밍 요구 사항을 완화한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810706-578ac3d0-0096-4239-b68a-e79d144d78be.png)
    
2. 데이터 전송 크기가 다른 장치들 사이의 완충을 제공할 때이다.
    1. 송신 측의 큰 메세지는 보다 작은 작은 네트워크 패킷으로 나누어진다.
    2. 패킷은 망을 통해 전송되고 수신 측에서 원래의 자료를 복원하기 위해 그 패킷들을 버퍼에서 결합한다.
3. 응용 프로그램의 입출력 **복제 시맨틱(copy semantics)**을 지원하기 위함이다.
    1. 복제 시멘틱에 의하면 *“디스크에 기록되어야 하는 버전은 응용 프로그램이 시스템 콜 이후 변경한 버전과는 무관하고, 단지 응용 프로그램이 시스템 콜을 하는 시점의 버퍼 버전만이 디스크에 써지는 것을 보장”*하는 것이다.
    2. 운영체제가 복제 시멘틱을 보장할 수 있는 가장 간단한 방법은 응용 프로그램으로 복귀하기 전에 write() 시스템 콜이 **응용 프로그램의 버퍼에 있는 자료를 커널 버퍼로 복사**하는 것이다.
    3. 이러한 방식은 응용 프로그램 버퍼와 커널 버퍼 간에 데이터를 복사해야 한다는 부담에도 불구하고 복제 시맨틱이 간단한는 이유로 대부분의 운영체제에서 채택되고 있는 방법이다.
    4. 이와 비슷한 효과를 가상 메모리 매핑과 copy-on-write 페이지 보호를 교모하게 사용함으로써 더욱 효율적으로 얻을 수 있다.

### 12.4.3 캐싱

- **캐시**는 자주 사용될 자료의 복사본을 저장하는 빠른 메모리 영역이다.
    - 캐시된 복사본을 사용하면 원래 자료를 사용하는 것보다 더 효율적이다.

- 버퍼와 캐시의 차이점
    - 버퍼는 데이터를 가지고 있는 **유일한** 장소이지만,
    - 캐시는 다른 곳에 이미 저장된 데이터의 **복사본을 빠른 저장 장소에 추가로 저장**하고 있다.

- 캐싱과 버퍼링은 서로 다른 기능이지만 때로는 메모리 영역을 두가지 용도로 모두 사용할 수 있다.

### 12.4.4 스풀링 및 장치 예약

- **스풀(spool)**은 인터리브(interleave)하게 동작할 수 없는 프린터 같은 장치를 위해 출력 데이터를 보관하는 버퍼이다.
    - 즉, 프린터는 한번에는 하나의 작업만을 그것이 다 끝나기까지 처리하여야 하고, 여러 응용 프로그램의 출력을 섞어 번갈아 가며 출력시킬 수는 없다.
    

> 많은 응용 프로그램은 동시에 출력 데이터를 만들어 내는 동시에, 각 출력이 다른 프로그램의 출력과 섞이지 않게 프린터로 출력되기를 원한다.
> 
1. 운영체제는 응용 프로그램으로부터 프린터로 가는 출력의 문제를 해결한다.
    - 각 응용 프로그램의 출력은 각각 대응되는 보조저장장치 파일에 저장(스풀)된다.
    - 응용 프로그램이 출력 데이터 만들어내기를 끝내게 되면, 스풀링 시스템은 그때까지 모아 놓은 출력 데이터를 프린터 출력용 대기 열에 삽입한다.
    - 스풀링 시스템은 큐에서 대기 중인 스풀 파일을 한번에 하나씩 프린터에 내보낸다.

1. VMS 처럼 한 프로그램만이 장치를 독점적으로 사용하도록 허용되는 방법이다.
    - 프로세스가 명시적으로 쉬고 있는 장치를 할당할 수 있고, 사용이 다 끝나면 명시적으로 그 장치를 반납할 수 있다.
    - 어떤 운영체제들은 그런 장치에 대한 open 할 수 있는 파일 핸들러 개수를 한 개로 제한한다.
    - 많은 운영체제는 프로세스들이 원하면 자기들기리 배타적 접근을 협조적으로 해결해 갈 수 있는 방편을 제공해 준다.
    

### 12.4.5 오류 처리

- 보호되는 메모리를 사용하는 운영체제는 많은 종류의 하드웨어 및 응용 프로그램 오류에 대처할 수 있으며 그러한 오류가 일어나도 시스템 전체의 마비로까지 확대되지 않는다.

- 입출력 장치나 네트워크 전송은 일시적인 원인 때문에 또는 영구적인 원인 때문에 실패할 수 있다.

⇒ 운영체제는 일시적인 고장으로부터 효과적으로 극복할 수 있다.

- 입출력 시스템 콜은 성공/실패를 나타내는 한 비트 정보를 반환함으로써 오류를 처리할 수 있다.
    - UNIX 운영체제는 반환 값 외에도 errno라 부르는 변수를 사용한다.
        - 이 변수는 100가지 종류의 오류를 구분하여 준다.

⇒ 하지만 오류에 대한 자세한 정보는 운영체제에 의해 응용 프로그램까지 전달되지 않는다.

- 예를 들어, SCSI 장치에 문제가 생기면 SCSI 프로토콜에 의해 **sense key** 형태로 보고되고,
    - 이 키 값은 하드웨어 고장이나 불법적인 요청 등과 같은 문제 유형을 알려준다.
    - 이외에도 **추가적인 sense code** 가 있어 명령어 인자의 문제점과 같은 자세한 정보를 알려준다.
    - **추가로 sense-code qualifier**가 더욱 자세한 정보를 제공해 준다.
    - 더 나아가 SCSI는 본체가 요청할 경우 알려주기 위하여 error-log 기록도 보관하고 있지만 이를 요청하는 경우는 흔치 않다.

### 12.4.6 입출력 보호

- 오류는 보호 문제와 밀접한 관계가 있다.

- 사용자가 불법적인 입출력을 못 하게 하기 위해, 모든 입출력 명령은 특권 명령으로 정의한다.
    - 따라서 사용자는 입출력 명령을 직접 수행할 수 없다.
    - 대신, 운영체제가 입출력을 수행하도록 시스템 콜을 수행한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236810772-f9724d51-79d4-44ac-b4bc-ced0ade0fa5d.png)
    

- 메모리 맵드 메모리 또는 입출력 포트 메모리의 위치는 메모리 보호 시스템에 의해 사용자로부터 보호되어야 한다.
    - 하지만 커널이 무조건 모든 사용자의 접근을 거부해서는 안된다.
    - 이러한 경우에는, 커널은 그래픽 메모리의 한 부분이 한번에 한 프로세스에 할당되도록 **잠금 기법**을 제공해야 한다.

### 12.4.7 커널 자료구조

- 커널은 입출력 구성요소에 대한 상태 정보를 유지해야 한다.
    - 커널은 네트워크 연결, 문자 장치 통신, 다른 입출력 활동을 관리하기 위해 여러 비슷한 구조를 사용한다.

> UNIX
> 
- 파일 시스템 인터페이스를 사용하여 다양한 것에 access를 할 수 있게 해준다.
    - 예를 들면, 사용자 파일, raw 장치와 같은 다양한 개체들을 파일 시스템처럼 액세스할 수 있게 해준다.

- 모든 객체가 read()를 지원해도 객체마다 행해지는 의미는 다르다.
    - 예를 들어,
    - 사용자 파일을 읽는 일이다 → 커널은 디스크 입출력을 수행하기 전에 버퍼 캐시를 먼저 조사한다.
    - raw 장치를 읽는 일이다 → 커널은 요청한 크기가 디스크 섹터 크기의 정수배인지 등을 조사한다.

⇒ 이러한 다양성을 객체지향 기법을 이용하여 하나의 구조로 묶는다.

![image](https://user-images.githubusercontent.com/87460638/236810816-4fb72eba-488e-41f0-81a2-861a9f45465b.png)

> Windows NT
> 
- 입출력 서비스를 커널이 직접 해주지 않고 커널 밖의 입출력 manager라는 프로그램에 넘겨준다.
    - 커널에 입출력 요청이 오면 → 그것을 메시지로 바뀌어 커널을 통해 전달되고 → 다시 장치 드라이버에게 전달된다.
    - 출력의 경우 → 메시지는 출력시킬 데이터를 담고 있을 것이고,
    - 입력의 경우 → 메시지는 입력 버퍼를 가리킬 것이다.
    

⇒ 입출력 서비스를 커널에서 해주지 않고 커널 밖의 독립 프로그램에서 해주게 되면 

- overhead가 더 많이 드는 단점이 있지만,
- 대신 입출력 시스템의 구조와 설계가 간단해지고 **운영체제 커널의 크기가 작아지고, 융통성이 커진다**는 장점이 있다.

### 12.4.8 전원 관리

- 운영체제는 전력 사용에서 중요한 역할을 한다.
    - 운영체제는 부하를 분석하여 부하가 충분히 낮고, 하드웨어 기능이 활성화되어 있으면
    - → CPU나 외부 입출력 장치와 같은 구성요소의 전원을 끊을 수 있다.

- 시스템 부하가 필요하지 않는 경우
    - → CPU 코어는 일시 중단될 수 있으며,
    - → 부하가 증가하고 스레드 큐를 실행하기 위해 더 많은 코어가 필요할 때 재개될 수 있다.

⇒ 이 기능은 서버로 가득 찬 데이터 센터는 대량의 전기를 사용하고 불필요한 코어를 비활성화하면 전기의 필요를 줄일 수 있기 때문에 서버에 필요하다.

- 모바일 컴퓨팅에서 전력 관리는 운영체제의 우선순위가 높은 측면이다.
    - 전력 사용을 최소화하고 배터리 수명을 최대화하면
    - → 장치의 사용성이 향상되고, 다른 장치와 경쟁할 수 있다.

- 일반적으로 전원 관리는 장치 관리에 기반을 두기 때문에 복잡하다.
    - 최신 범용 컴퓨터는 다른 하드웨어 코드 집합인 **고급 구성 및 전원 인터페이스(ACPI, addvanced configuration and power interface)**를 사용하여 하드웨어 측면을 관리한다.
    - ACPI는 장치 상태 검색 및 관리, 장치 오류 관리 등을 위해 커널에서 호출할 수 있는 루틴 형태로 코드를 제공한다.

### 12.4.9 커널 입출력 서브시스템 요약

<aside>
💡 요약하면, 입출력 서브시스템은 광범위한 서비스를 조정하며, 이 서비스는 응용 프로그램과 커널의 다른 부분이 사용할 수 있는 것이다.

</aside>

- 입출력 서브시스템은 다음 사항들을 관리한다.
    - 파일 및 장치의 이름 관리
    - 파일 및 장치에 대한 접근 제어
    - 작업 제어
    - 파일 시스템을 위한 공간 할당
    - 장치 할당
    - 버퍼링, 캐싱 및 스풀링
    - 입출력 스케줄링
    - 장치 상태 모니터링, 오류 처리 및 고장 복구
    - 장치 드라이버 구성 및 초기화
    - 입출력 장치의 전원 관리

- 입출력 서브시스템의 상위 계층은 장치 드라이버가 제공하는 공통적인 인터페이스를 통해 장치에 접근한다.

## 12.5 입출력 요구를 하드웨어 연산으로 변환

- UNIX는 장치 이름을 보통의 파일 이름처럼 표시한다.
    - 경로 이름을 해결하기 위해 **마운트 테이블**의 이름 중 접두어 부분이 경로명과 일치하는 가장 긴 이름을 찾는다.
        - 마운트 테이블의 그 항목이 장치 이름을 제공한다.
    - 파일 시스템 디렉터리 구조에서 이름을 찾으면 incode 번호 대신 **<major, minor> 장치 번호를 얻게 된다.**
        - 주(major) 장치 번호는 이 장치에 대한 입출력을 처리하기 위해 호출할 장치 드라이버를 나타낸다.
        - 부(minor) 장치 번호는 장치 드라이버에게 전달되고 통상 장치 테이블에 대한 인덱스로 사용된다.

- 최신 운영체제들은 입출력 요청과 장치 컨트롤러 사이에 여러 단계의 lookup table을 두어 상당한 유연성을 제공해 준다.
    - 커널을 다시 컴파일하지 않고도 새로운 장치와 드라이버를 컴퓨터에 추가할 수 있다.
    - 운영체제는 부트할 때에 버스를 보고 어떠한 하드웨어가 있는지를 조사하고 필요한 드라이버를 함께 적재한다.
    - 부팅 후 추가된 장치는 발생하는 오류에 의해 감지될 수 있다.
    - 이 오류가 발생하면 커널은 장치 세부 정보를 검사하고 적절한 장치 드라이버를 동적으로 적해하게 만든다는 메시지를 표시할 수 있다.
    
- 봉쇄형 읽기 요청을 처리하는 전체 윤곽을 그림으로 표시한 것이다.

![image](https://user-images.githubusercontent.com/87460638/236810874-7cf9c9cf-ea06-4ce9-8578-69b4689ee62a.png)

## 12.6 STREAMS

- UNIX 시스템 V는 **STREAMS** 라 불리는 기법을 가지고 있다.
    - 응용 프로그램이 동적으로 드라이버 코드의 파이프라인을 조립할 수 있게 한다.

- STREAMS는 디바이스 드라이버와 사용자 레벨 프로세스 사이의 ***완전 양방향 연결***을 말한다.
    - 사용자 프로세스와 상호 연동하는 **스트림 헤드**와
    - 디바이스를 제어하는 **드라이버 엔드**,
    - 그 둘 사이에 존재하는 0개 이상의 **스트림 모듈**로 구성된다.
    
- STREAMS의 각 구성요소들은 읽기와 쓰기를 위한 한 쌍의 큐를 가지고 있다.
    - 큐 사이에 데이터를 전송하는 데 메시지 전달이 사용된다.

![image](https://user-images.githubusercontent.com/87460638/236810920-892432b7-133a-43c3-bb24-21a37e2cee03.png)

> 모듈은 스트림 처리 기능을 제공하며 ioctl() 시스템 콜을 사용하여 스트림에 푸시된다.
> 
- 인접한 모듈의 큐 사이에서 메시지가 교환되기 때문에 한 모듈의 큐가 인접 큐를 오버플로 시킬 수 있다.

⇒ 이를 방지하기 위해 큐는 **흐름 제어**를 지원할 수 있다.

- 흐름 제어를 지원하는 큐에서는 메시지를 버퍼링하고 충분한 버퍼 용량이 없으면 메시지를 받아들이지 않는다.

⇒ 흐름 제어는 인접 모듈의 큐 사이에 제어 메시지를 교환함으로써 지원된다.

> 사용자 프로세스는 write() 또는 putmsg() 시스템 콜을 사용하여 디바이스에 데이터를 쓸 수 있다.
> 
- write() 시스템 콜은 미가공(raw) 데이터를 스트림에 쓴다.
- putmsg()는 사용자 프로세스가 메시지를 지정하는 것을 허용한다.

- 사용자 프로세스에 의해 사용되는 시스템 콜과는 관계없는 스트림 헤드는 메시지로 데이터를 복하고 다음 모듈의 큐에 전달한다.
    - 이런 메시지 복사는 드라이버 엔드, 즉 디바이스에 메시지가 복사될 때까지 계속된다.

> STREMS 입출력은 사용자 프로세스가 스트림 헤드와 통신할 때를 제외하고 비동기이다.
> 
- 스트림에 쓰기를 할 때 사용자 프로세스는 다음 큐가 흐름 제어를 사용한다고 가정할 경우, 메시지를 복사할 공간이 있을 때까지 봉쇄된다.
    - 마찬가지로 읽기를 할 때 데이터가 이용 가능할 때까지 봉쇄된다.

> STREAMS를 사용함으로써 얻어지는 이득
> 
- 디바이스 드라이버와 네트워크 프로토콜을 작성할 때 모듈식이다.
- 점진적인 접근을 위한 프레임워크를 제공한다.

## 12.7 성능

- 입출력은 시스템 성능에 매우 중요한 요소이다.
    - 입출력은 장치 드라이버 코드를 실행하고, 프로세스가 봉쇄되고 해제됨에 따라 이들을 공평하고 효율적으로 스케줄 하기 위해 CPU에 큰 부담을 준다.
    - 이로 인해 문맥교환에 따른 부담과, 인터럽트 처리 등의 문제를 야기한다.
    
- 일부 시스템들은 터미널 입출력을 전담하는 **프론트 엔드 처리기**를 사용하여 CPU로부터 인터럽트 부담을 덜고 있다.
    - 예를 들어, **터미널 집중기(terminal concentrator)**는 수백 개의 원격 터미널에서 들어오는 정보를 멀티 플렉스할 수 있다.
    - **입출력 채널**은 메인 프레임 CPU로부터 입출력 작업부담을 덜어 준다.

![image](https://user-images.githubusercontent.com/87460638/236810963-b5322462-4bf4-49a1-a1f4-4db31619056a.png)

- 입출력의 효율을 높이기 위해서는 다음과 같은 여러 원칙을 적용할 수 있다.
    - 문맥 교환의 빈도를 줄인다.
    - 메모리에서 장치와 응용 프로그램 사이에 데이터가 복사되는 횟수를 줄인다.
    - 인터럽트 빈도를 줄인다.
    - DMA나 채널 등을 사용하여 CPU의 입출력 부담을 줄이고, 입출력과 주 연산이 될수록 많이 중첩되도록 도모한다.
    - 원시 처리 연산을 하드웨어로 구현하여 장치 컨트롤러 내의 그들의 작업이 CPU와 버스의 작업과 별로 진행되게 한다.
    - CPU, 메모리, 버스, 입출력 등에 대한 부하가 균일하게 되도록 한다.
    
![image](https://user-images.githubusercontent.com/87460638/236811019-81e74cc9-fee5-4bf0-828f-237e73a2d4ac.png)