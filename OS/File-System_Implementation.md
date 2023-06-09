## 14.1 파일 시스템 구조

> 파일 시스템을 유지하기 위한 보조저장장치로 디스크가 대부분 사용된다.
> 
- 디스크가 보조저장장치로 사용되는 데에는 다음과 같은 2가지 특성이 있다.
    1. 디스크는 추가 장소를 사용하지 않고 재기록이 가능하다.
    2. 디스크에 있는 임의의 블록의 정보를 직접 접근할 수 있다. 따라서 임의의 파일을 순차적 또는 무작위 방법으로 쉽게 접근할 수 있다.

- 비휘발성 메모리(NVM) 장치는 파일 저장 및 파일 시스템의 장소로 점점 더 많이 사용되고 있다.
    
    

> I/O 효율성을 향상하기 위해 메모리와 대용량 스토리지 간의 I/O 전송이 **블록 단위**로 수행된다.
> 
- 하드 디스크 드라이브의 각 블록에는 하나 이상의 섹터가 있다.
- NVM 장치에는 일반적으로 4096 바이트의 블록이 있으며 디스크 드라이브에서 사용하는 것과 유사한 전송 방법이 사용된다.

> **파일 시스템**은 쉽게 데이터를 저장하고, 찾고, 인출할 수 있게 함으로써 저장장치를 더욱 효율적이고 편리하게 사용할 수 있게 한다.
> 
- 파일 시스템은 크게 2가지 설계 문제를 제기한다.
    1. 파일 시스템이 사용자에게 어떻게 보여야 할지를 정의하는 것이다.
        1. 즉, 파일은 무엇이며, 속성, 디렉터리 구조 등을 포함하낟.
    2. 논리 파일 시스템을 물리적인 2차 저장장치로 사상하는 알고리즘과 데이터 구조를 만드는 것이다.

- 파일 시스템은 여러 층으로 이루어져 있다.
    - ***각 층은 낮은 층의 기능을 사용하여 새로운 기능을 만들어 상위층에게 제공한다.***
    
    ![image](https://user-images.githubusercontent.com/87460638/236998645-2df7eba2-324f-44d6-9353-addd560f3fc2.png)
    

- **입출력 제어** 층은 장치 드라이버 루틴들과 인터럽트 핸들러로 이루어져 있어서 메모리와 디스크 시스템 간의 정보 전송을 담당한다.
    - 장치 드라이버는 통상 입출력 제어기 메모리의 특별한 위치에 특정 비트를 설정하여 제어기에 어느 장치에 어떤 일을 수행할지를 알린다.

- **기본 파일 시스템** 층은 적절한 장치 드라이버에게 저장장치상의 블록을 릭고 쓰도록 일반적인 명령을 내리는 층이다.
    - 이 층은 논리 블록 주소를 기반으로 드라이브에 명령을 내린다.
    - 또한 I/O 요청 스케줄링도 고려한다.
    - 다양한 파일 시스템, 디렉터리 및 데이터 블록을 저장하는 메모리 버퍼와 캐시를 관리한다.
        - 이 버퍼가 가득 차면, 버퍼 관리자는 요청된 입출력이 완료될 수 있도록 더 많은 버퍼 메모리를 찾아내거나 버퍼의 가용 공간을 확보해야 한다.
    - 캐시는 종종 성능을 향상하기 위해 파일 시스템 메타데이터를 저장하는 데 사용된다.

- **파일-구성 모듈** 층은 파이로가 상응하는 논리 블록을 알고 있다.
    - 각 파일의 논리 블록은 0 또는 1부터 N까지 번호를 준다.
    - 이 모듈은 어느 디스크 공간이 비어 있는지를 파악하는 가용 공간 관리자도 포함하고 있어 파일 구성 모듈이 요구할 대 이들 블록을 제공한다.

- **논리 파일 시스템** 층은 메타데이터 정보를 관리한다.
    - **메타 데이터**는 파일의 내용 자체인 데이터를 제외한 모든 파일 시스템 구조를 말한다.
    - 파일 이름을 심볼릭하게 줄 경우 처리하여 하위층인 파일 구성 모듈에 필요한 정보를 넘겨주기 위해 디렉터리 구조를 관리한다.
    - **파일 제어 블록(FCB)**는 소유, 허가, 파일 내용의 위치를 포함하여 파일에 관한 정보를 가지고 있다.

> 파일 시스템 구현을 위해 계층 구조를 사용함으로써, **코드의 중복이 최소화**되었다.
> 

> *현재 많은 파일 시스템들이 사용되고 있고, 대부분의 운영체제가 하나 이상의 파일 시스템을 지원하고 있다.*
> 
- UNIX
    - FFS(Berkeley Fast File System)에 기반을 둔 **UFS (UNIX File System)**을 사용한다.
    - 표준 파일 시스템은 **extended file system**이다.
- Windows
    - FAT, FAT32, NTFS 포맷을 지원한다.

## 14.2 파일 시스템 구현

### 14.2.1 개요

> 여러 저장장치상의 구조와 메모리 내 구조가 파일 시스템을 구현하는 데 사용된다.
> 
- 이들은 운영체제와 파일 시스템에 따라 다르지만, 몇 개의 일반적인 원칙은 공동 적용된다.

> *파일 시스템은 저장장치에 저장된 운영체제를 어떻게 부트시키는지, 그리고 블록의 총 수, 가용 블록의 수와 위치, 디렉터리 구조 그리고 개별 파일에 대한 정보를 **디스크 상에 가지고 있다.***
> 
- 디스크 상의 구조는 다음을 포함한다.
    - **부트 제어 블록**은 시스템이 그 파티션으로부터 운영체제를 부트시키는 데 필요한 정보를 가지고 있다.
        - UFS에서는 **부트 블록**, NTFS에서는 **파티션 부트 섹터**라 불린다.
    - **볼륨 제어 블록**은 볼륨의 블록의 수, 블록의 크기, 가용 블록의 수와 포인터, 그리고 가용 FCB 수와 포인터 같은 볼륨 정보를 포함한다.
        - UFS에서는 **슈퍼 블록**, NTFS에서는 **마스터 파일 테이블**이라 불린다.
    - 디렉터리 구조는 파일을 조직화하는 데 사용된다.
        - UFS에서는 디렉터리 구조에 파일 이름 및 해당 incode 번호가 저장된다.
        - NTFS에서는 마스터 파일 테이블에 이러한 정보가 저장된다.
    - 파일별 FCB는 자세한 파일 정보를 가지고 있다.
        - FCB는 디렉터리 항목과의 연결을 위해 고유한 식별 번호를 가지고 있다.
        

> *메모리 내의 정보는 **파일 시스템 관리와 캐싱**을 통한 **성능 향상을 위해** 사용된다.*
> 
- 이 정보들은 마운트 시점에 적재되고 → 파일 시스템 동작 중에 갱신 → 마운트 해제 시에 제거 된다.
- 다수의 자료구조 유형은 다음을 포함한다.
    - 메모리 내 파티션 테이블은 마운트된 모든 파티션 정보를 포함한다.
    - 메모리 내 디렉터리 구조는 최근 접근된 디렉터리의 디렉터리 정보를 가진다.
    - **범 시스템 오픈 파일 테이블(system wide open file table)**은 다른 정보와 더불어 오픈된 각 파일의 FCB의 복사본을 가지고 있다.
    - **프로세스별 오픈 파일 테이블(per-process open file table)**은 프로세스가 연 모든 파일에 대해 다른 정보뿐만 아니라 범 시스템 오픈 파일 테이블 내의 해당 항목에 대한 포인터를 포함한다.
    - 버퍼는 파일 시스템이 파일 시스템으로부터 읽히거나 써질 때 파일 시스템 블록을 저장한다.

> 새로운 파일을 생성하기 위해 프로세스는 논리 파일 시스템을 호출한다.
> 
- 논리 파일 시스템은 디렉터리 구조의 포맷을 알고 있다.

![image](https://user-images.githubusercontent.com/87460638/236998670-18b02ddd-078d-4023-a15c-c6a907d81177.png)

> UNIX를 포함하여 몇몇 운영체제는 디렉터리를 하나의 파일로 간주한다.
> 
- Windows NT와 같은 다른 운영체제에서는 디렉터리와 파일을 별개로 간주하며, 각각의 시스템 콜을 가지고 있다.
- 논리적 파일 시스템은 디렉터리 입출력을 저장장치 블록 위치로 매핑하기 위해 파일 구성 모듈을 호출한다.
    - 이것은 기본적인 파일 시스템과 입출력 제어 시스템에서 처리된다.

### 14.2.2 사용법

> 새로운 파일이 생성되면 입출력을 위해 사용될 수 있다.
> 

![image](https://user-images.githubusercontent.com/87460638/236998688-31cd3262-8f70-40af-9a71-c7949a778244.png)

1. 우선 `open()` 시스템 콜을 호출하여 논리 파일 시스템에 파일 이름을 넘겨준다.
    1. 이때 다른 프로세스가 파일을 사용 중인지 확인하기 위해 범 시스템 오픈 파일 시스템을 검색한다.
        - 사용 중이면 → 기존 범 시스템 오픈 파일 테이블을 가리키는 프로세스별 오픈 파일 테이블 항목이 생성된다.
        - 그렇지 않다면 → 주어진 파일 이름을 디렉터리 구조에서 찾는다.

1. 범 시스템 오픈 테이블의 항목에 대한 포인터와 몇 개의 다른 필드를 갖는 새로운 항목이 프로세스별 오픈 파일 테이블 안에 만들어 진다.
    1. 이 필드들은 파일 안의 현재 위치를 가리키는 포인터와 파일이 오픈된 접근 모드 등을 포함한다.
    2. 파일 이름은 같은 파일에 대한 다중 오픈 연산을 빠르게 하기 위해 캐쉬될 수 있다.
        - UNIX에서는 이것을 **파일 디스크립터** 라 부르고,
        - Windows는 **파일 핸들** 이라 한다.

 

1. 프로세스가 파일을 닫을 때, 프로세스별 테이블 항이 삭제되며 범 시스템 항목의 오픈 계수는 감소한다.

> *이러한 기법은 실제 데이터 블록을 제외한 오픈 파일에 대한 모든 정보는 메모리 내에 존재하게 한다.*
⇒ 디스크 입출력 작업을 줄일 수 있다. ex) BSD UNIX 시스템
> 

## 14.3 디렉터리 구현

> *디렉터리 공간을 어떻게 할당하고 관리하는가는 파일 시스템의 효율, 성능과 신뢰성에 큰 영향을 미친다.*
> 

→ 이들 알고리즘과 연관되어 있는 문제점들을 이해할 필요가 있다 !

### 14.3.1 선형 리스트

> 가장 간단한 방법으로써, **파일 이름과 데이터 블록에 대한 포인터들의 선형 리스트**를 디렉터리에 사용하는 것이다.
> 

⇒ 이 방법은 프로그램이 쉽지만, 실행 시간이 길다.

- 파일 생성하기 위해서는
    - → 같은 이름을 가진 파일의 존재 여부를 확인한 후, 디렉터리에 추가된다.
- 파일을 삭제하려면
    - → 디렉터리에서 이름을 찾아 그 파일에 할당된 공간을 방출한다.
- 디렉터리 항목을 재사용하기 위해서는
    - → 항목을 미사용으로 표시하거나 or 가용 디렉터리 항목 리스트에 추가할 수 있다.

- 이 방법의 가장 큰 단점은 파일을 찾기 위해 **선형 탐색을 해야 한다**는 점이다.
- 반대로 장점은 **별도의 정렬 없이 정렬된 디렉터리 목록을 생산할 수 있다**는 점이다.

### 14.3.2 해시 테이블

> 해시 테이블은 파일 이름을 제시하면 **해시로부터 값을 얻어서 그것을 포인터로 활용**하여 리스트에 직접 접근할 수 있다.
> 

→ 디렉터리 탐색 시간을 상당히 개선할 수 있다.

- 해시 테이블의 심각한 문제점은 해시 테이블이 고정된 크기를 갖는다는 점과 해시 테이블의 크기에 따라 해시 기능도 제한 받는다는 점이다.
    - → 대안으로, **체인 오버플로우 해시 테이블**을 사용할 수 있다.
    - 각 해시 항목은 하나의 값이 아닌 연결 리스트가 되고, 새로운 항목을 연결 리스트에 추가함으로써 충돌을 해결한다.

## 14.4 할당 방법

> 보조저장장치의 직접 접근 특성이 파일의 구현에 융통성을 허용한다.
> 
- 여기서 주요 문제는 “*파일들을 어떻게 저장장치 공간에 배치해야 디스크 공간을 효율적으로 사용할 수 있고, 파일들을 빨리 접근할 수 있는가”* 이다.
    - → 할당 기법 3가지 : 연속, 연결, 인덱스 기법

### 14.4.1 연속 할당

> **연속 할당**은 각 파일이 저장장치 내에서 연속적인 공간을 차지하도록 요구한다.
> 

→ 구현하기 쉽지만 한계가 있기 때문에 최신 파일 시스템에서는 사용되지 않는다.

- 장치 주소는 장치상에서 선형 순서를 정의한다.
    - HDD의 경우, 연속 할당된 파일들을 접근하기 위해서 필요한 디스크 탐색의 횟수를 최소화할 수 있다.

> 한 파일의 연속할당은 **첫 번째 블록의 주소와 블록단위의 길이**로 정의된다.
> 

![image](https://user-images.githubusercontent.com/87460638/236998719-7e8b21e0-20e2-4563-bf80-d210d31a4a2e.png)

- 파일의 길이가 n 블록이고 블록 b에서 시작한다면
    - → 이 파일은 블록 b, b+1, b+2 … b+n-1 을 차지한다.
- 각 파일을 위한 디렉터리 항목(entry)은 이 파일의 디스크 내 시작 블록 주소와 이 파일의 크기만 표시하면 된다.

> 연속적으로 할당된 파일을 순차적으로 접근하려면 파일 시스템은 가장 최근 참조된 주소를 기억하고 있다가 필요할 때 다음 블록을 읽어 들이면 된다.
> 
- 블록 b에서 시작하는 파일의 “i 번째 블록을 직접 접근”하기 위해서는 블록 b+i 를 즉시 접근할 수 있다.
    - ***따라서 연속 할당 기법은 순차 접근과 직접 접근 모두 지원할 수 있다.***

> 연속 할당 기법에서 한 가지 어려운 점은 **새로운 파일을 위한 가용 공간을 찾는 일**이다.
> 
- 이 문제는 9.2절에서 논의한 **동적 공간 할당 문제**의 특정 응용으로 볼 수 있다.
    - 최초 적합과 최적 적합 중에서 할당할 공간을 선택하는 전략이 일반적이다.

> 또한 이들 알고리즘은 모두 **외부 단편화** 때문에 어려움이 있다.
> 
- 파일이 할당되고 반납됨에 따라 가용 디스크 공간이 조그만 조각으로 나누어진다.
    - 외부 단편화는 가용 공간이 덩어리들로 나누어질 때마다 발생한다.
    - 이때 제일 큰 연속된 덩어리가 요구된 크기보다 작을 때 문제가 된다.
    - 디스크 공간의 전체 크기와 평균 파일 크기에 따라 외부 단편화가 작거나 큰 문제가 될 수 있다.

> 외부 단편화로 인한 공간 손실을 방지하는 한 가지 정책은 **전체 파일 시스템을 다른 장치로 복사하는 것**이다.
> 
- 원래 장치는 완전히 비게 되어 하나의 커다란 연속적인 가용공간이 된다.
    - 그런 후에 이 공간으로부터 할당받으면서 파일들을 원래의 장치로 다시 복사하게 된다.
    - 이 기법은 효과적으로 **모든 가용 공간을 하나의 연속공간으로 밀집함**으로써 단편화 문제를 해결한다.

- 그러나 대용량 저장장치의 경우, 밀집에 드는 시간과 비용이 매우 많이 들 수 있다.
    - 따라서 일부 시스템에서는 이러한 작업을 파일 시스템의 마운트를 해제한 상태로 **오프라인** 시점에서 처리한다.
    - 단편화를 완화해야 하는 대부분의 현존하는 시스템은 **온라인** 시점인 시스템의 정상 가동 중에 밀집 작업을 처리한다.

> 또 다른 문제점은 파일을 위해서 얼마나 많은 공간을 주어야 할지를 결정하는 것이다.
> 
- 파일 생성자가 생성될 파일의 크기를 예측하는 것은 어렵다.
    - 만약 너무 작은 공간을 할당한다면 ? → 파일이 커질 수 없다 !

> 이런 단점들을 최소화하기 위해, 운영체제는 어느 정도의 ***연속된 공간만 초기에 할당하고 추후 n개의 연속된 공간을 단위(extent)로 할당***한다.
> 

### 14.4.2 연결 할당

> **연결 할당**의 경우, 파일은 저장장치 블록의 연결 리스트 형태로 저장되고 블록들은 장치 내에 흩어져 저장될 수 있다.
> 

→ 연속 할당의 모든 문제를 해결 !

![image](https://user-images.githubusercontent.com/87460638/236998737-acdcc5cc-7374-4c1a-aca4-48d53f123582.png)

> 새 파일을 생성하려면 단순히 디렉터리 내에 새로운 항목(entry)을 만든다.
> 
- 외부 단편화가 없고 모든 블록은 크기가 같다.
- 가용 공간 리스트의 어떠한 가용 블록들을 이용하여도 무방하다.
- 파일 생성 시, 생성할 때 파일의 크기가 미리 고정될 필요도 없다.
- 파일은 계속해서 얼마든지 커질 수 있고, 디스크 공간을 주기적으로 밀집화할 필요도 없다.

> 그러나 연결 할당의 가장 중요한 단점은 **순차적 접근 파일에만 효과적으로 사용된다**는 것이다.
> 
- 직접 접근 방식에는 매우 비효율적이다.
    - i번째 블록을 찾으려면 그 파일의 첫 블록 한 번의 저장장치 읽기와 때로는 HDD 탐색이 필요하다.

> 또 다른 단점은 **포인터들을 위한 공간이 필요하다**는 것이다.
> 
- 일반적인 해결책은 블록을 모아 **클러스터(cluster)** 단위를 만들고 블록이 아닌 클러스터를 할당하는 것이다.
    - 이 방법을 사용하면 논리적-물리적 블록 매핑을 간단하게 유지할 수 있지만,
    - 디스크 헤드 탐색이 줄어들기 때문에 HDD 처리량이 향상되고 블록 할당 및 가용 리스트 관리에 필요한 공간이 줄어든다.
    - 또한 소량의 데이터 요청이 많은 양의 데이터 전송을 유발하기 때문에 임의 I/O 성능이 저한된다.

> 또 다른 문제는 **신뢰성의 문제**이다.
> 
- 각 블록이 전체 장치에 흩어져 연결되기 때문에 오류나 하드웨어의 고장으로 인하여 하나의 포인터를 잃어버리거나 잘못된 포인터 값을 가지게 되면 결국 모든 데이터를 잃는 결과를 초래할 수 있다.
    - 이 경우 이중 연결 리스트를 사용하거나 or 블록마다 파일 이름과 상대 블록 번호 등을 저장하는 방법을 같이 쓸 수 있다.
    - 그러나 이러한 기법들은 더 많은 overhead을 발생시킨다.

> 한 가지 중요한 변형은 **파일 할당 테이블(FAT, File Allocation Table)**을 사용하는 것이다.
> 

→ 단순하지만 효율적인 이 방법은 MS-DOS 운영체제에서 사용된다.

![image](https://user-images.githubusercontent.com/87460638/236998757-3515d036-148d-4142-b3df-8ffea439717d.png)

1. 각 블록마다 한 개의 항목을 가지고 있고 이 항목은 디스크 블록 번호를 인덱스로 찾는다.
    1. 디렉터리 항목은 각 파일의 첫 번째 블록 번호를 가리킨다.
2. 블록 번호를 가지고 FAT 테이블로 가면 그 항목은 다음 블록의 블록 번호를 가리킨다.

⇒ *이러한 사슬이 반복되고, 마지막 블록의 테이블 항은 파일의 끝을 나타내는 특수한 값을 가지고 있다.*

> ***FAT 할당 기법은 FAT가 캐시 되지 않으면 상당한 수의 디스크 찾기를 유발할 수 있음을 유의하자 !***
> 

### 14.4.3 색인 할당

> **색인 할당**은 모든 포인터들을 하나의 장소 즉, **색인 블록**으로 관리함으로써 직접 접근 방식을 지원한다.
> 

![image](https://user-images.githubusercontent.com/87460638/236998780-f973c7fa-5b60-4797-b9b4-c81965fcd7a2.png)

1. 각 파일은 저장장치 블록 주소를 모아놓은 배열인 색인(index) 블록을 가진다.
2. 색인 블록의 i번째 항목은 파일의 i번째 블록을 가리킨다.
3. 디렉터리는 색인 블록의 주소를 가지고 있다.
4. i번째 블록을 읽기 위해서는 색인 블록 항목에 있는 i번째 항목에서 포인터를 얻어서 그 블록을 읽는다.

- 파일이 생성될 때 인덱스 블록의 모든 포인터는 `null`로 설정된다.
- i번째 블록이 처음 쓰이면, 가용 블록 관리자로부터 한 블록을 받아 그 주소를 인덱스 블록의 i번째 항에 기록한다.

> 색인 할당을 사용하면 하나 또는 두 개의 포인터만 `null` 이 아니어도 전체 색인 블록을 할당해야 한다.
> 

→ 이 점이 색인 블록의 크기에 대한 쟁점을 제기한다.

- 이 문제를 처리하는 기법은 다음과 같다.
    - **연결 기법** = 하나의 색인 블록은 통상 한 저장장치 블록이다. 파일의 크기가 크면 여러 개의 색인 블록을 연결한다.
    - **다중 수준 색인** = 연결 기법의 변형으로서 여러 개의 두 번째 수준 색인 블록들의 집합을 가리키기 위하여 첫 번째 수준의 색인 블록을 사용한다. 두번째 수준의 색인 블록은 실제 파일 블록들을 가리킨다.
    - **결합 기법** = 파일의 incode에 색인 블록의 15개 포인터를 유지하는 것이다.
        - 이 포인터들의 처음 12개는 **직접 블록**을 가리키는데, 즉 이 포인터들은 파일의 데이터를 저장하고 있는 블록들의 주소를 저장한다.
        - 나머지 3개의 포인터는 간접 블록을 가리키는 포인터이다.
        - 첫 번째 포인터는 **단일 간접 블록**을 가리키고, 두번째 포인터는 **이중 간접 블록**을 가리킨다.
        - 마지막 포인터는 **삼중 간접 블록**의 주소를 저장한다.
    

> 색인 할당 기법은 연결 할당과 동일한 성능 문제를 갖는다.
> 
- 특히 색인 블록은 메모리에 캐시될 수 있지만 데이터 블록은 전체 볼륨 파티션 전체에 널리 펴져 있을 수 있다.

### 14.4.4 성능

> 할당 방법을 선택하기 전에, 시스템들이 어떤 목적을 위해 사용되느냐를 아는 것이 중요하다 !
> 

→ 순차 접근을 주로 하는 시스템은 랜덤 접근을 주로 하는 시스템과는 전혀 다른 방법을 사용할 것이다.

> 모든 유형의 액세스에 대해 연속 할당은 블록을 얻기 위해 **한 번의 액세스만 필요**하다.
> 
- 파일의 시작 주소를 메모리에 쉽게 유지할 수 있으므로 i번째 블록의 주소를 즉시 계산하여 직접 읽을 수 있다.

> 연결 할당의 경우, **다음 블록의 주소를 메모리에 유지하고 직접 읽을 수 있다.**
> 
- 이 방법은 순차적 액세스에 적합하다.
    - 그러나 직접 액세스의 경우, i번째 블록에 액세스 하려면 i번의 블록 읽기가 필요할 수 있기 때문에 사용해서는 안된다.

> 색인 할당의 경우, **색인 블록이 메모리 내에 상주한다면 직접 접근이 가능**하다.
> 
- 그러나 메모리 내에 색인 블록을 전부 상주시키는 것은 많은 양의 메모리를 필요로 한다.

> 일부 시스템은 연속 할당과 색인 할당을 결합하여 작은 파일은 연속 할당하고, 파일이 더 커지면 자동으로 색인 할당으로 전환한다.
> 
- 대부분의 파일이 작고 연속 할당이 작은 파일의 경우 효율적이기 때문에 평균 성능이 상당히 좋아진다.

## 14.5 가용 공간의 관리

- 저장장치 공간은 제한되어 있다 !
    - → 따라서 삭제된 파일들이 차지하던 공간을 새로운 파일들을 위해 다시 재사용해야 한다.

> ***시스템은 이러한 가용 공간을 리스트로 유지하고 관리한다.***
> 
- 이 리스트에 장치의 모든 가용 블록들을 등록한다.
    - 새로운 파일을 만들려면 이 **가용 공간 리스트**를 탐색하여 새로운 파일을 위한 공간을 할당받아야 한다.
    - 이렇게 할당된 공간은 가용 공간 리스트로부터 삭제된다.
    - 만약 이 파일이 삭제되면 → 그 파일이 쓰던 공간은 가용 공간 리스트에 추가된다.
    
- 가용 공간 리스트는 반드시 리스트 형태로만 구현될 필요는 없다 !

### 14.5.1 비트 벡터

> 가용 공간 리스트는 흔히 **비트 맵(bit map)** or **비트 벡터(bit vector)**로 구현된다.
> 
- 각 블록은 1비트로 표현된다.
    - 블록이 비어 있으면 → 1
    - 블록이 할당되어 있다면 → 0

> 예를 들어, 디스크의 블록들 2, 3, 4, 5, 8, 9, 10, 11, 12, 13, 17, 18, 25, 26, 27이 비어 있고, 나머지 블록은 할당되어 있다고 가정할 때, 가용 공간 비트맵은 다음과 같다.
> 

![image](https://user-images.githubusercontent.com/87460638/236998806-8436ff98-29db-4014-af39-55941f964cab.png)

> 이 방법의 큰 이점은 첫 번째 가용 블록 또는 n개의 연속된 가용 블록들을 찾는 일이 간편하고 효율적이라는 점이다.
> 
- 실제 많은 컴퓨터가 이 일을 효율적으로 수행할 수 있는 비트 조작 명령을 제공한다.
- 디스크 공간을 할당하기 위해 비트 벡터를 사용하는 시스템에서 첫 번째 가용 블록을 찾는 한 가지 방법은 비트맵의 각 워드를 순차적으로 검사하는 것이다.
    - 워드 값이 0이면 → 오직 0비트들로 구성되거 있다. ⇒ 할당된 블록임을 의미한다 !
    - 첫 번째 1인 비트를 찾으면 → 이 비트가 바로 첫 번째 가용 공간의 위치를 나타낸다 !

- 블록 번호는 다음과 같이 계산한다.
    - ***(워드의 비트수) x (값이 0인 워드의 수) + (첫 번째 1비트의 단위)***

> 안타깝게도 비트 벡터는 하드웨어 기능이 메인 메모리 내에 존재하지 않으면 비효율적이다.
> 
- 물론 복구할 때를 위하여 때때로 파일 시스템을 저장학 있는 장치에 기록되기는 하더라도 메모리에 존재해야 한다.
- 작은 디스크의 경우 비트 벡터를 메인 메모리에 유지하는 것이 가능하지만,
    - 용량이 큰 디스크의 경우에는 그렇지 않을 수 있다.

### 14.5.2 연결 리스트

> **연결 리스트**는 모든 가용 블록들을 함께 연결하는 것이다.
> 

![image](https://user-images.githubusercontent.com/87460638/236998834-5bfdb168-e2c1-4bd6-8322-a57c206f31e9.png)

- 첫 번째 가용 블록은 다음 가용 블록을 가리키는 포인터를 가진다.
    - 두 번째 가용 블록은 다음 가용 블록의 포인터를 갖고 … 이런 식으로 구현된다.
- 시스템은 첫 번째 가용 블록에 대한 포인터를 파일 시스템의 특정 위치에 두고, 메모리에 캐싱한다.

- HDD에서 이 기법은 리스트를 순회하려면 매번 디스크에 접근해야 한다. → 효율적이지 않다.
- 그러나 가용 리스트 순회는 그다지 빈번하게 일어나는 일은 아니다.
    - 통상 운영체제는 단순히, 파일에 할당하는 하나의 가용 블록이 필요하므로 가용 리스트의 첫 블록을 사용하게 된다.

- FAT 기법은 가용 블록 어카운팅과 할당 자료구조를 결합하여 사용한다.
    - 따라서 별도의 기법이 필요없다.

### 14.5.3 그룹핑

> **그룹핑**은 가용 리스트 방식의 변형이다.
> 
- 첫 번째 가용 블록 내에 n개의 블록 주소를 저장하는 방법이다.
    - 이 중 처음 n-1개는 실제로 비어있는 블록의 주소이다.
    - 마지막 1개는 자신과 마찬가지로 n-1개의 빈 블록을 가지고 있는 가용 블록을 가리킨다.

- 이 방법은 다수 개의 가용 블록 주소들을 쉽게 찾을 수 있다는 장점이 있다.

### 14.5.4 계수

> 일반적으로 디스크 공간의 할당과 반환이 여러 연속된 블록 단위로 이루어진다는 이점을 이용한 것이다.
> 

→ 연속 할당 알고리즘 or 클러스터링을 통해 공간을 할당할 경우 유용하다.

### 14.5.5 공간맵

> Solaris와 다른 운영체제에서 볼 수 있는 Oracle의 **ZFS** 파일 시스템은 대규모의 파일, 디렉터리, 파일 시스템을 저장할 수 있도록 설계되었다.
> 

→ 이러한 규모에서는 메타데이터 입출력이 성능에 지대한 영향을 미친다.

- ZFS는 **가용 공간을 관리할 때 자료구조의 크기를 제어하고 이 자료구조를 관리하기 위해 필요한 입출력을 최소화**하기 위하여 여러 기법을 조합하여 사용한다.
    - 장치의 공간을 관리 가능한 크기의 덩어리로 나누기 위해 **메타슬랩(metalabs)**을 생성한다.
    - 주어진 볼륨은 수백 개의 메타슬랩을 포함할 수 있다.
    - 각 메타 슬은 연관된 공간맵을 가지고 있다.

- ZFS는 가용 블록에 관한 정보를 저장하기 위해 계수 알고리즘을 사용한다.
    - 공간맵은 할당과 반환의 모든 블록 활동을 계수 형식으로 시간 순서로 기록한다.

- ZFS가 메타슬랩으로부터 공간을 할당하거나 반환하려고 할 때, 관련된 공간맵을 변위에 따라 색인된 균형-트리 형태로 메모리에 적재하고, 로그를 재실행하여 이 구조에 반영한다.
    - 그러면 메모리 내 공간맵은 메타슬랩의 할당과 반환 상태를 정확히 표현하게 된다.

- ZFS는 연속된 가용 블록을 결합하여 하나의 항으로 만들어 맵을 가능한 한 압축한다.

- ZFS의 트랜잭션 기반 연산의 일부분은 디스크에 존재하는 가용 공간 리스트가 갱신된다.

- 집합과 정렬 단계 동안 블록 요청은 계속 발생할 수 있고, ZFS는 이 요청을 로그에 충족시킨다.
    - ***ZFS는 본질적으로 로그와 균현 트리가 합쳐져 가용 리스트 역할을 한다.***

### **14.**5.6 사용하지 않는 블록 트림

> 업데이트를 위해 블록을 덮어쓸 수 있는 HDD 및 기타 저장매체는 가용 공간 관리를 위한 가용 리스트만 필요하다.
> 
- 블록을 해제할 때 특별한 조치 없이, 다음 블록에 할당되어 데이터를 덮어쓸 때까지 데이터를 유지한다.

- NVM 플래시 기반 저장장치와 같이 덮어쓰기를 허용하지 않는 저장장치는 동일한 알고리즘을 적용하기 어렵다.

- 파일 시스템이 페이지가 비어있고 페이지를 포함하는 블록이 완전히 비어 있는 경우,
    - → 장치에 알릴 수 있는 기법이 필요하다. … 해당 기법은 저장장치 컨트롤러마다 다르다.

## 14.6 효율과 성능

- 다양한 블록 할당과 디렉터리 관리 기법들은 저장장치의 성능과 효율적인 사용에 어떤 영향을 미치는지 알아보자.

### 14.6.1 효율

> 저장장치 공간의 효율적인 사용은 할당 및 디렉터리 알고리즘과 밀접한 관계가 있다.
> 
- ex) 한 파티션에서 UNIX incode들은 미리 할당된다.
    - 빈 디스크라 해도 그 공간의 일부가 incode에 할당되어 있다.
    - incode를 미리 할당하고 이들을 파티션 전체에 분산 할당함으로써, 파일 시스템 서능을 향상할 수 있다.
    - 이러한 성능 개선을 incode들을 가급적 데이터 블록 부근에 위치하도록 함으로써 디스크의 탐색 시간을 줄인 것에 기인한다.

- 고려할 사항은 파일의 디렉터리(또는 incode) 항목 내에 저장되어야 할 정보의 종류가 있다.
    - 일반적으로 파일과 연관된 모든 데이터 항은 효율과 성능에 대한 영향을 고려해야만 한다.

- 포인터 크기를 선택하거나 일반적으로 운영체제에서 할당 크기를 고정하도록 선택할 때 어려운 점 중의 하나는 변화하는 기술에 대한 대비이다.

### 14.6.2 성능

> 기본적인 파일 시스템 알고리즘이 결정되었다 하더라도 시스템 성능을 향상할 수 있는 방법들이 몇 가지 있다.
> 
- 저장장치 컨트롤러들은 전체 트랙 or 블록의 내용을 전부 저장할 수 있을 만큼 충분한 크기의 캐시를 가지고 있다.

- 어떤 시스템은 메인 메모리에서 별도의 구역을 **버퍼 캐시용**으로 유지한다.
- 다른 시스템은 파일 데이터를 페이지 캐시를 사용하여 캐시한다.
    - **페이지 캐시**는 가상 메모리 기법을 사용하여 파일 데이터를 파일 시스템 지향의 블록이 아닌 페이지로 캐시한다.
    - 파일 데이터를 가상 주소로 캐시 하는 것은 물리 블록으로 캐시하는 것보다 훨씬 효율적이다.

- 다수의 시스템이 페이지 캐싱을 사용하여 프로세스 페이지와 파일 데이터 모두를 캐시한다.
    - 이 기법은 **통합 가상 메모리(unified virtual memory)**라고 한다.

- UNIX의 몇 버전들은 **통합 버퍼 캐시(unified virtual cache)**를 제공한다.
- 파일을 오픈하고 접근하는 두 가지 대안을 생각해 보자.
    - 하나는 메모리 매핑을 사용하는 것이고, 다른 하나는 표준 시스템 콜인 `read()` `write()` 를 사용하는 것이다.
    - 통합 버퍼 캐시가 없다면 다음과 같은 상황을 맞이하게 된다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236998881-2a2a485a-4973-45d7-82b6-42927dca1152.png)
    
    - 가상 메모리 시스템은 버퍼 캐시와 인터페이스 할 수 없기 때문에 버퍼 캐시에 있는 파일이 페이지 캐시에 복사되어야만 한다.
        - → 이련 현상을 **이중 캐싱(double caching)** 라고 한다.

- 이중 캐싱은 메모리 낭비 뿐 아니라, 시스템 메모리 내에서 필요 없는 데이터 이동으로 인해 CPU와 I/O 사이클을 낭비하게 된다.
    - 또한 두 캐시 사이의 비일관성은 파일에 문제를 야기할 수 있다.

- 따라서 통합 버퍼 캐시를 제공함으로써 메모리 매핑과   `read()` `write()` 시스템 콜과 같은 페이지 캐시를 사용하게 된다.
    - 이는 이중 캐시 단점을 극복하며,
    - 가상 메모리 시스템이 파일 시스템 데이터를 관리할 수 있도록 해준다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236998908-ecf953b4-9d0d-4180-ace4-1187c3e4f1f0.png)
    

> *입출력의 성능에 영향을 미칠 수 있는 또 다른 문제는 파일 시스템에 **쓰기 연산이 동기적 혹은 비동기적으로 수행되느냐** 하는 것이다.*
> 
- **동기적 쓰기**는 저장장치 서브시스템이 요청을 받은 순서대로 이루어지며, 쓰기가 버퍼에 저장되지 않는다.
    - 따라서 호출 루틴은 데이터가 디스크 드라이브에 도착한 후에야 수행을 계속할 수 있다.
    - 메타데이터 쓰기 작업에 적합하다.

- **비동기식 쓰기**는 데이터를 캐시에 저장하고 호출자에게 제어를 돌려준다.
    - 대부분의 쓰기는 비동기적으로 이루어진다.

- 다른 시스템의 파일 접근 유형에 따라 다른 교체 알고리즘을 사용함으로써 페이지 캐시를 최적화한다.
    - 순차적으로 읽히고 쓰이는 파일은 LRU 순서에 따라 교체되어서는 안된다.
    - 대신에 순차 접근 free-behind와 read-ahead로 알려진 기술을 사용한다.
        - **Free-behind**는 다음 페이지가 요청되자마자 버퍼에서 페이지를 제거하는 것을 말한다.
        - **Read-ahead**는 요구된 페이지와 몇 개의 뒤이은 페이지를 읽어 캐싱하는 것을 말한다.

> 페이지 캐시, 파일 시스템, 장치 드라이버는 몇 가지 흥미 있는 상호 작용을 한다.
> 
- 적은 양의 데이터가 파일에 쓰일 때,
    - 페이지는 캐시에 버퍼되고,
    - 디스크 드라이버는 자신의 출력 큐를 장치 주소에 따라 소트한다.

→ 이러한 행동은 디스크 드라이버가 디스크 헤드 탐색을 최소화할 수 있게 한다.

- 동기식 쓰기가 필요하지 않다면,
    - 디스크에 쓰기를 하는 프로세스는 단순히 캐시에 쓰고,
    - 시스템은 비동기식으로 디스크에 데이터를 쓸 수 있다.

→ 사용자 프로세스는 매우 빠르게 쓰기 작업을 할 수 있다.

- 디스크 파일로부터 데이터가 읽혀질 때,
    - 블록 입출력 시스템은 약간의 미리 읽기 작업을 수행한다.

→ 대규모 전송의 경우 파일 시스템을 통한 디스크 출력이 직관과 달리 종종 입력보다 더 빠르다.

## 14.7 복구

> 파일들과 디렉터리는 때에 따라서는 메인 메모리와 저장장치 볼륨에 존재하게 된다.
> 

→ 따라서 시스템에 문제가 생길 경우, 두 곳의 내용이 일관성을 가져야 하고 자료를 보장해야 한다.

### 14.7.1 일관성 검사

> ***파일 시스템의 오염의 원인이 무엇이든지 간에 문제를 검출하고 교정할 수 있어야 한다.***
> 
- 검출을 위해서 각 파일 시스템의 모든 메타데이터에 대해 검사한다.
    - → 파일 시스템의 일관성을 확인하거나 부정할 수 있다.
    - 불행히도 이 검사는 시스템이 부트될 때마다 실행되어야 한다. (몇 분 ~ 시간 소요)
    
- 대체 방안으로 파일 시스템은 파일 시스템 메타 데이터 안에 자신의 상태를 기록할 수 있다.
    - 메타데이터를 변경하려고 할 때 → 이 상태 비트는 메타데이터가 변경 중 상태라는 것을 표시하도록 설정되어야 한다.
    - 메타 데이터에 대한 모든 갱신이 성공적으로 완료되면 → 파일 시스템은 이 비트를 소거한다.
        - 만약 상태 비트가 1로 남아 있다면 → 일관성 검사가 수행된다.

- UNIX 시스템의 `fsck`와 같은 **일관성 검사기(consistency checker)**는 디렉터리 구조에 있는 데이터와 다른 메타데이터를 저장장치에 있는 상태와 비교한다.
    - 불일치가 발견되면 → 그것을 복구하도록 시도한다.
    - 이때 할당 및 가용 공간 관리 알고리즘이 이 검사기에 발견할 수 있는 문제의 유형과 성공 회복을 결정한다.

### 14.7.2 로그 구조 파일 시스템

> 컴퓨터 과학 분야에서는 알고리즘과 기술이 종종 원래의 용도로부터 다른 분야로 전이된다.
> 
> 
> ex) DB 로그 기반 복구 알고리즘
> 
- 이들 로깅 알고리즘은 일관성 검사의 문제 성공적으로 적용되었다.
    - 이때 구현은 **로그 기반 트랜잭션 지향(or 저널링)** 파일 시스템으로 알려져 있다.
    

> 앞 절에서 설명한 일관성 검사 기법을 쓴다면, 시스템 구조가 깨질 수 있고, 그 경우 복수 시에 수리할 순 있다.
> 
> - 그러나 여러 문제가 존재한다.
- 문제점 중 한 가지는
    - 비일관성이 회복할 수 없을 수도 있다는 것이다.
    - 구조를 복구하지 못해 파일을 잃게 되거나 전체 디렉터리를 잃을 수 있다.

> ***이 문제의 해결 방안은 파일 시스템 메타데이터 갱신에 로그 기반 복구 기술을 적용하는 것이다.***
> 
- NTFS와 베리타스(Veritas) 파일 시스템 모두 이 기술을 적용하며,
    - Solaris 7과 그 이상 버전에서의 UFS에서는 이 기술을 옵션으로 제공한다.

> 기본적으로 모든 메타데이터 변경은 로그에 순차적으로 기록된다.
> 
- 특정 태스크를 수행하는 연산의 집합 각각을 하나의 트랜잭션이라고 한다.
    - 일단 변경이 로그에 기록되면 그들은 commit된 것으로 간주하고
    - 시스템 콜은 유저 프로세스로 복귀하여 실행을 계속하도록 허용한다.
    - 그동안 이 로그 엔트리는 실제 파일 시스템 구조에 대해 재실행된다.
    - 변경이 반영되는 동안, 어느 동작이 끝났는지 등의 상태를 나타내기 위해 포인터가 갱신된다.
    - commit된 전체 트랜잭션이 완료되면 → 그 사실을 나타내는 항목이 로그에 만들어 진다.
        - 이 로그 파일은 실제로 **원형 버퍼**이다.

- **원형 버퍼(circular buffer)**는 버퍼 공간의 맨 뒤에 기록하고, 맨 앞에서 시작하는 연속적인 공간으로 구성된다.
    - 원형 버퍼는 연속적으로 기록되므로, 이전의 데이터 위에 겹쳐서 기록될 것이다.
    - 아직 저장되지 않은 유효한 데이터 위에 새로운 데이터가 겹쳐 기록되지 않게 관리된다.

- 시스템이 크래시하면, 로그파일에 0개 이상의 트랜잭션이 있을 것이다.
    - 이러한 트랜잭션들은 운영체제에 의해 commit 되었다 할지라도 파일 시스템에서 결코 수행이 완료되지 않은 것이기 때문에 이들은 반드시 수행을 완료해야 한다.
    - 이 트랜잭션들은 작업이 완료될 때까지 실행될 수 있고, 파일 시스템 구조는 일관성을 유지하게 된다.
    - 유일한 문제는, 트랜잭션이 중단(abort)되었을 때 발생한다.
        - 즉, 그 트랜잭션은 시스템이 크래시 하기 전에 commit 되지 않은 것이다.
    - 이 경우에는 원상 복구를 통해 일관성을 유지할 수 있다.

### 14.7.3 다른 해결 방안들

> 일관성 검사 이외의 또 다른 해결방안이 Network Appliance의 WAFL 파일 시스템과 Solaris ZFS 파일 시스템에 채택되었다.
> 
- 이 시스템들은 옛 데이터를 새 데이터로 절대 덮어쓰지 않는다.
    - 오히려 트랜잭션은 모든 데이터와 메타데이터 변경을 새로운 블록에 기록한다.
    - 트랜잭션이 완료되면 이 블록들의 구버전을 가리키고 있는 메타데이터 구조가 새로운 블록들을 가리키도록 갱신된다.
    - 그런 후에 파일 시스템은 구 포인터들과 옛 블록들을 제거하여 재사용할 수 있게 만든다.

- 이전 포인터와 블록이 유지되면 **스냅숏**이 생성된다.
    - 스냅숏은 특정 시점의 파일 시스템 상탱다.
    - 이 해결방안은 포인터 갱신이 자동으로 일어난다면 일관성 검사 필요 없게 된다.

- ZFS는 디스크 일관성에 대해 더 혁신적인 접근 방법을 채택한다.
    - ZFS는 WAFL와 마찬가지로 절대 블록을 덮어스지 않는다.
    - 추가로 ZFS는 메타데이터와 데이터 블록의 검사-총합을 제공한다.

### 14.7.4 백업과 복구

> 저장장치 고장은 데이터를 잃어버리게 할 수 있으므로 자료가 영원히 손실되지 않도록 보장해 줄 필요가 있다.
> 

→ 이 때문에, 시스템 프로그램들은 하나의 보조저장장치의 내용을 보통 자기 테이프나 다른 보조저장장치와 같은 다른 저장장치에 주기적으로 **백업**해야 한다.

- 복사의 양을 최소화하기 위해, 각 파일의 디렉터리 항목 정보를 이용할 수 있다.

- 전형적인 백업 과정은 다음과 같다.
    - 첫 번째 날: 디스크로부터 모든 파일을 백업한다. **(full backup)**
    - 두 번째 날: 1일 이후로 변경된 모든 파일을 백업한다. **(incremental backup)**
    - 세 번째 날: 2일 이후로 변경된 모든 파일을 백업한다.
    - ….
    - N 번째 날: N-1 일 이후로 변경된 모든 파일을 백업하고 1일로 되돌아간다.

- 새로운 사이클은 자신의 백업을 이전 백업 집합에 쓸 수도 있고, 새로운 백업 미디어 집합에 쓸 수도 있다.

- 한 사용자가 파일에 문제가 발생한 지 오래 후에 그 파일에 문제가 발생하였음을 인식할 수 있다.
    - 이럴 경우를 대비해 때때로 매체를 영원히 저장해 둘 필요가 있다.
