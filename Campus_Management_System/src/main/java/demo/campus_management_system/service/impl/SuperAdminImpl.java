package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SuperAdminImpl extends ServiceImpl<SuperAdminMapper, Super_admin> implements SuperAdmin {

    @Autowired
    SuperAdminMapper superAdminMapper;

    //编辑用户
    public ResultDTO<Boolean> updateUsers(String token, UpdateUsersDTO updateUsersDTO) {

        //从token中获取账号密码
        String account = JwtUtil.getUserAccountToken(token);
        String password = JwtUtil.getUserPasswordToken(token);

        //超级管理员才能编辑用户，所以先判断是不是超级管理员
        Boolean isSuperAdmin = superAdminMapper.selectSuperAdmin(account, password);
        if (isSuperAdmin == null || !isSuperAdmin) {
            return ResultDTO.fail(401, "没有权限");
        } else {
            switch (updateUsersDTO.getUser_type()) {
                case "user":
                    if (superAdminMapper.editUsers(updateUsersDTO)) {
                        return ResultDTO.success(true);
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                case "teach_sec":
                    if (superAdminMapper.editTeach(updateUsersDTO)) {
                        return ResultDTO.success(true);
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                case "class_mgr":
                    if (superAdminMapper.editClassMgr(updateUsersDTO)) {
                        return ResultDTO.success(true);
                    } else {
                        return ResultDTO.fail(404, "修改失败");
                    }
                default:
                    return ResultDTO.fail(404, "没有选择用户类型");
            }
        }
    }


    //删除用户
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
                    return ResultDTO.success(true);
                } else {
                    return ResultDTO.fail(404, "普通用户不存在或删除失败");
                }
            case "teach_sec":
                // 删除教学秘书表（teach_secretary）
                int teachDelete = superAdminMapper.deleteTeachSec(deleteUsersDTO.getAccount());
                if (teachDelete > 0) {
                    return ResultDTO.success(true);
                } else {
                    return ResultDTO.fail(404, "教学秘书不存在或删除失败");
                }
            case "class_mgr":
                // 删除教室管理员表（classroom_manager）
                int mgrDelete = superAdminMapper.deleteClassMgr(deleteUsersDTO.getAccount());
                if (mgrDelete > 0) {
                    return ResultDTO.success(true);
                } else {
                    return ResultDTO.fail(404, "教室管理员不存在或删除失败");
                }
            default:
                return ResultDTO.fail(400, "用户类型错误，请选择正确类型");
        }
    }

    //添加用户
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
            return ResultDTO.success(true);
        } else {
            return ResultDTO.fail(500, "新增用户失败");
        }
    }

    //日志列表
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
            LocalDateTime StartTime = DataUtils.getStartOfCurrentWeek();

            //获取本日结束时间
            LocalDateTime EndTime = DataUtils.getEndOfToday();

            //获取全校今日同过数
            Integer TodayPending = superAdminMapper.selectTodayPending();

            //获取本周通过数
            Integer WeekApproved = superAdminMapper.selectWeekApproved(StartTime, EndTime);

            //获取全校本周驳回数
            Integer WeekRejected = superAdminMapper.selectWeekRejected(StartTime, EndTime);


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

    // 数据统计与分析
    @Override
    public ResultDTO<List<AnalyzeDataDTO>> analyzeData(String token, String dateStart, String dateEnd,
                                                       String collegeId, String buildingId, String roomType,
                                                       String dataType, Integer page, Integer size) {
        try {
            // 1. 权限验证（仅超级管理员）
            String account = JwtUtil.getUserAccountToken(token);
            String password = JwtUtil.getUserPasswordToken(token);
            Super_admin admin = superAdminMapper.selectById(account);
            if (admin == null || !admin.getPassword().equals(password)) {
                return ResultDTO.fail(401, "无权限访问，仅超级管理员可查看");
            }

            // 2. 初始化分页
            Page<AnalyzeDataDTO> pageObj = new Page<>(page, size);
            List<AnalyzeDataDTO> resultList = new ArrayList<>();

            // 3. 基础统计数据（所有类型均返回）
            AnalyzeDataDTO baseStats = new AnalyzeDataDTO();
            baseStats.setTotal_users(superAdminMapper.countTotalUsers());
            baseStats.setActive_users(superAdminMapper.countActiveUsers());
            baseStats.setTotal_applies(superAdminMapper.countTotalApplies(dateStart, dateEnd));
            baseStats.setApproval_rate(superAdminMapper.calculateApprovalRate(dateStart, dateEnd));
            baseStats.setClassroom_usage_rate(superAdminMapper.calculateClassroomUsageRate(dateStart, dateEnd, buildingId, roomType));
            baseStats.setPage(page);
            baseStats.setSize(size);
            resultList.add(baseStats);

            // 4. 根据dataType添加对应统计数据
            if ("usage".equals(dataType) || "all".equals(dataType) || dataType == null) {
                // 教室类型分布
                IPage<AnalyzeDataDTO> roomTypePage = superAdminMapper.countRoomTypeDistribution(pageObj, collegeId, buildingId);
                baseStats.setTotal((int) roomTypePage.getTotal());
                resultList.addAll(roomTypePage.getRecords());
            }

            if ("peak".equals(dataType) || "all".equals(dataType) || dataType == null) {
                // 高峰时段分析
                IPage<AnalyzeDataDTO> peakPage = superAdminMapper.analyzePeakPeriods(pageObj, dateStart, dateEnd, buildingId, roomType);
                baseStats.setTotal((int) peakPage.getTotal());
                resultList.addAll(peakPage.getRecords());
            }

            if ("success".equals(dataType) || "all".equals(dataType) || dataType == null) {
                // 预约成功率详情
                IPage<AnalyzeDataDTO> applyPage = superAdminMapper.queryApplyDetails(pageObj, dateStart, dateEnd, collegeId, null);
                baseStats.setTotal((int) applyPage.getTotal());
                resultList.addAll(applyPage.getRecords());
            }

            if ("activity".equals(dataType) || "all".equals(dataType) || dataType == null) {
                // 活跃用户数据已包含在baseStats中，无需额外查询
            }

            return ResultDTO.success(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "数据统计失败：" + e.getMessage());
        }
    }
}
