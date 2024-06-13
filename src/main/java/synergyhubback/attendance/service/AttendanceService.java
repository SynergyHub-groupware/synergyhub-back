package synergyhubback.attendance.service;


import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.repository.AttendanceRepository;
import synergyhubback.attendance.dto.request.AttendanceRegistRequest;
import synergyhubback.common.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    private final ModelMapper modelMapper;
    private final AttendanceRepository attendanceRepository;

    public AttendanceService(ModelMapper modelMapper, AttendanceRepository attendanceRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = attendanceRepository;
    }

    @Scheduled(cron = "30 17 13 * * *") // 매일 오후 1시 00분에 실행
    public void createDailyAttendanceRecord() {

        // 새로운 근무기록 생성
        AttendanceRegistRequest newAttendanceRequest = new AttendanceRegistRequest();
        newAttendanceRequest.setAtdDate(LocalDate.now()); // 현재 날짜로 설정
        newAttendanceRequest.setAtdStartTime(LocalTime.of(9, 0));   // 오전 09:00 으로 설정
        newAttendanceRequest.setAtdEndTime(LocalTime.of(18, 0));    // 오후 06:00 으로 설정
        newAttendanceRequest.setEmpCode(2024031);                                // 사원 코드
        newAttendanceRequest.setAtsCode(1);                                      // 근무 상태, 디폴트는 미출근?

        System.out.println(newAttendanceRequest);

        Attendance newAttendance = modelMapper.map(newAttendanceRequest, Attendance.class);

        System.out.println(newAttendance);

        attendanceRepository.save(newAttendance);
    }

    /* 근무일지 기록 */
    @Transactional
    public List<Attendance> registAttendance() {

        //앞단에서 출근 <-> 퇴근 버튼 활성화

        // 1. 출근시간 등록 : 출근시간이 null 이라면 등록, null이 아니라면 "이미 출근시간을 등록하였습니다."

        // 2. 퇴근시간 등록 : 출근시간이 현재 시간보다 이전이라면 등록

        return null;
    }


    /* 금주의 근태 기록 */
    @Transactional(readOnly = true)
    public List<Attendance> getAttendancesForCurrentWeek() {
        LocalDate[] dateRange = DateUtils.getCurrentWeek();
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];

        System.out.println(startDate);
        System.out.println(endDate);

        return attendanceRepository.findAttendanceInDateRange(startDate, endDate);
    }

    public List<Attendance> findAllAttendances() {
        return attendanceRepository.findAll();
    }

    /* 가장 최신 근태 기록 조회 */
    @Transactional(readOnly = true)
    public Attendance getLatestAttendanceRecord() {
        return attendanceRepository.findTopByOrderByAtdCodeDesc();
    }
}
