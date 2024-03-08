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

### @RestController
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