# [Final-Project] Synergy-Hub
Spring Framework + React.js SPA 기반의 그룹웨어 개발

<br/>

## 📄 프로젝트 소개
<img src="https://github.com/SynergyHub-groupware/synergyhub-back/assets/157452524/06f7dacb-6e24-49b3-aa06-29a12f2817dd" width="200px" height="150px" title="Alt Text">
<br/>
- **SynergyHub** : 소통과 협업을 강화하다.
- 팀원과 원활한 소통을 기반으로 시너지 효과를 극대화하는 그룹웨어를 제공합니다.

## 📅 개발 기간
- 2024.05.08 ~ 2024.07.16 (2달)

### 🏃 담당자 소개

<table>
  <tr>
    <th>담당자</th>
    <th>담당 기능</th>
  </tr>
  <tr>
    <td>김대한</td>
    <td>인사, 발령</td>
  </tr>
  <tr>
    <td>김정원</td>
    <td>게시글(생성/수정/임시저장), 게시판(생성/수정/권한),<br/>첨부파일(저장/다운로드)</td>
  </tr>
  <tr>
    <td>박은비</td>
    <td>근태/휴가(개인별 기록/관리, 권한별 관리), 메일링,<br/>엑셀 다운로드, SSE 통신 기반 피드</td>
  </tr>
  <tr>
    <td>박진영</td>
    <td>일정, 업무</td>
  </tr>
  <tr>
    <td>이다정</td>
    <td>결재상신, 회수 및 수정, 임시저장, 결재승인&반려,<br/>서명관리, 개인보관함관리, 결재양식관리</td>
  </tr>
  <tr>
    <td>이재현</td>
    <td>사용자 로그인, 쪽지 (발송/조회/임시저장/삭제),<br/>회원 차단, 주소록</td>
  </tr>
</table>

### ✏ 기술 스택 (플랫폼 & 언어)
![js](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![js](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![js](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)

![js](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![js](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![js](https://img.shields.io/badge/CSS-239120?&style=for-the-badge&logo=css3&logoColor=white)
![js](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)

### 🙌 협업 관리
![js](https://img.shields.io/badge/Notion-20232A?style=for-the-badge&logo=Notion&logoColor=#00000)
![js](https://img.shields.io/badge/Discord-20232A?style=for-the-badge&logo=Discord&logoColor=#5865F2)
![js](https://img.shields.io/badge/GitHub-20232A?style=for-the-badge&logo=GitHub&logoColor=#181717)
![js](https://img.shields.io/badge/Figma-20232A?style=for-the-badge&logo=Figma&logoColor=#F24E1E)

<br/>

## 업무 분석 및 모델링

### 업무 흐름도
<img src="https://github.com/JH5256/Rest-API-Study/assets/157452524/67b536ed-017d-412f-ad2e-316c6ada749b" width="300px" height="500px" title="Alt Text"><img>

### ERD Cloud

### DA#5 Modeling

### 이슈 관리
<img src="https://github.com/SynergyHub-groupware/synergyhub-back/assets/157452524/12c6b057-3f46-4886-96c7-a4e1c2b354fa" width="600px" height="400px" title="Alt Text"><img>

<br/>

## 💻 주요 기능

### 로그인
> JWT(JSON Web Tokens)를 이용한 유저 인증, 권한 관리 및 유저 식별
