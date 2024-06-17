INSERT INTO LINE_SORT VALUES
(1, '일반결재'), (2, '팀장전결'), (3, '부장전결'), (4, '인사관련'), (5, '경영지원'), (6, '채용관련');

INSERT INTO APPROVAL_LINE (LS_CODE, AL_SORT, AL_ORDER, AL_ROLE) VALUES
(1, 'T5', 1, '결재'),
(1, 'T4', 2, '결재'),
(1, 'T2', 3, '결재'),
(1, 'T1', 4, '결재'),
(2, 'T5', 1, '전결'),
(2, 'T4', 2, '결재'),
(2, 'T2', 3, '결재'),
(2, 'T1', 4, '결재'),
(3, 'T5', 1, '결재'),
(3, 'T4', 2, '전결'),
(3, 'T2', 3, '결재'),
(3, 'T1', 4, '결재'),
(4, 'T5', 1, '결재'),
(4, 'D4, T5', 2, '결재'),
(4, 'D4, T4', 3, '결재'),
(4, 'D4, T2', 4, '결재'),
(4, 'T1', 5, '결재'),
(5, 'T5', 1, '결재'),
(5, 'D3, T5', 2, '결재'),
(5, 'D3, T4', 3, '결재'),
(5, 'D3, T2', 4, '결재'),
(5, 'T1', 5, '결재'),
(6, 'T5', 1, '결재'),
(6, 'D5, T5', 2, '결재'),
(6, 'D5, T4', 3, '결재'),
(6, 'D5, T2', 4, '결재'),
(6, 'T1', 5, '결재');

INSERT INTO APPROVAL_PERSONAL VALUES
('AP1', '2022-01-03', '2022-02-28', '010-1111-1111', '허리디스크 수술 및 회복'),
('AP2', '2022-05-09', '2022-05-20', '010-1111-1111', '교통사고'),
('AP3', '2022-10-31', '2023-03-31', '010-1111-1111', '육아휴직'),
('AP4', '2024-01-01', '2024-01-19', '010-1111-1111', '갑상선 수술 및 회복'),
('AP5', '2024-07-01', '2024-08-02', '010-1111-1111', '어머니 간병');

INSERT INTO APPROVAL_ATTENDANCE VALUES
('AATT1', '재택', '2023-12-18 09:00', '2023-12-20 18:00', NULL, '집', '독감', NULL),
('AATT2', '재택', '2024-02-12 09:00', '2024-02-16 18:00', NULL, '집', '코로나', NULL),
('AATT3', '외근', '2024-03-13 09:00', '2024-03-13 18:00', NULL, '(주)엠케이렌탈솔루션', '최근 발생한 데이터 문제로 인한 미팅 및 점검', NULL),
('AATT4', '외근', '2024-03-19 09:00', '2024-03-19 18:00', NULL, '하이온넷(주)', '새로운 데이터 센터 미팅', NULL),
('AATT5', '교육', '2024-06-19 10:00', '2024-06-19 16:50', NULL, '포스코타워 역삼 이벤트홀(3층)', '2024 크리에이티브 디렉터 세미나', NULL),
('AATT6', '교육', '2024-06-20 13:30', '2024-06-20 18:00', NULL, '엘타워 오르체홀(5층)', '클라우드바리스타 커뮤니티 제9차 컨퍼런스', NULL),
('AATT7', '교육', '2024-06-26 13:00', '2024-06-26 18:00', NULL, '서울 영등포구 의사당대로 141 KB국민은행 신관 지하1층 다목적홀', 'KB국민은행 애자일 컨퍼런스 『KB애자일 하자CON』', NULL),
('AATT8', '연장근무', '2024-03-12 18:00', '2024-03-12 20:00', NULL, '회사', '데이터 센터 연락 및 지원', '데이터 문제 발생'),
('AATT9', '출근시각 정정요청', NULL, NULL, '2024-06-12', NULL, NULL, '지하철 연착'),
('AATT10', '연차', '2024-06-13', '2024-06-13', NULL, NULL, NULL, NULL);

INSERT INTO APPROVAL_APPOINT VALUES
('AAPP1', 'APP-2024-05-0001', '2024-02-01', '2024 발령');

INSERT INTO APPOINT_DETAIL (AAPP_CODE, AAPP_BEFORE, AAPP_AFTER, AAPP_TYPE, EMP_CODE) VALUES
('AAPP1', 'T5', 'T4', '진급', '2021032'),
('AAPP1', 'T6', 'T5', '진급', '2022041'),
('AAPP1', 'T6', 'T5', '진급', '2022091'),
('AAPP1', 'T6', 'T5', '진급', '2022073'),
('AAPP1', 'D15', 'D14', '부서이동', '2023062');

INSERT INTO APPROVAL_FORM VALUES
(1, '인사발령', NULL, 1),
(2, '예외근무신청서', '외근, 출장, 교육, 훈련, 재택', 3),
(3, '초과근무신청서', '연장, 휴일근무', 3),
(4, '지각사유서', '출퇴근 시각 정정요청', 2),
(5, '휴가신청서', '연차, 반차, 결혼, 출산, 병가, 공가, 경조사', 2),
(6, '채용요청서', NULL, 4),
(7, '휴직신청서', NULL, 4),
(8, '사직신청서', NULL, 4),
(9, '시말서', NULL, 4),
(10, '교육비신청서', NULL, 5),
(11, '지출결의서', '법인카드 사용 후 증빙과 함께 제출', 5),
(12, '기타', '등록된 양식이 없을 경우, 일회성 결재양식', 1);

INSERT INTO APPROVAL_DOC (AD_TITLE, EMP_CODE, AD_REPORT_DATE, AD_STATUS, AF_CODE, AD_DETAIL) VALUES
('[정보보안팀] 01/03-02/28 휴직신청서_박하늘', '2021048', '2021-12-01', '완료', 7, 'AP1'),
('[정보보안팀] 05/09-05/20 휴직신청서_박하늘', '2021048', '2022-05-09', '완료', 7, 'AP2'),
('[정보보안팀] 10/31-03/31 휴직신청서_박하늘', '2021048', '2022-08-16', '완료', 7, 'AP3'),
('[정보보안팀] 01/01-01/19 휴직신청서_박하늘', '2021048', '2022-11-23', '완료', 7, 'AP4'),
('[정보보안팀] 07/01-08/02 휴직신청서_박하늘', '2021048', '2024-05-13', '진행중', 7, 'AP5'),
('[인사부] 2024 1/4분기 인사발령', '2021032', '2024-01-29', '완료', 1, 'AAPP1'),
('[정보보안팀] 12/18-20 예외근무신청서_박하늘', '2021048', '2023-12-15', '완료', 2, 'AATT1'),
('[정보보안팀] 02/12-16 예외근무신청서_박하늘', '2021048', '2024-02-09', '완료', 2, 'AATT2'),
('[정보보안팀] 03/13 예외근무신청서_박하늘', '2021048', '2024-03-07', '완료', 2, 'AATT3'),
('[정보보안팀] 03/19 예외근무신청서_박하늘', '2021048', '2024-03-07', '완료', 2, 'AATT4'),
('[정보보안팀] 06/19 예외근무신청서_박하늘', '2021048', '2024-06-11', '대기', 2, 'AATT5'),
('[정보보안팀] 06/20 예외근무신청서_박하늘', '2021048', '2024-06-11', '진행중', 2, 'AATT6'),
('[정보보안팀] 06/26 예외근무신청서_박하늘', '2021048', '2024-06-13', '반려', 2, 'AATT7'),
('[정보보안팀] 03/12 초과근무신청서_박하늘', '2021048', '2024-03-12', '대기', 3, 'AATT8'),
('[정보보안팀] 06/12 지각사유서_박하늘', '2021048', '2024-06-12', '대기', 4, 'AATT9'),
('[정보보안팀] 06/13 휴가신청서_박하늘', '2021048', '2024-06-07', '대기', 5, 'AATT10');

INSERT INTO APPROVAL_BOX VALUES
(1, '교육', '2021048');