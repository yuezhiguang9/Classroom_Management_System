package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
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
            System.out.println("service层");
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
            //查找当前登录的是不是uses表中的用户
            if (usersMapper.selectById(user_account) == null) {
                return ResultDTO.fail(400, "身份验证失败");
            }
            //获取教室资源id列表
            String[] stringList = classroomApplyDTO.getRes_id();

            //检验这些教室资源是不是都空闲，只有空闲才能预约
            for (String res_id : stringList) {
                if (usersMapper.selectRoomStatus(res_id) == null || !usersMapper.selectRoomStatus(res_id).equals("空闲")) {
                    return ResultDTO.fail(400, res_id + "不是一个空闲教室资源,或者不存在该教室资源");
                }
            }

            // 初始化雪花算法生成器（workerId和dataCenterId自动分配）
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);

            //每次只生成一个申请信息id
            String apply_id = snowflake.nextIdStr();

            //获取预约时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String apply_book_time = sdf.format(new Date());

            //判断条件，用于只执行一次
            boolean flag = true;

            //循环
            for (String res_id : stringList) {
                try {
                    String sec_account;
                    // 获取申请人学院的教秘id
                    sec_account = usersMapper.selectSecAccountByUser(classroomApplyDTO.getUser_account());


                    // 如果找不到，那就安排默认的教秘处理
                    if (sec_account == null || sec_account.isEmpty()) {
                        sec_account = "sec004"; // 配置默认管理员账号
                    }

                    if (flag) {
                        //插入数据，只做一次
                        usersMapper.submitClassroomApply(
                                classroomApplyDTO.getUser_account(),
                                classroomApplyDTO.getPurpose(),
                                classroomApplyDTO.getPerson_count(),
                                apply_id,
                                apply_book_time,
                                sec_account
                        );
                        //把判断条件改成false，往后这个if不会触发
                        flag = false;
                    }
                    //插入完数据再更新教室资源表
                    usersMapper.updateRes(res_id, apply_id);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("资源更新失败: " + res_id, e);
                }
            }
            return ResultDTO.success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(400, e.toString());
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
