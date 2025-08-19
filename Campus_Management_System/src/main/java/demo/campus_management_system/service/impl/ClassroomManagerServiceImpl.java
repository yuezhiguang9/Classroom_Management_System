package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.ClassroomManagerMapper;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.DTO.ClassroomManageQueryDTO;
import demo.campus_management_system.entity.DTO.BatchUpdateStatusDTO;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.entity.VO.ApplyInfoManageVO;
import demo.campus_management_system.service.service_interface.ClassroomManagerService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
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


    @Override
    public ResultDTO<Boolean> batchUpdateStatus(String token, BatchUpdateStatusDTO updateDTO) {
        try {
            // 验证token并获取管理员账号
            String actualToken = JwtUtil.extractToken(token);
            String mgrAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(mgrAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 参数校验
            if (updateDTO.getRoom_nums() == null || updateDTO.getRoom_nums().isEmpty()) {
                return ResultDTO.fail(400, "教室编号列表不能为空");
            }
            if (updateDTO.getRoom_status() == null || updateDTO.getRoom_status().trim().isEmpty()) {
                return ResultDTO.fail(400, "教室状态不能为空");
            }

            // 验证状态值
            String status = updateDTO.getRoom_status();
            if (!"可用".equals(status) && !"维修中".equals(status) && !"已占用".equals(status)) {
                return ResultDTO.fail(400, "无效的教室状态");
            }

            // 批量更新教室状态
            int result = classroomManagerMapper.batchUpdateRoomStatus(
                    updateDTO.getRoom_nums(),
                    updateDTO.getRoom_status(),
                    mgrAccount
            );

            if (result > 0) {
                return ResultDTO.success(true, "批量更新成功");
            } else {
                return ResultDTO.fail(400, "更新失败，请检查教室编号是否在您的管辖范围内");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

}