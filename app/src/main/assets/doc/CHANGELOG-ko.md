******

### 릴리스 기록

******

# v1.0.0

###### 2026/10/12

* `안내` 첫 릴리스는 32비트 전용 네이티브 라이브러리를 포함해 AutoJs4를 그대로 반영하므로 64비트 전용 기기에는 설치할 수 없습니다
* `기능` AutoJs6 파일 관리자의 `AutoJs4로 실행` 메뉴 항목을 통해 내장된 AutoJs4 4.4.0 엔진 (Rhino 1.7.7.2, OpenCV 3.4.3)으로 `.js` 파일, `.auto` 기록 파일, 프로젝트 폴더 실행
* `기능` 버전 관리되는 `autojs4-runtime-api` AIDL 계약을 사용하는 보호된 런타임 서비스: 파일 경로 요청, 콘솔 줄 스트리밍, 시작과 완료 이벤트, 취소, 동시 실행
* `기능` 접근성 서비스, 다른 앱 위에 표시, 저장공간 접근, 배터리 최적화, Root 상태를 보여 주는 플러그인 설정 페이지와 `AutoJs4 콘솔 창 표시` 스위치
* `기능` `armeabi-v7a`, `x86`, `universal` APK 제공, Wake 활성화, 완전한 PluginInfo 메타데이터, 10개 언어의 플러그인 센터 설명, README, changelog
* `의존성` AutoJs4 4.4.0을 commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443)의 Git submodule로 내장
