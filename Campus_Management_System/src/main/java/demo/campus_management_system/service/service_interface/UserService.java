package demo.campus_management_system.service.service_interface;

import com.baomidou.mybatisplus.extension.service.IService;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.DTO.ClassroomQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.VO.ClassroomVO;
import demo.campus_management_system.entity.VO.ReservationVO;
import demo.campus_management_system.util.ResultDTO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<Users> {
    
    /**
     * 查询可用教室
     */
    ResultDTO<List<ClassroomVO>> selectClassroom(ClassroomQueryDTO queryDTO);
    
    /**
     * 提交教室预约申请
     */
    ResultDTO<Boolean> submitClassroomApply(ClassroomApplyDTO applyDTO, String token);
    
    /**
     * 查询用户预约记录
     */
    ResultDTO<List<ReservationVO>> myReservations(Integer page, Integer size, String token);
    
    /**
     * 取消预约
     */
    ResultDTO<Boolean> cancelReservation(String applyId, String token);
}
