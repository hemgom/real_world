# 작업 내용
## 도메인
도메인(domain) = 시스템이 구현해야하는 `핵심 비즈니스 업무 영역`
- 향후 다른 기술을 사용해도 `domain`은 그대로 유지할 수 있도록 해야 함
- 즉, 기술은 `domain`을 의존하고 `domain`은 기술에 의존하지 않도록 설계해야 함  
<br/><br/>

## lombok
### @Data
`종합 선물 세트(@Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor)`
- `POJO(Plain Old Java Objects)` & `bean` 과 관련된 모든 `재사용 가능 코드(boilerplate)`를 생성
- ex) `getter` `setter` `toString` `equals`  
<br/>

### @RequiredArgsConstructor
`생성자 주입`을 임의의 코드 없이 자동으로 설정해주는 어노테이션
- 초기화 되지 않은 `final 필드`나 `@NonNull`이 붙은 필드에 대해 생성자를 생성해 줌
- 새로운 필드 추가 시, 다시 생성자를 만들어서 관리하는 번거로움을 없애 줌  
<br/>

### @NoArgsConstructor
파라미터가 없는 `기본 생성자`를 생성해줌
- 클래스에 명시적으로 선언된 생성자가 없어도 인스턴스를 생성할 수 있음  
<br/>

### @AllArgsConstructor
모든 필드 값을 파라미터로 받는 생성자를 생성해줌
- `클래스의 모든 필드`를 한 번에 초기화 할 수 있음  
<br/>

### @Builder
자동으로 `Builder Pattern`에 맞게 `builder 클래스`를 생성해줌
- `Builder Pattern`: 객체 생성을 위한 방법 중 하나
  - 객체 생성시 여러 필드가 존재할 경우 나타나는 여러 문제들을 해결하기 위해 등장  
<br/>

### @Component
해당 애노테이션을 사용한 클래스가 스프링에서 객체로 만들어 관리하는 대상임을 명시
- 해당 애노테이션이 존재하는 클래스들을 빈으로 관리  
<br/>

### @ToString
`toString()`을 자동으로 생성해줌
- 설정 값으로 `exclude`를 사용하면 설정한 필드를 `toString()` 결과에서 제외시킴
  - ex) `@ToString(exclude = "필드명")`  
<br/><br/>

## annotation
### @NotEmpty
`null`과 `""(빈 문자열)`를  둘 다 허용하지 않음
- 단, `" "(공백 문자열)`은 허용
- `Bean Validation`에서 제공하는 `표준 Validation`  
<br/>

### @Entity
`JPA`가 사용하는 객체라는 `인식표`, 해당 애노테이션이 있어야 `JPA`가 인식 가능함
- 해당 애노테이션이 붙은 객체를 `JPA`에선 엔티티라 함  
<br/>

### @Id
테이블의 `PK(Primary Key = 기본 키)`와 해당 `필드`를 매핑함  
<br/>

### @GeneratedValue
`기본 키(Primary Key)`를 자동으로 생성해줌
- 속성 값으로 `strategy`가 있는데 이를 통해 `자동 생성 전략`을 지정할 수 있음
  - `IDENTITY`: 기본 키 생성을 `데이터베이스에 위임`하는 전략
  - `SEQUENCE`: `데이터베이스 시퀀스`를 사용해 기본 키를 생성
  - `TABLE`: 시퀀스 대신 `테이블`을 사용할 뿐 `SEQUENCE`전략과 동작 방식이 같음  
<br/>

### @Service
서비스 레이어 지정 어노테이션
- 비즈니스 로직을 수행하는 서비스 레이어 클래스임을 나타냄
- 캡슐화 없이 모델 내에서 단독 인터페이스로서 제공되는 동작을 의미  
<br/>

### @Configuration
설정 파일을 생성 or Bean 을 등록하기 위한 애노테이션
- 가시적으로 설정파일 인지 또는 Bean 등록을 할지 알 수 있음  
<br/>

### @RestController-----------------------
`Bean`의 타입을 `RestController`로 설정
- 해당 컨트롤러는 사용자 요청을 받아 `json 값`으로 응답을 주겠다고 명시함
- `@Controller`와 다르게 리턴 값에 자동으로 `@ResponseBdoy`가 붙어 별도로 명시하지 않아도 됨  
<br/><br/>

## JPA
### jpa.hibernate.ddl-auto
`JPA`의 `테이블 생성 전략`을 의미함, `Entity`클래스를 대상으로 DDL 쿼리 실행 여부 및 실행 쿼리를 선택 가능
- `none`: 테이블 생성 전략을 사용하지 않음 (보통의 운영 환경에서 권장되는 옵션)
- `create`: 기본 테이블 제거 후 테이블 재생성
- `creat-drop`: 에플리케이션 졸료 시점에 기본 테이블 제거 후 테이블 재생성
- `update`: 기존 테이블 구조 유지, 신규 추가 컬럼(멤버)만 테이블에 반영
  - 기존 컬럼은 속성이 변경되도 테이블레 반영되지 않음
- `validate`: DDL 쿼리를 실행하지 않음, 온전하게 엔티티와 테이블이 저장 매핑 되었는지 확인
  - `none`과 함께 운영 환경에서 권장되는 옵션  
<br/><br/>

## Querydsl
### Spring Boot 버전별 gradle 설정
1. Spring Boot 3.0 이하
```
dependencies {

  implementation 'com.querydsl:querydsl-jpa'
  annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa"
  
  annotationProcessor "jakarta.annotation:jakarta.annotation-api"
  annotationProcessor "jakarta.persistence:jakarta.persistence-api"

}
```
2. Spring Boot 3.0 이상
```
dependencies {

  implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
  annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
  
  annotationProcessor "jakarta.annotation:jakarta.annotation-api"
  annotationProcessor "jakarta.persistence:jakarta.persistence-api"

}
```  
<br/><br/>

## Spring Framework 6.1 이상 파라미터 인식 오류
스프링 프레임 워크 6.X - 파라미터 이름 인식 설정
- 참고 : [Spring Framework 6.2](https://github.com/spring-projects/spring-framework/wiki/Upgrading-to-Spring-Framework-6.x#parameter-name-retention)
- 원인 : `LocalVariableTableParameterNameDiscoverer` 6.1에서 제거
- 해결
  - `gradle`에 아래의 코드 추가 (Groovy DSL)
  ```
  trasks.withType(JavaCompile).configureEach {
      options.compilerArgs.add("-parameters")
  }
  ```
  - `IDE` 수동 구성
    - `Settings` → `Build, Execution, Deployment` → `Compiler` → `Java Compiler` → `Additional command line parameters`에  `-parameters`를 추가
  - `주의!`: 이미 실행되어 `out` 패키지가 생성된 프로젝트라면 해당 패키지 삭제하고  `IntelliJ` 재실행 한 후, 프로젝트를 실행하면 위의 설정이 정상 적용 됨  
<br/><br/>

## Map 보다 DTO 클래스를 사용해야 하는 이유
많은 개발자들이 `"Map을 사용하면 너무 많은 단점들을 가지기에 DTO 클래스를 사용해야 한다."`라고 말한다.
- Map 사용 시 단점
1. 컴파일 에러를 유발할 수 없음
   - 흔히 `개발자에게 최고의 에러는 컴파일 에러`라고 말한다.
   - 컴파일 단계에서 빠르게 에러를 인지할 수 있기 때문이다.
   - 그렇기에 컴파일 에러 발생이 없다는 것은 유지보수에 어려움을 가져온다.
2. `String` 타입의 `key`를 사용해 문제 발생 여지가 다분함
   - `key` 값에 오타가 있더라도 오류 발생이 없어 불필요한 시간낭비를 초래할 수 있음
3. 떨어지는 가독성
   - `Map<String, Object>`를 보면 어떤 `key` 값을 받고 어떤 `value` 값을 받는지 파악하기 쉽지 않다.
   - 만약 `Map` 안에 또 다른 `Map`이 있다면 이러한 불편함은 더 심각해짐
   - 또한 `Map`이 파라미터로 넘어가는 경우도 가독성 문제가 심각해짐
   - 이는 코드를 이해함에 있어 `불필요한 코드 리딩 시간`을 늘리게 됨
4. 타입 캐스트 비용 발생
   - 꺼내는 데이터가 많을 수록 비용이 증가하고 이는 불필요한 비용을 늘리는 꼴
5. 불변성을 확보 불가능
   - 자칫 불변성을 유지해야하는 데이터가 실수로 사라지거나 변경되는 불상사가 발생할 수 있음  
<br/><br/>

## Authentication(인증)과 Authorization(권한부여)
- `Authentication`: 로그인 처럼 사용자 또는 프로세스의 신원을 확인하는 프로세스
- `Authorization`: 누가 무엇을 할 수 있는지 결정하는 규칙  
<br/><br/>

## Spring Boot Security + JWT 동작 원리
1. `클라이언트 -> 서버`: ID/PW 정보로 로그인 요청
2. `클라이언트 <- 서버`: 로그인 정보 검증 후 `AccessToken + RefreshToken`을 발급
3. `클라이언트 -> 서버`: 요청 헤더에 발급 받은 `AccessToken`을 포함해 API 요청
   - `AccessToken` 검증 통과 시
     1. `클라이언트 <- 서버`: 요청된 API 수행 후 결과 반환
   - `AccessToken` 만료 시
     1. `클라이언트 <- 서버`: `AccessToken` 만료 응답
     2. `클라이언트 -> 서버`: `AccessToken + RefreshToken` 재발급 요청
     3. `클라이언트 <- 서버`: 토큰 검증 후 `AccessToken + RefreshToken` 재발급  
<br/>

### Token
- `AccessToken`: 인증된 사용자가 특정 리로스에 접근할 때 사용되는 토큰
  - 클라이언트는 해당 토큰을 사용해 `사용자 신원 확인`, `서비스(or 리소스)에 접근`
  - 유효 기간이 지나면 만료 됨 (expired)
  - 토큰 만료 시, 새로운 `AccessToken` 발급을 위해 `RefreshToken`을 사용 함
- `RefreshToken`: `AccessToken` 갱신을 위해 사용된 토큰
  - 일반적으로 `AccessToken` 발급시 함께 발급 됨
  - `AccessToken` 만료 시 새로운 토큰을 발급 받는데 사용 됨
  - 사용자의 `지속적인 인증 상태`를 유지할 수 있게 도와줌 (=매번 로그인을 다시 하지 않아도 됨)
  - 보안 상의 이유로 `AccessToken`보다 긴 유효 기간을 가짐  
<br/>

### 왜 두 개의 토큰을 사용할까?
- `AccessToken` 단일 사용 시 (문제 발생 예상)
  - `AccessToken`는 사용자의 검증을 위해 사용자의 정보를 담고 있음
  - 그렇기에 `토큰 탈취`에 주의해야 하는데 해당 토큰에 대한 정보를 지키기 위해 유효시간을 짧게 가져가야 함
  - 하지만 유효 시간을 짧게 가져갈 수록 이용자는 반복해 로그인을 재시도 해야하는 상황 발생
- `AccessToken + RefreshToken` 사용 시
  - `AccessToken`의 유효 시간을 짧게 가져가며 유효 만료 시 `RefreshToken`을 통해 갱신
  - 그렇기에 이용자가 서비스 이용 중 `AccessToken` 만료로 로그아웃 되는 상황 방지 됨
  - 설령 `RefreshToken`이 탈취 당해도 해당 토큰에는 사용자와 관련된 정보가 없어 상대적으로 안전함