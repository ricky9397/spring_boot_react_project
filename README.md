# spring_boot_react_project 2022-08-22

## 계획
- Security 권한체크 -> 사용자페이지, 관리자페이지 분리.   // 완료
- Aop -> 캐쉬처리, 로깅처리, 함수 실행시간 분석 처리 등등.. // 진행중
- Exception -> 핸들러 처리  // 진행중
- common -> 공통 부분 처리  // 진행중
- JWT 토큰방식처리 // 완료
- JPA + MyBatys // 완료
- API문서 스웨거 사용.
- 배치 + 스케줄러 => 휴먼계정, 문자발송 및 관리자페이지 대용량데이터 처리 (쉘스크립트 테스트)
- API -> 맵, 로그인 결제, SMTP, 문자발송 등등 구현
- 스프링클라우드 + 리눅스 + Doker + Jenkese 배포 // 
- MySql


## 서버
- 현재 Oracle VM VirtualBox 사용 
- nginx 설치 확인 명령어 : ps -ef | grep nginx
- 포트 (80포트)개방 : sudo firewall-cmd --zone=public --add-port=80/tcp --permanent
- 개방 후 포트리로드 : sudo firewall-cmd --reload
- 도커 설치 -> 도커 이미지설치 -> 도커콤포즈 -> 젠킨스 2시간주기 
