package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    date
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

}
