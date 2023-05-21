## 🌿 Join
### 🔎 Join의 정의와 종류에 대해 간단하게 설명해줘
> _Join 은 **두 개 이상의 테이블이나 데이터베이스를 연결하여 데이터를 검색하는 방법**이다._

두 개 이상의 테이블에서
- `Inner Join` : 교집합만을 추출한다.
- `Left Join` : from에 해당하는 부분을 추출한다.
- `Outer Join` : 모든 테이블에 해당하는 부분을 추출한다.

<br>

### 🔎 Inner Join과 Outer Join 의 정의와 어떻게 사용되는지 설명해봐
![](https://velog.velcdn.com/images/leeseunghee00/post/f8b0b996-6d16-4d9e-90ee-026249585c13/image.png)
> _Inner Join 은 두 개 이상의 테이블 간의 **공통된 값을 기준으로 조인**하는 방식이다._

- 두 테이블에서 일치하는 행만을 결과로 반환한다.
```sql
SELECT 컬럼
  FROM 테이블A
INNER 
  JOIN 테이블B
    ON 조인 조건
```

<br>

> _Outer Join 은 Inner Join과 다르게 **조인 조건을 만족하지 않는 행도 결과로 반환**하는 방식이다._

- 특정 테이블의 모든 행을 결과에 포함시키고, 다른 테이블과 조인할 때 조건을 만족하지 않는 경우에는 NULL 값으로 채워진다.

✅ Outer Join은 세가지 유형으로 나뉜다.
- `Left Outer Join(= Left Join)` :  왼쪽 테이블의 모든 행을 결과에 포함
- `Right Outer Join(= Right Join)` : 오른쪽 테이블의 모든 행을 결과에 포함
- `Full Outer Join(= Full Join)` : 양쪽 테이블의 모든 행을 결과에 포함

```sql
SELECT 컬럼
  FROM 테이블A
LEFT/RIGHT/FULL 
  JOIN 테이블B
    ON 조인 조건
```
