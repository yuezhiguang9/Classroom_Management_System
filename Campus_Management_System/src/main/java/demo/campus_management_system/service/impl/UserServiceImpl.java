package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UserMapper;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.DTO.ClassroomQueryDTO;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.VO.ClassroomVO;
import demo.campus_management_system.entity.VO.ReservationVO;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public ResultDTO<List<ClassroomVO>> selectClassroom(ClassroomQueryDTO queryDTO) {
        try {
            // 创建分页对象
            Page<ClassroomVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
            
            // 查询可用教室
            Page<ClassroomVO> result = userMapper.selectAvailableClassrooms(
                page, 
                queryDTO.getBuilding_id(),
                queryDTO.getCapacity(),
                queryDTO.getRoom_type(),
                queryDTO.getRoom_status(),
                queryDTO.getDate(),
                queryDTO.getPeriod()
            );
            
            return ResultDTO.success(result.getRecords(), "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    @Override
    public ResultDTO<Boolean> submitClassroomApply(ClassroomApplyDTO applyDTO, String token) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String userAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(userAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }
            
            // 验证是否为普通用户账号（检查是否存在于users表中）
            boolean isValidUser = userMapper.checkUserExists(userAccount);
            if (!isValidUser) {
                return ResultDTO.fail(403, "只有普通用户可以申请教室");
            }
            
            // 生成申请ID (限制在20字符以内)
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String applyId = "apply" + uuid.substring(0, 14); // apply + 14位UUID = 20位
            
            // 获取教室对应的教秘账号
            String secAccount = userMapper.getSecAccountByRoomNum(applyDTO.getRoom_num());
            if (secAccount == null) {
                return ResultDTO.fail(400, "教室信息错误");
            }
            
            // 插入申请记录
            int result = userMapper.insertClassroomApply(
                applyId,
                userAccount,
                applyDTO.getUser_name(),
                applyDTO.getCollege_id(),
                applyDTO.getPhone(),
                applyDTO.getPurpose(),
                applyDTO.getPerson_count(),
                applyDTO.getDate(),
                applyDTO.getWeek(),
                applyDTO.getDay_of_week(),
                applyDTO.getPeriod(),
                applyDTO.getRoom_num(),
                secAccount
            );
            
            if (result > 0) {
                // 生成资源ID并插入教室资源记录 (限制在20字符以内)
                String resUuid = UUID.randomUUID().toString().replace("-", "");
                String resId = "res" + resUuid.substring(0, 17); // res + 17位UUID = 20位
                userMapper.insertClassroomResource(
                    resId,
                    applyDTO.getDate(),
                    "第" + applyDTO.getWeek() + "周",
                    applyDTO.getDay_of_week(),
                    applyDTO.getPeriod(),
                    applyDTO.getRoom_num(),
                    applyId
                );
                
                return ResultDTO.success(true, "预约申请提交成功");
            } else {
                return ResultDTO.fail(400, "预约申请提交失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    @Override
    public ResultDTO<List<ReservationVO>> myReservations(Integer page, Integer size, String token) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String userAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(userAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }
            
            // 创建分页对象
            Page<ReservationVO> pageObj = new Page<>(page, size);
            
            // 查询用户预约记录
            Page<ReservationVO> result = userMapper.selectUserReservations(pageObj, userAccount);
            
            // 获取统计数据
            Integer weekTotal = userMapper.countWeekReservations(userAccount);
            Integer pendingCount = userMapper.countPendingReservations(userAccount);
            
            // 构建返回数据
            List<ReservationVO> records = result.getRecords();
            
            // 在第一条记录中添加统计信息（临时方案）
            if (!records.isEmpty()) {
                ReservationVO firstRecord = records.get(0);
                // 这里可以通过扩展VO或使用Map来返回统计信息
            }
            
            return ResultDTO.success(records, "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    @Override
    public ResultDTO<Boolean> cancelReservation(String applyId, String token) {
        try {
            // 验证token
            String actualToken = JwtUtil.extractToken(token);
            String userAccount = JwtUtil.getUserAccountToken(actualToken);
            if ("error".equals(userAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }
            
            // 检查是否可以取消
            boolean canCancel = userMapper.canCancelReservation(applyId);
            if (!canCancel) {
                return ResultDTO.fail(400, "预约不可取消");
            }
            
            // 取消预约
            int result = userMapper.cancelReservation(applyId);
            
            if (result > 0) {
                return ResultDTO.success(true, "预约已取消");
            } else {
                return ResultDTO.fail(400, "取消预约失败");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
}
