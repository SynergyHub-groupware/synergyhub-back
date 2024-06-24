package synergyhubback.attendance.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.attendance.domain.repository.AttendanceRepository;
import synergyhubback.attendance.domain.repository.DefaultScheduleRepository;
import synergyhubback.attendance.dto.request.AttendanceRegistEndTimeRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
import synergyhubback.attendance.dto.request.DefaultScheduleRequest;
import synergyhubback.attendance.dto.response.AttendancesResponse;
import synergyhubback.attendance.dto.response.DefaultScheduleResponse;
import synergyhubback.common.util.DateUtils;
import synergyhubback.employee.domain.entity.Employee;
import synergyhubback.employee.domain.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttendanceService {

    private List<Attendance> attendances;

    private final ModelMapper modelMapper;
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final DefaultScheduleRepository defaultScheduleRepository;

    public AttendanceService(ModelMapper modelMapper, AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository, DefaultScheduleRepository defaultScheduleRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.defaultScheduleRepository = defaultScheduleRepository;
    }

    /* 근무 일지 생성 */
    @Scheduled(cron = "00 02 11 * * *") // 매일 오전 4시 00분에 실행
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

            newAttendanceRequest.setAtdDate(LocalDate.now());                         // 현재 날짜로 설정
            newAttendanceRequest.setAtdStartTime(LocalTime.of(9, 0));   // 오전 09:00 으로 설정
            newAttendanceRequest.setAtdEndTime(LocalTime.of(18, 0));    // 오후 06:00 으로 설정
            newAttendanceRequest.setEmployee(employee);                               // 사원 코드 설정
            newAttendanceRequest.setAtsCode(1);                                       // 근무 상태, 디폴트는 미출근?

            System.out.println(newAttendanceRequest);

            Attendance newAttendance = modelMapper.map(newAttendanceRequest, Attendance.class);

            System.out.println(newAttendance);

            attendanceRepository.save(newAttendance);
        }
    }

    @Transactional
    public AttendanceRegistStartTimeRequest registAttendanceStartTime(int empCode) {
        // 사원 조회
        Employee employee = employeeRepository.findById(empCode)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        System.out.println(employee.getEmp_name());

        // 현재 날짜 계산
        LocalDate currentDate = LocalDate.now();

        // 근태일지 조회
        Attendance foundAttendance = attendanceRepository.findByEmployeeAndAtdDate(employee, currentDate);

        if (foundAttendance == null) {
            throw new EntityNotFoundException("근태 기록을 찾을 수 없습니다.");
        }

//        // 이미 출근 시간이 등록된 경우 예외 처리
//        if (foundAttendance.getAtdStartTime() != null) {
//            throw new RuntimeException("출근시간이 이미 등록되었습니다.");
//        }

        // 현재 시간을 출근 시간으로 설정
        LocalTime currentTime = LocalTime.now();
        foundAttendance.updateStartTime(currentTime);

        // 업데이트된 근태 기록 저장
        attendanceRepository.save(foundAttendance);

        AttendanceRegistStartTimeRequest response = new AttendanceRegistStartTimeRequest();
        response.setStartTime(currentTime);

        return response;
    }

    /* 퇴근시간 등록 */
    @Transactional
    public AttendanceRegistEndTimeRequest registAttendanceEndTime(int empCode) {

        // 사원 조회
        Employee employee = employeeRepository.findById(empCode)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        // 현재 날짜 계산
        LocalDate currentDate = LocalDate.now();

        // 근태일지 조회
        Attendance foundAttendance = attendanceRepository.findByEmployeeAndAtdDate(employee, currentDate);

        // 근태 기록이 없는 경우
        if (foundAttendance == null) {
            throw new EntityNotFoundException("근태 기록을 찾을 수 없습니다.");
        }

        // 출근 시간이 등록되지 않은 경우 처리
        if (foundAttendance.getAtdStartTime() == null) {
            throw new EntityNotFoundException("출근 시간이 등록되지 않았습니다.");
        }

//        // 이미 퇴근 시간이 등록된 경우 예외 처리
//        if (foundAttendance.getAtdEndTime() != null) {
//            throw new RuntimeException("퇴근시간이 이미 등록되었습니다.");
//        }

        // 현재 시간을 퇴근 시간으로 설정
        LocalTime endTime = LocalTime.now();

        // 현재 퇴근 시간과 출근 시간을 비교하여 1분 이내인지 확인
        if (endTime.isBefore(foundAttendance.getAtdStartTime().plusMinutes(1))) {
            throw new EntityNotFoundException("총 근무시간이 1분 이하일 수 없습니다.");
        }

        // 퇴근시간 업데이트
        foundAttendance.updateEndTime(endTime);

        // 업데이트된 근태 기록 저장
        attendanceRepository.save(foundAttendance);

        // 응답 객체 생성 및 설정
        AttendanceRegistEndTimeRequest response = new AttendanceRegistEndTimeRequest();
        response.setEndTime(endTime);

        return response;
    }


    /* 금주의 근태 기록 */
    @Transactional(readOnly = true)
    public List<AttendancesResponse> getAttendancesForCurrentWeek() {
        LocalDate[] dateRange = DateUtils.getCurrentWeek();
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];

        System.out.println(startDate);
        System.out.println(endDate);

        return attendanceRepository.findAttendanceInDateRange(startDate, endDate);
    }

    /* 모든 근태 기록 */
    public List<AttendancesResponse> findAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();

        return attendances.stream()
                .map(AttendancesResponse::new)
                .collect(Collectors.toList());
    }

    /* 가장 최신 근태 기록 조회 */
    @Transactional(readOnly = true)
    public Attendance getLatestAttendanceRecord() {
        return attendanceRepository.findTopByOrderByAtdCodeDesc();
    }

    /* 오늘의 근태 기록 조회 */
    public AttendancesResponse getAttendancesForToday(int empCode) {

        // 사원 조회
        Employee employee = employeeRepository.findById(empCode)
                .orElseThrow(() -> new EntityNotFoundException("사원을 찾을 수 없습니다."));

        // 현재 날짜 계산
        LocalDate currentDate = LocalDate.now();

        // 근태일지 조회
        Attendance foundAttendance = attendanceRepository.findByEmployeeAndAtdDate(employee, currentDate);

        // AttendancesResponse 객체로 변환하여 반환
        AttendancesResponse response = new AttendancesResponse(foundAttendance);

        return response;
    }

    /* 지정 출퇴근시간 등록 */
    @Transactional
    public void registDefaultSchedule(String deptTitle, LocalTime atdStartTime, LocalTime atdEndTime, Employee employee) {
        DefaultSchedule defaultSchedule = DefaultSchedule.builder()
                .deptTitle(deptTitle)
                .atdStartTime(atdStartTime)
                .atdEndTime(atdEndTime)
                .employee(employee)
                .build();

        defaultScheduleRepository.save(defaultSchedule);
    }

    /* 파라미터를 통한 지정 출퇴근시간 조회 */
    public List<DefaultSchedule> findDefaultSchedules(String deptTitle, LocalTime startTime, LocalTime endTime) {
        return defaultScheduleRepository.findByDeptTitleAndAtdStartTimeAndAtdEndTime(deptTitle, startTime, endTime);
    }

    /* 모든 지정 출퇴근시간 조회 */
    public List<DefaultScheduleResponse> findAllDefaultSchedules() {
        List<DefaultSchedule> defaultSchedules = defaultScheduleRepository.findAll();

        return defaultSchedules.stream()
                .map(DefaultScheduleResponse::new)
                .collect(Collectors.toList());
    }

    /* 지정 출퇴근시간 수정 */
    @Transactional
    public void updateDefaultSchedule(String deptTitle, LocalTime startTime, LocalTime endTime, Employee employee) {

        // deptTitle과 employee의 empCode가 모두 일치하는 첫 번째 DefaultSchedule 조회
        DefaultSchedule deptSchedule = defaultScheduleRepository.findByDeptTitleAndEmployee(deptTitle, employee);

        if (deptSchedule != null) {
            // empCode와 deptTitle 모두 일치하는 경우 단일 엔티티 업데이트
            deptSchedule.updateStartTime(startTime);
            deptSchedule.updateEndTime(endTime);
            defaultScheduleRepository.save(deptSchedule); // 엔티티 저장
        } else {
            // empCode와 deptTitle이 모두 일치하는 DefaultSchedule이 없는 경우
            // deptTitle에 해당하는 모든 DefaultSchedule 조회
            List<DefaultSchedule> deptSchedules = defaultScheduleRepository.findAllByDeptTitle(deptTitle);

            if (!deptSchedules.isEmpty()) {
                // deptTitle에 해당하는 모든 DefaultSchedule 일괄 업데이트
                for (DefaultSchedule schedule : deptSchedules) {
                    schedule.updateStartTime(startTime);
                    schedule.updateEndTime(endTime);
                }
                defaultScheduleRepository.saveAll(deptSchedules); // 엔티티들 일괄 저장
            } else {
                throw new RuntimeException("수정할 출퇴근시간이 존재하지 않습니다.");
            }
        }
    }

    /* 지정 출퇴근시간 삭제 */
    @Transactional
    public boolean deleteSchedule(int dsCode) {
        Optional<DefaultSchedule> optionalSchedule = defaultScheduleRepository.findById(dsCode);
        if (optionalSchedule.isPresent()) {
            defaultScheduleRepository.delete(optionalSchedule.get());
            return true;
        } else {
            return false;
        }
    }
}
