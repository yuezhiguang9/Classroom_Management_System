package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {
    @Autowired
    UsersMapper usersMapper;

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
}
