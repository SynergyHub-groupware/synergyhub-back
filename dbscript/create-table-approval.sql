use groupware;

CREATE TABLE IF NOT EXISTS `EMPLOYEE_INFO`
(
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    `EMP_NAME`    VARCHAR(25) NOT NULL COMMENT '사원 이름',
    `EMP_PASS`    VARCHAR(255) NOT NULL COMMENT '사원 비밀번호',
    `SOCIAL_SECURITY_NO`    VARCHAR(255) NOT NULL COMMENT '주민등록번호',
    `EMAIL`    VARCHAR(255) COMMENT '이메일',
    `PHONE`    VARCHAR(255) COMMENT '전화번호',
    `ADDRESS`    VARCHAR(255) COMMENT '주소',
    `DIRECT_LINE`    INTEGER COMMENT '직통번호',
    `ACCOUNT_NUM`    VARCHAR(255) COMMENT '계좌번호',
    `HIRE_DATE`    DATE NOT NULL COMMENT '입사일',
    `END_DATE`    DATE COMMENT '퇴사일',
    `EMP_STATUS`    VARCHAR(10) DEFAULT 'Y' NOT NULL COMMENT '재직상태',
    `EMP_SIGN`    VARCHAR(255) COMMENT '사인',
    `EMP_IMG`    VARCHAR(255) COMMENT '프로필사진',
    `DEPT_CODE`    VARCHAR(255) NOT NULL COMMENT '부서 코드',
    `TITLE_CODE`    VARCHAR(10) NOT NULL COMMENT '직책 코드',
    `POSITION_CODE`    VARCHAR(10) NOT NULL COMMENT '직급 코드',
    `BANK_CODE`    INTEGER NOT NULL COMMENT '은행코드',
    `REFRESH_TOKEN`       VARCHAR(300) COMMENT '리프레시토큰',
    PRIMARY KEY ( `EMP_CODE` )
    )ENGINE=INNODB COMMENT = '사원 정보';

CREATE TABLE IF NOT EXISTS `ATTENDANCE_STATUS`
(
    `ATS_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '근무상태코드',
    `ATS_NAME`    VARCHAR(25) NOT NULL COMMENT '근무상태명',
    PRIMARY KEY ( `ATS_CODE` )
    )ENGINE=INNODB COMMENT = '근무상태';

CREATE TABLE IF NOT EXISTS `TASK` (
                                      `TASK_CODE` VARCHAR(255) NOT NULL COMMENT '업무 코드',
    `TASK_TITLE` VARCHAR(30) NOT NULL COMMENT '제목',
    `TASK_MODI_DATE` DATE COMMENT '수정일',
    `START_DATE` DATETIME NOT NULL COMMENT '시작일',
    `END_DATE` DATETIME COMMENT '종료일',
    `TASK_STATUS` CHAR(1) NOT NULL COMMENT '상태 (A: 업무대기, B: 업무 진행중, C: 업무완료)',
    `TASK_PRI` CHAR(1) NOT NULL COMMENT '우선순위 (L: 낮음, M: 중간, H: 높음)',
    `TASK_CON` VARCHAR(300) NOT NULL COMMENT '내용',
    `TASK_OWNER` VARCHAR(30) NOT NULL COMMENT '담당자',
    `TASK_DISPLAY` BOOLEAN NOT NULL COMMENT '캘린더 표시',
    `EMP_CODE` INTEGER NOT NULL COMMENT '사원 코드',
    PRIMARY KEY (`TASK_CODE`),
    CONSTRAINT `FK_TASK_EMPLOYEE` FOREIGN KEY (`EMP_CODE`) REFERENCES `EMPLOYEE_INFO` (`EMP_CODE`)
    ) ENGINE=INNODB COMMENT='업무 정보';

CREATE TABLE IF NOT EXISTS `APPROVAL_ETC`
(
    `AE_CODE`    VARCHAR(255) NOT NULL COMMENT '기타결재코드',
    `AE_CON`    TEXT NOT NULL COMMENT '결재내용',
    PRIMARY KEY ( `AE_CODE` )
    )ENGINE=INNODB COMMENT = '기타결재';

CREATE TABLE IF NOT EXISTS `PHEED`
(
    `PHEED_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '피드코드',
    `PHEED_CON`    VARCHAR(225) NOT NULL COMMENT '피드내용',
    `CRE_STATUS`    DATETIME NOT NULL COMMENT '생성시간',
    `READ_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '읽음상태',
    `DE_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '삭제상태',
    `PHEED_SORT` VARCHAR(255) NOT NULL COMMENT '피드 분류',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    PRIMARY KEY ( `PHEED_CODE` )
    )ENGINE=INNODB COMMENT = '피드';

CREATE TABLE IF NOT EXISTS `GUEST`
(
    `GET_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '참석자 코드',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드(담장자, 참석자)',
    `GET_SORT` 	  VARCHAR(255) NOT NULL COMMENT '참석자 분류',
    PRIMARY KEY ( `GET_CODE` )
    )ENGINE=INNODB COMMENT = '참석자';

CREATE TABLE IF NOT EXISTS `OVERTIME_WORK`
(
    `OW_CODE` INTEGER NOT NULL AUTO_INCREMENT COMMENT '초과근무코드',
    `ATD_CODE` DATE NOT NULL COMMENT '근무날짜',
    `START_TIME` DATETIME NOT NULL COMMENT '시작시간',
    `END_TIME` DATETIME NOT NULL COMMENT '종료시간',
    `EMP_CODE` INTEGER NOT NULL COMMENT '사원코드',
    PRIMARY KEY ( `OW_CODE` )
    )ENGINE=INNODB COMMENT = '초과근무 일정';

CREATE TABLE IF NOT EXISTS `ATTENDANCE`
(
    `ATD_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '근태코드',
    `ATD_DATE`    DATE NOT NULL COMMENT '근무날짜',
    `ATD_START_TIME`    TIME DEFAULT '09:00:00' NOT NULL COMMENT '지정출근시간',
    `ATD_END_TIME`    TIME DEFAULT '18:00:00' NOT NULL COMMENT '지정퇴근시간',
    `START_TIME`    TIME COMMENT '출근시간',
    `END_TIME`    TIME COMMENT '퇴근시간',
    `ATS_CODE`    INTEGER NOT NULL COMMENT '근무상태코드',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    PRIMARY KEY ( `ATD_CODE` )
    )ENGINE=INNODB COMMENT = '근태';

CREATE TABLE IF NOT EXISTS `DEPT_RELATIONS`
(
    `SUP_DEPT_CODE`    VARCHAR(255) NOT NULL COMMENT '상위 부서',
    `SUB_DEPT_CODE`    VARCHAR(255) NOT NULL COMMENT '하위 부서',
    PRIMARY KEY ( `SUP_DEPT_CODE`,`SUB_DEPT_CODE` )
    )ENGINE=INNODB COMMENT = '부서관계';

CREATE TABLE IF NOT EXISTS `MESSAGE_BLOCK`
(
    `BLK_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '차단 코드',
    `BLK_DATE`    DATE NOT NULL COMMENT '차단 일자',
    `BLK_ID`    INTEGER NOT NULL COMMENT '사원 코드(차단자)',
    `BLK_NAME`    INTEGER NOT NULL COMMENT '사원 코드(차단 회원)',
    PRIMARY KEY ( `BLK_CODE` )
    )ENGINE=INNODB COMMENT = '쪽지 차단';

CREATE TABLE IF NOT EXISTS `EMP_POSITION`
(
    `POSITION_CODE`    VARCHAR(10) NOT NULL COMMENT '직급 코드',
    `POSITION_NAME`    VARCHAR(10) NOT NULL COMMENT '직급명',
    PRIMARY KEY ( `POSITION_CODE` )
    )ENGINE=INNODB COMMENT = '직급';

CREATE TABLE IF NOT EXISTS `TRUE_APP_LINE`
(
    `TAL_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '실결재라인코드',
    `TAL_ORDER`    INTEGER NOT NULL COMMENT '실결재자순서',
    `TAL_ROLE`    VARCHAR(255) NOT NULL COMMENT '실결재자역할',
    `AD_CODE`    VARCHAR(255) NOT NULL COMMENT '결재문서코드',
    `TAL_STATUS`    VARCHAR(255) COMMENT '실결재상태',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드(실결재자)',
    `TAL_REASON`    VARCHAR(255) COMMENT '반려 사유',
    `TAL_DATE`    DATE COMMENT '결재일',
    PRIMARY KEY ( `TAL_CODE` )
    )ENGINE=INNODB COMMENT = '실결재라인';

CREATE TABLE IF NOT EXISTS `POST`
(
    `POST_CODE`    VARCHAR(255) NOT NULL COMMENT '게시글 코드',
    `POST_NAME`    VARCHAR(255) NOT NULL COMMENT '게시글 제목',
    `POST_VIEW_CNT`    INTEGER NOT NULL COMMENT '게시글 조회수',
    `POST_DATE`    DATE NOT NULL COMMENT '게시글 작성일',
    `POST_CON`    VARCHAR(500) NOT NULL COMMENT '게시글 내용',
    `POST_COMM_SET`    INTEGER NOT NULL COMMENT '게시글 댓글 설정',
    `FIX_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '고정 상태',
    `NOTICE_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '공지사항 상태',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드(게시글 작성자)',
    `LOW_CODE`    INTEGER NOT NULL COMMENT '하위게시판 코드',
    `PS_CODE`    INTEGER NOT NULL COMMENT '게시글분류코드',
    PRIMARY KEY ( `POST_CODE` )
    )ENGINE=INNODB COMMENT = '게시글';

CREATE TABLE IF NOT EXISTS `POST_SORT`
(
    `PS_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시글분류코드',
    `PS_NAME`    VARCHAR(255) NOT NULL COMMENT '게시글분류명',
    PRIMARY KEY ( `PS_CODE` )
    )ENGINE=INNODB COMMENT = '게시글 분류';

CREATE TABLE IF NOT EXISTS `STORAGE`
(
    `STOR_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '보관함 코드',
    `STOR_NAME`    VARCHAR(255) NOT NULL COMMENT '보관함 이름',
    PRIMARY KEY ( `STOR_CODE` )
    )ENGINE=INNODB COMMENT = '보관함';

CREATE TABLE IF NOT EXISTS `BANK`
(
    `BANK_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '은행코드',
    `BANK_NAME`    VARCHAR(255) NOT NULL COMMENT '은행명',
    PRIMARY KEY ( `BANK_CODE` )
    )ENGINE=INNODB COMMENT = '은행';

CREATE TABLE IF NOT EXISTS `DAY_OFF`
(
    `DO_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '휴가코드',
    `DO_NAME`    VARCHAR(255) NOT NULL COMMENT '휴가명',
    `DO_START_DATE`    DATE NOT NULL COMMENT '시작일',
    `DO_END_DATE`    DATE NOT NULL COMMENT '종료일',
    `DO_START_TIME`    DATETIME NOT NULL COMMENT '시작시간',
    `DO_END_TIME`    DATETIME NOT NULL COMMENT '종료시간',
    `GRANTED`    INTEGER NOT NULL COMMENT '부여수',
    `REMAINNG`    INTEGER NOT NULL COMMENT '잔여수',
    `USED`    INTEGER NOT NULL COMMENT '사용수',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    `DO_INSERT_DATE`    DATE NOT NULL COMMENT '등록일자',
    PRIMARY KEY ( `DO_CODE` )
    )ENGINE=INNODB COMMENT = '휴가';

CREATE TABLE IF NOT EXISTS `POST_ROLE`
(
    `PR_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시판권한코드',
    `PR_WRITE_ROLE`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '쓰기권한',
    `LOW_CODE`    INTEGER NOT NULL COMMENT '하위게시판 코드',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드(권한인)',
    `PR_ADMIN`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '관리자 권한',
    PRIMARY KEY ( `PR_CODE` )
    )ENGINE=INNODB COMMENT = '게시판 권한';

CREATE TABLE IF NOT EXISTS `LINE_SORT`
(
    `LS_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '결재라인분류코드',
    `LS_NAME`    VARCHAR(255) NOT NULL COMMENT '결재라인분류명',
    PRIMARY KEY ( `LS_CODE` )
    )ENGINE=INNODB COMMENT = '결재라인분류';

CREATE TABLE IF NOT EXISTS `SHCOOL_INFO`
(
    `SCH_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '학교코드',
    `SCH_NAME`    VARCHAR(50) NOT NULL COMMENT '학교명',
    `GRAD_STATUS`    VARCHAR(5) NOT NULL COMMENT '졸업여부',
    `ENROLE_DATE`    DATE NOT NULL COMMENT '입학일',
    `GRAD_DATE`    DATE COMMENT '졸업일',
    `MAJOR`    VARCHAR(50) COMMENT '전공',
    `DAY_N_NIGHT`    VARCHAR(10) COMMENT '주야',
    `LOCATION`    VARCHAR(10) COMMENT '소재지',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    PRIMARY KEY ( `SCH_CODE` )
    )ENGINE=INNODB COMMENT = '학교';

CREATE TABLE IF NOT EXISTS `CERTIFICATE`
(
    `CER_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '자격증코드',
    `CER_NAME`    VARCHAR(50) NOT NULL COMMENT '자격증명',
    `CER_SCORE`    VARCHAR(50) COMMENT '점수',
    `CER_DATE`    DATE NOT NULL COMMENT '취득일',
    `CER_NUM`    VARCHAR(50) COMMENT '자격증번호',
    `ISS_ORGAN`    VARCHAR(255) COMMENT '발행기관',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드',
    PRIMARY KEY ( `CER_CODE` )
    )ENGINE=INNODB COMMENT = '자격증';

CREATE TABLE IF NOT EXISTS `ORGANIZATION`
(
    `ORG_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '조직도 코드',
    `ORG_HIST`    VARCHAR(500) NOT NULL COMMENT '조직도 히스토리',
    `ORG_DATE`    DATE NOT NULL COMMENT '생성 일자',
    PRIMARY KEY ( `ORG_CODE` )
    )ENGINE=INNODB COMMENT = '조직도';

CREATE TABLE IF NOT EXISTS `ATTACHMENT`
(
    `ATTACH_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '첨부파일코드',
    `ATTACH_ORIGINAL`    VARCHAR(255) NOT NULL COMMENT '원본명',
    `ATTACH_SAVE`    VARCHAR(255) NOT NULL COMMENT '저장명',
    `ATTACH_URL`    VARCHAR(255) NOT NULL COMMENT '경로',
    `ATTACH_SORT` VARCHAR(255) NOT NULL COMMENT '첨부파일 분류',
    PRIMARY KEY ( `ATTACH_CODE` )
    )ENGINE=INNODB COMMENT = '첨부파일';

CREATE TABLE IF NOT EXISTS `LABEL` (
                                       `LABEL_CODE` BIGINT NOT NULL AUTO_INCREMENT COMMENT '라벨 코드',
                                       `LABEL_COLOR` VARCHAR(255) COMMENT '라벨 색상',
    `LABEL_CON` VARCHAR(255) COMMENT '라벨 내용',
    `LABEL_TITLE` VARCHAR(255) COMMENT '라벨 제목',
    PRIMARY KEY (`LABEL_CODE`)
    ) ENGINE=INNODB COMMENT='라벨 정보';


CREATE TABLE IF NOT EXISTS `EVENT` (
                                       `EVENT_CODE` VARCHAR(255) NOT NULL COMMENT '이벤트 코드',
    `EVENT_TITLE` VARCHAR(255) NOT NULL COMMENT '이벤트 제목',
    `EVENT_CON` VARCHAR(255) NOT NULL COMMENT '이벤트 내용',
    `START_DATE` DATETIME NOT NULL COMMENT '시작 날짜',
    `END_DATE` DATETIME COMMENT '종료 날짜',
    `ALLDAY` BOOLEAN NOT NULL COMMENT '하루 종일 여부',
    `EVENT_GUESTS` VARCHAR(255) COMMENT '이벤트 게스트',
    `EMP_CODE` INTEGER NOT NULL COMMENT '사원 코드',
    `LABEL_CODE` BIGINT NOT NULL COMMENT '라벨 코드',
    PRIMARY KEY (`EVENT_CODE`),
    CONSTRAINT `FK_EVENT_EMPLOYEE` FOREIGN KEY (`EMP_CODE`) REFERENCES `employee_info` (`emp_code`),
    CONSTRAINT `FK_EVENT_LABEL` FOREIGN KEY (`LABEL_CODE`) REFERENCES `label` (`label_code`)
    ) ENGINE=INNODB COMMENT='이벤트 정보';

CREATE TABLE IF NOT EXISTS `MESSAGE`
(
    `MSG_CODE`    VARCHAR(255) NOT NULL COMMENT '쪽지 코드',
    `SEND_DATE`    DATE NOT NULL COMMENT '보낸 일자',
    `MSG_TITLE`    VARCHAR(225) NOT NULL COMMENT '쪽지 제목',
    `MSG_CON`    VARCHAR(500) NOT NULL COMMENT '쪽지 내용',
    `MSG_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '읽음 상태',
    `EMER_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '긴급 상태',
    `EMP_REV`    INTEGER NOT NULL COMMENT '사원 코드(받은 사람)',
    `EMP_SEND`    INTEGER NOT NULL COMMENT '사원 코드(보낸 사람)',
    `STOR_CODE`    INTEGER NOT NULL COMMENT '보관함 코드',
    PRIMARY KEY ( `MSG_CODE` )
    )ENGINE=INNODB COMMENT = '쪽지';

CREATE TABLE IF NOT EXISTS `BOARD`
(
    `BOARD_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '게시판 코드',
    `BOARD_NAME`    VARCHAR(50) NOT NULL COMMENT '게시판 이름',
    PRIMARY KEY ( `BOARD_CODE` )
    )ENGINE=INNODB COMMENT = '게시판';

CREATE TABLE IF NOT EXISTS `LOW_BOARD`
(
    `LOW_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '하위게시판 코드',
    `LOW_NAME`    VARCHAR(50) NOT NULL COMMENT '하위게시판 이름',
    `BOARD_CODE`    INTEGER NOT NULL COMMENT '게시판 코드',
    PRIMARY KEY ( `LOW_CODE` )
    )ENGINE=INNODB COMMENT = '하위 게시판';

CREATE TABLE IF NOT EXISTS `COMMENT`
(
    `COMM_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '댓글 코드',
    `COMM_CON`    VARCHAR(255) NOT NULL COMMENT '댓글 내용',
    `COMM_DATE`    DATETIME NOT NULL COMMENT '댓글 작성일',
    `COMM_STATUS`    CHAR(1) DEFAULT 'N' NOT NULL COMMENT '익명 상태',
    `POST_CODE`    VARCHAR(255) NOT NULL COMMENT '게시글 코드',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '사원 코드(댓글 작성자 아이디)',
    PRIMARY KEY ( `COMM_CODE` )
    )ENGINE=INNODB COMMENT = '댓글';

CREATE TABLE IF NOT EXISTS `APPROVAL_APPOINT`
(
    `AAPP_CODE`    VARCHAR(255) NOT NULL COMMENT '발령결재코드',
    `AAPP_NO`    VARCHAR(255) NOT NULL COMMENT '발령번호',
    `AAPP_DATE`    DATE NOT NULL COMMENT '발령일',
    `AAPP_TITLE`    VARCHAR(255) NOT NULL COMMENT '발령제목',
    PRIMARY KEY ( `AAPP_CODE` )
    )ENGINE=INNODB COMMENT = '발령결재';

CREATE TABLE IF NOT EXISTS `APPOINT_DETAIL`
(
    `ADET_CODE`		INTEGER NOT NULL AUTO_INCREMENT COMMENT '발령결재상세코드',
    `AAPP_CODE`    VARCHAR(255) NOT NULL COMMENT '발령결재코드',
    `AAPP_BEFORE`    VARCHAR(255) NOT NULL COMMENT '발령전',
    `AAPP_AFTER`    VARCHAR(255) NOT NULL COMMENT '발령후',
    `AAPP_TYPE`    VARCHAR(255) NOT NULL COMMENT '발령종류',
    `EMP_CODE`    INT NOT NULL COMMENT '사원 코드',
    PRIMARY KEY ( `ADET_CODE` )
    )ENGINE=INNODB COMMENT = '발령결재상세';

CREATE TABLE IF NOT EXISTS `APPROVAL_PERSONAL`
(
    `AP_CODE`    VARCHAR(255) NOT NULL COMMENT '인사결재코드',
    `AP_START`    DATE COMMENT '시작일',
    `AP_END`    DATE NOT NULL COMMENT '종료일',
    `AP_CONTACT`    VARCHAR(255) NOT NULL COMMENT '비상연락처',
    `AP_REASON`    VARCHAR(255) NOT NULL COMMENT '사유',
    PRIMARY KEY ( `AP_CODE` )
    )ENGINE=INNODB COMMENT = '인사결재';

CREATE TABLE IF NOT EXISTS `APPROVAL_ATTENDANCE`
(
    `AATT_CODE`    VARCHAR(255) NOT NULL COMMENT '근태결재코드',
    `AATT_SORT`    VARCHAR(255) NOT NULL COMMENT '분류',
    `AATT_START`    DATETIME COMMENT '시작일',
    `AATT_END`    DATETIME COMMENT '종료일',
    `AATT_OCCUR`    DATE COMMENT '발생일',
    `AATT_PLACE`    VARCHAR(255) COMMENT '근무지',
    `AATT_CON`    VARCHAR(255) COMMENT '내용',
    `AATT_REASON`    VARCHAR(255) COMMENT '사유',
    PRIMARY KEY ( `AATT_CODE` )
    )ENGINE=INNODB COMMENT = '근태결재';

CREATE TABLE IF NOT EXISTS `APPROVAL_FORM`
(
    `AF_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '결재양식코드',
    `AF_NAME`    VARCHAR(255) NOT NULL COMMENT '결재양식명',
    `AF_EXPLAIN` VARCHAR(255) COMMENT '결재양식설명',
    `LS_CODE`    INTEGER NOT NULL COMMENT '결재라인분류코드',
    `AF_CON` 	 TEXT COMMENT '결재양식기본내용',
    PRIMARY KEY ( `AF_CODE` )
    )ENGINE=INNODB COMMENT = '결재양식';

CREATE TABLE IF NOT EXISTS `APPROVAL_BOX`
(
    `AB_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '결재보관함코드',
    `AB_NAME`    VARCHAR(255) NOT NULL COMMENT '결재보관함명',
    `EMP_CODE`    INTEGER COMMENT '사원 코드',
    PRIMARY KEY ( `AB_CODE` )
    )ENGINE=INNODB COMMENT = '결재보관함';

CREATE TABLE IF NOT EXISTS `APPROVAL_STORAGE`
(
    `AS_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '결재보관코드',
    `AD_CODE`    VARCHAR(255) NOT NULL COMMENT '결재문서코드',
    `AB_CODE`    INTEGER NOT NULL COMMENT '결재보관함코드',
    PRIMARY KEY ( `AS_CODE` )
    )ENGINE=INNODB COMMENT = '결재보관내역';

CREATE TABLE IF NOT EXISTS `APPROVAL_LINE`
(
    `AL_CODE`    INTEGER NOT NULL AUTO_INCREMENT COMMENT '결재라인코드',
    `AL_SORT`	 VARCHAR(255) COMMENT '결재자',
    `AL_ORDER`   INTEGER NOT NULL COMMENT '결재순서',
    `AL_ROLE`    VARCHAR(255) NOT NULL COMMENT '결재역할' DEFAULT '결재',
    `LS_CODE`    INTEGER NOT NULL COMMENT '결재라인분류코드',
    PRIMARY KEY ( `AL_CODE` )
    )ENGINE=INNODB COMMENT = '결재라인';

CREATE TABLE IF NOT EXISTS `APPROVAL_DOC`
(
    `AD_CODE`    VARCHAR(255) NOT NULL COMMENT '결재문서코드',
    `AD_TITLE`    VARCHAR(255) NOT NULL COMMENT '결재문서제목',
    `EMP_CODE`    INTEGER NOT NULL COMMENT '작성자',
    `AD_REPORT_DATE`    DATE NOT NULL COMMENT '상신일',
    `AD_STATUS`    VARCHAR(255) NOT NULL COMMENT '문서상태',
    `AF_CODE`    INTEGER NOT NULL COMMENT '결재양식코드',
    `AD_DETAIL`	VARCHAR(255) NOT NULL COMMENT '결재문서상세',
    PRIMARY KEY ( `AD_CODE` )
    )ENGINE=INNODB COMMENT = '결재문서';

CREATE TABLE IF NOT EXISTS `DEPARTMENT`
(
    `DEPT_CODE`    VARCHAR(255) NOT NULL COMMENT '부서 코드',
    `DEPT_TITLE`    VARCHAR(255) NOT NULL COMMENT '부서명',
    `CREATION_DATE`    DATE NOT NULL COMMENT '생성일',
    `END_DATE`    DATE COMMENT '폐쇄일',
    PRIMARY KEY ( `DEPT_CODE` )
    )ENGINE=INNODB COMMENT = '부서';

CREATE TABLE IF NOT EXISTS `EMP_TITLE`
(
    `TITLE_CODE`    VARCHAR(10) NOT NULL COMMENT '직책 코드',
    `TITLE_NAME`    VARCHAR(10) NOT NULL COMMENT '직책명',
    PRIMARY KEY ( `TITLE_CODE` )
    )ENGINE=INNODB COMMENT = '직책';
