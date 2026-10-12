AutoJs4 Runtime은 AutoJs6의 동작 기준인 Auto.js 4.1.1 Alpha2 복제본 [AutoJs4](https://github.com/SuperMonster003/AutoJs4)의 엔진을 내장합니다. 설치하면 AutoJs6 파일 관리자의 "AutoJs4로 실행" 항목이 `.js` 파일, `.auto` 기록 파일, 프로젝트 폴더를 이 플러그인 자체 프로세스에서 원본 Rhino 1.7.7.2 엔진으로 실행하고 콘솔 출력을 AutoJs6로 돌려보냅니다. AutoJs4를 따로 설치하지 않고도 한 기기에서 스크립트가 AutoJs4와 AutoJs6에서 다르게 동작하는지 확인할 수 있습니다.

이 릴리스는 AutoJs4 4.4.0 (commit `3fb08269198ebd49a880ab3ca2621383eaa10443`)을 Rhino 1.7.7.2, OpenCV 3.4.3과 함께 내장하고 모든 스크립트를 격리된 `:autojs4_runtime` 프로세스에서 실행합니다. 시작, 콘솔 줄, 완료는 host 계약을 통해 AutoJs6로 반환되며 작업은 AutoJs6 작업 목록에서 중지할 수 있습니다.

### 빠른 시작

```javascript
console.log("engine package: " + context.getPackageName());
console.log("device: " + device.brand + " " + device.model + " (API " + device.sdkInt + ")");
toast("Hello from AutoJs4");
```

AutoJs6 콘솔에 `[AutoJs4]` 접두어가 붙은 줄이 출력되며, 첫 줄에 스크립트를 실행한 플러그인 패키지 이름이 표시되어 AutoJs4 엔진이 실행했음을 확인할 수 있습니다.

### 호환성과 범위

- 엔진: AutoJs4 4.4.0 (Auto.js 4.1.1 Alpha2 복제본), Git commit [`3fb08269198ebd49a880ab3ca2621383eaa10443`](https://github.com/SuperMonster003/AutoJs4/commit/3fb08269198ebd49a880ab3ca2621383eaa10443), Rhino 1.7.7.2, OpenCV 3.4.3. AutoJs4 소스는 Git submodule로 포함되며 수정 없이 컴파일됩니다.
- 플랫폼: 32비트 앱을 지원하는 Android 7.0 (API 24) 이상. 단일 ABI APK는 `armeabi-v7a, x86`를 지원하고 `universal` APK는 둘 모두를 포함합니다. 64비트 전용 기기에는 AutoJs4와 마찬가지로 설치할 수 없습니다.
- 스크립트: `.js` 파일, `.auto` root 기록 파일, `project.json`이 있는 프로젝트 폴더 (`main`과 `features`를 따릅니다). `"ui";` 스크립트는 플러그인 프로세스 안에서 AutoJs4 자체 스크립트 Activity를 엽니다.
- 권한: 저장공간 접근 (레거시 외부 저장공간, `targetSdk 28`), 다른 앱 위에 표시, AutoJs4 접근성 서비스는 AutoJs6와 별도로 플러그인에 부여합니다. Root 기능은 AutoJs4처럼 기기의 `su`를 사용합니다.
- 동시 실행: 최대 16개의 AutoJs4 스크립트를 동시에 실행합니다. AutoJs6에서 작업을 중지하면 AutoJs4 엔진이 중지되고, 플러그인 프로세스가 비정상 종료되면 실패한 작업으로 보고됩니다.

ABI 선택, 권한, 정확한 계약, 이 버전의 범위는 [프로젝트 README](https://github.com/SuperMonster003/AutoJs6-Plugin-AutoJs4-Runtime)를 참조하세요.
