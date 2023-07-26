# CMS_ADMIN
CMS(content management system) 토이 프로젝트 어드민

## 부트스트랩5 기반 adminkit-3.1.0 템플릿 적용
어드민 화면 UI 구성을 위해 부트스트랩5 기반의 adminkit 템플릿을 사용하여 구성중입니다.

## 사용기술 스택
Spring boot 2.7.9

Gradle

Thymeleaf

Srping Data JPA

QureyDSL

Srping Boot Dev Tools

Lombok

Srping Configuration Processor

H2 Database(개발용)

Srping Data JDBC(추후 사용가능)

### 관리자용 어드민을 최초부터 구성해보면서 여러가지 개인적인 테스트를 위한 토이프로젝트입니다.
기본적은 틀은 현재 다음과 같이 구성되어있고 아직 많이 수정중에 있습니다.
부트스트랩5 기반 템플릿을 사용하였고, 화면의 구성은 다음과 같이 되어있습니다.

최초 접근시 보여지는 로그인 화면
![로그인화면](https://github.com/helloworldkim/CMS_ADMIN/assets/68931285/e80d8d88-52be-42be-85c0-87d67a52043a)

로그인 후 보여지는 메인 화면
![기본 메인 화면](https://github.com/helloworldkim/CMS_ADMIN/assets/68931285/7e526c46-6f30-437e-aa8d-f83badbfbf15)

메뉴를 관리하는 화면
![메뉴관리화면](https://github.com/helloworldkim/CMS_ADMIN/assets/68931285/f15926a3-f32a-4abd-8c5f-9893ca3e3ace)

게시판 형태의 기본 화면
![게시판 기본 화면](https://github.com/helloworldkim/CMS_ADMIN/assets/68931285/a0b5e3ec-8b35-4f8b-95f0-930aa8fde63a)

여러가지 케이스를 생각하여 작성해보는 테스트코드
![테스트코드 리스트 화면](https://github.com/helloworldkim/CMS_ADMIN/assets/68931285/e66446ab-cd26-4774-9b1b-238840d80526)


1. 시스템 관리에 필요한 하위 메뉴들
  - 어드민
  - 어드민 그룹
  - 어드민 그룹별 메뉴관리
  - 메뉴관리(전체메뉴)

2. 기본적인 게시판
   - 공지사항
   - 커뮤니티

다른부분은 생각나는대로 하나씩 추가중에 있습니다.
