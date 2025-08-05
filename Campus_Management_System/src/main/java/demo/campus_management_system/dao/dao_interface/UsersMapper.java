package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.Users;

public interface UsersMapper extends BaseMapper<Users> {
    Page<SelectClassroomDTO> selectClassroom(
            Page<SelectClassroomDTO> pageObj,   //分页
            String building_id,     //楼栋id
            Integer capacity,       //容量
            String room_type,       //教室类型
            String room_status,     //教室状态
            String res_week,        //周次
            String res_day_of_week,    //星期
            String period,      //节次
            String date);//使用时间

}
