package demo.campus_management_system.service.service_interface;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.DTO.ClassroomManageQueryDTO;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.util.ResultDTO;

import java.util.List;

/**
 * 教室管理员服务接口
 */
public interface ClassroomManagerService extends IService<Classroom_manager> {
    
    /**
     * 教室分页&筛选
     */
    ResultDTO<List<ClassroomManageVO>> selectClassroom(String token, ClassroomManageQueryDTO queryDTO);

}