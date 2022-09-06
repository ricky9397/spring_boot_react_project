# spring_boot_react_project 2022-08-22

## 계획
- Security 권한체크 -> 사용자페이지, 관리자페이지 분리.
- Aop -> 캐쉬처리, 로깅처리, 함수 실행시간 분석 처리 등등..
- Exception -> 핸들러 처리
- common -> 공통 부분 처리
- JWT 토큰방식처리
- JPA + MyBatys 
- API문서 스웨거 사용.
- 배치 + 스케줄러 => 휴먼계정, 문자발송 및 관리자페이지 대용량데이터 처리 (쉘스크립트 테스트)
- API -> 맵, 로그인 결제, SMTP, 문자발송 등등 구현
- 스프링클라우드 + 리눅스 + Doker + Jenkese 배포
- MySql



# Security
 1. 사용자가 로그인을 하였을 때 LoginFilter를 만들어 UsernamePasswordAuthenticationFilter 로그인 성공여부 체크를 한다.
 2. 
