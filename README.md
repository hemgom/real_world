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
commit message : `add login system (checkLoginInfo)`
- 로그인 시스템 적용
	- 요청 받은 사용자의 정보를 토대로 DB에서 `email`과 `passwor`가 일치하는 사용자를 검색
	- 일치하는 사용자가 있다면 해당 사용자의 정보를 반환, 없다면 `null` 반환
- `QueryMemberRepositoryImpl.checkLoginInfo(RequestLogin request)`: DB에 접근해 요청 정보와 일치하는 사용자 반환, 없다면 null 반환
- `RequestLogin`: 로그인 요청 정보를 전달하는 DTO
	- `LoginInfo`: `RequestLogin` 클래스의 하위 중첩 클래스로 로그인 요청 정보인 `email`과 `password`를 저장 할 수 있음
	- `addLoginInfo(RequestLogin request)`: 로그인 요청 정보를 `LoginInfo` 객체에 저장하는 메서드
	- 중첩 클래스로 설계한 이유는 `realWorld specs`에 맞췄을 때, 사용자 정보를 `user`가 가지고 있기 때문임
- `Controller`는 엔티티를 모르고 있으며 이는 `MemberServiceImpl`이나 `RequestLogin`, `ResponseMember`의 `import`를 통해 확인 할 수 있음  
<br/>

### 24.03.14
commit message : 'add jwt'
- `JWT`: 사용자 인증(토큰 기반 - JWT) 스펙 추가
	- 로그인 성공 시 `JWT(accessToken)` 발급 기능 추가
	- 하지만 현재 JWT 발급만 구현됬을 뿐 토큰을 헤더에 추가해 사용할 수 있도록 추가 작업이 필요함
		- 정확하게는 토큰에 담긴 정보를 토대로 사용자 권한을 구분하는 기능 구현이 미완성  
<br/>

### 24.03.15
commit message : `edit SecurityConfig`
- 인증 정보가 필요한 API 를 설정하고 해당 인증 조건을 만족할 경우 API 요청이 수행되도록 조건 추가
	- `Controller`의 `"/users/test"` 요청 수행 (JWT 를 헤더의 Authentication 에 담아 요청)
		- JWT 일치 : "success" 문자열 출력
		- JWT 불일치 : null 출력  
<br/>

### 24.03.18
commit message : `edit dependencies`
- 의존관계 수정
	- `JwtUtilImpl`은 현재 `저장`과 관련 없이 `JWT 생성` 목적으로 사용되고 있다.
	- 하지만 현재 `MemberRepository` 인터페이스가 `JwtUtil` 인터페이스가를 상속 이를 `MemberServiceImpl`에서 주입받은 상황이다.
	- 그래서 `목적(의도)`에 맞춰 의존관계를 수정하였다.
		- `JwtUtil`을 특정 인터페이스에 상속시키지 않고 바로 `MemberServiceImpl`에 주입
	- 결과적으로 `MemberServiceImpl`가 주입받는 인터페이스와 `목적(의도)`는 아래와 같음
		- `MemberRepository`: `Member` 엔티티를 DB에 저장하고 이를 다루는 메서드들
		- `JwtUtil`: `JWT`를 생성하고 이를 다루는 메서드들, DB에 따로 저장되는 것이 없음  
<br/>

### 24.03.19
commit message : `add memberInfo update system`
- 사용자 정보 수정 기능 추가
	- `Controller - updateMember()`: 입력받은 요청 정보를 `MemberService`에 넘김
		- `RequestUpdateMember request`: `요청 정보(Body)` 데이터를 저장하는 전용 DTO에 담기 위해 사용
		- `String jwt`: `요청 헤더` 정보 중 `Authorization`의 jwt 을 저장하기 위해 사용
		- `Authentication authentication`: 인증 객체에서 `getName()`를 통해 사용자 정보를 찾기 위해 사용
	- `MemberServiceImpl - updateMember()`: `Controller`에서 넘겨 받은 정보 중 요청 정보와 인증 정보를 `MemberRepository`에 넘기고 `jwt`는 응답용 DTO에 저장
		- `MemberRepository`에 요청정보와 인증정보를 넘기고 업데이트된 `Member` 객체를 받아 DB에 저장
		- 반환 받은 `Member` 객체의 `Id` 값을 통해 찾은 사용자 정보를 응답용 DTO의 하위 클래스에 저장
		- `jwt`는 `"Bearer " + accessToken`으로 되어있기 때문에 `"Bearer "` 부분을 공백으로 바꿔 응답용 DTO의 하위 클래스에 저장
		- 응답용 DTO의 하위 클래스에 저장된 정보를 응답용 DTO에 담아 반환
	- `QueryMemberRepositoryImpl - updateMemberInfo()`: 인증정보에 담긴 `사용자 email`을 통해 DB에서 해당하는 사용자 정보를 조회 후 요청정보에 따라 사용자 정보 업데이트
	- `QueryMemberRepositoryImpl - findMemberById()`: `Id` 값을 통해 DB에서 사용자 정보 조회 후 사용자 정보를 응답용 DTO 하위 클래스에 저장해 반환  
<br/>

commit message : `edit method name`
- 현재까지 작성된 메서드 이름 수정 : 최대한 의도한바에 맞춰 메서드를 작성했으나 보편적으로 사용하는 방식이 아니어서 헷갈려 메서드 이름을 수정
	- `RequestAddMember`: `addUserInfo() -> setJoinInfo()`
		- 해당 메서드는 요청정보를 파라미터로 해 요청정보를 `RequestAddMember.JoinInfo` 객체에 저장하는 용도
		- `add`라는 표현 보다는 정보를 세팅하므로 `set`사용, 세팅 정보는 `가입정보`이므로 `User -> Join`으로 바꿔줌
	- `Member`: `addMemberInfo() -> setMemberInfo()`
		- 해당 메서드는 `가입정보`를 `Member` 객체에 저장하는 용도
		- `add`라는 표현보다, 정보를 세팅하기에 `set`을 사용
	- `ResponseMember`: `getJoinInfo() -> setUserInfo()`
		- 해당 메서드는 `가입정보`를 `ResponseMember(응답 DTO)`에 저장하는 용도
		- 값을 가져오는 것이 아닌 세팅이므로 `get -> set`으로 변경, 세팅 정보는 `사용자 정보`이므로 `Join -> User`로 변경
	- `RequestLogin`: `addLoginInfo() -> setLoginInfo()`
		- 해당 메서드는 `요청 정보`를 `RequestLogin.LoginInfo` 객체에 저장하는 용도
		- `add`라는 표현보다, 정보를 세팅하기에 `set`을 사용
	- `RequestUpdateMember`: `addUpdateInfo() -> setUpdateInfo()`
		- 해당 메서드는 `요청 정보`를 `RequestUpdateMember.UpdateInfo` 객체에 저장하는 용도
		- `add`라는 표현보다, 정보를 세팅하기에 `set`을 사용  
<br/>

### 24.03.20
commit message : `add followList in member filed`
- 팔로우한 `username`을 저장할 리스트를 `Member` 필드에 추가
	- `List<String> followList` 추가
	- `@Convert`를 통해 테이블의 한 컬럼 내에서 N개의 값을 가지게 만듬
		- `List -> String`으로 변환해 테이블에 저장 할 수 있게함
- `FollowConverter` 추가
	- DB에 저장시 `List -> String`으로, DB에서 값 조회시 `String -> List`로 전환하여 조회  
<br/>

### 24.03.21
commit message : `add get profile function`
- 사용자 로그인 후 특정 사용자의 프로필 정보 반환 기능 추가
	- `ResponseProfile`: 특정 사용자의 `profile 정보`를 전달하는 DTO
	- `QueryMemberRepositoryImpl`
		- `getProfile()`: 특정 사용자의 `username`을 통해 해당하는 사용자를 검색하고 그 정보를 `ResponseProfile.ProfileInfo`에 저장 후 반환
		- `checkFollow()`: 인증객체를 통해 로그인한 사용자의 `email`을 통해 팔로우 목록을 조회하여 팔로우 상태를 `ProfileInfo`에 저장 후 `ResponseProfile`로 반환
- 현재는 사용자의 팔로우 목록이 초기상태 그대로인 `Null`이기 때문에 팔로우 상태는 `false`만 확인 가능
	- 추후 팔로우 등록/해제 기능을 추가하면서 확인할 예정  
<br/>

### 24.03.22
commit message : `add follow/unfollow function`
- `Member`
	- `List<String> followList` 초기 값 설정 : `new ArrayList<>()`
	- `@Builder.Default`를 통해 엔티티 객체 생성시 해당 값을 초기화되어 저장됨
- 팔로우 등록 과정
	- `getProfile()`: 요청을 통해 얻은 타겟 사용자의 `username`을 통해 해당 사용자가 있는지 확인
	- `addFollow()`: 타겟 사용자가 없다면 `null` 반환, 있다면 로그인한 사용자의 `followList`를 확인하고 타겟 사용자의 `username`을 리스트에 추가
	- `save()`: 서비스에서 `followList`가 수정된 사용자의 정보를 DB에 업데이트
	- `checkFollow()`: 타켓의 `username`과 로그인 사용자의 `followList`를 비교해 있다면 `"true"`, 없다면 `"false"`로 팔로우 상태를 저장 후 `ReponseProfile`에 담아 반환
- 팔로우 해제 과정 : 다른 부분은 동일 `addFollow()` 부분에 아래의 과정 수행
	- `delFollow()`: 타겟 사용자가 없다면 `null` 반환, 있다면 로그인한 사용자의 `followList`를 확인하고 타겟 사용자의 `username`을 리스트에서 제거
- 현재 메서드들을 비교해보면 같은 기능을하는 `중복 구현`이 종종 보임 이러한 부분은 다시 리팩토링 할 필요성이 있음  
<br/>

commit message : `edit method name`
- 메서드 이름이 중복된걸 발견해서 중복된 기능을 하는 메서드가 존재하는 줄 알았음
	- 하지만 의도한 바가 비슷해서 이름이 중복 됬을 뿐 `중복된 기능`이라 하기에는 애매한 부분이 있어 메소드 명을 바꿈
- 변경 메서드 : `QueryMemberRepositoryImpl` 클래스	
	- `checkFollow() -> getFollowState()`: 다른 사용자에 대한 로그인 사용자의 팔로우 상태를 반환
	- `checkFollow() -> checkFollowState()`: 다른 사용자에 대한 팔로우 등록 OR 해제 후, 로그인 사용자의 팔로우 상태를 반환  
<br/>

### 24.03.24
commit message : `edit package location`
- 패키지 위치 변경
	- 추후 스펙 추가시 도메인 추가 예정
	- 도메인 별로 구분해 패키지 루트만으로 구분하기 쉽게 하기 위함
	- 전역으로 사용하는 패키지의 경우 `domain` 패키지와 같은 위치에 위치하도록 함
		- ex) config, security 등
- 패키지 구상도 (예상)
```
project-root
└─ config
└─ domain
│	└─ each entity-naming-package
│		└─ entity class
│		└─ dto, controller, service, repository package
└─ security
└─ error
```  
<br/>

### 24.03.25
commit message : `separate methods, narrow down parameters`
- `method` 분리 및 `파라미터 좁히기`
	- 현재 2개 이상의 기능이 포함된 메서드가 다수 포착됨
		- 메서드의 재사용 등의 이유로 메서드 당 1개의 기능을 가지도록 수정
		- ex) 어떤 정보를 조회하는 메서드라면 조회 정보를 반환하고 끝
	- 메서드가 필요한 정보 외에 불필요한 정보가 파라미터로 넘어가고 있음
		- `변경 사항 전파 문제`를 고려해 메서드가 필요한 정보만 넘어가도록 `파라미터를 좁히기` 작업을 수행
		- why?) 파라미터의 수정이 필요할 경우 관련 파라미터를 사용하는 모든 메서드를 수정해야 함, 이러한 점을 예방하거나 전파 범위를 줄이기 위해서 메서드는 필요한 정보만 파라미터로 넘겨 받는 것이 좋음
	- 결과적으로 기존에 8개의 메서드가 10개로 늘어남
		- 모든 메서드를 분리 하였음에도 `개수에 크게 차이가 나지 않음`
		- 아마 메서드의 재사용으로 인해 `개수가 크게 늘지 않은 것`으로 예상됨
	- 각각의 메서드는 `재사용하기 용이`해졌고 `유지보수 편의성이 높아`졌기 때문에 결과적으로는 더 좋은 상태가 됬다고 생각함  
<br/>

### 24.03.26
commit message : `specify controller response value & global exception handling`
- `@ResponseStatus`를 통한 `HTTP 응답`에 대한 `상태 코드` 설정
- `@(Rest)ControllerAdvice & @ExceptionHandler`를 통한 전역적 예외 처리 추가
	- 모든 `Controller`에서 클라이언트에게 일관성있는 예외 응답을 보내줄 수 있도록 `전역적`으로 예외를 처리하도록 함
	- 추가적으로 `@Valid`를 사용해 요청 값에 대한 검증을 하도록 함
	- `GlobalExceptionHandler`를 통해 처리하는 예외 종류
		1. `@ExceptionHandler`를 통해 지정한 예외(들)
			- ex) `ExceptionHandler(NoSuchElementException.class)`
		2. `1)`에서 지정한 예외들 외에 대부분의 예외들
			- `ExceptionHandler({Exception.class})`
		3. `@Valid`를 통한 유효성 검증에 대한 예외
			- `ResponseEntityExceptionHandler`의 `handleMethodArgumentNotValid()`