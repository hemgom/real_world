# Endpoints Specs in RealWorld
해당 프로젝트는 [RealWorld_Specs](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints) 의 `Backend Specs` 스펙을 추가 하였다.
## Check Specs
적용 할 스펙과 해당 스펙을 프로젝트에 추가 했는지 확인
- [x] 인증 헤더
  - `Request` 헤더에서 `Authentication` 헤더를 통해 인증 정보를 확인 가능
  - `Autentication`헤더 : `JWT`의 토큰 `타입`과 `AccessToken`을 가짐  
<br/>

- [x] 등록 (회원가입)
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/users`
  - `인증 필요 여부`: No
  - `필수 필드`: `email`, `username`, `password`  
<br/>

- [x] 인증 (로그인)
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/users/login`
  - `인증 필요 여부`: No
  - `필수 필드`: `email`, `password`  
<br/>

- [x] 현재 로그인한 사용자 정보 조회
    - `요청 타입`: `GET`
    - `요청 URL`: `/api/user`
    - `인증 필요 여부`: Yes  
<br/>

- [x] 사용자 정보 수정 (업데이트)
    - `요청 타입`: `PUT`
    - `요청 URL`: `/api/user`
    - `인증 필요 여부`: Yes
    - `접근 가능 정보`: `email`, `username`, `password`, `image`, `bio`  
<br/>

- [x] 프로필 조회
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/profiles/:username`
  - `인증 필요 여부`: Options  
<br/>

- [x] 팔로우 추가
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/profiles/:username/follow`
  - `인증 필요 여부`: Yes
  - `추가 파라미터`: No  
<br/>

- [x] 팔로우 제거
  - `요청 타입`: `DELETE`
  - `요청 URL`: `/api/profiles/:username/follow`
  - `인증 필요 여부`: Yes
  - `추가 파라미터`: No  
<br/>

- [x] 기사 리스트
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/articles`
  - `인증 필요 여부`: Options
  - `최근 기사 우선 반환`
  - `사용 가능 쿼리 파라미터`
    - `?tag=tagName`: `tagName`과 같은 `tag`를 가진 기사 반환
    - `?author=authorName`: 작성자가 `authorName`인 기사 반환
    - `?favorited=username`: `username`을 가진 사용자가 `favorite`으로 등록한 기사 반환
    - `?limit=number`: `number`만큼, 리스트의 기사 개수 제한 `(기본 값 = 20)`
    - `?offset=number`: `number`만큼의 기사를 건너뛰고(스킵) 리스트 생성 `(기본 값 = 0)`  
<br/>

- [ ] 피드 기사
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/articles/feed`
  - `인증 필요 여부`: Options
    - `인증`: 팔로우 사용자가 작성한 기사들을 최신 순으로 반환
    - `미인증`: 작성된 기사들을 최신 순으로 반환
  - `사용 가능 쿼리 파라미터`
    - `?limit=number`: `number`만큼, 리스트의 기사 개수 제한 `(기본 값 = 20)`
    - `?offset=number`: `number`만큼의 기사를 건너뛰고(스킵) 리스트 생성 `(기본 값 = 0)`  
<br/>

- [x] 기사 조회
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/articles/:slug`
  - `인증 필요 여부`: Options    
<br/>

- [x] 기사 등록 (생성)
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/articles`
  - `인증 필요 여부`: Yes
  - `필수 필드`: `title`, `description`, `body`
  - `선택 필드`: `tagList(List<String>)`  
<br/>

- [x] 기사 수정 (업데이트)
  - `요청 타입`: `PUT`
  - `요청 URL`: `/api/articles/:slug`
  - `인증 필요 여부`: Yes
  - `선택 필드`: `title`, `description`, `body`
    - 만약 `title`이 변경되면 `slug`도 변경되어야 함  
<br/>

- [x] 기사 삭제
  - `요청 타입`: `DELETE`
  - `요청 URL`: `/api/articles/:slug`
  - `인증 필요 여부`: Yes    
<br/>

- [x] 기사에 댓글 작성
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/articles/:slug/comments`
  - `인증 필요 여부`: Yes
  - `필수 필드`: `body`  
<br/>

- [x] 기사의 모든 댓글 조회
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/articles/:slug/comments`
  - `인증 필요 여부`: Options  
<br/>

- [x] 기사 댓글 삭제
  - `요청 타입`: `DELETE`
  - `요청 URL`: `/api/articles/:slug/comments/:id`
  - `인증 필요 여부`: Yes  
<br/>

- [x] 기사 즐겨찾기 등록
  - `요청 타입`: `POST`
  - `요청 URL`: `/api/articles/:slug/favorite`
  - `인증 필요 여부`: Yes
  - `추가 파라미터`: No  
<br/>

- [x] 기사 즐겨찾기 해제
  - `요청 타입`: `DELETE`
  - `요청 URL`: `/api/articles/:slug/favorite`
  - `인증 필요 여부`: Yes
  - `추가 파라미터`: No  
<br/>

- [x] 태그 조회
  - `요청 타입`: `GET`
  - `요청 URL`: `/api/tags`
  - `인증 필요 여부`: No