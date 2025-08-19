package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.ClassroomManageQueryDTO;
import demo.campus_management_system.entity.DTO.BatchUpdateStatusDTO;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.entity.VO.ApplyInfoManageVO;
import demo.campus_management_system.service.service_interface.ClassroomManagerService;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教室管理员控制器
 */
@RestController
@RequestMapping("/mgr")
public class ClassroomManagerController {

    @Autowired
    private ClassroomManagerService classroomManagerService;

    /**
     * 教室分页&筛选
     */
    @GetMapping("/selectClassroom")
    public ResultDTO<List<ClassroomManageVO>> selectClassroom(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String room_num,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String room_status,
            @RequestParam(required = false) String room_type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        // 构建查询DTO
        ClassroomManageQueryDTO queryDTO = new ClassroomManageQueryDTO();
        queryDTO.setRoom_num(room_num);
        queryDTO.setCapacity(capacity);
        queryDTO.setRoom_status(room_status);
        queryDTO.setRoom_type(room_type);
        queryDTO.setPage(page);
        queryDTO.setSize(size);

        return classroomManagerService.selectClassroom(token, queryDTO);
    }

    /**
     * 批量更新教室状态
     */
    @PostMapping("/batchUpdateStatus")
    public ResultDTO<Boolean> batchUpdateStatus(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody BatchUpdateStatusDTO updateDTO) {

        return classroomManagerService.batchUpdateStatus(token, updateDTO);
    }
}