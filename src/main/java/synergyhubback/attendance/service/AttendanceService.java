package synergyhubback.attendance.service;


import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.repository.AttendanceRepository;
import synergyhubback.attendance.dto.request.AttendanceRegistRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.common.util.DateUtils;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private List<Attendance> attendances;

    private final ModelMapper modelMapper;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(ModelMapper modelMapper, AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    /* 근무 일지 생성 */
    @Scheduled(cron = "00 05 13 * * *") // 매일 오전 4시 00분에 실행
    @Transactional
    public void createDailyAttendanceRecord() {

        //모든 사원 목록 조회
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            int empCode = employee.getEmp_code();

            // 사원의 최신 근무기록 조회
            Attendance lastestAttendance = attendanceRepository.findTopByOrderByAtdCodeDesc();

            // 새로운 근무기록 생성
            AttendanceRegistRequest newAttendanceRequest = new AttendanceRegistRequest();

            if (lastestAttendance != null) {
                newAttendanceRequest.setAtdCode(lastestAttendance.getAtdCode() + 1);
            } else {
                newAttendanceRequest.setAtsCode(1);
            }

            newAttendanceRequest.setAtdDate(LocalDate.now()); // 현재 날짜로 설정
            newAttendanceRequest.setAtdStartTime(LocalTime.of(9, 0));   // 오전 09:00 으로 설정
            newAttendanceRequest.setAtdEndTime(LocalTime.of(18, 0));    // 오후 06:00 으로 설정
            newAttendanceRequest.setEmployee(employee);                   // 사원 코드 설정
            newAttendanceRequest.setAtsCode(1);                         // 근무 상태, 디폴트는 미출근?

            System.out.println(newAttendanceRequest);

            Attendance newAttendance = modelMapper.map(newAttendanceRequest, Attendance.class);

            System.out.println(newAttendance);

            attendanceRepository.save(newAttendance);
        }
    }

    /* 출근시간 등록 */
    public Attendance registAttendanceStartTime(int empCode) {

        // 사원 조회
        Employee employee = employeeRepository.findById(empCode)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        // 현재 날짜 계산
        LocalDate currentDate = LocalDate.now();

        // 근태일지 조회
        Attendance foundAttendance = attendanceRepository.findByEmployeeAndAtdDate(employee, currentDate);

        if (foundAttendance == null) {
            throw new EntityNotFoundException("근태 기록을 찾을 수 없습니다.");
        }

        // 현재 시간을 출근 시간으로 설정
        LocalTime currentTime = LocalTime.now();
        foundAttendance.updateStartTime(currentTime);

        // 업데이트된 근태 기록 저장
        attendanceRepository.save(foundAttendance);

        return foundAttendance;
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
