package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.ClassroomQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.VO.ClassroomVO;
import demo.campus_management_system.entity.VO.ReservationVO;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户查询教室
     */
    @GetMapping("/selectClassroom")
    public ResultDTO<List<ClassroomVO>> selectClassroom(
            @RequestParam(required = false) String building_id,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String room_type,
            @RequestParam(required = false) String room_status,
            @RequestParam String date,
            @RequestParam String period,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        // 参数校验
        if (date == null || date.trim().isEmpty()) {
            return ResultDTO.fail(400, "使用日期不能为空");
        }
        if (period == null || period.trim().isEmpty()) {
            return ResultDTO.fail(400, "使用节次不能为空");
        }
        
        // 自动处理period格式：如果只是数字，自动添加"节"字
        if (period.matches("^\\d+$")) {
            period = period + "节";
        }
        
        // 构建查询DTO
        ClassroomQueryDTO queryDTO = new ClassroomQueryDTO();
        queryDTO.setBuilding_id(building_id);
        queryDTO.setCapacity(capacity);
        queryDTO.setRoom_type(room_type);
        queryDTO.setRoom_status(room_status);
        queryDTO.setDate(date);
        queryDTO.setPeriod(period);
        queryDTO.setPage(page);
        queryDTO.setSize(size);
        
        return userService.selectClassroom(queryDTO);
    }
    
    /**
     * 用户预约教室信息填写
     */
    @PostMapping("/submitClassroomApply")
    public ResultDTO<Boolean> submitClassroomApply(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ClassroomApplyDTO applyDTO) {
        
        // 参数校验
        if (applyDTO.getUser_name() == null || applyDTO.getUser_name().trim().isEmpty()) {
            return ResultDTO.fail(400, "申请人姓名不能为空");
        }
        if (applyDTO.getCollege_id() == null || applyDTO.getCollege_id().trim().isEmpty()) {
            return ResultDTO.fail(400, "所属学院不能为空");
        }
        if (applyDTO.getPhone() == null || applyDTO.getPhone().trim().isEmpty()) {
            return ResultDTO.fail(400, "联系电话不能为空");
        }
        if (applyDTO.getPurpose() == null || applyDTO.getPurpose().trim().isEmpty()) {
            return ResultDTO.fail(400, "用途不能为空");
        }
        if (applyDTO.getPerson_count() == null || applyDTO.getPerson_count() <= 0) {
            return ResultDTO.fail(400, "参与人数必须大于0");
        }
        if (applyDTO.getDate() == null || applyDTO.getDate().trim().isEmpty()) {
            return ResultDTO.fail(400, "使用日期不能为空");
        }
        if (applyDTO.getWeek() == null || applyDTO.getWeek() <= 0) {
            return ResultDTO.fail(400, "周数必须大于0");
        }
        if (applyDTO.getDay_of_week() == null || applyDTO.getDay_of_week().trim().isEmpty()) {
            return ResultDTO.fail(400, "星期不能为空");
        }
        if (applyDTO.getPeriod() == null || applyDTO.getPeriod().trim().isEmpty()) {
            return ResultDTO.fail(400, "节次不能为空");
        }
        if (applyDTO.getRoom_num() == null || applyDTO.getRoom_num().trim().isEmpty()) {
            return ResultDTO.fail(400, "教室编号不能为空");
        }
        
        // 自动处理period格式：如果只是数字，自动添加"节"字
        String period = applyDTO.getPeriod().trim();
        if (period.matches("^\\d+$")) {
            applyDTO.setPeriod(period + "节");
        }
        
        return userService.submitClassroomApply(applyDTO, token);
    }
    
    /**
     * 用户预约记录
     */
    @GetMapping("/myReservations")
    public ResultDTO<List<ReservationVO>> myReservations(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        return userService.myReservations(page, size, token);
    }
    
    /**
     * 用户确认取消预约
     */
    @PostMapping("/cancelReservation")
    public ResultDTO<Boolean> cancelReservation(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String apply_id) {
        
        // 参数校验
        if (apply_id == null || apply_id.trim().isEmpty()) {
            return ResultDTO.fail(400, "申请编号不能为空");
        }
        
        return userService.cancelReservation(apply_id, token);
    }
}