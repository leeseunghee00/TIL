### 캐시란 ?
> 캐시는 자주 사용하는 데이터나 값을 미리 복사해 놓는 임시 장소를 말한다.

캐시는 **CPU 안에 들어있는 작고 빠른 메모리**이다. 
- 프로세서가 매번 메인 메모리에 접근해 데이터를 받아오면 시간이 오래 걸리기 때문에 캐시에 자주 사용되는 데이터를 담아두고, 해당 데이터가 필요할 때 프로세서가 메인 메모리 대신 캐시에 접근하도록해 처리 속도를 높인다.
![](https://velog.velcdn.com/images/leeseunghee00/post/a5022266-e990-4503-bf3a-cd248e7c6a87/image.png)

<br>

### 캐시의 지역성에 대해 설명해봐라
⚠️ 모든 데이터를 캐시에 담기에는 저장 공간이 작은 것이 캐시다. 그렇기 때문에 파레토의 법칙에 해당하는 소수의 데이터를 선별해야 하는데, 이때 사용되는 것이 _지역성_이다.

> 지역성은 크게 3가지로 나뉘는데 **시간적, 공간적, 순차적**으로 나눌 수 있다.

- **시간적 지역성**은 특정 데이터가 한번 접근되었을 경우, 가까운 미래에 또 한번 데이터에 접근할 가능성이 높은 것을 말한다.
- **공간적 지역성**은 특성 데이터와 가까운 주소가 순서대로 접근되었을 경우를 말한다.
- **순차 지역성**은 데이터가 순차적으로 액세스되는 경향을 보이며, 프로그램 내의 명령어가 순차적으로 구성된다.
![](https://velog.velcdn.com/images/leeseunghee00/post/7735eea6-81f8-4ad6-8801-8642f7443a68/image.png)
