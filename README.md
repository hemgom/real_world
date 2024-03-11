# Real World Progress
## Setting
### Spring Boot
- Project : `Gradle - Groovy`
- Language : `Java - 17`
- version : `3.2.3`
- Package name : `hello.real_world`
- Packaging : `Jar`
- Dependencies : `Spring Web` `Thymeleaf` `Lombok` `JPA` `H2 DB` `Querydsl`  
<br/><br/>

## Work Log
### 24.03.05
commit message : `setting & H2 DB Connecting`
- `h2 DB 연결`, `Member` 테이블 생성(임시)  
<br/>

### 24.03.06
commit message : `add user and confirmation related functions.01`
- 사용자 등록, 조회에 대한 기본적인 `config` `domain` `repository` `service` 생성  
<br/>

### 24.03.07
commit message : `jwt testing result failed`
- `JWT` 를 통한 사용자 인증 추가 1차 시도  
<br/>

### 24.03.08
commit message : `add basic code for user registration function`
- 사용자 등록 코드 수정
	- package `web` -> `controller` rename
	- `Member.java`: `realWorld specs`에 맞춰 수정
	- `MemberSearchCond`: 사용자 조회 검색 조건 변경 `email -> username`
	- `MemberController`
		- 사용자 추가 요청 코드 수정 -> 추가된 사용자 정보(id 제외)를 반환
- `application.yml` 삭제 -> `application.properties`에 H2 DB 연결 설정만 작성
- Spring framework 6.1 이상에서 파라미터 인식 오류 문제 해결 (해결 방법은 `CreateRealWorld.md`에 작성)  
<br/>

### 24.03.09
commit message : `add login system`
- realworld 스펙에 맞춰 코드 수정
	- `Member` 객체 한 번더 `User` 객체로 매핑
- 사용자 로그인 기능 추가
	- 입력된 `email`과 `password` 정보를 통해 일치하는 사용자 정보를 찾아 반환  
<br/>

### 24.03.10
commit message : `add application.yml & Edit RealworldApplication.java`
- application.yml 추가
	- 가독성 좋은 yml 파일로 변경
	- h2 database 설정, jpa 설정 작성
- `RealWorldApplication` 애노테이션 재설정
	- @Import 삭제
   	- @SpringBootApplication 옵션 삭제
   	- 기존 방식처럼 사용하면 Controller 에서 DB Entity 에 접근하는데, 해당 부분을 수정하면서 설정을 변경함
- `Member` 기본 생성자 설정	
	- `NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
	- 기본 생성자의 사용처가 있기에 다른 곳에서 기본 생성자를 접근하지 못하도록 접근제한자를 설정함  
<br/>

### 24.03.11
commit message : `edit repository package & addMember logic`
- repository 패키지 수정
	- 기존 repository 를 `SpringDataJpa`를 사용하도록 변경
		- `MemberRepository` 인터페이스가 `JpaRepository` 인터페이스와 `QueryMemberRepository` 인터페이스르 상속
		- 앞으로 `DB 저장(엔티티 영속화)`은 `SpringDataJpa`를 통해서 함
		- 조회나 다른 기능의 경우 `SpringDataJpa`로는 한계가 있어 `Querydsl`을 사용함
- 사용자 등록 설계 수정
	- 기존 로직에서는 `Controller`가 `Member`에 까지 접근을 할 수 있었음
	- 위와 같은 상태에서 더 이상 `Controller`가 `Member`에 대해 모르도록 코드를 수정
		- 요청을 받아 사용자 가입 정보를 추출하며, `RequestAddMember`를 통해 작업을 수행
		- 추출한 가입 정보를 `Member`의 `addMemberInfo()` 메서드를 통해 엔티티를 등록
		- 사용자 정보가 정상적으로 등록됬다면 가입 정보를 `ResponseMember`에 담아서 반환  
<br/>

### 24.03.12
commit message : `add login system`
- 로그인 시스템 적용
	- 요청 받은 사용자의 정보를 토대로 DB에서 `email`과 `passwor`가 일치하는 사용자를 검색
	- 일치하는 사용자가 있다면 해당 사용자의 정보를 반환, 없다면 `null` 반환
- `QueryMemberRepositoryImpl.checkLoginInfo(RequestLogin request)`: DB에 접근해 요청 정보와 일치하는 사용자 반환, 없다면 null 반환
- `RequestLogin`: 로그인 요청 정보를 전달하는 DTO
	- `LoginInfo`: `RequestLogin` 클래스의 하위 중첩 클래스로 로그인 요청 정보인 `email`과 `password`를 저장 할 수 있음
	- `addLoginInfo(RequestLogin request)`: 로그인 요청 정보를 `LoginInfo` 객체에 저장하는 메서드
	- 중첩 클래스로 설계한 이유는 `realWorld specs`에 맞췄을 때, 사용자 정보를 `user`가 가지고 있기 때문임