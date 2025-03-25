# 🚍 F4T1-LOGISTICS
> 물류 관리 및 배송 시스템을 위한 MSA 기반 B2B 프로젝트</br>
> 개발 기간 : 2025.03.11 ~ 2025.03.25

<br/>

### 😏 개발 인원 및 역할

|팀원명|역할|
|:---:|:---:|
|<img src="https://avatars.githubusercontent.com/u/96504592?v=4" width="150px;" alt=""/><br /><sub><b>[김형주](https://github.com/kim0527)</b></sub></a><br />|`배송`/`배송 담당자`/`배송 경로` 서비스 구현, <br/> 주문-배송간 RabbitMQ 비동기 통신 환경 구축, <br/> slack API 메시지 발송 기능 구현
|<img src="https://avatars.githubusercontent.com/u/112970256?v=4" width="150px;" alt=""/><br /><sub><b>[김수빈](https://github.com/Soobinnni)</b></sub></a><br />|서비스 디스커버리 및 Gateway, Zipkin 구축, `인증`/`인가`/`사용자` 서비스 구현<br/> RabbitMQ 비동기 통신, Redis 활용 인증 구현
|<img src="https://avatars.githubusercontent.com/u/110008586?v=4" width="150px;" alt=""/><br /><sub><b>[박보현](https://github.com/unKnownKade)</b></sub></a><br />|`주문`/`상품` 서비스 구현, <br/> 주문 - 상품 및 주문 - 배송 간 RabbitMQ 비동기 통신 환경 구축, Redis 캐싱 환경 구축
|<img src="https://avatars.githubusercontent.com/u/110008586?v=4" width="150px;" alt=""/><br /><sub><b>[차상준](https://github.com/Ssan0613)</b></sub></a><br />|`허브`/`허브 간 이동정보`서비스 구현, <br/> Redis 캐싱 환경 구축, dijkstra 활용 허브 최단거리 구현, Naver API 연동|
|<img src="https://avatars.githubusercontent.com/u/110008586?v=4" width="150px;" alt=""/><br /><sub><b>[최성민](https://github.com/seongmin1117)</b></sub></a><br />|`업체`/`상품`/`배송담당자` 서비스 구현, <br/> Redisson 분산락 구축, KMeans 와 Greedy를 활용한 배송 경로 최적화 알고리즘 구현
<br/><br/>

## ✨ 서버 아키텍처
<img width = "1518" alt = "image" src = "https://github.com/user-attachments/assets/3a8aeaeb-2619-4b52-b0f0-5c8d5f0fbb73" />

<br/><br/>

## ✨️ 서비스 구성 및 실행방법

### 🛡 Api-Gateway
> * 로드밸런싱, 로깅
> * 토큰을 통한 사용자 요청 인증/인가 처리(인증 서비스와 통신)

### 🛡 Auth-Service

> * 로그인
> * JWT Access, Refresh 토큰 발급
> * Refresh 토큰을 통한 Access Token 재발급
> * Jti를 통한 Token Black list 관리(로그아웃 등)
> * 각 마이크로서비스 통신에 필요한 Passport 발급 및 캐싱

### 🛡 User-Service

> * 사용자 등록
> * 상태/프로필 변경
> * 사용자 삭제
> * 조회 및 검색 (필터링, 정렬)

### 🚍 Order-Service

> * 주문 생성
> * 주문 상태수정
> * 조회 및 검색 (필터링, 정렬)
> * RabbitMq 비동기 통신 환경 구축

### 🚍 Company-Service

> * 업체 CRUD 권한 검증
> * 조회 및 검색 (필터링, 정렬)

### 🚍 Product-Service

> * 재고 수정 시 분산 락 구현
> * 조회 및 검색 (필터링, 정렬)

### 🚍 Driver-Service

> * KMeans로 클러스터링 구현
> * Greedy 알고리즘으로 최적 경로 계산
> * 조회 및 검색 (필터링, 정렬)

### 🚍 Delivery-Service

> * 배송 생성, 조회 및 검색
> * 배송 담당자 생성, 순차 배정
> * 배송 경로 생성, 조회 및 검색

### 🚍 Hub-Service

> * 허브, 경로 생성
> * 허브 및 허브 간 이동 정보 조회 및 검색 (필터링, 정렬)
> * 허브, 허브 간 이동경로 캐싱
> * Dijkstra 알고리즘으로 최단경로 계산
> * 허브 담당자 등록, 삭제

### 🚍 Ai-Service

> * Open AI API를 활용한 배송 출발 마감 시간 계산

### 🚍 Gps-Service

> * Naver API를 활용한 허브 위도, 경도 계산
> * Naver API를 활용한 허브 간 소요 시간, 거리 계산

### 📦 Notification-Service

> * Slack API를 활용한 주문 및 배달 메시지 발송

<br/>


<br/><br/>


## ✨️ 트러블 슈팅

#### 🚀 Jackson 직렬화 내부 동작 방식으로 인한 Redis 캐시 데이터 파싱 오류 
#### 🚀 RabbitMQ를 사용한 성능 개선
#### 🚀 Reids를 사용한 성능 개선 ( 기존 1.43s ---> 30 ms )
#### 🚀 서비스 간 feignClient 통신 오류 

<br/><br/>

## ✨ 사용기술 및 개발환경

**Development**

<p>
<img src="https://img.shields.io/badge/JDK 17-E38836?style=flat&logo=openJdk&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot 3.4-6DB33F?style=flat-square&logo=springboot&logoColor=white"/> 
<br />
<img src="https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=hibernate&logoColor=white"/> 
<img src="https://img.shields.io/badge/SpringDataJPA-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/QueryDSL-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
</p>

**Database**

<p>
<img src="https://img.shields.io/badge/MySQL 8-08668E?style=flat&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Redis-FF0000?style=flat&logo=redis&logoColor=white">
</p>

**Server**

<p>
<img src="https://img.shields.io/badge/Eureka-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringGateway-6DB33F?style=flat-square&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat-square&logo=springSecurity&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/-rabbitmq-%23FF6600?style=flat&logo=rabbitmq&logoColor=white"/>
<img src="https://img.shields.io/badge/chatGPT-74aa9c?logo=openai&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack api-4A154B?style=flat-square&logo=slack&logoColor=white"/>
<br />
<img src="https://img.shields.io/badge/Zipkin-FE5F50?style=flat-square&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/DockerCompose-2496ED?style=flat-square&logo=docker&logoColor=white"/>
</p>

<br/><br/>



## ✨ ERD

<img src ="https://github.com/user-attachments/assets/6129150e-ebe1-40db-a646-45f28d5bbd1a"/>
