# SAML / OAuth / OIDC / SSO

## ✅ SSO

> 정의
> 
- SSO(Single SIng-On)은 **1회 사용자 인증** 으로 다수의 애플리케이션 및 웹 사이트에 대한 **사용자 로그인을 허용하는 인증 솔루션** 이다.
- SSO는 한 번의 인증이 완료된 사용자에게는 **반복되는 로그인 없이** 모든 암호 보호 리소스에 액세스하도록 한다.

> SSO가 중요한 이유 ?
> 
1. **암호 보안 강화**
    1. 권장되지 않는 보안 관행이 발생하거나 사용자가 보안 인증 정보를 잊어버리는 경우 등 암호로 인한 번거로움을 방지한다.
    2. 사용자가 여러 웹 사이트에 사용할 수 있는 강력한 비밀번호를 만들도록 권장한다.
2. **생산성 향상**
    1. 사용자 검증 프로세스를 간소화하고, 보호된 리소스에 더 쉽게 액세스할 수 있다.
3. **비용 절감**
    1. 사용자가 수많은 암호를 기억하지 못하더라도 이미 저장되어 있는 암호가 있으므로 잊어버리는 일이 줄어든다.
    2. 암호 재설정 요청을 처리할 때 지원 리소스를 최소화할 수 있다.
4. **보안 개선**
    1. 사용자당 비밀번호 수를 최소화하여 사용자 액세스를 용이하게 한다.
    2. 모든 유형의 데이터에 대한 강력한 액세스 제어를 제공한다.
    3. → 보안 이벤트의 위험을 줄이는 동시에 조직이 데이터 보안 규정을 준수할 수 있다 !

> SSO 작동
> 
- ***SSO는 애플리케이션 또는 서비스와 외부 서비스 공급자(IdP) 간의 신뢰를 설정한다.***
    - 신뢰 설정은 **인증, 검증, 통신** 을 통해 이루어 진다.
    
1. 사용자가 애플리케이션에 로그인하면 앱은 SSO 토큰을 생성하고 인증 요청을 SSO 서비스로 보낸다.
2. 서비스는 사용자가 이전에 시스템에서 **인증되었는지** 확인한다. 
    1. 인증된 경우 
    → 애플리케이션에 인증 확인 응답을 전송하여 사용자에게 액세스 권한을 부여한다.
3. 사용자에게 유효한 보안 인증 정보가 없는 경우 
→ SSO 서비스는 사용자를 중앙 로그인 시스템으로 리디렉션하고 사용자에게 사용자 이름과 암호를 제출하라는 메시지를 표시한다.
4. 사용자가 정보를 제출하면 이 서비스는 **사용자 보안 인증 정보를 확인** 하고 긍정적인 **응답을 애플리케이션으로 보낸다.**
    1. 그렇지 않으면 오류 메시지가 표시되고 보안 인증 정보를 다시 입력한다. 
    2. 로그인 시도가 여러 번 실패하면 서비스에서 사용자가 일정 기간 동안 더 이상 시도하지 못하도록 차단할 수 있다.

> SSO 유형 종류
> 
- SSO은 사용자 보안 인증 정보를 검증하고 인증하는 데 사용하는 표준 및 프로토콜이 있다.
    - **SAML**
    - **OAuth**
    - **OIDC**
    - Kerberos 
    … 둘 이상의 당사자가 네트워크에서 신원을 서로 검증할 수 있는 티켓 기반 인증 시스템을 말한다.
    

## ✅ SAML

> 정의
> 
- SAML은 보안 검증 마크업 언어로, **ID 제공자(IdP)가 사용자를 인증한 다음 SP(서비스 제공자)라고 하는 다른 애플리케이션에 인증 토큰을 전달** 할 수 있는 공개 통합 표준이다.

> SAML을 사용하면 ?
> 
- 자체 인증을 수행하지 않고도 SP가 작업을 수행하고 ID를 전달하여 **내부 및 외부 사용자를 통합** 할 수 있다.
    - 따라서 네트워크에서 SP와 보안 자격 증명을 공유할 수 있다.
    
- 퍼블릭 클라우드와 기타 SAML 지원 시스템 간의 안전한 크로스 도메인 통신을 비롯하여 온프레미스 또는 다른 클라우드에 있는 선택된 수의 기타 ID 관리 시스템을 제공할 수 있다.

- SAML 프로토콜 및 서비스를 지원하는 두 애플리케이션에서 **사용자에게 SSO 환경을 제공**하여 SSO가 하나 이상의 애플리케이션 대신 여러 보안 기능을 수행할 수 있다.

> SAML의 두 가지 주요 보안 기능
> 
- **인증** - 사용자의 신원 확인
- **권한 부여** - 특정 시스템 또는 콘텐츠에 액세스하기 위해 사용자 권한 부여를 앱에 전달한다.

> SAML 제공자 ?
> 

IdP 및 SP의 두 당사자 간에 ID 데이터를 전송한다.

- **Id 제공자 (IdP) -** 인증을 수행하고 사용자의 ID 및 권한 부여 레벨을 SP에 전달한다.
- **SP(서비스 공급자)** - IdP를 전달하고 지정된 사용자에게 요청된 리소스에 액세스할 수 있는 권한을 부여한다.

> 동작 설명
> 
1. SSO 인증 로그인 및 액세스
2. ID 제공자에서 메타데이터를 익스포트하고 임포트한다.
3. ID 시스템은 SSO ID 제공자에 대해 더 자세히 이해하여 ID 시스템에서 메타데이터를 익스포트한다.
4. SSO ID 제공자 팀에 메타데이터를 제공한다.
5. SSO를 테스트 및 사용으로 설정한다.
6. 사용자는 자신의 SSO 자격 증명으로만 로그인하는 것이 좋다.

> OAuth 사용 사례
> 

주로 웹 프라우저 SSO를 사용으로 설정하는 데 사용된다. SSO에 대한 목표는 사용자가 자격 증명을 다시 제출하지 않고도 한 번 인증하고 별도로 보안 시스템에 접근할 수 있도록 하는 것이다.

- **클라우드 및 온프레미스에서 ID를 관리** 함으로써 사용자 프로비저닝 및 관리를 간소화한다.
- 여러 환경에서 사용자, 역할 및 그룹을 반복적으로 변경할 필요성을 줄인다 → ***ID 작업 간소화***
- 적응형 인증을 사용하면 장치, 위치 또는 활동을 기준으로 사용자 액세스가 위험하다고 판단되는 경우 로그인 요구사항을 늘려 위험을 줄일 수 있다.

<br>

## ✅ OAuth

> 정의
> 
- OAuth는 인터넷 사용자들이 비밀번호를 제공하지 않고 **다른 웹사이트 상의 자신들의 정보에 대해 웹 사이트나 애플리케이션의 접근 권한을 부여**할 수 있는 공통적인 수단으로서 사용되는, 접근 위임을 위한 개방형 표준이다.

> OAuth 참여자
> 

OAuth 동작에 관여하는 참여자는 크게 3가지로 구분할 수 있다.

- **Resource Server**
    - Client가 제어하고자 하는 자원을 보유하고 있는 서버이다.
    - Facebook, Google, Twitter 등
- **Resource Owner**
    - 자원의 소유자를 말한다.
    - Client가 제공하는 서비스를 통해 로그인하는 실제 유저가 이에 속한다.
- **Client**
    - Resource Server에 접속해서 정보를 가져오고자 하는 Client(웹 어플리케이션)를 말한다.

> OAuth 동작 과정
> 

예를 들어, github 계정으로 웹 어플리케이션을 로그인한다고 할 때 각 참여자의 동작을 살펴보자.

1. **Client 등록**

![image](https://user-images.githubusercontent.com/87460638/236811623-79bd1c13-7a98-46c3-85c4-d15a5d4fe686.png)

- Client는 Resource Server를 이용하기 위해 자신의 **서비스를 등록하여 사전 승인**을 받아야 한다.
    - Github Setting → Developer settings → OAuth Apps → New OAuth App 에서 등록한다.
    - 등록 절차를 통해 세 가지 정보를 부여 받는다.
    → ***Client ID, Client Secret, Authorized redirect URL***
- 외부 서비스를 통해 인증을 마치면 Client를 명시된 주소로 리다이렉트 시키는데, 이때 **Query String으로 특별한 code가 함께 전달** 된다.
- Client는 해당 code와 Client ID, Client Secret을 Resource Server에 보내, **Access Token을 발급 받는다.**
    - 등록되지 않은 리다이렉트 URL을 사용하는 경우, Resource Server가 인증을 거부한다.

<br>

2. **Resource Owner의 승인**
- Github docs에 따르면 Github 소셜 로그인을 하기 위해선 다음과 같은 주소로 GET 요청과 필요한 파라미터를 보내도록 명시해야 한다.

```java
GET https://github.com/login/oauth/authorize?client_id={client_id}&redirect_uri={redirect_uri}?scope={scope}
```

- **scope** 는 Client가 Resource Server로부터 인가받을 권한의 범위를 말한다.
- 파라미터의 세부 옵션에 대한 내용은 문서를 참고하자.
    
    [GitHub REST API 설명서 - GitHub Docs](https://docs.github.com/ko/rest?apiVersion=2022-11-28)
    

- Resource Owner는 Resource Server에 접속하여 로그인 수행을 한다.
    
    ![image](https://user-images.githubusercontent.com/87460638/236811786-087d8c61-0bc6-4fb7-b838-059946b65b9e.png)
    
    - 로그인이 완료되면 Resource Server는 Query String으로 넘어온 파라미터들을 통해 Client를 검사한다.
        - 파라미터로 전달된 **Client ID와 동일한 ID값이 존재** 하는지 확인
        - 해당 Client ID에 해당하는 **Redirect URL이 파라미터로 전달된 Redirect URL과 같은지** 확인
        
        
- 검증이 마무리 되면 Resource Server는 Resource Owner에게 다음과 부여에 대한 여부를 질문한다.
    - 허용한다면 
    → 최종적으로 Resource Owner가 Resource Server에게 Client의 접근을 승인하게 된다.
    
<br>

3. **Resource Server의 승인**
- Resource Owner의 승인이 마무리 되면 명시된 **Redirect URL로 Client를 리다이렉트 시킨다.**
    - 이때 Resource Server는 Client가 자신의 자원을 사용할 수 있는 Access Token을 발급하기 전에, **임시 암호인 Authorization code를 함께 발급** 한다.
        - **Query String으로 들어온 code가** 바로 **Authorization code** 이다.

- Client는 ID와 비밀키 및 code를 Resource Owner를 거치지 않고 Resource Server에 직접 전달한다.
    - Resource Server는 정보를 검사한 다음, 유효한 요청이면 **Access Token을 발급** 한다.

- Client는 해당 토큰을 서버에 저장해두고, Resource Server의 자원을 사용하기 위한 API 호출시 해당 토큰을 헤더에 담아 보낸다.

<br>

4. Access Token을 헤더에 담아 github **API를 호출** 하면, 해당 계정과 연동된 Resource Server의 자원 및 기능들을 웹 어플리케이션에서 사용할 수 있다.

> **Refresh Token ?**
> 
- Access Token은 만료 기간이 있으며, 만료된 Access Token으로 API를 요청하면 401 에러가 발생한다.
    - → 그렇다고 만료될 때마다 재발급 받기엔 번거롭다 !

- ***그래서 보통 Resource Server는 Access Token을 발급할 때 Refresh Token을 함께 발급한다.***
    - Client는 두 Token을 모두 저장해두고,
    - Resource Server의 API를 호출할 때는 Access Token을 사용한다.
    - Access Token이 만료되어 401 에러가 발생하면, Client는 보관 중이던 Refresh Token을 보내 새로운 Access Token을 발급받게 된다.

<br>

## ✅ OIDC

> 정의
> 
- OIDC(OpenID Connet)는 **추가 인증 프로토콜로 사용하기 위해** OAuth 2.0 권한 부여 프로토콜을 확장한다.

- OIDC에서 ID 토큰이라는 보안 토큰을 사용하여 OAuth 사용 애플리케이션 간에 SSO를 사용하도록 설정할 수 있다.

> OpenID 주체 ?
> 

OpenID는 대표적으로 2가지의 주체가 있다. → IdP, RP

- **IdP (Identity Provider)**
    - Google, Kakao와 같이 OpenID 서비스를 제공하는 당사자이다.
- **RP (Relying Party)**
    - 사용자를 인증하기 위해 IdP에 의존하는 주체이다.

> OIDC vs. OAuth
> 
- OAuth의 주요 목적은 **인가(Authorization)** 이다.
    - Client가 Resource Owner로부터 Resource Server의 자원을 인가 받고, 접근하기 위해 사용된다.
- OpenID의 주요 목적은 **인증(Authentication)** 이다.
    - OpenID를 사용하면 Client는 ID 토큰을 획득할 수 있다.

<aside>
💡 정리하자면, **OAuth**는 Resource Server로부터 Reource를 가져오기 위한 **Access Token 확보에 목적**이 있다면, **OIDC**는 사용자의 **신원 정보가 담긴 ID 토큰 확보에 목적**이 있다.

</aside>

> OIDC 인증 과정
> 

![image](https://user-images.githubusercontent.com/87460638/236811982-c7e7c309-b30d-4b89-b86b-0c60db558d8e.png)

- OIDC는 사실상 OAuth 위에서 동작하는 얇은 ID 계층이다.
    - → OIDC는 사용자 인증을 OAuth 프로세스를 확장하여 구현하는 OAuth 확장판이다.

- OAuth의 scope에 `openid` 값이 포함되어 있으면,
    - Access Token과 함께 사용자 인증에 대한 정보를 ID 토큰 이라고 불리는 **JWT로 반환**한다.

- JWT에는 사용자 식별을 위한 정보가 담겨있다.
