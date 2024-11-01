# SIH

주식투자보조 웹 서비스
https://

# 사용 기술

## 1. Back-end

> java  
> SpringBoot  
> MySql  
> Redis

## 2. Front-end

> React.js

<br />

## ERD

<div markdown="1" style="padding-left: 15px;">
  <img src="https://github.com/user-attachments/assets/e2ee72cb-99e1-49d8-b807-7615942cd6c4" />
</div>


## Structure

<div markdown="1" style="padding-left: 15px;">
<img src="https://github.com/user-attachments/assets/56fef95d-afb1-452b-ae49-a282d4085dab" />
</div>

## 서비스 설명

### 1. 워드 클라우드
> 1. 파이썬에서 주식 관련 최신 뉴스의 제목과 내용을 크롤링.
> 2. 뉴스내용에 대한 형태소분석을 통해 단어와 단어개수를 DB에 저장.
> 3. DB 데이터를 조회하여 프론트에서 워드클라우드로 생성.

### 2. 주식 등락율 사이드바
> 1. 파이썬에서 주식 등락율 데이터를 크롤링.
> 2. 사이드바에 주식 등락율을 보여줌.

### 3. 로그인 / 로그아웃 기능
> Redis를 통해 토큰 기반 인증 구현. Access Token과 Refresh Token을 통해 로그인/로그아웃 구현.

### 4. 게시판 기능
> 게시판 및 첨부파일 기능 구현

<br />

