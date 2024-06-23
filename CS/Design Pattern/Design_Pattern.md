### 🔎 Design Pattern 이 뭔가요 ?
> _Design Pattern 은 기존 환경 내에서 **반복적으로 일어나는 문제들을 어떻게 풀어나갈 것인가에 대한 솔루션**이다._

<br>

### 🔎 MVC 패턴이 뭔가요 ?
> MVC 는 **Model, View, Controller** 를 구성요소로 가진다.

![](https://velog.velcdn.com/images/leeseunghee00/post/d7a6316e-848c-4d0e-bdea-05dc466cc121/image.png)

- `Model` 은 데이터의 형태를 정의하고 수정하는 역할을 한다.
- `View` 는 Model 을 UI 로 표현하고, 사용자의 입력을 받아 Contoller 로 전달한다.
- `Controller` 는 입력받은 이벤트를 애플리케이션 내에 어떻게 처리할지 판단하고 가공하고 Model 또는 View 를 조작하는 역할을 한다.

<br>

### 🔎 MVC 패턴에서 발생할 수 있는 문제점
> MVC 패턴은 각 구성요소들끼리 **양방향으로 통신**하기 때문에 연쇄적인 변화가 발행하면 애플리케이션의 동작 흐름을 분석하거나 예측할 수 없는 문제가 발생할 수 있다.

<br>

### 🔎 Singleton Pattern 에 대해 설명해봐
> 싱글톤(Singleton) 패턴은 **오직 한 개의 인스턴스만을 생성**하고, 이를 전역적으로 접근할 수 있게 하는 패턴이다.

- 커넥션 풀, 스레드 풀 등의 경우, 인스턴스를 여러 개 만들게 되면 자원을 낭비하게 되거나 버그를 발생시킬 수 있으므로 오직 하나만 생성하고 그 인스턴스를 사용하도록 하는 것이 이 패턴의 목적이다.
- 이 패턴은 클래스의 인스턴스화를 제한하고, 생성된 인스턴스에 대한 일관된 접근을 제공한다.
- 객체 지향 설계 원칙 중 하나인 **`단일 책임 원칙` 을 만족**하고, 전역적인 상태를 제어할 필요가 있을 때 유용하게 사용될 수 있다.
- 그러나 과도한 사용은 코드의 유연성을 저하시킬 수 있으므로, 상황에 맞춰 적절하게 사용하도록 한다.

<br>

### 🔎 Singleton Pattern 은 언제/어떻게 사용하나 ?
> _전역 상태를 공유하거나 유일한 인스턴스를 요구하는 상황에서 유용하게 사용된다._

#### ✅ 싱글톤 패턴 구현은 다음과 같다.
- 클래스의 생성자를 **`private`** 으로 선언하여 외부에서 인스턴스를 직접 생성하는 것을 막는다.
- 클래스 내부에서 유일한 인스턴스를 생성하고 반환하는 **`static method`** 를 구현한다. → 이 메서드를 통해 싱글톤 인스턴스에 접근할 수 있다.
- 인스턴스가 필요한 시점에서 생성되도록 **지연 초기화 (Lazy Initialzation)** 를 구현한다.
- 멀티 스레드 환경에서 동시에 인스턴스를 생성하는 것을 방지하기 위해 **동기화**를 고려한다.

