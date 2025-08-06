package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.entity.DTO.UserListQueryDTO;
import demo.campus_management_system.entity.DTO.UserOperationDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuperAdminImpl extends ServiceImpl<SuperAdminMapper, Super_admin> implements SuperAdmin {

    @Autowired
    SuperAdminMapper superAdminMapper;

    public ResultDTO<List<ListLogsDTO>> ListLogs(
            String apply_status,
            String college_id,
            String building_id,
            String user_name,
            String date_start,
            String date_end,
            Integer page,
            Integer size) {


        //定义返回包装类
        ResultDTO<List<ListLogsDTO>> resultDTO = new ResultDTO<>();

        List<ListLogsDTO> result = new ArrayList<>();
        try {

            //使用工具类获取本周开始时间
            LocalDateTime startTime = DataUtils.getStartOfCurrentWeek();

            //获取本日结束时间
            LocalDateTime endTime = DataUtils.getEndOfToday();

            //获取全校今日同过数
            Integer TodayPending = superAdminMapper.selectTodayPending();

            //获取本周通过数
            Integer WeekApproved = superAdminMapper.selectWeekApproved(startTime, endTime);

            //获取全校本周驳回数
            Integer WeekRejected = superAdminMapper.selectWeekRejected(startTime, endTime);


            //创建分页对象
            Page<ListLogsVO> pageObj = new Page<>(page, size);

            //获取需要分页数据
            Page<ListLogsVO> ListLogsVo = superAdminMapper.selectRecordsByPage(
                    pageObj,
                    apply_status,
                    college_id,
                    building_id,
                    user_name,
                    date_start,
                    date_end
            );


            //创建一个ListLogsDTO类
            ListLogsDTO listLogsDTO = new ListLogsDTO();

            //获取总页数
            listLogsDTO.setTotal(ListLogsVo.getPages());

            //获取今天待处理数
            listLogsDTO.setToday_pending(TodayPending);

            //获取全校本周已通过数
            listLogsDTO.setWeek_approved(WeekApproved);

            //获取全校本周驳回数
            listLogsDTO.setWeek_rejected(WeekRejected);

            //获取需要分页内容
            listLogsDTO.setRecordsPage(ListLogsVo);

            //把数据包装
            result.add(listLogsDTO);

            //使用返回类
            resultDTO.setCode(200);
            resultDTO.setMsg("返回成功");
            resultDTO.setData(result);

            //返回数据
            return resultDTO;

        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            resultDTO.setCode(404);
            resultDTO.setMsg("资源不存在");

            return resultDTO;
        }

    }

    /**
     * 用户列表查询
     */
    public ResultDTO<List<UserListVO>> listUsers(UserListQueryDTO queryDTO) {
        try {
            // 创建分页对象
            Page<UserListVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
            
            // 查询用户列表
            Page<UserListVO> result = superAdminMapper.selectUserList(
                page,
                queryDTO.getUser_type(),
                queryDTO.getCollege_id(),
                queryDTO.getUser_name(),
                queryDTO.getUser_id()
            );
            
            // 获取总用户数和活跃用户数
            Integer totalUsers = superAdminMapper.countTotalUsers();
            Integer activeUsers = superAdminMapper.countActiveUsers();
            
            // 构建返回数据
            List<UserListVO> records = result.getRecords();
            
            return ResultDTO.success(records, "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    /**
     * 编辑用户
     */
    public ResultDTO<Boolean> updateUser(UserOperationDTO userDTO) {
        try {
            int result = 0;
            String userType = userDTO.getUser_type();
            
            switch (userType) {
                case "user":
                    result = superAdminMapper.updateUserInfo(
                        userDTO.getAccount(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getCollege_id()
                    );
                    break;
                case "teach_sec":
                    result = superAdminMapper.updateTeachSecInfo(
                        userDTO.getAccount(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getCollege_id()
                    );
                    break;
                case "class_mgr":
                    result = superAdminMapper.updateClassroomManagerInfo(
                        userDTO.getAccount(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getBuilding_id()
                    );
                    break;
                case "super_admin":
                    result = superAdminMapper.updateSuperAdminInfo(
                        userDTO.getAccount(),
                        userDTO.getPassword()
                    );
                    break;
            }
            
            return result > 0 ? ResultDTO.success(true, "更新成功") : ResultDTO.fail(400, "更新失败");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    /**
     * 删除用户
     */
    public ResultDTO<Boolean> deleteUser(String account) {
        try {
            // 先删除用户相关的数据，避免外键约束错误
            // 注意删除顺序：先删除资源记录，再删除申请记录（因为资源记录引用申请记录）
            superAdminMapper.deleteUserResources(account);  // 删除资源记录
            superAdminMapper.deleteUserApplies(account);    // 删除申请记录
            
            // 尝试从所有用户表中删除该账号，只要有一个成功就算成功
            int totalDeleted = 0;
            
            // 尝试删除普通用户
            totalDeleted += superAdminMapper.deleteUser(account);
            
            // 尝试删除教学秘书
            totalDeleted += superAdminMapper.deleteTeachSec(account);
            
            // 尝试删除教室管理员
            totalDeleted += superAdminMapper.deleteClassroomManager(account);
            
            // 尝试删除超级管理员
            totalDeleted += superAdminMapper.deleteSuperAdmin(account);
            
            return totalDeleted > 0 ? ResultDTO.success(true, "删除成功") : ResultDTO.fail(400, "用户不存在或删除失败");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    /**
     * 添加用户
     */
    public ResultDTO<Boolean> addUser(UserOperationDTO userDTO) {
        try {
            int result = 0;
            String userType = userDTO.getUser_type();
            
            switch (userType) {
                case "user":
                    result = superAdminMapper.insertUser(
                        userDTO.getAccount(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getCollege_id()
                    );
                    break;
                case "teach_sec":
                    result = superAdminMapper.insertTeachSec(
                        userDTO.getAccount(),
                        userDTO.getName(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getCollege_id()
                    );
                    break;
                case "class_mgr":
                    result = superAdminMapper.insertClassroomManager(
                        userDTO.getAccount(),
                        userDTO.getPassword(),
                        userDTO.getPhone(),
                        userDTO.getBuilding_id()
                    );
                    break;
                case "super_admin":
                    result = superAdminMapper.insertSuperAdmin(
                        userDTO.getAccount(),
                        userDTO.getPassword()
                    );
                    break;
            }
            
            return result > 0 ? ResultDTO.success(true, "添加成功") : ResultDTO.fail(400, "添加失败");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    /**
     * 查看日志详情
     */
    public ResultDTO<ListLogsVO> viewLogDetail(String applyId) {
        try {
            ListLogsVO logDetail = superAdminMapper.selectLogDetailByApplyId(applyId);
            
            if (logDetail == null) {
                return ResultDTO.fail(404, "申请记录不存在");
            }
            
            return ResultDTO.success(logDetail, "查询成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }
    
    /**
     * 数据统计与分析
     */
    public ResultDTO<Object> analyzeData(String timeRange, String dateStart, String dateEnd, 
                                       String collegeId, String buildingId, String roomType, String dataType) {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 根据dataType返回不同的统计数据
            if (dataType == null || "all".equals(dataType)) {
                // 返回所有统计数据
                result.put("totalUsers", superAdminMapper.countTotalUsers());
                result.put("activeUsers", superAdminMapper.countActiveUsers());
                result.put("totalApplies", superAdminMapper.countTotalApplies(timeRange, dateStart, dateEnd));
                result.put("classroomUsageRate", superAdminMapper.calculateUsageRate(timeRange, dateStart, dateEnd));
                result.put("approvalRate", superAdminMapper.calculateApprovalRate(timeRange, dateStart, dateEnd));
            } else {
                // 根据具体类型返回相应数据
                switch (dataType) {
                    case "usage":
                        result.put("classroomUsageRate", superAdminMapper.calculateUsageRate(timeRange, dateStart, dateEnd));
                        result.put("usageDetails", superAdminMapper.getUsageDetails(timeRange, dateStart, dateEnd, collegeId, buildingId, roomType));
                        break;
                    case "peak":
                        result.put("peakAnalysis", superAdminMapper.getPeakTimeAnalysis(timeRange, dateStart, dateEnd));
                        break;
                    case "activity":
                        result.put("userActivity", superAdminMapper.getUserActivityStats(timeRange, dateStart, dateEnd));
                        break;
                    case "success":
                        result.put("approvalRate", superAdminMapper.calculateApprovalRate(timeRange, dateStart, dateEnd));
                        result.put("successDetails", superAdminMapper.getApprovalDetails(timeRange, dateStart, dateEnd));
                        break;
                }
            }
            
            return ResultDTO.success(result, "统计成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

}
