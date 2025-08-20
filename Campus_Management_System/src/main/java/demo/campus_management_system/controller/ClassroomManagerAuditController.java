package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.ClassroomAuditQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomAuditResultDTO;
import demo.campus_management_system.entity.DTO.ResultDTO;
import demo.campus_management_system.service.service_interface.ClassroomManagerAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mgr") // 使用新的路径以避免与旧接口冲突
public class ClassroomManagerAuditController {

    @Autowired
    private ClassroomManagerAuditService classroomManagerAuditNewService;

    @GetMapping("listApplyInfo")
    public ResultDTO<ClassroomAuditResultDTO> getClassroomAuditInfo(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String mgr_account,
            @RequestParam(required = false) String apply_status,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        // 创建查询DTO并设置参数
        ClassroomAuditQueryDTO queryDTO = new ClassroomAuditQueryDTO();
        queryDTO.setToken(token);
        queryDTO.setMgr_account(mgr_account);
        queryDTO.setApply_status(apply_status);
        queryDTO.setDate_start(date_start);
        queryDTO.setDate_end(date_end);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return classroomManagerAuditNewService.getClassroomAuditInfo(queryDTO);
    }
}