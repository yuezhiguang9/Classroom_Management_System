package demo.campus_management_system.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.Apply_info;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.DTO.myReservationsDTO;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.VO.myReservationsVO;
import demo.campus_management_system.dao.dao_interface.ApplyInfo;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    ApplyInfo applyInfo;

    //查找教室
    public ResultDTO<Page<SelectClassroomDTO>> selectClassroom(
            String building_id,     //楼栋id
            Integer capacity,       //容量
            String room_type,       //教室类型
            String room_status,     //教室状态
            String res_week,        //周次
            String res_day_of_week,    //星期
            String period,      //节次
            String date,      //使用时间
            Integer page,   //当前页码
            Integer size   //每页条数
    ) {
        //创建返回类
        ResultDTO<Page<SelectClassroomDTO>> resultDTO = new ResultDTO<>();
        try {
            //设置接受返回数据
            Page<SelectClassroomDTO> selectClassroomDTOPage;
            //分页
            Page<SelectClassroomDTO> pageObj = new Page<>(page, size);

            //接受数据
            selectClassroomDTOPage = usersMapper.selectClassroom(
                    pageObj,
                    building_id,     //楼栋id
                    capacity,       //容量
                    room_type,       //教室类型
                    room_status,     //教室状态
                    res_week,        //周次
                    res_day_of_week,    //星期
                    period,      //节次
                    date    //使用时间
            );
            resultDTO.setCode(200);
            resultDTO.setMsg("返回成功");
            resultDTO.setData(selectClassroomDTOPage);
            return resultDTO;
        } catch (Exception e) {
            resultDTO.setCode(400);
            resultDTO.setMsg("返回失败");
            return resultDTO;
        }
    }


    //提交预约
    //确保事物完整性
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO<Boolean> submitClassroomApply(String user_account, ClassroomApplyDTO classroomApplyDTO) {
        try {
            //生成预约id
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
            String apply_id = snowflake.nextId() + "";

            String sec_account = usersMapper.selectSecAccountByUser(user_account);

            //校验预约信息
            for (int i = 0; i < classroomApplyDTO.getPeriod().size(); i++) {
                //如果没有选择预约节次，则返回错误信息
                if (classroomApplyDTO.getPeriod().get(i) == null || classroomApplyDTO.getPeriod().get(i).equals("")) {
                    return ResultDTO.fail(400, "请选择预约节次");
                }
                //如果改教室不是空闲状态，则返回错误信息
                if (!(usersMapper.selectRoomStatus(
                        classroomApplyDTO.getUse_date(),
                        classroomApplyDTO.getRoom_num(),
                        classroomApplyDTO.getPeriod().get(i)).equals("空闲"))
                ) {
                    return ResultDTO.fail(400, "该教室在该时间段已被预约");
                }

            }

            //插入预约信息
            usersMapper.submitClassroomApply(classroomApplyDTO.getUser_account(),
                    classroomApplyDTO.getPurpose(),
                    classroomApplyDTO.getPerson_count(),
                    apply_id,
                    new DateTime().toString(),
                    sec_account);
            
            //更新教室资源表
            for (int i = 0; i < classroomApplyDTO.getPeriod().size(); i++) {
                System.out.println("节次：" + classroomApplyDTO.getPeriod().get(i));
                int updateResResult = usersMapper.updateRes(
                        classroomApplyDTO.getUse_date(),
                        classroomApplyDTO.getRoom_num(),
                        classroomApplyDTO.getPeriod().get(i),
                        apply_id
                );
                if (updateResResult <= 0) {
                    return ResultDTO.fail(400, "更新教室资源失败");
                }
            }
            return ResultDTO.success(true);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }

    //查看个人记录
    public ResultDTO<myReservationsDTO> myReservations(
            String token,   //token
            String user_id, //用户id
            Integer page,   //页数
            Integer size    //每页数量
    ) {
        //定义返回类
        ResultDTO<myReservationsDTO> myReservationsDTOResultDTO = new ResultDTO<>();

        try {
            //验证身份，只有自己才能查看自己的预约记录
            String account = JwtUtil.getUserAccountToken(token);
            if (!account.equals(user_id)) {
                myReservationsDTOResultDTO.setCode(400);
                myReservationsDTOResultDTO.setMsg("身份验证失败");
                return myReservationsDTOResultDTO;
            }

            //身份验证成功后查询
            myReservationsDTO myReservations = new myReservationsDTO();
            LocalDateTime StartTime = DataUtils.getStartOfCurrentWeek();
            LocalDateTime EndTime = DataUtils.getEndOfToday();

            //本周预约数
            myReservations.setWeek_total(usersMapper.selectUserWeelTotal(user_id, StartTime, EndTime));
            //待审核数
            myReservations.setMy_pending(usersMapper.selectMyPending(user_id));
            //需要分页的地方
            Page<myReservationsVO> pageObj = new Page<>(page, size);
            myReservations.setMyReservationsVoPage(usersMapper.selectMyReservationsVO(
                    pageObj,
                    user_id
            ));
            myReservationsDTOResultDTO.setCode(200);
            myReservationsDTOResultDTO.setMsg("查询成功");
            myReservationsDTOResultDTO.setData(myReservations);
            return myReservationsDTOResultDTO;

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        myReservationsDTOResultDTO.setCode(400);
        myReservationsDTOResultDTO.setMsg("查询失败");
        return myReservationsDTOResultDTO;

    }


    //取消预约
    public ResultDTO<Boolean> cancelReservation(String token, String apply_id) {
        try {
            //验证身份信息
            String account = JwtUtil.getUserAccountToken(token);
            //如果这个预约记录的预约人不是当前用户，或者这个记录已经取消过，或者过了使用日期
            if (!account.equals(applyInfo.selectById(apply_id).getUserAccount()) ||
                    applyInfo.selectById(apply_id).getUserCancel() == 1 ||
                    applyInfo.selectById(apply_id).getUserCancel() == 2) {
                return ResultDTO.fail(400, "你没有预约这个记录或者这个预约记录已不可取消");
            } else {
                int updateApplyInfoResult = usersMapper.updateApplyInfoCancel(apply_id);
                int updateResourceResult = usersMapper.updateClassroomResource(apply_id);
                if (updateApplyInfoResult > 0 || updateResourceResult > 0) {
                    return ResultDTO.success(true);
                } else {
                    return ResultDTO.fail(400, "数据库更新失败");
                }

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }

    }


}
