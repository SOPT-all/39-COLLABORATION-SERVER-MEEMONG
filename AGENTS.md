# 미몽 서버

헤어 디자이너와 모델을 연결하는 미몽 앱의 리디자인·클론코딩 프로젝트.
39기 SOPT 합동세미나 모바일 앱 3조의 서버 저장소다.

## 저장소

- Repository: `SOPT-all/39-COLLABORATION-SERVER-MEEMONG`
- 기본 패키지: `org.sopt.meemong`
- 통합 브랜치: `develop`
- 배포 브랜치: `main`

## 사용자 작업 방식

- 프로젝트 파일은 사용자가 직접 수정한다.
- AI는 파일을 읽고 분석하거나 수정할 코드와 명령어를 제안한다.
- 파일 생성·수정·삭제는 사용자가 해당 작업을 명시적으로 허용한 경우에만 수행한다.
- 테스트와 빌드는 요청받은 범위에서 실행할 수 있다. 생성되는 빌드 산출물은 허용한다.
- 사용자의 명시적 요청 없이 커밋, 푸시, PR 생성·수정, 리뷰 게시, 머지를 수행하지 않는다.
- 비밀번호가 포함된 파일이나 환경 변수 값을 출력하지 않는다.

## Tech Stack

- Java 21
- Spring Boot 4.0.8
- Gradle Wrapper
- Spring Web MVC
- Spring Data JPA
- MySQL 8.4
- Docker / Docker Compose

버전과 의존성은 실제 `build.gradle` 및 Gradle Wrapper 설정을 우선한다.
팀 합의 없이 프레임워크 버전을 변경하거나 의존성을 추가하지 않는다.

## Package Structure

```text
org.sopt.meemong
├── MeemongServerApplication
├── domain
│   ├── detail
│   ├── like
│   └── match
│       ├── controller
│       ├── domain
│       ├── dto
│       │   ├── request
│       │   └── response
│       ├── repository
│       └── service
└── global
    ├── config
    └── exception
```

- 도메인별 패키지와 레이어드 아키텍처를 사용한다.
- Controller는 요청·응답 처리, Service는 비즈니스 규칙과 트랜잭션,
  Repository는 DB 접근을 담당한다.
- 엔티티는 각 도메인의 `domain` 패키지에 둔다.
- DTO는 `dto/request`, `dto/response`로 구분한다.
- 공통 코드만 `global`에 둔다.
- 패키지는 실제 코드가 필요할 때 생성한다.
- 기존 구현 패턴과 팀이 합의한 API 명세를 우선한다.

## 구현 범위

현재 합의된 예상 API는 총 6개다.

| 도메인 | 기능 |
|---|---|
| match | 디자이너 카드 목록 조회 — 홈·빠른매칭 미몽 추천 영역에서 재사용 |
| match | 일반 공고 목록 조회 |
| detail | 상세페이지 내용 상세 조회 |
| detail | 상세페이지 디자이너 정보 조회 |
| like | 공고 찜 등록 |
| like | 공고 찜 취소 |

- 실제 endpoint와 요청·응답 형식은 API 명세 확정 후 따른다.
- 더보기, 정렬, 필터, 빈 결과 처리는 기획과 합의한 정책을 확인한다.
- 로그인 및 사용자 식별 방식은 확정된 정책을 확인한다.
- 찜 구현을 위해 임의의 사용자 ID나 인증 방식을 도입하지 않는다.
- 기능 명세에 있는 모든 화면을 서버 구현 대상으로 간주하지 않는다.
- 클라이언트 목업과 서버 연동 범위를 구분한다.

## 이미지 및 외부 링크

- 시연용 이미지는 GitHub Public 저장소의 이슈 첨부 이미지 URL을
  목데이터로 사용한다.
- API 응답에서 이미지 URL을 용도에 맞는 필드로 제공한다.
- 현재 범위에서는 S3와 이미지 업로드 API를 도입하지 않는다.
- 인스타그램 바로가기는 클라이언트와 합의한 경우 URL을 제공한다.
- 실제 개인정보나 비공개 자료를 공개 이미지 저장소에 업로드하지 않는다.

## 주요 파일

| 목적 | 경로 |
|---|---|
| 의존성 및 Java 버전 | `build.gradle` |
| Gradle 프로젝트 이름 | `settings.gradle` |
| 앱 설정 | `src/main/resources/application.yaml` |
| 로컬 MySQL 실행 | `compose.yml` |
| 앱 컨테이너 이미지 | `Dockerfile` |
| 이슈 폼 | `.github/ISSUE_TEMPLATE/` |
| PR 템플릿 | `.github/pull_request_template.md` |
| 로컬 환경 변수 | `.env` — Git 추적 제외 |

PRD와 API 정책은 팀이 공유한 최신 문서를 확인한다.
문서와 코드가 충돌하면 차이를 알리고 구현 기준을 확인한다.

## 로컬 실행 환경

- Docker Compose는 MySQL만 실행한다.
- Spring 앱은 기본적으로 IntelliJ에서 실행한다.
- MySQL의 호스트 포트는 `3307`, 컨테이너 내부 포트는 `3306`이다.
- DB 이름과 앱 계정명은 `meemong`이다.

### Compose 환경 변수

레포 루트의 `.env`에 각자 설정한다.

```dotenv
MYSQL_ROOT_PASSWORD='개인 로컬 관리자 비밀번호'
DB_PASSWORD='개인 로컬 앱 계정 비밀번호'
```

```shell
docker compose up -d
docker compose ps
docker compose logs --tail=30 mysql
```

### Spring 실행 환경 변수

```text
DB_URL=jdbc:mysql://localhost:3307/meemong
DB_USERNAME=meemong
DB_PASSWORD=각자의 앱 계정 비밀번호
```

- `.env`는 Compose가 읽는다.
- IntelliJ 실행 설정과 터미널 환경 변수는 별개다.
- 터미널에서 Gradle 테스트를 실행할 때도 필요한 환경 변수를 설정한다.
- 로컬 DB 비밀번호를 팀원과 공유하지 않는다.
- Spring까지 컨테이너에서 실행할 때는 Docker 네트워크의
  DB 서비스명과 내부 포트를 사용한다.

## DB 규칙

- 현재 `spring.jpa.hibernate.ddl-auto`는 `none`이다.
- 스키마 생성·변경 방식은 팀 합의 후 적용한다.
- 임의로 `create`, `create-drop`, `update`로 변경하지 않는다.
- 기존 볼륨이 있으면 `.env`의 초기화 비밀번호를 바꿔도
  DB 계정 비밀번호가 자동으로 변경되지 않는다.
- 데이터가 삭제되는 볼륨 삭제나 초기화는 명시적 요청 없이 수행하지 않는다.

## 검증

Windows PowerShell에서는 아래 명령을 사용한다.

```powershell
.\gradlew.bat build
```

macOS/Linux에서는 Gradle Wrapper로 실행한다.

```shell
sh gradlew build
```

- 빌드 전에 MySQL과 테스트 프로세스의 DB 환경 변수를 확인한다.
- DB 설정 변경은 연결 및 애플리케이션 기동까지 확인한다.
- Dockerfile 변경은 이미지 빌드 및 앱 컨테이너 기동을 확인한다.
- Compose 변경은 DB 기동과 Spring 연결을 확인한다.
- 테스트하지 못한 항목과 검증 환경을 정확하게 보고한다.
- 테스트를 통과시키기 위해 임의로 테스트를 삭제하거나 비활성화하지 않는다.

## Git Rules

- 이슈 생성 후 해당 이슈 번호로 작업 브랜치를 만든다.
- 작업 브랜치는 `develop`에서 생성한다.
- 브랜치명은 `유형/#이슈번호-작업-내용` 형식이다.
- 작업 내용은 소문자 영어와 하이픈을 사용한다.
- 초기 세팅 브랜치에는 `init/#1-setting`을 사용했다.
- `main`, `develop`에 직접 푸시하거나 Force Push하지 않는다.
- 커밋은 의미 있는 작은 단위로 나눈다.
- 커밋 전 빌드와 관련 실행 결과를 확인한다.

### 커밋 메시지

```text
[유형/#이슈번호] 작업 내용
```

```text
[init/#1] MySQL 연결 설정 추가
[chore/#1] 로컬 MySQL Docker Compose 설정 추가
[feat/#3] 미몽 추천 공고 목록 조회 구현
```

유형은 다음을 사용한다.

- `feat`: 새로운 기능
- `fix`: 버그 수정
- `docs`: 문서
- `refactor`: 리팩토링
- `test`: 테스트
- `comment`: 주석
- `remove`: 파일·코드 삭제
- `rename`: 파일명·경로 변경
- `init`: 프로젝트 초기 세팅
- `chore`: 빌드·환경 설정 및 사소한 수정

## Issue / PR Rules

- 작업 전 대상 저장소와 실제 템플릿을 확인한다.
- 이슈 제목은 `[유형] 작업 내용` 형식으로 작성한다.
- PR 제목은 관련 이슈 제목과 동일하게 작성한다.
- 템플릿의 항목과 순서를 유지한다.
- 사용자에게 본문을 제공할 때는 복사 가능한 Markdown 코드 블록으로 제공한다.
- 완료되지 않은 검증 항목을 완료로 표시하지 않는다.
- PR 본문에 `Closes #이슈번호`로 관련 이슈를 연결한다.
- 서버 팀원 2명의 승인과 필수 CI 통과 후 PR 작성자가 머지한다.
- Squash/Rebase 대신 Merge commit을 사용한다.
- 머지 메시지는 `[MERGE] #이슈번호 -> 대상브랜치` 형식이다.
- 머지 후 팀 채팅방에 공유할 내용을 사용자에게 안내한다.

## 코드 리뷰

`P{n}: 리뷰 내용` 형식을 따른다.

- P1: 꼭 반영 — Request changes
- P2: 적극적으로 고려 — Request changes
- P3: 웬만하면 반영 — Comment
- P4: 선택적으로 반영 — Approve
- P5: 사소한 의견 — Approve

- 실제 변경과 근거를 확인하고 리뷰한다.
- 리뷰 답변은 변경 및 검증 여부에 맞춰 작성한다.
- 반영 전인 내용을 이미 반영했다고 답하지 않는다.
- 사용자의 요청 없이 리뷰 스레드를 해결 처리하지 않는다.

## Coding Rules

- 클래스와 인터페이스는 PascalCase를 사용한다.
- 변수와 메서드는 camelCase를 사용한다.
- 상수와 Enum 값은 UPPER_SNAKE_CASE를 사용한다.
- 패키지명은 소문자를 사용한다.
- 컬렉션 변수명은 복수형으로 작성한다.
- 불필요한 줄임말과 의미가 불명확한 이름을 피한다.
- 동일한 개념에는 동일한 용어를 사용한다.
- 접근 제어자는 필요한 최소 범위로 설정한다.
- 사용하지 않는 import를 제거한다.
- 의존성은 종류별로 빈 줄을 넣어 구분한다.
- 관련 없는 리팩토링이나 전체 포맷 변경을 섞지 않는다.

## 보안 및 협업

- `.env`, 비밀번호, 토큰, 운영 자격 증명은 커밋하지 않는다.
- 로그와 명령 출력에 민감정보를 노출하지 않는다.
- 중요한 결정과 다른 팀원에게 영향을 주는 변경은 사전에 공유한다.
- 회의 결정, 담당자, 마감 일정은 팀 문서에 기록한다.