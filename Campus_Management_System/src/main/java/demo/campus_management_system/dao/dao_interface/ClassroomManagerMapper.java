package demo.campus_management_system.dao.dao_interface;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.campus_management_system.entity.Classroom_manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface ClassroomManagerMapper extends BaseMapper<Classroom_manager> {

    int updateStatusOne(
            @Param("roomNum") String roomNum,
            @Param("roomStatus") String roomStatus,
            @Param("nowTime") DateTime nowTime);

    int updateStatusOnet(
            @Param("roomNum") String roomNum,
            @Param("nowTime") DateTime nowTime
    );

    int updateStatusTwo(
            @Param("roomNum") String roomNum,
            @Param("roomStatus") String roomStatus,
            @Param("nowTime") DateTime nowTime,
            @Param("duration") DateTime duration);

}
