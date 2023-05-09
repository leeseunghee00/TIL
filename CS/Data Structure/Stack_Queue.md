## 🌿 스택 & 큐
### 🔎 Stack 란 ?
> 스택(Stack)은 **LIFO(Last-In First-Out)**으로 후입선출 구조를 가진다.

- 만약 스택이 비어있을 때 자료를 꺼내려고 시도하면 _**스택 언더플로우**_가 발생하고 반대로, 스택이 꽉 차 있을 때 자료를 넣으려고 하면 _**스택 오버플로우**_가 발생한다.
- 주로 함수의 콜 스택, 문자열 역순 출력, 연산자 후위표기법 등에 쓰인다.
- **`push()` 로 데이터를 삽입**하고, **`pop()`으로  데이터 최상위 값을 삭제**한다.
![](https://velog.velcdn.com/images/leeseunghee00/post/6abe126a-17df-415c-8cec-04310fd57308/image.png)

### 🔎 Queue 란 ?
> 큐(Queue)는 **FIFO(First-In First-Out)**으로 선입선출 구조를 가진다.

- 주로 프로세스 처리나 CPU 관리에 많이 사용된다.
- 큐는 스택과 다르게 **rear 부분에서 데이터를 삽입**하고, **front 부분에서 데이터를 삭제**한다.
![](https://velog.velcdn.com/images/leeseunghee00/post/325f07fa-a9e0-417d-afaa-ba548f670843/image.png)
