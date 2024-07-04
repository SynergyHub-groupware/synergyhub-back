UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2020011 WHERE EMP_CODE = 2020011;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2020021 WHERE EMP_CODE = 2020021;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2021032 WHERE EMP_CODE = 2021032;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2021068 WHERE EMP_CODE = 2021068;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2021091 WHERE EMP_CODE = 2021091;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2024051 WHERE EMP_CODE = 2024051;
UPDATE EMPLOYEE_INFO SET EMP_SIGN = 2024062 WHERE EMP_CODE = 2024062;

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

INSERT INTO APPROVAL_FORM VALUES
(1, '인사발령', NULL, 1, NULL),
(2, '예외근무신청서', '외근, 출장, 교육, 훈련, 재택', 3, NULL),
(3, '초과근무신청서', '연장, 휴일근무', 3, NULL),
(4, '지각사유서', '출퇴근 시각 정정요청', 2, NULL),
(5, '휴가신청서', '연차, 반차, 결혼, 출산, 병가, 공가, 경조사', 2, NULL),
(6, '채용요청서', NULL, 6, '<figure class="table"><table><tbody><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용사유 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용인원 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용형태 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용직급 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp;</p><p>&nbsp;</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;경력사항 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;모집부문 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;담당업무 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;자격요건 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;우대사항 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;근로조건 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;전형절차 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan="3"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr></tbody></table></figure>'),
(7, '휴직신청서', NULL, 4, NULL),
(8, '사직신청서', NULL, 4, NULL),
(9, '시말서', NULL, 4, NULL),
(10, '교육비신청서', NULL, 5, '<p>※ 수료증, 자격증 등과 같은 교육의 결과를 보여주는 서류 첨부 필수</p><p>※ 교육비 납입 영수증 첨부 필수</p><p>&nbsp;</p><figure class=\"table\"><table><tbody><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 교육명 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=\"3\"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 시작일 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 종료일 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>&nbsp;</p></td></tr><tr><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;교육목적</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; (내용) &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td><td colspan=\"3\"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;교육결과 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=\"3\"><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;납부교육비 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=\"3\"><p>&nbsp;</p><p>&nbsp;</p></td></tr></tbody></table></figure>'),
(11, '지출결의서', '법인카드 사용 후 증빙과 함께 제출', 5, '<figure class="table"><table><tbody><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 사용내역 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;수량 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 단가 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 금액 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td colspan="3">합계</td><td>&nbsp;</td></tr></tbody></table></figure><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>'),
(12, '기타', '등록된 양식이 없을 경우, 일회성 결재양식', 1, NULL);

INSERT INTO APPROVAL_BOX VALUES
(1, '임시저장', 0);

INSERT INTO APPROVAL_DOC VALUES
('AD1', '[채용팀] 01/01-03/31 휴직신청서_이복구', 2024062, '2023-11-15', '완료', 7, 'AP1'),
('AD2', '[채용팀] 4/16 지각사유서_이복구', 2024062, '2024-04-16', '완료', 4, 'AATT1'),
('AD3', '[채용팀] 05/22-24 예외근무신청서_이복구', 2024062, '2024-05-21', '완료', 2, 'AATT2'),
('AD4', '[채용팀] 06/03-07 휴가신청서_이복구', 2024062, '2024-05-14', '완료', 5, 'AATT3'),
('AD5', '[채용팀] 06/08 초과근무신청서_이복구', 2024062, '2024-06-07', '완료', 3, 'AATT4'),
('AD6', '[채용팀] 06/18 지각사유서_이복구', 2024062, '2024-06-18', '완료', 4, 'AATT5'),
('AD7', '[채용팀] 07/04 지각사유서_이복구', 2024062, '2024-07-04', '반려', 4, 'AATT6'),
('AD8', '[채용팀] 07/16 교육비신청서_이복구', 2024062, '2024-07-04', '임시저장', 10, 'AE1'),
('AD9', '[채용팀] 7/4 채용요청서_이복구', 2024062, '2024-07-04', '완료', 6, 'AE2'),
('AD10', '[채용팀] 07/10-12 휴가신청서_이복구', 2024062, '2024-07-04', '완료', 5, 'AATT7');


INSERT INTO APPROVAL_PERSONAL VALUES
('AP1','2024-01-01','2024-03-31','010-1234-5678','허리디스크 수술 및 회복');


INSERT INTO APPROVAL_ETC VALUES
('AE1',"<p>※ 수료증, 자격증 등과 같은 교육의 결과를 보여주는 서류 첨부 필수</p><p>※ 교육비 납입 영수증 첨부 필수</p><p>&nbsp;</p><figure class=""table""><table><tbody><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 교육명 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>React.js와 Springboot를 활용한 자바 풀스택 개발자</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 시작일 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>2024-01-23</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 종료일 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>2024-07-16</p></td></tr><tr><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;교육목적</p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; (내용) &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p></td><td colspan=""3""><p>응용 SW 개발을 위한 자바프로그래밍기초</p><p>데이터베이스를 활용한 영속성 프레임워크 기반 프로그래밍</p><p>웹 표준을 적용한 Web Front 개발</p><p>스프링 프레임워크 기반 웹 프로그래밍</p><p>스프링 부트와 마이바티스를 활용한 웹 애플리케이션 개발</p><p>스프링 부트와 JPA를 활용한 웹 애플리케이션 개발</p><p>스프링 부트를 활용한 RestAPI 개발</p><p>React.js를 활용한 SPA 애플리케이션 개발</p><p>도커를 활용한 클라우드 배포</p><p>스프링 프레임워크와 마이바티스를 활용한 MVC Model2 기반 웹 애플리케이션 개발</p><p>스프링 부트와 JPA를 활용한 RestAPI 서버 개발 및 도커를 활용한 클라우드 배포</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;교육결과 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>디지털 기술을 기반으로 다양한 기기의 융합과 콘텐츠의 융합을 통해 클라우드를 활용한 모바일 플랫폼에서 활용할 수 있는&nbsp;</p><p>웹&amp;앱 콘텐츠를 기획, 구현, 설계, 제작, 운용 및 시험하는 업무를 수행할 수 있습니다.&nbsp;</p><p>입문자도 참여 가능한 수업으로 기초부터 시작하여 최종적으로 스프링과 마이 마티스를 활용한</p><p>&nbsp;웹 애플리케이션 개발 및 스프링 부트와 JPA를 활용한 웹 애플리케이션을 개발할 수 있습니다.</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;납부교육비 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><strong>8,529,300 원</strong></td></tr></tbody></table></figure>"),
('AE2',"<figure class=""table""><table><tbody><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용사유 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>&nbsp;</p><p>담당자 퇴사</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용인원 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>1명</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용형태 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p><p>정규직 (수습기간 3개월)</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;채용직급 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td><p>&nbsp;</p><p>신입 ~ 대리급</p></td><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;경력사항 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td>5년 이내</td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;모집부문 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>&nbsp;</p><p>헤드헌터(채용전문가)</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;담당업무 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><ul><li>각각의 채용 포지션에 맞는 인재 발굴</li><li>고객사에 적합한 인재 추천 및 후보자의 커리어 개발을 위해 고민</li><li>인재 데이터 축적, 이를 바탕으로 분석 및 활용 가능하도록 관리</li><li>기존 고객을 유지하고 신규 고객을 발굴하기 위한 다양한 마케팅 활동</li><li><p>고객사의 채용 브랜딩을 강화하고 적합한 인재를 채용하고 유지할 수 있도록 자문</p><p>&nbsp;</p></li></ul></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;자격요건 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>&nbsp;</p><p>대졸이상 (2,3년)</p><p>&nbsp;</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;우대사항 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><ul><li>우수 인해 Direct Sourcing 경험자</li><li>채용 관련 행사 기획, 경험해보신 분</li><li>서치펌의 헤드헌팅 또는 기업의 채용담당 경력자</li><li>채용 트렌드에 민감하고 적극적으로 학습하시는 분</li><li>빠르게 성장하고 변화하는 환경에서 주도성을 가지고 업무를 이끌어 나갈 분</li></ul><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;근로조건 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>근무일시 : 주 5일 (월 ~ 금 / 09:00 ~18:00)</p><p>근무지역 : 서울 종로구 인사동길 12 대일빌딩 15층</p><p>&nbsp;</p></td></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;전형절차 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</td><td colspan=""3""><p>&nbsp;</p><p>&nbsp;</p><p>서류전형 &gt; 1차면접 및 인적성검사 &gt; 2차면접 &gt; 최종합격</p><p>&nbsp;</p><p>&nbsp;</p></td></tr></tbody></table></figure>");


INSERT INTO APPROVAL_ATTENDANCE VALUES
('AATT1', '지각사유서', null, null, '2024-04-16', null, null, '죄송합니다.'),
('AATT2', '재택', '2024-05-22 09:00:00', '2024-05-24 18:00:00', null, '자택', '코로나 양성판정', null),
('AATT4', '휴일근무', '2024-06-08 10:00:00', '2024-06-08 15:00:00', null, '회사', '채용관련 업무', '이승훈 팀장님의 요청으로 초과 근무 신청합니다.'),
('AATT5', '출근시각 정정요청', null, null, '2024-06-18', null, null, '전장연 시위로 인해 지하철이 연착되었습니다.'),
('AATT3', '연차', '2024-06-03 09:00:00', '2024-06-07 18:00:00', null, null, null, null),
('AATT6', '출근시각 정정요청', null, null, '2024-07-04', null, null, '지하철 연착'),
('AATT7', '연차', '2024-07-10 09:00:00', '2024-07-12 18:00:00', null, null, null, null);


INSERT INTO TRUE_APP_LINE (TAL_ORDER, TAL_ROLE, AD_CODE, TAL_STATUS, EMP_CODE, TAL_REASON, TAL_DATE) VALUES
(1, '결재', 'AD1', '승인', 2024051, null, '2023-11-15'),
(2, '결재', 'AD1', '승인', 2021091, null, '2023-11-16'),
(3, '결재', 'AD1', '승인', 2021032, null, '2023-11-16'),
(4, '결재', 'AD1', '승인', 2020021, null, '2023-11-16'),
(5, '결재', 'AD1', '승인', 2020011, null, '2023-11-17'),
(1, '전결', 'AD2', '승인', 2024051, null, '2024-04-16'),
(2, '결재', 'AD2', '전결', 2021032, null, '2024-04-16'),
(3, '결재', 'AD2', '전결', 2020021, null, '2024-04-16'),
(4, '결재', 'AD2', '전결', 2020011, null, '2024-04-16'),
(1, '결재', 'AD3', '승인', 2024051, null, '2024-05-21'),
(2, '전결', 'AD3', '승인', 2021032, null, '2024-05-21'),
(3, '결재', 'AD3', '전결', 2020021, null, '2024-05-21'),
(4, '결재', 'AD3', '전결', 2020011, null, '2024-05-21'),
(1, '결재', 'AD5', '승인', 2024051, null, '2024-06-07'),
(2, '전결', 'AD5', '승인', 2021032, null, '2024-06-07'),
(3, '결재', 'AD5', '전결', 2020021, null, '2024-06-07'),
(4, '결재', 'AD5', '전결', 2020011, null, '2024-06-07'),
(1, '전결', 'AD6', '승인', 2024051, null, '2024-06-18'),
(2, '결재', 'AD6', '전결', 2021032, null, '2024-06-18'),
(3, '결재', 'AD6', '전결', 2020021, null, '2024-06-18'),
(4, '결재', 'AD6', '전결', 2020011, null, '2024-06-18'),
(1, '전결', 'AD4', '승인', 2024051, null, '2024-05-14'),
(2, '결재', 'AD4', '전결', 2021032, null, '2024-05-14'),
(3, '결재', 'AD4', '전결', 2020021, null, '2024-05-14'),
(4, '결재', 'AD4', '전결', 2020011, null, '2024-05-14'),
(1, '전결', 'AD7', '반려', 2024051, "지연증명서 첨부 필수입니다.", '2024-07-04'),
(2, '결재', 'AD7', '미결재', 2021032, null, null),
(3, '결재', 'AD7', '미결재', 2020021, null, null),
(4, '결재', 'AD7', '미결재', 2020011, null, null),
(1, '결재', 'AD8', '미결재', 2024051, null, null),
(2, '결재', 'AD8', '미결재', 2021091, null, null),
(3, '결재', 'AD8', '미결재', 2021068, null, null),
(4, '결재', 'AD8', '미결재', 2020021, null, null),
(5, '결재', 'AD8', '미결재', 2020011, null, null),
(0, '열람', 'AD9', '미결재', 2021091, null, null),
(0, '열람', 'AD9', '미결재', 2022041, null, null),
(0, '열람', 'AD9', '미결재', 2022073, null, null),
(0, '열람', 'AD9', '미결재', 2022091, null, null),
(0, '열람', 'AD9', '미결재', 2023062, null, null),
(0, '열람', 'AD9', '미결재', 2023091, null, null),
(0, '열람', 'AD9', '미결재', 2024033, null, null),
(0, '열람', 'AD9', '미결재', 2024101, null, null),
(1, '결재', 'AD9', '승인', 2024051, null, '2024-07-04'),
(2, '결재', 'AD9', '승인', 2021032, null, '2024-07-04'),
(3, '결재', 'AD9', '승인', 2020021, null, '2024-07-04'),
(4, '결재', 'AD9', '승인', 2020011, null, '2024-07-04'),
(1, '전결', 'AD10', '승인', 2024051, null, '2024-07-04'),
(2, '결재', 'AD10', '전결', 2021032, null, '2024-07-04'),
(3, '결재', 'AD10', '전결', 2020021, null, '2024-07-04'),
(4, '결재', 'AD10', '전결', 2020011, null, '2024-07-04');




INSERT INTO PHEED (PHEED_CON, CRE_STATUS, READ_STATUS, DE_STATUS, PHEED_SORT, EMP_CODE, URL) VALUES
("이복구님이 '[채용팀] 01/01-03/31 휴직신청서_이복구' 결재를 상신하였습니다.","2023-11-15 14:54:04","N","N","AD1",2024051,"/approval/view/AD1"),
("'[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 시작되었습니다.","2023-11-15 14:54:52","N","N","AD1",2024062,"/approval/view/AD1"),
("이복구님이 상신한 '[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 대기중입니다.","2023-11-16 14:54:52","N","N","AD1",2021091,"/approval/view/AD1"),
("이복구님이 상신한 '[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 대기중입니다.","2023-11-16 14:55:17","N","N","AD1",2021032,"/approval/view/AD1"),
("이복구님이 상신한 '[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 대기중입니다.","2023-11-16 14:55:31","N","N","AD1",2020021,"/approval/view/AD1"),
("이복구님이 상신한 '[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 대기중입니다.","2023-11-16 14:55:46","N","N","AD1",2020011,"/approval/view/AD1"),
("'[채용팀] 01/01-03/31 휴직신청서_이복구' 결재가 완료되었습니다.","2023-11-17 14:56:01","N","N","AD1",2024062,"/approval/view/AD1"),
("이복구님이 '[채용팀] 4/16 지각사유서_이복구' 결재를 상신하였습니다.","2024-04-16 17:15:56","N","N","AD2",2024051,"/approval/view/AD2"),
("'[채용팀] 4/16 지각사유서_이복구' 결재가 시작되었습니다.","2024-04-16 17:16:32","N","N","AD2",2024062,"/approval/view/AD2"),
("'[채용팀] 4/16 지각사유서_이복구' 결재가 완료되었습니다.","2024-04-16 17:16:32","N","N","AD2",2024062,"/approval/view/AD2"),
("이복구님이 '[채용팀] 05/22-24 예외근무신청서_이복구' 결재를 상신하였습니다.","2024-05-21 16:02:40","N","N","AD3",2024051,"/approval/view/AD3"),
("이복구님이 '[채용팀] 06/08 초과근무신청서_이복구' 결재를 상신하였습니다.","2024-06-07 16:02:58","N","N","AD5",2024051,"/approval/view/AD5"),
("이복구님이 '[채용팀] 06/18 지각사유서_이복구' 결재를 상신하였습니다.","2024-06-18 16:03:12","N","N","AD6",2024051,"/approval/view/AD6"),
("이복구님이 '[채용팀] 06/03-07 휴가신청서_이복구' 결재를 상신하였습니다.","2024-05-14 16:03:28","N","N","AD4",2024051,"/approval/view/AD4"),
("'[채용팀] 06/03-07 휴가신청서_이복구' 결재가 시작되었습니다.","2024-05-14 16:04:40","N","N","AD4",2024062,"/approval/view/AD4"),
("'[채용팀] 06/03-07 휴가신청서_이복구' 결재가 완료되었습니다.","2024-05-14 16:04:40","N","N","AD4",2024062,"/approval/view/AD4"),
("'[채용팀] 06/18 지각사유서_이복구' 결재가 시작되었습니다.","2024-06-18 16:04:42","N","N","AD6",2024062,"/approval/view/AD6"),
("'[채용팀] 06/18 지각사유서_이복구' 결재가 완료되었습니다.","2024-06-18 16:04:42","N","N","AD6",2024062,"/approval/view/AD6"),
("'[채용팀] 06/08 초과근무신청서_이복구' 결재가 시작되었습니다.","2024-06-07 16:04:44","N","N","AD5",2024062,"/approval/view/AD5"),
("이복구님이 상신한 '[채용팀] 06/08 초과근무신청서_이복구' 결재가 대기중입니다.","2024-06-07 16:04:44","N","N","AD5",2021032,"/approval/view/AD5"),
("'[채용팀] 05/22-24 예외근무신청서_이복구' 결재가 시작되었습니다.","2024-05-21 16:04:47","N","N","AD3",2024062,"/approval/view/AD3"),
("이복구님이 상신한 '[채용팀] 05/22-24 예외근무신청서_이복구' 결재가 대기중입니다.","2024-05-21 16:04:47","N","N","AD3",2021032,"/approval/view/AD3"),
("'[채용팀] 06/08 초과근무신청서_이복구' 결재가 완료되었습니다.","2024-06-07 16:05:43","N","N","AD5",2024062,"/approval/view/AD5"),
("'[채용팀] 05/22-24 예외근무신청서_이복구' 결재가 완료되었습니다.","2024-05-21 16:05:47","N","N","AD3",2024062,"/approval/view/AD3"),
("이복구님이 '[채용팀] 07/04 지각사유서_이복구' 결재를 상신하였습니다.",'2024-07-04 11:37:46',"N","N","AD7",2024051,"/approval/view/AD7"),
("이승훈님이 '[채용팀] 07/04 지각사유서_이복구' 결재를 반려하였습니다.",'2024-07-04 11:39:41',"N","N","AD7",2024062,"/approval/view/AD7"),
("이복구님이 '[채용팀] 7/4 채용요청서_이복구' 결재를 상신하였습니다.","2024-07-04 12:35:23","N","N","AD9",2024051,"/approval/view/AD9"),
("'[채용팀] 7/4 채용요청서_이복구' 결재가 시작되었습니다.","2024-07-04 12:35:37","N","N","AD9",2024062,"/approval/view/AD9"),
("이복구님이 상신한 '[채용팀] 7/4 채용요청서_이복구' 결재가 대기중입니다.","2024-07-04 12:35:37","N","N","AD9",2021032,"/approval/view/AD9"),
("이복구님이 상신한 '[채용팀] 7/4 채용요청서_이복구' 결재가 대기중입니다.","2024-07-04 12:36:31","N","N","AD9",2020021,"/approval/view/AD9"),
("이복구님이 상신한 '[채용팀] 7/4 채용요청서_이복구' 결재가 대기중입니다.","2024-07-04 12:36:56","N","N","AD9",2020011,"/approval/view/AD9"),
("'[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다.","2024-07-04 12:37:19","N","N","AD9",2024062,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 이서연님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:39","N","N","AD9",2021091,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 강예지님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2022041,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 김지연님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2022073,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 황민서님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2022091,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 이승우님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2023062,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 장수빈님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2023091,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 최지우님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2024033,"/approval/view/AD9"),
("이복구님의 '[채용팀] 7/4 채용요청서_이복구' 결재가 완료되었습니다. 박재원님은 '열람자'로써 해당 결재를 확인하실 수 있습니다.","2024-07-04 13:08:40","N","N","AD9",2024101,"/approval/view/AD9"),
("이복구님이 '[채용팀] 07/10-12 휴가신청서_이복구' 결재를 상신하였습니다.","2024-07-04 14:39:03","N","N","AD10",2024051,"/approval/view/AD10"),
("'[채용팀] 07/10-12 휴가신청서_이복구' 결재가 시작되었습니다.","2024-07-04 14:39:52","N","N","AD10",2024062,"/approval/view/AD10"),
("'[채용팀] 07/10-12 휴가신청서_이복구' 결재가 완료되었습니다.","2024-07-04 14:39:52","N","N","AD10",2024062,"/approval/view/AD10");




INSERT INTO APPROVAL_STORAGE VALUES
(1, 'AD8', 1);

INSERT INTO ATTACHMENT VALUES
(1,"진단서.jpg","967bc8c8-9add-4442-AD93-9f3b65eea51c.jpg","C:/SynergyHub/approval","AD1");