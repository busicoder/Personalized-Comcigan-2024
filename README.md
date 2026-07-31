# Personalized-Comcigan-2024

> 컴시간 알리미 데이터를 분석하여 실시간 시간표 변경과 개인별 이동수업을 반영하는 Android 시간표 애플리케이션

2024년 부천고등학교 재학 중 개발한 Android 프로젝트입니다.  
기존 시간표 서비스의 한계를 개선하기 위해 외부 시간표 데이터 구조를 분석하고,  
학교 시간표 변경 사항과 개인별 이동수업을 동시에 지원하는 애플리케이션을 제작했습니다.

---

## 📌 Project Overview

기존 시간표 서비스는 다음과 같은 한계가 있었습니다.

- 실시간 시간표 변경만 지원
- 개인별 이동수업 반영 불가능
- 또는 개별 시간표만 제공하고 실시간 변경 대응 불가능

이를 해결하기 위해 컴시간 알리미의 데이터 요청 구조와 응답 데이터를 분석하여,

- 서버 데이터 갱신을 통한 시간표 변경 반영
- 학생별 이동수업 설정
- 개인화된 시간표 제공

기능을 구현했습니다.

---

## 📅 Development Information

| 항목 | 내용 |
| --- | --- |
| 개발 기간 | 2024.03.29 ~ 2024.06.15 (79일) |
| 플랫폼 | Android |
| 개발 언어 | Java |
| 개발 환경 | Android Studio |
| Target SDK | Android 14 (API 34) |
| Minimum SDK | Android 7.0 (API 24) |
| 버전 | 2.4 |

---

# ✨ Features

## 1. 실시간 시간표 동기화

컴시간 알리미 서버의 시간표 데이터를 분석하여 HTTP 통신으로 데이터를 받아옵니다.

지원 기능:

- 학교 시간표 데이터 요청
- JSON 데이터 분석
- 변경된 수업 감지
- 변경 수업 Highlight 표시

데이터 처리 흐름:


컴시간 서버
|
v
HTTP Request
|
v
JSON Response
|
v
TimeJSON Parsing
|
v
시간표 데이터 생성
|
v
UI 출력


---

## 2. 개인별 시간표 생성

학생별 이동수업 정보를 추가하여 기본 학급 시간표를 개인 시간표로 변환합니다.

지원 기능:

- 학년 / 반 설정
- 이동수업 등록
- 이동수업 삭제
- 개인 시간표 자동 생성
- 설정 데이터 저장

처리 과정:


학급 시간표

  +

이동수업 설정

  ↓

개인별 시간표 생성


---

# 🔍 Data Analysis Process

## 컴시간 알리미 데이터 분석

웹 개발자 도구를 활용하여 컴시간 알리미의 데이터 요청 구조를 분석했습니다.

분석 과정:

1. 웹 페이지 동작 분석
2. Ajax 요청 구조 확인
3. 실제 데이터 제공 URL 확인
4. JSON 응답 데이터 구조 분석
5. 앱에서 사용할 수 있는 형태로 변환

---

# 🏗 Architecture

                   Server
                       |
                       |
                HTTP Communication
                       |
                       v

                     HttpAct
                       |
                       |
                JSON Response
                       |
                       v

                    TimeJSON
             (Data Processing Layer)
                       |
                       |
                     vertex
                (Timetable Model)
                       |
          +------------+------------+
          |                         |
          v                         v

      School Table              stInfo
                                  |
                                  |
                          Personal Settings
                                  |
                                  v

                          Personal Table
                                  |
                                  v

                          Fragment UI

---

# 📂 Project Structure


com.bchs.myapplication

├── MainActivity
│
├── HttpAct
│ └── HTTP 통신 및 데이터 요청
│
├── TimeJSON
│ └── JSON 파싱 및 시간표 데이터 생성
│
├── vertex
│ └── 수업 데이터 모델
│
├── stInfo
│ └── 사용자 설정 및 개인 시간표 관리
│
└── Fragment

├── TtableShow
│   └── 시간표 화면

├── settingwithtyping
│   └── 이동수업 관리

├── addingfrag
│   └── 이동수업 추가

└── OnLoding
    └── 데이터 로딩 화면

---

# 🧩 Implementation Details

## TimeJSON

외부 JSON 데이터를 앱 내부 모델로 변환하는 핵심 클래스입니다.

처리 과정:


Raw JSON

↓

자료 분석

↓

vertex 객체 변환

↓

Ttable[학년][반][요일][교시]

↓

시간표 출력


---

## vertex Data Model

하나의 수업 정보를 표현하는 데이터 모델입니다.

저장 정보:

- 과목명
- 교사명
- 학년
- 반
- 변경 여부
- 이동수업 여부

---

## stInfo

사용자의 개인 설정을 관리합니다.

기능:

- 학년 / 반 저장
- 이동수업 목록 관리
- 개인 시간표 생성
- SharedPreferences 기반 데이터 저장

---

# 🛠 Tech Stack

## Development

| 분야 | 기술 |
| --- | --- |
| Language | Java |
| Platform | Android |
| IDE | Android Studio |
| Build | Gradle Kotlin DSL |

---

## Android Library

| Library | Usage |
| --- | --- |
| AndroidX AppCompat | Android 지원 |
| Material Components | UI 구성 |
| ConstraintLayout | Layout |
| Preference | 설정 화면 구현 |
| Gson 2.10.1 | 데이터 직렬화 |
| Espresso | 테스트 |

---

# 💡 Development Experience

## 외부 서비스 분석 경험

컴시간 알리미의 데이터 요청 구조를 직접 분석하면서,

- HTTP 통신 구조
- JSON 데이터 처리
- 웹 서비스 동작 방식

을 학습했습니다.

---

## Android Application 개발 경험

Android Studio를 처음 활용하여:

- Fragment 기반 화면 구성
- 네트워크 비동기 처리
- 데이터 저장 구조 설계
- 사용자 설정 관리

를 직접 구현했습니다.

---

# ⚠️ Status

현재는 아카이브된 프로젝트입니다.

2024년에 개발된 학습 목적 프로젝트이며,
Android 환경 변화 및 외부 서비스 구조 변화로 인해 현재 빌드는 정상 동작하지 않을 수 있습니다.

다만 외부 데이터 분석부터 Android 애플리케이션 구현까지의 전체 개발 과정을 기록하기 위해 소스코드를 보존합니다.

