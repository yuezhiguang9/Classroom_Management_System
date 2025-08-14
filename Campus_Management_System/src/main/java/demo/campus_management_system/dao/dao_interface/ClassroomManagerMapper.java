package demo.campus_management_system.dao.dao_interface;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.VO.ClassroomManageVO;
import demo.campus_management_system.entity.VO.ApplyInfoManageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教室管理员数据访问层
 */
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
    /**
     * 查询管辖教室列表(分页)
     */
    Page<ClassroomManageVO> selectManagedClassrooms(
            Page<ClassroomManageVO> page,
            @Param("mgrAccount") String mgrAccount,
            @Param("roomNum") String roomNum,
            @Param("capacity") Integer capacity,
            @Param("roomStatus") String roomStatus,
            @Param("roomType") String roomType
    );
    
    /**
     * 统计今日可用教室数
     */
    Integer countTodayAvailableRooms(@Param("mgrAccount") String mgrAccount);
    
    /**
     * 批量更新教室状态
     */
    int batchUpdateRoomStatus(
            @Param("roomNums") List<String> roomNums,
            @Param("roomStatus") String roomStatus,
            @Param("mgrAccount") String mgrAccount
    );

}
