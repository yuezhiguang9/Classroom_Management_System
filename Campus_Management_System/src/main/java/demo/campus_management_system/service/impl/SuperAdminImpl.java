package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.entity.DTO.UserListQueryDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.BuildingUsageVO;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.RoomTypeUsageVO;
import demo.campus_management_system.entity.VO.RoomUsageVO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.*;

@Service
public class SuperAdminImpl extends ServiceImpl<SuperAdminMapper, Super_admin> implements SuperAdmin {

    @Autowired
    SuperAdminMapper superAdminMapper;

    /**
     * 用户列表查询
     */
    public ResultDTO<UserListDTO> listUsers(UserListQueryDTO queryDTO) {
        try {
            // 创建分页对象
            Page<UserListVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());

            // 查询用户列表
            Page<UserListVO> result = superAdminMapper.selectUserList(
                    page,
                    queryDTO.getUser_type(),
                    queryDTO.getCollege_id(),
                    queryDTO.getUser_name(),
                    queryDTO.getUser_id());

            // 获取总用户数和活跃用户数
            Integer totalUsers = superAdminMapper.countTotalUsers();
            Integer activeUsers = superAdminMapper.countActiveUsers();


            // 构建返回数据
            List<UserListVO> records = result.getRecords();
            //构建返回的数据
            UserListDTO userListDTO = new UserListDTO();
            // 设置总用户数
            userListDTO.setPage(queryDTO.getPage());
            // 设置每页显示数量
            userListDTO.setSize(queryDTO.getSize());
            // 设置总记录数
            userListDTO.setTotal(result.getTotal());
            // 设置总用户数
            userListDTO.setTotalUsers(totalUsers);
            // 设置活跃用户数
            userListDTO.setActiveUsers(activeUsers);
            // 设置用户列表
            userListDTO.setUserListVO(records);

            //返回数据
            return ResultDTO.success(userListDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

    // 编辑用户
    public ResultDTO<Boolean> updateUsers(String token, UpdateUsersDTO updateUsersDTO) {

        // 从token中获取账号密码
        String account = JwtUtil.getUserAccountToken(token);
        String password = JwtUtil.getUserPasswordToken(token);

        // 超级管理员才能编辑用户，所以先判断是不是超级管理员
        Boolean isSuperAdmin = superAdminMapper.selectSuperAdmin(account, password);
        if (isSuperAdmin == null || !isSuperAdmin) {
            return ResultDTO.fail(401, "没有权限");
        } else {
            switch (updateUsersDTO.getUser_type()) {
                case "user":
                    if (superAdminMapper.editUsers(updateUsersDTO)) {
                        return ResultDTO.success(true, "查询成功");
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                case "teach_sec":
                    if (superAdminMapper.editTeach(updateUsersDTO)) {
                        return ResultDTO.success(true, "查询成功");
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                case "class_mgr":
                    if (superAdminMapper.editClassMgr(updateUsersDTO)) {
                        return ResultDTO.success(true, "查询成功");
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                default:
                    return ResultDTO.fail(404, "没有选择用户类型");
            }
        }
    }

    // 删除用户
    @Override
    public ResultDTO<Boolean> deleteUsers(String token, DeleteUsersDTO deleteUsersDTO) {
        // 从token中获取当前登录的管理员账号和密码
        String account = JwtUtil.getUserAccountToken(token);
        String password = JwtUtil.getUserPasswordToken(token);

        // 验证是否为超级管理员
        Boolean isSuperAdmin = superAdminMapper.selectSuperAdmin(account, password);
        if (isSuperAdmin == null || !isSuperAdmin) {
            return ResultDTO.fail(401, "没有权限删除用户");
        }

        // 根据用户类型删除对应表中的数据
        switch (deleteUsersDTO.getUser_type()) {
            case "user":
                // 删除普通用户表（users）
                int userDelete = superAdminMapper.deleteUser(deleteUsersDTO.getAccount());
                if (userDelete > 0) {
                    return ResultDTO.success(true, "查询成功");
                } else {
                    return ResultDTO.fail(404, "普通用户不存在或删除失败");
                }
            case "teach_sec":
                // 删除教学秘书表（teach_secretary）
                int teachDelete = superAdminMapper.deleteTeachSec(deleteUsersDTO.getAccount());
                if (teachDelete > 0) {
                    return ResultDTO.success(true, "查询成功");
                } else {
                    return ResultDTO.fail(404, "教学秘书不存在或删除失败");
                }
            case "class_mgr":
                // 删除教室管理员表（classroom_manager）
                int mgrDelete = superAdminMapper.deleteClassMgr(deleteUsersDTO.getAccount());
                if (mgrDelete > 0) {
                    return ResultDTO.success(true, "查询成功");
                } else {
                    return ResultDTO.fail(404, "教室管理员不存在或删除失败");
                }
            default:
                return ResultDTO.fail(400, "用户类型错误，请选择正确类型");
        }
    }

    // 添加用户
    @Override
    public ResultDTO<Boolean> addUsers(String token, AddUsersDTO addUsersDTO) {
        // 1. 验证Token有效性
        String account = JwtUtil.getUserAccountToken(token);
        String password = JwtUtil.getUserPasswordToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }

        // 2. 验证是否为超级管理员
        Boolean isSuperAdmin = superAdminMapper.selectSuperAdmin(account, password);
        if (isSuperAdmin == null || !isSuperAdmin) {
            return ResultDTO.fail(401, "没有权限新增用户");
        }

        // 3. 验证必填参数（账号和密码）
        if (addUsersDTO.getAccount() == null || addUsersDTO.getAccount().trim().isEmpty()) {
            return ResultDTO.fail(400, "账号不能为空");
        }
        if (addUsersDTO.getPassword() == null || addUsersDTO.getPassword().trim().isEmpty()) {
            return ResultDTO.fail(400, "密码不能为空");
        }
        if (addUsersDTO.getUser_type() == null || addUsersDTO.getUser_type().trim().isEmpty()) {
            return ResultDTO.fail(400, "用户类型不能为空");
        }

        // 4. 根据用户类型执行新增操作
        int rowsAffected;
        switch (addUsersDTO.getUser_type()) {
            case "user":
                // 验证普通用户特有必填字段
                if (addUsersDTO.getName() == null || addUsersDTO.getCollege_id() == null) {
                    return ResultDTO.fail(400, "普通用户姓名和学院ID不能为空");
                }
                rowsAffected = superAdminMapper.addUser(addUsersDTO);
                break;
            case "teach_sec":
                // 验证教学秘书特有必填字段
                if (addUsersDTO.getName() == null || addUsersDTO.getCollege_id() == null) {
                    return ResultDTO.fail(400, "教学秘书姓名和学院ID不能为空");
                }
                rowsAffected = superAdminMapper.addTeachSec(addUsersDTO);
                break;
            case "class_mgr":
                // 验证教室管理员特有必填字段
                if (addUsersDTO.getBuilding_id() == null) {
                    return ResultDTO.fail(400, "教室管理员楼栋ID不能为空");
                }
                rowsAffected = superAdminMapper.addClassMgr(addUsersDTO);
                break;
            default:
                return ResultDTO.fail(400, "用户类型错误，请选择正确类型");
        }

        // 5. 返回结果
        if (rowsAffected > 0) {
            return ResultDTO.success(true, "查询成功");
        } else {
            return ResultDTO.fail(500, "新增用户失败");
        }
    }

    // 日志列表
    public ResultDTO<List<ListLogsDTO>> ListLogs(
            String apply_status,
            String college_id,
            String building_id,
            String user_name,
            String date_start,
            String date_end,
            Integer page,
            Integer size) {
        // 定义返回包装类
        ResultDTO<List<ListLogsDTO>> resultDTO = new ResultDTO<>();

        List<ListLogsDTO> result = new ArrayList<>();
        try {

            // 使用工具类获取本周开始时间
            LocalDateTime StartTime = DataUtils.getStartOfCurrentWeek();

            // 获取本日结束时间
            LocalDateTime EndTime = DataUtils.getEndOfToday();

            // 获取全校今日同过数
            Integer TodayPending = superAdminMapper.selectTodayPending();

            // 获取本周通过数
            Integer WeekApproved = superAdminMapper.selectWeekApproved(StartTime, EndTime);

            // 获取全校本周驳回数
            Integer WeekRejected = superAdminMapper.selectWeekRejected(StartTime, EndTime);

            // 创建分页对象
            Page<ListLogsVO> pageObj = new Page<>(page, size);

            // 获取需要分页数据
            Page<ListLogsVO> ListLogsVo = superAdminMapper.selectRecordsByPage(
                    pageObj,
                    apply_status,
                    college_id,
                    building_id,
                    user_name,
                    date_start,
                    date_end);

            // 创建一个ListLogsDTO类
            ListLogsDTO listLogsDTO = new ListLogsDTO();

            // 获取总页数
            listLogsDTO.setTotal(ListLogsVo.getPages());

            // 获取今天待处理数
            listLogsDTO.setToday_pending(TodayPending);

            // 获取全校本周已通过数
            listLogsDTO.setWeek_approved(WeekApproved);

            // 获取全校本周驳回数
            listLogsDTO.setWeek_rejected(WeekRejected);


            // 获取需要分页内容
            listLogsDTO.setRecordsPage(ListLogsVo);


            // 把数据包装
            result.add(listLogsDTO);

            // 使用返回类
            resultDTO.setCode(200);
            resultDTO.setMsg("返回成功");
            resultDTO.setData(result);

            // 返回数据
            return resultDTO;

        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            resultDTO.setCode(404);
            resultDTO.setMsg("资源不存在");

            return resultDTO;
        }

    }

    // 数据统计与分析
    @Override
    public ResultDTO<List<AnalyzeDataDTO>> analyzeData(String token, String dateStart, String dateEnd) {
        try {
            // 1. 权限验证（仅超级管理员）
            String account = JwtUtil.getUserAccountToken(token);
            String password = JwtUtil.getUserPasswordToken(token);
            Super_admin admin = superAdminMapper.selectById(account);
            if (admin == null || !admin.getPassword().equals(password)) {
                return ResultDTO.fail(401, "无权限访问，仅超级管理员可查看");
            }

            // 2. 基础统计数据（所有类型均返回）
            AnalyzeDataDTO baseStats = new AnalyzeDataDTO();
            baseStats.setTotal_users(superAdminMapper.countTotalUsers());
            baseStats.setTotal_teach_secs(superAdminMapper.countTotalTeachSecs());
            baseStats.setTotal_classroom_mgrs(superAdminMapper.countTotalClassroomMgrs());
            baseStats.setActive_users(superAdminMapper.countActiveUsers());
            baseStats.setTotal_applies(superAdminMapper.countTotalApplies(dateStart, dateEnd));
            baseStats.setMom_appt_comparison(superAdminMapper.calculateMomApptComparison());

            // 3. 查询经常使用的教室（前5）
            List<RoomUsageVO> roomUsageVO;
            roomUsageVO = superAdminMapper.countFrequentlyUsedRooms(dateStart, dateEnd);
            baseStats.setActive_classroom(roomUsageVO);

            // 4. 经常使用的教室类型（前5）
            List<RoomTypeUsageVO> roomTypeUsageVOS;
            roomTypeUsageVOS = superAdminMapper.countFrequentlyUsedRoomTypes(dateStart, dateEnd);
            baseStats.setActive_classroom_type(roomTypeUsageVOS);


            // 5. 统计当月每栋楼预约数


            // 3. 统计当月每栋楼预约数

            List<BuildingUsageVO> buildingUsageVOS;
            buildingUsageVOS = superAdminMapper.countMonthlyBuildingApplies();
            baseStats.setTotal_of_building(buildingUsageVOS);


            return ResultDTO.success(Collections.singletonList(baseStats), "查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "数据统计失败：" + e.getMessage());
        }
    }
}
