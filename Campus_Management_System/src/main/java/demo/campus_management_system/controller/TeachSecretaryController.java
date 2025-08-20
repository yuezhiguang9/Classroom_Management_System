package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.ClassroomUsageQueryDTO;
import demo.campus_management_system.entity.DTO.UpdateStatusDTO;

import demo.campus_management_system.entity.VO.ClassroomUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.service.service_interface.TeachSecretaryService;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教学秘书控制器
 */
@RestController
@RequestMapping("/sec")
public class TeachSecretaryController {

    @Autowired
    private TeachSecretaryService teachSecretaryService;

    /**
     * 教秘审核工作台
     */
    @GetMapping("/listLogs")
    public ResultDTO<List<ListLogsVO>> listLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String apply_status,
            @RequestParam(required = false) String building_id,
            @RequestParam(required = false) String user_name,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return teachSecretaryService.listLogs(token, apply_status, building_id,
                user_name, date_start, date_end, page, size);
    }

    /**
     * 审核详情查看
     */
    @GetMapping("/viewLogs")
    public ResultDTO<ListLogsVO> viewLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String apply_id) {

        // 参数校验
        if (apply_id == null || apply_id.trim().isEmpty()) {
            return ResultDTO.fail(400, "申请编号不能为空");
        }

        return teachSecretaryService.viewLogs(token, apply_id);
    }

    /**
     * 审核状态更新（可修改批注原因）
     */
    @PostMapping("/updateStatus")
    public ResultDTO<Boolean> updateStatus(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody UpdateStatusDTO updateDTO) {

        // 参数校验
        if (updateDTO.getApply_id() == null || updateDTO.getApply_id().trim().isEmpty()) {
            return ResultDTO.fail(400, "申请编号不能为空");
        }
        if (updateDTO.getApply_status() == null || updateDTO.getApply_status().trim().isEmpty()) {
            return ResultDTO.fail(400, "申请状态不能为空");
        }

        // 验证状态值
        String status = updateDTO.getApply_status();
        if (!"待审核".equals(status) && !"已通过".equals(status) && !"已驳回".equals(status)) {
            return ResultDTO.fail(400, "无效的申请状态");
        }

        return teachSecretaryService.updateStatus(token, updateDTO);
    }

    /**
     * 查看教室使用率页面
     */
    @GetMapping("/classroomUsage")
    public ResultDTO<List<ClassroomUsageVO>> classroomUsage(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String time_range,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(required = false) String building_id,
            @RequestParam(required = false) String room_type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        // 参数校验
        if (time_range == null || time_range.trim().isEmpty()) {
            return ResultDTO.fail(400, "时间范围不能为空");
        }
        if (!"week".equals(time_range) && !"month".equals(time_range) && !"semester".equals(time_range)) {
            return ResultDTO.fail(400, "无效的时间范围");
        }

        // 构建查询DTO
        ClassroomUsageQueryDTO queryDTO = new ClassroomUsageQueryDTO();
        queryDTO.setTime_range(time_range);
        queryDTO.setDate_start(date_start);
        queryDTO.setDate_end(date_end);
        queryDTO.setBuilding_id(building_id);
        queryDTO.setRoom_type(room_type);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return teachSecretaryService.classroomUsage(token, queryDTO);
    }
}