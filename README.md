## 문제3. 게시판에 필요한 REST API를 작성하여 코드 및 테스트 가능한 환경을 제출해주십시오.
- 사용자 생성 API 
  > POST /api/v1/signup
- 사용자 인증 API
  > POST /api/v1/auth
- 게시글 작성 API
  > POST /api/v1/board
- 게시글 수정 API (본인 게시글 수정/타인 게시글 수정 불가)
  > PUT /api/v1/board/{id}
- 게시글 삭제 API (본인 게시글 삭제/타인 게시글 삭제 불가)
  > DELETE /api/v1/board/{id}
- 게시글 목록 API
  > GET /api/v1/board
- 게시글 본문 보기 API (조회수 증가, 본인 게시글 조회시 조회수 증가 하지 않음)
  > GET /api/v1/board/{id}