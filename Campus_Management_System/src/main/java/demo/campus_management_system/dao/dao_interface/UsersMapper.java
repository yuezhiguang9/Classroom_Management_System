package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.VO.myReservationsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
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


    //查询教室状态
    String selectRoomStatus(
            @Param("date") String data,
            @Param("room_num") String room_num,
            @Param("period") String period);

    //查询教室申请信息
    String selectSecAccountByUser(
            @Param("user_account") String user_account
    );

    //提交教室申请
    int submitClassroomApply(
            @Param("user_account") String user_account,
            @Param("purpose") String purpose,
            @Param("person_count") Integer person_count,
            @Param("apply_id") String apply_id,
            @Param("apply_book_time") String apply_book_time,
            @Param("sec_account") String sec_account
    );

    //更新教室资源表
    int updateRes(
            @Param("date") String date,
            @Param("room_num") String room_num,
            @Param("period") String period,
            @Param("apply_id") String apply_id
    );


    //查看个人预约记录
    //本周预约数
    Integer selectUserWeelTotal(
            @Param("user_id") String user_id,
            @Param("StartTime") LocalDateTime StartTime,
            @Param("EndTime") LocalDateTime EndTime);

    //我的待审核数
    Integer selectMyPending(@Param("user_id") String user_id);

    //需要分页的部分
    Page<myReservationsVO> selectMyReservationsVO(
            Page<myReservationsVO> pageObj,
            @Param("user_id") String user_id
    );
    //END查看个人预约记录


    //取消预约
    //更新申请信息表
    int updateApplyInfoCancel(String applyId);

    //更新教室资源表
    int updateClassroomResource(String applyId);
    //END取消预约

}
