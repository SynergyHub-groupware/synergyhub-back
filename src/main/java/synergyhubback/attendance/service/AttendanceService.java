package synergyhubback.attendance.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synergyhubback.attendance.domain.entity.Attendance;
import synergyhubback.attendance.domain.entity.AttendanceStatus;
import synergyhubback.attendance.domain.entity.DefaultSchedule;
import synergyhubback.attendance.domain.repository.*;
import synergyhubback.attendance.dto.request.AttendanceRegistEndTimeRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistRequest;
import synergyhubback.attendance.dto.request.AttendanceRegistStartTimeRequest;
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
    private final OverTimeWorkRepository overTimeWorkRepository;
    private final AttendanceStatusRepository attendanceStatusRepository;
    private final DayOffRepository dayOffRepository;

    public AttendanceService(ModelMapper modelMapper, AttendanceRepository attendanceRepository,
                             EmployeeRepository employeeRepository, DefaultScheduleRepository defaultScheduleRepository,
                             OverTimeWorkRepository overTimeWorkRepository, AttendanceStatusRepository attendanceStatusRepository,
                             DayOffRepository dayOffRepository) {

        this.modelMapper = modelMapper;
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.defaultScheduleRepository = defaultScheduleRepository;
        this.overTimeWorkRepository = overTimeWorkRepository;
        this.attendanceStatusRepository = attendanceStatusRepository;
        this.dayOffRepository = dayOffRepository;
    }

    /* 근무 일지 생성 */
    @Scheduled(cron = "00 48 16 * * *") // 매일 오전 4시 00분에 실행
    @Transactional
    public void createDailyAttendanceRecord() {

        // 모든 사원 목록 조회
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            // 사원의 최신 근무기록 조회
            Attendance latestAttendance = attendanceRepository.findTopByOrderByAtdCodeDesc();

            // 새로운 근무기록 생성을 위한 DTO 객체 생성
            AttendanceRegistRequest newAttendanceRequest = new AttendanceRegistRequest();

            // 오늘의 날짜 설정
            LocalDate currentDate = LocalDate.now();
            newAttendanceRequest.setAtdDate(currentDate);

            // 사원의 지정 출퇴근시간 조회
            String deptCode = employee.getDepartment().getDept_code();

            // 1. 부서 정보 조회
            DefaultSchedule selectDept = defaultScheduleRepository.findWithDeptCodeAndNullEmployee(deptCode);

            // 2. 사원코드 조회
            DefaultSchedule selectEmpCode = defaultScheduleRepository.findByEmployee(employee);

            // 근무일지 코드 생성
            if (latestAttendance != null) {
                newAttendanceRequest.setAtdCode(latestAttendance.getAtdCode() + 1);
            } else {
                newAttendanceRequest.setAtdCode(1);
            }

            // 지정 출퇴근시간 설정
            if (selectDept != null) {
                if(selectEmpCode != null) {
                    newAttendanceRequest.setAtdStartTime(selectEmpCode.getAtdStartTime());
                    newAttendanceRequest.setAtdEndTime(selectEmpCode.getAtdEndTime());
                } else {
                    newAttendanceRequest.setAtdStartTime(selectDept.getAtdStartTime());
                    newAttendanceRequest.setAtdEndTime(selectDept.getAtdEndTime());
                }
            } else {
                newAttendanceRequest.setAtdStartTime(LocalTime.of(9, 0));    // 오전 09:00 으로 설정
                newAttendanceRequest.setAtdEndTime(LocalTime.of(18, 0));    // 오후 06:00 으로 설정
            }

            // 사원 코드 및 근무 상태 설정
            newAttendanceRequest.setEmployee(employee);

            // 근무 상태 설정, 디폴트는 미출근
            AttendanceStatus defaultStatus = attendanceStatusRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("기본 근무 상태를 찾을 수 없습니다."));
            newAttendanceRequest.setAttendanceStatus(defaultStatus);

            // DTO 객체를 Entity로 매핑하여 저장
            Attendance newAttendance = modelMapper.map(newAttendanceRequest, Attendance.class);
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

        // 이미 출근 시간이 등록된 경우 예외 처리
        if (foundAttendance.getStartTime() != null) {
            throw new RuntimeException("출근시간이 이미 등록되었습니다.");
        }

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

        // 이미 퇴근 시간이 등록된 경우 예외 처리
        if (foundAttendance.getEndTime() != null) {
            throw new RuntimeException("퇴근시간이 이미 등록되었습니다.");
        }

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
    public void registDefaultSchedule(String deptCode, LocalTime atdStartTime, LocalTime atdEndTime, Employee employee) {
        DefaultSchedule defaultSchedule = DefaultSchedule.builder()
                .deptCode(deptCode)
                .atdStartTime(atdStartTime)
                .atdEndTime(atdEndTime)
                .employee(employee)
                .build();

        defaultScheduleRepository.save(defaultSchedule);
    }

    /* 파라미터를 통한 지정 출퇴근시간 조회 */
    public List<DefaultSchedule> findDefaultSchedules(String deptCode, LocalTime startTime, LocalTime endTime) {
        return defaultScheduleRepository.findByDeptCodeAndAtdStartTimeAndAtdEndTime(deptCode, startTime, endTime);
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
    public void updateDefaultSchedule(String deptCode, LocalTime startTime, LocalTime endTime, Employee employee) {

        // deptCode와 employee의 empCode가 모두 일치하는 첫 번째 DefaultSchedule 조회
        DefaultSchedule deptSchedule = defaultScheduleRepository.findByDeptCodeAndEmployee(deptCode, employee);

        if (deptSchedule != null) {
            // empCode와 deptCode 모두 일치하는 경우 단일 엔티티 업데이트
            deptSchedule.updateStartTime(startTime);
            deptSchedule.updateEndTime(endTime);
            defaultScheduleRepository.save(deptSchedule); // 엔티티 저장
        } else {
            // empCode와 deptCode 모두 일치하는 DefaultSchedule이 없는 경우
            // deptCode에 해당하는 모든 DefaultSchedule 조회
            List<DefaultSchedule> deptSchedules = defaultScheduleRepository.findAllByDeptCode(deptCode);

            if (!deptSchedules.isEmpty()) {
                // deptCode에 해당하는 모든 DefaultSchedule 일괄 업데이트
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

    /* ------------------------------------ 초과근무 기록 ------------------------------------ */

    public List<AttendancesResponse> findAllOverTimeWork() {
        List<DefaultSchedule> overTimeWorkList = overTimeWorkRepository.findAll();

        return null;
    }
}
