package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.ClassroomManagerMapper;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.DTO.ClassroomManageQueryDTO;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.service.service_interface.ClassroomManagerService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教室管理员服务实现类
 */
@Service
public class ClassroomManagerServiceImpl extends ServiceImpl<ClassroomManagerMapper, Classroom_manager> implements ClassroomManagerService {
    
    @Autowired
    private ClassroomManagerMapper classroomManagerMapper;
    
    @Override
    public ResultDTO<List<ClassroomManageVO>> selectClassroom(String token, ClassroomManageQueryDTO queryDTO) {
        try {
            // 验证token并获取管理员账号
            String actualToken = JwtUtil.extractToken(token);
            String mgrAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(mgrAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }
            
            // 创建分页对象
            Page<ClassroomManageVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
            
            // 查询管辖教室列表
            Page<ClassroomManageVO> result = classroomManagerMapper.selectManagedClassrooms(
                page,
                mgrAccount,
                queryDTO.getRoom_num(),
                queryDTO.getCapacity(),
                queryDTO.getRoom_status(),
                queryDTO.getRoom_type()
            );
            System.out.println(result);
            
            // 获取今日可用教室数
            Integer todayAvailable = classroomManagerMapper.countTodayAvailableRooms(mgrAccount);
            
            List<ClassroomManageVO> records = result.getRecords();


            // 在第一条记录中设置统计信息
            if (!records.isEmpty()) {
                records.get(0).setToday_available_count(todayAvailable);
            }
            
            return ResultDTO.success(records, "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

}