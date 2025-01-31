# WasteSorting

## 목차
- [프로젝트 개요](#프로젝트-개요)
- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [사용 기술](#사용-기술)


## 프로젝트 개요
- 프로젝트 제목 : 쓰레기 분리배출 교육 앱
- 프로젝트 수행기간 : 2023.03 ~ 2023.06
- 팀장 및 팀원 : 김용규(팀장), 배상준, 윤태석

## 프로젝트 소개
컴퓨터 비전 기술을 활용해 쓰레기를 인식하고, 분리배출 방법을 안내하는 안드로이드 앱

## 주요 기능
#### 1. 쓰레기 분류 기능
- 쓰레기 분류를 위한 'YOLOv5' 모델 구현 및 학습
  - 'ROBOFLOW'에서 데이터셋을 구축하고
  - 학습한 모델을 TensorFlow Lite 모델로 변환
- 컴퓨터 비전을 활용한 쓰레기 대분류
  - 카메라로 촬영한 쓰레기를 '병', '비닐', '캔', '상자'로 분류
- 사용자와의 질의를 통한 쓰레기 소분류
  - 대분류된 쓰레기를 다시 세부적으로 분류
  - ex) 대분류 결과가 '병'이라면 '유리병'인지 '도자기병'인지 질문
#### 2. 분리배출 및 재활용 교육 기능
- 분류가 끝난 쓰레기의 분리배출 방법과 재활용 과정을 텍스트와 음성으로 제공

## 사용 기술
- 개발 환경
    - Android Studio, Google Colab
- 개발 언어
    - Java, Python
- API & 라이브러리
    - TensorFlow Lite 라이브러리
- 버전 관리
    - Git, GitHub