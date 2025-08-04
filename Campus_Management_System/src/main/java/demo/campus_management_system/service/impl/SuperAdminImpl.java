package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.SuperAdminMapper;
import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.entity.DTO.UpdateUsersDTO;
import demo.campus_management_system.entity.Super_admin;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


}
