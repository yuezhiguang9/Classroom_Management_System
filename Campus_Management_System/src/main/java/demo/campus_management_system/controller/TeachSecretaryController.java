package demo.campus_management_system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.ClassroomUsageQueryDTO;
import demo.campus_management_system.entity.DTO.UpdateStatusDTO;

import demo.campus_management_system.entity.VO.CalculateClassroomMetricsVo;
import demo.campus_management_system.entity.VO.ClassroomUsageStatsVO;
import demo.campus_management_system.entity.VO.ClassroomUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.service.service_interface.TeachSecretaryService;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public ResultDTO<Page<ListLogsVO>> listLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String applyStatus,
            @RequestParam(required = false) String buildingId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String dateEnd,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(dateStart)) {
            dateStart = date_start;
        }
        if (StringUtils.isEmpty(dateEnd)) {
            dateEnd = date_end;
        }
        return teachSecretaryService.listLogs(token, applyStatus, buildingId,
                userName, dateStart, dateEnd, page, size);
    }


    /**
     * 获取教室使用率统计数据
     */
    @GetMapping("/getClassroomUsageStats")
    public ResultDTO<ClassroomUsageStatsVO> getClassroomUsageStats(
            @RequestHeader(value = "Authorization") String token) {
        return teachSecretaryService.getClassroomUsageStats(token);
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
    public ResultDTO<Page<ClassroomUsageVO>> classroomUsage(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String dateEnd,
            @RequestParam(required = false) String buildingId,
            @RequestParam(required = false) String roomType,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        // 参数校
        
        // 构建查询DTO
        ClassroomUsageQueryDTO queryDTO = new ClassroomUsageQueryDTO();
        queryDTO.setDate_start(dateStart);
        queryDTO.setDate_end(dateEnd);
        queryDTO.setBuilding_id(buildingId);
        queryDTO.setRoom_type(roomType);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return teachSecretaryService.classroomUsage(token, queryDTO);
    }

    /**
     * 查看教室使用率页面
     */
    @GetMapping("/calculateClassroomMetrics")
    public ResultDTO<CalculateClassroomMetricsVo> calculateClassroomMetrics(
            @RequestHeader(value = "Authorization") String token) {
        return teachSecretaryService.calculateClassroomMetrics(token);
    }
}