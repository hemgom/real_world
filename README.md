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

### 24.03.08
commit message : ``
- 사용자 등록, 정보 조회/수정 코드 수정
	- package `web` -> `controller` rename
	- `Member.java`: `realWorld specs`에 맞춰 수정
	- `MemberSearchCond`: 사용자 조회 검색 조건 변경 `email -> username`
	- `MemberController`
		- 사용자 추가 요청 코드 수정 -> 추가된 사용자 정보(id 제외)를 반환
		- 사용자 로그인 코드 추가 -> 
- `application.yml` 삭제 -> `application.properties`에 H2 DB 연결 설정만 작성
- Spring framework 6.1 이상에서 파라미터 인식 오류 문제 해결 (해결 방법은 `CreateRealWorld.md`에 작성)
- `JWT` 를 통한 사용자 인증 추가 2차 시도