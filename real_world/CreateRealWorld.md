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

### @Builder.Default
빌더 패턴을 통해 객체를 생성 시 특정(지정한) 값으로 초기화 할 수 있게 해줌
```java
@Builder.Default
private String follow = "이 문장을 초기화 값으로";
```
위처럼 작성 후 객체를 생성시 `followList = "이 문장을 초기화 값으로"`으로 초기화해 반환 받을 수 있음  
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

### @RestController
`Bean`의 타입을 `RestController`로 설정
- 해당 컨트롤러는 사용자 요청을 받아 `json 값`으로 응답을 주겠다고 명시함
- `@Controller`와 다르게 리턴 값에 자동으로 `@ResponseBdoy`가 붙어 별도로 명시하지 않아도 됨  
<br/>

### @ResponseStatus
`HTTP 상태 코드 지정`에 사용되는 애노테이션
- Spring 에서 기본적으로 요청에 대한 응답이 성공적으로 이루어지면 `HTTP 200 (OK)` 응답을 제공
- 해당 애노테이션을 사용하면 상황에 맞게 필요한 상태 코드를 지정 할 수 있음
- 또한 오류를 알리고 싶다면 `reason` 을 통해 오류 메시지를 제공할 수 있음
  - Spring 은 지정한 메서드가 성공적으로 완료 될 때만 `@ResponseStatus`를 사용하므로 적합하지 않아 보임
  - 오류 메시지로 메시지를 알릴 뿐 따로 예외를 던지지 않음
- 예외를 `HTTP 응답 상태`로 변환하기 위해서는 아래의 방법을 사용하도록 하자
  - `@ExceptionHandler` 사용
  - `@ControllerAdvice` 사용
  - `Exception 클래스` 지정  
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
<br/><br/>

## 의존관계 주입에 관하여...
지금까지는 어떻게든 최소한의 의존관계를 통해 코드를 짜는 것에 `강박(?)`을 가졌다.
- 하지만 중요한 것은 의존관계를 주입하는 `의도`라는 것을 알게 되었다.
- 분명한 목적을 가지고 `클래스` 또는 `인터페이스`를 주입하는 것이 중요하다.
- 의존관계가 추가됨에 따라 그 수가 증가하는 것만 주의할 것이 아니라 주입한 목적 또한 잘 생각해 봐야 한다.  
<br/><br/>

## Converter
속성 상태를 전환하는 기능을 다루며, 해당 프로젝트에서는 엔티티 테이블의 컬럼이 여러개의 값을 가질 수 있도록 사용됨
- `Converter` 적용 시 사용 `annotation`
  - `@Convert`: 기본 필드 또는 속성의 전환을 지정
  - `@Converter`: `Converter`로 사용하겠다고 지정 및 적용 범위 정의
- `FollowConverter` 클래스
  - `AttributeConverter` 인터페이스를 상속
    - 해당 인터페이스는 `엔티티 속성(attribute) 상태`를 `데이터베이스 컬럼 표현`으로 전환하고, 이를 역전환하는 메서드들을 가짐
  - `Member`의 `List<String> followList`를 전환
    - `DB 저장`: `List -> String` 전환하여 저장, `FollowConverter.convertToDataBaseColumn()`
    - `정보 조회`: `String -> List` 전환하여 조회, `FollowConverter.convertToEntityAttribute()`
  - 데이터 전환 메서드 구현에 `Stream`을 사용했으며 초기 `null`값에 대해선 해당 값을 반환하도록 함  
<br/><br/>

## Optional<T> Class
Null 값을 다루기 위해 등장한 클래스
- `T 타입`의 객체를 포장해주는 `래퍼 클래스(Wrapper class)`, 모든 타입의 참조 변수를 저장 가능
- 복잡한 조건문 없이도 해당 클래스에서 제공하는 메서드를 사용하면 간단하게 처리 가능
- 물론 `null` 대신 초기값을 사용하는 것이 좋은 방법  
<br/>

### Optional 객체 생성
1. `of()`
   - 절대 `null` 값을 가지지 않는 데이터라면 해당 메서드로 객체 생성
   - 만약 `null` 값을 저장하려 하면 `NullPointerException`이 발생
```
Optional<String> testData = Optional.of("test");
// testData 가 절대로 null 이 아닐 거란 것도 명시적으로 확인 가능
```
2. `ofNullable()`
   - `null` 값을 가질 수도 있는 데이터라면 해당 메서드로 객체 생성
   - 데이터가 `null`이 아니라면 데이터 값을 가지는 객체를 반환
   - 데이터가 `null`이라면 비어있는 객체를 반환
```
Optional<String> testData = Optional.ofNullable("test");
// testData 가 null 값을 가질 수도 있단 것도 명시적으로 확인 가능
```
3. `empty()`
   - 객체를 `null` 값으로 초기화
```
Optional<String> testData = Optional.empty();

System.out.println(testData.isPresent());   // false 출력
```  
<br/>

### Optional 객체 접근
1. `get()`
    - 단, 객체에 저장된 값이 `null`일 경우 `NullPointerException` 발생
    - 따라서 `isPresent()`를 사용해 객체에 저장된 값이 `null`이 아닌지 확인 후 호출하는게 좋음
      - `null` 값이면 `false`, 아니라면 `true`를 반환함
```
Optional<String> testData = Optional.ofNullable("test");

if (testData.isPresent()) {...}
```
2. `orElse()` & `orElseGet()`
   - `null` 값을 대체할 값을 지정하는 메서드
       - `orElse()`: 파라미터로 값을 받음
       - `orElseGet()`: 파라미터로 함수형 인터페이스를 받음
   - 차이점 : 만약 파라미터로 메서드를 넣는다면?
     - `orElse()`: 메서드의 반환 값을 지정 값으로 사용하기 위해 해당 메서드가 먼저 수행됨
     - `orElseGet()`: 메서드 자체가 넘어가므로 객체가 `null` 값을 가지면 해당 메서드가 수행됨
   - 결론
     - `orElse()`: 파라미터로 값을 사용하고, 그 값이 미리 존재하는 경우 사용
     - `orElseGet()`: 파라미터로 함수를 사용하고, 그 값이 미리 존재하지 않는 경우에 대부분 사용
3. `orElseThrow()`
   - 저장된 값이 존재하면 해당 값을 반환, `null`이라면 지정한 예외를 발생시킴  
<br/><br/>  

## Spring 예외 처리 - 전역적 예외 처리
아래의 두 `annotation`을 통해 `전역적`으로 예외를 처리 가능하다.
### @ExceptionHandler
`Spring`이 제공하고 유연하고 간단하게 예외처리를 할 수 있도록 도와줌
- `@(Rest)Controller` 또는 `@(Rest)ControllerAdvice`에만 적용이 가능함 -> `@Service, @Repository` 적용 불가
  - `@(Rest)Controller`에 적용한 경우: 해당 컨트롤러에만 적용됨
  - `@(Rest)ControllerAdvice`에 적용한 경우: 모든 컨트롤러에 적용됨
- 에러를 처리하고 예외는 `ExceptionHandlerExceptionResolver`에 의해 처리됨
- `HttpServletRequest`, `WebRequest` 등을 얻을 수 있고 `ResponseEntity`, `String` 등 자유롭게 반환타입으로 사용 가능한 장점이 있음
- 하지만 특정 컨트롤러에서만 발생하는 예외만 처리하기에 `에러 처리 코드 중복`, `단일 책임 원칙 위배(SRP)` 등의 문제가 발생 할 수 있음
  - `@(Rest)ControllerAdvice`와 같이 사용해야 하는 이유
- 2개 이상의 예외 클래스를 등록 가능함
  - ex) `ExceptionHandler({ExampleException1.class, ExampleException2.class})`  
<br/>

### @ControllerAdvice & @RestControllerAdvice
`Controller`, `RestController`가 명시된 컨트롤러에서 발생하는 예외들을 `AOP`를 적용, `전역적으로 처리`할 수 있는 `annotation`
- 결국 해당 `annotation`이 붙은 클래스는 예외처리 전용 클래스가 됨
- 여러 `Controller`에 대해 전역적으로 `ExceptionHandler`를 적용해줌
  - `@Component`가 포함되어있어 적용된 클래스가 스프링 빈에 등록되기 때문이다.
  - `@ExceptionHandler`를 해당 클래스의 메서드에 적용하면 단일로 사용할 때의 단점을 해결할 수 있음
    - 모든 `Controller`에 적용되므로 코드 중복이나 단일 책임 원칙 위배 등이 해결
- `직접 정의한 에러 응답`을 일관성 있게 클라이언트에게 할 수 있음
- 별도로 `try-cathc`문을 사용하지 않아 코드 가독성이 높아짐
- 주의! : 
  - 한 프로젝트에 하나의 `ControllerAdvice`만 관리하는 것이 좋음
    - 여러 `ControllerAdvice`가 필요한 경우 `basepackage`, `annotation` 등을 지정해야 함
  - `직접 구현한 Exception 클래스`들은 한 공간에서 관리하도록 함  
<br/><br/>

## Spring Boot Validation
클라이언트의 요청에 대한 `데이터 유효성 검증`을 할 수 있게 제공되는 기능
### 처리과정
1. 클라이언트가 요청에 대한 응답을 받기위해 필요한 데이터를 담아 서버에 요청
2. 서버는 클라이언트의 요청에 담긴 데이터에 대한 `유효성 검증`을 수행
    - 검증 성공 : 요청에 대한 로직 수행
    - 검증 실패 : `MethodArgumentNotValidException` 발생
3. 서버는 요청에 대한 응답 또는 발생한 에러에 대한 에러코드를 클라이언트에게 전달하게 됨  
<br/>

### 관련 annotation
- `@Valid`: `@RequestBody`와 함께 사용되며 전달 받은 데이터에 대한 유효성 검증을 하기 위해 사용
  - `Controller`에서 직접적으로 유효성 검증을 처리
  - 검증 결과는 `BindingResult` 객체 담겨 반환
  - `JSR-303` 표준 스펙, `Java`에서 제공
  - 전달 받은 데이터가 다른 객체를 가지고 있을 경우
    - 포함된 객체도 검증이 필요하다면 해당 객체 클래스에 적용하면 됨
    - 추가로 `@Valid`를 지정해 `"여기도 검증 할게요!"`라 알리는 것과 같다.
- `@Validated`: `@Valid`의 모든 기능 + 유효성 검증 그룹 지정
  - `유효성 검증 그룹 지정`: 객체 필드에서 그룹을 지정해 `Controller` 메서드에서 해당 그룹만 유효성 검증 수행
  - `Bean`으로 등록되어 있는 클래스에 적용해 검증을 수행
  - 여러 검증 그룹을 사용 가능하며, 검증 결과는 `Errors` 객체에 담겨 반환
  - 표준 스펙이 아님, `Spring Framework`에서 제공
- `@NotNull`: `null`을 허용하지 않음
  - 단, `""(빈 문자열)`, `" "(공백 문자)`은 허용됨
- `@NotEmpty`: `null`과 `""`를 허용하지 않음
  - 단, `" "`은 허용됨
- `@NotBlank`: `null`과 `""`, `" "`를 허용하지 않음
- `@Size(min = , max = )`: `최소/최대 값의 범위`를 벗어난 값은 허용하지 않음
- `@Max`: `최대 값`의 범위를 벗어난 값은 허용하지 않음
- `@Min`: `최소 값`의 범위를 벗어난 값은 허용하지 않음
- `@Email`: 올바른 형식의 이메일 주소가 아닌 값은 허용하지 않음
  - 유효한 이메일의 구성에 대한 기준 정립은 `Jakarta Bean Validation providers`가 수행
  - `CharSequence`를 허용
  - `null` 요소에 대해서는 `유효`하다고 간주