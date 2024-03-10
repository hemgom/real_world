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
