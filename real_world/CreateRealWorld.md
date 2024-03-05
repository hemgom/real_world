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