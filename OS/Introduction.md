## What Operating Systems Do ?

? **Key point !** **OS**

? **운영체제 (Operating System)** = 컴퓨터 HW 관리하는 SW

- 역할
    - 컴퓨터 사용자와 컴퓨터 HW 의 중재자
    - 응용 프로그램 기반을 제공
    

> ?? 컴퓨터 시스템 구성요소 4가지
> 
> - user
> - application programs
>     
>     : 사용자의 계산 문제를 해결하기 위해 자원이 어떻게 사용될지를 정의
>     
>     ex) 컴파일러, 웹브라우저 등
>     
> - OS : 다양한 사용자를 위해 다양한 응용 프로그램 간의 HW 사용을 제어하고 조정
> - HW : CPU, 메모리 I/O 장치로 구성되어, 기본 계산용 자원을 제공한다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dfe11cc0-0b24-4e93-bcc4-64e35b6babae/Untitled.png)

? **무어의 법칙**

*“마이크로칩에 저장할 수 있는 데이터양이 18-24개월마다 2배씩 증가한다.”*

※ 운영체제 요소에 일반적인 정의는 없다. 

? 일반적으로 지지하는 정의 = *“컴퓨터에서 항상 실행되는 프로그램”* ⇒ ***kernel***

- 2가지 프로그램 유형
    - **시스템 프로그램**
        
        = OS와 관련되어 있지만 반드시 kernel의 일부일 필요는 없는 프로그램
        
    - **응용 프로그램**
        
        = 시스템 작동과 관련되지 않은 모든 프로그램을 포함한 프로그램
        

---

## Computer-System Organization

? **Key point !** **Storage Structure**

- 컴퓨터 시스템 구성요소
    - 하나 이상의 CPU
    - 여러 장치 컨트롤러
    
     ⇒  **Bus 형태**로 연결되어 있다.
    

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c28660bc-417e-42d2-b54d-33d3d20c9474/Untitled.png)

> 시스템의 3가지 주요 측면에 따른 시스템 작동방식 알아보기
> 
> - Interrupts
> - Storage Structure
> - I/O Structure

(1) Interrupts

: HW는 언제든 CPU 신호를 보내 인터럽트를 trigger(발생)시킬 수 있다.

⇒ OS와 HW의 상호작용 방식의 핵심 부분으로써, 작업의 상황을 알린다.

┕ 보통 system bus 를 통해 이루어 진다.

![I/O 요청이 들어오고 인터럽트가 보내지면 → CPU는 하던 일을 중단하고 즉시 요청을 실행 → 인터럽트 서비스 루틴 실행 → 인터럽트 서비스 루틴 완료시, CPU는 기존에 실행하던 연산을 재개한다. ](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9d6c2482-a4ff-45f6-a108-e9acf6ad3439/Untitled.png)

I/O 요청이 들어오고 인터럽트가 보내지면 → CPU는 하던 일을 중단하고 즉시 요청을 실행 → 인터럽트 서비스 루틴 실행 → 인터럽트 서비스 루틴 완료시, CPU는 기존에 실행하던 연산을 재개한다. 

(2) Storage Structure

※ CPU는 메모리에서만 적재할 수 있으므로 실행하려면 *프로그램을 먼저 메모리에 적재해야 한다.*

? **RAM** (Random Access Memory) = 메인 메모리, 휘발성 메모리

? **bootstrap program** = 컴퓨터 전원을 켤 때 가장 먼저 실행되는 프로그램

┕ OS를 메모리에 로딩시킨다.

? **폰 노이만 구조 시스템** = 전형적인 명령-실행 사이클

- 명령어 실행 사이클은 크게 아래와 같은 과정으로 나뉜다.
    
    ⑴ 명령어 가져오기
    
    ⑵ 명령어 실행
    
    ⑶ 인터럽트 체크
    
- 이 과정을 세분하면 아래와 같이 5가지로 나뉜다.
    
    ⑴ 명령어 가져오기 (IF, Instruction Fetch)
    
    : 기억장치(메모리)로부터 명령어를 가져온다.
    
    ⑵ 명령어 해석 (ID, Instruction Decode)
    
    : 가져온 명령어가 어떤 명령어인지 해석한다.
    
    ⑶ 피연산자 인출 (OF, Operands Fetch)
    
    : 명령의 실행에 필요한 정보를 기억장치에 접근해 가져온다.
    
    ⑷ 명령어 실행 (EX, Instruction Execution) 
    
    : 가져온 연산자와 데이터를 가지고 연산을 수행 & 저장한다.
    
    ⑸ 인터럽트 체크
    

<aside>
? **프로그램과 데이터가 메인 메모리에 영구히 존재할 수 없는 이유**

1. 메인 메모리는 모든 필요한 프로그램과 데이터를 영구히 저장하기에는 너무 작다.
2. 메인 메모리는 전원이 공급되지 않으면 그 내용을 잃어버리는 휘발성 저장장치이다.

⇒ 따라서, 대부분의 컴퓨터 시스템은 보조저장장치를 제공한다.

ex) HDD(Hard Disk Drive ), NVM(Non-volatile Memory)

</aside>

※ ‘storage capacity’ 및 ‘access time’ 에 따라 계층 구조를 구성할 수 있다.

![Main memory = RAM, Nonvolatile memory = SSD, Hard-disk drives = HDD](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/469cdc82-13cd-4f5d-8814-c9d09bf93065/Untitled.png)

Main memory = RAM, Nonvolatile memory = SSD, Hard-disk drives = HDD

(3) I/O Structure

: OS code 의 상당 부분은 시스템 안정성과 성능에 대한 중요성과 장치의 다양한 특성으로 인해 I/O 관리에 할애된다.

※ 인터럽트 구동 I/O 형태는 대량 데이터 이동에 사용할 시, 오버헤드를 유발시킨다. ⇒ 해결 : **DMA**

? **DMA** (Direct Memory Access) 

= 메모리 버퍼, 포인터, 카운터를 사용하여 장치 제어기가 CPU 도움없이 DMA 컨트롤러를 이용하여 데이터를 직접 메모리로 전송하는 I/O 방식

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/59e1ed55-fc7c-4615-8aec-5894c12236ec/Untitled.png)

---

## Computer-System Architecture

? **Key point !** **Computer System Architecture**

> Computer System Architecture
> 
> 1. Single-Processor Systems
> 2. Multiprocessor Systems
> 3. Clustered Systems

> Computer System Components
> 
> - **CPU** = 명령을 실행하는 HW
> - **Processor** = 하나 이상의 CPU 를 포함하는 물리적 칩
> - **Core** = CPU 의 기본 계산 단위
> - **Multi-core** = 동일한 CPU 에 여러 컴퓨팅 코어를 포함
> - **Multi-processor** = 여러 프로세서를 포함

1. Single-Processor Systems

? **Single-Processor System** = 하나의 Processor 를 포함하는 컴퓨터 시스템

┕ 단일 처리 Core를 가진 범용 CPU가 하나만 있는 경우가 대부분이다.

1. Multiprocessor Systems

? **Multiprocessor System** = 2개 이상의 Processor 를 포함하는 컴퓨터 시스템

┕ 장점 : 처리량 증가 - 프로세서 수가 증가해도 더 적은 시간에 더 많은 작업을 수행할 수 있다.

┕ The Most common Multiprocessor system

- **SMP** (Symmetric Multi Processing)
    
    = 각 peer CPU 프로세서 가 OS 기능 및 사용자 프로세스를 포함한 모든 작업을 수행 
    
    = 2개 이상의 동일한 프로세서가 하나의 자원을 공유하여 단일 시스템 버스를 통해, 각각의 프로세서는 다른 프로그램을 실행하고 다른 데이터를 처리하는 시스템
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a676dabc-9bf5-4f1b-aea4-6e0993ab5b08/Untitled.png)
    
    ↔ **AMP** (Asymmetric Multiprocessing)
    
    = 각 프로세서는 특정 작업이 할당된다.
    
    = 2개 이상의 각각의 프로세서 자신만의 다른 특정 기능을 수행하는 구조
    

┕ **Multi-core system**

= 여러 개의 컴퓨팅 코어가 단일 칩에 상주하는 컴퓨터 시스템

: Processor 내의 통신이 Processor 간의 통신보다 빠름 

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/94ae131b-b22b-427c-abf7-2537138ccc2e/Untitled.png)

? **NUMA** (Non-Unifor Memory Access) 

= Multi Processor 에 작고 빠른 local bus 를 통해 액세스 되는 자체 local memory 를 제공하고, 모든 CPU가 공유 시스템 연결로 연결되어 모든 CPU가 하나의 물리 주소 공간을 공유하는 방법

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/31e19e71-dfde-43c8-a032-4c0a7bf59b99/Untitled.png)

┕ 장점 : CPU가 local memory에 액세스 할 때 빠름 & 시스템 상호연결에 대한 경합 X

┕ 단점 : CPU가 시스템 상호 연결을 통해 원격 메모리에 액세스해야 할 때 delay time 증가로 성능 저하 발생

? **블레이드 서버** 

= 다수의 Processor Board, I/O Board, Networking Board들이 하나의 chassis 안에 장착되는 형태

1. Clustered Systems

? **Clustered System =** 둘 이상의 독자적 시스템 또는 노드들을 연결하여 구성

… 각 노드는 통상 *다중 코어 시스템*이고, 그러한 시스템은 *약결합(loosely coupled)*이라고 간주된다.

┕ 높은 가용성 제공 

: 클러스터 내 하나 이상의 컴퓨터 시스템이 고장나더라도 서비스는 계속 제공된다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/dcdc728c-c34a-4faa-b082-7194768a60ef/Untitled.png)

┕ 2 clustering

- **비대칭형 클러스터링** = 다른 컴퓨터들이 응용 프로그램을 실행하는 동안 한 컴퓨터는 긴급 대기 (hot-standby) 모드 상태를 유지한다.
- **대칭형 클러스터링** = 둘 이상의 host들이 응용 프로그램을 실행하고 서로를 감시한다.

---

## Operating System Operations

? **Key point !** **Multi-programming, Multi-tasking**

? **Multi-programming**

= 한번에 2개 이상의 프로그램을 실행한다.

┕ 동시에 여러 프로세스를 메모리에 유지

⇒  CPU 사용률을 높인다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/03b004e1-3939-4c26-bcba-6e4c75b58533/Untitled.png)

? **Multi-tasking** (= Multi-processing) 

┕ Multi-programming의 논리적 확장

= CPU 는 여러 프로세스를 전환하며 프로세스를 실행하지만, 전환이 자주 발생하여 사용자에게 빠른 응답 시간을 제공한다.

… concurrency

… pallelism

? **CPU sheduling**

= 여러 프로세스가 동시에 실행할 준비가 되면, 시스템은 다음 실행할 프로세스를 선택해야 한다. 이때 결정을 내리는 역할을 한다.

<aside>
? OS와 user는 컴퓨터 시스템의 HW 및 SW 자원을 공유하기 때문에 올바르게 설계된 OS는 잘못된 프로그램으로 인해 프로그램 or OS 자체가 잘못 실행될 수 없도록 보장해야 한다.

⇒ 올바르게 실행하려면 “OS 코드 실행” 과 “사용자 정의 코드 실행”을 구분해야 한다.

</aside>

> 2개의 독립된 연산 모드
> 
> - user mode
> - kernel mode

![사용자 Application 이 OS로부터 서비스를 요청하면 (시스템 콜) 이 요청을 수행하기 위해서 user mode → kernel mode로 전환해야 한다. 이는 잘못된 사용자로부터 OS를, 그리고 잘못된 사용자 서로를 보호하는 방법을 제공한다.](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/26839b21-a4db-4c17-9042-13ef6900f03f/Untitled.png)

사용자 Application 이 OS로부터 서비스를 요청하면 (시스템 콜) 이 요청을 수행하기 위해서 user mode → kernel mode로 전환해야 한다. 이는 잘못된 사용자로부터 OS를, 그리고 잘못된 사용자 서로를 보호하는 방법을 제공한다.

\- **모드 비트(mode bit)**는 하나의 비트가 현재의 모드를 가나타내기 위해 컴퓨터의 HW에 추가되었다.

\- 모드 비트 사용으로 OS를 위해 실행되는 작업과 사용자를 위해 실행되는 작업을 구분할 수 있게 되었다.

┕ **VMM** (Virtual Machine Manager)**=** 가상화를 지원하는 머신 모니터

<aside>
? 사용자 프로그램이 무한 루프에 빠지거나 시스템 서비스 호출에 실패하여 제어가 OS로 복귀하지 않는 경우가 없도록 이를 방지해야 한다.

⇒ *타이머* 사용

</aside>

? **Timer** = 지정된 시간 후 컴퓨터를 인터럽트 하도록 설정할 수 있다.

---

## Resource Management

? **Key point !** **Resource Management**

> Management
> 
> 1. Process Management
> 2. Memory Management
> 3. File-System Management
> 4. Mass-Storage Management
> 5. Cache Management
> 6. I/O Systems Management

1. Process Management

<aside>
? 프로세스는 자기 일을 수행하기 위해 CPU 시간, 메모리, 파일, I/O를 포함한 여러가지 자원을 필요로 한다.

</aside>

┕ 프로그램 그 자체는 프로세스가 아니다.

\- 프로그램 : 디스크에 저장된 파일의 내용과 같이 Passive(수동적) 개체

\- 프로세스 : 다음 수행할 명령을 지정하는 **프로그램 카운터(program counter)** 를 가진 Active(능동적) 개체

┕ 시스템은 프로세스의 집합으로 구성된다.

: OS 프로세스와 사용자 프로세스

> OS는 프로세스 관리와 연관해 다음과 같은 활동에 대해 책임진다.
> 
> - 사용자 프로세스와 시스템 프로세스의 생성 / 제거
> - CPU에 프로세스와 스레드 스케줄하기
> - 프로세스의 일시중지와 재수행
> - 프로세스 동기화를 위한 기법 제공
> - 프로세스 통신을 위한 기법 제공

1. Memory Management

<aside>
? 메인 메모리는 CPU와 I/O 장치에 의해 공유되는, 빠른 접근이 가능한 데이터 저장소이다.

</aside>

┕ 프로그램이 수행되기 위해선 *반드시 절대 주소로 mapping 되고 메모리에 적재되어야 한다.*

┕ 메모리 관리 기법 필요 : CPU 이용률과 사용자에 대한 컴퓨터 응답 속도 개선을 위함 

> OS는 메모리 관리와 관련하여 다음과 같은 일을 담당해야 한다.
> 
> - 메모리의 어느 부분이 현재 사용되고 있으며, 어느 프로세스에 의해 사용되고 있는지를 추적해야 한다.
> - 필요에 따라 메모리 공간을 할당하고 회수해야 한다.
> - 어떤 프로세스(or 그 일부)들을 메모리에 적재하고 제거할 것인가를 결정해야 한다.

1. File-System Management

<aside>
? OS는 저장장치의 물리적 특성을 추상화하여 논리적인 저장 단위인 파일을 정의한다.

</aside>

┕ 파일은 파일 생성자에 의해 정의된 관련 정보의 집합체이다.

… 일반적으로 파일은 프로그램(소스와 목적 프로그램 형태)과 데이터를 나타낸다.

: 통상 *디렉토리*로 구성하여 사용하기 쉽도록 만든다.

┕ OS는 대량 저장 매체와 그것을 제어하는 장치를 관리함으로써 

⇒ 파일의 추상적인 개념을 구현한다.

> OS는 파일 관리를 위해 다음과 같은 일을 담당한다.
> 
> - 파일의 생성 / 제거
> - 디렉터리 생성 / 제거
> - 파일과 디렉터리를 조작하기 위한 프리미티브의 제공
> - 파일을 보조저장장치로 mapping
> - 안정적인 (비휘발성) 저장 매체에 파일을 백업

1. Mass-Storage Management

<aside>
? 컴퓨터 시스템은 메인 메모리를 백업하기 위해 보조저장장치(HDD, NVM 등)를 제공해야 한다.

</aside>

> OS는 보조저장장치 관리와 관련하여 다음 활동을 책임진다.
> 
> - 마운팅과 언마우팅
> - 사용 가능 공간(free-space)의 관리
> - 저장장소 할당
> - 디스크 스케줄링
> - 저장장치 분할
> - 보호

? **마운팅** (Mounting) 

= 해당 파일 시스템이 디렉토리 (마운트 지점) 에 연결되어 시스템에서 사용될 수 있게 만드는 작업

? **언마운팅** (Unmounting) 

= 해당 파일 시스템의 마운트 지점 연결을 끊어 시스템에서 사용할 수 없게 만드는 작업

1. Cache Management

<aside>
? **캐시(Cache)** 는 컴퓨터 과학에서 데이터나 값을 미리 복사해 놓는 임시 장소를 가리킨다.

</aside>

┕ *캐싱은 컴퓨터 시스템의 중요한 원리이다.*

: 정보는 통상 어떤 저장장치에 보관된다. 정보가 사용됨에 따라, 더 빠른 장치인 캐시에 일시적으로 복사된다.

⇒ 따라서, 특정 정보가 필요할 경우, *먼저 캐시에 그 정보가 있는지를 조사해 봐야 한다.*

… 만약 캐시에 정보가 없다면, 메인 메모리 시스템으로부터 그 정보를 가져와서 사용해야 한다. 이때, 이 정보가 다음에 곧 다시 사용될 확률이 높다는 가정하에 캐시에 넣는다.

┕ **캐시 관리**는 중요한 설계 문제이다.

why ? 캐시 크기가 제한되어 있으므로 크기와 교체 정책에 따라 성능이 달라진다.

┕ **캐시 일관성 문제** 고려 

: 한 캐시에 있는 A값이 갱신될 경우, A가 존재하는 모든 캐시에 즉각적으로 반영되어야 한다.

1. I/O Systems Management

<aside>
? OS 목적 중 하나는 사용자에게 특정 HW 장치의 특성을 숨기는 것이다

… 단지 장치 드라이버만이 자신에게 지정된 특정 장치의 특성을 알고 있다.

</aside>

> I/O 시스템은 다음과 같이 구성되어 있다.
> 
> - 버퍼링, 캐싱, 스풀링을 포함한 메모리 관리 구성 요소
> - 일반적인 장치 드라이버 인터페이스
> - 특정 HW 장치들을 위한 드라이버

---

## Security and Protection

? **Key point !** **Security, Protection**

? **Security** 

= 컴퓨터 시스템이 정의한 자원에 대해 프로그램, 프로세스 or 사용자들의 접근을 제어하는 기법

┕ 시행될 제어에 대한 명세와 이들을 강제 시행하는 방법을 규정하는 수단을 반드시 제공해야 한다.

? **Protection** 

= 외부 or 내부의 공격을 방어하는 기능

┕ 시스템의 모든 사용자를 구분하기 위해 사용자 식별자, 그룹 식별자, 유효 사용자 식별자가 존재한다.

---

## Virtualization

? **Key point !** **Virtualization, VMM**

? **Virtualization**

= 단일 컴퓨터(CPU, memory, Disk Drive,  NIC 등)의 HW를 여러 가지 실행 환경으로 추상화하여 개별 환경이 자신만의 컴퓨터에서 실행되고 있다는 환상을 만들 수 있는 기술

┕ **VMM** (Vitrual Machine Manager)

= Guest OS를 수행하고 그들의 자원 이용을 관리하며 각 Guest를 서로로부터 보호

ex) VMware, XEN, WSL 등

![다수의 OS를 필요로 할 때 가상화를 이용하여 하나의 물리 서버를 이용해 모든 OS가 개발, 테스팅, 디버깅을 수행할 수 있다.](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2dfc46f8-c483-4a51-ba23-5c33098d2bbb/Untitled.png)

다수의 OS를 필요로 할 때 가상화를 이용하여 하나의 물리 서버를 이용해 모든 OS가 개발, 테스팅, 디버깅을 수행할 수 있다.

---

## Distributed Systems

? **Key point !** **Distributed Systems**

? **분산 시스템 =** 물리적으로 떨어져 있는 이기종 컴퓨터들의 집합

┕ 사용자가 시스템 내의 다양한 자원을 접근할 수 있도록 *네트워크로 연결*되어 있다.

? **네트워크** = 2개 이상 시스템 간의 통신 경로

┕ 분산 시스템의 많은 기능이 네트워킹에 의존하고 있다.

┕ **TCP/IP** = 가장 일반적인 네트워크 프로토콜, 인터넷의 기본 구조를 제공

- 네트워크는 노드 간의 거리에 의해 유형이 결정된다.
    - **LAN** (근거리 통신망) = 가까운 거리에 있는 각종 기기를 통신 회선으로 연결한 통신망
    - **WAN** (광역 통신망) = 국가 or 대륙과 같은 매우 넓은 지역을 대상으로 하는 통신망
    - **MAN** (도시권 통신망) = 도시 규모의 거리에 있는 컴퓨터들을 통신 회선으로 연결한 통신망
    - **PAN** (단거리 통신망) = 3m 이내의 거리의 인접 지역 간의 통신망

---

## Kernel Data Structures

? **Key point !** **Data Structures**

> Data Structures
> 
> 1. 리스트, 스택 및 큐
> 2. 트리
> 3. 해시 함수와 맵
> 4. 비트맵

1. 리스트, 스택 및 큐

? **리스트** = 데이터 값들의 집단을 하나의 시퀀스로 표시한다.

┕ **연결 리스트 (Linked List)**

\- 장점 : 가변 수의 항들을 수용하며 항의 삭제와 삽입이 쉽다.

\- 단점 : 시간복잡도가 O(n) 이다.

- **Singly Linked List**

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/73577985-7504-4bdc-8dcd-153a44d8fad0/Untitled.png)

- **Doubly Linked List**

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d065abab-89a5-4593-842c-07abdd8afefb/Untitled.png)

- **Circularly Linked List**

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/02725c4a-2048-4a2e-b536-040b7d2b5c3b/Untitled.png)

? **스택 (Stack)** = 후입선출(LIFO, Last In First Out) 사용

┕ 삽입 : push , 제거 : pop

? **큐 (Queue)** = 선입선출(FIFO, Fist In First Out) 사용

1. 트리

? **트리 (Tree)** = 데이터의 서열을 표시하는데 사용가능한 자료구조

┕ 데이터 값은 부모-자식 관계로 연결된다.

> 트리 종류
> 
> - **일반 트리**
>     
>     = 부모 노드는 임의의 수의 자식 노드를 가질 수 있다.
>     
> - **이진 트리**
>     
>     = 부모 노드는 최대 2개의 자식 노드를 가질 수 있다.
>     
> - **이진 탐색 트리**
>     
>     = 좌측 자식 노드 ≤ 우측 자식 노드 순서로 이루어 진다.
>     
> - **균형 이진 탐색 트리**
>     
>     = 좌측과 우측 자식 트리 간에 균형있는 높이를 가지도록 하는 조건이 있다.
>     

1. 해시 함수와 맵

? **해시 함수** = 데이터를 입력으로 받아 이 데이터에 산술 연산을 수행하여 하나의 수를 반환한다.

┕ 이 수는 데이터를 인출하기 위해 테이블의 인덱스로 사용가능하다.

┕ **해시 충돌** (hasp collision) = 2개의 서로 다른 입력이 하나의 출력 값을 가질 수 있을 때 발생한다.

※ 해시 함수의 한 용도는 **해시 맵 (Hash Map)** 을 구현하는 것이다.

… [키:값] 을 매핑시킨다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/717f088f-91a7-4eac-a564-829dd390130f/Untitled.png)

1. 비트맵

? **비트맵** (Bitmaps) = n개의 항의 상태를 나타내는 데 사용가능한 n개의 이진 비트의 문자열

┕ 일반적으로 대량의 자원의 가용성을 표시할 때 사용된다.

*ex) 001011101*

---

## Computing Environments

? **Key point !** **Computing Environments**

> Variety of Computing Environment
> 
> - Traditional Computing
> - Moblie Computing
> - Client-Server Computing
> - Peer-to-Peer Computing
> - Cloud Computing
> - Real-Time Embedded Systems

(1) Traditional Computing

\- 오늘날 웹 기술이 전통적인 계산의 경계를 확장하고 있다.

- 회사 : 자신의 내부 서버에 웹 접근을 제공하는 포털을 구현하고 있다. 네트워크 컴퓨터를 통해 높은 보안이나 쉬운보수가 필요한 경우 전통적 워크스테이션을 대체하여 사용되고 있다.
- 개인 : 모바일 컴퓨터나 휴대용 컴퓨터를 통해 웹 포털을 사용한다.

(2) Moblie Computing

? **Moblie Computing** = 휴대용 스마트폰과 태블릿 컴퓨터의 컴퓨팅 환경

┕ 이동 가능하고 가볍다.

┕ 온라인 서비스에 접근을 허용하기 위해 휴대장치는 전형적으로 IEEE 표준 802.11 무선 or 휴대전화 데이터망을 사용한다.

┕ 현재 대표적인 모바일 OS : Apple iOS, Goole Android

(3) Client-Server Computing

? **Client-Server Computing =** 서버 시스템이 클라이언트 시스템이 생성한 요청을 만족시키는 컴퓨팅

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8a40c3ae-fb9d-47c2-9f3b-d45cef4ce16e/Untitled.png)

- 서버 시스템의 2가지 시스템
    - **계산-서버 시스템**
        
        = Client가 어떤 작업을 요청할 인터페이스를 제공한다. 그 결과로 서버는 그 작업을 수행하고, 결과를 Client에게 돌려보낸다. 
        
        *ex) DB 서버*
        
    - **파일-서버 시스템**
        
        = Client가 파일을 생성, 갱신, 읽기 및 제거할 수 있는 파일 시스템 인터페이스를 제공한다.
        
        *ex) Web 서버*
        
    
     
    

(4) Peer-to-Peer Computing

? **Peer-to-Peer Computing** = Client와 서버가 서로 구별되지 않고, 시스템상의 모든 노드가 peer로 간주되고, 각 peer는 서비스를 요청하느냐 제공하느냐에 따라 Client 및 서버로 동작한다.

┕ 클라이언트 서버 시스템과의 차이점

\- 클라이언트 서버 시스템 : 서버가 병목으로 작용

\- 피어 간 계산 : 서비스가 네트워크에 분산된 여러 노드에 의해 제공될 수 있다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4fe4f553-9020-4b26-9c86-950492c0b67e/Untitled.png)

(5) Cloud Computing

? ****Cloud Computing** = 계산, 저장장치는 물론 Application조차도 네트워크를 통한 서비스를 제공하는 컴퓨팅 타입

*ex) Amazon의 Elastic Computer Cloud(EC2)*

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/783778a0-1ff6-4380-9dad-2dbc550a2aa4/Untitled.png)

> 클라우드 컴퓨팅 유형
> 
> - **공중 클라우드**
>     
>     = 서비스를 위해 지불 가능한 사람은 누구나 인터넷을 통해 사용 가능한 클라우드
>     
> - **사유 클라우드**
>     
>     = 한 회사가 사용하기 위해 운영하는 클라우드
>     
> - **혼합형 클라우드**
>     
>     = 공공과 사유 부분을 모두 포함하는 크랄우드
>     
> - **소프트웨어 서비스(SaaS, software as a service)**
>     
>     = 인터넷을 통해 사용 가능한 하나 이상의 Application
>     
>     *ex) Word Process, Spread Sheets*
>     
> - **플랫폼 서비스(PaaS, platform as a service)**
>     
>     = 인터넷을 통해 사용하도록 Application에 맞게 준비된 소프트웨어 스택
>     
>     *ex) Database Server, Jenkins Server, …*
>     
> - **하부구조 서비스(IaaS, infrastructure as a service)**
>     
>     = 인터넷을 통해 사용가능한 서버나 저장장치
>     
>     *ex) 생산 데이터의 백업 복사본을 만들기 위한 저장장치*
>     

※ 클라우드 컴퓨팅 환경은 다수 유형의 조합을 제공하기 때문에 이들 클라우드 컴퓨팅의 유형들은 서로 독립적이 아니다.

(6) Real-Time Embedded Systems

? **Real-Time Embedded Systems :** 현재 가장 유행하는 컴퓨터의 형태

┕  **내장형 시스템** = 거의 언제나 실시간 운영체제를 수행한다.

┕ **실시간 시스템** = 프로세서의 작동이나 데이터의 흐름에 엄격한 시간 제약이 있을 때 사용된다.

---

## Free and Open-Source Operating Systems

\- 소프트웨어 사용 및 재배포를 제한하려는 움직임에 대응하기 위해 1984년 Richard Stallman은 GNU라는 무료 UNIX 호환 운영체제(“GNU’s Not Unix!”의 약어)를 개발하기 시작하였다.

\- 자유 소프트웨어 운동은 사용자에게 다음의 4가지 자유를 보장시키기 위해 시행되었다.

1. 자유롭게 소프트웨어를 실행시킬 권리
2. 소스 코드를 분석하고 수정할 권리
3. 코드 수정 없이 배포하거나 판매할 권리
4. 코드를 수정하여 배포하거나 판매할 권리

- 무료 OS : GNU/Linux, BSD Unix, Solaris